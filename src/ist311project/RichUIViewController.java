/**
 * Sample Skeleton for 'RichUIView.fxml' Controller Class
 */

package ist311project;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

public class RichUIViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pieChart"
    private PieChart pieChart; // Value injected by FXMLLoader

    @FXML // fx:id="buttonPie"
    private Button buttonPie; // Value injected by FXMLLoader
    
    @FXML // fx:id="tabPicture"
    private Tab tabPicture; // Value injected by FXMLLoader

    @FXML // fx:id="tabGraph"
    private Tab tabGraph; // Value injected by FXMLLoader

     @FXML // fx:id="tabSearch"
    private Tab tabSearch; // Value injected by FXMLLoader

    @FXML // fx:id="tabReport"
    private Tab tabReport; // Value injected by FXMLLoader
    
     @FXML // fx:id="textReport"
    private Text textReport; // Value injected by FXMLLoader
     
     
    @FXML
    void tabGraphChanged(Event event) {
        System.out.println("tab");
         // load data 
        List<SampleModel> data  = manager.createNamedQuery("SampleModel.findAll").getResultList();
        double percentage = 1.0/data.size();
        
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        
        for(SampleModel m : data) {
            pieData.add(new PieChart.Data(m.getValue(), percentage));
        }
        
        pieChart.setData(pieData);

    }

    @FXML
    void tabPictureChanged(Event event) {

    }

    @FXML
    void tabReportChanged(Event event) {
        
        textReport.setText("Any thing you want");

    }

    ArrayList<String> l = new ArrayList<>();
    @FXML
    void tabSearchChanged(Event event) {
        System.out.println("search tab");
                 
    }
    

    // pie chart: https://docs.oracle.com/javafx/2/charts/pie-chart.htm
    @FXML
    void buttonPieAction(ActionEvent event) {
        // load data 
        List<SampleModel> data  = manager.createNamedQuery("SampleModel.findAll").getResultList();
        double percentage = 1.0/data.size();
        
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        
        for(SampleModel m : data) {
            pieData.add(new PieChart.Data(m.getValue(), percentage));
        }
        
        pieChart.setData(pieData);

    }
    
    
    EntityManager manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectPU").createEntityManager();
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tabPicture != null : "fx:id=\"tabPicture\" was not injected: check your FXML file 'RichUIView.fxml'.";
        assert tabGraph != null : "fx:id=\"tabGraph\" was not injected: check your FXML file 'RichUIView.fxml'.";
        assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'RichUIView.fxml'.";
        assert buttonPie != null : "fx:id=\"buttonPie\" was not injected: check your FXML file 'RichUIView.fxml'.";
        assert tabSearch != null : "fx:id=\"tabSearch\" was not injected: check your FXML file 'RichUIView.fxml'.";       
        assert tabReport != null : "fx:id=\"tabReport\" was not injected: check your FXML file 'RichUIView.fxml'.";
        assert textReport != null : "fx:id=\"textReport\" was not injected: check your FXML file 'RichUIView.fxml'.";
        
        
    }
}
