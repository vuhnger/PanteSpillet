public class Modell {

    public Slange slange;
    public Brett brett;
    public int retning;
    public boolean spillFerdig;
    public int antallPant;

    final Kontroller kontroller;

    public Modell(
        Slange slange,
        Brett brett,
        Kontroller kontroller
    ){
        this.slange = slange;
        this.brett = brett;
        this.kontroller = kontroller;
        antallPant = 0;
    }

    void oppdater(){
       if (spillFerdig){
            System.exit(1);
       }

       if (retning == Retning.RETNING_INGEN){
        return;
       }

       Rute neste = hentNesteRute(slange.hode);

       if (slange.kollisjon(neste)){
        retning = Retning.RETNING_INGEN;
        spillFerdig = true;
        return;
       }

       slange.flytt(neste);
       if (neste.ruteType == RuteType.PANT){
        slange.voks();
        antallPant++;
        brett.opprettMat();
       }
       kontroller.oppdater();
    }

    Rute hentNesteRute(Rute posisjon){

        int rad = posisjon.rad, kolonne = posisjon.kolonne;

        if (retning == Retning.RETNING_HOYRE){kolonne++;}
        if (retning == Retning.RETNING_VENSTRE){kolonne--;}
        if (retning == Retning.RETNING_OPP){rad++;}
        if (retning == Retning.RETNING_NED){rad--;}
        return brett.ruter[rad][kolonne];
    }   

    Rute hentRute(int rad, int kolonne){
        return brett.ruter[rad][kolonne];
    }

    void settHode(int rad, int kolonne){
        brett.ruter[rad][kolonne] = slange.hode;
    }

}
