import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class PanteSpilletGUI extends JFrame{
    
    final int RUTE_DIMENSJON = 20;

    Kontroller kontroller;
    SpillRute[][] ruter;
    SpillPanel rutenett;
    JLabel antallPant;
    private static final int spillDelay = 2000;

    PanteSpilletGUI(
        Kontroller kontroller
    ){
        
        this.kontroller = kontroller;

        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.exit(1);
        }

        rutenett = new SpillPanel();
        rutenett.setLayout(
            new GridLayout(
                kontroller.ANTALL_RADER,
                kontroller.ANTALL_KOLONNER 
                )
            );

        ruter = new SpillRute[kontroller.ANTALL_RADER][kontroller.ANTALL_KOLONNER];

        assert kontroller.ANTALL_RADER > 0 && kontroller.ANTALL_KOLONNER > 0;

        for (int rad = 0; rad < kontroller.ANTALL_RADER; rad++){
            for (int kolonne = 0; kolonne < kontroller.ANTALL_KOLONNER; kolonne++){
                SpillRute rute = new SpillRute(rad, kolonne);
                ruter[rad][kolonne] = rute;
                rutenett.add(rute);
            }
        }

        add(
            rutenett,
            BorderLayout.CENTER
            );
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setName("PanteSpillet");

        spill();

    }

    void spill(){

        kontroller.start();

        kontroller.settHode(
            kontroller.ANTALL_RADER / 2,
            kontroller.ANTALL_KOLONNER / 2
        );

        new Thread(
            () -> {
                while (kontroller.kjorer()){
                    try {
                        Thread.sleep(spillDelay);
                        SwingUtilities.invokeLater(
                        () -> {
                            oppdater();
                        }
                    );
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }
            }
        ).start();
    }

    void oppdater(){
        kontroller.oppdater();
        tegnAlleRuter();  
    }

    void tegnRute(SpillRute rute){
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.HODE){rute.setBackground(SpillFarger.HODE);}
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.TOM){rute.setBackground(SpillFarger.TOM);}
        if (kontroller.hentRute(rute.rad, rute.kolonne).ruteType == RuteType.PANT){rute.setBackground(SpillFarger.PANT);}
        kontroller.oppdater();
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

    }

    class SpillRute extends JLabel{

        int rad, kolonne;
        public SpillRute(int rad, int kolonne){
            this.rad = rad;
            this.kolonne = kolonne;
            this.setPreferredSize(new Dimension(RUTE_DIMENSJON, RUTE_DIMENSJON));
            this.setBackground(SpillFarger.BAKGRUNN);
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
        }

        
    }


}
