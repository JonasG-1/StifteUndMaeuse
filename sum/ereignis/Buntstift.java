package sum.ereignis;

import java.awt.*;
import java.awt.geom.*;

/**
 Der Buntstift uebernimmt die Eigenschaften der Klasse Stift. Allerdings
 besitzt er darueber hinausgehende Eigenschaften, die einzeln gesetzt werden
 koennen:
 Farbe: weiss, schwarz, rot, gruen, blau, gelb, etc.
 Schriftart: standardSchrift etc.
 Schriftstil: standardStil, fett, kursiv etc.
 Schriftgroesse: 10 bzw. andere positive ganze Zahl
 Linienbreite: 1 bzw. andere positive ganze Zahl.
 Fuellmuster: durchsichtig, gefuellt etc.
 Fuer den Zustand (stiftHoch, stiftRunter) und den Modus (normalModus, wechselModus,
 radierModus) existieren Konstanten.
 @author Bernard Schriek
@version 7.5 vom 29.10.2013
 */
public class Buntstift extends Stift
{
	private String zAktuellFont = Schrift.STANDARDSCHRIFTART;
	private int zSchriftStil = Schrift.STANDARDSTIL;
	private int zSchriftGroesse = Schrift.STANDARDGROESSE;
	private Font zSchriftArt = Schrift.STANDARDSCHRIFT;
	private Color zFarbe = Color.black;
	private int zLinienbreite = 1;
	private int zMuster = Muster.DURCHSICHTIG;

	/**
	 Der Buntstift wird als Stift f&uuml;r den Bildschirm initialisiert und mit den
	 Standardeinstellungen versehen.
	 */
	public Buntstift()
	{
		super();
	}

	/**
	 Der Buntstift wird als Stift f&uuml; ein Fenster initialisiert und mit den
	 Standardeinstellungen versehen.
	 */
	public Buntstift(Fenster pFenster)
	{
		super(pFenster);
	}

	/**
	 Die angegebene Farbe wird die aktuelle Farbe des Buntstifts.
	 @param pFarbe neue Farbe des Buntstifts
	 */
	public void setzeFarbe(Color pFarbe)
	{
		zFarbe = pFarbe;
	}

	/**
	 Die angegebene Farbe wird die aktuelle Farbe des Buntstifts.
	 @param pFarbe neue Farbe des Buntstifts
	 */
	public void setzeFarbe(int pFarbe)
	{
		if (pFarbe < 0)
			pFarbe = 0;
		pFarbe %= 13;
		switch (pFarbe)
		{
			case 0:
				this.setzeFarbe(Color.black);
				break;
			case 1:
				this.setzeFarbe(Color.blue);
				break;
			case 2:
				this.setzeFarbe(Color.cyan);
				break;
			case 3:
				this.setzeFarbe(Color.darkGray);
				break;
			case 4:
				this.setzeFarbe(Color.gray);
				break;
			case 5:
				this.setzeFarbe(Color.green);
				break;
			case 6:
				this.setzeFarbe(Color.lightGray);
				break;
			case 7:
				this.setzeFarbe(Color.magenta);
				break;
			case 8:
				this.setzeFarbe(Color.orange);
				break;
			case 9:
				this.setzeFarbe(Color.pink);
				break;
			case 10:
				this.setzeFarbe(Color.red);
				break;
			case 11:
				this.setzeFarbe(Color.white);
				break;
			case 12:
				this.setzeFarbe(Color.yellow);
				break;
		}
	}

	/**
	 Die angegebene Breite wird die aktuelle Linienbreite des Buntstifts.
	 Schreibweise 1
	 @param pBreite neue Linienbreite des Buntstifts
	 */
	public void setzeLinienbreite(int pBreite)
	{
		if (pBreite > 0)
		{
			zLinienbreite = pBreite;
			hatStroke = new BasicStroke(pBreite * 1.0f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER);
		}
	}

	/**
	 Die angegebene Breite wird die aktuelle Linienbreite des Buntstifts.
	 Schreibweise 2
	 @param pBreite neue Linienbreite des Buntstifts
	 */
	public void setzeLinienBreite(int pBreite)
	{
		this.setzeLinienbreite(pBreite);
	}

	/**
	 Der Buntstift liefert seine Linienbreite.
	 Schreibweise 1
	 @return aktuelle Linienbreite des Buntstifts
	 */
	public int linienbreite()
	{
		return zLinienbreite;
	}

	/**
	 Der Buntstift liefert seine Linienbreite.
	 Schreibweise 2
	 @return aktuelle Linienbreite des Buntstifts
	 */
	public int linienBreite()
	{
		return zLinienbreite;
	}

	/**
	 Die angegebene Schriftart wird die aktuelle Schriftart des Buntstifts.
	 Schreibweise 1
	 @param pArt neuer Zeichensatz des Buntstifts
	 */
	public void setzeSchriftart(String pArt)
	{
		zAktuellFont = pArt;
	}

	/**
	 Die angegebene Schriftart wird die aktuelle Schriftart des Buntstifts.
	 Schreibweise 2
	 @param pArt neuer Zeichensatz des Buntstifts
	 */
	public void setzeSchriftArt(String pArt)
	{
		zAktuellFont = pArt;
	}

	/**
	 Die angegebene Schriftgroesse wird die aktuelle Schriftgroesse des Buntstifts.
	 Schreibweise 1
	 @param pGroesse neuer Schriftgroesse des Buntstifts
	 */
	public void setzeSchriftgroesse(int pGroesse)
	{
		zSchriftGroesse = pGroesse;
	}

	/**
	 Die angegebene Schriftgroesse wird die aktuelle Schriftgroesse des Buntstifts.
	 Schreibweise 2
	 @param pGroesse neuer Schriftgroesse des Buntstifts
	 */
	public void setzeSchriftGroesse(int pGroesse)
	{
		zSchriftGroesse = pGroesse;
	}

	/**
	 Der angegebene Schriftstil wird der aktuelle Schriftstil des Buntstifts.
	 Schreibweise 1
	 @param pStil neuer Schriftstil des Buntstifts (siehe Klasse Schrift)
	 */
	public void setzeSchriftstil(int pStil)
	{
		zSchriftStil = pStil;
	}

	/**
	 Der angegebene Schriftstil wird der aktuelle Schriftstil des Buntstifts.
	 Schreibweise 2
	 @param pStil neuer Schriftstil des Buntstifts (siehe Klasse Schrift)
	 */
	public void setzeSchriftStil(int pStil)
	{
		zSchriftStil = pStil;
	}

	/**
	 Das angegebene Muster ist das aktuelle Muster des Buntstifts fuer Rechtecke und Kreise.
	 Schreibweise 1
	 @param pMuster neues Fuellmuster des Buntstifts (siehe Klasse Muster)
	 */
	public void setzeFuellmuster(int pMuster)
	{
		zMuster = pMuster;
	}

	/**
	 Das angegebene Muster ist das aktuelle Muster des Buntstifts fuer Rechtecke und Kreise.
	 Schreibweise 2
	 @param pMuster neues Fuellmuster des Buntstifts (siehe Klasse Muster)
	 */
	public void setzeFuellMuster(int pMuster)
	{
		zMuster = pMuster;
	}

	/**
	 Der Buntstift zeichnet in der aktuellen Farbe und Linienbreite ein
	 achsenparalleles Rechteck mit der aktuellen Position  als linker
	 oberer Ecke und der angegebenen Breite und Hoehe. Das Rechteck wird
	 mit dem aktuellen Muster gefuellt. Die Position und die Richtung des
	 Buntstiftes sind unveraendert.
	 @param pBreite die Breite des Rechtecks
	 @param pHoehe die Hoehe des Rechtecks
	 */
	public void zeichneRechteck(double pBreite, double pHoehe)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			if (zMuster == Muster.DURCHSICHTIG)
				g
						.draw(new Rectangle2D.Double(zStiftH, zStiftV, pBreite,
								pHoehe));
			else
				g
						.fill(new Rectangle2D.Double(zStiftH, zStiftV, pBreite,
								pHoehe));
		}
	}

	/**
	 Der Buntstift zeichnet in der aktuellen Farbe einen Kreis mit der
	 aktuellen Position als Mittelpunkt und dem angegebenen Radius.
	 Der Kreis ist mit dem aktuellen Muster gefuellt. Die Position und die
	 Richtung des Buntstiftes sind unveraendert.
	 @param pRadius der Radius des Kreises
	 */
	public void zeichneKreis(double pRadius)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			if (zMuster == Muster.DURCHSICHTIG)
				g.draw(new Ellipse2D.Double(zStiftH - pRadius, zStiftV
						- pRadius, 2 * pRadius, 2 * pRadius));
			else
				g.fill(new Ellipse2D.Double(zStiftH - pRadius, zStiftV
						- pRadius, 2 * pRadius, 2 * pRadius));
		}
	}

	/**
	 wird intern von bewegeBis() und bewegeUm() aufgerufen.
	 */
	protected void zeichneLinie(double x1, double y1, double x2, double y2)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			g.draw(new Line2D.Double(x1, y1, x2, y2));
		}
	}

	/**
	 ermittelt die Breite des Texts unter Beruecksichtigung der
	 Eigenschaften des Buntstifts.
	 @param pText der zu pruefende Text
	 @return die Breite des Textes in Pixeln
	 */
	public int textbreite(String pText)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			return g.getFontMetrics().stringWidth(pText);
		}
		else
			return 12;
	}

	/**
	 ermittelt die Breite des Zeichens unter Beruecksichtigung der
	 Eigenschaften des Buntstifts.
	 @param pZeichen das zu pruefende Zeichen
	 @return die Breite des Zeichens in Pixeln
	 */
	public int zeichenbreite(char pZeichen)
	{
		String lText = "" + pZeichen;
		return textbreite(lText);
	}

	/**
	 ermittelt die Breite der Zahl unter Beruecksichtigung der
	 Eigenschaften des Buntstifts.
	 @param pZahl die zu pruefende Zahl
	 @return die Breite der Zahl in Pixeln
	 */
	public int zahlbreite(int pZahl)
	{
		String lText = "" + pZahl;
		return textbreite(lText);
	}

	/**
	 ermittelt die Breite der Zahl unter Beruecksichtigung der
	 Eigenschaften des Buntstifts.
	 @param pZahl die zu pruefende Zahl
	 @return die Breite der Zahl in Pixeln
	 */
	public int zahlbreite(double pZahl)
	{
		String lText = "" + pZahl;
		return textbreite(lText);
	}

	/**
	 alternative Schreibweise
	 @param pText der zu pruefende Text
	 @return die Breite des Textes in Pixeln
	 */
	public int textBreite(String pText)
	{
		return textbreite(pText);
	}

	/**
	 alternative Schreibweise
	 @param pZeichen das zu pruefende Zeichen
	 @return die Breite des Zeichens in Pixeln
	 */
	public int zeichenBreite(char pZeichen)
	{
		return zeichenbreite(pZeichen);
	}

	/**
	 alternative Schreibweise
	 @param pZahl die zu pruefende Zahl
	 @return die Breite der Zahl in Pixeln
	 */
	public int zahlBreite(int pZahl)
	{
		return zahlbreite(pZahl);
	}

	/**
	 alternative Schreibweise
	 @param pZahl die zu pruefende Zahl
	 @return die Breite der Zahl in Pixeln
	 */
	public int zahlBreite(double pZahl)
	{
		return zahlbreite(pZahl);
	}

	/**
	 wird intern vor dem Zeichnen aufgerufen.
	 */
	protected void setzeZustand(Graphics2D g)
	{
		if (zMuster == Muster.DURCHSCHEINEND
				&& zFarbe.getTransparency() != Color.TRANSLUCENT)
			zFarbe = new Color(zFarbe.getRed(), zFarbe.getGreen(), zFarbe
					.getBlue(), 128);
		else
			if (zMuster != Muster.DURCHSCHEINEND
					&& zFarbe.getTransparency() != Color.OPAQUE)
				zFarbe = new Color(zFarbe.getRed(), zFarbe.getGreen(), zFarbe
						.getBlue());
		if (zSchreibModus == RADIERMODUS)
		{
			g.setPaint(kenntPrivatschirm.hintergrundfarbe());
			g.setPaintMode();
		}
		else
			if (zSchreibModus == NORMALMODUS)
			{
				g.setPaint(zFarbe);
				g.setPaintMode();
			}
			else
			{
				g.setPaint(zFarbe);
				g.setXORMode(kenntPrivatschirm.hintergrundfarbe());
			}
		zSchriftArt = new Font(zAktuellFont, zSchriftStil, zSchriftGroesse);
		g.setFont(zSchriftArt);
	}

	/**
	 wird intern von dem Constructor Buntstift() aufgerufen.
	 */
	protected void setzeStandard()
	{
		zStiftH = 0;
		zStiftV = 0; // Stiftposition
		zHoch = true; // Stiftzustand
		zWinkel = 0; // Stiftrichtung 
		zSchreibModus = NORMALMODUS; //Normalmodus 
		zAktuellFont = Schrift.STANDARDSCHRIFTART;
		zSchriftStil = Schrift.STANDARDSTIL;
		zSchriftGroesse = Schrift.STANDARDGROESSE;
		zSchriftArt = Schrift.STANDARDSCHRIFT;
		zFarbe = Color.black;
		zLinienbreite = 1;
		zMuster = Muster.DURCHSICHTIG;
	}
}