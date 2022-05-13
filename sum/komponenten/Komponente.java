package sum.komponenten;

import javax.swing.*;
import java.awt.Color;
import sum.ereignis.*;

/**
 Die Klasse Komponente ist eine abstrakte Oberklasse fuer alle Komponenten der SuM-Komponentenbibliothek. 
 Jede einer solchen Komponente hat eine Position, die durch die linke obere Ecke der Komponente angegeben ist. 
 Neben der Groesse kann auch die Sichtbarkeit und die Aktivitaet gesteuert werden. 
 Mit Hilfe der Tabulatortaste und der Maus kann der Fokus auf die jeweilige Komponente gesetzt werden.
 @author Horst Hildebrecht, Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public abstract class Komponente
{
	private String zFokusErhaltenBearbeiter = "";
	private String zFokusVerlorenBearbeiter = "";
	private boolean zHatFokus = false;

	protected JComponent hatComponent;
	protected Bildschirm kenntFenster;

	public Komponente()
	{
	}

	/**
	 Position, Breite und Hoehe der Komponente werden festgelegt.
	 */
	protected void init(double pLinks, double pOben, double pBreite,
			double pHoehe)
	{
		this.setzePosition(pLinks, pOben);
		this.setzeGroesse(pBreite, pHoehe);
		this.setzeFarbe(kenntFenster.hintergrundfarbe());
	}

	/**
	 Die Komponente kennt die eigentliche Component aus der Java-Bibliothek.
	 */
	protected void lerneKomponenteKennen(Bildschirm pFenster,
			JComponent pKomponente)
	{
		hatComponent = pKomponente;
		kenntFenster = pFenster;
	}

	/**
	 Die Komponente merkt sich, ob sie den Fokus hat.
	 */
	protected void setzeFokusWert(boolean pFokus)
	{
		zHatFokus = pFokus;
	}

	/**
	 Die Methode zur Bearbeitung des FokusVerloren-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn die Komponente den Fokus verloren hat.
	 */
	public void setzeBearbeiterFokusVerloren(String pBearbeiter)
	{
		zFokusVerlorenBearbeiter = pBearbeiter;
	}

	/**
	 Die Methode zur Bearbeitung des FokusErhalten-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn die Komponente den Fokus erhalten hat.
	 */
	public void setzeBearbeiterFokusErhalten(String pBearbeiter)
	{
		zFokusErhaltenBearbeiter = pBearbeiter;
	}

	/**
	 Der Name der Methode zur Bearbeitung des FokusVerloren-Ereignisses in
	 der Ereignisanwendung wird geliefert.
	 */
	protected String fokusVerlorenBearbeiter()
	{
		return zFokusVerlorenBearbeiter;
	}

	/**
	 Der Name der Methode zur Bearbeitung des FokusErhalten-Ereignisses in
	 der Ereignisanwendung wird geliefert.
	 */
	protected String fokusErhaltenBearbeiter()
	{
		return zFokusErhaltenBearbeiter;
	}

	/**
	 Die Komponente erhaelt eine neue Position (Ecke oben links).
	 @param pWohinH die neue horizontale Position der linken oberen Ecke
	 @param pWohinV die neue vertikale Position der linken oberen Ecke
	 */
	public void setzePosition(double pWohinH, double pWohinV)
	{
		hatComponent.setLocation((int) pWohinH, (int) pWohinV);
	}

	/**
	Die Komponente erhaelt eine neue Groesse.
	@param pBreite die neue Breite der Komponente
	@param pHoehe die neue Hoehe der Komponente
	 */
	public void setzeGroesse(double pBreite, double pHoehe)
	{
		hatComponent.setSize((int) pBreite, (int) pHoehe);
	}

	/**
	 Die Hintergrundfarbe der Komponente wird geaendert.
	 @param pFarbe die neue Hintergrundfarbe der Komponente
	 */
	public void setzeFarbe(Color pFarbe)
	{
		hatComponent.setBackground(pFarbe);
		hatComponent.repaint();
	}

	/**
	Die Hintergrundfarbe der Komponente wird veraendert
	@param pFarbe die neue Hintergrundfarbe der Komponente
	 */
	public void setzeFarbe(int pFarbe)
	{
		if (pFarbe < 0)
			pFarbe = 0;
		pFarbe %= 13;
		switch (pFarbe)
		{
			case 0:
				this.setzeFarbe(Color.black);
				break;
			case 1:
				this.setzeFarbe(Color.blue);
				break;
			case 2:
				this.setzeFarbe(Color.cyan);
				break;
			case 3:
				this.setzeFarbe(Color.darkGray);
				break;
			case 4:
				this.setzeFarbe(Color.gray);
				break;
			case 5:
				this.setzeFarbe(Color.green);
				break;
			case 6:
				this.setzeFarbe(Color.lightGray);
				break;
			case 7:
				this.setzeFarbe(Color.magenta);
				break;
			case 8:
				this.setzeFarbe(Color.orange);
				break;
			case 9:
				this.setzeFarbe(Color.pink);
				break;
			case 10:
				this.setzeFarbe(Color.red);
				break;
			case 11:
				this.setzeFarbe(Color.white);
				break;
			case 12:
				this.setzeFarbe(Color.yellow);
				break;
		}
		hatComponent.repaint();
	}

	/**
	 Die Hintergrundfarbe der Komponente wird zurueckgegeben.
	 @return die Hintergrundfarbe der Komponente
	 */
	public Color farbe()
	{
		return hatComponent.getBackground();
	}

	/**
	 Der linke Rand der Komponente wird zurueckgegeben.
	 @return der Abstand der Komponente vom linken Bildschirmrand
	 */
	public int links()
	{
		return hatComponent.getLocation().x;
	}

	/**
	Der obere Rand der Komponente wird zurueckgegeben.
	@return der Abstand der Komponente vom oberen Bildschirmrand
	 */
	public int oben()
	{
		return hatComponent.getLocation().y;
	}

	/**
	 Die Breite der Komponente wird zurueckgegeben.
	 @return die Breite der Komponente
	 */
	public int breite()
	{
		return hatComponent.getSize().width;
	}

	/**
	 Die Hoehe der Komponente wird zurueckgegeben.
	 @return die Hoehe der Komponente
	 */
	public int hoehe()
	{
		return hatComponent.getSize().height;
	}

	/**
	Die Komponente wird unsichtbar, existiert aber weiter.
	 */
	public void verstecke()
	{
		hatComponent.setVisible(false);
	}

	/**
	Die Komponente wird angezeigt.
	 */
	public void zeige()
	{
		hatComponent.setVisible(true);
	}

	/**
	Es wird zurueckgegeben, ob die Komponente sichtbar ist.
	@return true, wenn die Komponente sichtbar ist
	 */
	public boolean istSichtbar()
	{
		return hatComponent.isVisible();
	}

	/**
	Die Komponente wird deaktiviert und reagiert nicht mehr.
	 */
	public void deaktiviere()
	{
		hatComponent.setEnabled(false);
	}

	/**
	Die Komponente wird aktiviert.
	 */
	public void aktiviere()
	{
		hatComponent.setEnabled(true);
	}

	/**
	Es wird zurueckgegeben, ob die Komponente aktiviert ist.
	@return true, wenn die Komponente aktiv ist
	 */
	public boolean istAktiv()
	{
		return hatComponent.isEnabled();
	}

	/**
	Liefert die Information, ob die Komponente den Fokus besitzt.
	@return true, wenn die Komponente den Fokus besitzt
	 */
	public boolean besitztFokus()
	{
		return zHatFokus;
	}

	/**
	Die Komponente erhaelt den Fokus.
	 */
	public void setzeFokus()
	{
		hatComponent.requestFocus();
	}

	/**
	Die Komponente erhaelt einen Hinweis (Tooltip, Hint), der angezeigt wird,
	wenn sich die Maus ueber der Komponente befindet.
	@param pText der Text des Tooltips
	 */
	public void setzeHinweis(String pText)
	{
		hatComponent.setToolTipText(pText);
	}

	/** 
	aus Kompatibilitaetsgruenden
	 */
	public void gibFrei()
	{
	}

}