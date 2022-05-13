package sum.kern;

/**
 Eine Tastatur realisiert die Tastatureingabe des verwendeten Computers.
 Sie speichert die eingegebenen Tastaturzeichen in der Reihenfolge ihrer Eingabe.
 F&uuml;r einige Tastatureingaben stehen bereits Konstanten zur Verfuegung: 
 ESCAPE, ENDE, POS1, PFEILLINKS, PFEILRECHTS, PFEILOBEN, PFEILUNTEN,
 BILDUNTEN, BILDAUF, TAB, EINGABE, BACKSPACE, DELETE, F1, F2, ... , F12
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Tastatur
{
	private boolean zGetestet;

	/**
	 Die Tastatur wird initialisiert und enthaelt keine Zeichen.
	 */
	public Tastatur()
	{
		zGetestet = false;
	}

	/**
	 Es wird geprueft, ob ein Zeichen im Tastaturpuffer ist.
	 @return true, wenn mindestens ein Zeichen im Zastaturpuffer ist.
	 */
	public boolean wurdeGedrueckt()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		if (Bildschirm.hatPrivatschirm != null)
		{
			if (Bildschirm.hatPrivatschirm.hatTastaturpuffer.size() > 0)
			{
				zGetestet = true;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}

	/** 
	 Das erste Zeichen des Tastaturpuffers. Es wird erst mit "weiter()"
	 geloescht. Mehrere Aufrufe von "zeichen()" liefern also das gleiche Zeichen. Falls
	 der Tastaturpuffer vorher nicht mit "wurdeGedrueckt()" gestestet wurde,
	 erfolgt eine Fehlermeldung.
	 @return erstes Zeichen im Tastaturpuffer
	 */
	public char zeichen()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		if (Bildschirm.hatPrivatschirm != null)
		{
			if (!zGetestet)
			{
				Warnung warnung = new Warnung(Bildschirm.hatPrivatschirm,
						"Die Tastatur wurde nicht getestet.");
				if (warnung.istOk())
					System.exit(0);
				return (char) 0;
			}
			else
			{
				Integer merke = (Integer) Bildschirm.hatPrivatschirm.hatTastaturpuffer
						.elementAt(0);
				return (char) merke.intValue();
			}
		}
		else
		{
			Warnung warnung = new Warnung(Bildschirm.hatPrivatschirm,
					"Der Bildschirm wurde nicht erzeugt.");
			if (warnung.istOk())
				System.exit(0);
			return (char) 0;
		}
	}

	/**
	 Mit weiter() wird das vorderste Zeichen im Tastaturpuffer entfernt. Falls
	 der Tastaturpuffer vorher nicht mit "wurdeGedrueckt()" gestestet wurde,
	 erfolgt eine Fehlermeldung.
	 */
	public void weiter()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		if (!zGetestet)
		{
			Warnung warnung = new Warnung(Bildschirm.hatPrivatschirm,
					"Die Tastatur wurde nicht getestet.");
			if (warnung.istOk())
				System.exit(0);
		}
		else
		{
			zGetestet = false;
			Bildschirm.hatPrivatschirm.hatTastaturpuffer.removeElementAt(0);
		}
	}

	/**
		Dummy-Prozedur
	 */
	public void gibFrei()
	{
	}

}