package com.gui.controllers.home;

import com.metroporto.cards.*;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;
import com.metroporto.users.Student;
import com.gui.controllers.Controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class CardController extends Controller
{
    private String cardType;

    private CardDaoInterface cardDao;

    @FXML
    private ImageView cardTypeImageView;

    @FXML
    private VBox cardBox;

    @FXML
    private ImageView activityIcon;
    @FXML
    private Label activityLabel;
    @FXML
    private Label cardIdLabel;
    @FXML
    private Label cardTypeLabel;
    @FXML
    private Label accessTypeLabel;
    @FXML
    private Label topUpTitleLabel;
    @FXML
    private Label topUpLabel;

    public CardController()
    {
        user = app.getUser();

        cardDao = new MySqlCardDao();

        try
        {
            userCard = cardDao.findCardByUserId(user.getUserId());
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();

        if (userCard != null)
        {
            initialiseCardImage();
            initialiseCardDetails();
        }
        else
        {
            initialiseNoCard();
        }
    }

    private void initialiseCardImage()
    {
        cardType = getCardType(userCard);

        Image cardTypeImage = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/img/cards/" + cardType + ".png")));
        cardTypeImageView.setImage(cardTypeImage);
    }

    private void initialiseCardDetails()
    {
        if (userCard.isActive())
        {
            activityIcon.setImage(new Image("/img/icons/tick.png"));
            activityLabel.setText("Card is active");
        } else
        {
            activityIcon.setImage(new Image("/img/icons/warning.png"));
            activityLabel.setText("Card inactive");
        }

        cardIdLabel.setText(userCard.getCardId());
        cardTypeLabel.setText(capitalise(cardType) + " Card");

        if (userCard.getZones() == null)
        {
            accessTypeLabel.setText(userCard.getCardAccessType().getLabel());
        } else
        {
            StringBuilder sb = new StringBuilder();

            sb.append(userCard.getCardAccessType().getLabel()).append(" (");

            int count = 0;
            for (Zone zone : userCard.getZones())
            {
                count++;
                if (count == userCard.getZones().size())
                    sb.append(zone.getZoneName());
                else
                    sb.append(zone.getZoneName()).append(", ");
            }

            sb.append(")");
            accessTypeLabel.setText(sb.toString());
        }

        switch (cardType)
        {
            case "student":
            case "grey":
                if (((GreyCard) userCard).getStartDate().isAfter(LocalDate.now()))
                {
                    topUpTitleLabel.setText("Validity start date: ");
                    topUpLabel.setText(((GreyCard) userCard).getStartDate().toString());
                }
                else
                {
                    topUpTitleLabel.setText("Validity end date: ");
                    topUpLabel.setText(((GreyCard) userCard).getEndDate().toString());
                }
                break;
            case "tour":
            case "blue":
                topUpTitleLabel.setText("Total trips: ");
                topUpLabel.setText(Integer.toString(((BlueCard) userCard).getTotalTrips()));
                break;
        }
    }

    private void initialiseNoCard()
    {
        cardBox.getChildren().clear();
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setSpacing(20);
        cardBox.setPadding(new Insets(50, 0, 0, 0));

        ImageView sadFace = new ImageView(new Image("/img/icons/sad-face.png"));
        sadFace.setFitWidth(50);
        sadFace.setFitHeight(50);

        Label text = new Label();
        text.setText("No card found");
        text.getStyleClass().add("subheader-label");
        text.setTextFill(Color.web("#00305f"));

        Label orderCardText = new Label();
        orderCardText.setText("I want a card");
        orderCardText.getStyleClass().add("text-underline");
        orderCardText.setOnMouseClicked(event -> {
            if (user instanceof Student)
            {
                try
                {
                    redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            try
            {
                redirectToPage(event, Folder.ORDER_CARD, Page.PASSENGER_CARD);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        cardBox.getChildren().addAll(sadFace, text, orderCardText);
    }
}