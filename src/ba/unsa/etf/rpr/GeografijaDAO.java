package ba.unsa.etf.rpr;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GeografijaDAO {
    private static GeografijaDAO instance;
    private static Connection connection;

    private GeografijaDAO() {
        boolean init = !databaseExists();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            if(init) {
                String createGradTable = "create table grad (id integer primary key, naziv text, broj_stanovnika integer);";
                String createDrzavaTable = "create table drzava (id integer primary key, naziv text, glavni_grad integer references grad(id));";
                String alterGradTable = "alter table grad add drzava integer references drzava(id);";
                try {
                    Statement statement = connection.createStatement();
                    statement.execute(createGradTable);
                    statement.execute(createDrzavaTable);
                    statement.execute(alterGradTable);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                Grad paris = new Grad(-1, "Pariz", 2206488, null);
                Grad london = new Grad(-1, "London", 8825000, null);
                Grad bec = new Grad(-1, "Beč", 1899055, null);
                Grad manch = new Grad(-1, "Manchester", 545500, null);
                Grad graz = new Grad(-1, "Graz", 280200, null);

                Drzava fran = new Drzava(-1, "Francuska", paris);
                Drzava vBrit = new Drzava(-1, "Velika Britanija", london);
                Drzava aut = new Drzava(-1, "Austrija", bec);

                paris.setDrzava(fran);
                london.setDrzava(vBrit);
                bec.setDrzava(aut);
                manch.setDrzava(vBrit);
                graz.setDrzava(aut);

                dodajGrad(paris);
                dodajGrad(london);
                dodajGrad(bec);
                dodajGrad(manch);
                dodajGrad(graz);
                dodajDrzavu(fran);
                dodajDrzavu(vBrit);
                dodajDrzavu(aut);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private boolean databaseExists() {
        File db = new File("baza.db");
//        System.out.println(db.getAbsolutePath());
        return db.exists();
    }

    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public static void removeInstance() {
        instance = null;
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static GeografijaDAO getInstance() {
        if(instance == null)
            initialize();
        return instance;
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        ArrayList<Drzava> drzave = new ArrayList<>();

        String query = "select g.id g_id, g.naziv g_naziv, g.broj_stanovnika g_brstan," +
                "d.id d_id, d.naziv d_naziv, d.glavni_grad d_ggrad " +
                " from grad g, drzava d" +
                " where g.drzava = d.id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int gId = resultSet.getInt("g_id");
                String gNaziv = resultSet.getString("g_naziv");
                int gBrojStan = resultSet.getInt("g_brstan");
                int dId = resultSet.getInt("d_id");
                String dNaziv = resultSet.getString("d_naziv");
                int dgGrad = resultSet.getInt("d_ggrad");

                Grad noviGrad = new Grad(gId, gNaziv, gBrojStan, null);
                Drzava novaDrzava = containsDrzava(drzave, dId);
                if(novaDrzava == null) {
                    novaDrzava = new Drzava(dId, dNaziv, null);
                    drzave.add(novaDrzava);
                }
                noviGrad.setDrzava(novaDrzava);
                if(novaDrzava.getGlavniGrad() == null) {
                    if(gId == dgGrad) {
                        novaDrzava.setGlavniGrad(noviGrad);
                    }
                }
                gradovi.add(noviGrad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(gradovi);

        return gradovi;
    }

    private Drzava containsDrzava(ArrayList<Drzava> drzave, int id) {
        for(Drzava d : drzave) {
            if(d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public Grad glavniGrad(String drzava) {
        ArrayList<Grad> grd = gradovi();

        if(grd == null)
            return null;

        for(Grad g : grd) {
            if(g.getDrzava().getNaziv().equals(drzava)) {
                return g;
            }
        }
        return null;
    }

    public Drzava nadjiDrzavu(String drzava) {
        String query = "select d.id, d.naziv, d.glavni_grad from drzava d where d.naziv=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drzava);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isClosed())
                return null;

            int id = resultSet.getInt(1);
            int gGrad = resultSet.getInt(2);
            return new Drzava(id, drzava, glavniGrad(drzava));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void dodajGrad(Grad grad) {
        String query = "insert into grad values(?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, grad.getId());
            preparedStatement.setString(2, grad.getNaziv());
            preparedStatement.setInt(3, grad.getBrojStanovnika());
            preparedStatement.setInt(4,grad.getDrzava().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dodajDrzavu(Drzava drzava) {
        String query = "insert into drzava values (?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, drzava.getId());
            preparedStatement.setString(2, drzava.getNaziv());
            preparedStatement.setInt(3, drzava.getGlavniGrad().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmijeniGrad(Grad grad) {
        String query = "update grad set naziv=?, broj_stanovnika=?, drzava=? where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, grad.getNaziv());
            preparedStatement.setInt(2, grad.getBrojStanovnika());
            preparedStatement.setInt(3, grad.getDrzava().getId());
            preparedStatement.setInt(4, grad.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void obrisiDrzavu(String drzava) {
        String query = "delete from drzava where naziv=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, drzava);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}