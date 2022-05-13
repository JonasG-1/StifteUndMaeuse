package sum.strukturen;

import java.io.*;
import sum.werkzeuge.*;

/**
 * Binaerbaum ist die Klasse f&uuml;r einen bin&auml;ren Baum nach der Schnittstelle aus dem learn:line Arbeitsbereich "Von Stiften und M&auml;usen".<br>
 * Er kann als Binaerbaum mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage wurzelInhalt ein Object und es wird Typkonversion n&ouml;tig.
 * @author Bernard Schriek
 * @version 7.5 vom 29.10.2013
 */
public class Binaerbaum<Typ> implements Serializable
{
    static final long serialVersionUID = 8311777060302L;

    Typ kenntInhalt;
    Binaerbaum<Typ> kenntLinkenNachfolger, kenntRechtenNachfolger, kenntVater;

    /**
     * Es wurde ein leerer Binaerbaum erzeugt.
     */
    public Binaerbaum()
    {
        kenntInhalt = null;
        kenntLinkenNachfolger = null;
        kenntRechtenNachfolger = null;
        kenntVater = null;
    }            
       
    /**
     * Es wurde ein Binaerbaum erzeugt, dessen Wurzel den angegebenen Inhalt hat und dessen Teilb&auml;ume leer sind.
     */
    public Binaerbaum(Typ pInhalt)
    {
        kenntInhalt = pInhalt;
        kenntLinkenNachfolger = this.neuerLeererBaum();
        kenntLinkenNachfolger.setzeVater(this);
        kenntRechtenNachfolger = this.neuerLeererBaum();
        kenntRechtenNachfolger.setzeVater(this);
    }        
    
    /**
     * Es wurde ein Binaerbaum erzeugt, dessen Wurzel den angegebenen Inhalt hat und der die angegebenen Teilb&auml;ume hat.
     */
    public Binaerbaum(Typ pInhalt, Binaerbaum<Typ> pLinkerBaum, Binaerbaum<Typ> pRechterBaum)
    {
        kenntInhalt = pInhalt;
        kenntLinkenNachfolger = pLinkerBaum;
        kenntRechtenNachfolger = pRechterBaum;
        kenntLinkenNachfolger.setzeVater(this);
        kenntRechtenNachfolger.setzeVater(this);
    }
    
    private Binaerbaum<Typ> neuerLeererBaum()
    {   
        Object lBaum=null;
        try
        {
          lBaum=this.getClass().newInstance();
        } catch (InstantiationException ie){System.out.println("fehler1");}
          catch (IllegalAccessException ae){System.out.println("fehler2");}
        return (Binaerbaum)lBaum;
    }

    /**
     * Die Wurzel hat den angegebenen Inhalt. Die beiden Teilb&auml;ume sind leer.
     */
    public void ueberschreibeWurzel(Typ pInhalt)
    {   
        if (this.istLeer())
        {
            kenntLinkenNachfolger = this.neuerLeererBaum();
            kenntRechtenNachfolger = this.neuerLeererBaum();
            kenntLinkenNachfolger.setzeVater(this);
            kenntRechtenNachfolger.setzeVater(this);
        }
        kenntInhalt = pInhalt;
    }
    
    /**
     * Der rechte Teilbaum ist nun der angegebene Binaerbaum.
     */
    public void haengeRechtsAn(Binaerbaum<Typ> pBaum)
    {
        if (! this.istLeer())
        {
            kenntRechtenNachfolger = pBaum;
            kenntRechtenNachfolger.setzeVater(this);
        }
    }
    
    /**
     * Der linke Teilbaum ist nun der angegebene Binaerbaum.
     */
    public void haengeLinksAn(Binaerbaum<Typ> pBaum)
    {
        if (!this.istLeer())
        {
            kenntLinkenNachfolger = pBaum;
            kenntLinkenNachfolger.setzeVater(this);
        }
    }
    
    private void setzeVater(Binaerbaum<Typ> pBaum)
    {
        kenntVater = pBaum;
    }
    
    /**
     * Die Anfrage liefert den Vater des Binaerbaums.
     */
    public Binaerbaum<Typ> vater()
    {
        return kenntVater;
    }
    
    /**
     * Die Anfrage liefert den Inhalt der Wurzel.
     */
    public Typ wurzelInhalt()
    {
         return kenntInhalt;
    }
    
    /**
     * Die Anfrage liefert den linken Teilbaum des Binaerbaums.
     */
    public Binaerbaum<Typ> linkerTeilbaum()
    {
         return kenntLinkenNachfolger;
    }
     
    /**
     * Die Anfrage liefert den rechten Teilbaum des Binaerbaums.
     */
    public Binaerbaum<Typ> rechterTeilbaum()
    {
        return kenntRechtenNachfolger;
    }   
    
    /**
     * Die Anfrage gibt an, ob der Binaerbaum leer ist.
     */
    public boolean istLeer()
    {
        return (kenntInhalt==null);
    }
    
    /**
     * Die Anfrage gibt an, ob der Binaerbaum ein Blatt ist.
     */
    public boolean teilbaeumeLeer()
    {
        return (kenntLinkenNachfolger.istLeer()) && (kenntRechtenNachfolger.istLeer());
    }

     /**
     * Die Anfrage liefert eine Stringrepr&auml;sentation des Baum mit seinen Unterb&auml;umen.
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
        for (int lNiveau = 1; lNiveau <= pTiefe; lNiveau++)
            lPunkte += ".";
         if (this.istLeer())
            lString += lPunkte + "leer\n";
        else
        {
            if (!this.teilbaeumeLeer())
                lString += kenntLinkenNachfolger.baumString(pTiefe + 1);
            lString += lPunkte + kenntInhalt.toString() + "\n";
            if (!this.teilbaeumeLeer())
                lString += kenntRechtenNachfolger.baumString(pTiefe + 1);
        }
        return lString;
    }

}
