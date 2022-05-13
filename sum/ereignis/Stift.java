package sum.ereignis;

import java.awt.*;
import java.awt.geom.*;

/**
 Der Stift ist ein Werkzeug, das sich auf dem Bildschirm  oder in einem
 Fenster bewegen kann.
 Er befindet sich stets auf einer genau definierten Position des
 Bildschirms bzw. Fensters, die durch Zeichenkoordinaten (horizontal nach rechts,
 vertikal nach unten) angegeben wird, und zeigt in eine Richtung,
 die durch Winkel beschrieben wird (0 = rechts, Drehsinn mathematisch
 positiv). 
 Der Stift kennt zwei Zustaende: Ist der Stift abgesenkt (runter)
 und bewegt er sich &uuml;ber den Bildschirm, so hinterlaesst er eine Spur,
 die von einem Zeichenmodus abhaengig ist. Ist der Stift angehoben (hoch),
 hinterlaesst er keine Spur. 
 Beim Zeichnen kennt der Stift drei Modi:
 Normal - der Stift zeichnet eine Linie in der Stiftfarbe;
 Wechseln - der Stift zeichnet eine Linie, wobei die Untergrundfarbe in
 die Stiftfarbe und die Stiftfarbe in die Untergrundfarbe ge&auml;ndert wird;
 Radieren - der Stift zeichnet eine Linie in der Farbe des Untergrunds. 
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Stift
{
	// Objekte
	protected BasicStroke hatStroke;
	protected Bildschirm kenntPrivatschirm;

	// Stiftmodi als Klassenkonstante
	protected static final int NORMALMODUS = 0;
	protected static final int RADIERMODUS = 1;
	protected static final int WECHSELMODUS = 2;

	protected double zStiftH = 0, zStiftV = 0; // Stiftposition
	protected boolean zHoch = true; // Stiftzustand
	protected double zWinkel = 0; // Stiftrichtung
	protected int zSchreibModus = NORMALMODUS;

	/**
	 Der Stift f&uuml;r den Bildschirm wird initialisiert. 
	 Die Zeichenebene steht zur Verfuegung und
	 der Stift befindet sich angehoben oben links an Position (0,0) mit 
	 Richtung 0 Grad im normalen Zeichenmodus.
	 */
	public Stift()
	{
	    kenntPrivatschirm = Bildschirm.topFenster;
		hatStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER);
		this.setzeStandard();
	}

	/**
	 Der Stift f&uuml;r ein Fenster wird initialisiert. 
	 Die Zeichenebene steht zur Verfuegung und
	 der Stift befindet sich angehoben oben links an Position (0,0) mit 
	 Richtung 0 Grad im normalen Zeichenmodus.
	 */
	public Stift(Fenster pFenster)
	{
	    kenntPrivatschirm = pFenster;
		hatStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER);
		this.setzeStandard();
	}

	/**
	 Der Stift wird unabhaengig von seiner vorherigen Position auf die 
	 durch die Parameter angegebene Position bewegt.
	 */
	public void bewegeBis(double pH, double pV)
	{
		if (!zHoch)
			this.zeichneLinie(pH, pV, zStiftH, zStiftV);
		this.zStiftH = pH;
		this.zStiftV = pV;
	}

	/**
	 Der Stift wird von seiner aktuellen Position in die aktuelle Richtung
	 bewegt. Distanz gibt die Laenge der  zurueckgelegten Strecke an.
	 */
	public void bewegeUm(double pDistanz)
	{
		double w;
		double x, y;

		w = zWinkel * Math.PI / 180;
		x = zStiftH + pDistanz * Math.cos(w);
		y = zStiftV - pDistanz * Math.sin(w);
		if (zHoch == false)
			this.zeichneLinie(zStiftH, zStiftV, x, y);
		zStiftH = x;
		zStiftV = y;
	}

	/**
	 Der Stift wird unabhaengig von seiner vorherigen Richtung auf die
	 durch Winkel angegebene Winkelgroesse gedreht.
	 */
	public void dreheBis(double pWinkel)
	{
		zWinkel = pWinkel;
		while (zWinkel < 0)
			zWinkel += 360;
		while (zWinkel >= 720)
			zWinkel -= 360;
	}

	/**
	 Der Stift wird unabhaengig von seiner vorherigen Richtung
	 in die Richtung des Punktes gedreht, dessen Koordinaten
	 uebergeben werden.
	 */
	public void dreheZu(double pWohinH, double pWohinV)
	{
		if (pWohinH != zStiftH || pWohinV != zStiftV)
		{
			if (pWohinH == zStiftH)
				if (pWohinV > zStiftV)
					zWinkel = 270;
				else
					zWinkel = 90;
			else
				if (pWohinV == zStiftV)
					if (pWohinH > zStiftH)
						zWinkel = 0;
					else
						zWinkel = 180;
				else
					if (pWohinH > zStiftH)
						zWinkel = Math.atan((pWohinV - zStiftV)
								/ (zStiftH - pWohinH))
								* 180 / Math.PI;
					else
						zWinkel = Math.atan((pWohinV - zStiftV)
								/ (zStiftH - pWohinH))
								* 180 / Math.PI + 180;
		}
		while (zWinkel < 0)
			zWinkel += 360;
		while (zWinkel >= 720)
			zWinkel -= 360;
	}

	/**
	 Der Stift wird ausgehend von seiner jetzigen Richtung um die
	 durch Winkel angegebene Winkelgroesse im mathematisch positiven Sinne 
	 weitergedreht.
	 */
	public void dreheUm(double pWinkel)
	{
		zWinkel += pWinkel;
		while (zWinkel < 0)
			zWinkel += 360;
		while (zWinkel >= 720)
			zWinkel -= 360;
	}

	/**
	 Der Stift schreibt den angegebenen Text auf die Zeichenebene unter Verwendung
	 seines aktuellen Zeichenmodus unabhaengig vom Zustand.
	 Die aktuelle Stiftposition ist die linke obere Ecke des Textes.
	 Die neue Stiftposition wird die rechte obere Ecke des Textes.
	 */
	public void schreibeText(String pText)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			g.drawString(pText, (int) zStiftH, (int) zStiftV);
			zStiftH += g.getFontMetrics().stringWidth(pText);
		}
	}

	/**
	 Der Stift schreibt das angegebenen Zeichen auf die Zeichenebene unter Verwendung
	 seines aktuellen Zeichenmodus unabhaengig vom Zustand.
	 Die aktuelle Stiftposition ist die linke obere Ecke des Textes.
	 Die neue Stiftposition wird die rechte obere Ecke des Textes.
	 */
	public void schreibeText(char pZeichen)
	{
		this.schreibeText("" + pZeichen);
	}

	/**
	 Der Stift schreibt die angegebene Zahl auf die Zeichenebene unter
	 Verwendung seines aktuellen Zeichenmodus unabhaengig vom Zustand.
	 Die aktuelle Stiftposition ist die linke obere Ecke des Textes.
	 Die neue Stiftposition wird die rechte obere Ecke des Textes.
	 */
	public void schreibeZahl(int pZahl)
	{
		this.schreibeText("" + pZahl);
	}

	/**
	 Der Stift schreibt die angegebene Zahl auf die Zeichenebene unter
	 Verwendung seines aktuellen Zeichenmodus unabhaengig vom Zustand.
	 Die aktuelle Stiftposition ist die linke obere Ecke des Textes.
	 Die neue Stiftposition wird die rechte obere Ecke des Textes.
	 */
	public void schreibeZahl(double pZahl)
	{
		this.schreibeText("" + pZahl);
	}

	/**
	 Der Stift wird angehoben.
	 */
	public void hoch()
	{
		zHoch = true;
	}

	/**
	 Der Stift wird abgesenkt.
	 */
	public void runter()
	{
		zHoch = false;
	}

	/**
	 liefert, ob der Stift abgesenkt ist.
	 */
	public boolean istUnten()
	{
		return !zHoch;
	}

	/**
	 Der Stift arbeitet danach im Normalmodus.
	 */
	public void normal()
	{
		zSchreibModus = NORMALMODUS;
	}

	/**
	 Der Stift arbeitet danach im Radiermodus.
	 */
	public void radiere()
	{
		zSchreibModus = RADIERMODUS;
	}

	/**
	 Der Stift arbeitet danach im Wechselmodus.
	 */
	public void wechsle()
	{
		zSchreibModus = WECHSELMODUS;
	}

	/**
	 liefert die horizontale Koordinate der aktuellen Stiftposition.
	 */
	public double hPosition()
	{
		return zStiftH;
	}

	/**
	 liefert die vertikale Koordinate der aktuellen Stiftposition.
	 */
	public double vPosition()
	{
		return zStiftV;
	}

	/**
	 liefert die aktuelle Bewegungsrichtung des Stifts.
	 */
	public double winkel()
	{
		return zWinkel;
	}

	/**
	 wird intern von bewegeBis() aufgerufen.
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
	 Der Stift zeichnet unabhaengig von seinem Zustand im aktuellen
	 Zeichenmodus ein achsenparalleles Rechteck mit der aktuellen
	 Position  als linker oberer Ecke und der angegebenen Breite
	 und Hoehe. Die Position und die Richtung des Stiftes bleiben unveraendert.
	 */
	public void zeichneRechteck(double pBreite, double pHoehe)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			g.draw(new Rectangle2D.Double(zStiftH, zStiftV, pBreite, pHoehe));
		}
	}

	/**
	 Der Stift zeichnet unabhaengig von seinem Zustand im aktuellen Zeichenmodus
	 einen Kreis mit der aktuellen Position als Mittelpunkt und dem angegebenen
	 Radius. Die Position und die Richtung des Stiftes bleiben unveraendert.
	 */
	public void zeichneKreis(double pRadius)
	{
		Graphics2D g;

		g = get2DGraphics(kenntPrivatschirm.g());
		if (g != null)
		{
			this.setzeZustand(g);
			g.draw(new Ellipse2D.Double(zStiftH - pRadius, zStiftV - pRadius,
					2 * pRadius, 2 * pRadius));
		}
	}

	/**
	 Wird intern vom Constructor Stift() aufgerufen.
	 */
	private void setzeStandard()
	{
		zStiftH = 0;
		zStiftV = 0; // Stiftposition
		zHoch = true; // Stiftzustand
		zWinkel = 0; // Stiftrichtung
		this.normal(); // Normalmodus  
	}

	/**
	 wird intern vor dem Zeichnen aufgerufen.
	 */
	protected void setzeZustand(Graphics2D g)
	{
		if (zSchreibModus == RADIERMODUS)
		{
			g.setPaint(kenntPrivatschirm.hintergrundfarbe());
			g.setPaintMode();
		}
		else
			if (zSchreibModus == NORMALMODUS)
			{
				g.setPaint(Color.black);
				g.setPaintMode();
			}
			else
			{
				g.setPaint(Color.black);
				g.setXORMode(kenntPrivatschirm.hintergrundfarbe());
			}
		g.setFont(Schrift.STANDARDSCHRIFT);
	}

	/**
	 wird intern vor dem Zeichnen aufgerufen.
	 */
	protected Graphics2D get2DGraphics(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		if (g2d != null)
			g2d.setStroke(hatStroke);
		return g2d;
	}

	/**
	   Dummy-Prozedur
	 */
	public void gibFrei()
	{
	}

}