package ba.unsa.etf.rpr;

public class Grad implements Comparable<Grad>{
    private static int count = 0;
    private int id;
    private String naziv;
    private int brojStanovnika;
    private Drzava drzava;

    public Grad() {
        id = count;
        count++;
    }

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        if(id == -1) {
            this.id = count;
            count++;
        }
        else
            this.id = id;

        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return naziv + " (" + ( (drzava != null)?drzava.getNaziv() : "nema drzave") + ") - " + brojStanovnika;
    }

    @Override
    public int compareTo(Grad o) {
        return o.brojStanovnika - brojStanovnika;
    }
}