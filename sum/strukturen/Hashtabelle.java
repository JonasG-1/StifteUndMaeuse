package sum.strukturen;

import sum.werkzeuge.*;
import java.io.*;

/**
Eine Hashtabelle enth&auml;lt Schl&uuml;sselobjekte, um nach dem Schl&uuml;ssel suchen
zu k&ouml;nnen.<br>
Sie kann als Hashtabelle mit einem Inhaltstyp, der Ordnungsobjekt implementiert, deklariert werden. 
Wird dieser weggelassen, liefert die Anfrage suche ein Object und es wird Typkonversion n&ouml;tig.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Hashtabelle<Typ extends Schluesselobjekt> implements Serializable
{
    static final long serialVersionUID = 8311777060802L;
    
    // Bezugsobjekte
    /**
     * Ein Rechner um die Hashfunktion zu berechnen
     */
    private Rechner hatRechner;
    
    /**
     * Ein Objekt, das in einer gel&ouml;schten Zelle steht
     */
    private Object hatGeloescht;

    // Attribute
    /**
     * Die Tabelle, die Schl&uuml;sselobjekte enth&auml;lt
     */
    private Object[] zFeld;
    
     /**
     * Die Gr&ouml;&szlig;e der Hashtabelle
     */
    private int zGroesse;

    /**
     * Die Anzahl der belegten Zellen in der Hashtabelle
     */
    private int zAnzahl;

    // Konstruktor
    /**
     * Eine neue leere Hashtabelle mit der Gr&ouml;&szlig;e 11 wird angelegt
     */
    public Hashtabelle()
    {
        zGroesse = 11;
        zFeld = new Object[zGroesse];
        hatRechner = new Rechner();
        hatGeloescht = new Object();
        zAnzahl = 0;
    }

    /**
     * Eine neue leere Hashtabelle wird angelegt
     * @param pGroesse die Gr&ouml;&szlig;e der neuen Hashtabelle
     */
    public Hashtabelle(int pGroesse)
    {
        zGroesse = pGroesse;
        zFeld = new Object[zGroesse];
        hatRechner = new Rechner();
        hatGeloescht = new Object();
        zAnzahl = 0;
    }

    // Dienste    
    /**
     * Die Hashfunktion mit der Divisions-Rest-Methode
     * @param pObject das Object zu dem die Position in der Hashtabelle berechnet wird
     * @return die Position in der Hashtabelle f&uuml;r den Parameter pObject
     */
    private int h(Object pObject)
    {
        int lHashcode;
        
        lHashcode = pObject.hashCode();
        return hatRechner.betrag(lHashcode) % zGroesse;
    }
    
    /**
     * Ein neues Schl&uuml;sselobjekt wird eingef&uuml;gt
     * @param pSchluesselobjekt das Schl&uuml;sselobjekt, das eingef&uuml;gt wird
     */
    public void fuegeEin(Schluesselobjekt pSchluesselobjekt)
    {
        int lPosition;
        
        if (this.suche(pSchluesselobjekt.schluessel()) == null)
        {
            lPosition = this.h(pSchluesselobjekt.schluessel());
            while (!this.leer(lPosition) && !this.geloescht(lPosition))
            { // lineares Sondieren
                lPosition++;
                lPosition %= zGroesse;
            }
            zFeld[lPosition] = pSchluesselobjekt;
            zAnzahl++;
            if (zAnzahl / 0.75 > zGroesse)
                this.rehash();
        }
    }
    
    /**
     * Ein Stringrepr&auml;sentation der Hashtabelle wird zur&uuml;ckgegeben
     * @return einen String, der in mehreren Zeilen die Hashtabelle darstellt
     */
    public String toString()
    {
        String lString;
        Schluesselobjekt lSchluesselobjekt;
        
        lString = "";
        for (int i = 0; i < zGroesse; i++)
            if (this.leer(i))
                lString += "leer\n";
            else if (this.geloescht(i))
                lString += "gel&ouml;scht\n";
            else
            {
                lSchluesselobjekt = (Schluesselobjekt)zFeld[i];
                lString += zFeld[i].toString() + " (" + lSchluesselobjekt.schluessel().hashCode() + " )\n";
            }
        return lString;
    }
    
    /**
     * Wenn die entsprechende Zelle leer ist, wird true zur&uuml;ckgegeben
     * @param pPosition die Position in der Hashtabelle, die untersucht wird
     * @return ob die entsprechende Zelle leer ist
     */
    private boolean leer(int pPosition)
    {
        return zFeld[pPosition] == null;
    }
    
    /**
     * Wenn die entsprechende Zelle gel&ouml;scht ist, wird true zur&uuml;ckgegeben
     * @param pPosition die Position in der Hashtabelle, die untersucht wird
     * @return ob die entsprechende Zelle gel&ouml;scht ist
     */
    private boolean geloescht(int pPosition)
    {
        return zFeld[pPosition].equals(hatGeloescht);
    }
    
    /**
     * Ein Schl&uuml;sselobjekt wird aus der Hashtabelle entfernt
     * @param pSchluessel der Schl&uuml;ssel, dessen Schl&uuml;sselobjekt entfernt werden soll
     */
    public void loesche(Object pSchluessel)
    {
        int lPosition;
        boolean lGefunden;
        Schluesselobjekt lSchluesselobjekt;
        
        lGefunden = false;
        lPosition = this.h(pSchluessel);
        while (!this.leer(lPosition) && !lGefunden)
        {
            if (!this.geloescht(lPosition))
            {
                lSchluesselobjekt = (Schluesselobjekt)zFeld[lPosition];
                lGefunden = lSchluesselobjekt.schluessel().equals(pSchluessel);
            }
            if (!lGefunden)
            { // lineares Sondieren
                lPosition++;
                lPosition %= zGroesse;
            }
        }
        if (lGefunden)
        {
            zFeld[lPosition] = hatGeloescht;
            zAnzahl--;
        }
    }
    
    /**
     * Ein gesuchtes Schl&uuml;sselobjekt wird zur&uuml;ckgegeben bzw.null, wenn es nicht vorhanden ist
     * @param pSchluessel der Schl&uuml;ssel, dessen zugeh&ouml;riges Schl&uuml;sselobjekt zur&uuml;ckgegeben werden soll
     * @return das gesuchte Schl&uuml;sselobjekt oder null, wenn es nicht vorhanden ist
     */
    public Typ suche(Object pSchluessel)
    {
        int lPosition;
        boolean lGefunden;
        Schluesselobjekt lSchluesselobjekt;
        
        lGefunden = false;
        lPosition = this.h(pSchluessel);
        while (!this.leer(lPosition) && !lGefunden)
        {
            if (!this.geloescht(lPosition))
            {
                lSchluesselobjekt = (Schluesselobjekt)zFeld[lPosition];
                lGefunden = lSchluesselobjekt.schluessel().equals(pSchluessel);
            }
            if (!lGefunden)
            { // lineares Sondieren
                lPosition++;
                lPosition %= zGroesse;
            }
        }
        if (lGefunden)
            return (Typ) zFeld[lPosition];
        else    
            return null;
    }

    /**
     * Die Hashtabelle wird in ihrer Gr&ouml;sse verdoppelt
     */
    private void rehash()
    {
        Object[] lAltesFeld;
        Schluesselobjekt lSchluesselobjekt;
        int lAlteGroesse;
        
        lAltesFeld = zFeld;
        lAlteGroesse = zGroesse;
        zGroesse = zGroesse * 2 + 1; // leider nicht immer prim
        zFeld = new Object[zGroesse];
        zAnzahl = 0;
        for (int i = 0; i < lAlteGroesse; i++)
        {
            if (lAltesFeld[i] != null && !lAltesFeld[i].equals(hatGeloescht))
            {
                lSchluesselobjekt = (Schluesselobjekt)lAltesFeld[i];
                this.fuegeEin(lSchluesselobjekt);
            }
        }
    }
    
    /**
     * Alle Schl&uuml;sselobjekte werden aus der Hashtabelle entfernt
     */
    public void entferneAlleElemente()
    {
        for (int i = 0; i < zGroesse; i++)
            zFeld[i] = null;
        zAnzahl = 0;
    }
}
