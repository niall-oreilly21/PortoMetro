package com.metroporto.import_timetables;

import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.util.ArrayList;
import java.util.List;

public class ImportTimetables implements ImportTimetablesInterface
{
    public static void main(String[] args)
    {
        ImportTimetables importTimetables = new ImportTimetables();

        importTimetables.start();
    }

    @Override
    public void start()
    {
//        PdfReaderTimetables pdfReaderTimetables = new PdfReaderTimetables(createStations());
//        pdfReaderTimetables.start();

        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(createStations());
        excelReaderTimetables.start();
    }

    private static List<Station> createStations()
    {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("SEM", new Zone(4, "Zone"), "Senhor De Matosinhos"));
        stations.add(new Station("MER",  new Zone(4, "Zone"), "Mercado"));
        stations.add(new Station("BRI",  new Zone(4, "Zone"), "Brito Capelo"));
        stations.add(new Station("MAT",  new Zone(4, "Zone"), "Matosinhos Sul"));
        stations.add(new Station("CAM",  new Zone(4, "Zone"), "Câmara Matosinhos"));
        stations.add(new Station("PAR",  new Zone(4, "Zone"), "Parque de Real"));
        stations.add(new Station("PED",  new Zone(4, "Zone"), "Pedro Hispano"));
        stations.add(new Station("EST",  new Zone(4, "Zone"), "Estádio Do Mar"));
        stations.add(new Station("VAS",  new Zone(4, "Zone"), "Vasco Da Gama"));
        stations.add(new Station("VIS",  new Zone(9, "Zone"), "Viso"));
        stations.add(new Station("RAM",  new Zone(9, "Zone"), "Ramalde"));
        stations.add(new Station("CMS",  new Zone(8, "Zone"), "Carolina Michaelis"));
        stations.add(new Station("LAP",  new Zone(8, "Zone"), "Lapa"));
        stations.add(new Station("TRI",  new Zone(8, "Zone"), "Trindade"));
        stations.add(new Station("BOL",  new Zone(8, "Zone"), "Bolhão"));
        stations.add(new Station("HER",  new Zone(8, "Zone"), "Heroísmo"));
        stations.add(new Station("CPH",  new Zone(8, "Zone"), "Campanhã"));
        stations.add(new Station("PDV",  new Zone(1, "Zone"), "Póvoa De Varzim"));
        stations.add(new Station("SBR",  new Zone(1, "Zone"), "São Brás"));
        stations.add(new Station("PFR",  new Zone(1, "Zone"), "Portas Fronhas"));
        stations.add(new Station("ADP",  new Zone(1, "Zone"), "Alto De Pega"));
        stations.add(new Station("VDC",  new Zone(1, "Zone"), "Vila Do Conde"));
        stations.add(new Station("STC",  new Zone(1, "Zone"), "Santa Clara"));
        stations.add(new Station("AZR",  new Zone(2, "Zone"), "Azurara"));
        stations.add(new Station("ARV",  new Zone(2, "Zone"), "Árvore"));
        stations.add(new Station("VRZ",  new Zone(2, "Zone"), "Varziela"));
        stations.add(new Station("ESN",  new Zone(2, "Zone"), "Espaço Natureza"));
        stations.add(new Station("MDL",  new Zone(2, "Zone"), "Mindelo"));
        stations.add(new Station("VCO",  new Zone(3, "Zone"), "VC Fashion Outlet I Modivas"));
        stations.add(new Station("MDC",  new Zone(3, "Zone"), "Modivas Centro"));
        stations.add(new Station("MDS",  new Zone(3, "Zone"), "Modivas Sul"));
        stations.add(new Station("VLP",  new Zone(3, "Zone"), "Vilar do Pinheiro"));
        stations.add(new Station("LDR",  new Zone(3, "Zone"), "Lidador"));
        stations.add(new Station("PDR",  new Zone(3, "Zone"), "Pedras Rubras"));
        stations.add(new Station("VDS",  new Zone(3, "Zone"), "Verdes"));
        stations.add(new Station("CRT",  new Zone(3, "Zone"), "Crestins"));
        stations.add(new Station("CDC",  new Zone(5, "Zone"), "Custóias"));
        stations.add(new Station("FDC",  new Zone(6, "Zone"), "Fonte Do Cuco"));
        stations.add(new Station("CDM",  new Zone(8, "Zone"), "Casa Da Música"));
        stations.add(new Station("ISM",  new Zone(5, "Zone"), "ISMAI"));
        stations.add(new Station("CAS",  new Zone(5, "Zone"), "Castêlo Da Maia"));
        stations.add(new Station("MAN",  new Zone(5, "Zone"), "Mandim"));
        stations.add(new Station("ZIN",  new Zone(5, "Zone"), "Zona Indústrial"));
        stations.add(new Station("FMA",  new Zone(6, "Zone"), "Fórum Maia"));
        stations.add(new Station("PMA",  new Zone(6, "Zone"), "Parque Maia"));
        stations.add(new Station("CUS",  new Zone(6, "Zone"), "Custió"));
        stations.add(new Station("ARA",  new Zone(6, "Zone"), "Araújo"));
        stations.add(new Station("PIA",  new Zone(6, "Zone"), "Pias"));
        stations.add(new Station("CDR",  new Zone(6, "Zone"), "Cândido Dos Reis"));
        stations.add(new Station("FRA",  new Zone(8, "Zone"), "Francos"));
        stations.add(new Station("C24",  new Zone(8, "Zone"), "24 De Agosto"));
        stations.add(new Station("STO", new Zone(12, "Zone"), "Santo Ovídio"));
        stations.add(new Station("DJ2",  new Zone(12, "Zone"), "D. João II"));
        stations.add(new Station("JDD",  new Zone(12, "Zone"), "João De Deus"));
        stations.add(new Station("CGV",  new Zone(12, "Zone"), "Câmara De Gaia"));
        stations.add(new Station("GTO",  new Zone(12, "Zone"), "General Torres"));
        stations.add(new Station("JDM",  new Zone(12, "Zone"), "Jardim Do Morro"));
        stations.add(new Station("SBO",  new Zone(8, "Zone"), "São Bento"));
        stations.add(new Station("ALD",  new Zone(8, "Zone"), "Aliados"));
        stations.add(new Station("FGU",  new Zone(8, "Zone"), "Faria Guimarães"));
        stations.add(new Station("MRQ",  new Zone(8, "Zone"), "Marquês"));
        stations.add(new Station("CTE",  new Zone(8, "Zone"), "Combatentes"));
        stations.add(new Station("SLG",  new Zone(8, "Zone"), "Salgueiros"));
        stations.add(new Station("PLU",  new Zone(10, "Zone"), "Pólo Universitário"));
        stations.add(new Station("IPO",  new Zone(10, "Zone"), "IPO"));
        stations.add(new Station("HSJ",  new Zone(10, "Zone"), "Hospital De São João"));
        stations.add(new Station("DRG",  new Zone(8, "Zone"), "Estádio Do Dragão"));
        stations.add(new Station("SEB",  new Zone(9, "Zone"), "Sete Bicas"));
        stations.add(new Station("ESP",  new Zone(5, "Zone"), "Esposade"));
        stations.add(new Station("BOT",  new Zone(3, "Zone"), "Botica"));
        stations.add(new Station("APO",  new Zone(3, "Zone"), "Aeroporto"));
        stations.add(new Station("SDH",  new Zone(9, "Zone"), "Senhora Da Hora"));
        stations.add(new Station("CON",  new Zone(10, "Zone"), "Contumil"));
        stations.add(new Station("NAS",  new Zone(10, "Zone"), "Nasoni"));
        stations.add(new Station("NAV",  new Zone(10, "Zone"), "Nau Vitória"));
        stations.add(new Station("LEV",  new Zone(7, "Zone"), "Levada"));
        stations.add(new Station("RTO",  new Zone(7, "Zone"), "Rio Tinto"));
        stations.add(new Station("CPA",  new Zone(7, "Zone"), "Campainha"));
        stations.add(new Station("BGM",  new Zone(7, "Zone"), "Baguim"));
        stations.add(new Station("CAR",  new Zone(7, "Zone"), "Carreira"));
        stations.add(new Station("VEN",  new Zone(11, "Zone"), "Venda Nova"));
        stations.add(new Station("FNZ", new Zone(11, "Zone"), "Fânzeres"));

        return stations;
    }


}

