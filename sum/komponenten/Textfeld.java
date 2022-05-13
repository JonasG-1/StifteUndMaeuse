package sum.komponenten;import javax.swing.*;import javax.swing.text.*;import javax.swing.event.*;import java.awt.event.*;import java.io.*;import java.lang.reflect.*;import sum.ereignis.*;/**Ein Textfeld ist die Klasse fuer Java-TextFields. Textfeldersind Editoren mit einer festen Breite und Hoehe. Im Gegensatz zu Textbereichenhaben  sie keinen Rollbalken und bestehen nur aus einer einzigen Zeile.Sie reagieren auf Texteingaben und Mausklicks. Wenn etwas in ein Textfeldgetippt werden soll, muss es vorher mit einem Mausklick aktiviert werden,d.h. das Textfeld muss den Fokus haben.@author Bernard Schriek, Horst Hildebrecht@version 7.5 vom 29.10.2013*/public class Textfeld extends Markierungskomponente implements Serializable{  	protected String zEingabeBestaetigtBearbeiter = "";  	protected JTextField hatTextField;		private class DokumentReaktor implements DocumentListener	{		public void insertUpdate(DocumentEvent e)		{			inhaltGeaendert();			markierungGeaendert();		}		public void removeUpdate(DocumentEvent e)		{			inhaltGeaendert();			markierungGeaendert();		}		public void changedUpdate(DocumentEvent e)		{			inhaltGeaendert();			markierungGeaendert();		}	}		private class FeldMausReaktor implements MouseMotionListener	{		public void mouseDragged(MouseEvent e)		{			markierungGeaendert();		}				public void mouseMoved(MouseEvent e)		{}	}		private class FeldTastenReaktor implements KeyListener	{		public void keyTyped(KeyEvent e)		{}		public void keyPressed(KeyEvent e)		{			if (e.getKeyCode() == KeyEvent.VK_ENTER)				eingabeBestaetigt();		}				public void keyReleased(KeyEvent e)		{}	}		private class FeldFokusReaktor implements FocusListener	{		public void focusGained(FocusEvent e)				{			bekommtFokus();		}				public void focusLost(FocusEvent e)				{			verliertFokus();		}	}		/** Das Textfeld wird erzeugt und enthaelt den uebergebenen Text. Position, Breite und  Hoehe werden als Parameter uebergeben. Das Textfeld befindet sich auf dem Bildschirm. @param pLinks der Abstand der Komponente vom linken Fensterrand @param pOben der Abstand der Komponente vom oberen Fensterrand @param pBreite die Breite der Komponente @param pHoehe die Hoehe der Komponente @param pText der Inhalt der Komponente*/	public Textfeld(double pLinks, double pOben, double pBreite, double pHoehe, String pText)	{		hatTextField = new JTextField();		hatTextField.setOpaque(true);		hatTextField.getDocument().addDocumentListener(new DokumentReaktor());		hatTextField.addMouseMotionListener(new FeldMausReaktor());		hatTextField.addFocusListener(new FeldFokusReaktor());		hatTextField.addKeyListener(new FeldTastenReaktor());		Bildschirm.topFenster.privatPanel().add(hatTextField, 0);		this.lerneKomponenteKennen(Bildschirm.topFenster, hatTextField);		this.init(pLinks, pOben, pBreite, pHoehe, pText);	}	/** Das Textfeld wird erzeugt und enthaelt den uebergebenen Text. Position, Breite und  Hoehe werden als Parameter uebergeben. Das Textfeld befindet sich auf dem Fenster. @param pFenster das Fenster, das die Komponente enth&auml;lt @param pLinks der Abstand der Komponente vom linken Fensterrand @param pOben der Abstand der Komponente vom oberen Fensterrand @param pBreite die Breite der Komponente @param pHoehe die Hoehe der Komponente @param pText der Inhalt der Komponente*/	public Textfeld(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe, String pText)	{		hatTextField = new JTextField();		hatTextField.setOpaque(true);		hatTextField.getDocument().addDocumentListener(new DokumentReaktor());		hatTextField.addMouseMotionListener(new FeldMausReaktor());		hatTextField.addFocusListener(new FeldFokusReaktor());		hatTextField.addKeyListener(new FeldTastenReaktor());		pFenster.privatPanel().add(hatTextField, 0);		this.lerneKomponenteKennen(pFenster, hatTextField);		this.init(pLinks, pOben, pBreite, pHoehe, pText);	}	/** Dummy-Konstruktor f&uuml;r die Unterklasse Kennwortfeld.*/	public Textfeld()	{	}	/** Die Methode zur Bearbeitung des EingabeBestaetigt-Ereignisses in der Ereignisanwendung wird festgelegt. @param pBearbeiter der Bezeichner des Dienstes der SuMAnwendung, der aufgerufen wird, wenn in der Komponente Enter gedrueckt wurde.*/	public void setzeBearbeiterEingabeBestaetigt(String pBearbeiter)	{		zEingabeBestaetigtBearbeiter = pBearbeiter;	}		/** Das Textfeld reagiert auf Veraenderungen.*/	protected void inhaltGeaendert()	{		Class sumEreignis;		Class[] formparas = new Class[1];		Method methode;		Textfeld[] meinTextfeld = new Textfeld[1];				if (zInhaltGeaendertBearbeiter.length() > 0)		{			try			{				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();				try				{					methode = sumEreignis.getDeclaredMethod(zInhaltGeaendertBearbeiter, null);					methode.setAccessible(true);					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);							}				catch (InvocationTargetException e0)				{					System.out.println("Fehler in Methode \"" + zInhaltGeaendertBearbeiter + "\" eines Textfelds: " + e0.getTargetException().toString());					e0.printStackTrace();				}				catch (Exception e1)				{					try					{						formparas[0] = Textfeld.class;						methode = sumEreignis.getDeclaredMethod(zInhaltGeaendertBearbeiter, formparas);						methode.setAccessible(true);						meinTextfeld[0] = this;						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinTextfeld);								}					catch (InvocationTargetException e2)					{						System.out.println("Fehler in Methode \"" + zInhaltGeaendertBearbeiter + "\" eines Textfelds: " + e2.getTargetException().toString());						e2.printStackTrace();					}					catch (Exception e3)					{						System.out.println("Fehler: Methode \"" + zInhaltGeaendertBearbeiter + "\" eines Textfelds nicht gefunden.");					}				}			}			catch (Exception e4)			{				System.out.println("SuMAnwendung: " + e4.toString());			}		}	}	/** Das Textfeld reagiert auf die Eingabebestaetigung (Enter).*/	protected void eingabeBestaetigt()	{		Class sumEreignis;		Class[] formparas = new Class[1];		Method methode;		Textfeld[] meinTextfeld = new Textfeld[1];				if (zEingabeBestaetigtBearbeiter.length() > 0)		{			try			{				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();				try				{					methode = sumEreignis.getDeclaredMethod(zEingabeBestaetigtBearbeiter, null);					methode.setAccessible(true);					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);							}				catch (InvocationTargetException e0)				{					System.out.println("Fehler in Methode \"" + zEingabeBestaetigtBearbeiter + "\" eines Textfelds: " + e0.getTargetException().toString());					e0.printStackTrace();				}				catch (Exception e1)				{					try					{						formparas[0] = Textfeld.class;						methode = sumEreignis.getDeclaredMethod(zEingabeBestaetigtBearbeiter, formparas);						methode.setAccessible(true);						meinTextfeld[0] = this;						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinTextfeld);								}					catch (InvocationTargetException e2)					{						System.out.println("Fehler in Methode \"" + zEingabeBestaetigtBearbeiter + "\" eines Textfelds: " + e2.getTargetException().toString());						e2.printStackTrace();					}					catch (Exception e3)					{						System.out.println("Fehler: Methode \"" + zEingabeBestaetigtBearbeiter + "\" eines Textfelds nicht gefunden.");					}				}			}			catch (Exception e4)			{				System.out.println("SuMAnwendung: " + e4.toString());			}		}	}	/** Das Textfeld reagiert auf Veraenderungen der Markierung.*/	protected void markierungGeaendert()	{		Class sumEreignis;		Class[] formparas = new Class[1];		Method methode;		Textfeld[] meinTextfeld = new Textfeld[1];				if (zMarkierungGeaendertBearbeiter.length() > 0)		{			try			{				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();				try				{					methode = sumEreignis.getDeclaredMethod(zMarkierungGeaendertBearbeiter, null);					methode.setAccessible(true);					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);							}				catch (InvocationTargetException e0)				{					System.out.println("Fehler in Methode \"" + zMarkierungGeaendertBearbeiter + "\" eines Textfelds: " + e0.getTargetException().toString());					e0.printStackTrace();				}				catch (Exception e1)				{					try					{						formparas[0] = Textfeld.class;						methode = sumEreignis.getDeclaredMethod(zMarkierungGeaendertBearbeiter, formparas);						methode.setAccessible(true);						meinTextfeld[0] = this;						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinTextfeld);								}					catch (InvocationTargetException e2)					{						System.out.println("Fehler in Methode \"" + zMarkierungGeaendertBearbeiter + "\" eines Textfelds: " + e2.getTargetException().toString());						e2.printStackTrace();					}					catch (Exception e3)					{						System.out.println("Fehler: Methode \"" + zMarkierungGeaendertBearbeiter + "\" eines Textfelds nicht gefunden.");					}				}			}			catch (Exception e4)			{				System.out.println("SuMAnwendung: " + e4.toString());			}		}	}	/** Das Textfeld erhaelt den Fokus.*/	protected void bekommtFokus()	{		Class sumEreignis;		Class[] formparas = new Class[1];		Method methode;		Textfeld[] meinTextfeld = new Textfeld[1];				this.setzeFokusWert(true);		if (this.fokusErhaltenBearbeiter().length() > 0)		{			try			{				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();				try				{					methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), null);					methode.setAccessible(true);					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);							}				catch (InvocationTargetException e0)				{					System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" eines Textfelds: " + e0.getTargetException().toString());					e0.printStackTrace();				}				catch (Exception e1)				{					try					{						formparas[0] = Textfeld.class;						methode = sumEreignis.getDeclaredMethod(this.fokusErhaltenBearbeiter(), formparas);						methode.setAccessible(true);						meinTextfeld[0] = this;						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinTextfeld);								}					catch (InvocationTargetException e2)					{						System.out.println("Fehler in Methode \"" + this.fokusErhaltenBearbeiter() + "\" eines Textfelds: " + e2.getTargetException().toString());						e2.printStackTrace();					}					catch (Exception e3)					{						System.out.println("Fehler: Methode \"" + this.fokusErhaltenBearbeiter() + "\" eines Textfelds nicht gefunden.");					}				}			}			catch (Exception e4)			{				System.out.println("SuMAnwendung: " + e4.toString());			}		}	}	/** Das Textfeld verliert den Fokus.*/	protected void verliertFokus()	{		Class sumEreignis;		Class[] formparas = new Class[1];		Method methode;		Textfeld[] meinTextfeld = new Textfeld[1];				this.setzeFokusWert(false);		if (this.fokusVerlorenBearbeiter().length() > 0)		{			try			{				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung.getClass();				try				{					methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), null);					methode.setAccessible(true);					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, null);							}				catch (InvocationTargetException e0)				{					System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" eines Textfelds: " + e0.getTargetException().toString());					e0.printStackTrace();				}				catch (Exception e1)				{					try					{						formparas[0] = Textfeld.class;						methode = sumEreignis.getDeclaredMethod(this.fokusVerlorenBearbeiter(), formparas);						methode.setAccessible(true);						meinTextfeld[0] = this;						methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung, meinTextfeld);								}					catch (InvocationTargetException e2)					{						System.out.println("Fehler in Methode \"" + this.fokusVerlorenBearbeiter() + "\" eines Textfelds: " + e2.getTargetException().toString());						e2.printStackTrace();					}					catch (Exception e3)					{						System.out.println("Fehler: Methode \"" + this.fokusVerlorenBearbeiter() + "\" eines Textfelds nicht gefunden.");					}				}			}			catch (Exception e4)			{				System.out.println("SuMAnwendung: " + e4.toString());			}		}	}		/** Ds Textfeld erhaelt einen neuen Inhalt. @param pText der Text, der als neuer Inhalt gesetzt wird*/	public void setzeInhalt(String pText)	{		hatTextField.setText(pText);	}	/**Der Text des Textfeldes wird als String zurueckgegeben.@return der Inhalt der Komponente als Zeichenkette (String)*/	public String inhaltAlsText()	{		return hatTextField.getText();	}		/**Der Inhaltdes Textfeldes von pAnfang bis pEnde wird als String zurueckgegeben.@param pAnfang erste Stelle@param pEnde letzte Stelle@return der Inhalt zwischen den gew&auml;hlten Positionen der Komponente als Zeichenkette (String)*/	public String teilinhalt(int pAnfang, int pEnde)	{		String s;				s = hatTextField.getText();		return s.substring(pAnfang - 1, pEnde);	}	/** Der Text pText wird an Position pStelle eingefuegt. @param pText der Text, der eingefuegt wird @param pStelle die Stelle, wo der Text eingefuegt wird*/	public void fuegeEin(String pText, int pStelle)	{		String s, s1, s2;				s = hatTextField.getText();		s1 = s.substring(0, pStelle - 1);		s2 = s.substring(pStelle - 1, s.length());		this.setzeInhalt(s1 + pText + s2);	}	/** Der Text pText wird am Ende angehaengt. @param pText der Text, der am Ende angehaengt wird*/	public void haengeAn(String pText)	{		this.setzeInhalt(hatTextField.getText() + pText);	}	/** Das Zeichen pZeichen wird am Ende angehaengt. @param pZeichen das Zeichen, das am Ende angehaengt wird*/	public void haengeAn(char pZeichen)	{		this.setzeInhalt(hatTextField.getText() + pZeichen);	}	/** Das ganze Zahl pZahl wird am Ende angehaengt. @param pZahl die Zahl, die am Ende angehaengt wird*/	public void haengeAn(int pZahl)	{		this.setzeInhalt(hatTextField.getText() + pZahl);	}	/** Die Kommazahl pZahl wird am Ende angehaengt. @param pZahl die Zahl, die am Ende angehaengt wird*/	public void haengeAn(double pZahl)	{		this.setzeInhalt(hatTextField.getText() + pZahl);	}	/** Der im Textfeld markierte Text wird geliefert. @return der markierte Text*/	public String markierterInhalt()	{		return hatTextField.getSelectedText();	}	/**Der Text zwischen den Zeichen an Position pAnfang und pEndewird markiert.@param pAnfang erste Stelle@param pEnde letzte Stelle*/	public void setzeMarkierung(int pAnfang, int pEnde)	{		hatTextField.requestFocus();		hatTextField.select(pAnfang - 1, pEnde);		this.markierungGeaendert();	}	/** Der gesamte Text wird markiert.*/	public void markiereAlles()	{		hatTextField.requestFocus();		hatTextField.selectAll();		this.markierungGeaendert();	}	/** Der gesamte Text wird nicht markiert.*/	public void markiereNichts()	{		hatTextField.requestFocus();		hatTextField.select(0, 0);		this.markierungGeaendert();	}	/** Der gesamte Text wird geloescht.*/	public void loescheAlles()	{		this.setzeInhalt("");	}	/** Der markierte Text wird geloescht.*/	public void loescheMarkierung()	{		String s, s1, s2;		int von, bis;				s = hatTextField.getText();		von = hatTextField.getSelectionStart();		bis = hatTextField.getSelectionEnd();		if (bis > von)		{			s1 = s.substring(0, von);			s2 = s.substring(bis, s.length());			this.setzeInhalt(s1 + s2);			this.markierungGeaendert();		}			}	/**Der durch die Parameter bestimmte Text wird geloescht.@param pAnfang erste Stelle@param pEnde letzte Stelle*/	public void loesche(int pAnfang, int pEnde)	{		String s, s1, s2;				s = hatTextField.getText();		s1 = s.substring(0, pAnfang - 1);		s2 = s.substring(pEnde, s.length());		this.setzeInhalt(s1 + s2);		this.markierungGeaendert();	}	/** Es wird zurueckgegeben, ob der Text markiert ist. @return true, wenn Text markiert ist*/	public boolean istMarkiert()	{		return hatTextField.getSelectionStart() < hatTextField.getSelectionEnd();	}	/** Es wird zurueckgegeben, von wo an der Text markiert ist. @return die erste markierte Stelle*/	public int markierungsAnfang()	{		return hatTextField.getSelectionStart()+1;	}	/** Es wird zurueckgegeben, bis wohin der Text markiert ist. @return die letzte markierte Stelle*/	public int markierungsEnde()	{		return hatTextField.getSelectionEnd();	}/** Die Ausrichtung des Texts im Textfeld wird geaendert. @param pAusrichtung die neue Ausrichtung des Textes (siehe Klasse Ausrichtung)*/	public void setzeAusrichtung(int pAusrichtung)  	{  		switch (pAusrichtung)  		{  			case Ausrichtung.LINKS: hatTextField.setHorizontalAlignment(JTextField.LEFT); break;  			case Ausrichtung.MITTE: hatTextField.setHorizontalAlignment(JTextField.CENTER); break;  			case Ausrichtung.RECHTS: hatTextField.setHorizontalAlignment(JTextField.RIGHT); break;  		}  	}  	}