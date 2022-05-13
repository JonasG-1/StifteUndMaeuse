package sum.komponenten;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import sum.ereignis.*;

/**
Eine Tabelle ist die eingedeutschte Klasse fuer Java-JTables. 
Tabellen haben Zeilen, Spalten und Zellen. Spalten besitzen &Uuml;berschriften,
die auch ausgeblendet werden k&ouml;nnen.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Tabelle extends Komponente implements Serializable, ScrollPaneConstants
{
    private String zInhaltGeaendertBearbeiter = "";
    private String zMarkierungGeaendertBearbeiter = "";
    private JTable hatTable;
    private JScrollPane hatScrollPane;
    private TableModel hatModel;
    protected String zAktuellFont = Schrift.STANDARDSCHRIFTART;
    protected int zSchriftStil =    Schrift.STANDARDSTIL;
    protected int zSchriftGroesse = Schrift.STANDARDGROESSE;
    protected Font zSchriftArt = Schrift.STANDARDSCHRIFT;
    
    private class TableDataReaktor implements TableModelListener
    {
        public void tableChanged(TableModelEvent e)
        {
            inhaltGeaendert();
            markierungGeaendert();
        }

    }
    
    private class AuswahlReaktor implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            markierungGeaendert();
        }
    }
    
    private class BereichFokusReaktor implements FocusListener
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
 Die Tabelle wird erzeugt und enthaelt den uebergebenen Text. Position, Breite
 und Hoehe sowie die Anzahl der Zeilen und Spalten werden als Parameter uebergeben.
 Die Tabelle befindet sich auf dem Bildschirm.
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
 @param pZeilen die Anzahl der Zeilen der Tabelle
 @param pSpalten die Anzahl der Spalten der Tabelle
*/
    public Tabelle(double pLinks, double pOben, double pBreite, double pHoehe, 
                   int pZeilen, int pSpalten)
    {       
        super();
        hatTable = new JTable(pZeilen, pSpalten);
        hatModel = hatTable.getModel();
        hatModel.addTableModelListener(new TableDataReaktor());
        hatTable.addFocusListener(new BereichFokusReaktor());
        hatTable.getSelectionModel().addListSelectionListener(new AuswahlReaktor());
        hatScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        hatScrollPane.setSize(new Dimension(80, 80));
        Bildschirm.topFenster.privatPanel().add(hatScrollPane, 0);
        hatScrollPane.setViewportView(hatTable);
        hatTable.setCellSelectionEnabled(true);
        hatTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.lerneKomponenteKennen(Bildschirm.topFenster, hatTable);
        this.init(pLinks, pOben, pBreite, pHoehe);
    }
    
/**
 Die Tabelle wird erzeugt und enthaelt den uebergebenen Text. Position, Breite
 und Hoehe sowie die Anzahl der Zeilen und Spalten werden als Parameter uebergeben.
 Die Tabelle befindet sich auf dem Fenster.
 @param pFenster das Fenster, das die Komponente enth&auml;lt
 @param pLinks der Abstand der Komponente vom linken Fensterrand
 @param pOben der Abstand der Komponente vom oberen Fensterrand
 @param pBreite die Breite der Komponente
 @param pHoehe die Hoehe der Komponente
 @param pZeilen die Anzahl der Zeilen der Tabelle
 @param pSpalten die Anzahl der Spalten der Tabelle
*/
    public Tabelle(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe, 
                   int pZeilen, int pSpalten)
    {       
        super();
        hatTable = new JTable(pZeilen, pSpalten);
        hatModel = hatTable.getModel();
        hatModel.addTableModelListener(new TableDataReaktor());
        hatTable.addFocusListener(new BereichFokusReaktor());
        hatTable.getSelectionModel().addListSelectionListener(new AuswahlReaktor());
        hatScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        hatScrollPane.setSize(new Dimension(80, 80));
        pFenster.privatPanel().add(hatScrollPane, 0);
        hatScrollPane.setViewportView(hatTable);
        hatTable.setCellSelectionEnabled(true);
        hatTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.lerneKomponenteKennen(pFenster, hatTable);
        this.init(pLinks, pOben, pBreite, pHoehe);
    }
    
/**
 Die Tabelle reagiert auf Veraenderungen.
*/
    protected void inhaltGeaendert()
    {
        Class sumEreignis;
        Class[] formparas = new Class[1];
        Method methode;
        Tabelle[] meineTabelle = new Tabelle[1];
        
        if (zInhaltGeaendertBearbeiter.length() > 0)
        {
            try
            {
                sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();
                try
                {
                    methode = sumEreignis.getDeclaredMethod(zInhaltGeaendertBearbeiter, null);
                    methode.setAccessible(true);
                    methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);         
                }
                catch (InvocationTargetException e0)
                {
                    System.out.println("Fehler in Methode \"" + zInhaltGeaendertBearbeiter + "\" einer Tabelle: " + e0.getTargetException().toString());
                    e0.printStackTrace();
                }
                catch (Exception e1)
                {
                    try
                    {
                        formparas[0] = Tabelle.class;
                        methode = sumEreignis.getDeclaredMethod(zInhaltGeaendertBearbeiter, formparas);
                        methode.setAccessible(true);
                        meineTabelle[0] = this;
                        methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meineTabelle);         
                    }
                    catch (InvocationTargetException e2)
                    {
                        System.out.println("Fehler in Methode \"" + zInhaltGeaendertBearbeiter + "\" einer Tabelle: " + e2.getTargetException().toString());
                        e2.printStackTrace();
                    }
                    catch (Exception e3)
                    {
                        System.out.println("Fehler: Methode \"" + zInhaltGeaendertBearbeiter + "\" einer Tabelle nicht gefunden.");
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
 Die Tabelle reagiert auf Veraenderungen der Markierung.
*/
    protected void markierungGeaendert()
    {
        Class sumEreignis;
        Class[] formparas = new Class[1];
        Method methode;
        Tabelle[] meineTabelle = new Tabelle[1];
        
        if (zMarkierungGeaendertBearbeiter.length() > 0)
        {
            try
            {
                sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();
                try
                {
                    methode = sumEreignis.getDeclaredMethod(zMarkierungGeaendertBearbeiter, null);
                    methode.setAccessible(true);
                    methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);         
                }
                catch (InvocationTargetException e0)
                {
                    System.out.println("Fehler in Methode \"" + zMarkierungGeaendertBearbeiter + "\" einer Tabelle: " + e0.getTargetException().toString());
                    e0.printStackTrace();
                }
                catch (Exception e1)
                {
                    try
                    {
                        formparas[0] = Tabelle.class;
                        methode = sumEreignis.getDeclaredMethod(zMarkierungGeaendertBearbeiter, formparas);
                        methode.setAccessible(true);
                        meineTabelle[0] = this;
                        methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meineTabelle);         
                    }
                    catch (InvocationTargetException e2)
                    {
                        System.out.println("Fehler in Methode \"" + zMarkierungGeaendertBearbeiter + "\" einer Tabelle: " + e2.getTargetException().toString());
                        e2.printStackTrace();
                    }
                    catch (Exception e3)
                    {
                        System.out.println("Fehler: Methode \"" + zMarkierungGeaendertBearbeiter + "\" einer Tabelle nicht gefunden.");
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
 Der Zeilenbereich erhaelt den Fokus.
*/
    protected void bekommtFokus()
    {
        Class sumEreignis;
        Class[] formparas = new Class[1];
        Method methode;
        Tabelle[] meineTabelle = new Tabelle[1];
        
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
                    System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" einer Tabelle: " + e0.getTargetException().toString());
                    e0.printStackTrace();
                }
                catch (Exception e1)
                {
                    try
                    {
                        formparas[0] = Tabelle.class;
                        methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), formparas);
                        methode.setAccessible(true);
                        meineTabelle[0] = this;
                        methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meineTabelle);         
                    }
                    catch (InvocationTargetException e2)
                    {
                        System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" einer Tabelle: " + e2.getTargetException().toString());
                        e2.printStackTrace();
                    }
                    catch (Exception e3)
                    {
                        System.out.println("Fehler: Methode \"" + this.fokusErhaltenBearbeiter() + "\" einer Tabelle nicht gefunden.");
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
 Die Tabelle verliert den Fokus.
*/
    protected void verliertFokus()
    {
        Class sumEreignis;
        Class[] formparas = new Class[1];
        Method methode;
        Tabelle[] meineTabelle = new Tabelle[1];
        
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
                    System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" einer Tabelle: " + e0.getTargetException().toString());
                    e0.printStackTrace();
                }
                catch (Exception e1)
                {
                    try
                    {
                        formparas[0] = Tabelle.class;
                        methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), formparas);
                        methode.setAccessible(true);
                        meineTabelle[0] = this;
                        methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meineTabelle);         
                    }
                    catch (InvocationTargetException e2)
                    {
                        System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" einer Tabelle: " + e2.getTargetException().toString());
                        e2.printStackTrace();
                    }
                    catch (Exception e3)
                    {
                        System.out.println("Fehler: Methode \"" + this.fokusVerlorenBearbeiter() + "\" einer Tabelle nicht gefunden.");
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
 Die Methode zur Bearbeitung des InhaltGeaendert-Ereignisses in
 der Ereignisanwendung wird festgelegt.
 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn sich der Inhalt der Tabelle aendert.
*/
    public void setzeBearbeiterInhaltGeaendert(String pBearbeiter)
    {
        zInhaltGeaendertBearbeiter = pBearbeiter;
    }
    
/**
 Die Methode zur Bearbeitung des InhaltGeaendert-Ereignisses in
 der Ereignisanwendung wird festgelegt.
 @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn sich die Markierung der Tabelle aendert.
*/
    public void setzeBearbeiterMarkierungGeaendert(String pBearbeiter)
    {
        zMarkierungGeaendertBearbeiter = pBearbeiter;
    }
    
/**
 Die Tabelle erhaelt eine neue Groesse.
 @param pBreite die neue Breite der Komponente
 @param pHoehe die neue Hoehe der Komponente
*/
    public void setzeGroesse(double pBreite, double pHoehe)
    {
        super.setzeGroesse(pBreite, pHoehe);
        hatScrollPane.setSize((int)pBreite, (int)pHoehe);
        hatScrollPane.revalidate();     
    }
    
/**
 Die Tabelle erhaelt eine neue Position (Ecke oben links).
 @param pWohinH die neue horizontale Position der linken oberen Ecke
 @param pWohinV die neue vertikale Position der linken oberen Ecke
*/
    public void setzePosition(double pWohinH, double pWohinV)
    {
        hatScrollPane.setLocation((int)pWohinH, (int)pWohinV);
    }
        
/**
 Die Zeilen der Tabelle erhalten eine neue Hoehe.
 @param pHoehe die neue Zeilenhoehe
*/
    public void setzeZeilenhoehe(int pHoehe)
    {
        hatTable.setRowHeight(pHoehe);
    }
        
/**
 Die Spalten der Tabelle erhalten eine neue Breite.
 @param pBreite die neue Spaltenbreite
*/
    public void setzeSpaltenbreite(int pBreite)
    {
        hatTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < hatTable.getColumnCount(); i++)
            hatTable.getColumnModel().getColumn(i).setPreferredWidth(pBreite);
    }
        
/**
 Die Anzahl der Zeilen im der Tabelle wird zurueckgegeben.
 @return die Anzahl der Zeilen der Tabelle
*/
    public int zeilenanzahl()
    {
        return hatTable.getRowCount();
    }
    
/**
 Die Anzahl der Spalten in der Tabelle wird zurueckgegeben.
 @return die Anzahl der Spalten der Tabelle
*/
    public int spaltenanzahl()
    {
        return hatTable.getColumnCount();
    }
    
/**
 Die Anzahl der Spalten in der Tabelle wird veraendert.
 @param pAnzahl die neue Anzahl der Spalten der Tabelle
*/
    public void setzeSpaltenanzahl(int pAnzahl)
    {
        ((DefaultTableModel) hatModel).setColumnCount(pAnzahl);
    }
    
/**
 Die Anzahl der Zeilen in der Tabelle wird veraendert.
 @param pAnzahl die neue Anzahl der Zeilen der Tabelle
*/
    public void setzeZeilenanzahl(int pAnzahl)
    {
        ((DefaultTableModel) hatModel).setRowCount(pAnzahl);
    }
    
/**
 Eine neue Spalte wird angehaengt.
*/
    public void haengeNeueSpalteAn()
    {
        hatTable.addColumn(new TableColumn());
    }
    
/**
 Eine neue Zeile wird angehaengt.
*/
    public void haengeNeueZeileAn()
    {
        ((DefaultTableModel) hatModel).addRow(new Vector());
    }
    
/**
 Eine neue Spalte wird Position pSpalte eingefuegt.
 @param pSpalte die Position, wo eine neue Spalte eingefuegt wird
*/
    public void fuegeNeueSpalteEinAn(int pSpalte)
    {
        this.haengeNeueSpalteAn();
        hatTable.moveColumn(hatTable.getColumnCount()-1, pSpalte - 1);
    }
    
/**
 Eine neue Zeile wird Position pZeile eingefuegt.
 @param pZeile die Position, wo eine neue Zeile eingefuegt wird
*/
    public void fuegeNeueZeileEinAn(int pZeile)
    {
        ((DefaultTableModel) hatModel).insertRow(pZeile - 1, new Vector());
    }
    
/**
 Die Spalte an Position pSpalte wird entfernt.
 @param pSpalte die Position, wo die Spalte entfernt wird
*/
    public void entferneSpalteAn(int pSpalte)
    {
        hatTable.removeColumn(hatTable.getColumnModel().getColumn(pSpalte - 1));
    }
    
/**
 Die Zeile an Position pZeile wird entfernt.
 @param pZeile die Position, wo die Zeile entfernt wird
*/
    public void entferneZeileAn(int pZeile)
    {
        ((DefaultTableModel) hatModel).removeRow(pZeile - 1);
    }
    
/**
 Der Text pText wird alsTitel fuer Spalte pSpalte eingefuegt.
 @param pText der Titel der Spalte
 @param pSpalte die Position der Spalte fuer den Titel
*/
    public void setzeSpaltentitelAn(String pText, int pSpalte)
    {
        hatTable.getColumnModel().getColumn(pSpalte - 1).setHeaderValue(pText);
//      hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
//      hatTable.validate();
    }
    
/**
 Der Spaltentitel der Spalte pSpalte wird zurueckgegeben.
 @param pSpalte die Position der Spalte fuer den Titel
 @return der Spaltentitel
*/
    public String spaltentitel(int pSpalte)
    {
        return hatTable.getColumnName(pSpalte - 1);
    }
    
/**
 Der Text pText wird in Zelle pSpalte, pZeile eingefuegt.
 @param pText der neue Inhalt der Zelle
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
*/
    public void setzeInhaltAn(String pText, int pZeile, int pSpalte)
    {
        hatTable.setValueAt(pText, pZeile - 1, pSpalte - 1);
//      hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
    }
    
/**
 Das Zeichen pZeichen wird in Zelle pSpalte, pZeile eingefuegt.
 @param pZeichen der neue Inhalt der Zelle
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
*/
    public void setzeInhaltAn(char pZeichen, int pZeile, int pSpalte)
    {
        this.setzeInhaltAn("" + pZeichen, pZeile, pSpalte);
    }
    
/**
 Die Zahl pZahl wird in Zelle pSpalte, pZeile eingefuegt.
 @param pZahl der neue Inhalt der Zelle
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
*/
    public void setzeInhaltAn(double pZahl, int pZeile, int pSpalte)
    {
        this.setzeInhaltAn("" + pZahl, pZeile, pSpalte);
    }
    
/**
 Die ganze Zahl pZahl wird in Zelle pSpalte, pZeile eingefuegt.
 @param pZahl der neue Inhalt der Zelle
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
*/
    public void setzeInhaltAn(int pZahl, int pZeile, int pSpalte)
    {
        this.setzeInhaltAn("" + pZahl, pZeile, pSpalte);
    }
    
/**
 Der Text pText wird in Zelle pSpalte, pZeile angehaengt.
 @param pText der angehaengte Text der Zelle
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
*/
    public void haengeAnAn(String pText, int pZeile, int pSpalte)
    {
        hatTable.setValueAt(((String) hatTable.getValueAt(pSpalte - 1, pZeile - 1)) + pText, pZeile - 1, pSpalte - 1);
    //  hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
    //  hatTable.validate();
    }
    
/**
Es wird zurueckgegeben, ob die Zelle an Position pZeile, pSpalte keine (ganze) Zahl ist.
@param pZeile die Zeilennummer der Zelle
@param pSpalte die Spaltennummer der Zelle
@return true, wenn der Inhalt der Zelle keine Zahl ist
*/
    public boolean inhaltIstTextAn(int pZeile, int pSpalte)
    {
        return !this.inhaltIstGanzeZahlAn(pZeile, pSpalte) && !this.inhaltIstZahlAn(pZeile, pSpalte);
    }
    
/**
Es wird zurueckgegeben, ob die Textkomponente eine ganze Zahl ist.
@param pZeile die Zeilennummer der Zelle
@param pSpalte die Spaltennummer der Zelle
@return true, wenn der Inhalt der Zelle eine ganze Zahl ist
*/
    public boolean inhaltIstGanzeZahlAn(int pZeile, int pSpalte)
    {
        try
        {
            Integer.valueOf(this.inhaltAlsTextAn(pZeile, pSpalte));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
/**
Es wird zurueckgegeben, ob die Textkomponente eine Kommazahl ist.
@param pZeile die Zeilennummer der Zelle
@param pSpalte die Spaltennummer der Zelle
@return true, wenn der Inhalt der Zelle eine Zahl ist
*/
    public boolean inhaltIstZahlAn(int pZeile, int pSpalte)
    {
        try
        {
            Double.valueOf(this.inhaltAlsTextAn(pZeile, pSpalte));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
/**
 Der Inhalt einer Zelle der Tabelle wird als String zurueckgegeben.
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
 @return der Inhalt der Zelle als Zeichenkette (String)
*/
    public String inhaltAlsTextAn(int pZeile, int pSpalte)
    {
        return (String) hatTable.getValueAt(pZeile - 1, pSpalte  - 1);
    }
        
/**
 Der Inhalt einer Zelle der Tabelle wird als ganze Zahl zurueckgegeben.
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
 @return der Inhalt der Zelle als ganze Zahl
*/
    public int inhaltAlsGanzeZahlAn(int pZeile, int pSpalte) throws ArithmeticException
    {
        if (this.inhaltIstGanzeZahlAn(pZeile, pSpalte))
            return Integer.parseInt(this.inhaltAlsTextAn(pZeile, pSpalte));
        else
            throw new ArithmeticException("inhaltAlsGanzeZahlAn: ist keine ganze Zahl");
    }
        
/**
 Der Inhalt einer Zelle der Tabelle wird als Kommazahl zurueckgegeben.
 @param pZeile die Zeilennummer der Zelle
 @param pSpalte die Spaltennummer der Zelle
 @return der Inhalt der Zelle als Kommazahl
*/
    public double inhaltAlsZahlAn(int pZeile, int pSpalte) throws ArithmeticException
    {
        if (this.inhaltIstZahlAn(pZeile, pSpalte))
        {
            Double d = new Double(this.inhaltAlsTextAn(pZeile, pSpalte));
            return d.doubleValue();
        }
        else
            throw new ArithmeticException("inhaltAlsZahlAn: ist keine Zahl");
    }
    
/**
    Die Zeilen von pAnfang bis pEnde
    werden zusaetzlich markiert.
    @param pAnfang die Nummer der ersten Zeile
    @param pEnde die Nummer der letzten Zeile
*/
    public void setzeMarkierteZeilen(int pAnfang, int pEnde)
    {
        hatTable.addRowSelectionInterval(pAnfang - 1, pEnde - 1);
    }
    
/**
    Die Spalten von pAnfang bis pEnde
    werden zusaetzlich markiert.
    @param pAnfang die Nummer der ersten Spalte
    @param pEnde die Nummer der letzten Spalte
*/
    public void setzeMarkierteSpalten(int pAnfang, int pEnde)
    {
        hatTable.addColumnSelectionInterval(pAnfang - 1, pEnde - 1);
    }
    
/**
    Die Zelle pZeile, pSpalte
    wird zusaetzlich markiert.
    @param pZeile die Nummer der Zeile
    @param pSpalte die Nummer der Spalte
*/
    public void setzeMarkierteZelle(int pZeile, int pSpalte)
    {
        this.setzeMarkierteSpalten(pSpalte, pSpalte);
        this.setzeMarkierteZeilen(pZeile, pZeile);
    }
    
/**
    Es wird zurueckgegeben, ob die Zelle markiert ist.
    @param pZeile die Nummer der Zeile
    @param pSpalte die Nummer der Spalte
    @return true, wenn die Zelle markiert ist
*/
    public boolean istZelleMarkiert(int pZeile, int pSpalte)
    {
        return hatTable.isCellSelected(pZeile - 1, pSpalte - 1);
    }
    
/**
    Es wird zurueckgegeben, ob die Zeile markiert ist.
    @param pZeile die Nummer der Zeile
    @return true, wenn die Zeile markiert ist
*/
    public boolean istZeileMarkiert(int pZeile)
    {
        return hatTable.isRowSelected(pZeile - 1);
    }
    
/**
    Es wird zurueckgegeben, ob die Spalte markiert ist.
    @param pSpalte die Nummer der Spalte
    @return true, wenn die Spalte markiert ist
*/
    public boolean istSpalteMarkiert(int pSpalte)
    {
        return hatTable.isColumnSelected(pSpalte - 1);
    }
    
/**
    Alle Zellen werden markiert.
*/
    public void markiereAlles()
    {
        hatTable.selectAll();
    }
    
/**
    Alle Zeilen werden nicht markiert.
*/
    public void markiereNichts()
    {
        hatTable.clearSelection();
    }
    
/**
    Die Schriftart der Tabelle wird veraendert
    @param pSchriftart die neue Schriftart der Tabelle (siehe Klasse Schrift)
*/
    public void setzeSchriftArt(String pSchriftart)
    {
        zAktuellFont = pSchriftart;
        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
        hatTable.setFont(zSchriftArt);   
    //  hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
    //  hatTable.validate();
    }
    
/**
    Die Schriftart der Tabelle wird veraendert
    @param pSchriftart die neue Schriftart der Tabelle (siehe Klasse Schrift)
*/
    public void setzeSchriftart(String pSchriftart)
    {
        this.setzeSchriftArt(pSchriftart);  
    }
    
/**
    Die Schriftgroesse der Tabelle wird veraendert
    @param pGroesse die neue Schriftgroesse der Tabelle
*/
    public void setzeSchriftgroesse(int pGroesse)
    {
        this.setzeSchriftGroesse(pGroesse);
    }
    
/**
    Die Schriftgroesse der Tabelle wird veraendert
    @param pGroesse die neue Schriftgroesse der Tabelle
*/
    public void setzeSchriftGroesse(int pGroesse)
    {
        zSchriftGroesse = pGroesse;
        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
        hatTable.setFont(zSchriftArt);   
        hatTable.setRowHeight(hatTable.getFontMetrics(hatTable.getFont()).getHeight());
//      hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
//      hatTable.validate();
    }
    
/**
    Der Schriftstil der Tabelle wird veraendert
    @param pStil der neue Schriftstil der Tabelle (siehe Klasse Schrift)
*/
    public void setzeSchriftStil(int pStil)
    {
        zSchriftStil = pStil;
        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
        hatTable.setFont(zSchriftArt);   
//      hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
//      hatTable.validate();
    }
    
/**
    Der Schriftstil der Tabelle wird veraendert
    @param pStil der neue Schriftstil der Tabelle (siehe Klasse Schrift)
*/
    public void setzeSchriftstil(int pStil)
    {
        this.setzeSchriftStil(pStil);
    }
    
/**
    Die Schriftfarbe der Tabelle wird veraendert
    @param pFarbe die neue Schriftfarbe der Tabelle
*/
    public void setzeSchriftFarbe(Color pFarbe)
    {
        hatTable.setForeground(pFarbe);
    //  hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
    //  hatTable.validate();
    }
    
/**
    Die Schriftfarbe der Tabelle wird veraendert
    @param pFarbe die neue Schriftfarbe der Tabelle
*/
    public void setzeSchriftfarbe(Color pFarbe)
    {
        this.setzeSchriftFarbe(pFarbe);
    }
    
/**
    Die Schriftfarbe der Tabelle wird veraendert
    @param pFarbe die neue Schriftfarbe der Tabelle
*/
    public void setzeSchriftfarbe(int pFarbe)
    {
        this.setzeSchriftFarbe(pFarbe);
    }
    
/**
    Die Schriftfarbe der Tabelle wird veraendert
    @param pFarbe die neue Schriftfarbe der Tabelle
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
    //  hatTable.paintImmediately(0, 0, hatTable.getWidth(), hatTable.getHeight());
    //  hatTable.validate();
    }
    
}
