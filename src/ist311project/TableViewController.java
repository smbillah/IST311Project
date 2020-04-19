/**
 * Sample Skeleton for 'TableView.fxml' Controller Class
 */
package ist311project;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    EntityManager manager;

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert modelTable != null : "fx:id=\"modelTable\" was not injected: check your FXML file 'TableView.fxml'.";
        assert modelColumnID != null : "fx:id=\"modelColumnID\" was not injected: check your FXML file 'TableView.fxml'.";
        assert modelColumnValue != null : "fx:id=\"modelColumnValue\" was not injected: check your FXML file 'TableView.fxml'.";

        //set up the columns in the table
        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("Id")); //should match with attribute Id (e.g., getId/setId methods) in SimpleModel
        modelColumnValue.setCellValueFactory(new PropertyValueFactory<>("Value")); //should match with attribute Value (e.g., getValue/setValue methods) in SimpleModel

        // loading data from database
        //database reference: "IST311ProjectPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectPU").createEntityManager();

        //loading data
        loadData();

    }
}
