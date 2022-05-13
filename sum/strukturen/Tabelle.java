package sum.strukturen;

/**
 * Eine Tabelle ist ein nur teilweise gef&uuml;lltes Feld.<br>
 * Sie kann als Tabelle mit einem Inhaltstyp, der Ordnungsobjekt implementiert, deklariert werden. Wird dieser weggelassen, liefert die Anfrage inhalt ein Object und es wird Typkonversion n&ouml;tig.
 * @author Horst Hildebrecht
 * @version 7.5 vom 29.10.2013
 */
public class Tabelle<Typ extends Ordnungsobjekt> extends Feld<Typ>
{
	static final long serialVersionUID = 8311777061002L;
	
	// Bezugsobjekte

    // Attribute
    int zAnzahl, zMaximaleAnzahl, zErsterFreierPlatz;
    
    // Konstruktor
    /**
     * nachher: Die Tabelle ist initialisiert. Die aufzunehmenden Objekte haben die Nummern pUntereGrenze bis pObereGrenze (Indexbereich). Die Tabelle enth&auml;lt noch keine Objekte. Die Anzahl der gespeicherten Objekte ist also 0.
     */
    public Tabelle (int pUntereGrenze, int pObereGrenze)
    {
        super(pUntereGrenze, pObereGrenze);
        zAnzahl = 0;
        zMaximaleAnzahl = pObereGrenze-pUntereGrenze+1;
        zErsterFreierPlatz = pUntereGrenze;
    }

    // Dienste

    /**
     * vorher: Die Tabelle ist noch nicht voll.<br>
     * nachher: Das eingegebene Objekt pInhalt befindet sich in der Tabelle. Die Anzahl der gespeicherten Objekte wird um 1 erh&ouml;ht.
     */
    public void fuegeEin(Typ pInhalt)
    {
        zAnzahl++;
        this.setzeInhalt(zErsterFreierPlatz,pInhalt);
        zErsterFreierPlatz++;
    }
    
    /**
     * vorher: pIndex ist eine Zahl im Indexbereich der Tabelle.<br>
     * nachher: Das Objekt an der Stelle pIndex wurde aus der Tabelle gel&ouml;scht. Die Anzahl der gespeicherten Objekte wurde um 1 verkeinert.
     */
    public void loesche(int pIndex)
    {
        for (int lIndex = pIndex; lIndex < zErsterFreierPlatz - 1; lIndex++)
            this.setzeInhalt(lIndex, this.inhalt(lIndex+1));
        zAnzahl--;
        zErsterFreierPlatz--;
    }
  
    /**
     * nachher: Wenn sich ein Objekt mit dem identischen Suchschl&uml;ssel in der Tabelle befindet, dann liefert die Anfrage dessen Index im Indexbereich. Wenn es sich nicht in der Tabelle befindet, liefert die Anfrage eine Zahl au&szlig;erhalb des Indexbereichs. 
     */
    public int index(Typ pInhalt)
    {
        for (int lIndex = this.untereGrenze(); lIndex < zErsterFreierPlatz; lIndex++)
            if (this.inhalt(lIndex).istGleichWie(pInhalt))
                return lIndex;
        return this.untereGrenze() - 1;
    }
    
    /**
     * nachher: Die Anfrage liefert die Anzahl der Objekte in der Tabelle.
     */
    public int anzahl()
    {
        return zAnzahl;
    }
    
    /**
     * nachher: Die Anfrage liefert wahr, wenn kein weiteres Objekt eingef&uuml;gt werden kann.
     */
    public boolean voll()
    {
        return zAnzahl >= zMaximaleAnzahl;
    }
    
}