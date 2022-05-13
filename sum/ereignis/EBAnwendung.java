package sum.ereignis;

import java.util.*;

/**
 Die EBAnwendung ist die Ereignisbearbeiteranwendung, die es ermoeglicht,
 eigene Ereignisbearbeiter in die Ereignisverwaltung einzubinden. Diese
 Ereignisbearbeiter werden in einer Liste verwaltet und erhalten
 von der Ereignisbearbeiteranwendung Informationen ueber aufgetretene Ereignisse. 
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class EBAnwendung extends Ereignisanwendung
{
	private Vector hatEreignisbearbeiterListe = null;

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 */
	public EBAnwendung()
	{
		super();
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public EBAnwendung(boolean pMitDoubleBuffering)
	{
		super(pMitDoubleBuffering);
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 Die Breite und Hoehe des Fensters werden als Parameter uebergeben.
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 */
	public EBAnwendung(int pBreite, int pHoehe)
	{
		super(pBreite, pHoehe);
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 Die Breite und Hoehe des Fensters werden als Parameter uebergeben.
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public EBAnwendung(int pBreite, int pHoehe, boolean pMitDoubleBuffering)
	{
		super(pBreite, pHoehe, pMitDoubleBuffering);
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 Die linke obere Ecke und Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pLinks der Abstand des zugehoerigen Fensters vom linken Bildschirmrand
	 @param pOben der Abstand des zugehoerigen Fensters vom oberen Bildschirmrand
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 */
	public EBAnwendung(int pLinks, int pOben, int pBreite, int pHoehe)
	{
		super(pLinks, pOben, pBreite, pHoehe);
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Die EBAnwendung wird initialisiert. Die Ereignisbearbeiterliste ist leer.
	 Die linke obere Ecke und Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pLinks der Abstand des zugehoerigen Fensters vom linken Bildschirmrand
	 @param pOben der Abstand des zugehoerigen Fensters vom oberen Bildschirmrand
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public EBAnwendung(int pLinks, int pOben, int pBreite, int pHoehe, boolean pMitDoubleBuffering)
	{
		super(pLinks, pOben, pBreite, pHoehe, pMitDoubleBuffering);
		hatEreignisbearbeiterListe = new Vector();
	}

	/**
	 Ein neuer Ereignisbearbeiter wird registriert und
	 erhaelt ab sofort Informationen ueber aufgetretene Ereignisse.
	 @param pEreignisbearbeiter der Ereignisbearbeiter der in die Liste aufgenommen wird
	 */
	public void meldeAn(Ereignisbearbeiter pEreignisbearbeiter)
	{
		hatEreignisbearbeiterListe.addElement(pEreignisbearbeiter);
	}

	/**
	 Das Tastaturereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 @param pZeichen das Zeichen, welches eingegeben wurde
	 */
	public void bearbeiteTaste(char pZeichen)
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteTaste(pZeichen);
			}
	}

	/**
	 Das Mausdruckereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 @param pWoH die horizontale Position der Maus beim Druck der Maustaste
	 @param pWoV die vertikale Position der Maus beim Druck der Maustaste
	 */
	public void bearbeiteMausDruck(int pWoH, int pWoV)
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteMausDruck(pWoH, pWoV);
			}
	}

	/**
	 Das Mauslosereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 @param pWoH die horizontale Position der Maus beim Loslassen der Maustaste
	 @param pWoV die vertikale Position der Maus beim Loslassen der Maustaste
	 */
	public void bearbeiteMausLos(int pWoH, int pWoV)
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteMausLos(pWoH, pWoV);
			}
	}

	/**
	 Das Mausbewegtereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 @param pWohinH die horizontale Position der Maus nach der Bewegung
	 @param pWohinV die vertikale Position der Maus nach der Bewegung
	 */
	public void bearbeiteMausBewegt(int pWohinH, int pWohinV)
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteMausBewegt(pWohinH, pWohinV);
			}
	}

	/**
	 Das Doppelklickereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 @param pWoH die horizontale Position der Maus beim Doppelklick
	 @param pWoV die vertikale Position der Maus beim Doppelklick
	 */
	public void bearbeiteDoppelKlick(int pWoH, int pWoV)
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteDoppelKlick(pWoH, pWoV);
			}
	}

	/**
	 Das Leerlaufereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 */
	public void bearbeiteLeerlauf()
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteLeerlauf();
			}
	}

	/**
	 Das Updateereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 */
	public void bearbeiteUpdate()
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteUpdate();
			}
	}

	/**
	 Das Fokuserhaltenereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 */
	public void bearbeiteFokusErhalten()
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteFokusErhalten();
			}
	}

	/**
	 Das Fokusverlorenereignis wird an alle angemeldeten Ereignisbearbeiter weitergeleitet.
	 */
	public void bearbeiteFokusVerloren()
	{
		Ereignisbearbeiter aktueller;

		if (hatEreignisbearbeiterListe != null)
			for (int i = 0; i < hatEreignisbearbeiterListe.size(); i++)
			{
				aktueller = (Ereignisbearbeiter) hatEreignisbearbeiterListe
						.elementAt(i);
				aktueller.bearbeiteFokusVerloren();
			}
	}

}