package sum.ereignis;

import java.awt.Font;
import java.io.*;

/**
 Konstanten fuer Schriftarten.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Schrift implements Serializable
{
	public static final String HELVETICA = "Helvetica";
	public static final String ARIAL = "Arial";
	public static final String TIMESROMAN = "TimesRoman";
	public static final String STANDARDSCHRIFTART = HELVETICA;
	public static final int KURSIV = Font.ITALIC;
	public static final int FETT = Font.BOLD;
	public static final int KURSIVFETT = Font.ITALIC + Font.BOLD;
	public static final int STANDARDSTIL = Font.PLAIN;
	public static final int STANDARDGROESSE = 12;
	public static final Font STANDARDSCHRIFT = new Font(STANDARDSCHRIFTART,
			STANDARDSTIL, STANDARDGROESSE);
}