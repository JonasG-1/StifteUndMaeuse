package sum.ereignis;

import java.io.*;

/**
 Der Ereignisbearbeiter ist die abstrakte Oberklasse fuer konkrete Ereignisbearbeiter
 und besitzt leere Methoden zur Bearbeitung von Ereignissen, die in den konkreten
 Ereignisbearbeitern ueberschrieben werden.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public abstract class Ereignisbearbeiter implements Serializable
{
	/**
	 Der Ereignisbearbeiter wird initialisiert.
	 */
	public Ereignisbearbeiter()
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Tastaturereignis informiert.
	 @param pZeichen das Zeichen, welches eingegeben wurde
	 */
	public void bearbeiteTaste(char pZeichen)
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Mausdruckereignis informiert.
	 @param pWoH die horizontale Position der Maus beim Druck der Maustaste
	 @param pWoV die vertikale Position der Maus beim Druck der Maustaste
	 */
	public void bearbeiteMausDruck(int pWoH, int pWoV)
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Mauslosereignis informiert.
	 @param pWoH die horizontale Position der Maus beim Loslassen der Maustaste
	 @param pWoV die vertikale Position der Maus beim Loslassen der Maustaste
	 */
	public void bearbeiteMausLos(int pWoH, int pWoV)
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Mausbewegtereignis informiert.
	 @param pWohinH die horizontale Position der Maus nach der Bewegung
	 @param pWohinV die vertikale Position der Maus nach der Bewegung
	 */
	public void bearbeiteMausBewegt(int pWohinH, int pWohinV)
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Doppelklickereignis informiert.
	 @param pWoH die horizontale Position der Maus beim Doppelklick
	 @param pWoV die vertikale Position der Maus beim Doppelklick
	 */
	public void bearbeiteDoppelKlick(int pWoH, int pWoV)
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Leerlaufereignis informiert.
	 */
	public void bearbeiteLeerlauf()
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Updateereignis informiert.
	 */
	public void bearbeiteUpdate()
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Fokuserhaltenereignis informiert.
	 */
	public void bearbeiteFokusErhalten()
	{
	}

	/**
	 Der Ereignisbearbeiter wird ueber das Fokusverlorenereignis informiert.
	 */
	public void bearbeiteFokusVerloren()
	{
	}

	/**
	 Aus Kompatibilitaetsgruenden
	 */
	public void gibFrei()
	{
	}
}