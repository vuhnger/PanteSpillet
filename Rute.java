public class Rute {
    
    public final int rad, kolonne;
    public RuteType ruteType;

    public Rute(int rad, int kolonne){
        this.rad = rad;
        this.kolonne = kolonne;
        ruteType = RuteType.TOM;
    }

    public void settRuteType(RuteType type){
        ruteType = type;
    }

}
