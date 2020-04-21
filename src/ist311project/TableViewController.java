/**
 * Sample Skeleton for 'TableView.fxml' Controller Class
 */
package ist311project;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class TableViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="modelTable"
    private TableView<SampleModel> modelTable; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnID"
    private TableColumn<SampleModel, String> modelColumnID; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumnValue"
    private TableColumn<SampleModel, String> modelColumnValue; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="showDetailButton"
    private Button showDetailButton; // Value injected by FXMLLoader

    @FXML // fx:id="textfieldID"
    private TextField textfieldID; // Value injected by FXMLLoader

    @FXML // fx:id="textfieldValue"
    private TextField textfieldValue; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="checkboxEditCell"
    private CheckBox checkboxEditCell; // Value injected by FXMLLoader

    @FXML
    private CheckBox checkboxNewWinddow;
    
    @FXML // fx:id="buttonRich"
    private Button buttonRich; // Value injected by FXMLLoader


    
    @FXML
    void buttonRichAction(ActionEvent event) throws IOException {
        // fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RichUIView.fxml"));

        // load the ui elements
        Parent richView = loader.load();
        // load the scene
        Scene tableViewScene = new Scene(richView);

        //access the controller and call a method
        RichUIViewController controller = loader.getController();

        // create a new state
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();

    }
    
    @FXML
    void checkboxNewWindowAction(ActionEvent event) {

    }

    @FXML
    void valueColumnChanged(TableColumn.CellEditEvent event) {
        System.out.println("changed");

        SampleModel model = modelTable.getSelectionModel().getSelectedItem();
        model.setValue(event.getNewValue().toString());

        // update in database
        update(model);
    }

    @FXML
    void createButtonAction(ActionEvent event) {
        int id = Integer.parseInt(textfieldID.getText());
        String value = textfieldValue.getText();

        SampleModel model = new SampleModel();
        model.setId(id);
        model.setValue(value);

        // add model to tableview
        modelTable.getItems().add(model);

        // add model to database
        create(model);

    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        SampleModel model = modelTable.getSelectionModel().getSelectedItem();

        // delete confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            
            // delete from tableview
            modelTable.getItems().remove(model);

            // delete from database
            delete(model);
        }

    }

    @FXML
    void editCellAction(ActionEvent event) {
        isEditable = checkboxEditCell.isSelected();
        modelTable.setEditable(isEditable);
    }

    @FXML
    void showDetailButtonAction(ActionEvent event) throws IOException {
        // fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailModelView.fxml"));

        // load the ui elements
        Parent detailedModelView = loader.load();
        // load the scene
        Scene tableViewScene = new Scene(detailedModelView);

        //access the controller and call a method
        DetailModelController controller = loader.getController();

        // pass currently selected model
        controller.initData(modelTable.getSelectionModel().getSelectedItem());

        if (checkboxNewWinddow.isSelected()) {
            // create a new state
            Stage stage = new Stage();
            stage.setScene(tableViewScene);
            stage.show();

        } else {

            // pass current scene to return
            controller.setPreviousScene(((Node) event.getSource()).getScene());

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }

    }

    // Database operations
    EntityManager manager;

    //----- Database CRUD Operations------
    public void create(SampleModel model) {
        try {
            manager.getTransaction().begin();
            if (model.getId() != null) {
                // create model
                manager.persist(model);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update(SampleModel model) {
        try {

            SampleModel dbModel = manager.find(SampleModel.class, model.getId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                // update
                dbModel.setValue(model.getValue());
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void delete(SampleModel model) {
        try {
            SampleModel dbModel = manager.find(SampleModel.class, model.getId());

            if (dbModel != null) {
                manager.getTransaction().begin();
                //remove model
                manager.remove(dbModel);
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void loadData() {
        Query query = manager.createNamedQuery("SampleModel.findAll");
        List<SampleModel> data = query.getResultList();

        ObservableList<SampleModel> odata = FXCollections.observableArrayList();

        for (SampleModel d : data) {
            //...   
            System.out.println(d.getId());
            odata.add(d);
        }

        modelTable.setItems(odata);
    }

    private boolean isEditable = false;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert checkboxEditCell != null : "fx:id=\"checkboxEditCell\" was not injected: check your FXML file 'TableView.fxml'.";
        assert modelTable != null : "fx:id=\"modelTable\" was not injected: check your FXML file 'TableView.fxml'.";
        assert modelColumnID != null : "fx:id=\"modelColumnID\" was not injected: check your FXML file 'TableView.fxml'.";
        assert modelColumnValue != null : "fx:id=\"modelColumnValue\" was not injected: check your FXML file 'TableView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'TableView.fxml'.";
        assert showDetailButton != null : "fx:id=\"showDetailButton\" was not injected: check your FXML file 'TableView.fxml'.";
        assert textfieldID != null : "fx:id=\"textfieldID\" was not injected: check your FXML file 'TableView.fxml'.";
        assert textfieldValue != null : "fx:id=\"textfieldValue\" was not injected: check your FXML file 'TableView.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'TableView.fxml'.";

        //set up the columns in the table
        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("Id")); //should match with attribute Id (e.g., getId/setId methods) in SimpleModel
        modelColumnValue.setCellValueFactory(new PropertyValueFactory<>("Value")); //should match with attribute Value (e.g., getValue/setValue methods) in SimpleModel

        //eanble row selection
        //modelTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        modelTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // make cell editable
        //Update the table to allow for the Value field  to be editable
        modelTable.setEditable(isEditable);
        modelColumnValue.setCellFactory(TextFieldTableCell.forTableColumn());

        // loading data from database
        //database reference: "IST311ProjectPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectPU").createEntityManager();

        //loading data
        loadData();

    }
}
