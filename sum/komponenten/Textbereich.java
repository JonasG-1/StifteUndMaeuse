package sum.komponenten;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Component;
import java.io.*;
import sum.ereignis.*;

/**
Ein Textbereich ist die Klasse fuer Java-TextAreas. Textbereiche
sind Editoren mit einer festen Breite und Hoehe. Sie reagieren auf Texteingaben
und Mausklicks. Wenn etwas in einen Textbereich getippt werden soll, muss es
vorher mit einem Mausklick aktiviert werden, d.h. der Textbereich muss den
Fokus haben.
Es gib zwei Unterklassen von Textbereich: Zeichenbereich und Zeilenbereich.
Der Textbereich ist zeichenorientiert, der Zeilenbereich ist zeilenorientiert.
Der Textbereich ist abstrakt wird normalerweise nicht benutzt.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
*/
public abstract class Textbereich extends Markierungskomponente implements Serializable, ScrollPaneConstants
{
	protected JScrollPane hatScrollPane;

/**
 Der Textbereich wird erzeugt und enthaelt den uebergebenen Text. Position, Breite
 und Hoehe werden als Parameter uebergeben.
 Der Textbereich befindet sich auf dem Bildschirm.
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
*/
	public Textbereich(double pLinks, double pOben, double pBreite, double pHoehe)
	{
		hatScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
		hatScrollPane.setSize(new Dimension(10, 10));
		Bildschirm.topFenster.privatPanel().add(hatScrollPane, 0);
	}
	
/**
 Der Textbereich wird erzeugt und enthaelt den uebergebenen Text. Position, Breite
 und Hoehe werden als Parameter uebergeben.
 Der Textbereich befindet sich auf dem Fenster.
 @param pFenster das Fenster, das die Komponente enth&auml;lt
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
*/
	public Textbereich(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe)
	{
		hatScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
		hatScrollPane.setSize(new Dimension(10, 10));
		pFenster.privatPanel().add(hatScrollPane, 0);
	}
	
/**
 Die Markierungskomponente kennt die eigentliche TextComponent aus der Java-Bibliothek.
*/
	protected void lerneKomponenteKennen(Bildschirm pFenster, JComponent pKomponente)
	{
		hatScrollPane.setViewportView(pKomponente);
		super.lerneKomponenteKennen(pFenster, pKomponente);
	}
	
/**
 Die Komponente erhaelt eine neue Groesse.
 @param pBreite die neue Breite der Komponente
 @param pHoehe die neue Hoehe der Komponente
*/
	public void setzeGroesse(double pBreite, double pHoehe)
	{
		super.setzeGroesse(pBreite, pHoehe);
		hatScrollPane.setSize((int)pBreite, (int)pHoehe);
		hatScrollPane.revalidate();		
	}
	
/**
 Die Komponente erhaelt eine neue Position (Ecke oben links).
 @param pWohinH die neue horizontale Position der linken oberen Ecke
 @param pWohinV die neue vertikale Position der linken oberen Ecke
*/
	public void setzePosition(double pWohinH, double pWohinV)
	{
		hatScrollPane.setLocation((int)pWohinH, (int)pWohinV);
	}
		
/**
 Der Text pText wird an Position pStelle eingefuegt.
 @param pText der Text, der eingefuegt wird
 @param pStelle die Stelle, wo der Text eingefuegt wird
*/
	public abstract void fuegeEin(String pText, int pStelle);
	
/**
 Der Text pText wird am Ende angehaengt.
 @param pText der Text, der ans Ende angehaengt wird
*/
	public abstract void haengeAn(String pText);
	
/**
 Das Zeichen pZeichen wird am Ende angehaengt.
 @param pZeichen das Zeichen, das ans Ende angehaengt wird
*/
	public abstract void haengeAn(char pZeichen);
	
/**
 Das ganze Zahl pZahl wird am Ende angehaengt.
 @param pZahl die Zahl, die ans Ende angehaengt wird
*/
	public abstract void haengeAn(int pZahl);
	
/**
 Das Kommazahl pZahl wird am Ende angehaengt.
 @param pZahl die Zahl, die ans Ende angehaengt wird
*/
	public abstract void haengeAn(double pZahl);
	
}
