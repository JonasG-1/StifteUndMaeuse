package sum.komponenten;

import java.awt.*;
import sum.ereignis.*;

/**
Die Klasse Textkomponente ist eine abstrakte Oberklasse fuer alle textorientierten Komponenten der SuM-Komponentenbibliothek. 
Neben den Diensten und Eigenschaften, die von der Oberklasse geerbt werden, 
besitzt eine Textkomponente einen Inhalt, der in verschiedenen Datentypen abgefragt oder gesetzt werden kann. 
Standardmaessig ist der Inhalt leer. 
Das Aussehen des Inhalts kann in Schriftart, Schriftgroesse, Schriftfarbe und Schriftstil veraendert werden.
@author Horst Hildebrecht
@version 7.5 vom 29.10.2013
*/
public abstract class Textkomponente extends Komponente
{

 	protected String zAktuellFont = Schrift.STANDARDSCHRIFTART;
  	protected int zSchriftStil =	Schrift.STANDARDSTIL;
  	protected int zSchriftGroesse = Schrift.STANDARDGROESSE;
  	protected Font zSchriftArt = Schrift.STANDARDSCHRIFT;
  		
	/**
	 Position, Breite,Hoehe und Inhalt der Textkomponente werden festgelegt.
	*/
	protected void init(double pLinks, double pOben, double pBreite, double pHoehe, String pInhalt)
	{
		super.init(pLinks, pOben, pBreite, pHoehe);
		this.setzeInhalt(pInhalt);
	}
	
/**
 Die Textkomponente erhaelt einen neuen Inhalt.
 Leere Methode, wird in der Unterklasse ueberschrieben.
 @param pText der Text, der als neuer Inhalt gesetzt wird
*/
	public abstract void setzeInhalt(String pText);
	
/**
 Die Textkomponente erhaelt einen neuen Inhalt als Zeichen.
 @param pZeichen das Zeichen, das als neuer Inhalt gesetzt wird
*/
	public void setzeInhalt(char pZeichen)
	{
		this.setzeInhalt("" + pZeichen);
	}
	
/**
 Die Textkomponente erhaelt einen neuen Inhalt als ganze Zahl.
 @param pZahl die Zahl, die als neuer Inhalt gesetzt wird
*/
	public void setzeInhalt(int pZahl)
	{
		this.setzeInhalt("" + pZahl);
	}
	
/**
 Die Textkomponente erhaelt einen neuen Inhalt als lange ganze Zahl.
 @param pZahl die Zahl, die als neuer Inhalt gesetzt wird
*/
	public void setzeInhalt(long pZahl)
	{
		this.setzeInhalt("" + pZahl);
	}
	
/**
 Die Textkomponente erhaelt einen neuen Inhalt als Kommazahl.
 @param pZahl die Zahl, die als neuer Inhalt gesetzt wird
*/
	public void setzeInhalt(double pZahl)
	{
		this.setzeInhalt("" + pZahl);
	}
	
/**
Es wird zurueckgegeben, ob die Textkomponente keine (ganze) Zahl ist.
@return true, wenn der Inhalt keine Zahl ist
*/
	public boolean inhaltIstText()
	{
		return !this.inhaltIstGanzeZahl() && !this.inhaltIstZahl();
	}
	
/**
Es wird zurueckgegeben, ob die Textkomponente eine ganze Zahl ist.
@return true, wenn der Inhalt eine ganze Zahl ist
*/
	public boolean inhaltIstGanzeZahl()
	{
		try
		{
			Integer.valueOf(this.inhaltAlsText());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Es wird zurueckgegeben, ob die Textkomponente eine lange ganze Zahl ist.
@return true, wenn der Inhalt eine lange ganze Zahl ist
*/
	public boolean inhaltIstLangeGanzeZahl()
	{
		try
		{
			Long.valueOf(this.inhaltAlsText());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Es wird zurueckgegeben, ob die Textkomponente eine Kommazahl ist.
@return true, wenn der Inhalt eine Zahl ist
*/
	public boolean inhaltIstZahl()
	{
		try
		{
			Double.valueOf(this.inhaltAlsText());
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Abstrakter Dienst, der in den Unterklassen ueberschrieben wird.
In Unterklassen wird der Text der Textkomponente als String zurueckgegeben.
@return der Inhalt der Komponente als Zeichenkette (String)
*/
	public abstract String inhaltAlsText();
	
/**
Der Text der Textkomponente wird als ganze Zahl zurueckgegeben.
@return der Inhalt der Komponente als ganze Zahl
*/
	public int inhaltAlsGanzeZahl() throws ArithmeticException
	{
		if (this.inhaltIstGanzeZahl())
			return Integer.parseInt(this.inhaltAlsText());
		else
			throw new ArithmeticException("inhaltAlsGanzeZahl: ist keine ganze Zahl");
	}
	
/**
Der Text der Textkomponente wird als lange ganze Zahl zurueckgegeben.
@return der Inhalt der Komponente als lange ganze Zahl
*/
	public long inhaltAlsLangeGanzeZahl() throws ArithmeticException
	{
		if (this.inhaltIstLangeGanzeZahl())
			return Long.parseLong(this.inhaltAlsText());
		else
			throw new ArithmeticException("inhaltAlsLangeGanzeZahl: ist keine lange ganze Zahl");
	}
	
/**
Der Text der Textkomponente wird als Kommazahl zurueckgegeben.
@return der Inhalt der Komponente als Kommazahl
*/
	public double inhaltAlsZahl() throws ArithmeticException
	{
		if (this.inhaltIstZahl())
		{
			Double d = new Double(this.inhaltAlsText());
			return d.doubleValue();
		}
		else
			throw new ArithmeticException("inhaltAlsZahl: ist keine Zahl");
	}
	
/**
Die Schriftart der Textkomponente wird veraendert
@param pSchriftart die neue Schriftart der Komponente (siehe Klasse Schrift)
*/
	public void setzeSchriftArt(String pSchriftart)
	{
		zAktuellFont = pSchriftart;
    	zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
    	hatComponent.setFont(zSchriftArt);   
	 	hatComponent.repaint();	
	}
	
/**
Die Schriftart der Textkomponente wird veraendert
@param pSchriftart die neue Schriftart der Komponente (siehe Klasse Schrift)
*/
	public void setzeSchriftart(String pSchriftart)
	{
	 	this.setzeSchriftArt(pSchriftart);	
	}
	
/**
Die Schriftgroesse der Textkomponente wird veraendert
@param pGroesse die neue Schriftgroesse der Komponente
*/
	public void setzeSchriftgroesse(int pGroesse)
	{
	 	this.setzeSchriftGroesse(pGroesse);
	}
	
/**
Die Schriftgroesse der Textkomponente wird veraendert
@param pGroesse die neue Schriftgroesse der Komponente
*/
	public void setzeSchriftGroesse(int pGroesse)
	{
		zSchriftGroesse = pGroesse;
    	zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
    	hatComponent.setFont(zSchriftArt);   
	 	hatComponent.repaint();
	}
	
/**
Der Schriftstil der Textkomponente wird veraendert
@param pStil der neue Schriftstil der Komponente (siehe Klasse Schrift)
*/
	public void setzeSchriftStil(int pStil)
	{
		zSchriftStil = pStil;
    	zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
    	hatComponent.setFont(zSchriftArt);   
	 	hatComponent.repaint();
	}
	
/**
Der Schriftstil der Textkomponente wird veraendert
@param pStil der neue Schriftstil der Komponente (siehe Klasse Schrift)
*/
	public void setzeSchriftstil(int pStil)
	{
	 	this.setzeSchriftStil(pStil);
	}
	
/**
Die Schriftfarbe der Textkomponente wird veraendert
@param pFarbe die neue Schriftfarbe der Komponente
*/
	public void setzeSchriftFarbe(Color pFarbe)
	{
	 	hatComponent.setForeground(pFarbe);
	 	hatComponent.repaint();
	}
	
/**
Die Schriftfarbe der Textkomponente wird veraendert
@param pFarbe die neue Schriftfarbe der Komponente
*/
	public void setzeSchriftfarbe(Color pFarbe)
	{
	 	this.setzeSchriftFarbe(pFarbe);
	}
	
/**
Die Schriftfarbe der Textkomponente wird veraendert
@param pFarbe die neue Schriftfarbe der Komponente
*/
	public void setzeSchriftfarbe(int pFarbe)
	{
	 	this.setzeSchriftFarbe(pFarbe);
	}
	
/**
Die Schriftfarbe der Textkomponente wird zurueckgegeben
@return die Schriftfarbe der Textkomponente
*/
	public Color schriftfarbe()
	{
	 	return hatComponent.getForeground();
	}
	
/**
Die Schriftfarbe der Textkomponente wird zurueckgegeben
@return die Schriftfarbe der Textkomponente
*/
	public Color schriftFarbe()
	{
	 	return this.schriftfarbe();
	}
	
/**
Die Schriftfarbe der Textkomponente wird veraendert
@param pFarbe die neue Schriftfarbe der Komponente
*/
	public void setzeSchriftFarbe(int pFarbe)
  	{
		if (pFarbe < 0)
			pFarbe = 0;
		pFarbe %= 13;
		switch (pFarbe)
		{
			case 0: this.setzeSchriftFarbe(Color.black); break;
			case 1: this.setzeSchriftFarbe(Color.blue); break;
			case 2: this.setzeSchriftFarbe(Color.cyan); break;
			case 3: this.setzeSchriftFarbe(Color.darkGray); break;
			case 4: this.setzeSchriftFarbe(Color.gray); break;
			case 5: this.setzeSchriftFarbe(Color.green); break;
			case 6: this.setzeSchriftFarbe(Color.lightGray); break;
			case 7: this.setzeSchriftFarbe(Color.magenta); break;
			case 8: this.setzeSchriftFarbe(Color.orange); break;
			case 9: this.setzeSchriftFarbe(Color.pink); break;
			case 10: this.setzeSchriftFarbe(Color.red); break;
			case 11: this.setzeSchriftFarbe(Color.white); break;
			case 12: this.setzeSchriftFarbe(Color.yellow); break;
		}
	 	hatComponent.repaint();
  	}
	
}
