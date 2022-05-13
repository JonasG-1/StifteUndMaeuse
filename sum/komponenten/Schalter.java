package sum.komponenten;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import sum.ereignis.*;

/**
Ein Schalter ist die Klasse fuer Java-Checkboxen. Schalter reagieren
auf einen Klick mit der Maus und schalten sich ein bzw. aus.
@author Bernard Schriek, Horst Hildebrecht
@version 7.5 vom 29.10.2013
*/
public class Schalter extends Textkomponente implements Serializable
{
  	private String zGeklicktBearbeiter = "";
  	
  	private JCheckBox hatCheckbox;
	
	private class SchalterReaktor implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			schalterGeklickt();
		}
	}
	
	private class SchalterTastenReaktor implements KeyListener
	{
		public void keyTyped(KeyEvent e)
		{}

		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				schalterGeklickt();
		}
		
		public void keyReleased(KeyEvent e)
		{}
	}
	
	private class SchalterFokusReaktor implements FocusListener
	{
		public void focusGained(FocusEvent e)		
		{
			fokusErhalten();
		}
		
		public void focusLost(FocusEvent e)		
		{
			fokusVerloren();
		}
	}
	
/**
 Der Schalter wird erzeugt und bekommt den Text pAufschrift. Position, Breite und Hoehe
 werden als Parameter uebergeben. Der Name der Aktionsmethode, die beim Druecken des
 Schalteres aufgerufen wird, muss noch festgelegt werden.
 Der Schalter befindet sich auf dem Bildschirm.
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
 @param pAufschrift der Text des Schalters
*/
	public Schalter(double pLinks, double pOben, double pBreite, double pHoehe,
				 String pAufschrift)
	{
		hatCheckbox = new JCheckBox(pAufschrift);
		hatCheckbox.setOpaque(true);
		Bildschirm.topFenster.privatPanel().add(hatCheckbox, 0);
		hatCheckbox.addItemListener(new SchalterReaktor());
		hatCheckbox.addKeyListener(new SchalterTastenReaktor());
		hatCheckbox.addFocusListener(new SchalterFokusReaktor());
		this.lerneKomponenteKennen(Bildschirm.topFenster, hatCheckbox);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}
	
/**
 Der Schalter wird erzeugt und bekommt den Text pAufschrift. Position, Breite und Hoehe
 werden als Parameter uebergeben. Der Name der Aktionsmethode, die beim Druecken des
 Schalteres aufgerufen wird, muss noch festgelegt werden.
 Der Schalter befindet sich auf dem Fenster.
 @param pFenster das Fenster, das die Komponente enth&auml;lt
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
 @param pAufschrift der Text des Schalters
*/
	public Schalter(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe,
				 String pAufschrift)
	{
		hatCheckbox = new JCheckBox(pAufschrift);
		hatCheckbox.setOpaque(true);
		pFenster.privatPanel().add(hatCheckbox, 0);
		hatCheckbox.addItemListener(new SchalterReaktor());
		hatCheckbox.addKeyListener(new SchalterTastenReaktor());
		hatCheckbox.addFocusListener(new SchalterFokusReaktor());
		this.lerneKomponenteKennen(pFenster, hatCheckbox);
		this.init(pLinks, pOben, pBreite, pHoehe);
	}
	
/**
 Der Schalter wird erzeugt und bekommt den Text pAufschrift. Position, Breite und Hoehe
 werden als Parameter uebergeben. Der letzte Parameter ist der Name der Aktionsmethode,
 die beim Druecken des Schalters aufgerufen wird.
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
 @param pAufschrift der Text des Schalters
 @param pGeklicktBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn der Schalter gedrueckt wurde.
*/
	public Schalter(double pLinks, double pOben, double pBreite, double pHoehe,
				 String pAufschrift, String pGeklicktBearbeiter)
	{
		this(pLinks, pOben, pBreite, pHoehe, pAufschrift);
		zGeklicktBearbeiter = pGeklicktBearbeiter;
	}
	
/**
 Die Methode zur Bearbeitung des Geklickt-Ereignisses in
 der Ereignisanwendung wird festgelegt.
 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn der Schalter gedrueckt wurde.
*/
	public void setzeBearbeiterGeklickt(String pBearbeiter)
	{
		zGeklicktBearbeiter = pBearbeiter;
	}
	
/**
 Der Schalter reagiert auf einen Mausklick, indem er die beim Konstruktor als Parameter uebergebene
 Methode der Anwendung aufruft.
*/
	protected void schalterGeklickt()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Schalter[] schalter = new Schalter[1];
		
		if (zGeklicktBearbeiter.length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(zGeklicktBearbeiter, null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);			
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \"" + zGeklicktBearbeiter + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Schalter.class;
						methode = sumEreignis.getDeclaredMethod(zGeklicktBearbeiter, formparas);
						methode.setAccessible(true);
						schalter[0] = this;
						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, schalter);			
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \"" + zGeklicktBearbeiter + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \"" + zGeklicktBearbeiter + "\" von Schalter \"" + this.inhaltAlsText() + "\" nicht gefunden.");
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
 Der Schalter erhaelt den Fokus.
*/
	protected void fokusErhalten()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Schalter[] schalter = new Schalter[1];
		
		this.setzeFokusWert(true);
		
		if (this.fokusErhaltenBearbeiter().length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);			
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Schalter.class;
						methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), formparas);
						methode.setAccessible(true);
						schalter[0] = this;
						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, schalter);			
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \"" + this.fokusErhaltenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\" nicht gefunden.");
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
 Der Schalter verliert den Fokus.
*/
	protected void fokusVerloren()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Schalter[] schalter = new Schalter[1];
		
		this.setzeFokusWert(false);
		if (this.fokusVerlorenBearbeiter().length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();
				try
				{
					methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), null);
					methode.setAccessible(true);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);			
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e0.getTargetException().toString());
					e0.printStackTrace();
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Schalter.class;
						methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), formparas);
						methode.setAccessible(true);
						schalter[0] = this;
						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, schalter);			
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\": " + e2.getTargetException().toString());
						e2.printStackTrace();
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \"" + this.fokusVerlorenBearbeiter() + "\" von Schalter \"" + this.inhaltAlsText() + "\" nicht gefunden.");
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
 Der Schalter wird angeschaltet.
*/
	public void schalteAn()
	{
		hatCheckbox.setSelected(true);
	}
	
/**
 Der Schalter wird ausgeschaltet.
*/
	public void schalteAus()
	{
		hatCheckbox.setSelected(false);
	}
	
/**
 Es wird zurueckgegeben, ob der Schalter angeschaltet ist.
 @return true, wenn der Schalter angeschaltet ist
*/
	public boolean angeschaltet()
	{
		return hatCheckbox.isSelected();
	}
	
/**
 Der Schalter erhaelt eine neue Aufschrift.
 @param pText der neue Text des Schalters
*/
	public void setzeInhalt(String pText)
	{
		hatCheckbox.setText(pText);
		kenntFenster.doUpdate(hatCheckbox);
	}
		
/**
	Die Beschriftung des Schalters wird als String zurueckgegeben.
 	@return der aktuelle Text des Schalters
*/
	public String inhaltAlsText()
	{
		return hatCheckbox.getText();
	}

}