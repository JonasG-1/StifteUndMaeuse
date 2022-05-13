package sum.komponenten;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import sum.ereignis.*;

/**
 Ein Regler ist die Klasse fuer Java-Sliders. Regler
 reagieren auf die Verschiebung des Reglers mit der Maus.
 @author Bernard Schriek, Horst Hildebrecht
 @version 7.5 vom 29.10.2013
 */
public class Regler extends Komponente implements Serializable
{
	private String zGeaendertBearbeiter = "";

	private JSlider hatSlider;

	private class SliderReaktor implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			geaendert();
		}
	}

	private class SliderFokusReaktor implements FocusListener
	{
		public void focusGained(FocusEvent e)
		{
			bekommtFokus();
		}

		public void focusLost(FocusEvent e)
		{
			verliertFokus();
		}
	}

	/**
	 Der Regler wird erzeugt. Der Stil ist Ausrichtung.VERTIKAL oder
	 Ausrichtung.HORIZONTAL. Ausserdem werden Anfangswert, Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe wird im
	 Anwendungsprogramm festgelegt.
	 @param pStil die Ausrichtung des Reglers (siehe Klasse Ausrichtung)
	 @param pAnfangswert der Anfangswert des Reglers
	 @param pMinwert der Minimalwert des Reglers
	 @param pMaxwert der Maximalwert des Reglers
	 */
	public Regler(int pStil, int pAnfangswert, int pMinwert, int pMaxwert)
	{
		this(10, 10, 10, 10, pAnfangswert, pMinwert, pMaxwert);
	}

	/**
	 Der Regler wird erzeugt. Der Stil ist Ausrichtung.VERTIKAL oder
	 Ausrichtung.HORIZONTAL. Ausserdem werden Anfangswert, Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe werden als
	 Parameter uebergeben.
	 @param pLinks der Abstand der Komponente vom linken Fensterrand
	 @param pOben der Abstand der Komponente vom oberen Fensterrand
	 @param pBreite die Breite der Komponente
	 @param pHoehe die Hoehe der Komponente
	 @param pAnfangswert der Anfangswert des Reglers
	 @param pMinwert der Minimalwert des Reglers
	 @param pMaxwert der Maximalwert des Reglers
	 */
	public Regler(double pLinks, double pOben, double pBreite, double pHoehe,
			int pAnfangswert, int pMinwert, int pMaxwert)
	{
		if (pHoehe > pBreite)
			hatSlider = new JSlider(JSlider.VERTICAL, pMinwert, pMaxwert,
					pAnfangswert);
		else
			hatSlider = new JSlider(JSlider.HORIZONTAL, pMinwert, pMaxwert,
					pAnfangswert);
		hatSlider.setOpaque(true);
		hatSlider.addChangeListener(new SliderReaktor());
		hatSlider.addFocusListener(new SliderFokusReaktor());
		Bildschirm.topFenster.privatPanel().add(hatSlider, 0);
		this.lerneKomponenteKennen(Bildschirm.topFenster, hatSlider);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}

	/**
	 Der Regler wird erzeugt. Der Stil ist Ausrichtung.VERTIKAL oder
	 Ausrichtung.HORIZONTAL. Ausserdem werden Anfangswert, Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe wird im
	 Anwendungsprogramm festgelegt.
	 Der Regeler befindet sich auf dem Fenster.
	 @param pFenster das Fenster, das die Komponente enth&auml;lt
	 @param pStil die Ausrichtung des Reglers (siehe Klasse Ausrichtung)
	 @param pAnfangswert der Anfangswert des Reglers
	 @param pMinwert der Minimalwert des Reglers
	 @param pMaxwert der Maximalwert des Reglers
	 */
	public Regler(Fenster pFenster, int pStil, int pAnfangswert, int pMinwert,
			int pMaxwert)
	{
		this(pFenster, 10, 10, 10, 10, pAnfangswert, pMinwert, pMaxwert);
	}

	/**
	 Der Regler wird erzeugt. Der Stil ist Ausrichtung.VERTIKAL oder
	 Ausrichtung.HORIZONTAL. Ausserdem werden Anfangswert, Minimalwert und
	 Maximalwert uebergeben. Position, Breite und Hoehe werden als
	 Parameter uebergeben.
	 Der Regler befindet sich auf dem Fenster.
	 @param pFenster das Fenster, das die Komponente enth&auml;lt
	 @param pLinks der Abstand der Komponente vom linken Fensterrand
	 @param pOben der Abstand der Komponente vom oberen Fensterrand
	 @param pBreite die Breite der Komponente
	 @param pHoehe die Hoehe der Komponente
	 @param pAnfangswert der Anfangswert des Reglers
	 @param pMinwert der Minimalwert des Reglers
	 @param pMaxwert der Maximalwert des Reglers
	 */
	public Regler(Fenster pFenster, double pLinks, double pOben,
			double pBreite, double pHoehe, int pAnfangswert, int pMinwert,
			int pMaxwert)
	{
		if (pHoehe > pBreite)
			hatSlider = new JSlider(JSlider.VERTICAL, pMinwert, pMaxwert,
					pAnfangswert);
		else
			hatSlider = new JSlider(JSlider.HORIZONTAL, pMinwert, pMaxwert,
					pAnfangswert);
		hatSlider.setOpaque(true);
		hatSlider.addChangeListener(new SliderReaktor());
		hatSlider.addFocusListener(new SliderFokusReaktor());
		pFenster.privatPanel().add(hatSlider, 0);
		this.lerneKomponenteKennen(pFenster, hatSlider);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}

	/**
	 Die Methode zur Bearbeitung des Geaendert-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn der Regler geaendert wurde.
	 */
	public void setzeBearbeiterGeaendert(String pBearbeiter)
	{
		zGeaendertBearbeiter = pBearbeiter;
	}

	/**
	 Der Regler reagiert auf eine Bewegung.
	 */
	protected void geaendert()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Regler[] meinRegler = new Regler[1];

		if (zGeaendertBearbeiter.length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(zGeaendertBearbeiter, null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung,
							null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ zGeaendertBearbeiter + "\" eines Reglers: "
							+ e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Regler.class;
						methode = sumEreignis.getDeclaredMethod(zGeaendertBearbeiter, formparas);
						methode.setAccessible(true);
						meinRegler[0] = this;
						methode.invoke(
								Ereignisanwendung.hatSuMPrivateAnwendung,
								meinRegler);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ zGeaendertBearbeiter + "\" eines Reglers: "
								+ e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ zGeaendertBearbeiter
								+ "\" eines Reglers nicht gefunden.");
					}
				}
			}
			catch (Exception e4)
			{
				System.out.println("SuMAnwendung: " + e4.toString());
			}
		}
		kenntFenster.doUpdate(hatSlider);
	}

	/**
	 Der Regler erhaelt den Fokus.
	 */
	protected void bekommtFokus()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Regler[] meinRegler = new Regler[1];

		this.setzeFokusWert(true);
		if (this.fokusErhaltenBearbeiter().length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ this.fokusErhaltenBearbeiter()
							+ "\" eines Reglers: "
							+ e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Regler.class;
						methode = sumEreignis.getDeclaredMethod(this
								.fokusErhaltenBearbeiter(), formparas);
						meinRegler[0] = this;
						methode.invoke(
								Ereignisanwendung.hatSuMPrivateAnwendung,
								meinRegler);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ this.fokusErhaltenBearbeiter()
								+ "\" eines Reglers: "
								+ e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ this.fokusErhaltenBearbeiter()
								+ "\" eines Reglers nicht gefunden.");
					}
				}
			}
			catch (Exception e4)
			{
				System.out.println("SuMAnwendung: " + e4.toString());
			}
		}
	}

	/**
	 Der Regler verliert den Fokus.
	 */
	protected void verliertFokus()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Regler[] meinRegler = new Regler[1];

		this.setzeFokusWert(false);
		if (this.fokusVerlorenBearbeiter().length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ this.fokusVerlorenBearbeiter()
							+ "\" eines Reglers: "
							+ e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Regler.class;
						methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), formparas);
						methode.setAccessible(true);
						meinRegler[0] = this;
						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinRegler);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ this.fokusVerlorenBearbeiter()
								+ "\" eines Reglers: "
								+ e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ this.fokusVerlorenBearbeiter()
								+ "\" eines Reglers nicht gefunden.");
					}
				}
			}
			catch (Exception e4)
			{
				System.out.println("SuMAnwendung: " + e4.toString());
			}
		}
	}

	/**
	 Der Regler erhaelt eine neuen aktuellen Wert.
	 @param pWert der neue Wert des Reglers
	 */
	public void setzeWert(int pWert)
	{
		hatSlider.setValue(pWert);
		hatSlider.paintImmediately(0, 0, hatSlider.getWidth(), hatSlider
				.getHeight());
		hatSlider.validate();
	}

	/**
	 Die momentane Position (Wert) des Reglers wird abgefragt.
	 @return der aktuelle Wert des Reglers
	 */
	public int wert()
	{
		return hatSlider.getValue();
	}

	/**
	 Der Regler erhaelt eine neues Minimum.
	 @param pWert das neue Minimum des Reglers
	 */
	public void setzeMinimum(int pWert)
	{
		hatSlider.setMinimum(pWert);
	}

	/**
	 Das momentane Minimum des Reglers wird abgefragt.
	 @return das aktuelle Minimum des Reglers
	 */
	public int minimum()
	{
		return hatSlider.getMinimum();
	}

	/**
	 Der Regler erhaelt eine neues Maximum.
	 @param pWert das neue Maximum des Reglers
	 */
	public void setzeMaximum(int pWert)
	{
		hatSlider.setMaximum(pWert);
	}

	/**
	 Das momentane Maximum des Reglers wird abgefragt.
	 @return das aktuelle Maximum des Reglers
	 */
	public int maximum()
	{
		return hatSlider.getMaximum();
	}

}
