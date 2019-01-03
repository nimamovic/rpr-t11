package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.util.ResourceBundle;

public class DrzavaController implements Initializable {
    public ComboBox<String> drzavaCombo;
    private ObservableList<String> listaDrzava = FXCollections.observableArrayList();

    public DrzavaController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            listaDrzava.add("Francuska");
            listaDrzava.add("Velika Britanija");
            listaDrzava.add("Austrija");
            
            drzavaCombo.setItems(listaDrzava);
    }

    public void kreirajIzvjestaj(ActionEvent actionEvent) {
        GradoviReport gradoviReport = new GradoviReport();
        try {
            gradoviReport.showReportDrzava(GeografijaDAO.getConnection(), drzavaCombo.getValue());
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}