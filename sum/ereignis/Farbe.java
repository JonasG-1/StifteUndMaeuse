package sum.ereignis;

import java.awt.Color;
import java.io.*;
import javax.swing.*;

/**
Konstanten fuer Farben (Buntstift bzw. Hintergrund)
@author Bernard Schriek
@version 7.5 vom 29.10.2013
*/
public class Farbe implements Serializable
{
	public final static int SCHWARZ = 0;
	public final static int BLAU = 1;	
	public final static int CYAN = 2;
	public final static int DUNKELGRAU = 3;
	public final static int GRAU = 4;
	public final static int GRUEN = 5;
	public final static int HELLGRAU = 6;	
	public final static int MAGENTA = 7;
	public final static int ORANGE = 8;
	public final static int PINK = 9;	
	public final static int ROT = 10;	
	public final static int WEISS = 11;	
	public final static int GELB = 12;
	
/** 
Mischen eigener RGB-Farben
@param pR Rotanteil der Farbe
@param pG Gruenanteil der Farbe
@param pB Blauanteil der Farbe
@return die neue gemischte Farbe
*/	
	public final static Color rgb(int pR, int pG, int pB)
	{
		return new Color(pR, pG, pB);
	}
	
/**
In einem Dialog liefert der Farbwaehler eine neue Farbe.
@param pAlteFarbe die Ausgangsfarbe fuer den Dialog
@return die neue Farbe, falls ok gedrueckt wurde, sonst die alte Farbe
*/
	public final static Color neueFarbe(Color pAlteFarbe)
	{
		Color neueFarbe = JColorChooser.showDialog(Ereignisanwendung.hatSuMPrivateAnwendung.bildschirm(), "Farbauswahl", pAlteFarbe);
		if (neueFarbe != null)
			return neueFarbe;
		else
			return pAlteFarbe;
	}

}