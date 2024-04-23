import java.util.LinkedList;

public class Slange {
    
    public LinkedList<Rute> slangeDeler = new LinkedList<>();
    public Rute hode;

    public Slange(Rute startPosisjon){
        hode = startPosisjon;
        slangeDeler.add(hode);
        hode.ruteType = RuteType.KROPP;
    }

    public void voks(){
        slangeDeler.add(hode);
    }

    public void flytt(Rute neste){
        Rute hale = slangeDeler.removeLast();
        hale.ruteType = RuteType.TOM;

        hode = neste;
        hode.ruteType = RuteType.KROPP;
        slangeDeler.addFirst(hode);
    }

    public boolean kollisjon(Rute neste){
        for (Rute r : slangeDeler){
            if (r == neste){
                return true;
            }
        }
        return false;
    }

}
