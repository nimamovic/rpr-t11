package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;

public class ProzorController {
    public TextField gradZaDodavanje;
    public TextField drzavaZaBrisanje;
    public Button dugmeBrisiDrzavu;
    public Label opcija;

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
            opcija.setText("Dzrazava i njeni gradovi su izbrisani iz baze");
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
}