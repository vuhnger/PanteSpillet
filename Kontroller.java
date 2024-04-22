
public class Kontroller{

    final int ANTALL_RADER, ANTALL_KOLONNER;
    final PanteSpilletGUI view;
    final Modell modell;

    public Kontroller(int antallRader, int antallKolonner){
        this.ANTALL_RADER = antallRader;
        this.ANTALL_KOLONNER = antallKolonner;
        modell = new Modell(
            new Slange(new Rute(0,0)), 
            new Brett(ANTALL_RADER, ANTALL_KOLONNER),
            this
            );
        view = new PanteSpilletGUI(this);
    }

    Rute hentRute(int rad, int kolonne){
        return modell.hentRute(rad, kolonne);
    }

    void oppdater(){
        modell.oppdater();
    }

    void avslutt(){
        modell.spillFerdig = true;
    }

    void settHode(int rad, int kolonne){
        modell.settHode(rad, kolonne);
    }

    int hentRetning(){
        return modell.retning;
    }

    void settRetning(int retning){
        modell.retning = retning;
    }

    boolean kjorer(){
        return !modell.spillFerdig;
    }

    void start(){
        modell.spillFerdig = false;
    }

}