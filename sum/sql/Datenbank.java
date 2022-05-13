package sum.sql;

import java.sql.*;

/**
 Die Klasse Datenbank stellt die Schnittstelle zu relationalen Datenbanken her.
 Diese Klasse wurde bisher nur mit MySQL-Datenbanken getestet.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
*/
public class Datenbank
{
    public static Connection hatVerbindung;
    private String hatProduktDaten;
    private String fehler = "SQL-Fehler!";
    private SQLErgebnisliste hatErgebnis;

    /**
     * Ein neues Datenbankobjekt wird erstellt. Es besitzt noch keine Verbindung
     * zu einer konkreten Datenbank.
     */
    public Datenbank()
    {
        hatVerbindung = null;
        hatErgebnis = null;
    }
    
    /**
     * interner Dienst
     */
    private String transaktionsStatus()
    {
        try
        {
            int action = hatVerbindung.getTransactionIsolation();
            switch (action)
            {
                case 0: return "TRANSACTION_NONE ";
                case 1: return "TRANSACTION_READ_COMMITTED ";
                case 2: return "TRANSACTION_READ_UNCOMMITTED ";
                case 3: return "TRANSACTION_REPEATABLE_READ  ";
                case 4: return "TRANSACTION_SERIALIZABLE  ";
            }
            return "Nicht definiert";     
        }
        catch (SQLException e)
        {
            System.out.println(fehler);
            System.out.println("Datenbank Transaktionsstatus: " + e);               
            return "DB-Fehler";             
        }
    }

    /**
     * Das Datenbankobjekt wird mit einer konkreten Datenbank verbunden.
     * Falls mit einer MySQL-Datenbank verbunden werden soll, ist der Dienst
     * verbindeMySQL geeigneter.
     * @param pDatenbank der Name der Datenbank
     * @param pTreiber der Name des Datenbanktreibers
     * @param pAnbindung die Anbindung zur Datenbank
     * @param pUser der "registrierte" Benutzer der Datenbank
     * @param pPwd das Passwort des Benutzers zum Zugriff auf die Datenbank
     * @return die Fehlermeldung, ein leerer String "", falls die Vebindung aufgebaut wurde
     */
    public String verbinde(String pDatenbank, String pTreiber, String pAnbindung, String pUser, String pPwd)
    {
        String lTreiber = pTreiber;
        try
        {
            Class.forName(lTreiber);
            hatVerbindung = DriverManager.getConnection(pAnbindung + pDatenbank, pUser, pPwd);
            hatProduktDaten = "Produkt: "+ hatVerbindung.getMetaData().getDatabaseProductName() +
                              " Version: "+ hatVerbindung.getMetaData().getDatabaseProductVersion();
            return "";
        }
        catch (Exception e)
        {
            System.out.println(fehler);
            System.out.println("Datenbank verbinden: " + e.toString());
            return "DB-Fehler: " + e.toString();             
        }
    }
    
    /**
     * Das Datenbankobjekt wird mit einer konkreten MySQL-Datenbank verbunden.
     * @param pDatenbank der Name der Datenbank
     * @param pServer der Server bzw. die IP-Nr des Datenbankservers
     * @param pUser der "registrierte" Benutzer der Datenbank
     * @param pPwd das Passwort des Benutzers zum Zugriff auf die Datenbank
     * @return die Fehlermeldung, ein leerer String "", falls die Vebindung aufgebaut wurde
     */
    public String verbindeMySQL(String pDatenbank, String pServer, String pUser, String pPwd)
    {
        return this.verbinde(pDatenbank, "com.mysql.jdbc.Driver", "jdbc:mysql://" + pServer + "/", pUser, pPwd);
    }  

    /**
     * Eine bestehende Verbindung zu einer konkreten Datenbank wird getrennt.
     */
    public void trenne()
    {
        try
        {
            hatVerbindung.close();
        }
        catch (SQLException e)
        {
            System.out.println(fehler);
            System.out.println("Datenbank trennen: " + e.toString());               
        }
    }
    
    /**
     * Die Anzahl der Tabellen in der Datenbank wird zur&uuml;ckgegeben
     * @return die Anzahl der Tabellen in der Datenbank
     */
    public int tabellenanzahl()
    {
        try
        {
            int lAnzahl = 0;
            ResultSet tab = hatVerbindung.getMetaData().getTables(null,null,null,null);
            while (tab.next())
                if ((tab.getString("Table_Type")).equals("TABLE"))
                    lAnzahl++;
            tab = null;
            return lAnzahl;
        }
        catch (SQLException e)
        {
            System.out.println("Datenbanktabellen: " + e);
            return -1;              
        }
    }

    /**
     * Der Name der entsprechenden Tabelle wird zur&uuml;ckgegeben.
     * @param pNummer die Nummer der Tabelle (beginnend bei 1)
     * @return der Name der entsprechenden Tabelle
     */
    public String tabellenname(int pNummer)
    {
        try
        {
            int lAnzahl = 0;
            String lName = "";  
            ResultSet tab = hatVerbindung.getMetaData().getTables(null, null, null, null);
            while (tab.next())
                if ((tab.getString("Table_Type")).equals("TABLE"))
                {
                    lAnzahl++;
                    if (lAnzahl == pNummer)
                        lName = tab.getString("Table_Name");
                }
            tab = null;
            return lName;
        }
        catch (SQLException e)
        {
            System.out.println("Datenbanktabellen: " + e);
            return "Keine Tabelle";             
        }
    }
    
    /**
     * Informationen zur aktuellen Datenbank werden zur&uuml;ckgegeben.
     * @return die Informationen zur Datenbank
     */
    public String datenbankinfo()
    {
        String c;
        try
        {
            if (hatVerbindung.getAutoCommit())
                c = "AUTO-COMMIT";
            else 
                c = "KEIN AUTO-COMMIT" ; 
            return hatProduktDaten + "\n" + "Status: " + transaktionsStatus() + "\n" + "Commit: " + c + "\n"
                    + "User: " + hatVerbindung.getMetaData().getUserName();
        }
        catch (SQLException e)
        {
            System.out.println(fehler);
            System.out.println("DatenbankInfo: " + e.toString());
            return "DB-Fehler: " + e.toString();             
        }
    }

    /**
     * Eine SQL-Anweisung wird an die Datenbank geschickt. Die Datenbank
     * liefert ein Ergebnis, das dann weiterverarbeitet werden kann.
     * @param pSQLAnweisung die SQL-Anweisung, die an die Datenbank geschickt wird
     * @return eine Fehlermeldung, falls die Anweisung korrekt war, wird ein leerer String "" zur&uuml;ckgegeben
     */
    public String sendeSQL(String pSQLAnweisung) 
    {
        if (hatErgebnis != null)
            hatErgebnis.gibFrei();      
        hatErgebnis= new SQLErgebnisliste(this, pSQLAnweisung);
        if (hatErgebnis != null)
            return "";
        else
        {
            System.out.println("sendeSQL: " + fehler);
            return fehler;
        }
    }
    
    /**
     * Die Ergebnisliste (Tabelle) zur letzten verschickten SQL-Anweisung wird zur&uuml;ckgegeben.
     * @return die Ergebnisliste
     */
    public SQLErgebnisliste ergebnis()
    {
        return hatErgebnis;
    }   
        
}
