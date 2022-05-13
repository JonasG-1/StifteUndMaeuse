package sum.strukturen;

import java.io.*;

/**
* Suchbinaerbaum ist die Klasse f&uuml;r einen Suchbaum nach der Schnittstelle aus dem learn:line Arbeitsbereich "Von Stiften und M&auml;usen".<br>
* Ein  verwaltet Exemplare von Unterklassen der Klasse SuchbaumInhalt.<br>
* Er kann als Suchbinaerbaum mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage suche ein Object und es wird Typkonversion n&ouml;tig.

* @author Horst Hildebrecht
* @version 7.5 vom 29.10.2013
*/
public class Suchbinaerbaum < Typ extends SuchbaumInhalt > extends Binaerbaum < Typ > implements Serializable
{
    static final long serialVersionUID = 8311777060402L;

    private Liste hatListe;

    /**
    * Ein leerer Suchbaum existiert.
    */
    public Suchbinaerbaum()
    {
        super();
    }

    /**
     * Diese Anfrage liefert den linken Teilbaum des Suchbaums.
     */
    public Suchbinaerbaum < Typ > linkerSuchbaum()
    {
        return(Suchbinaerbaum) this.linkerTeilbaum();
    }

    /**
     * Diese Anfrage liefert den rechten Teilbaum des Suchbaums.
     */
    public Suchbinaerbaum < Typ > rechterSuchbaum()
    {
        return(Suchbinaerbaum) this.rechterTeilbaum();
    }

    /**
     * Wenn ein Objekt mit dem Schl&uuml;ssel von pInhalt noch nicht im Suchbaum war, wurde pInhalt passend eingef&uuml;gt. Andernfalls wurde der Z&auml;hler in dem entsprechenden Knoteninhalt um eins erh&ouml;ht.
     */
    public void fuegeEin(Typ pInhalt)
    {
        Typ lSuchergebnis = this.suche(pInhalt);
        if(lSuchergebnis != null)
            lSuchergebnis.erhoeheAnzahl();
        else if(this.istLeer())
        {
            this.ueberschreibeWurzel(pInhalt);
        }
        else
        {
            if(pInhalt.istKleiner(this.wurzelInhalt()))
            {
                this.linkerSuchbaum().fuegeEin(pInhalt);
            }
            else if(pInhalt.istGroesser(this.wurzelInhalt()))
                this.rechterSuchbaum().fuegeEin(pInhalt);
        }
    }

    /**
     * Wenn ein Objekt mit dem Schl&uuml;ssel von pObjekt im Baum gefunden wurde, liefert die Anfrage dieses Objekt, andernfalls ist das Ergebnis null.
     */
    public Typ suche(Typ pObjekt)
    {
        if(this.istLeer())
            return null;
        else
        {
            if(pObjekt.istKleiner(this.wurzelInhalt()))
                return this.linkerSuchbaum().suche(pObjekt);
            else if(pObjekt.istGroesser(this.wurzelInhalt()))
                return this.rechterSuchbaum().suche(pObjekt);
            else
                return this.wurzelInhalt();
        }
    }

    /**
     * Es gibt keinen Knoten im Suchbaum mit demselben Schl&uuml;ssel wie pObjekt.
     */
    public void entferne(Typ pObjekt)
    {
        Suchbinaerbaum < Typ > lKnoten, lGroessterLinkerKnoten, lVaterGroesster;
        if(this.istLeer())
        {
            if(this.wurzelInhalt().istGleich(pObjekt))
            {
                if(this.rechterSuchbaum().istLeer() && this.linkerSuchbaum().istLeer())
                {
                    this.haengeRechtsAn(null);
                    this.haengeLinksAn(null);
                    this.ueberschreibeWurzel(null);
                }
                else
                {
                    if(this.rechterSuchbaum().istLeer())
                    {
                        lKnoten = this.rechterSuchbaum();
                        this.ueberschreibeWurzel(lKnoten.wurzelInhalt());
                        this.haengeLinksAn(lKnoten.linkerSuchbaum());
                        this.haengeRechtsAn(lKnoten.rechterSuchbaum());
                    }
                    else
                    {
                        lVaterGroesster = null;
                        lGroessterLinkerKnoten = this.linkerSuchbaum();
                        while(!lGroessterLinkerKnoten.rechterSuchbaum().istLeer())
                        {
                            lVaterGroesster = lGroessterLinkerKnoten;
                            lGroessterLinkerKnoten = lGroessterLinkerKnoten.rechterSuchbaum();
                        }
                        if(!lVaterGroesster.istLeer())
                        {
                            lVaterGroesster.haengeRechtsAn(lGroessterLinkerKnoten.linkerSuchbaum());
                            lGroessterLinkerKnoten.haengeLinksAn(this.linkerSuchbaum());
                        }
                        this.ueberschreibeWurzel(lGroessterLinkerKnoten.wurzelInhalt());
                        this.haengeLinksAn(lGroessterLinkerKnoten.linkerSuchbaum());
                    }
                }
            }
            else if(this.wurzelInhalt().istKleiner(pObjekt))
                this.rechterSuchbaum().entferne(pObjekt);
            else
                this.linkerSuchbaum().entferne(pObjekt);
        }

    }

    /**
     * Der Suchbinaerbaum ist nun leer.
     */
    public void entferneAlle()
    {
        if(!this.istLeer())
        {
            this.linkerSuchbaum().entferneAlle();
            this.rechterSuchbaum().entferneAlle();
            this.entferne(this.wurzelInhalt());
        }
    }

    /**
     * Diese Anfrage liefert eine nach dem Schl&uuml;ssel sortierte Liste aller Inhalte des Suchbaums.
     */
    public Liste sortierteListe()
    {
        hatListe = new Liste();
        this.ergaenzeListe(hatListe);
        return hatListe;
    }

    private void ergaenzeListe(Liste pListe)
    {
        if(!this.istLeer())
        {
            this.linkerSuchbaum().ergaenzeListe(pListe);
            pListe.haengeAn(this.wurzelInhalt());
            this.rechterSuchbaum().ergaenzeListe(pListe);
        }
    }

}
