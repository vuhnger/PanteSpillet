
public class Kontroller{

    final int ANTALL_RADER, ANTALL_KOLONNER;
    final PanteSpilletGUI view;
    final Modell modell;
    final int SPILL_DELAY_TID = 100;

    public Kontroller(int antallRader, int antallKolonner){
        this.ANTALL_RADER = antallRader;
        this.ANTALL_KOLONNER = antallKolonner;
        modell = new Modell(
            new Slange(new Rute(
                ANTALL_RADER / 2,
                ANTALL_KOLONNER / 2)
                ), 
            new Brett(ANTALL_RADER, ANTALL_KOLONNER),
            this
            );
        view = new PanteSpilletGUI(this);
        new Thread(
            new Kjorer()
        ).start();
    }

    class Kjorer implements Runnable{
        @Override
        public void run(){
        // Kjor lokken som driver spillet
        while(!modell.spillFerdig){
            try {
                Thread.sleep(SPILL_DELAY_TID);
            } catch (InterruptedException e) {
                return;
            }
            oppdater();
        }
        view.visTaperMelding();
        }
    }

    Rute hentRute(int rad, int kolonne){
        return modell.hentRute(rad, kolonne);
    }

    String hentAntallPant(){
        return Integer.toString(modell.antallPant); 
    }

    void oppdater(){
        modell.oppdater();
        view.oppdater();
    }

    int hentRetning(){
        return modell.retning;
    }

    void settRetning(int retning){
        modell.retning = retning;
    }
    
    public static void main(String[] args) {

        if (args.length < 2){
            new Kontroller(10, 20);
        }
        
    }

}