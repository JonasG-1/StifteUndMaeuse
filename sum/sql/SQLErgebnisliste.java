package sum.sql;

import java.sql.*;
import sum.komponenten.*;

/**
 Die Klasse SQLErgebnisliste ist die Klasse f&uuml;r das Ergebnis einer SQL-Anfrage
 and die Datenbank. Die Ergebnisliste ist eine Tabelle, die zeilenweise verarbeitet werden kann.
 Die 0-te Zeile enth&auml;lt die Namen (Titel) der Spalten. Die Spaltennummern beginnen bei 1.
 Mit vor() kann zur n&auml;chsten Zeile weitergegangen werden.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
*/
public class SQLErgebnisliste
{
    // Objekte
    private Statement hatSQLAnweisung;
    private ResultSet hatSQLErgebnis;
    private ResultSetMetaData hatMetaDaten;
    
    // Attribute
    private String fehler = "SQL - Fehler!";
    private int zZeilenanzahl, zAktuelleZeile, zSpaltenanzahl;
    private String hatSQLAnfrage;
    private Datenbank kenntDatenbank;
    
    /**
     * Eine neue SQLErgebnisliste wird erzeugt.
     * @param pDatenbank die Datenbank, die die Anfrage erh&auml;lt
     * @param pSQLAnfrage die SQLAnfrage, die an die Datenbank geschickt wird
     */
    public SQLErgebnisliste(Datenbank pDatenbank, String pSQLAnfrage)
    {
        hatSQLErgebnis = null;
        zZeilenanzahl = 0;
        zSpaltenanzahl = 0;
        zAktuelleZeile = 0;
        kenntDatenbank = pDatenbank;
        hatSQLAnfrage = pSQLAnfrage;
        hatSQLErgebnis = this.bearbeiteAnweisung();
        this.zumAnfang();
            
    }
    
    /**
     * interner Dienst
     */
    private ResultSet bearbeiteAnweisung() 
    {
        ResultSet lErgebnis;
       
        try
        {
            hatSQLAnweisung = kenntDatenbank.hatVerbindung.createStatement();
            if (hatSQLAnweisung.execute(hatSQLAnfrage))
            {
                zZeilenanzahl = 0;
                lErgebnis = hatSQLAnweisung.executeQuery(hatSQLAnfrage);
                hatMetaDaten =  lErgebnis.getMetaData();
                zSpaltenanzahl = hatMetaDaten.getColumnCount();
                while (lErgebnis.next()) 
                    zZeilenanzahl++;
                lErgebnis.close();
                hatSQLAnweisung.close();
                hatSQLAnweisung = kenntDatenbank.hatVerbindung.createStatement();
                lErgebnis = hatSQLAnweisung.executeQuery(hatSQLAnfrage);
                return  lErgebnis;
            }
            else
            {
                zSpaltenanzahl = 0;
                zZeilenanzahl = 0;
                zAktuelleZeile = 0;
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println("Keine Ergebnisliste erhalten!");
            System.out.println(e.toString());
            return null;            
        }
    }
    
    /**
     * Es wird zum Anfang der Ergebnisliste gesprungen. Die n&auml;chsten Anfragen
     * beziehen sich auf die erste Zeile der Tabelle.
     */
    public void zumAnfang()
    {
        zAktuelleZeile = 0;
        try
        { 
            if (hatSQLErgebnis != null)
            {
                hatSQLErgebnis.close();
                hatSQLAnweisung.close();
                hatSQLErgebnis = this.bearbeiteAnweisung(); 
                this.vor();
                zAktuelleZeile = 1;
            }
        }
        catch (Exception e)
        {  
            System.out.println(e.toString());
        }
    }
    
    /**
     * Es wird zur n&auml;chsten Zeile der Ergebnisliste weitergegangen.
     */
    public void vor()
    {
        zAktuelleZeile++;
        if (!this.istDahinter())
        {
            try
            {
                hatSQLErgebnis.next();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Es wurde bis hinter die letzte Zeile der Ergebnisliste weitergegangen.
     */
    public boolean istDahinter()
    {
        if (zAktuelleZeile <= zZeilenanzahl)
            return false;
        else
            return true;
    }
    
    /**
     * Die Anzahl der Zeilen in der Ergebnisliste wird ermittelt.
     * @return die Anzahl der Zeilen der Ergebnisliste
     */
    public int zeilenanzahl()
    {
        return zZeilenanzahl;
    }

    /**
     * Die Nummer der aktuellen Zeile in der Ergebnisliste wird zur&uuml;ckgegeben.
     * @return die Nummer der aktuellen Zeile der Ergebnisliste
     */
    public int aktuelleZeile()
    {
        return zAktuelleZeile;
    }
    
    /**
     * Die Anzahl der Spalten in der Ergebnisliste (Tabelle) wird zur&uuml;ckgegeben.
     * @return die Anzehl der Spalten der Tabelle
     */
    public int spaltenanzahl()
    {
        return zSpaltenanzahl;
    }

    /**
     * Der Typ einer Spalte wird zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der Spalte
     * @return der Typ der untersuchten Spalte in der Ergebnisliste
     */
    public String spaltentyp(int pSpalte)
    {
        try
        {       
            if (pSpalte <= zSpaltenanzahl)
                return hatMetaDaten.getColumnTypeName(pSpalte);
            else 
                return fehler;
        }
        catch (Exception e)
        { 
            return fehler;
        }
    }

    /**
     * Der Bezeichner der Spalte wird zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der Spalte
     * @return der Name (Titel) der entsprechenden Spalte
     */
    public String spaltenname(int pSpalte)
    {
        try
        {       
            String name ="";
            if (pSpalte <= zSpaltenanzahl)
                name = hatMetaDaten.getColumnName(pSpalte);
            return name;
        }
        catch (Exception e)
        { 
            return fehler;
        }
    }

    /**
     * Die Nummer der Spalte zu dem entsprechenden Namen (Titel)
     * wird zur&uuml;ckgegeben. Falls es keine Spalte mit diesem Namen gibt, 
     * wird -1 zur&uuml;ckgegeben.
     * @param pSpaltenname der entsprechenden Spalte
     * @return die Nummer der Spalte (beginnend bei 1)
     */
    public int spaltennummer(String pSpaltenname)
    {
        try
        {       
            return hatSQLErgebnis.findColumn(pSpaltenname);
        }
        catch (Exception e)
        { 
            return -1;
        }
    }

    /**
     * Die Breite der Spalte in Pixeln
     * wird zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der entsprechenden Spalte
     * @return die Breite der Spalte
     */
    public int spaltenbreite(int pSpalte)
    {
        try
        {
             return hatMetaDaten.getColumnDisplaySize(pSpalte);
        }
        catch (Exception e)
        {
            return -1;
        }
    }
    
    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als String zur&uuml;ckgegeben.
     * @param pSpaltenname die Bezeichnung der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public String datenfeldAlsText(String pSpaltenname) 
    {
        try
        {
            Object lObjekt;
            lObjekt = hatSQLErgebnis.getObject(pSpaltenname);
            return lObjekt.toString();
        }
        catch (Exception e)
        {
            return fehler + " " + e.toString();             
        }
    }
    
    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als String zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public String datenfeldAlsText(int pSpalte) 
    {
        try
        {
            Object lObjekt;
            lObjekt = hatSQLErgebnis.getObject(pSpalte);
            if (lObjekt == null)
                return "null";
            else
                return lObjekt.toString();
        }
        catch (Exception e)
        {
            return fehler + " " + e.toString();             
        }
    }

    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als Kommazahl zur&uuml;ckgegeben.
     * @param pDatenfeldname die Bezeichnung der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public double datenfeldAlsZahl(String pDatenfeldname)
    {
        try
        {
            return hatSQLErgebnis.getDouble(pDatenfeldname);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());               
            return 0;               
        }
    }
    
    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als Kommazahl zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public double datenfeldAlsZahl(int pSpalte)
    {
        try
        {
            return hatSQLErgebnis.getDouble(pSpalte);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return 0;               
        }

    }

    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als ganze Zahl zur&uuml;ckgegeben.
     * @param pSpaltenname die Bezeichnung der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public int datenfeldAlsGanzeZahl(String pSpaltenname)
    {
        try
        {
            return hatSQLErgebnis.getInt(pSpaltenname);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return 0;               
        }
    }
    
    /**
     * Der Inhalt der Zelle in der entsprechenden Spalte in der aktuellen Zeile
     * wird als Kommazahl zur&uuml;ckgegeben.
     * @param pSpalte die Nummer der entsprechenden Spalte
     * @return der Inhalt der Zelle
     */
    public int datenfeldAlsGanzeZahl(int pSpalte)
    {
        try
        {
            return hatSQLErgebnis.getInt(pSpalte);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return 0;               
        }
    }
    
    /**
     * Der Inhalt der Ergebnisliste (Tabelle) wird in eine Tabelle (Komponente)
     * eingetragen.
     * @param pTabelle die Tabelle (Komponente), die gef&uuml;llt wird
     */
    public void alsTabelle(Tabelle pTabelle)
    {
        int altZeile, aktZeile;
        
        altZeile = zAktuelleZeile;
        if (this.spaltenanzahl() > 0)
        {
            pTabelle.setzeSpaltenanzahl(this.spaltenanzahl());
            pTabelle.setzeZeilenanzahl(this.zeilenanzahl());
            for (int spalte = 1; spalte <= this.spaltenanzahl(); spalte++) 
                pTabelle.setzeSpaltentitelAn(this.spaltenname(spalte), spalte);
            this.zumAnfang();
            aktZeile = 0;
            while (!this.istDahinter())
            {
                aktZeile++;
                for (int spalte = 1; spalte <= this.spaltenanzahl(); spalte++) 
                    pTabelle.setzeInhaltAn(this.datenfeldAlsText(spalte), aktZeile, spalte);
                this.vor();
            }
            this.zumAnfang();
            while (altZeile > zAktuelleZeile)
                this.vor();
        }
    }
    
    /**
     * Die Ergebnisliste wird freigegeben.
     */
    public void gibFrei()
     {
        try
        {
            if (hatSQLErgebnis != null)
                hatSQLErgebnis.close();
            if (hatSQLAnweisung != null)
                hatSQLAnweisung.close();
            hatSQLErgebnis = null;
            hatSQLAnweisung = null;
            System.gc();
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
     }
    
}
    
