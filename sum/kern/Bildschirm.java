package sum.kern;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import sum.werkzeuge.*;

/**
Ein Bildschirm ist das Modell des angeschlossenen Computerbildschirms.
Er wird durch eine Fenster mit dem Titel "SuM-Programm" realisiert.
Auf ihm kann mit Stiften gezeichnet werden. Zu diesem Zweck ist die
Zeichenebene auf dem Bildschirm mit einem Koordinatensystem versehen,
dessen Ursprung sich in der oberen linken Ecke der Zeichenebene
befindet und dessen Achsen horizontal nach rechts und vertikal nach
unten gerichtet sind. Die Einheit ist ein Pixel.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Bildschirm extends JFrame
{
    protected static Bildschirm hatPrivatschirm;
    protected Vector hatTastaturpuffer;
    private JPanel hatPanel;
    protected static Bildschirm topFenster; // merkt sich das oberste Fenster
    protected static int zFensternummer;  // fuer den Fenstertitel

    // fuer den Bildschirm
    private Image dbImage = null;
    private Graphics2D dbGraphics = null;
    private Color zHintergrundfarbe = Color.white;
    private int zHoehe = 0, zBreite = 0;
    private boolean zMitDoubleBuffering;

    // fuer die Maus
    protected boolean zTasteIstUnten = false;
    protected boolean zTasteIstDoppel = false;
    protected int zMausHatPositionX = 0;
    protected int zMausHatPositionY = 0;

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
        }
    }

    private class MausBeweger extends MouseMotionAdapter
    {
        public void mouseDragged(MouseEvent e)
        {
            merkeXY(e.getX(), e.getY());
            merkeUnten(true);
        }

        public void mouseMoved(MouseEvent e)
        {
            merkeXY(e.getX(), e.getY());
            merkeUnten(false);
        }
    }

    private class MausTaster extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            merkeXY(e.getX(), e.getY());
        }

        public void mousePressed(MouseEvent e)
        {
            merkeXY(e.getX(), e.getY());
            merkeUnten(true);
        }

        public void mouseReleased(MouseEvent e)
        {
            merkeXY(e.getX(), e.getY());
            merkeUnten(false);
            if (e.getClickCount() > 1)
                merkeDoppelklick(true);
            else
                merkeDoppelklick(false);
        }
    }

    private class TastenTester extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() != 17)
                if (e.isActionKey() || e.getKeyCode() < 32
                || e.getKeyCode() == 127)
                    merkeDruck(e.getKeyCode() + 500);
                else
                    merkeDruck((int) e.getKeyChar());
        }
    }

    private class FokusReaktor implements FocusListener
    {
        public void focusGained(FocusEvent e)
        {
            bearbeiteFokusErhalten();
        }

        public void focusLost(FocusEvent e)
        {
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
        this(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1),
            pMitDoubleBuffering);
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
        if (hatPrivatschirm == null)
            hatPrivatschirm = this;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("mac os"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        zMitDoubleBuffering = pMitDoubleBuffering;
        hatPanel = (JPanel) this.getContentPane();
        hatPanel.setLayout(null); //dann funktioniert setSize und setLocation
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
        hatTastaturpuffer = new Vector();
        this.setVisible(true);
        this.fenstergroesseAnpassen();
        this.setSize(this.getWidth() - this.breite() + pBreite, this.getHeight() - this.hoehe() + pHoehe);
        if (zMitDoubleBuffering)
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbGraphics = (Graphics2D) dbImage.getGraphics();
        }
        zFensternummer++;
        this.init2DGraphics();
        this.setzeFarbe(Farbe.WEISS);
        this.zeichneDich();
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
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0));
    }

    /** 
    wird intern aufgerufen.
     */
    protected JPanel privatPanel()
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
    macht das Bildschirmfenster zum vordersten Fenster
     */
    public void nachVorn()
    {
        this.setAlwaysOnTop(true);
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
        if (dbImage != null)
            g.drawImage(dbImage, hatPanel.getX(), hatPanel.getY(), hatPanel);
        else
            super.paint(g);
    }

    /** 
    wenn der Blildschirm gepuffert ist, wird das gepufferte Bild jetzt angezeigt,
    falls der Bildschirm nicht gepuffert ist, bewirkt diese Anweisung nichts.
     */
    public void zeichneDich()
    {
        if (zMitDoubleBuffering)
            hatPanel.getGraphics().drawImage(dbImage, hatPanel.getX(),
                hatPanel.getY(), hatPanel);
    }

    /** 
    aendert die Hintergrundfarbe der Zeichenebene. Alte
    Zeichnungen auf dem Bildschirm werden geloescht.
    @param pFarbe die neue Hintergrundfarbe des Bildschirms
     */
    public void setzeFarbe(Color pFarbe)
    {
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
        hatPanel.paintImmediately(hatPanel.getBounds());
        hatPanel.validate();
    }

    /**
    aendert die Hintergrundfarbe der Zeichenebene. Alte
    Zeichnungen auf dem Bildschirm werden geloescht.
    @param pFarbe die neue Hintergrundfarbe des Bildschirms
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
    protected Color hintergrundfarbe()
    {
        return zHintergrundfarbe;
    }

    /** 
    liefert die Breite der Zeichenebene.
    @return die Breite des Fensters
     */
    public int breite()
    {
        return zBreite;
    }

    /** 
    liefert die Hoehe der Zeichenebene.
    @return die Hoehe des Fensters
     */
    public int hoehe()
    {
        return zHoehe;
    }

    /**
     * liefert einen Text, der  mit einem Eingabedialog gelesen wird.
     * @return den eingegebenen Text
     */
    public String holeText()
    {
        Eingabe lEingabe = new Eingabe(this, "Gib einen Text ein.");
        return lEingabe.eingabe();
    }

    /**
     * liefert eine (Komma-) Zahl, die mit einem Eingabedialog gelesen wird.
     * Falls die Eingabe keine Zahl ist, wird 0.0 zur&uuml;ckgegeben.
     * @return die eingegebene Zahl
     */
    public double holeZahl()
    {
        Textwerkzeug lTW;
        String lText;
        double lZahl;

        lTW = new Textwerkzeug();
        Eingabe lEingabe = new Eingabe(this, "Gib eine Zahl ein.");
        lText = lEingabe.eingabe();
        if (lTW.istZahl(lText))
            lZahl = lTW.alsZahl(lText);
        else
            lZahl = 0.0;
        return lZahl;
    }

    /**
     * liefert eine ganze Zahl, die mit einem Eingabedialog gelesen wird.
     * Falls die Eingabe keine ganze Zahl ist, wird 0 zur&uuml;ckgegeben.
     * @return die eingegebene ganze Zahl
     */
    public int holeGanzeZahl()
    {
        Textwerkzeug lTW;
        String lText;
        int lZahl;

        lTW = new Textwerkzeug();
        Eingabe lEingabe = new Eingabe(this, "Gib eine ganze Zahl ein.");
        lText = lEingabe.eingabe();
        if (lTW.istGanzeZahl(lText))
            lZahl = lTW.alsGanzeZahl(lText);
        else
            lZahl = 0;
        return lZahl;
    }

    /**
     * liefert einen Text, der  mit einem Eingabedialog gelesen wird.
     * @param ein im Eingabedialog angezeigter Text
     * @return den eingegebenen Text
     */
    public String holeText(String pMeldung)
    {
        Eingabe lEingabe = new Eingabe(this, pMeldung);
        return lEingabe.eingabe();
    }

    /**
     * liefert eine (Komma-) Zahl, die mit einem Eingabedialog gelesen wird.
     * Falls die Eingabe keine Zahl ist, wird 0.0 zur&uuml;ckgegeben.
     * @param ein im Eingabedialog angezeigter Text
     * @return die eingegebene Zahl
     */
    public double holeZahl(String pMeldung)
    {
        Textwerkzeug lTW;
        String lText;
        double lZahl;

        lTW = new Textwerkzeug();
        Eingabe lEingabe = new Eingabe(this, pMeldung);
        lText = lEingabe.eingabe();
        if (lTW.istZahl(lText))
            lZahl = lTW.alsZahl(lText);
        else
            lZahl = 0.0;
        return lZahl;
    }

    /**
     * liefert eine ganze Zahl, die mit einem Eingabedialog gelesen wird.
     * Falls die Eingabe keine ganze Zahl ist, wird 0 zur&uuml;ckgegeben.
     * @param ein im Eingabedialog angezeigter Text
     * @return die eingegebene ganze Zahl
     */
    public int holeGanzeZahl(String pMeldung)
    {
        Textwerkzeug lTW;
        String lText;
        int lZahl;

        lTW = new Textwerkzeug();
        Eingabe lEingabe = new Eingabe(this, pMeldung);
        lText = lEingabe.eingabe();
        if (lTW.istGanzeZahl(lText))
            lZahl = lTW.alsGanzeZahl(lText);
        else
            lZahl = 0;
        return lZahl;
    }

    /** 
    Der Bildschirm wird zerstoert.
     */
    public void gibFrei()
    {
        int i;

        if (this.equals(hatPrivatschirm))
        {
            this.setTitle("Das SuM-Programm ist beendet.");
            this.warte(1);
            while (zTasteIstUnten)
            //Warte auf Mauslos
                this.warte(1);
            while (!zTasteIstUnten)
            //Warte auf Mausdruck
                this.warte(1);
        }
        this.fensterZerstoeren();
    }

    /**
    interne Methode, wenn die Fenstergroesse veraendert wird
     */
    private void fenstergroesseAnpassen()
    {
        this.merkeGroesse(hatPanel.getVisibleRect().width, hatPanel
            .getVisibleRect().height);
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
        this.dispose();
        if (this.equals(hatPrivatschirm))
            System.exit(0);
    }

    /** 
    interne Methode
     */
    private void merkeUnten(boolean b)
    {
        zTasteIstUnten = b;
    }

    /** 
    interne Methode
     */
    private void merkeXY(int x, int y)
    {
        zMausHatPositionX = x;
        zMausHatPositionY = y;
    }

    /** 
    interne Methode
     */
    private void merkeDoppelklick(boolean b)
    {
        zTasteIstDoppel = b;
    }

    /** 
    interne Methode
     */
    private void merkeDruck(int z)
    {
        hatTastaturpuffer.addElement(new Integer(z));
        if (z == 27)
        {
            this.dispose();
            System.exit(0);
        }
    }

    /** 
    interne Methode zum Testen
     */
    protected static void fehler(String pMeldung)
    {
        System.out.println(pMeldung);
        System.exit(0);
    }

}