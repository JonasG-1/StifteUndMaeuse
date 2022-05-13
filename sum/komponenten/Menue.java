package sum.komponenten;import javax.swing.*;import java.awt.event.*;import java.awt.Font;import java.awt.Color;import java.io.*;import java.lang.reflect.*;import sum.ereignis.*;/**Ein Menue ist die Klasse fuer Java-Menues (Fenster-Menues). Menuesreagieren auf einen Mausklick.@author Bernard Schriek, Horst Hildebrecht@version 7.5 vom 29.10.2013*/public class Menue extends Textkomponente implements Serializable{    private ActionListener hatListener;    private JMenu hatMenu;        private class MenueReaktor implements ActionListener    {        public void actionPerformed(ActionEvent e)        {            String cmd = e.getActionCommand();            gewaehlt(cmd);        }    }    /**Das Menue mit dem angegebenen Titel wird im Menuebar erzeugt.@param pTitel der Titel des Menues*/    public Menue(String pTitel)    {        this(pTitel, null);    }    /**Das Menue mit dem angegebenen Titel und Obermenue wird erzeugt.Ist kein Obermenue angegeben, wird es im Menuebar erzeugt.*/    protected Menue(String pTitel, JMenu pObermenu)    {        JMenuBar lMenuBar;        lMenuBar = Bildschirm.topFenster.getJMenuBar();        hatMenu = new JMenu(pTitel);        hatListener = new MenueReaktor();        hatMenu.addActionListener(hatListener);        if (pObermenu == null)        {            if (lMenuBar == null)            {                lMenuBar = new JMenuBar();                Bildschirm.topFenster.setJMenuBar(lMenuBar);            }            lMenuBar.add(hatMenu);        }        else            pObermenu.add(hatMenu);        lMenuBar.setVisible(false);        lMenuBar.setVisible(true);    }    /**Das Menue reagiert auf einen Mausklick.*/    protected void gewaehlt(String pAuftrag)    {        Class sumEreignis;        Class[] formparas = new Class[1];        Method methode;        Menue[] meineAuswahl = new Menue[1];                if (pAuftrag.length() > 0)        {            try            {                sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();                try                {                    methode = sumEreignis.getDeclaredMethod(pAuftrag, null);                    methode.setAccessible(true);                    methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);                         }                catch (InvocationTargetException e0)                {                    System.out.println("Fehler in Methode \"" + pAuftrag + "\" eines Menues: " + e0.getTargetException().toString());                    e0.printStackTrace();                }                catch (Exception e1)                {                    try                    {                        formparas[0] = Menue.class;                        methode = sumEreignis.getDeclaredMethod(pAuftrag, formparas);                        methode.setAccessible(true);                        meineAuswahl[0] = this;                        methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meineAuswahl);                             }                    catch (InvocationTargetException e2)                    {                        System.out.println("Fehler in Methode \"" + pAuftrag + "\" eines Menues: " + e2.getTargetException().toString());                        e2.printStackTrace();                    }                    catch (Exception e3)                    {                        System.out.println("Fehler: Methode \"" + pAuftrag + "\" eines Menues nicht gefunden.");                    }                }            }            catch (Exception e4)            {                System.out.println("SuMAnwendung: " + e4.toString());            }        }    }    /**Eine neue Zeile wird an das Menue angehaengt.@param pText der Inhalt der Menuezeile@param pAuftrag der Name des Dienstes der SuMAnwendung, der durch die Menuezeile aufgerufen wird*/    public void haengeZeileAn(String pText, String pAuftrag)    {        JMenuItem mi;                mi = new JMenuItem(pText);        mi.addActionListener(hatListener);        mi.setActionCommand(pAuftrag);        hatMenu.add(mi);    }/**Eine neue Zeile wird mit einer Beschleunigertaste an das Menue angehaengt.@param pText der Inhalt der Menuezeile@param pZeichen das Beschleunigerzeichen fuer die Menuezeile@param pMitShift bei true wird zwischen Gross- und Kleinschrift des Beschleunigerzeichens unterschieden@param pAuftrag der Name des Dienstes der SuMAnwendung, der durch die Menuezeile aufgerufen wird*/    public void haengeZeileAn(String pText, char pZeichen, boolean pMitShift, String pAuftrag)    {        JMenuItem mi;        KeyStroke msc;                msc = KeyStroke.getKeyStroke((int)pZeichen, java.awt.event.InputEvent.SHIFT_MASK, false);        mi = new JMenuItem(pText);        mi.setAccelerator(msc);        mi.addActionListener(hatListener);        mi.setActionCommand(pAuftrag);        hatMenu.add(mi);    }/** Das Menue erhaelt einen neuen Titel. @param pText der neue Titel des Menues*/    public void setzeInhalt(String pText)    {        hatMenu.setText(pText);    }    /**    Der Titel des Menues wird als String zurueckgegeben.    @return der Titel des Menues*/    public String inhaltAlsText()    {        return hatMenu.getText();    }    /** Die Methode zur Bearbeitung des Gewaehlt-Ereignisses f&uuml;r eine Menuezeile in der Ereignisanwendung wird festgelegt. @param pZeile die Zeile des Menues fuer die der Dienst festgelegt wird @param pAuftrag der Dienst, der mit der Menuezeile aufgerufen wird*/    public void setzeBearbeiterGewaehlt(int pZeile, String pAuftrag)    {        hatMenu.getItem(pZeile).setActionCommand(pAuftrag);    }    /**Ein Untermenue wird an das Menue angehaengt.@param pText der Titel des Untermenues@return das neue Untermenue*/    public Menue neuesUntermenue(String pText)    {        return new Menue(pText, hatMenu);    }/**Eine Trennzeile wird an das Menue angehaengt.*/    public void haengeTrennungAn()    {        hatMenu.addSeparator();    }/**Die momentane Anzahl der Zeilen des Menues wird abgefragt.@return die Anzahl der Zeilen des Menues*/    public int zeilenAnzahl()    {        return hatMenu.getItemCount();    }    /**Das Menue wird deaktiviert und reagiert nicht mehr.*/    public void deaktiviere()    {        hatMenu.setEnabled(false);    }    /**Das Menue wird aktiviert.*/    public void aktiviere()    {        hatMenu.setEnabled(true);    }    /**Es wird zurueckgegeben, ob das Menue aktiviert ist.@return true, wenn das Menue aktiviert ist*/    public boolean istAktiv()    {        return hatMenu.isEnabled();    }    /**Eine Menuezeile wird deaktiviert und reagiert nicht mehr.@param pZeile die deaktivierte Zeile*/    public void deaktiviereZeile(int pZeile)    {        hatMenu.getItem(pZeile).setEnabled(false);    }    /**Eine Menuezeile wird aktiviert.@param pZeile die aktivierte Zeile*/    public void aktiviereZeile(int pZeile)    {        hatMenu.getItem(pZeile).setEnabled(true);    }    /**Es wird zurueckgegeben, ob eine Menuezeile aktiviert ist.@param pZeile die untersuchte Zeile@return true, wenn die Zeile aktiviert ist*/    public boolean istZeileAktiv(int pZeile)    {        return hatMenu.getItem(pZeile).isEnabled();    }    /**Die Schriftart des Menues wird veraendert@param pSchriftart die neue Schrift des Menues (siehe Klasse Schrift)*/    public void setzeSchriftArt(String pSchriftart)    {        zAktuellFont = pSchriftart;        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);        hatMenu.setFont(zSchriftArt);       }    /**    Die Schriftgroesse des Menues wird veraendert    @param pGroesse die neue Groesse der Menueschrift*/    public void setzeSchriftGroesse(int pGroesse)    {        zSchriftGroesse = pGroesse;        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);        hatMenu.setFont(zSchriftArt);       }    /**    Der Schriftstil des Menues wird veraendert    @param pStil der neue Schriftstil des Menues (siehe Klasse Schrift)*/    public void setzeSchriftStil(int pStil)    {        zSchriftStil = pStil;        zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);        hatMenu.setFont(zSchriftArt);       }    /**    Der Schriftstil des Menues wird veraendert    @param pStil der neue Schriftstil des Menues (siehe Klasse Schrift)*/    public void setzeSchriftstil(int pStil)    {        this.setzeSchriftStil(pStil);    }        /**    Die Schriftfarbe des Menues kann nicht veraendert werden.    Leerer Dienst.    */    public void setzeSchriftFarbe(Color pFarbe)    {}        /*     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    protected void lerneKomponenteKennen(JComponent pKomponente)    {}        /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzeBearbeiterFokusVerloren(String pBearbeiter)    {}            /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzeBearbeiterFokusErhalten(String pBearbeiter)    {}    /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzePosition(double pWohinH, double pWohinV)    {}            /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzeGroesse(double pBreite, double pHoehe)    {}        /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzeFarbe(Color pFarbe)     {}            /**     Die Anfrage kann fuer ein Menue nicht angewandt werden und liefert 0.    */    public int links()    {        return 0;    }        /**     Die Anfrage kann fuer ein Menue nicht angewandt werden und liefert 0.    */    public int oben()    {        return 0;    }        /**     Die Anfrage kann fuer ein Menue nicht angewandt werden und liefert 0.    */    public int breite()    {        return 0;    }        /**     Die Anfrage kann fuer ein Menue nicht angewandt werden und liefert 0.    */    public int hoehe()    {        return 0;    }        /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void verstecke()    {}            /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void zeige()    {}            /**     Die Anfrage kann fuer ein Menue nicht angewandt werden und liefert true.    */    public boolean istSichtbar()    {        return true;    }    /**     Die Methode kann fuer ein Menue nicht angewandt werden und ist daher leer.    */    public void setzeFokus()     {}}