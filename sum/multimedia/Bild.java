package sum.multimedia;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.*;
import java.applet.Applet;
import sum.ereignis.*;

/**
 Ein Bild ist die Klasse fuer Java-Bilder. Bilder reagieren
 auf einen Klick mit der Maus wie Knoepfe. 
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Bild extends JPanel implements Serializable
{
	// Objekte
	private Image hatOriginal = null;
	private BufferedImage hatKopie = null;

	// Attribute
	private String zGeklicktBearbeiter = "";
	private String zFokusErhaltenBearbeiter = "";
	private String zFokusVerlorenBearbeiter = "";
	private boolean zHatFocus = false;

	private class BildReaktor extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
		}

		public void mouseReleased(MouseEvent e)
		{
			bildGeklickt();
		}
	}

	private class BildTastenReaktor implements KeyListener
	{
		public void keyTyped(KeyEvent e)
		{
		}

		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				bildGeklickt();
		}

		public void keyReleased(KeyEvent e)
		{
		}
	}

	private class BildFokusReaktor implements FocusListener
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
	 Ein leeres Bild wird erzeugt.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Der Name der Aktionsmethode, die beim Druecken des
	 Bildes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(double pLinks, double pOben, double pBreite, double pHoehe)
	{
		super();
		Bildschirm.topFenster.privatPanel().add(this, 0);
		this.setBackground(Color.black);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		this.setOpaque(false);
		hatOriginal = new BufferedImage((new Double(pBreite)).intValue(), (new Double(pHoehe)).intValue(), BufferedImage.TYPE_INT_ARGB);
		this.bildPuffern();
		this.repaint();
	}

	/**
	 Das Bild, dessen Pfadname uebergeben wird, wird von der Festplatte geladen.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Der Name der Aktionsmethode, die beim Druecken des
	 Bildes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(double pLinks, double pOben, double pBreite, double pHoehe,
			String pPfad)
	{
		super();
		Bildschirm.topFenster.privatPanel().add(this, 0);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		this.ladeBild(pPfad);
		this.repaint();
	}

	/**
	 Das Bild, dessen Pfadname uebergeben wird, wird von der Festplatte in ein Applet geladen.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Der Name der Aktionsmethode, die beim Druecken des
	 Bildes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(double pLinks, double pOben, double pBreite, double pHoehe,
			String pPfad, Applet pApplet)
	{
		super();
		Bildschirm.topFenster.privatPanel().add(this, 0);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.ladeBild(pApplet, pPfad);
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		this.repaint();
	}

	/**
	 Das Bild, das als Parameter uebergeben wird, wird uebernommen.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Der Name der Aktionsmethodee, die beim Druecken des
	 Knopfes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(double pLinks, double pOben, double pBreite, double pHoehe,
			Bild pBild)
	{
		super();
		Bildschirm.topFenster.privatPanel().add(this, 0);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		hatOriginal = pBild.hatOriginal;
		this.bildPuffern();
		this.repaint();
	}

	/**
	 Ein leeres Bild wird erzeugt.
	 Position, Breite und Hoehe werden als Parameter uebergeben.
	 Das Bild befindet sich auf dem Fenster pFenster.
	 Der Name der Aktionsmethode, die beim Druecken des
	 Bildes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe)
	{
		super();
		pFenster.privatPanel().add(this, 0);
		this.setBackground(Color.black);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		this.setOpaque(false);
		hatOriginal = new BufferedImage((new Double(pBreite)).intValue(), (new Double(pHoehe)).intValue(), BufferedImage.TYPE_INT_ARGB);
		this.bildPuffern();
		this.repaint();
	}

	/**
	 Das Bild, dessen Pfadname uebergeben wird, wird von der Festplatte geladen.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Das Bild befindet sich auf dem Fenster pFenster.
	 Der Name der Aktionsmethode, die beim Druecken des
	 Bildes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe,
			String pPfad)
	{
		super();
		pFenster.privatPanel().add(this, 0);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		this.ladeBild(pPfad);
		this.repaint();
	}

	/**
	 Das Bild, das als Parameter uebergeben wird, wird uebernommen.
	 Position, Breite und Hoehe werden als Parameter uebergeben. 
	 Das Bild befindet sich auf dem Fenster pFenster.
	 Der Name der Aktionsmethodee, die beim Druecken des
	 Knopfes aufgerufen wird, muss noch festgelegt werden.
	 */
	public Bild(Fenster pFenster, double pLinks, double pOben, double pBreite, double pHoehe,
			Bild pBild)
	{
		super();
		pFenster.privatPanel().add(this, 0);
		this.addMouseListener(new BildReaktor());
		this.addKeyListener(new BildTastenReaktor());
		this.addFocusListener(new BildFokusReaktor());
		this.setzeGroesse(pBreite, pHoehe);
		this.setzePosition(pLinks, pOben);
		hatOriginal = pBild.hatOriginal;
		this.bildPuffern();
		this.repaint();
	}

	/**
	 Die Methode zur Bearbeitung des Geklickt-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 */
	public void setzeBearbeiterGeklickt(String pBearbeiter)
	{
		zGeklicktBearbeiter = pBearbeiter;
	}

	/**
	 Die Methode zur Bearbeitung des FokusVerloren-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 */
	public void setzeBearbeiterFokusVerloren(String pBearbeiter)
	{
		zFokusVerlorenBearbeiter = pBearbeiter;
	}

	/**
	 Die Methode zur Bearbeitung des FokusErhalten-Ereignisses in
	 der Ereignisanwendung wird festgelegt.
	 */
	public void setzeBearbeiterFokusErhalten(String pBearbeiter)
	{
		zFokusErhaltenBearbeiter = pBearbeiter;
	}

	/**
	 Ein Bild wird in einem Applet vom Pfad pPfad geladen.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean ladeBild(Applet pApplet, String pPfad)
	{
		MediaTracker mt = null;

		hatOriginal = pApplet.getImage(pApplet.getCodeBase(), pPfad);
		mt = new MediaTracker(Bildschirm.topFenster);
		mt.addImage(hatOriginal, 0);
		try
		{
			mt.waitForAll();
			this.bildPuffern();
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return false;
		}
	}

	/**
	 Ein Bild wird vom Pfad pPfad geladen.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean ladeBild(String pPfad)
	{
		MediaTracker mt = null;

		hatOriginal = Bildschirm.topFenster.getToolkit()
				.createImage(pPfad);
		mt = new MediaTracker(Bildschirm.topFenster);
		mt.addImage(hatOriginal, 0);
		try
		{
			mt.waitForAll();
			this.bildPuffern();
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return false;
		}
	}

	/**
	 Der Bild wird von einer zu waehlenden Datei (gif, jpg, png) geladen.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean ladeBild()
	{
		FileDialog ladendialog;
		String dateiname, pfadname;

		ladendialog = new FileDialog(Bildschirm.topFenster, "Bild laden",
				FileDialog.LOAD);
		ladendialog.setVisible(true);
		dateiname = ladendialog.getFile();
		if (dateiname != null) // Es wurde ok geklickt
		{
			pfadname = ladendialog.getDirectory();
			return this.ladeBild(pfadname + dateiname);
		}
		else
			return false;
	}

	/**
	 Das Bild wird als jpg oder png-Datei pDatei gespeichert.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean speichereBild(String pDatei)
	{
		File datei = new File(pDatei);
		try
		{
			ImageIO.write(hatKopie, pDatei.substring(pDatei.length() - 3,
					pDatei.length()), datei);
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return false;
		}
	}

	/**
	 Das Bild wird mit einem zu waehlenden Namen gespeichert.
	 Der Name benoetigt die Endung jpg oder png!
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean speichereBild()
	{
		FileDialog speicherndialog;
		String dateiname, pfadname;

		speicherndialog = new FileDialog(Bildschirm.topFenster,
				"Ton speichern", FileDialog.SAVE);
		speicherndialog.setVisible(true);
		dateiname = speicherndialog.getFile();
		if (dateiname != null) // Es wurde ok geklickt
		{
			pfadname = speicherndialog.getDirectory();
			return this.speichereBild(pfadname + dateiname);
		}
		else
			return false;
	}

	/**
	 Ein Bild wird vom Bild pBild uebernommen.
	 */
	public void setzeBild(Bild pBild)
	{
		hatOriginal = pBild.hatKopie;
		this.bildPuffern();
	}

	/**
	 interner Dienst
	 */
	private void bildPuffern()
	{
		Graphics2D graphics;

		if (hatOriginal != null)
		{
			this.setSize(hatOriginal.getWidth(null), hatOriginal
					.getHeight(null));
			hatKopie = new BufferedImage(hatOriginal.getWidth(null),
					hatOriginal.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			graphics = hatKopie.createGraphics();
			graphics.drawImage(hatOriginal, 0, 0, this);
			this.repaint();
		}
	}

	/**
	 interner Dienst
	 */
	public void paint(Graphics g)
	{
		if (hatKopie != null)
			((Graphics2D) g).drawImage(hatKopie, 0, 0, this);
	}

	/**
	Das Bild reagiert auf einen Mausklick, indem es die beim Konstruktor als Parameter uebergebene
	Methode der Anwendung aufruft.
	 */
	public void bildGeklickt()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Bild[] meinBild = new Bild[1];

		if (zGeklicktBearbeiter.length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getMethod(zGeklicktBearbeiter, null);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung,
							null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ zGeklicktBearbeiter + "\" des Bilds: "
							+ e0.getTargetException().toString());
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Bild.class;
						methode = sumEreignis.getMethod(zGeklicktBearbeiter,
								formparas);
						meinBild[0] = this;
						methode.invoke(
								Ereignisanwendung.hatSuMPrivateAnwendung,
								meinBild);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ zGeklicktBearbeiter + "\" des Bilds: "
								+ e2.getTargetException().toString());
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ zGeklicktBearbeiter
								+ "\" des Bilds nicht gefunden.");
					}
				}
			}
			catch (Exception e4)
			{
				System.out.println("Bild: " + e4.toString());
			}
		}
	}

	/**
	 Das Bild erhaelt den Fokus.
	 */
	public void bekommtFokus()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Bild[] meinBild = new Bild[1];

		zHatFocus = true;
		if (zFokusErhaltenBearbeiter.length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getMethod(zFokusErhaltenBearbeiter,
							null);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung,
							null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ zFokusErhaltenBearbeiter + "\" des Bilds: "
							+ e0.getTargetException().toString());
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Bild.class;
						methode = sumEreignis.getMethod(
								zFokusErhaltenBearbeiter, formparas);
						meinBild[0] = this;
						methode.invoke(
								Ereignisanwendung.hatSuMPrivateAnwendung,
								meinBild);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ zFokusErhaltenBearbeiter + "\" des Bilds: "
								+ e2.getTargetException().toString());
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ zFokusErhaltenBearbeiter
								+ "\" des Bilds nicht gefunden.");
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
	 Das Bild verliert den Fokus.
	 */
	public void verliertFokus()
	{
		Class sumEreignis;
		Class[] formparas = new Class[1];
		Method methode;
		Bild[] meinBild = new Bild[1];

		zHatFocus = false;
		if (zFokusVerlorenBearbeiter.length() > 0)
		{
			try
			{
				sumEreignis = Ereignisanwendung.hatSuMPrivateAnwendung
						.getClass();
				try
				{
					methode = sumEreignis.getMethod(zFokusVerlorenBearbeiter,
							null);
					methode.invoke(Ereignisanwendung.hatSuMPrivateAnwendung,
							null);
				}
				catch (InvocationTargetException e0)
				{
					System.out.println("Fehler in Methode \""
							+ zFokusVerlorenBearbeiter + "\" des Bilds: "
							+ e0.getTargetException().toString());
				}
				catch (Exception e1)
				{
					try
					{
						formparas[0] = Bild.class;
						methode = sumEreignis.getMethod(
								zFokusVerlorenBearbeiter, formparas);
						meinBild[0] = this;
						methode.invoke(
								Ereignisanwendung.hatSuMPrivateAnwendung,
								meinBild);
					}
					catch (InvocationTargetException e2)
					{
						System.out.println("Fehler in Methode \""
								+ zFokusVerlorenBearbeiter + "\" des Bilds: "
								+ e2.getTargetException().toString());
					}
					catch (Exception e3)
					{
						System.out.println("Fehler: Methode \""
								+ zFokusVerlorenBearbeiter
								+ "\" des Bilds nicht gefunden.");
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
	 Das Bild erhaelt eine neue Groesse.
	 */
	public void setzeGroesse(double pBreite, double pHoehe)
	{
		this.setSize((int) pBreite, (int) pHoehe);
		if (hatOriginal != null)
		{
			ImageFilter groesseFilter = new ReplicateScaleFilter((int) pBreite,
					(int) pHoehe);
			ImageProducer imageprod = new FilteredImageSource(hatKopie
					.getSource(), groesseFilter);
			Image img = createImage(imageprod);
			hatOriginal = img;
			this.bildPuffern();
		}
	}

	/**
	 Das Bild erhaelt eine neue Position (Ecke oben links).
	 */
	public void setzePosition(double pWohinH, double pWohinV)
	{
		this.setLocation((int) pWohinH, (int) pWohinV);
		if (hatOriginal != null)
		{
			this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
			Bildschirm.topFenster.loescheAlles();
		}
	}

	/**
	 Der linke Rand des Bilds wird zurueckgegeben.
	 */
	public int links()
	{
		return this.getLocation().x;
	}

	/**
	 Der obere Rand des Bilds wird zurueckgegeben.
	 */
	public int oben()
	{
		return this.getLocation().y;
	}

	/**
	 Die Breite des Bilds wird zurueckgegeben.
	 */
	public int breite()
	{
		return this.getSize().width;
	}

	/**
	 Die Hoehe des Bilds wird zurueckgegeben.
	 */
	public int hoehe()
	{
		return this.getSize().height;
	}

	/**
	 Das Bild wird unsichtbar, existiert aber weiter.
	 */
	public void verstecke()
	{
		this.setVisible(false);
	}

	/**
	 Das Bild wird angezeigt.
	 */
	public void zeige()
	{
		this.setVisible(true);
	}

	/**
	 Es wird zurueckgegeben, ob das Bild sichtbar ist.
	 */
	public boolean istSichtbar()
	{
		return this.isVisible();
	}

	/**
	 Das Bild wird deaktiviert und reagiert nicht mehr.
	 */
	public void deaktiviere()
	{
		this.setEnabled(false);
	}

	/**
	 Das Bild wird aktiviert.
	 */
	public void aktiviere()
	{
		this.setEnabled(true);
	}

	/**
	 Es wird zurueckgegeben, ob das Bild aktiviert ist.
	 */
	public boolean istAktiv()
	{
		return this.isEnabled();
	}

	/**
	 Liefert die Information, ob das Bild den Fokus besitzt.
	 */
	public boolean besitztFocus()
	{
		return zHatFocus;
	}

	/**
	 Das Bild erhaelt den Fokus.
	 */
	public void setzeFocus()
	{
		this.requestFocus();
	}

	/** 
	 Das Bild wird entfernt.
	 */
	public void gibFrei()
	{
	    JPanel lPanel;
		lPanel = (JPanel)this.getParent();
		lPanel.remove(this);
		lPanel.repaint();
		hatOriginal = null;
		hatKopie = null;
	}

	public int rotanteil(int pH, int pV)
	{
		int rgba, rot;

		rgba = hatKopie.getRGB(pH, pV);
		rot = (rgba >> 16) & 0xff;
		return rot;
	}

	public int gruenanteil(int pH, int pV)
	{
		int rgba, gruen;

		rgba = hatKopie.getRGB(pH, pV);
		gruen = (rgba >> 8) & 0xff;
		return gruen;
	}

	public int blauanteil(int pH, int pV)
	{
		int rgba, blau;

		rgba = hatKopie.getRGB(pH, pV);
		blau = rgba & 0xff;
		return blau;
	}

	public int alphaanteil(int pH, int pV)
	{
		int rgba, alpha;

		rgba = hatKopie.getRGB(pH, pV);
		alpha = (rgba >> 24) & 0xff;
		return alpha;
	}

	public void setzeRotanteil(int pH, int pV, int pRot)
	{
		int rgba, rot, blau, gruen, alpha;

		rgba = hatKopie.getRGB(pH, pV);
		rot = pRot;
		gruen = (rgba >> 8) & 0xff;
		blau = rgba & 0xff;
		alpha = (rgba >> 24) & 0xff;
		rgba = (alpha << 24) | (rot << 16) | (gruen << 8) | blau;
		hatKopie.setRGB(pH, pV, rgba);
	}

	public void setzeGruenanteil(int pH, int pV, int pGruen)
	{
		int rgba, rot, blau, gruen, alpha;

		rgba = hatKopie.getRGB(pH, pV);
		gruen = pGruen;
		rot = (rgba >> 16) & 0xff;
		gruen = pGruen;
		blau = rgba & 0xff;
		alpha = (rgba >> 24) & 0xff;
		rgba = (alpha << 24) | (rot << 16) | (gruen << 8) | blau;
		hatKopie.setRGB(pH, pV, rgba);
	}

	public void setzeBlauanteil(int pH, int pV, int pBlau)
	{
		int rgba, rot, blau, gruen, alpha;

		rgba = hatKopie.getRGB(pH, pV);
		rot = (rgba >> 16) & 0xff;
		gruen = (rgba >> 8) & 0xff;
		blau = pBlau;
		alpha = (rgba >> 24) & 0xff;
		rgba = (alpha << 24) | (rot << 16) | (gruen << 8) | blau;
		hatKopie.setRGB(pH, pV, rgba);
	}

	public void setzeAlphaanteil(int pH, int pV, int pAlpha)
	{
		int rgba, rot, blau, gruen, alpha;

		rgba = hatKopie.getRGB(pH, pV);
		rot = (rgba >> 16) & 0xff;
		gruen = (rgba >> 8) & 0xff;
		blau = rgba & 0xff;
		alpha = pAlpha;
		rgba = (alpha << 24) | (rot << 16) | (gruen << 8) | blau;
		hatKopie.setRGB(pH, pV, rgba);
	}

	public void filter(ImageFilter pFilter)
	{
		ImageProducer imageprod = new FilteredImageSource(hatKopie.getSource(),
				pFilter);
		Image img = createImage(imageprod);
		hatOriginal = img;
		this.bildPuffern();
	}

}