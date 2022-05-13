package sum.strukturen;

/**
 * Ein Feld verallgemeinert ein Array so, dass es beliebige Grenzen hat.<br>
 * Es kann als Feld mit einem Inhaltstyp deklariert werden. Wird dieser weggelassen, liefert die Anfrage inhalt ein Object und es wird Typkonversion n&ouml;tig.
 * @author Horst Hildebrecht
 * @version 7.5 vom 29.10.2013
 */
public class Feld<Typ>
{
	static final long serialVersionUID = 8311777060902L;
	
	// Bezugsobjekte
    Object[] hatArray;
    
    // Attribute
    int zUntereGrenze, zObereGrenze;
    
    // Konstruktor
    /**
     * nachher: Das Feld der angegebenen Gr&ouml;&szlig;e wurde erzeugt. Es hat noch keinen Inhalt.
     */
    public Feld(int pUntereGrenze, int pObereGrenze)
    {
        hatArray = new Object[pObereGrenze-pUntereGrenze+1];
        zUntereGrenze = pUntereGrenze;
        zObereGrenze = pObereGrenze;
    }

    // Dienste

    /**
     * nachher: Als Inhalt des Feldes an der angegebenen Position wurde das angegebene Objekt gesetzt.
     */
    public void setzeInhalt(int pPosition, Typ pInhalt)
    {
        hatArray[pPosition - zUntereGrenze] = pInhalt;
    }
    
    /**
     * nachher: Der Inhalt des Feldes an der angegebenen Position wurde geliefert.
     */
    public Typ inhalt(int pPosition)
    {
        return (Typ) hatArray[pPosition - zUntereGrenze];
    }
    
    /**
     * nachher: Die untere Grenze des Indexbereichs wurde geliefert.
     */
    public int untereGrenze()
    {
        return zUntereGrenze;
    }
    
    /**
     * nachher: Die obere Grenze des Indexbereichs wurde geliefert.
     */
    public int obereGrenze()
    {
        return zObereGrenze;
    }
    
}
