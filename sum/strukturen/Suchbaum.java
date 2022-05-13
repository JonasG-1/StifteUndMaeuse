package sum.strukturen;

import java.io.*;

/**
Ein Suchbaum ist als Bin&auml;rbaum nach der Schnittstelle aus dem Buch "Informatik mit Java" realisiert und enth&auml;lt Ordnungsobjekte.<br>
Er kann als Suchbaum mit einem Inhaltstyp, der Ordnungsobjekt implementiert, deklariert werden. Wird dieser weggelassen, liefert die Anfrage suche ein Object und es wird Typkonversion n&ouml;tig.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Suchbaum<Typ extends Ordnungsobjekt> extends Baum<Typ> implements Serializable
{
	static final long serialVersionUID = 8311777060702L;
	
	// Bezugsobjekte

    // Attribute

    // Konstruktor
    /**
     * Ein neuer Suchbaum mit leeren Teilb&auml;umen wird erzeugt
     * @param pInhalt das Objekt, das der Inhalt der Wurzel wird
     */
    public Suchbaum(Typ pInhalt)
    {
        super(pInhalt);
    }

    // Dienste
    /**
     * Ein Objekt wird an der passenden Stelle eingef&uuml;gt, wenn noch kein Objekt mit diesem Schl&uuml;ssel vorhanden war.
     * @param pInhalt das Objekt, das eingef&uuml;gt werden soll
     */
    public void fuegeEin(Typ pInhalt)
    {
        Suchbaum<Typ> lTeilbaum;
        
        if (this.inhalt() == null)
            this.setzeInhalt(pInhalt);
        else
        {
            if (pInhalt.istKleinerAls(this.inhalt()))
            {
                if (this.linkerTeilbaum() == null)
                    this.setzeLinkenTeilbaum(new Suchbaum(pInhalt));
                else
                {
                    lTeilbaum = (Suchbaum)this.linkerTeilbaum();
                    lTeilbaum.fuegeEin(pInhalt);
                }
            }
            else if (pInhalt.istGroesserAls(this.inhalt()))
            {
                if (this.rechterTeilbaum() == null)
                    this.setzeRechtenTeilbaum(new Suchbaum(pInhalt));
                else
                {
                    lTeilbaum = (Suchbaum)this.rechterTeilbaum();
                    lTeilbaum.fuegeEin(pInhalt);
                }
            }
            // else ist gleich, also nicht einf&uuml;gen, 
            // denn doppelte Inhalte sind verboten.
        }
    }

    /**
     * Wenn ein Objekt mit dem Schl&uuml;ssel von pInhalt vorhanden ist, wird es aus dem Baum gel&ouml;scht.
     * @param pInhalt das Objekt, das gel&ouml;scht werden soll
     */
    public void loesche(Typ pInhalt)
    {
        Suchbaum<Typ> lTeilbaum, lVater, lTeilbaum2;
        
        if (this.inhalt() != null)
        {
            if (pInhalt.istKleinerAls(this.inhalt()))
            {
                if (this.linkerTeilbaum() != null)
                {
                    lTeilbaum = (Suchbaum)this.linkerTeilbaum();
                    lTeilbaum.loesche(pInhalt);
                }
            }
            else if (pInhalt.istGroesserAls(this.inhalt()))
            {
                if (this.rechterTeilbaum() != null)
                {
                    lTeilbaum = (Suchbaum)this.rechterTeilbaum();
                    lTeilbaum.loesche(pInhalt);
                }
            }
            else // ist gleich, also gefunden
            {
                if (this.linkerTeilbaum() == null && this.rechterTeilbaum() == null)
                // also keine Teilb&auml;ume vorhanden
                {
                    lVater = (Suchbaum)this.vater();
                    if (lVater != null)
                    {
                        if (lVater.linkerTeilbaum() == this)
                            lVater.setzeLinkenTeilbaum(null);
                        else
                            lVater.setzeRechtenTeilbaum(null);
                    }
                    else // nur wenn Wurzel gel&ouml;scht wird und keine Teilb&auml;ume vorhanden
                        this.setzeInhalt(null);
                }
                else if (this.linkerTeilbaum() == null)
                // rechten Teilbaum an Vater anh&auml;ngen
                {
                    this.setzeInhalt(this.rechterTeilbaum().inhalt());
                    this.setzeLinkenTeilbaum(this.rechterTeilbaum().linkerTeilbaum());
                    this.setzeRechtenTeilbaum(this.rechterTeilbaum().rechterTeilbaum());
                }
                else if (this.rechterTeilbaum() == null)
                // linken Teilbaum an Vater anh&auml;ngen
                {
                    this.setzeInhalt(this.linkerTeilbaum().inhalt());
                    this.setzeLinkenTeilbaum(this.linkerTeilbaum().linkerTeilbaum());
                    this.setzeRechtenTeilbaum(this.linkerTeilbaum().rechterTeilbaum());
                }
                else // beide Teilb&auml;ume sind vorhanden
                {
                    lTeilbaum = (Suchbaum)this.rechterTeilbaum();
                    this.setzeInhalt(this.linkerTeilbaum().inhalt());
                    this.setzeRechtenTeilbaum(this.linkerTeilbaum().rechterTeilbaum());
                    this.setzeLinkenTeilbaum(this.linkerTeilbaum().linkerTeilbaum());
                    // rechtesten Teilbaum suchen
                    lTeilbaum2 = this;
                    while (lTeilbaum2.rechterTeilbaum() != null)
                        lTeilbaum2 = (Suchbaum)lTeilbaum2.rechterTeilbaum();
                    // alten rechten Teilbaum rechts anh&auml;ngen
                    lTeilbaum2.setzeRechtenTeilbaum(lTeilbaum);
                }
            }
        }
    }

    /**
     * Wenn ein Objekt mit dem Schl&uuml;ssel von pInhalt vorhanden ist, wird es geliefert, sonst ist das Ergebnis null.
     * @param pInhalt das Objekt, das gel&ouml;scht werden soll
     * @return das Objekt aus dem Suchbaum mit demselben Schl&uuml;ssel wie pInhalt
     */
    public Typ suche(Typ pInhalt)
    {
        Suchbaum<Typ> lTeilbaum;
        
        if (this.inhalt() == null)
            return null;
        else
        {
            if (pInhalt.istKleinerAls(this.inhalt()))
            {
                if (this.linkerTeilbaum() == null)
                    return null;
                else
                {
                    lTeilbaum = (Suchbaum)this.linkerTeilbaum();
                    return lTeilbaum.suche(pInhalt);
                }
            }
            else if (pInhalt.istGroesserAls(this.inhalt()))
            {
                if (this.rechterTeilbaum() == null)
                    return null;
                else
                {
                    lTeilbaum = (Suchbaum)this.rechterTeilbaum();
                    return lTeilbaum.suche(pInhalt);
                }
            }
            else // ist gleich, also gefunden
                return this.inhalt();
        }
    }

}
