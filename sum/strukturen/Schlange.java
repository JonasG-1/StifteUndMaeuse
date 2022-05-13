package sum.strukturen; 

import java.io.*;
 
/**
Die Schlange ist eine FIFO-Struktur.<br>
Sie kann als Schlange mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage kopf bzw. erstes ein Object und es wird Typkonversion n&ouml;tig.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
*/
public class Schlange<Typ> extends Liste<Typ> implements Serializable
{
	static final long serialVersionUID = 8311777060202L;
	
    // Konstruktor
    /**
     * Eine neue leere Schlange wird erzeugt.
     */
    public Schlange()
    {
        super();
    }

    // Dienste
    /**
     * Das erste Objekt der Schlange wird zur&uuml;ckgegeben.
     * @return das erste Objekt der Schlange
     */
    public Typ erstes()
    {
        this.zumAnfang();
        return this.aktuellesElement();
    }
    
    /**
     * Das erste Objekt der Schlange wird zur&uuml;ckgegeben.
     * @return das erste Objekt der Schlange
     */
    public Typ kopf()
    {
        return this.erstes();
    }

    /**
     * Ein neues Objekt wird an das Ende der Schlange angeh&auml;ngt.
     * @param pInhalt der neue Inhalt (Objekt)
     */
    public void haengeAn(Typ pInhalt)
    {
        this.zumEnde();
        this.fuegeDahinterEin(pInhalt);
    }
    
    /**
     * Das erste Element der Schlange wird entfernt.
     */
    public void entferneErstes()
    {
        this.zumAnfang();
        this.loescheAktuelles();
    }
    
    /**
     * Das erste Element der Schlange wird entfernt.
     */
    public void entferneKopf()
    {
        this.entferneErstes();
    }
}
