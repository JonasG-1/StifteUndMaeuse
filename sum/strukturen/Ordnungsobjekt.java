package sum.strukturen;

/**
Ein Ordnungsobjekt ist eine Interface f&uuml;r Objekte, die in Suchb&auml;ume
eingef&uuml;gt werden sollen.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */

public interface Ordnungsobjekt
{
	// Dienste
	/**
	 * Ist genau dann wahr, wenn das Ordnungsobjekt gr&ouml;&szlig;er als das Vergleichsobjekt pObjekt ist.
	 */
	public boolean istGroesserAls(Ordnungsobjekt pObjekt);
	
	/**
	 * Ist genau dann wahr, wenn das Ordnungsobjekt kleiner als das Vergleichsobjekt pObjekt ist.
	 */
	public boolean istKleinerAls(Ordnungsobjekt pObjekt);
	
	/**
	 * Ist genau dann wahr, wenn das Ordnungsobjekt gleich dem Vergleichsobjekt pObjekt ist.
	 */
	public boolean istGleichWie(Ordnungsobjekt pObjekt);	
}
