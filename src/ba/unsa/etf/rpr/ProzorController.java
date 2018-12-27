package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ProzorController {
    public TextField gradZaDodavanje;
    public TextField drzavaZaBrisanje;
    public Button dugmeBrisiDrzavu;
    public Label opcija;


    public BorderPane mainPane;


    public void ObrisiDrzavuINjeneGradove(ActionEvent actionEvent) {
        GeografijaDAO gdo = GeografijaDAO.getInstance();

        if(drzavaZaBrisanje.getText().isEmpty() )
        {
            Alert upozori = new Alert(Alert.AlertType.WARNING);
            upozori.setTitle("UPOZORENJE");
            upozori.setContentText("Niste unijeli drzavu u namjenjeno polje!");
            upozori.showAndWait();
        }
        else if(true)
        { //kako da brisem drzavu haha


        }
        else
        {
            opcija.setText("Drzava i njeni gradovi su izbrisani iz baze");
        }

    }

    public void NadjiGlavniGrad(ActionEvent actionEvent) {
        GeografijaDAO gdo = GeografijaDAO.getInstance();

        Grad gg = gdo.glavniGrad( gradZaDodavanje.getText() );

        if( gradZaDodavanje.getText().isEmpty())
        {
            Alert upozori = new Alert(Alert.AlertType.WARNING);
            upozori.setTitle("UPOZORENJE");
            upozori.setContentText("Niste unijeli nista u namjeneno polje!");
            upozori.showAndWait();
        }
        else if(gg == null)
        {
            Alert upozori = new Alert(Alert.AlertType.WARNING);
            upozori.setTitle("UPOZORENJE");
            upozori.setContentText("Unijeti grad ne postoji u bazi!");
            upozori.showAndWait();
        }
        else
        {
            opcija.setText("Glavni grad "+ gradZaDodavanje.getText()+" je "+gg.getNaziv()+"!");
        }
    }
    private void selectLanguage(Locale locale) {
        Stage primaryStage = (Stage)mainPane.getScene().getWindow();
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prozor.fxml"), bundle);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);
    }

    public void setBa(ActionEvent actionEvent) {
        selectLanguage(new Locale("bs","BA"));
    }

    public void setEn(ActionEvent actionEvent) {
        selectLanguage(new Locale("en","US"));
    }

    public void setDe(ActionEvent actionEvent) {
        selectLanguage(new Locale("de","DE"));
    }

    public void setFr(ActionEvent actionEvent) {
        selectLanguage(new Locale("fr","FR"));
    }
}