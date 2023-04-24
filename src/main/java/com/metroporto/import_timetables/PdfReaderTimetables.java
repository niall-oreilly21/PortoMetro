package com.metroporto.import_timetables;

import com.metroporto.metro.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReaderTimetables extends ExcelReaderTimetables implements ImportTimetablesInterface
{
    private PortoMetroPdfPageManager portoMetroPdfPageManager;
    private PDFTextStripperByArea stripper;
    private PDDocument document;

    public PdfReaderTimetables(List<Station> stations, List<Line> lines)
    {
        super(stations, lines);
        this.portoMetroPdfPageManager = new PortoMetroPdfPageManager();
        this.stripper = null;
        this.document = null;
    }

    @Override
    public void start()
    {
        String url = "https://en.metrodoporto.pt/metrodoporto/uploads/document/file/635/horarios_abril_2023.pdf";
        try
        {
            document = PDDocument.load(new URL(url).openStream());

            this.stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            for (int i = 1; i < document.getNumberOfPages(); i++)
            {
                portoMetroPdfPageManager.addPageIndex();
                changeLine(portoMetroPdfPageManager.getLineId());

                if(useRouteOne)
                {
                    line.getRoutes().get(1).addTimeTable(getSchedulesFromPdf());
                }
                else
                {
                    line.getRoutes().get(0).addTimeTable(getSchedulesFromPdf());
                }

                switchRoute();
            }
            document.close();
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    }

    private Timetable getSchedulesFromPdf() throws IOException
    {
        // Get the text from the specified region
        String text = extractTextFromPdf();

        List<Station> listStations = getStations(text);

        List<List<LocalTime>> times = getSchedules(text, listStations);
        System.out.println();

        Timetable timetable = new Timetable(portoMetroPdfPageManager.getScheduleDay());

        for (List<LocalTime> departureTimes : times)
        {
            List<Schedule> rowSchedules = new ArrayList<>();

            for (int j = 0; j < departureTimes.size(); j++)
            {
                Station station = listStations.get(j);
                Schedule schedule = new Schedule(station, departureTimes.get(j));
                rowSchedules.add(schedule);
            }

            timetable.addSchedules(rowSchedules);
        }

        return timetable;

    }

    private String extractTextFromPdf() throws IOException
    {
        PDPage page = document.getPage(portoMetroPdfPageManager.getPageIndex());

        // Define the region to extract text from
        int ignoreTop = 800;
        PDRectangle cropBox = page.getCropBox();
        int width = (int) cropBox.getWidth();
        int height = (int) cropBox.getHeight();
        int x = (int) cropBox.getLowerLeftX();
        int y = (int) cropBox.getUpperRightY() - ignoreTop;

        Rectangle region = new Rectangle(x, y, width, height - y);

        stripper.addRegion("region", region);
        stripper.extractRegions(page);

        return stripper.getTextForRegion("region");
    }

    private List<Station> getStations(String text)
    {
        String output = text.replaceAll("\\d{1,2}:\\d{2}\\s+", "");

        String[] onlyText = output.split("\n");

        List<Station>stationList = new ArrayList<>();

        for (String stationText : onlyText)
        {

            for (Station station : stations)
            {

                if (stationText.toLowerCase().contains(station.getStationName().toLowerCase()))
                {
                    stationList.add(station);
                }
            }
        }

        stationList.remove(0);

        if(portoMetroPdfPageManager.isLineC())
        {
            stationList.remove(0);
        }
        return stationList;
    }


    private List<List<LocalTime>> getSchedules(String text, List<Station> stations)
    {
        Pattern pattern = Pattern.compile("(?<!\\d)(\\d{1,2}\\s*:\\s*\\d{2}|\\-)(?!\\d)");
        Matcher matcher = pattern.matcher(text);

        ArrayList<LocalTime> numbers = new ArrayList<>();

        while (matcher.find())
        {
            String match = matcher.group();
            if(match.equalsIgnoreCase("-"))
            {
                match = "04:00";
            }
            LocalTime localTime = LocalTime.parse(match);
            numbers.add(localTime);
        }

        if(portoMetroPdfPageManager.isLineC())
        {
            numbers.remove(numbers.size() - 1);
        }


        List<List<LocalTime>> schedulesList = new ArrayList<>();
        List<LocalTime> currentRow = new ArrayList<>();

        int count = 0;

        for (LocalTime number : numbers)
        {
            currentRow.add(number);
            count++;

            if (count == stations.size())
            {
                schedulesList.add(currentRow);
                currentRow = new ArrayList<>();
                count = 0;
            }
        }

        if (currentRow.size() > 0)
        {
            schedulesList.add(currentRow);
        }

        return schedulesList;
    }

}
