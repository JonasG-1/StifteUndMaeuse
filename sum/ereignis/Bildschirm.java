package sum.ereignis;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javafx.*;

/**
Ein Bildschirm ist das Modell des angeschlossenen Computerbildschirms.
Auf ihm kann mit Stiften gezeichnet werden. Zu diesem Zweck ist die
Zeichenebene auf dem Bildschirm mit einem Koordinatensystem versehen,
dessen Ursprung sich in der oberen linken Ecke der Zeichenebene
befindet und dessen Achsen horizontal nach rechts und vertikal nach
unten gerichtet sind. Die Einheit ist ein Pixel.
Der Bildschirm kann mit Doppelpufferung versehen werden. Das bedeutet,
dass statt auf den Bildschirm in ein verstecktes Bild gezeichnet wird. Dieses
Bild kann mit dem Auftrag zeichneDich() im Bildschirm angezeigt werden.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Bildschirm extends JFrame
{
    public static Bildschirm hatPrivatschirm;
    private JPanel hatPanel;
    public static Bildschirm topFenster; // merkt sich das oberste Fenster
    protected static int zFensternummer;  // fuer den Fenstertitel
    private Ereignisanwendung kenntEreignisanwendung;
    private Color zHintergrundfarbe = Color.white;
    private Image dbImage = null;
    private Graphics2D dbGraphics = null;
    private int zHoehe = 0, zBreite = 0;
    private boolean zHatFocus = true;
    private boolean zHatGezeichnet = false;
    private boolean zMitDoubleBuffering;

    private class FensterTester extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            fensterZerstoeren();
        }
    }

    private class GroesseTester extends ComponentAdapter
    {
        public void componentResized(ComponentEvent e)
        {
            fenstergroesseAnpassen();
            e.getComponent().repaint();
        }

        public void componentMoved(ComponentEvent e)
        {
            e.getComponent().repaint();
        }
    }

    private class MausBeweger extends MouseMotionAdapter
    {
        public void mouseDragged(MouseEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
                kenntEreignisanwendung.bearbeiteMausBewegt(e.getX(), e.getY());
        }

        public void mouseMoved(MouseEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
                kenntEreignisanwendung.bearbeiteMausBewegt(e.getX(), e.getY());
        }
    }

    private class MausTaster extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
                kenntEreignisanwendung.bearbeiteMausBewegt(e.getX(), e.getY());
        }

        public void mousePressed(MouseEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
            {
                kenntEreignisanwendung.bearbeiteMausDruck(e.getX(), e.getY());
                hatPanel.requestFocus();
            }
        }

        public void mouseReleased(MouseEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
                if (e.getClickCount() > 1)
                    kenntEreignisanwendung.bearbeiteDoppelKlick(e.getX(), e
                        .getY());
                else
                    kenntEreignisanwendung.bearbeiteMausLos(e.getX(), e.getY());
        }
    }

    private class TastenTester extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus()
            && e.getKeyCode() != 17)
                if (e.isActionKey() || e.getKeyCode() < 32
                || e.getKeyCode() == 127)
                    kenntEreignisanwendung.bearbeiteTaste((char) (e
                            .getKeyCode() + 500));
                else
                    kenntEreignisanwendung.bearbeiteTaste(e.getKeyChar());
        }
    }

    private class FokusReaktor implements FocusListener
    {
        public void focusGained(FocusEvent e)
        {
            bearbeiteFokusErhalten();
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
            {
                zHatFocus = true;
                kenntEreignisanwendung.bearbeiteFokusErhalten();
            }
        }

        public void focusLost(FocusEvent e)
        {
            if (kenntEreignisanwendung != null
            && kenntEreignisanwendung.fuehrtAus())
            {
                zHatFocus = false;
                kenntEreignisanwendung.bearbeiteFokusVerloren();
            }
        }
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die Fenstergroesse entspricht der Bildschirmgroesse.
     */
    public Bildschirm()
    {
        this(0, 0, -1, -1, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die Fenstergroesse entspricht der Bildschirmgroesse.
    @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Bildschirm(boolean pMitDoubleBuffering)
    {
        this(0, 0, -1, -1, "SuM-Fenster " + (zFensternummer + 1), pMitDoubleBuffering);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die linke obere Ecke sowie die Breite und Hoehe des Bildschirms werden
    als Parameter uebergeben.
    @param pLinks der Abstand des Fensters vom linken Bildschirmrand
    @param pOben der Abstand des Fensters vom oberen Bildschirmrand
    @param pBreite die Breite des Fensters
    @param pHoehe die Hoehe des Fensters
     */
    public Bildschirm(int pLinks, int pOben, int pBreite, int pHoehe)
    {
        this(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die linke obere Ecke sowie die Breite und Hoehe des Bildschirms werden
    als Parameter uebergeben.
    @param pLinks der Abstand des Fensters vom linken Bildschirmrand
    @param pOben der Abstand des Fensters vom oberen Bildschirmrand
    @param pBreite die Breite des Fensters
    @param pHoehe die Hoehe des Fensters
    @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Bildschirm(int pLinks, int pOben, int pBreite, int pHoehe,
    boolean pMitDoubleBuffering)
    {
        this(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), pMitDoubleBuffering);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die Breite und Hoehe des Bildschirms werden als Parameter uebergeben.
    @param pBreite die Breite des Fensters
    @param pHoehe die Hoehe des Fensters
     */
    public Bildschirm(int pBreite, int pHoehe)
    {
        this(0, 0, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die Breite und Hoehe des Bildschirms werden als Parameter uebergeben.
    @param pBreite die Breite des Fensters
    @param pHoehe die Hoehe des Fensters
    @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Bildschirm(int pBreite, int pHoehe, boolean pMitDoubleBuffering)
    {
        this(0, 0, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), pMitDoubleBuffering);
    }

    /**
    Der Bildschirm ist mit seiner Zeichenebene initialisiert.
    Die linke obere Ecke sowie die Breite und Hoehe des Bildschirms werden
    als Parameter uebergeben.
    @param pLinks der Abstand des Fensters vom linken Bildschirmrand
    @param pOben der Abstand des Fensters vom oberen Bildschirmrand
    @param pBreite die Breite des Fensters
    @param pHoehe die Hoehe des Fensters
    @param pAlsFenster der Bildschim ist ein zus&auml;tzliches Fenster
    @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    protected Bildschirm(int pLinks, int pOben, int pBreite, int pHoehe,
    String pName, boolean pMitDoubleBuffering)
    {
        super(pName);
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE) ;
//        if (hatPrivatschirm == null)
            hatPrivatschirm = this;
        zMitDoubleBuffering = pMitDoubleBuffering;
        kenntEreignisanwendung = Ereignisanwendung.hatSuMPrivateAnwendung;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("mac os"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        this.setJMenuBar(new JMenuBar());
        hatPanel = (JPanel) this.getContentPane();
        hatPanel.setLayout(null); //dann funktioniert setSize und setLocation
        if (osName.toLowerCase().startsWith("mac os"))
            hatPanel.setBounds(0, 0, 2000, 2000);
        else
            hatPanel.setBounds(0, 22, 2000, 2022);
        hatPanel.setOpaque(true);
        this.addWindowListener(new FensterTester());
        this.addComponentListener(new GroesseTester());
        hatPanel.addMouseMotionListener(new MausBeweger());
        hatPanel.addMouseListener(new MausTaster());
        hatPanel.addKeyListener(new TastenTester());
        hatPanel.addFocusListener(new FokusReaktor());
        if (pBreite == -1) // ganzer Bildschirm
        {
            Dimension dimension = this.getToolkit().getScreenSize();
            pBreite = dimension.width - 20;
            pHoehe = dimension.height - 60;
        }
        this.setBounds(pLinks, pOben, pBreite, pHoehe);
        this.setVisible(true);
        this.getJMenuBar().setVisible(true);
        this.fenstergroesseAnpassen();
        this.setSize(this.getWidth() - this.breite() + pBreite, this
            .getHeight()
            - this.hoehe() + pHoehe);
        if (zMitDoubleBuffering)
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbGraphics = (Graphics2D) dbImage.getGraphics();
        }
        this.init2DGraphics();
        this.setzeFarbe(Farbe.WEISS);
        this.bearbeiteFokusErhalten();
        this.warte(1000);
        hatPanel.requestFocus();
    }

    /** 
    wird intern aufgerufen.
     */
    protected void init2DGraphics()
    {
        Graphics2D g2d;

        if (zMitDoubleBuffering)
            g2d = dbGraphics;
        else
            g2d = (Graphics2D) hatPanel.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) 1.0));
    }

    /** 
    wird intern aufgerufen.
     */
    public JPanel privatPanel()
    {
        return hatPanel;
    }

    /** 
     wird intern aufgerufen.
     */
    protected void bearbeiteFokusErhalten()
    {
        topFenster = this;
    }

    /** 
    wird intern aufgerufen.
     */
    protected void merkeGroesse(int x, int y)
    {
        zBreite = x;
        zHoehe = y;
    }

    /** 
    wird intern aufgerufen.
     */
    protected Graphics g()
    {
        if (zMitDoubleBuffering)
            return dbGraphics;
        else
            return hatPanel.getGraphics();
    }

    /** 
    wird intern aufgerufen.
     */
    public void paint(Graphics g)
    {
        Component komponente;

        if (dbImage != null)
            g.drawImage(dbImage, 0, 0, this);
        else
            super.paint(g);
        for (int i = 0; i < hatPanel.getComponentCount(); i++)
        {
            komponente = hatPanel.getComponent(i);
            komponente.repaint();
        }
    }

    /** 
    wird intern aufgerufen.
     */
    public void update(Graphics g)
    {
        super.update(g);
        if (zHatGezeichnet)
            kenntEreignisanwendung.bearbeiteUpdate();
        else
        if (this.isVisible() && kenntEreignisanwendung.fuehrtAus())
        {
            zHatGezeichnet = true;
            this.loescheAlles();
        }
    }

    /** 
    wenn der Blildschirm gepuffert ist, wird das gepufferte Bild jetzt angezeigt,
    falls der Bildschirm nicht gepuffert ist, bewirkt diese Anweisung nichts.
     */
    public void zeichneDich()
    {
        Component komponente;

        if (zMitDoubleBuffering)
            hatPanel.getGraphics().drawImage(dbImage, 0, 0, this);
        for (int i = 0; i < hatPanel.getComponentCount(); i++)
        {
            komponente = hatPanel.getComponent(i);
            komponente.repaint();
        }
    }

    /**
    Liefert die Information, ob der Bildschirm den Fokus besitzt.
    @return true, wenn das Fenster den Fokus besitzt.
     */
    public boolean besitztFokus()
    {
        return zHatFocus;
    }

    /**
    Der Bildschirm erhaelt den Fokus.
     */
    public void setzeFokus()
    {
        hatPanel.requestFocus();
    }

    /** 
    aendert die Hintergrundfarbe der Zeichenebene.
    @param pFarbe die neue Farbe des Fensterhintergrundes.
     */
    public void setzeFarbe(Color pFarbe)
    {
        Component komponente;

        if (zMitDoubleBuffering)
        {
            dbGraphics.setBackground(pFarbe);
            dbGraphics.clearRect(0, 0, 2000, 2000);
        }
        else
        {
            hatPanel.setBackground(pFarbe);
            hatPanel.getGraphics().clearRect(0, 0, 2000, 2000);
        }
        zHintergrundfarbe = pFarbe;
        hatPanel.paintImmediately(0, 0, 2000, 2000);
        hatPanel.validate();
        for (int i = 0; i < hatPanel.getComponentCount(); i++)
        {
            komponente = hatPanel.getComponent(i);
            komponente.setBackground(pFarbe);
            komponente.repaint();
        }
    }

    /**
    aendert die Hintergrundfarbe der Zeichenebene.
    @param pFarbe die neue Farbe des Fensterhintergrundes.
     */
    public void setzeFarbe(int pFarbe)
    {
        if (pFarbe < 0)
            pFarbe = 0;
        pFarbe %= 13;
        switch (pFarbe)
        {
            case 0:
            this.setzeFarbe(Color.black);
            break;
            case 1:
            this.setzeFarbe(Color.blue);
            break;
            case 2:
            this.setzeFarbe(Color.cyan);
            break;
            case 3:
            this.setzeFarbe(Color.darkGray);
            break;
            case 4:
            this.setzeFarbe(Color.gray);
            break;
            case 5:
            this.setzeFarbe(Color.green);
            break;
            case 6:
            this.setzeFarbe(Color.lightGray);
            break;
            case 7:
            this.setzeFarbe(Color.magenta);
            break;
            case 8:
            this.setzeFarbe(Color.orange);
            break;
            case 9:
            this.setzeFarbe(Color.pink);
            break;
            case 10:
            this.setzeFarbe(Color.red);
            break;
            case 11:
            this.setzeFarbe(Color.white);
            break;
            case 12:
            this.setzeFarbe(Color.yellow);
            break;
        }
    }

    /**
    Die Zeichenebene ist danach leer.
     */
    public void loescheAlles()
    {
        this.setzeFarbe(zHintergrundfarbe);
    }

    /** 
    wird intern aufgerufen.
     */
    protected void warte(long zeit)
    {
        try
        {
            Thread.sleep(zeit);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    /** 
    wird intern aufgerufen.
     */
    public Color hintergrundfarbe()
    {
        return zHintergrundfarbe;
    }

    /** 
    wird intern aufgerufen.
     */
    public void doUpdate(JComponent pKomponente)
    {
        pKomponente.paintImmediately(0, 0, pKomponente.getWidth(), pKomponente
            .getHeight());
        pKomponente.validate();
    }

    /** 
    liefert die Breite der Zeichenebene.
    @return die aktuelle Breite des Fensters
     */
    public int breite()
    {
        return zBreite;
    }

    /** 
    liefert die Hoehe der Zeichenebene.
    @return die aktuelle Hoehe des Fensters
     */
    public int hoehe()
    {
        return zHoehe;
    }

    /** 
    Der Bildschirm wird unsichtbar.
     */
    public void verstecke()
    {
        this.setVisible(false);
    }

    /** 
    Der Bildschirm wird sichtbar.
     */
    public void zeige()
    {
        this.setVisible(true);
    }

    /** 
    Der Bildschirm (das Fenster) wird ganz nach vorn geholt
    und steht in Zukunft vor allen andern Fenstern.
     */
    public void immerNachVorn()
    {
        this.setAlwaysOnTop(true);
    }

    /** 
    Der Bildschirm (das Fenster) wird nach vorn geholt
    und erh&auml;lt den Fokus.
     */
    public void nachVorn()
    {
        this.toFront();
    }

    /** 
    Der Bildschirm (das Fenster) wird nach hinten verschoben
    und verliert den Fokus.
     */
    public void nachHinten()
    {
        this.toBack();
    }

    /** 
    Der Bildschirm wird zerstoert.
     */
    public void gibFrei()
    {
        this.fensterZerstoeren();
    }

    /**
    interne Methode, wenn die Fenstergroesse veraendert wird
     */
    private void fenstergroesseAnpassen()
    {
        this.merkeGroesse(hatPanel.getVisibleRect().width, hatPanel.getVisibleRect().height);
        if (zMitDoubleBuffering)
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbGraphics = (Graphics2D) dbImage.getGraphics();
            this.init2DGraphics();
            this.setzeFarbe(zHintergrundfarbe);
        }
    }

    /**
    interne Methode, wenn das Fenster geschlossen wird
     */
    private void fensterZerstoeren()
    {
        if (this.equals(hatPrivatschirm))
            kenntEreignisanwendung.halteAn();
        this.dispose();
    }

}