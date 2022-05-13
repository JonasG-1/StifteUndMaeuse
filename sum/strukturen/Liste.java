package sum.strukturen;

import sum.werkzeuge.*;
import java.io.*;

/**
 Die Klasse Liste ist die Realisierung einer doppelt verketteten
 Liste mit zwei Sentinels (Bug und Heck) am Anfang und am Ende der Liste.<br>
 Sie kann als Liste mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage aktuelles ein Object und es wird Typkonversion n&ouml;tig.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
*/
public class Liste<Typ> implements Serializable
{

	static final long serialVersionUID = 8311777060102L;
	
    // innere Klasse ------------------------
    private class Knoten<Typ> implements Serializable
    {
        static final long serialVersionUID = 8311777060002L;
        	
        // Bezugsobjekte
        private Typ kenntInhalt;
        private Knoten kenntNachfolger;
        private Knoten kenntVorgaenger;
        
        // Konstruktor
        public Knoten(Typ pInhalt)
        {
            kenntInhalt = pInhalt;
            kenntNachfolger = null;
            kenntVorgaenger = null;
        }
        
        // Dienste
        public Typ inhalt()
        {
            return kenntInhalt;
        }
        
        public void setzeInhalt(Typ pObject)
        {
            kenntInhalt = pObject;
        }
        
        public Knoten nachfolger()
        {
            return kenntNachfolger;
        }
        
        public Knoten vorgaenger()
        {
            return kenntVorgaenger;
        }
        
        public void setzeNachfolger(Knoten pNachfolger)
        {
            kenntNachfolger = pNachfolger;
        }

        public void setzeVorgaenger(Knoten pVorgaenger)
        {
            kenntVorgaenger = pVorgaenger;
        }
    }
    // Ende der inneren Klasse ----------------------------
    
    // Bezugsobjekte
    /**
     * Zur Unterstuetzung der Ausgabe als String
     */
    private Textwerkzeug hatTextwerkzeug;
    
    /**
     * Der erste Knoten (zaehlt nicht zur Liste)
     */
    private Knoten<Typ> hatBug;
    
    /**
     * Der letzte Knoten (zaehlt nicht zur Liste)
     */
    private Knoten<Typ> hatHeck;
    
    /**
     * Der aktuelle Knoten
     */
    private Knoten<Typ> kenntAktuell;
    
    // Attribute
    /**
     * Die Anzahl der Listenelemente
     */
    private int zAnzahl;

    /**
     * Die Position des aktuellen Knotens
     */
    private int zPosition;

    // Konstruktor
    /**
     * Eine neue leere Liste wird erzeugt.
     */
    public Liste()
    {
        hatBug = new Knoten(null);
        hatHeck = new Knoten(null);
        hatBug.setzeNachfolger(hatHeck);
        hatHeck.setzeVorgaenger(hatBug);
        kenntAktuell = hatBug;
        zAnzahl = 0;
        zPosition = 0;
        hatTextwerkzeug = new Textwerkzeug();
    }

    // Dienste
    /**
     * Die aktuelle Position wird auf den Listenanfang gesetzt.
     */
    public void zumAnfang()
    {
        kenntAktuell = hatBug.nachfolger();
        zPosition = 1;
    }

    /**
     * Die aktuelle Position wird auf das Listenende gesetzt.
     */
    public void zumEnde()
    {
        kenntAktuell = hatHeck.vorgaenger();
        zPosition = zAnzahl;
    }

    /**
     * Die aktuelle Position wird vor gesetzt.
     */
    public void vor()
    {
        if (!this.istDahinter())
        {
            kenntAktuell = kenntAktuell.nachfolger();
            zPosition++;
        }
    }

    /**
     * Die aktuelle Position wird zur&uuml;ck gesetzt.
     */
    public void zurueck()
    {
        if (!this.istDavor())
        {
            kenntAktuell = kenntAktuell.vorgaenger();
            zPosition--;
        }
    }

    /**
     * Wenn die aktuelle Position hinter der Liste ist, wird true zur&uuml;ckgegeben.
     * @return true, wenn die aktuelle Position hinter der Liste ist.
     */
    public boolean istDahinter()
    {
        return kenntAktuell == hatHeck;
    }

    /**
     * Wenn die aktuelle Position vor der Liste ist, wird true zur&uuml;ckgegeben.
     * @return true, wenn die aktuelle Position vor der Liste ist.
     */
    public boolean istDavor()
    {
        return kenntAktuell == hatBug;
    }

    /**
     * Wenn die aktuelle Position das erste Element der Liste ist, wird true zur&uuml;ckgegeben.
     * @return true, wenn die aktuelle Position das erste Element der Liste ist.
     */
  	public boolean istAmAnfang()
  	{
  		return !this.istLeer() && kenntAktuell == hatBug.nachfolger();
  	}

    /**
     * Wenn die aktuelle Position das letzte Element der Liste ist, wird true zur&uuml;ckgegeben.
     * @return true, wenn die aktuelle Position das letzte Element der Liste ist.
     */
   	public boolean istAmEnde()
  	{
  		return !this.istLeer() && kenntAktuell == hatHeck.vorgaenger();
  	}

    /**
     * Das Objekt an der aktuellen Position in der Liste wird zur&uuml;ckgegeben.
     * @return das aktuelle Objekt der Liste
     */
    public Typ aktuellesElement()
    {
        return kenntAktuell.inhalt();
    }

    /**
     * Das Objekt an der aktuellen Position in der Liste wird zur&uuml;ckgegeben.
     * @return das aktuelle Objekt der Liste
     */
    public Typ aktuelles()
    {
        return kenntAktuell.inhalt();
    }

    /**
     * Ein neues Objekt wird hinter der aktuellen Position in die Liste eingef&uuml;gt.
     * Die aktuelle Position bleibt unver&auml;ndert.
     * @param pInhalt der neue Inhalt (Objekt)
     */
    public void fuegeDahinterEin(Typ pInhalt)
    {
        Knoten lNeuer;
        
        if (this.istDahinter())
            this.zurueck();
        lNeuer = new Knoten(pInhalt);
        lNeuer.setzeNachfolger(kenntAktuell.nachfolger());
        lNeuer.nachfolger().setzeVorgaenger(lNeuer);
        lNeuer.setzeVorgaenger(kenntAktuell);
        kenntAktuell.setzeNachfolger(lNeuer);
        zAnzahl++;
    }
    
    /**
     * Ein neues Objekt wird vor der aktuellen Position in die Liste eingef&uuml;gt.
     * Die aktuelle Position bleibt unver&auml;ndert.
     * @param pInhalt der neue Inhalt (Objekt)
     */
    public void fuegeDavorEin(Typ pInhalt)
    {
        Knoten lNeuer;
        
        if (this.istDavor())
            this.vor();
        lNeuer = new Knoten(pInhalt);
        lNeuer.setzeVorgaenger(kenntAktuell.vorgaenger());
        lNeuer.vorgaenger().setzeNachfolger(lNeuer);
        lNeuer.setzeNachfolger(kenntAktuell);
        kenntAktuell.setzeVorgaenger(lNeuer);
        zAnzahl++;
        zPosition++;
    }
    
    /**
     * Die L&auml;nge der Liste wird zur&uuml;ckgegeben.
     * @return die Anzahl der Elemente in der Liste
     */
    public int laenge()
    {
        return zAnzahl;
    }

    /**
     * Es wird zur&uuml;ckgegeben, ob die Liste leer ist.
     * @return true, wenn die Liste leer ist
     */
    public boolean istLeer()
    {
        return zAnzahl == 0;
    }
    
     /**
     * Alle Listenelemente werden aus der Liste entfernt. Die Liste ist danach leer.
     */
    public void entferneAlleElemente()
    {
        hatBug.setzeNachfolger(hatHeck);
        hatHeck.setzeVorgaenger(hatBug);
        kenntAktuell = hatBug;
        zAnzahl = 0;
        zPosition = 0;
    }

    /**
     * Die aktuelle Position wird zu einer bestimmten Position bewegt.
     */
    public void geheZuPosition(int pPosition)
    {
        if (pPosition >= 0 && pPosition <= zAnzahl + 1)
        {
            while (pPosition > zPosition)
                this.vor();
            while (pPosition < zPosition)
                this.zurueck();
        }
    }
        
    /**
     * Das Position des aktuellen Knotens wird zur&uuml;ckgegeben.
     * @return die Position des aktuellen Knotens in der Liste
     */
    public int aktuellePosition()
    {
        return zPosition;
    }
        
    /**
     * Das aktuelle Listenelement wird gel&ouml;scht.
     */
    public void loescheAktuelles()
    {
        this.entferneAktuelles();
    }
    
    /**
     * Das aktuelle Listenelement wird gel&ouml;scht.
     */
    public void entferneAktuelles()
    {
        if (!this.istDavor() || !this.istDahinter())
        {
            kenntAktuell.vorgaenger().setzeNachfolger(kenntAktuell.nachfolger());
            kenntAktuell.nachfolger().setzeVorgaenger(kenntAktuell.vorgaenger());
            kenntAktuell = kenntAktuell.nachfolger();
            zAnzahl--;
        }
    }
    
     /**
     * Das aktuelle Listenelement wird ersetzt.
     * @param pObject der neue Inhalt des aktuellen Listenelements
     */
    public void ersetzeAktuelles(Typ pObject)
    {
        if (!this.istDavor() || !this.istDahinter())
        {
            kenntAktuell.setzeInhalt(pObject);
        }
    }
    
    /**
     * Die Position eines Objekts in der Liste wird ermittelt. Falls das Objekt nicht in der Liste
     * enthalten ist, wird -1 zur&uuml;ckgegeben. Falls das Objekt mehrfach in der Liste enthalten
     * ist, wird die Position des ersten Auftretens zur&uuml;ckgegeben.
     * @param pObject das Objekt, dessen Position in der Liste ermittelt wird.
     * @return die Position des Objekts in der Liste
     */
    public int position(Typ pObject)
    {
        int lPosition;
        
        lPosition = -1;
        if (!this.istLeer())
        {
            this.zumAnfang();
            do
            {
                if (this.aktuellesElement() == pObject)
                    lPosition = zPosition;
                else
                    this.vor();
            } while (!this.istDahinter() && lPosition == -1);
        }
        return lPosition;
    }
    
    /**
     * Eine weitere Liste wird an die Liste angeh&auml;ngt.
     * @param pListe die Liste, die an diese Liste angeh&auml;ngt wird.
     */
    public void haengeListeAn(Liste pListe)
    {
        if (!pListe.istLeer())
        {
            this.zumEnde();
            kenntAktuell.setzeNachfolger(pListe.ersterKnoten());
            pListe.ersterKnoten().setzeVorgaenger(kenntAktuell);
            hatHeck = pListe.letzterKnoten().nachfolger();
            zAnzahl += pListe.laenge();
        }
    }
    
    /**
     * Eine weitere Liste wird vor die Liste angeh&auml;ngt.
     * @param pListe die Liste, die vor diese Liste angeh&auml;ngt wird.
     */
    public void setzeListeDavor(Liste pListe)
    {
        if (!pListe.istLeer())
        {
            this.zumAnfang();
            kenntAktuell.setzeVorgaenger(pListe.letzterKnoten());
            pListe.letzterKnoten().setzeNachfolger(kenntAktuell);
            hatBug = pListe.ersterKnoten().vorgaenger();
            zAnzahl += pListe.laenge();
        }
    }
    
    /**
     * interner Dienst zur Unterst&uuml;tzung von haengeListeAn
     */
    protected Knoten ersterKnoten()
    {
        return hatBug.nachfolger();
    }
    
    /**
     * interner Dienst zur Unterst&uuml;tzung von haengeListeAn
     */
    protected Knoten letzterKnoten()
    {
        return hatHeck.vorgaenger();
    }
           
    /**
     * Der Inhalt der Liste wird als String zur&uuml;ckgegeben.
     * @return der Inhalt der Liste als String
     */
    public String toString()
    {
        String lString;
        int lPosition;
        
        if (this.istLeer())
            return "leere Liste";
        else
        {
            lPosition = this.aktuellePosition();
            lString = "";
            this.zumAnfang();
            while (!this.istDahinter())
            {
                lString += this.aktuellesElement().toString() + "\n";
                this.vor();
            }
            if (hatTextwerkzeug.laenge(lString) > 0)
                lString = hatTextwerkzeug.textOhne(lString, hatTextwerkzeug.laenge(lString), hatTextwerkzeug.laenge(lString));
            this.geheZuPosition(lPosition);
            return lString;
        }
    }
    
     /**
     * Ein neues Objekt wird vor den Anfang der Liste eingef&uuml;gt.
     * @param pInhalt der neue Inhalt (Objekt)
     */
    public void setzeDavor(Typ pInhalt)
    {
        this.zumAnfang();
        this.fuegeDavorEin(pInhalt);
    }
    
     /**
     * Ein neues Objekt wird an das Ende der Liste angeh&auml;ngt.
     * @param pInhalt der neue Inhalt (Objekt)
     */
    public void haengeAn(Typ pInhalt)
    {
        this.zumEnde();
        this.fuegeDahinterEin(pInhalt);
    }
    

}
