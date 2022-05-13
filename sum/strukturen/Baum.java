package sum.strukturen;

import sum.werkzeuge.*;
import java.io.*;

/**
Die Klasse Baum ist ein Bin&auml;rbaum nach der Schnittstelle aus dem Buch "Informatik mit Java".<br>
Er kann als Baum mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage inhalt ein Object und es wird Typkonversion n&ouml;tig.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Baum<Typ> implements Serializable
{
	static final long serialVersionUID = 8311777060602L;
	
	// Bezugsobjekte
    /**
     * Der Inhalt des Knotens bzw. der Wurzel
     */
    private Typ kenntInhalt;
    
    /**
     * Der linke Teilbaum
     */
    private Baum<Typ> kenntLinks;
    
    /**
     * Der rechte Teilbaum
     */
    private Baum kenntRechts;
    
    /**
     * Der Vater des Baums
     */
    private Baum<Typ> kenntVater;
    // Attribute

    // Konstruktor
    /**
     * Ein neuer Baum mit leeren Teilb&auml;umen wird erzeugt
     * @param pInhalt das Objekt, das der Inhalt der Wurzel wird
     */
    public Baum(Typ pInhalt)
    {
        kenntInhalt = pInhalt;
        kenntLinks = null;
        kenntRechts = null;        
        kenntVater = null;
    }

    /**
     * Ein neuer Baum mit zwei Teilb&auml;umen wird erzeugt
     * @param pInhalt das Objekt, das der Inhalt der Wurzel wird
     * @param pLinks der linke Teilbaum
     * @param pRechts der rechte Teilbaum
     */
    public Baum(Typ pInhalt, Baum<Typ> pLinks, Baum<Typ> pRechts)
    {
        kenntInhalt = pInhalt;
        kenntLinks = pLinks;
        if (pLinks != null)
            pLinks.setzeVater(this);
        kenntRechts = pRechts;
        if (pRechts != null)
            pRechts.setzeVater(this);
        kenntVater = null;
    }

    // Dienste
    /**
     * Der Inhalt der Wurzel wird zur&uuml;ckgegeben
     * @return der Inhalt der Wurzel
     */
    public Typ inhalt()
    {
        return kenntInhalt;
    }
    
    /**
     * Der Inhalt der Wurzel wird ge&auml;ndert
     * @param pInhalt der neue Inhalt der Wurzel
     */
    public void setzeInhalt(Typ pInhalt)
    {
        kenntInhalt = pInhalt;
    }
    
    /**
     * Wenn die Wurzel keinen Inhalt besitzt, wird true zur&uuml;ckgegeben
     * @return ob der Inhalt der Wurzel leer ist
     */
    public boolean istLeer()
    {
        return kenntInhalt == null;
    }
       
    /**
     * Wenn die Teilb&auml;ume leer sind, wird true zur&uuml;ckgegeben
     * @return ob die Teilb&auml;e leer sind
     */
    public boolean teilbaeumeLeer()
    {
        return (kenntLinks.istLeer()) && (kenntRechts.istLeer());
    }
    
    /**
     * Der linke Teilbum wird zur&uuml;ckgegeben
     * @return den linken Teilbaum
     */
    public Baum<Typ> linkerTeilbaum()
    {
        return kenntLinks;
    }
    
    /**
     * Der rechte Teilbum wird zur&uuml;ckgegeben
     * @return den rechten Teilbaum
     */
    public Baum<Typ> rechterTeilbaum()
    {
        return kenntRechts;
    }
    
    /**
     * Der rechte Teilbaum wird ver&auml;ndert
     * @param pBaum der neue rechte Teilbaum
     */
    public void setzeLinkenTeilbaum(Baum<Typ> pBaum)
    {
        kenntLinks = pBaum;
        if (pBaum != null)
            pBaum.setzeVater(this);
    }
    
    /**
     * Der rechte Teilbaum wird ver&auml;ndert
     * @param pBaum der neue rechte Teilbaum
     */
    public void setzeRechtenTeilbaum(Baum<Typ> pBaum)
    {
        kenntRechts = pBaum;
        if (pBaum != null)
            pBaum.setzeVater(this);
    }
    
    /**
     * Wenn der Baum keine Teilb&auml;ume hat, wird true zur&uuml;ckgegeben
     * @return ob keine Teilb&auml;ume vorhanden sind
     */
    public boolean istBlatt()
    {
        return kenntLinks == null && kenntRechts == null;
    }
    
    /**
     * Der Vater des Baums wird zur&uuml;ckgegeben
     * @return den Vater des Baums
     */
    public Baum<Typ> vater()
    {
        return kenntVater;
    }
    
    /**
     * Der Vater des Baums wird ge&auml;ndert
     * @param pBaum der neue Vater
     */
    public void setzeVater(Baum<Typ> pBaum)
    {
        kenntVater = pBaum;
    }
    
    /**
     * Wenn der Baum keinen Vater hat, wird true zur&uuml;ckgegeben
     * @return ob kein Vater vorhanden ist
     */
    public boolean istWurzel()
    {
        return kenntVater == null;
    }
    
    /**
     * Eine Stringrepr&auml;sentation des Baum mit seinen Unterb&auml;umen wird zur&uuml;ckgegeben
     * @return einen String, der den Baum in mehreren Zeilen darstellt
     */
    public String toString()
    {
        String lString;
        Textwerkzeug lTextwerkzeug = new Textwerkzeug();
        
        lString = this.baumString(0);
        if (lTextwerkzeug.laenge(lString) > 0)
           lString = lTextwerkzeug.textOhne(lString, lTextwerkzeug.laenge(lString), lTextwerkzeug.laenge(lString));
        return lString;
    }
    
    /**
     * rekursiver Dienst um die Baumstruktur durch Einr&uuml;ckungen darzustellen
     * @param pTiefe die aktuelle Tiefe im Baum
     */
    private String baumString(int pTiefe)
    {
        String lString, lPunkte;
        
        lString = "";
        lPunkte = "";
        for (int i = 1; i <= pTiefe; i++)
            lPunkte += ".";
        if (kenntLinks != null)
            lString += kenntLinks.baumString(pTiefe + 1);
        if (kenntInhalt == null)
            lString += lPunkte + "leer\n";
        else
            lString += lPunkte + kenntInhalt.toString() + "\n";
        if (kenntRechts != null)
            lString += kenntRechts.baumString(pTiefe + 1);
        return lString;
    }
}