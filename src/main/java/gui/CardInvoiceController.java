package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Objects;

public class CardInvoiceController
{
    @FXML
    private ImageView metro1;

    @FXML
    private ImageView logo;

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

    private App app;

    public void setApp(App app) {
        this.app = app;
    }

    public void initialize()
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_4.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        // Set the items of the table
        ObservableList<InvoiceItem> data = FXCollections.observableArrayList();
        data.add(new InvoiceItem("Andante Grey Card", 1, 3));
        data.add(new InvoiceItem("Monthly top up", 3, 30));
        invoiceTable.setItems(data);

        invoiceTable.setFixedCellSize(40);

        int numRows = invoiceTable.getItems().size();
        invoiceTable.setPrefHeight((numRows + 1) * invoiceTable.getFixedCellSize() + 11);

        double total = invoiceTable.getItems().stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();
        String formattedPrice = String.format("€ %.2f", total);
        totalInvoicePrice.setText(formattedPrice);

        priceColumn.setCellFactory(tc -> new TableCell<InvoiceItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    String formattedPrice = String.format("€ %.2f", price);
                    setText(formattedPrice);
                }
            }
        });

        totalPriceColumn.setCellFactory(tc -> new TableCell<InvoiceItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    String formattedPrice = String.format("€ %.2f", price);
                    setText(formattedPrice);
                }
            }
        });
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }

    public void submitForm(ActionEvent event)
    {
    }
}
