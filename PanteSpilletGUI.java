import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class PanteSpilletGUI extends JFrame{
    
    final int RUTE_DIMENSJON = 40;

    Kontroller kontroller;
    SpillRute[][] ruter;
    SpillPanel rutenett;
    JLabel antallPant;
    private static final int spillDelay = 2000;

    ImageIcon panteIkon;

    PanteSpilletGUI(
        Kontroller kontroller
    ){
        //super();
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.exit(1);
        }

        this.kontroller = kontroller;
        antallPant = new JLabel(
            kontroller.hentAntallPant()
        );
        rutenett = new SpillPanel();
        rutenett.setLayout(
            new GridLayout(
                kontroller.ANTALL_RADER,
                kontroller.ANTALL_KOLONNER 
                )
            );
        
        // Skalere panteikonet
        panteIkon = new ImageIcon("panteIkon.jpeg");
        Image skalertPanteIkon = panteIkon.getImage().getScaledInstance(RUTE_DIMENSJON, RUTE_DIMENSJON, Image.SCALE_SMOOTH);
        panteIkon = new ImageIcon(skalertPanteIkon);

        ruter = new SpillRute[kontroller.ANTALL_RADER][kontroller.ANTALL_KOLONNER];

        assert kontroller.ANTALL_RADER > 0 && kontroller.ANTALL_KOLONNER > 0;

        for (int rad = 0; rad < kontroller.ANTALL_RADER; rad++){
            for (int kolonne = 0; kolonne < kontroller.ANTALL_KOLONNER; kolonne++){
                SpillRute rute = new SpillRute(rad, kolonne);
                ruter[rad][kolonne] = rute;
                rutenett.add(rute);
            }
        }

        // Legge til komponenter på tegneflaten
        add(
            antallPant,
            BorderLayout.NORTH
        );

        add(
            rutenett,
            BorderLayout.SOUTH
            );

        // Pakke og sette egenskaper til vinduet
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setName("PanteSpillet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void oppdater(){
        tegnAlleRuter();  
    }

    void tegnRute(SpillRute rute){
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.KROPP){
            rute.setBackground(SpillFarger.KROPP);
            rute.setIcon(null);
        }
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.TOM){
            rute.setBackground(SpillFarger.TOM);
            rute.setIcon(null);
        }
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.PANT){
            rute.setBackground(SpillFarger.PANT);
            rute.setIcon(panteIkon);
        }
    }

    void tegnAlleRuter(){
        for (int rad = 0; rad < kontroller.ANTALL_RADER; rad++){
            for (int kolonne = 0; kolonne < kontroller.ANTALL_KOLONNER; kolonne++){
                tegnRute(ruter[rad][kolonne]);
            }
        }
        tegnAntallPant();
    }   

    private void tegnAntallPant(){
        antallPant.setText("Pant samlet: " + kontroller.hentAntallPant());
    }

    public void visTaperMelding(){
        JOptionPane.showMessageDialog(
            rootPane,
            "UPS, DU TAPTE!"
            );
    }

    class SpillRute extends JLabel{

        int rad, kolonne;
        public SpillRute(int rad, int kolonne){
            super("", SwingConstants.CENTER);
            this.rad = rad;
            this.kolonne = kolonne;
            this.setPreferredSize(new Dimension(RUTE_DIMENSJON, RUTE_DIMENSJON));
            this.setBackground(SpillFarger.TOM);
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            this.setOpaque(true);
        }

    }

    class SpillPanel extends JPanel implements ActionListener, KeyListener{

        public SpillPanel(){
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.addKeyListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e){
            
            if (e.getKeyCode() == KeyEvent.VK_UP){kontroller.settRetning(Retning.RETNING_OPP);}
            if (e.getKeyCode() == KeyEvent.VK_DOWN){kontroller.settRetning(Retning.RETNING_NED);}
            if (e.getKeyCode() == KeyEvent.VK_LEFT){kontroller.settRetning(Retning.RETNING_VENSTRE);}
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){kontroller.settRetning(Retning.RETNING_HOYRE);}
            oppdater();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        
    }


}
