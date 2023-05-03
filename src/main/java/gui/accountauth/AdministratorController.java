package gui.accountauth;

import com.metroporto.cards.*;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
import com.metroporto.enums.TrainModel;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Train;
import com.metroporto.users.Administrator;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;
import gui.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AdministratorController extends Controller
{
    private TrainDaoInterface trainDao;
    private LineDaoInterface lineDao;
    private UserDaoInterface userDao;
    private CardDaoInterface cardDao;

    private Administrator admin;

    @FXML
    private Label trainsLabel;
    @FXML
    private Label usersLabel;
    @FXML
    private Label cardsLabel;
    @FXML
    private Label signOutLabel;
    @FXML
    private Label greet;

    @FXML
    private ImageView administratorIcon;
    @FXML
    private ImageView trainsIcon;
    @FXML
    private ImageView usersIcon;
    @FXML
    private ImageView cardsIcon;
    @FXML
    private ImageView signOut;

    @FXML
    private VBox contentBox;

    private List<Train> trains;
    private List<Line> lines;
    private MenuButton filterTrainsByLine;
    private TableView<Train> trainsTable;
    private ObservableList<Train> trainsTableData;

    private MenuButton filterUsersByType;
    private TableView<User> usersTable;
    private ObservableList<User> usersTableData;
    private List<User> users;

    private TableView<Card> cardsTable;
    private MenuButton filterCardsByActivity;
    private ObservableList<Card> cardsTableData;
    private List<Card> cards;

    public AdministratorController()
    {
        trainDao = new MySqlTrainDao();
        lineDao = new MySqlLineDao();
        userDao = new MySqlUserDao();
        cardDao = new MySqlCardDao();
        admin = (Administrator) app.getUser();

        try
        {
            trains = trainDao.findAll();
            lines = lineDao.findAll();
            users = userDao.findAll();
            cards = cardDao.findAll();
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    @Override
    public void initialize()
    {
        initialiseLogo();

        Image adminImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/administrator.png")));
        administratorIcon.setImage(adminImage);

        Image trainImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/train.png")));
        trainsIcon.setImage(trainImage);

        Image usersImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/group.png")));
        usersIcon.setImage(usersImage);

        Image cardsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card-white.png")));
        cardsIcon.setImage(cardsImage);

        Image signOutImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sign-out.png")));
        signOut.setImage(signOutImage);

        greet.setText("Hello, " + admin.getFirstName() + "!");

        initialiseTrains();
    }

    private void initialiseTrains()
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        trainsLabel.getStyleClass().add("nav-active");
        usersLabel.getStyleClass().remove("nav-active");
        cardsLabel.getStyleClass().remove("nav-active");

        HBox heading = new HBox();
        heading.setPadding(new Insets(0, 10, 20, 10));
        heading.setAlignment(Pos.CENTER);

        Label title = new Label();
        title.getStyleClass().add("subheader-label-large");
        title.setText("Trains");
        title.setTextFill(Color.web("#00305f"));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        filterTrainsByLine = new MenuButton();
        filterTrainsByLine.setText("Filter by Line");
        List<RadioMenuItem> radioMenuItems = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();

        for (Line line : lines)
        {
            RadioMenuItem radioMenuItem = new RadioMenuItem(line.getLineId() + " | " + line.getLineName());
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItems.add(radioMenuItem);
        }

        filterTrainsByLine.getItems().addAll(radioMenuItems);

        Button clear = new Button("Clear");
        clear.setOnAction(this::clearLineFilter);
        clear.getStyleClass().add("form-button");

        heading.getChildren().addAll(title, region, filterTrainsByLine, clear);

        trainsTable = new TableView<>();
        trainsTable.setPrefWidth(700);

        TableColumn<Train, String> trainId = new TableColumn<>("ID");
        TableColumn<Train, TrainModel> trainModel = new TableColumn<>("Train Model");
        TableColumn<Train, Integer> trainCarriages = new TableColumn<>("Carriages");
        TableColumn<Train, Integer> trainCapacity = new TableColumn<>("Capacity");
        TableColumn<Train, String> trainLine = new TableColumn<>("Line");

        trainId.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        trainModel.setCellValueFactory(new PropertyValueFactory<>("trainModel"));
        trainModel.setCellFactory(column ->
                new TableCell<>()
                {
                    @Override
                    protected void updateItem(TrainModel item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item == null || empty)
                        {
                            setText(null);
                        } else
                        {
                            setText(item.getLabel());
                        }
                    }
                });
        trainCarriages.setCellValueFactory(new PropertyValueFactory<>("carriages"));
        trainCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        trainLine.setCellValueFactory(cellData ->
        {
            Train train = cellData.getValue();
            try
            {
                for (Line line : lines)
                {
                    if (trainDao.findAllTrainsByLineId(line.getLineId()).contains(train))
                        return new SimpleStringProperty(line.getLineId() + " | " + line.getLineName());
                }

            } catch (DaoException de)
            {
                de.printStackTrace();
            }

            return null;
        });

        double tableWidth = trainsTable.getPrefWidth();
        trainId.setPrefWidth(tableWidth * 0.2);
        trainModel.setPrefWidth(tableWidth * 0.375);
        trainCarriages.setPrefWidth(tableWidth * 0.165);
        trainCapacity.setPrefWidth(tableWidth * 0.165);
        trainLine.setPrefWidth(tableWidth * 0.165);

        trainsTableData = FXCollections.observableArrayList(trains);
        trainsTable.setItems(trainsTableData);

        trainsTable.getColumns().add(trainId);
        trainsTable.getColumns().add(trainModel);
        trainsTable.getColumns().add(trainCarriages);
        trainsTable.getColumns().add(trainCapacity);
        trainsTable.getColumns().add(trainLine);

        for (RadioMenuItem radioMenuItem : radioMenuItems)
        {
            radioMenuItem.setOnAction(event ->
            {
                filterTrainsByLine.setText(radioMenuItem.getText());

                String lineId = radioMenuItem.getText().split("\\|")[0];

                try
                {
                    trainsTableData = FXCollections.observableArrayList(trainDao.findAllTrainsByLineId(lineId));
                    trainsTable.setItems(trainsTableData);
                } catch (DaoException de)
                {
                    de.printStackTrace();
                }
            });
        }

        contentBox.getChildren().addAll(heading, trainsTable);
    }

    private void clearLineFilter(ActionEvent event)
    {
        filterTrainsByLine.setText("Filter by Line");

        ObservableList<MenuItem> items = filterTrainsByLine.getItems();

        for (MenuItem item : items)
        {
            RadioMenuItem radioItem = (RadioMenuItem) item;
            radioItem.setSelected(false);
        }

        trainsTableData = FXCollections.observableArrayList(trains);
        trainsTable.setItems(trainsTableData);
    }

    public void initialiseTrainsEvent(MouseEvent event)
    {
        initialiseTrains();
    }

    public void initialiseUsersEvent(MouseEvent event)
    {
        initialiseUsers();
    }

    private void initialiseUsers()
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        usersLabel.getStyleClass().add("nav-active");
        trainsLabel.getStyleClass().remove("nav-active");
        cardsLabel.getStyleClass().remove("nav-active");

        HBox heading = new HBox();
        heading.setPadding(new Insets(0, 10, 20, 10));
        heading.setAlignment(Pos.CENTER);

        Label title = new Label();
        title.getStyleClass().add("subheader-label-large");
        title.setText("Users");
        title.setTextFill(Color.web("#00305f"));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        filterUsersByType = new MenuButton();
        filterUsersByType.setText("Filter by User Type");
        List<RadioMenuItem> radioMenuItems = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();

        String[] userTypes = {"Passenger", "Student", "Administrator"};

        for (String type : userTypes)
        {
            RadioMenuItem radioMenuItem = new RadioMenuItem(type);
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItems.add(radioMenuItem);
        }

        filterUsersByType.getItems().addAll(radioMenuItems);

        Button clear = new Button("Clear");
        clear.setOnAction(this::clearUserTypeFilter);
        clear.getStyleClass().add("form-button");

        heading.getChildren().addAll(title, region, filterUsersByType, clear);

        usersTable = new TableView<>();
        usersTable.setPrefWidth(700);

        TableColumn<User, Integer> userId = new TableColumn<>("ID");
        TableColumn<User, String> email = new TableColumn<>("E-mail");
        TableColumn<User, String> firstName = new TableColumn<>("First Name");
        TableColumn<User, String> surname = new TableColumn<>("Surname");
        TableColumn<User, String> userType = new TableColumn<>("User Type");
        TableColumn<User, Void> deleteUserButton = new TableColumn<>();

        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userType.setCellValueFactory(cellData ->
        {
            User user = cellData.getValue();
            if (user instanceof Passenger)
                if (user instanceof Student)
                    return new SimpleStringProperty("Student");
                else
                    return new SimpleStringProperty("Passenger");

            if (user instanceof Administrator)
                return new SimpleStringProperty("Administrator");

            return null;
        });

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory =
                new Callback<>()
                {
                    @Override
                    public TableCell<User, Void> call(final TableColumn<User, Void> param)
                    {
                        return new TableCell<>()
                        {

                            private final Button button = new Button();

                            @Override
                            public void updateItem(Void item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                if (empty)
                                {
                                    setGraphic(null);
                                } else
                                {
                                    button.setOnAction(event ->
                                    {
                                        int index = getIndex();
                                        User user = usersTable.getItems().get(index);
                                        try
                                        {
                                            userDao.remove(user);
                                            usersTable.getItems().remove(user);
                                            users = userDao.findAll();
                                        } catch (DaoException de)
                                        {
                                            de.printStackTrace();
                                        }
                                    });
                                    button.getStyleClass().add("form-button");
                                    ImageView trash = new ImageView();
                                    trash.setFitHeight(15);
                                    trash.setFitWidth(15);
                                    Image trashImage = new Image(Objects.requireNonNull(getClass()
                                            .getResourceAsStream("/img/trash-can.png")));
                                    trash.setImage(trashImage);
                                    button.setGraphic(trash);
                                    setGraphic(button);
                                }
                            }
                        };
                    }
                };
        deleteUserButton.setCellFactory(cellFactory);


        double tableWidth = trainsTable.getPrefWidth();
        userId.setPrefWidth(tableWidth * 0.075);
        email.setPrefWidth(tableWidth * 0.365);
        firstName.setPrefWidth(tableWidth * 0.18);
        surname.setPrefWidth(tableWidth * 0.18);
        deleteUserButton.setPrefWidth(tableWidth * 0.1);

        usersTableData = FXCollections.observableArrayList(users);
        usersTable.setItems(usersTableData);

        usersTable.getColumns().add(userId);
        usersTable.getColumns().add(email);
        usersTable.getColumns().add(firstName);
        usersTable.getColumns().add(surname);
        usersTable.getColumns().add(userType);
        usersTable.getColumns().add(deleteUserButton);

        for (RadioMenuItem radioMenuItem : radioMenuItems)
        {
            radioMenuItem.setOnAction(event ->
            {
                filterUsersByType.setText(radioMenuItem.getText());

                usersTableData = FXCollections.observableArrayList();

                for (User user : users)
                {
                    switch (radioMenuItem.getText())
                    {
                        case "Passenger":
                            if (user instanceof Passenger)
                                if (!(user instanceof Student))
                                    usersTableData.add(user);
                            break;
                        case "Student":
                            if (user instanceof Student)
                                usersTableData.add(user);
                            break;
                        case "Administrator":
                            if (user instanceof Administrator)
                                usersTableData.add(user);
                            break;
                        default:
                            break;
                    }
                }

                usersTable.setItems(usersTableData);

            });
        }

        contentBox.getChildren().addAll(heading, usersTable);
    }

    private void clearUserTypeFilter(ActionEvent event)
    {
        filterUsersByType.setText("Filter by User Type");

        ObservableList<MenuItem> items = filterUsersByType.getItems();

        for (MenuItem item : items)
        {
            RadioMenuItem radioItem = (RadioMenuItem) item;
            radioItem.setSelected(false);
        }

        usersTableData = FXCollections.observableArrayList(users);
        usersTable.setItems(usersTableData);
    }

    public void initialiseCardsEvent(MouseEvent event)
    {
        initialiseCards();
    }

    private void initialiseCards()
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        usersLabel.getStyleClass().remove("nav-active");
        trainsLabel.getStyleClass().remove("nav-active");
        cardsLabel.getStyleClass().add("nav-active");

        HBox heading = new HBox();
        heading.setPadding(new Insets(0, 10, 20, 10));
        heading.setAlignment(Pos.CENTER);

        Label title = new Label();
        title.getStyleClass().add("subheader-label-large");
        title.setText("Cards");
        title.setTextFill(Color.web("#00305f"));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        filterCardsByActivity = new MenuButton();
        filterCardsByActivity.setText("Filter by Activity");
        List<RadioMenuItem> radioMenuItems = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();

        String[] activityTypes = {"Active", "Inactive"};

        for (String type : activityTypes)
        {
            RadioMenuItem radioMenuItem = new RadioMenuItem(type);
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItems.add(radioMenuItem);
        }

        filterCardsByActivity.getItems().addAll(radioMenuItems);

        Button clear = new Button("Clear");
        clear.setOnAction(this::clearCardTypeFilter);
        clear.getStyleClass().add("form-button");

        heading.getChildren().addAll(title, region, filterCardsByActivity, clear);

        cardsTable = new TableView<>();
        cardsTable.setPrefWidth(700);

        TableColumn<Card, Integer> userId = new TableColumn<>("ID");
        TableColumn<Card, String> cardType = new TableColumn<>("Card Type");
        TableColumn<Card, String> isActive = new TableColumn<>("Activity");
        TableColumn<Card, String> accessType = new TableColumn<>("Access");
        TableColumn<Card, Double> price = new TableColumn<>("Price");
        TableColumn<Card, LocalDate> endDateTime = new TableColumn<>("End Date");
        TableColumn<Card, Integer> numberOfTrips = new TableColumn<>("Num Trips");
        TableColumn<Card, Double> topUpPrice = new TableColumn<>("Top Up");
        TableColumn<Card, Void> deleteCardButton = new TableColumn<>();

        cardsTable.getColumns().add(userId);
        cardsTable.getColumns().add(cardType);
        cardsTable.getColumns().add(isActive);
        cardsTable.getColumns().add(accessType);
        cardsTable.getColumns().add(price);
        cardsTable.getColumns().add(endDateTime);
        cardsTable.getColumns().add(numberOfTrips);
        cardsTable.getColumns().add(topUpPrice);
        cardsTable.getColumns().add(deleteCardButton);

        double tableWidth = trainsTable.getPrefWidth();
        userId.setPrefWidth(tableWidth * 0.07);
        isActive.setPrefWidth(tableWidth * 0.12);
        accessType.setPrefWidth(tableWidth * 0.12);
        price.setPrefWidth(tableWidth * 0.1);
        endDateTime.setPrefWidth(tableWidth * 0.16);
        numberOfTrips.setPrefWidth(tableWidth * 0.13);
        topUpPrice.setPrefWidth(tableWidth * 0.11);

        userId.setCellValueFactory(new PropertyValueFactory<>("cardId"));

        cardType.setCellValueFactory(cellData ->
        {
            Card card = cellData.getValue();
            if (card instanceof GreyCard)
                if (card instanceof StudentCard)
                    return new SimpleStringProperty("Student card");
                else
                    return new SimpleStringProperty("Grey card");

            if (card instanceof BlueCard)
                if (card instanceof TourCard)
                    return new SimpleStringProperty("Tour card");
                else
                    return new SimpleStringProperty("Blue card");

            return null;
        });

        isActive.setCellValueFactory(cellData ->
        {
            Card card = cellData.getValue();
            if (card.isActive())
            {
                return new SimpleStringProperty("Active");
            } else
            {
                return new SimpleStringProperty("Inactive");
            }
        });

        accessType.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCardAccessType().getLabel())
        );

        price.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCardPrice().getPhysicalCardPrice()));

        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.getDefault()));
        StringConverter<Double> converter = new StringConverter<>()
        {
            @Override
            public String toString(Double object)
            {
                return "€" + df.format(object);
            }

            @Override
            public Double fromString(String string)
            {
                return null;
            }
        };
        price.setCellFactory(col -> new TableCell<>()
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
                    setText(converter.toString(price));
                }
            }
        });

        endDateTime.setCellValueFactory(cellData ->
        {
            Card card = cellData.getValue();
            if (card instanceof GreyCard)
            {
                LocalDate endDate = ((GreyCard) card).getEndDate();
                return new SimpleObjectProperty<>(endDate);
            }
            return null;
        });

        numberOfTrips.setCellValueFactory(cellData ->
        {
            Card card = cellData.getValue();

            if (card instanceof BlueCard)
            {
                int numberOfTripsValue = ((BlueCard) card).getTotalTrips();
                return new SimpleObjectProperty<>(numberOfTripsValue);
            } else
            {
                return null;
            }
        });

        topUpPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCardPrice().getTopUpPrice()));

        topUpPrice.setCellFactory(col -> new TableCell<>()
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
                    setText(converter.toString(price));
                }
            }
        });

        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory =
                new Callback<>()
                {
                    @Override
                    public TableCell<Card, Void> call(final TableColumn<Card, Void> param)
                    {
                        return new TableCell<>()
                        {

                            private final Button button = new Button();

                            @Override
                            public void updateItem(Void item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                if (empty)
                                {
                                    setGraphic(null);
                                } else
                                {
                                    button.setOnAction(event ->
                                    {
                                        int index = getIndex();
                                        Card card = cardsTable.getItems().get(index);
                                        try
                                        {
                                            cardDao.remove(card);
                                            cardsTable.getItems().remove(card);
                                            cards = cardDao.findAll();
                                        } catch (DaoException de)
                                        {
                                            de.printStackTrace();
                                        }
                                    });
                                    button.getStyleClass().add("form-button");
                                    ImageView trash = new ImageView();
                                    trash.setFitHeight(15);
                                    trash.setFitWidth(15);
                                    Image trashImage = new Image(Objects.requireNonNull(getClass()
                                            .getResourceAsStream("/img/trash-can.png")));
                                    trash.setImage(trashImage);
                                    button.setGraphic(trash);
                                    setGraphic(button);
                                }
                            }
                        };
                    }
                };
        deleteCardButton.setCellFactory(cellFactory);

        cardsTableData = FXCollections.observableArrayList(cards);
        cardsTable.setItems(cardsTableData);

        for (RadioMenuItem radioMenuItem : radioMenuItems)
        {
            radioMenuItem.setOnAction(event ->
            {
                cardsTableData = FXCollections.observableArrayList();

                filterCardsByActivity.setText(radioMenuItem.getText());

                for (Card card : cards)
                {
                    switch (radioMenuItem.getText())
                    {
                        case "Active":
                            if (card.isActive())
                                cardsTableData.add(card);
                            break;
                        case "Inactive":
                            if (!card.isActive())
                                cardsTableData.add(card);
                            break;
                        default:
                            break;
                    }
                }

                cardsTable.setItems(cardsTableData);
            });
        }

        contentBox.getChildren().addAll(heading, cardsTable);
    }

    private void clearCardTypeFilter(ActionEvent event)
    {
        filterCardsByActivity.setText("Filter by Activity");

        ObservableList<MenuItem> items = filterCardsByActivity.getItems();

        for (MenuItem item : items)
        {
            RadioMenuItem radioItem = (RadioMenuItem) item;
            radioItem.setSelected(false);
        }

        cardsTableData = FXCollections.observableArrayList(cards);
        cardsTable.setItems(cardsTableData);
    }

    public void signOut(MouseEvent event) throws IOException
    {
        trainsLabel.getStyleClass().remove("nav-active");
        usersLabel.getStyleClass().remove("nav-active");
        cardsLabel.getStyleClass().remove("nav-active");
        signOutLabel.getStyleClass().add("nav-active");

        app.setUser(null);
        redirectToPage(event, Folder.ACCOUNT_AUTH, Page.SIGN_IN);
    }
}