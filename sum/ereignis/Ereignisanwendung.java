package sum.ereignis;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 Eine Ereignisanwendung ist der Prototyp einer Anwendung, die auf die
 Standardereignisse der Maus und der Tastatur reagiert. Sie besitzt
 bereits einen Bildschirm, eine Maus und eine Tastatur. Anfallende
 Ereignisse werden einzeln einer zugehoerigen Bearbeitungsmethode
 uebergeben. Unabhaengig davon koennen zwischendurch andere Ereignisse
 bearbeitet werden.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Ereignisanwendung implements Runnable, Serializable
{
	public Bildschirm hatBildschirm;
	private Thread sumThread;
	public static Ereignisanwendung hatSuMPrivateAnwendung;
	private boolean zFuehrtAus = false;

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 */
	public Ereignisanwendung()
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm();
	}

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public Ereignisanwendung(boolean pMitDoubleBuffering)
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm(pMitDoubleBuffering);
	}

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 Das zugehoerige Fenster sowie Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 */
	public Ereignisanwendung(int pBreite, int pHoehe)
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm(pBreite, pHoehe);
	}

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 Das zugehoerige Fenster sowie Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public Ereignisanwendung(int pBreite, int pHoehe, boolean pMitDoubleBuffering)
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm(pBreite, pHoehe, pMitDoubleBuffering);
	}

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 Das zugehoerige Fenster sowie die linke obere Ecke und Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pLinks der Abstand des zugehoerigen Fensters vom linken Bildschirmrand
	 @param pOben der Abstand des zugehoerigen Fensters vom oberen Bildschirmrand
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 */
	public Ereignisanwendung(int pLinks, int pOben, int pBreite, int pHoehe)
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster" + (Bildschirm.zFensternummer + 1), false);
	}

	/**
	 Die Anwendung wird initialisiert. Sie besitzt einen Bildschirm, eine Tastatur und
	 eine Maus, die alle initialisiert sind.
	 Das zugehoerige Fenster sowie die linke obere Ecke und Breite und Hoehe des 
	 Fensters werden als Parameter uebergeben.
	 @param pLinks der Abstand des zugehoerigen Fensters vom linken Bildschirmrand
	 @param pOben der Abstand des zugehoerigen Fensters vom oberen Bildschirmrand
	 @param pBreite die Breite des zugehoerigen Fensters
	 @param pHoehe die Hoehe des zugehoerigen Fensters
	 @param pMitDoubleBuffering soll der Bildschirm gepuffert werden
	 */
	public Ereignisanwendung(int pLinks, int pOben, int pBreite, int pHoehe, boolean pMitDoubleBuffering)
	{
		hatSuMPrivateAnwendung = this;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		hatBildschirm = new Bildschirm(pLinks, pOben, pBreite, pHoehe, "SuM-Fenster" + (Bildschirm.zFensternummer + 1), pMitDoubleBuffering);
	}

	/**
	 Dient nur zum eventuellen Debuggen der Ereignisanwendung.
	 */
	protected void melde(String s)
	{
		System.out.println(s); //zum Debuggen
	}

	/**
	 Der Bildschirm der Ereignisanwendung wird zurueckgegeben.
	 @return der zugehoerige Bildschirm 
	 */
	public Bildschirm bildschirm()
	{
		return hatBildschirm;
	}

	/**
	 Der Ereignisanwendung beginnt auf Ereignisse zu reagieren. 
	 */
	public void fuehreAus()
	{
		if (sumThread == null)
		{
			this.warte(500);
			zFuehrtAus = true;
			sumThread = new Thread(this);
			sumThread.start();
			Stift hatStift = new Stift();
		}
	}

	/**
	 Der Ereignisanwendung beendet die Reaktion auf Ereignisse. 
	 */
	protected void halteAn()
	{
		zFuehrtAus = false;
	}

	/**
	 Die Ereignisanwendung wird beendet. Das zugehoerige Fenster wird geschlossen.
	 */
	public void beenden()
	{
		this.halteAn();
		hatBildschirm.gibFrei();
		System.exit(0);
	}

	/** 
	 wird intern aufgerufen.
	 */
	protected boolean fuehrtAus()
	{
		return zFuehrtAus;
	}

	/**
	 Die Ereignisanwendung arbeitet und reagiert auf Ereignisse, indem sie die entsprechenden
	 Bearbeite-Dienste aufruft.
	 */
	public void run()
	{
		while (zFuehrtAus)
		{
			try
			{
				this.bearbeiteLeerlauf();
				Thread.sleep(30);
			}
			catch (InterruptedException e)
			{
			}
		}
		this.beenden();
	}

	/**
	 Das Ereignisanwendung pausiert f&uuml;r pMillisekunden Millisekunden.
	 */
	protected void warte(long pMillisekunden)
	{
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < pMillisekunden)
		{
		}
	}

	/**
	 Eine Taste ist gedrueckt worden. Das entsprechende Zeichen wird
	 als Parameter uebergeben. Diese Methode sollte ueberschrieben werden.
	 @param pZeichen das Zeichen, welches eingegeben wurde
	 */
	public void bearbeiteTaste(char pZeichen)
	{
	}

	/**
	 Die Maustaste ist gedrueckt worden. Die Koordinaten werden
	 als Parameter uebergeben. Diese Methode sollte ueberschrieben werden.
	 @param pWoH die horizontale Position der Maus beim Druck der Maustaste
	 @param pWoV die vertikale Position der Maus beim Druck der Maustaste
	 */
	public void bearbeiteMausDruck(int pWoH, int pWoV)
	{
	}

	/**
	 Die Maustaste ist losgelassen worden. Die Koordinaten werden
	 als Parameter uebergeben. Diese Methode sollte ueberschrieben werden.
	 @param pWoH die horizontale Position der Maus beim Loslassen der Maustaste
	 @param pWoV die vertikale Position der Maus beim Loslassen der Maustaste
	 */
	public void bearbeiteMausLos(int pWoH, int pWoV)
	{
	}

	/**
	 Die Maust ist bewegt worden. Die neuen Koordinaten werden
	 als Parameter uebergeben. Diese Methode sollte ueberschrieben werden.
	 @param pWohinH die horizontale Position der Maus nach der Bewegung
	 @param pWohinV die vertikale Position der Maus nach der Bewegung
	 */
	public void bearbeiteMausBewegt(int pWohinH, int pWohinV)
	{
	}

	/**
	 Die Maustaste ist doppelt gedrueckt worden (DoppelKlick). Die Koordinaten werden
	 als Parameter uebergeben. Diese Methode sollte ueberschrieben werden.
	 @param pWoH die horizontale Position der Maus beim Doppelklick
	 @param pWoV die vertikale Position der Maus beim Doppelklick
	 */
	public void bearbeiteDoppelKlick(int pWoH, int pWoV)
	{
	}

	/**
	 Kein spezielles Ereignis ist eingetreten (Idle). Diese Methode sollte ueberschrieben werden.
	 */
	public void bearbeiteLeerlauf()
	{
	}

	/**
	 Das Fenster wird neu gezeichnet.
	 Diese Methode sollte ueberschrieben werden.
	 */
	public void bearbeiteUpdate()
	{
	}

	/**
	 Das Fenster hat den Fokus erhalten.
	 Diese Methode sollte ueberschrieben werden.
	 */
	public void bearbeiteFokusErhalten()
	{
	}

	/**
	 Das Fenster hat den Fokus verloren.
	 Diese Methode sollte ueberschrieben werden.
	 */
	public void bearbeiteFokusVerloren()
	{
	}

	/**
	 Liefert die Information, ob der Bildschirm den Fokus besitzt.
	 @return true, wenn der zugehoerige Bildschirm den Fokus besitzt.
	 */
	public boolean besitztFokus()
	{
		return hatBildschirm.besitztFokus();
	}

	/**
	 Der Bildschirm erhaelt den Fokus.
	 */
	public void setzeFokus()
	{
		hatBildschirm.requestFocus();
	}

	/** 
	 aus Kompatibilitaetsgruenden
	 */
	public void gibFrei()
	{
		hatBildschirm.gibFrei();
	}

}