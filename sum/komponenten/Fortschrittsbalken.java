package sum.komponenten;

import javax.swing.*;
import java.io.*;
import sum.ereignis.*;

/**
 Ein Fortschrittsbalken ist die Klasse fuer Java-Sliders. Rollbalken
 reagieren auf die Verschiebung des Reglers mit der Maus.
 @author Bernard Schriek, Horst Hildebrecht
 @version 7.5 vom 29.10.2013
 */
public class Fortschrittsbalken extends Komponente implements Serializable
{
	private JProgressBar hatProgressBar;

	/**
	 Der Fortschrittsbalken wird erzeugt. Der Stil ist JProgressBar.VERTICAL oder
	 JProgressBar.HORIZONTAL. Ausserdem werden Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe wird im
	 Anwendungsprogramm festgelegt.
	 Der Fortschrittsbalken befindet sich auf dem Fenster.
	 @param pFenster das Fenster, das die Komponente enth&auml;lt
	 @param pStil die Ausrichtung des Forschrittbalken (siehe Klasse Ausrichtung)
	 @param pMinWert der Minimalwert
	 @param pMaxWert der Maximalwert
	 */
	public Fortschrittsbalken(Fenster pFenster, int pStil, int pMinWert,
			int pMaxWert)
	{
		this(pFenster, 10, 10, 10, 10, pMinWert, pMaxWert);
	}

	/**
	 Der Fortschrittsbalken wird erzeugt. Der Stil ist JProgressBar.VERTICAL oder
	 JProgressBar.HORIZONTAL. Ausserdem werden Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe wird im
	 Anwendungsprogramm festgelegt.
	 @param pStil die Ausrichtung des Forschrittbalken (siehe Klasse Ausrichtung)
	 @param pMinWert der Minimalwert
	 @param pMaxWert der Maximalwert
	 */
	public Fortschrittsbalken(int pStil, int pMinWert, int pMaxWert)
	{
		this(10, 10, 10, 10, pMinWert, pMaxWert);
	}

	/**
	 Der Fortschrittsbalken wird erzeugt. Der Minimalwert und
	 Maximalwert sowie Position, Breite und Hoehe werden als
	 Parameter uebergeben.
	 Der Fortschrittsbalken befindet sich auf dem Bildschirm.
	 @param pLinks der Abstand der Komponente vom linken Fensterrand
	 @param pOben der Abstand der Komponente vom oberen Fensterrand
	 @param pBreite die Breite der Komponente
	 @param pHoehe die Hoehe der Komponente
	 @param pMinWert der Minimalwert
	 @param pMaxWert der Maximalwert
	 */
	public Fortschrittsbalken(double pLinks, double pOben, double pBreite,
			double pHoehe, int pMinWert, int pMaxWert)
	{
		if (pHoehe > pBreite)
			hatProgressBar = new JProgressBar(JProgressBar.VERTICAL, pMinWert,
					pMaxWert);
		else
			hatProgressBar = new JProgressBar(JProgressBar.HORIZONTAL,
					pMinWert, pMaxWert);
		hatProgressBar.setOpaque(true);
		Bildschirm.topFenster.privatPanel().add(hatProgressBar, 0);
		this.lerneKomponenteKennen(Bildschirm.topFenster, hatProgressBar);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}

	/**
	 Der Fortschrittsbalken wird erzeugt. Der Minimalwert und
	 Maximalwert sowie Position, Breite und Hoehe werden als
	 Parameter uebergeben.
	 Der Fortschrittsbalken befindet sich auf dem Fenster.
	 @param pFenster das Fenster, das die Komponente enth&auml;lt
	 @param pLinks der Abstand der Komponente vom linken Fensterrand
	 @param pOben der Abstand der Komponente vom oberen Fensterrand
	 @param pBreite die Breite der Komponente
	 @param pHoehe die Hoehe der Komponente
	 @param pMinWert der Minimalwert
	 @param pMaxWert der Maximalwert
	 */
	public Fortschrittsbalken(Fenster pFenster, double pLinks, double pOben,
			double pBreite, double pHoehe, int pMinWert, int pMaxWert)
	{
		if (pHoehe > pBreite)
			hatProgressBar = new JProgressBar(JProgressBar.VERTICAL, pMinWert,
					pMaxWert);
		else
			hatProgressBar = new JProgressBar(JProgressBar.HORIZONTAL,
					pMinWert, pMaxWert);
		hatProgressBar.setOpaque(true);
		pFenster.privatPanel().add(hatProgressBar, 0);
		this.lerneKomponenteKennen(pFenster, hatProgressBar);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}

	/**
	 Der Fortschrittsbalken erhaelt eine neuen aktuellen Wert.
	 @param pWert der neue Wert des Fortschrittbalkens
	 */
	public void setzeWert(final int pWert)
	{
		hatProgressBar.setValue(pWert);
		hatProgressBar.paintImmediately(0, 0, hatProgressBar.getWidth(),
				hatProgressBar.getHeight());
	}

	/**
	 Die momentane Position (Wert) des Fortschrittsbalkens wird abgefragt.
	 @return der aktuelle Wert des Fortschrittbalkens
	 */
	public int wert()
	{
		return hatProgressBar.getValue();
	}

	/**
	 Der Fortschrittsbalken erhaelt eine neues Minimum.
	 @param pWert das neue Minimum
	 */
	public void setzeMinimum(int pWert)
	{
		hatProgressBar.setMinimum(pWert);
	}

	/**
	 Das momentane Minimum des Fortschrittsbalkens wird abgefragt.
	 @return das aktuelle Minimum
	 */
	public int minimum()
	{
		return hatProgressBar.getMinimum();
	}

	/**
	 Der Fortschrittsbalken erhaelt eine neues Maximum.
	 @param pWert das neue Maximum
	 */
	public void setzeMaximum(int pWert)
	{
		hatProgressBar.setMaximum(pWert);
	}

	/**
	 Das momentane Maximum des Fortschrittsbalkens wird abgefragt.
	 @return das aktuelle Maximum
	 */
	public int maximum()
	{
		return hatProgressBar.getMaximum();
	}

}
