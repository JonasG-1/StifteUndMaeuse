package sum.strukturen;

import java.io.*;

/**
Ein Stapel ist die Realisierung eines Stacks. (LIFO)<br>
Er kann als Stapel mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage oberstes bzw. spitze ein Object und es wird Typkonversion n&ouml;tig.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Stapel<Typ> extends Liste<Typ> implements Serializable
{
	static final long serialVersionUID = 8311777060502L;
    // Bezugsobjekte

    // Attribute

    // Konstruktor
    /**
     *Ein neuer leerer Stapel wird erzeugt
     */
    public Stapel()
    {
        super();
    }

    // Dienste
    /**
     * Ein neues Objekt wird auf den Stapel gelegt
     * @param pObject das Objekt, das auf den Stapel gelegt wird
     */
    public void legeAuf(Typ pObject)
    {
        this.zumAnfang();
        this.fuegeDavorEin(pObject);
    }
    
    /**
     * Das oberste Objekt wird vom Stapel entfernt
     */
    public void nimmAb()
    {
        this.zumAnfang();
        this.loescheAktuelles();
    }
    
    /**
     * Das oberste Objekt wird vom Stapel entfernt
     */
    public void entferneOberstes()
    {
        this.zumAnfang();
        this.loescheAktuelles();
    }
    
    /**
     * Das oberste Stapelelement wird zur&uuml;ckgegeben
     * @return das oberste Objekt auf dem Stapel
     */
    public Typ oberstes()
    {
        this.zumAnfang();
        return this.aktuellesElement();
    }

     /**
     * Das oberste Stapelelement wird zur&uuml;ckgegeben
     * @return das oberste Objekt auf dem Stapel
     */
    public Typ spitze()
    {
        this.zumAnfang();
        return this.aktuellesElement();
    }

}
