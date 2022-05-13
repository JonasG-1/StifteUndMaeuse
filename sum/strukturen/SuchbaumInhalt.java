package sum.strukturen;

/**
 * Ein SuchbaumInhalt ist eine Klasse mit einer Ordnung, die au&szlig;erdem z&auml;hlen kann, wie oft ein Exemplar mit diesem Schl&uuml;ssel eingef&uuml;gt wurde.
 * @author Horst Hildebrecht
 * @version 7.5 vom 29.10.2013
 */
public abstract class SuchbaumInhalt extends Ordnungsklasse
{
        int zAnzahl;
        
        /**
         * Der SuchbaumInhalt hat die Anzahl 1.
         */
        public SuchbaumInhalt()
        {
            zAnzahl = 1;
        }
        
        /**
         * Der SuchbaumInhalt hat die angegebene Anzahl.
         */
        public SuchbaumInhalt(int pAnzahl)
        {
            zAnzahl = pAnzahl;
        }
        
        /**
         * Die Anzahl des SuchbaumInhalts wird geliefert.
         */
        public int anzahl()
        {
            return zAnzahl;
        }
        
        /**
         * Der SuchbaumInhalt hat die angegebene Anzahl.
         */
        public void setzeAnzahl(int pAnzahl)
        {
            zAnzahl = pAnzahl;
        }
        
        /**
         * Die Anzahl des SuchbaumInhalts wurde inkrementiert.
         */
         public void erhoeheAnzahl()
        {
            zAnzahl++;
        }
        
  }

