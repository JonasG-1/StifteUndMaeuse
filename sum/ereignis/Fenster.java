package sum.ereignis;

/**
Die Klasse Fenster dient zur Erstellung von zus&auml;tzlichen Fenstern
zum SuM-Fenster. Falls ein Stift in diesem Fenster zeichnen soll, muss
das Fenster im Konstruktor als Parameter &uuml;bergeben werden. Das gilt 
auch f&uuml;r Komponenten des Fensters. Jedes Fenster besitzt einen Namen,
der in der Titelleiste angezeigt wird. Falls keion Fenstername angegeben wird,
heisst das Fenster "SuM-Fenster".
Das Fenster kann mit Doppelpufferung versehen werden. Das bedeutet,
dass statt auf das Fenster in ein verstecktes Bild gezeichnet wird. Dieses
Bild kann mit dem Auftrag zeichneDich() im Fenster angezeigt werden.

@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Fenster extends Bildschirm
{
    /**
     * Ein neues Fenster mit dem Namen SuM-Fenster wurde erzeugt.
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     */
    public Fenster()
    {
        super(0, 0, -1, -1, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
     * Ein neues Fenster wurde erzeugt. Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pName der Name des Fensters (in der Titelleiste)
     */
    public Fenster(String pName)
    {
        super(0, 0, -1, -1, pName, false);
    }

    /**
     * Ein neues Fenster wurde erzeugt. Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(boolean pMitDoubleBuffering)
    {
        super(0, 0, -1, -1, "SuM-Fenster " + (zFensternummer + 1), pMitDoubleBuffering);
    }

    /**
     * Ein neues Fenster wurde erzeugt. Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pName der Name des Fensters (in der Titelleiste)
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(String pName, boolean pMitDoubleBuffering)
    {
        super(0, 0, -1, -1, pName, pMitDoubleBuffering);
    }

    /**
     * Ein neues Fenster mit festgelegter Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     */
    public Fenster(int pBreite, int pHoehe)
    {
        super(0, 0, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
     * Ein neues Fenster mit festgelegter Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pName der Name des Fensters (in der Titelleiste)
     */
    public Fenster(int pBreite, int pHoehe, String pName)
    {
        super(0, 0, pBreite, pHoehe, pName, false);
    }

    /**
     * Ein neues Fenster mit festgelegter Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     * @param pName der Name des Fensters (in der Titelleiste)
     */
    public Fenster(int pBreite, int pHoehe, String pName, boolean pMitDoubleBuffering)
    {
        super(0, 0, pBreite, pHoehe, pName, pMitDoubleBuffering);
    }

    /**
     * Ein neues Fenster mit festgelegter Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(int pBreite, int pHoehe, boolean pMitDoubleBuffering)
    {
        super(0, 0, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), pMitDoubleBuffering);
    }

    /**
     * Ein neues Fenster mit festgelegter Position, Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pLinks der Abstand des Fensters vom linken Bildschirmrand
     * @param pOben der Abstand des Fensters vom oberen Bildschirmrand
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pName der Name des Fensters (in der Titelleiste)
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(int pLinks, int pOben, int pBreite, int pHoehe, String pName)
    {
        super(pLinks, pOben, pBreite, pHoehe, pName, false);
    }

    /**
     * Ein neues Fenster mit festgelegter Position, Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pLinks der Abstand des Fensters vom linken Bildschirmrand
     * @param pOben der Abstand des Fensters vom oberen Bildschirmrand
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     */
    public Fenster(int pLinks, int pOben, int pBreite, int pHoehe)
    {
        super(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    /**
     * Ein neues Fenster mit festgelegter Position, Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pLinks der Abstand des Fensters vom linken Bildschirmrand
     * @param pOben der Abstand des Fensters vom oberen Bildschirmrand
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pName der Name des Fensters (in der Titelleiste)
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(int pLinks, int pOben, int pBreite, int pHoehe, String pName, boolean pMitDoubleBuffering)
    {
        super(pLinks, pOben, pBreite, pHoehe, pName, false);
    }

    /**
     * Ein neues Fenster mit festgelegter Position, Breite und H&ouml;he wurde erzeugt. 
     * Das Fenster liegt &uuml;ber den bisher
     * erzeugten Fenstern und hat eine maximale Gr&ouml;sse.
     * @param pLinks der Abstand des Fensters vom linken Bildschirmrand
     * @param pOben der Abstand des Fensters vom oberen Bildschirmrand
     * @param pBreite die Breite des Fensters
     * @param pHoehe die H&ouml;he des Fensters
     * @param pMitDoubleBuffering soll der Bildschim gepuffert werden
     */
    public Fenster(int pLinks, int pOben, int pBreite, int pHoehe, boolean pMitDoubleBuffering)
    {
        super(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster " + (zFensternummer + 1), false);
    }

    // Dienste
}
