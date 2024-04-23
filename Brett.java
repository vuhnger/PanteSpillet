public class Brett {
    
    final int ANTALL_RADER, ANTALL_KOLONNER;
    public Rute[][] ruter;

    public Brett(
        int antallRader,
        int antallKolonner
    ){
        ANTALL_RADER = antallRader;
        ANTALL_KOLONNER = antallKolonner;
    
        ruter = new Rute[ANTALL_RADER][ANTALL_KOLONNER];
        for (int rad = 0; rad < ANTALL_RADER; rad++){
            for (int kolonne = 0; kolonne < ANTALL_KOLONNER; kolonne++){
                ruter[rad][kolonne] = new Rute(rad, kolonne);
            }
        }
        for (int i = 0; i < 3; i++){
            opprettMat();
        }
    }

    public void opprettMat(){
        int rad = 0, kolonne = 0;
        while(true){
            rad = (int) (Math.random() * ANTALL_RADER);
            kolonne = (int) (Math.random() * ANTALL_KOLONNER);
            if (ruter[rad][kolonne].ruteType != RuteType.KROPP){break;}
        }
        System.out.println("Mat laget på [" + rad + "," + kolonne + "]");
        ruter[rad][kolonne].ruteType = RuteType.PANT;
    }

}
