package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static javafx.application.Application.launch;


public class Main extends Application
{
    public void saveData(List<CarEntity> v) {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream("VehicleData.csv");
            ObjectOutputStream  objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (CarEntity veh : v) objectOutputStream.writeObject(veh);
            objectOutputStream.close();
            objectOutputStream.flush();
        } catch (IOException e)
        {
            System.err.println(e);
        }
    }
    public void loadData(List<CarEntity> v) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("VehicleData.csv"))) {
            while (true) {
                CarEntity temp = (CarEntity) objectInputStream.readObject();
                v.add(temp);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
    }
    public void saveTransactions(List<Transaction> transactions) {
        try {
            FileOutputStream outTrans = new FileOutputStream("Transactions.csv");
            ObjectOutputStream outObjTrans = new ObjectOutputStream(outTrans);
            for (Transaction transaction : transactions) outObjTrans.writeObject(transaction);
            outObjTrans.close();
            outTrans.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public void loadTransactions(List<Transaction> transactions) {
        try (ObjectInputStream inObjTrans = new ObjectInputStream(new FileInputStream("Transactions.csv")))
        {
            while (true)
            {
                Transaction tempTrans = (Transaction) inObjTrans.readObject();
                transactions.add(tempTrans);
            }
        } catch (IOException | ClassNotFoundException e)
        {
            System.err.println(e);
            System.err.println("EOF!");
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("BuyYourselfACar");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setWidth(950);
        primaryStage.setHeight(400);
        primaryStage.setResizable(true);
        //___________________________________________________________
        final Label label = new Label("Available cars");
        label.setFont(new Font("Arial", 20));

        //final Map<CarEntity,SaloonEntity> map=new HashMap<CarEntity,SaloonEntity>();
        //final ObservableMap<CarEntity, SaloonEntity> data = FXCollections.observableMap(map);

        final ObservableList<CarEntity>[] data = new ObservableList[]{FXCollections.observableArrayList()};
        final ObservableList<SaloonEntity>[] saloonData = new ObservableList[]{FXCollections.observableArrayList()};
        final List<CarEntity>[] carList = new List[]{new ArrayList<>()};
        final List<SaloonEntity>[] saloonList = new List[]{new ArrayList<>()};
        SessionFactory sessionFactory;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        //Loading from db
        Controller controller=new Controller(sessionFactory);
        carList[0] = controller.listVehicles();
        saloonList[0] = controller.listSaloons();
        data[0] = FXCollections.observableArrayList(carList[0]);
        saloonData[0]=FXCollections.observableArrayList(saloonList[0]);

        if(!carList[0].isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("A data has been loaded from DB!");
            alert.showAndWait();
        }
        else if (carList[0].isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loading backup!");
            alert.setHeaderText("Unable to load stored records in DB - loading backup!");
            alert.showAndWait();
            controller.addVehicle(1,"Toyota","Supra", "used", 300000, 1999, 20000,5.0);
            controller.addVehicle(2,"Mazda","Rx-7", "used", 150000, 1997, 200000, 2.5);
            controller.addVehicle(3,"Nissan"," 350Z", "used", 400000, 2002, 4200000, 4.0);
            controller.addVehicle(4,"Mazda","Rx-8", "used", 80000, 2017, 2000, 4.6);
            controller.addVehicle(5,"Nissan"," 350Z", "used", 410000, 2002, 4200000, 4.0);
            controller.addVehicle(6,"Skoda","Superb", "used", 400000, 2020, 200, 2.0);
            controller.addVehicle(7,"Hyundai","i30", "used", 100000, 2014, 200000, 2.0);
            controller.addVehicle(8,"Opel","Astra", "used", 100000, 2014, 200000, 2.0);
            controller.addVehicle(9,"Skoda","Octavia", "used", 100000, 2014, 200000, 2.0);
            controller.addVehicle(10,"Volvo","S40", "used", 199000, 2009, 200000, 2.0);
            controller.addVehicle(11,"Ford","Fiesta", "new", 250000, 2020, 199, 2.0);
            controller.addVehicle(12,"Toyota","Auris", "new", 250000, 2020, 199, 2.0);
            controller.addVehicle(13,"Nissan","Skyline", "used", 60000, 1999, 199231, 5.0);
            controller.addVehicle(14,"Hyundai","Tucson", "used", 10000, 2005, 309231, 2.0);
            carList[0] =controller.listVehicles();
            data[0] = FXCollections.observableArrayList(carList[0]);
        }

        //Loading transactions history
        File transactionData = new File("./Transactions.csv");
        List<Transaction> transactions = new ArrayList<>();
        if (transactionData.exists() && !transactionData.isDirectory())
        {
            loadTransactions(transactions);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loading data!");
            alert.setHeaderText("Transaction data has been loaded!");
            alert.showAndWait();
            saveTransactions(transactions);
        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loading backup!");
            alert.setHeaderText("Unable to open transaction file!");
            alert.showAndWait();
            saveTransactions(transactions);
        }
        TableView<CarEntity> carsTable = new TableView();
        carsTable.setEditable(true);

        TableColumn IDColumn = new TableColumn("ID");
        IDColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, String>("carId"));
        IDColumn.setSortable(false);
        TableColumn brandColumn = new TableColumn("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, String>("carBrand"));
        brandColumn.setSortable(true);
        TableColumn modelColumn = new TableColumn("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, String>("carModel"));
        modelColumn.setSortable(true);
        TableColumn itemConditionColumn = new TableColumn("Condition");
        itemConditionColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, String>("carCondition"));
        itemConditionColumn.setSortable(false);
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, String>("carPrice"));
        priceColumn.setSortable(true);
        TableColumn productionYearColumn = new TableColumn("Year");
        productionYearColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, Integer>("carProductionYear"));
        productionYearColumn.setSortable(true);
        TableColumn mileageColumn = new TableColumn("Mileage");
        mileageColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, Integer>("carMileage"));
        mileageColumn.setSortable(false);
        TableColumn engineCapacityColumn = new TableColumn("Engine");
        engineCapacityColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, Double>("carEngineCapacity"));
        engineCapacityColumn.setSortable(false);
        /*TableColumn saloonColumn = new TableColumn("Saloon ID");
        saloonColumn.setCellValueFactory(new PropertyValueFactory<CarEntity, Integer>("saloonID"));
        saloonColumn.setSortable(false);*/
        carsTable.setItems(data[0]);
        carsTable.getColumns().addAll(IDColumn,brandColumn, modelColumn, itemConditionColumn, priceColumn, productionYearColumn,mileageColumn, engineCapacityColumn/*,saloonColumn*/);
        carsTable.setFixedCellSize(20);
        carsTable.setPrefSize(600,200);
        carsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableView<SaloonEntity> saloonsTable = new TableView();
        saloonsTable.setEditable(true);
        TableColumn idColumn = new TableColumn("Saloon ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<SaloonEntity, String>("saloonId"));
        idColumn.setSortable(true);
        TableColumn saloonNameColumn = new TableColumn("Saloon name");
        saloonNameColumn.setCellValueFactory(new PropertyValueFactory<SaloonEntity, String>("saloonName"));
        saloonNameColumn.setSortable(false);
        TableColumn locationColumn = new TableColumn("Saloon location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<SaloonEntity, String>("saloonLocation"));
        locationColumn.setSortable(false);
        saloonsTable.setItems(saloonData[0]);
        saloonsTable.getColumns().addAll(idColumn,saloonNameColumn,locationColumn);
        saloonsTable.setFixedCellSize(20);
        saloonsTable.setPrefSize(600,100);
        saloonsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //List<CarEntity> finalCarList = carList[0];
        //List<CarEntity> finalCarList2 = carList[0];
        javafx.event.EventHandler<ActionEvent> pressed = new javafx.event.EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                CarEntity selectedItem = carsTable.getSelectionModel().getSelectedItem();
                transactions.add(new Transaction(selectedItem.getCarId(), Calendar.getInstance().getTime().toString(),selectedItem.getCarId().toString()));
                controller.deleteVehicle(selectedItem.getCarId());
                carList[0] =controller.listVehicles();
                data[0] = FXCollections.observableArrayList(carList[0]);
                carsTable.setItems(data[0]);
            }

        };
        
        Button buyButton = new Button("Buy!");
        buyButton.setLayoutX(780);
        buyButton.setLayoutY(50);
        buyButton.setOnAction(pressed);
        buyButton.setMinWidth(150);
        
        Label searchLabel = new Label("Search brands");
        TextField textField = new TextField();
        searchLabel.setLayoutX(780);
        searchLabel.setLayoutY(80);
        textField.setLayoutX(780);
        textField.setLayoutY(100);
        Button searchButton = new Button("Search");
        searchButton.setLayoutX(780);
        searchButton.setLayoutY(130);
        searchButton.setMinWidth(150);
        List<CarEntity> finalCarList1 = carList[0];
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent e)
            {
                if (textField.getText() != null && textField.getText() != "")
                {
                    String searched = textField.getText();
                    //System.out.print(searched);
                    carList[0] =controller.searchBrand(searched);
                    data[0] = FXCollections.observableArrayList(carList[0]);
                    carsTable.setItems(data[0]);
                }
                else
                {
                    data[0] = FXCollections.observableArrayList(finalCarList1);
                    carsTable.setItems(data[0]);
                }
            }
        });
        Label searchBySaloonID = new Label("Search by saloon ID");
        searchBySaloonID.setLayoutX(780);
        searchBySaloonID.setLayoutY(220);
        TextField textField2 = new TextField();
        textField2.setLayoutX(780);
        textField2.setLayoutY(240);
        Button searchButton2 = new Button("search by S ID");
        searchButton2.setLayoutX(780);
        searchButton2.setLayoutY(270);
        searchButton2.setMinWidth(150);
        searchButton2.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent e)
            {
                if (textField2.getText() != null && textField2.getText() != "")
                {
                    System.out.print("HANDLER\n");
                    String searched = textField2.getText();
                    //Integer integer = Integer.parseInt(searched);
                    //System.out.print(searched);
                    carList[0] =controller.searchBySaloonID(searched);
                    data[0] = FXCollections.observableArrayList(carList[0]);
                    carsTable.setItems(data[0]);
                    carsTable.refresh();
                }
                else
                {
                    System.out.print("HANDLER2\n");
                    data[0] = FXCollections.observableArrayList(finalCarList1);
                    carsTable.setItems(data[0]);
                }
            }
        });

        //transactions history
        Button transactionButton = new Button("Transactions");
        transactionButton.setLayoutX(780);
        transactionButton.setLayoutY(170);
        transactionButton.setMinWidth(150);
        Stage transactionStage = new Stage();
        transactionStage.setTitle("Transaction History");
        transactionStage.setWidth(400);

        Scene transactionWindow = new Scene(new Group());
        BorderPane transactionPane = new BorderPane();
        ListView<String> transactionList = new ListView<>();
        ((Group) transactionWindow.getRoot()).getChildren().add(transactionPane);
        transactionButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                transactionList.getItems().clear();
                for (Transaction temptransaction : transactions)
                {
                    String tempString = ("");
                    tempString += Integer.toString(temptransaction.vehicleID) + "," + temptransaction.name+","+temptransaction.brand;
                    transactionList.getItems().add(tempString);
                }
                transactionPane.setCenter(transactionList);
                transactionStage.setScene(transactionWindow);
                transactionStage.show();
            }
        });

        saveTransactions(transactions);
        transactionStage.close();
        /*
        //Zapis przed zamknięciem
        Stage closeStage = new Stage();
        closeStage.setTitle("Save data");
        Scene closeWindow = new Scene(new Group());
        HBox closeButtons = new HBox();
        Button saveButton = new Button("YES");
        Button dontSaveButton = new Button("NO");
        Text doYouWantToSave = new Text("Do tou want to keep changes?");
        primaryStage.setOnCloseRequest(event ->
        {
            //display of the window
            closeButtons.getChildren().addAll(doYouWantToSave, saveButton, dontSaveButton);
            ((Group) closeWindow.getRoot()).getChildren().add(closeButtons);
            closeStage.setScene(closeWindow);
            closeStage.show();
        });

        List<CarEntity> finalCarList4 = controller.listVehicles(); 
        EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                saveData(finalCarList4);
                saveTransactions(transactions);
                closeStage.close();
                transactionStage.close();
            }
        };
        saveButton.setOnAction(saveAction);
        EventHandler<ActionEvent> dontSaveAction = new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                closeStage.close();
                transactionStage.close();
            }
        };
        dontSaveButton.setOnAction(dontSaveAction);
        */





        //Display of the UI elements
        ((Group) scene.getRoot()).getChildren().add(transactionButton);
        ((Group) scene.getRoot()).getChildren().add(buyButton);
        ((Group) scene.getRoot()).getChildren().add(searchButton);
        ((Group) scene.getRoot()).getChildren().add(searchLabel);
        ((Group) scene.getRoot()).getChildren().add(textField);
        ((Group) scene.getRoot()).getChildren().add(textField2);
        ((Group) scene.getRoot()).getChildren().add(searchBySaloonID);
        ((Group) scene.getRoot()).getChildren().add(searchButton2);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, carsTable,saloonsTable);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
