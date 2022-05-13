package sum.werkzeuge;

import java.io.*;

/**
Ein Textwerkzeug realisiert ein Objekt, das verschiedene 
Zeichenkettenoperationen zur Verfuegung stellt. Das Trennungszeichen
trennt einen Text in "Worte". Es ist normalerweise das Leerzeichen.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
*/
public class Textwerkzeug implements Serializable
{
	private String zTrennung;
	
/**
Das Textwerkzeug wird mit einem Leerzeichen als Trennung initialisiert.
*/
	public Textwerkzeug()
	{
			zTrennung = " "; // Leerzeichen
	}

/**
Die Zeichenkette, die zur Trennung dient, wurde definiert.
@param pTrennungszeichenkette die neue Trennung
*/	
	public void setzeTrennungszeichen(String pTrennungszeichenkette)
	{
		zTrennung = pTrennungszeichenkette;
	}
	
/**
Es wird die Laenge der Zeichenkette zurueckgegeben.
@param pText der zu untersuchende Text
@return die Zahl der Zeichen im Text
*/	
	public int laenge(String pText)
	{
		return pText.length();
	}
	
/**
Es wird das Zeichen an einer bestimmten Stelle der Zeichenkette zurueckgegeben.
@param pText der Text, in dem ein Zeichen gesucht wird
@param pStelle die Position fuer das Zeichen (beginnend bei 1)
@return das Zeichen an der entsprechenden Position
*/	
	public char zeichenAn(String pText, int pStelle)
	{
		return pText.charAt(pStelle - 1);
	}
	
/**
Es wird das Wort der angegebenen Nummer der Zeichenkette zurueckgegeben.
@param pText der Text, in dem das Wort gesucht wird
@param pWortnummer die Nummer fuer das Wort
@return das n-te Wort (n ist die Wortnummer)
*/	
	public String wortAn(String pText, int pWortnummer)
	{
		String lWort = pText;
		int lWortZaehler = 1;
		int lFundstelle = 0;
		
		while ( ((lWortZaehler < pWortnummer) && (lFundstelle = lWort.indexOf(zTrennung, 0)) > -1))
		{
			lWort = lWort.substring(lFundstelle + zTrennung.length(), lWort.length());
			lWortZaehler++;
		}
		if (lFundstelle > -1) // Es gibt noch Woerter dahinter
			if ((lFundstelle = lWort.indexOf(zTrennung, 0)) > -1)
				return lWort.substring(0, lFundstelle); // bis zur naechsten Fundstelle
			else
				return lWort; //bis zum Ende
		else
			return ""; // Es gibt kein Wort mit dieser Nummer
	}
	
/**
Es wird die Anzahl Worte in der Zeichenkette zurueckgegeben.
@param pText der Text, in dem die Wort gezaehlt werden
@return die Anzahl der Worte
*/	
	public int wortanzahl(String pText)
	{
		String lWort = pText;
		int lWortZaehler = 1;
		int lFundstelle = 0;
		
		while ((lFundstelle = lWort.indexOf(zTrennung, 0)) > -1)
		{
			lWort = lWort.substring(lFundstelle + zTrennung.length(), lWort.length());
			lWortZaehler++;
		}
		return lWortZaehler;
	}
	
/**
Es wird eine Teilzeichenkette zurueckgegeben.
@param pText der zu untersuchende Text
@param pVon die Position des ersten Zeichens
@param pBis die Position des letzten Zeichens
@return die Teilzeichenkette
*/	
	public String teilzeichenkette(String pText, int pVon, int pBis)
	{
		return pText.substring(pVon - 1, pBis);
	}
	
/**
Es wird eine Teilzeichenkette zurueckgegeben.
@param pText der zu untersuchende Text
@param pVon die Position des ersten Zeichens
@param pBis die Position des letzten Zeichens
@return die Teilzeichenkette
*/	
	public String teilZeichenkette(String pText, int pVon, int pBis)
	{
		return this.teilzeichenkette(pText, pVon, pBis);
	}
	
/**
Es wird die Position einer Teilzeichenkette in der
Zeichenkette zurueckgegeben. Falls der Suchtext nichtvorhanden ist,
wird 0 zurueckgegeben.
@param pText der Text, in dem gesucht wird
@param pSuchtext die Teilzeichenkette
@return die Position der Teilzeichenkette im Text
*/	
	public int positionVon(String pText, String pSuchtext)
	{
		return pText.indexOf(pSuchtext) + 1;
	}
	
/**
Es wird eine Teilzeichenkette aus der Zeichenkette entfernt.
@param pText der Text, aus dem entfernt wird
@param pVon die erste Position, von der an entfernt wird
@param pBis die letzte Position, bis zu der entfernt wird
@return der gekuerzte Text
*/	
	public String textOhne(String pText, int pVon, int pBis)
	{
		String s = "";
		
		if (pVon > 1)
			s = pText.substring(0, pVon - 1);
		if (pBis < pText.length())
			s +=  pText.substring(pBis, pText.length());
		return s;
	}
	
/**
Es wird eine Zeichenkette an einer bestimmten Position eingefuegt.
@param pText der Text, in den eingefuegt wird
@param pNeu der Text, der eingefuegt wird
@param pStelle die Position im Text, wo eingefuegt wird
@return der verlaengerte Text
*/	
	public String textMit(String pText, String pNeu, int pStelle)
	{
		String s = "";
		
		if (pStelle > 1)
			s = pText.substring(0, pStelle);
		s += pNeu;
		s +=  pText.substring(pStelle, pText.length());
		return s;
	}
	
/**
Es wird die Zeichenkette in Kleinbuchstaben umgewandelt.
@param pText der Ausgangstext
@return der in Kleinschrift gewandelte Text
*/	
	public String kleinschrift(String pText)
	{
		return pText.toLowerCase();
	}
	
/**
Es wird die Zeichenkette in Kleinbuchstaben umgewandelt.
@param pText der Ausgangstext
@return der in Kleinschrift gewandelte Text
*/	
	public String kleinSchrift(String pText)
	{
		return pText.toLowerCase();
	}
	
/**
Es wird die Zeichenkette in Grossbuchstaben umgewandelt.
@param pText der Ausgangstext
@return der in Grossschrift gewandelte Text
*/	
	public String grossschrift(String pText)
	{
		return pText.toUpperCase();
	}
	
/**
Es wird die Zeichenkette in Grossbuchstaben umgewandelt.
@param pText der Ausgangstext
@return der in Grossschrift gewandelte Text
*/	
	public String grossSchrift(String pText)
	{
		return pText.toUpperCase();
	}
	
/**
Es werden zwei Zeichenketten verglichen (=).
@param pText1 der erste Text
@param pText2 der zweite Text
@return true, wenn beide Texte gleich sind
*/	
	public boolean istGleich(String pText1, String pText2)
	{
		return pText1.compareTo(pText2) == 0;
	}
	
/**
Es werden zwei Zeichenketten verglichen (<).
@param pText1 der erste Text
@param pText2 der zweite Text
@return true, wenn der erste Text kleiner ist
*/	
	public boolean istKleiner(String pText1, String pText2)
	{
		return pText1.compareTo(pText2) < 0;
	}
	
/**
Es werden zwei Zeichenketten verglichen (>).
@param pText1 der erste Text
@param pText2 der zweite Text
@return true, wenn der erste Text groesser ist
*/	
	public boolean istGroesser(String pText1, String pText2)
	{
		return pText1.compareTo(pText2) > 0;
	}
	
/**
Es wird zurueckgegeben, ob die Zeichenkette eine Zahl ist.
@param pText der zu untersuchende Text
@return true, wenn der Text eine Zahl ist
*/
	public boolean istZahl(String pText)
	{
		try
		{
			new Double(pText);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Es wird zurueckgegeben, ob das Zeichen eine ganze Zahl ist.
@param pZeichen das zu untersuchende Zeichen
@return true, wenn das Zeichen eine ganze Zahl ist
*/
	public boolean istGanzeZahl(char pZeichen)
	{
		return this.istGanzeZahl("" + pZeichen);
	}
	
/**
Es wird zurueckgegeben, ob die Zeichenkette eine ganze Zahl ist.
@param pText der zu untersuchende Text
@return true, wenn der Text eine ganze Zahl ist
*/
	public boolean istGanzeZahl(String pText)
	{
		try
		{
			new Integer(pText);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Es wird zurueckgegeben, ob die Zeichenkette eine lange ganze Zahl ist.
@param pText der zu untersuchende Text
@return true, wenn der Text eine lange ganze Zahl ist
*/
	public boolean istLangeGanzeZahl(String pText)
	{
		try
		{
			new Long(pText);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
/**
Die Zeichenkette wird in eine Zahl umgewandelt.
@param pText der zu wandelnde Text
@return der in eine Zahl gewandelte Text
*/
	public double alsZahl(String pText)
	{
		if (this.istZahl(pText))
		{
			Double d = new Double(pText);
			return d.doubleValue();
		}
		else
			throw new ArithmeticException("alsZahl: ist keine Zahl");
	}
	
/**
Das Zeichen wird in eine ganze Zahl umgewandelt.
@param pZeichen das zu wandelnde Zeichen
@return das in eine ganze Zahl gewandelte Zeichen
*/
	public int alsGanzeZahl(char pZeichen)
	{
		return this.alsGanzeZahl("" + pZeichen);
	}
	
/**
Die Zeichenkette wird in eine ganze Zahl umgewandelt.
@param pText der zu wandelnde Text
@return der in eine ganze Zahl gewandelte Text
*/
	public long alsLangeGanzeZahl(String pText)
	{
		if (this.istLangeGanzeZahl(pText))
		{
			Long i = new Long(pText);
			return i.longValue();
		}
		else
			throw new ArithmeticException("alsLangeGanzeZahl: ist keine ganze Zahl");
	}
	
/**
Die Zeichenkette wird in eine ganze Zahl umgewandelt.
@param pText der zu wandelnde Text
@return der in eine ganze Zahl gewandelte Text
*/
	public int alsGanzeZahl(String pText)
	{
		if (this.istGanzeZahl(pText))
		{
			Integer i = new Integer(pText);
			return i.intValue();
		}
		else
			throw new ArithmeticException("alsGanzeZahl: ist keine ganze Zahl");
	}
	
/**
Die Zahl wird in eine Zeichenkette umgewandelt.
@param pZahl die zu wandelnde Zahl
@return die in eine Zeichenkette gewandelte Zahl
*/
	public String alsText(double pZahl)
	{
		return "" + pZahl;
	}
	
/**
Die ganze Zahl wird in eine Zeichenkette umgewandelt.
@param pZahl die zu wandelnde ganze Zahl
@return die in eine Zeichenkette gewandelte ganze Zahl
*/
	public String alsText(int pZahl)
	{
		return "" + pZahl;
	}
	
/**
Die beiden Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@return der aus beiden Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2)
	{
		return pText1 + pText2;
	}
	
/**
Die drei Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@return der aus den drei Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3)
	{
		return pText1 + pText2 + pText3;
	}
	
/**
Die vier Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@return der aus den vier Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4)
	{
		return pText1 + pText2 + pText3 + pText4;
	}
	
/**
Die fuenf Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@return der aus den fuenf Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5;
	}
	
/**
Die sechs Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@param pText6 der sechste Text
@return der aus den sechs Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5, String pText6)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5 + pText6;
	}
	
/**
Die sieben Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@param pText6 der sechste Text
@param pText7 der sechste Text
@return der aus den sieben Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5, String pText6,
							 String pText7)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5 + pText6 
			   + pText7;
	}
	
/**
Die acht Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@param pText6 der sechste Text
@param pText7 der sechste Text
@param pText8 der sechste Text
@return der aus den acht Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5, String pText6,
							 String pText7, String pText8)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5 + pText6 
			   + pText7 + pText8;
	}
	
/**
Die neun Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@param pText6 der sechste Text
@param pText7 der sechste Text
@param pText8 der sechste Text
@param pText9 der neunte Text
@return der aus den neun Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5, String pText6,
							 String pText7, String pText8, String pText9)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5 + pText6 
			   + pText7 + pText8 + pText9;
	}
	
/**
Die zehn Zeichenketten werden verkettet.
@param pText1 der erste Text
@param pText2 der zweite Text
@param pText3 der dritte Text
@param pText4 der vierte Text
@param pText5 der fuenfte Text
@param pText6 der sechste Text
@param pText7 der sechste Text
@param pText8 der sechste Text
@param pText9 der neunte Text
@param pText10 der zehnte Text
@return der aus den zehn Texten zusammengesetzte Text
*/
	public String verkettung(String pText1, String pText2, String pText3, 
							 String pText4, String pText5, String pText6,
							 String pText7, String pText8, String pText9,
							 String pText10)
	{
		return pText1 + pText2 + pText3 + pText4 + pText5 + pText6 
			   + pText7 + pText8 + pText9 + pText10;
	}
	
/**
Die Ordinalzahl wird in das zugehoerige Zeichen umgewandelt
@param pOrdinalzahl die Zahl fuer die Position in der Unicode-Tabelle
@return das Zeichen an der entsprechenden Position der Unicode-Tabelle
*/

   public char zeichenVon(int pOrdinalzahl)
   {
      return (char)pOrdinalzahl;
   }


/**
Das Zeichen wird in die zugehoerige Ordinalzahl umgewandelt
@param pZeichen das Zeichen
@return die Position des Zeichens in der Unicode-Tabelle
*/

   public int ordinalzahl (char pZeichen)
   {
	    return (int)pZeichen;
   }
/** 
aus Kompatibilitaetsgruenden
*/
	public void gibFrei()
	{}

}