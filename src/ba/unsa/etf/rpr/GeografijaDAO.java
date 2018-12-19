package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private Connection conn;
    private PreparedStatement upit;
    private ArrayList<Grad> gradovi;
    private ArrayList<Drzava> drzave;

    private void napuniPodacima() {
        Grad pariz = new Grad(1, "Pariz", 2229621 , null);
        Grad london = new Grad(2, "London",  	7355400 , null);
        Grad bec = new Grad(3, "Beč", 1867582, null);
        Grad manchester = new Grad(4, "Manchester",  	441200, null);
        Grad graz = new Grad(5, "Graz",  	286686, null);
        Drzava francuska = new Drzava(1, "Francuska", pariz);
        Drzava engleska = new Drzava(2, "Engleska", london);
        Drzava austrija = new Drzava(3, "Austrija", bec);
        pariz.setDrzava(francuska);
        london.setDrzava(engleska);
        bec.setDrzava(austrija);
        manchester.setDrzava(engleska);
        graz.setDrzava(austrija);
        gradovi.add(pariz);
        gradovi.add(london);
        gradovi.add(bec);
        gradovi.add(manchester);
        gradovi.add(graz);
        drzave.add(francuska);
        drzave.add(engleska);
        drzave.add(austrija);
    }

    private GeografijaDAO() {
        gradovi = new ArrayList<>();
        drzave = new ArrayList<>();
        napuniPodacima();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + "/.IdeaProjects/baza.db");//koji url uzeti?????
            upit = conn.prepareStatement("INSERT INTO grad VALUES (?, ?, ?, NULL)");
            for (var grad : gradovi) {
                try {
                    upit.setInt(1, grad.getId());
                    upit.setString(2, grad.getNaziv());
                    upit.setInt(3, grad.getBrojStanovnika());
                    upit.executeUpdate();
                } catch (SQLException ignored) {
                }
            }
            upit = conn.prepareStatement("INSERT  INTO drzava VALUES(?, ?, ?)");
            for (var drzava : drzave) {
                try {
                    upit.setInt(1, drzava.getId());
                    upit.setString(2, drzava.getNaziv());
                    upit.setInt(3, drzava.getGlavniGrad().getId());
                    upit.executeUpdate();
                } catch (SQLException ignored) {
                }
            }
            upit = conn.prepareStatement("UPDATE grad SET drzava = ? WHERE id = ?");
            for (var grad : gradovi) {
                try {
                    upit.setInt(1, grad.getDrzava().getId());
                    upit.setInt(2, grad.getId());
                    upit.executeUpdate();
                } catch (SQLException ignored) {
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeInstance() {
        instance = null;
    }


    public static GeografijaDAO getInstance() {
        if(instance == null) instance = new GeografijaDAO();
        return null;
    }

    public ArrayList<Grad> gradovi() {
        return null;
    }

    public Grad glavniGrad(String bosna_i_hercegovina) {
        return  null;
    }

    public void obrisiDrzavu(String d) {
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(d)) {
                drzave.remove(i);
                break;
            }
        }
    }

    public Drzava nadjiDrzavu(String d) {
        for (int i = 0; i < drzave.size(); i++) {
            if (drzave.get(i).getNaziv().equals(d))
                return drzave.get(i);
        }
        return null;
    }

    public void dodajGrad(Grad grad) {
        if (gradovi.contains(grad)) throw new IllegalArgumentException("Grad vec postoji");
        gradovi.add(grad);
    }

    public void dodajDrzavu(Drzava d) {
        if (drzave.contains(d)) throw new IllegalArgumentException("Drzava vec postoji");
        drzave.add(d);
    }

    public void izmijeniGrad(Grad grad) {
        for (int i = 0; i < gradovi.size(); i++) {
            if (gradovi.get(i).equals(grad)) {
                gradovi.get(i).setNaziv(grad.getNaziv());
                gradovi.get(i).setBrojStanovnika(grad.getBrojStanovnika());
                gradovi.get(i).setDrzava(grad.getDrzava());
                gradovi.get(i).setId(grad.getId());
                break;
            }
        }
        throw new IllegalArgumentException("Grad ne postoji");
    }
}
