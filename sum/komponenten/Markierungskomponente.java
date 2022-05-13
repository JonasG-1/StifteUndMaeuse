package sum.komponenten;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;
import sum.ereignis.*;

/**
Die Klasse Markierungskomponente ist eine abstrakte Oberklasse fuer die textorientierten Komponenten der SuM-Komponentenbibliothek, 
in denen der Benutzer einen Teil des Textes markieren kann. 
Neben den Diensten und Eigenschaften, die von der Oberklasse geerbt werden, 
besitzt eine Markierungskomponente Dienste zum Abfragen und Aendern der Markierung,
zum Einfuegen, Anhaengen und Loeschen von Text
@author Horst Hildebrecht, Bernard Schriek
@version 7.5 vom 29.10.2013
*/
public abstract class Markierungskomponente extends Textkomponente
{  	
	protected String zInhaltGeaendertBearbeiter = "";
  	protected String zMarkierungGeaendertBearbeiter = "";

	
/**
Die Methode zur Bearbeitung des InhaltGeaendert-Ereignisses in
der Ereignisanwendung wird festgelegt.
@param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn der Inhalt der Komponente geaendert wurde.
*/
	public void setzeBearbeiterInhaltGeaendert(String pBearbeiter)
	{
		zInhaltGeaendertBearbeiter = pBearbeiter;
	}
	
/**
Die Methode zur Bearbeitung des MarkierungGeaendert-Ereignisses in
der Ereignisanwendung wird festgelegt.
@param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn die Markierung der Komponente geaendert wurde.
*/
	public void setzeBearbeiterMarkierungGeaendert(String pBearbeiter)
	{
		zMarkierungGeaendertBearbeiter = pBearbeiter;
	}

/**
Die Markierungskomponente reagiert auf Veraenderungen der Markierung.
Abstrakte Methode, muss in Unterklassen ueberschrieben werden.
*/
	protected abstract void markierungGeaendert();
	
/**
Der Inhalt der Textkomponente von pAnfang bis pEnde wird als String zurueckgegeben.
*/
	public abstract String teilinhalt(int pAnfang, int pEnde);
	
/**
Der Inhalt der Markierungskomponente von pAnfang bis pEnde wird als String zurueckgegeben.
@param pAnfang der Anfang des Textes
@param pEnde das Ende des Textes
*/
	public String teilInhalt(int pAnfang, int pEnde)
	{
		return this.teilinhalt(pAnfang, pEnde);
	}
	
/**
 Der Text pText wird an Position pStelle eingefuegt.
 @param pText der Text, der eingefuegt wird
 @param pStelle die Stelle, an der Text als neue Zeile eingefuegt wird
*/
public abstract void fuegeEin(String pText, int pStelle);
	
/**
 Der Text pText wird am Ende angehaengt.
 @param pText der Text, der am Ende angehaengt wird
*/
	public abstract void haengeAn(String pText);
	
/**
 Das Zeichen pZeichen wird am Ende angehaengt.
 @param pZeichen das Zeichen, das am Ende angehaengt wird
*/
	public abstract void haengeAn(char pZeichen);
	
/**
 Das ganze Zahl pZahl wird am Ende angehaengt.
 @param pZahl die Zahl, die am Ende angehaengt wird
*/
	public abstract void haengeAn(int pZahl);
	
/**
 Die Kommazahl pZahl wird am Ende angehaengt.
 @param pZahl die Zahl, die am Ende angehaengt wird
*/
	public abstract void haengeAn(double pZahl);
	
/**
 Der im Textfeld markierte Text wird geliefert.
 @return der markierte Text
*/
	public abstract String markierterInhalt();
	
/**
 Der Text zwischen zwischen pAnfang und pEnde
 wird markiert.
@param pAnfang erste Stelle der Markierung
@param pEnde letzte Stelle der Markierung
*/
	public abstract void setzeMarkierung(int pAnfang, int pEnde);
	
/**
 Der gesamte Text wird markiert.
*/
	public abstract void markiereAlles();
	
/**
 Der gesamte Text wird nicht markiert.
*/
	public abstract void markiereNichts();
	
/**
 Der gesamte Text wird geloescht.
*/
	public void loescheAlles()
	{
		this.setzeInhalt("");
	}
	
/**
 Der markierte Text wird geloescht.
*/
	public abstract void loescheMarkierung();
	
/**
Der durch die Parameter bestimmte Text wird geloescht.
@param pAnfang erste Stelle
@param pEnde letzte Stelle
*/
	public abstract void loesche(int pAnfang, int pEnde);
	
/**
 Es wird zurueckgegeben, ob der Text markiert ist.
 @return true, wenn Text markiert ist
*/
	public abstract boolean istMarkiert();
	
/**
Es wird zurueckgegeben, von wo an der Text markiert ist.
@return die erste markierte Stelle
*/
	public abstract int markierungsAnfang();
	
/**
Es wird zurueckgegeben, bis wohin der Text markiert ist.
@return die letzte markierte Stelle
*/
	public abstract int markierungsEnde();
	
}
