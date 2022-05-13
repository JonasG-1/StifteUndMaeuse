package sum.strukturen;

import java.io.*;

/**
 * Die Ordnungsklasse ist eine Oberklasse f&uuml;r Objekte mit einer Ordnung.
 * @author Horst Hildebrecht
 * @version 7.5 vom 29.10.2013
 */
public abstract class Ordnungsklasse implements Serializable
{
	/**
	 * Ist genau dann wahr, wenn dieses Exemplar der Ordnungsklasse gleich dem Vergleichsobjekt pObjekt ist.
	 */
    public abstract boolean istGleich(Ordnungsklasse pObjekt);
    
	/**
	 * Ist genau dann wahr, wenn dieses Exemplar der Ordnungsklasse kleiner als das Vergleichsobjekt pObjekt ist.
	 */
    public abstract boolean istKleiner(Ordnungsklasse pObjekt);
      
	/**
	 * Ist genau dann wahr, wenn dieses Exemplar der Ordnungsklasse gr&ouml;&szlig; als das Vergleichsobjekt pObjekt ist.
	 */
    public boolean istGroesser(Ordnungsklasse pObjekt)
    {
        return !(this.istGleich(pObjekt) || this.istKleiner(pObjekt));
    }
    
}
