import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

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
    JPanel spillInfo;
    JLabel antallPant;

    ImageIcon[] panteIkoner;

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
        rutenett = new SpillPanel();
        rutenett.setLayout(
            new GridLayout(
                kontroller.ANTALL_RADER,
                kontroller.ANTALL_KOLONNER 
                )
            );

        panteIkoner = new ImageIcon[]{
            new ImageIcon(new ImageIcon("panteIkon.jpeg").getImage().getScaledInstance(RUTE_DIMENSJON, RUTE_DIMENSJON, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("tuborg_ikon.jpeg").getImage().getScaledInstance(RUTE_DIMENSJON, RUTE_DIMENSJON, Image.SCALE_SMOOTH)),
            new ImageIcon(new ImageIcon("cola_ikon.jpeg").getImage().getScaledInstance(RUTE_DIMENSJON, RUTE_DIMENSJON, Image.SCALE_SMOOTH))
        };

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
            panteSpillTekst(),
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
        setTitle("Pantespillet");
        setName("Pantespillet");
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
            rute.setIcon(
                panteIkoner[(int)((Math.random() * (3 - 0))+0)] // TODO: Oppdatere ikone sjeldnere
            );
        }
    }

    void tegnAlleRuter(){
        for (int rad = 0; rad < kontroller.ANTALL_RADER; rad++){
            for (int kolonne = 0; kolonne < kontroller.ANTALL_KOLONNER; kolonne++){
                tegnRute(ruter[rad][kolonne]);
            }
        }
        tegnSpillTekst();
    }   

    private void tegnSpillTekst(){
        antallPant.setText("PANT: " + kontroller.hentAntallPant());
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
            this.setBorder(BorderFactory.createLineBorder(SpillFarger.RUTE_KANT));
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

    private JPanel panteSpillTekst(){
        System.out.println("Tegnet info" + kontroller.hentAntallPant());

        final Font FONT_STOR = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        final Font FONT_LITEN = new Font(Font.SANS_SERIF, Font.BOLD, 20);

        JPanel spillTekst = new JPanel();
        spillTekst.setLayout(new BorderLayout());
        spillTekst.setBackground(SpillFarger.TOM);

        JLabel tittel = new JLabel("Pantespillet");
        tittel.setFont(FONT_STOR);
        tittel.setForeground(SpillFarger.TEKST);
        spillTekst.add(tittel, BorderLayout.LINE_START);

        antallPant = new JLabel("PANT: " + kontroller.hentAntallPant(), SwingConstants.CENTER);
        antallPant.setFont(FONT_LITEN);
        antallPant.setForeground(SpillFarger.TEKST);
        spillTekst.add(antallPant);
        return spillTekst;
    }

}
