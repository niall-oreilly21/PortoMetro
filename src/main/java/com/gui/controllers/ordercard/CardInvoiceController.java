package com.gui.controllers.ordercard;

import com.metroporto.cards.*;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.enums.Folder;
import com.metroporto.exceptions.DaoException;
import com.gui.controllers.Controller;
import com.metroporto.enums.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.Period;

public class CardInvoiceController extends Controller
{
    private CardPrice cardPrice;

    private CardDaoInterface cardDao;

    @FXML
    private TableView<InvoiceItem> invoiceTable;

    @FXML
    private TableColumn<InvoiceItem, String> nameColumn;

    @FXML
    private TableColumn<InvoiceItem, Integer> quantityColumn;

    @FXML
    private TableColumn<InvoiceItem, Double> priceColumn;

    @FXML
    private TableColumn<InvoiceItem, Double> totalPriceColumn;

    @FXML
    private Label totalInvoicePrice;

    public CardInvoiceController()
    {
        cardDao = new MySqlCardDao();

        try
        {
            userCard = cardDao.findCardByUserId(user.getUserId());
            cardPrice = cardDao.findCardPriceForCard(userCard);
            userCard.setCardPrice(cardPrice);
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro-4");

        ObservableList<InvoiceItem> data = FXCollections.observableArrayList();

        String cardType = getCardType(userCard);

        data.add(new InvoiceItem("Andante " + capitalise(cardType) + " Card", 1,
                cardPrice.getPhysicalCardPrice()));

        int topUpQuantity;

        if (userCard instanceof GreyCard)
        {
            topUpQuantity = (int) Period.between(((GreyCard) userCard).getStartDate(),
                    ((GreyCard) userCard).getEndDate()).toTotalMonths() + 1;
            data.add(new InvoiceItem("Monthly top up - " + userCard.getCardAccessType().getLabel(),
                    topUpQuantity, cardPrice.getTopUpPrice()));
        }

        if (userCard instanceof BlueCard)
        {
            topUpQuantity = ((BlueCard) userCard).getTotalTrips();
            if (userCard instanceof TourCard)
                data.add(new InvoiceItem("1 trip (24 hours) - " + userCard.getCardAccessType().getLabel(),
                        topUpQuantity, cardPrice.getTopUpPrice()));
            else
                data.add(new InvoiceItem("1 trip - " + userCard.getCardAccessType().getLabel(),
                        topUpQuantity, cardPrice.getTopUpPrice()));
        }

        invoiceTable.setItems(data);

        invoiceTable.setFixedCellSize(40);

        int numRows = invoiceTable.getItems().size();
        invoiceTable.setPrefHeight((numRows + 1) * invoiceTable.getFixedCellSize() + 11);

        double total = invoiceTable.getItems().stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();
        String formattedPrice = String.format("€ %.2f", total);
        totalInvoicePrice.setText(formattedPrice);

        priceColumn.setCellFactory(tc -> new TableCell<>()
        {
            @Override
            protected void updateItem(Double price, boolean empty)
            {
                super.updateItem(price, empty);
                if (empty)
                {
                    setText(null);
                } else
                {
                    String formattedPrice = String.format("€ %.2f", price);
                    setText(formattedPrice);
                }
            }
        });

        totalPriceColumn.setCellFactory(tc -> new TableCell<>()
        {
            @Override
            protected void updateItem(Double price, boolean empty)
            {
                super.updateItem(price, empty);
                if (empty)
                {
                    setText(null);
                } else
                {
                    String formattedPrice = String.format("€ %.2f", price);
                    setText(formattedPrice);
                }
            }
        });
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                metro.fitHeightProperty().setValue(newValue)
        );
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.HOME);
    }
}
