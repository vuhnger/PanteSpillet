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
        spillFerdig = false;
        
    }

    String hentNivaa(){
        if (antallPant > 4 && antallPant < 10) return "PanteSamler";
        if (antallPant > 9 && antallPant < 15) return "Pantemannen";
        if (antallPant > 14 && antallPant < 20) return "KIRURGEN!";
        if (antallPant > 19 && antallPant < 25) return "PANT OVERLORD!!";
        if (antallPant > 24 && antallPant < 30) return "RICH GUY DONT PANT!!!";
        if (antallPant > 29 && antallPant < 35) return "PANTEMONARK!!!!";;
        return "IFI-Student"; 
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

       System.out.println(
        "Denne : " +  slange.hode.rad + " " + slange.hode.kolonne + 
        "\nNeste: " + neste.ruteType + " " + neste.rad + " " + neste.kolonne
        );

        
        if (neste.ruteType == RuteType.PANT){
            System.out.println("Generer pant.");
            slange.voks();
            antallPant++;
            brett.opprettMat();
        }

        slange.flytt(neste);
    }

    Rute hentNesteRute(Rute posisjon){

        int rad = posisjon.rad, kolonne = posisjon.kolonne;

        if (retning == Retning.RETNING_HOYRE){kolonne++;}
        if (retning == Retning.RETNING_VENSTRE){kolonne--;}
        if (retning == Retning.RETNING_OPP){rad--;}
        if (retning == Retning.RETNING_NED){rad++;}

        // Slangen kjorer inn på brettet fra andre siden
        rad = (rad + brett.ANTALL_RADER) % brett.ANTALL_RADER;
        kolonne = (kolonne + brett.ANTALL_KOLONNER) % brett.ANTALL_KOLONNER;

        return brett.ruter[rad][kolonne];
    }   

    Rute hentRute(int rad, int kolonne){
        return brett.ruter[rad][kolonne];
    }

    void settHode(int rad, int kolonne){
        brett.ruter[rad][kolonne] = slange.hode;
    }

}
