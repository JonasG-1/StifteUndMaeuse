package sum.multimedia;

import java.io.*;
import javax.sound.sampled.*;
import java.awt.*;
import sum.ereignis.*;

/**
 Ein Ton realisiert ein Objekt, das Toene aufnehmen, abspielen,
 laden und speichern kann.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Ton implements Serializable
{
	private boolean zNimmtAuf = false;
	private boolean zSpieltAb = false;
	private AudioFormat hatFormat = null;
	private ByteArrayOutputStream hatOutStream;

	/**
	 Der Ton wird initialisiert.
	 */
	public Ton()
	{
		super();
		hatFormat = this.getFormat();
	}

	/**
	 Interner Dienst
	 */
	private AudioFormat getFormat()
	{
		float sampleRate = 44100;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

	/**
	 Die Aufnahme des Tons wird gestartet.
	 */
	public void starteAufnahme()
	{
		if (!zSpieltAb)
		{
			try
			{
				hatFormat = this.getFormat();
				DataLine.Info info = new DataLine.Info(TargetDataLine.class,
						hatFormat);
				final TargetDataLine line = (TargetDataLine) AudioSystem
						.getLine(info);
				line.open(hatFormat);
				line.start();
				Runnable runner = new Runnable()
				{
					int bufferSize = (int) hatFormat.getSampleRate()
							* hatFormat.getFrameSize();
					byte buffer[] = new byte[bufferSize];

					public void run()
					{
						hatOutStream = new ByteArrayOutputStream();
						zNimmtAuf = true;
						try
						{
							while (zNimmtAuf)
							{
								int count = line.read(buffer, 0, buffer.length);
								if (count > 0)
									hatOutStream.write(buffer, 0, count);
							}
							hatOutStream.close();
						}
						catch (IOException e)
						{
							System.out.println("I/O problems: " + e);
							System.exit(-1);
						}
					}
				};
				Thread captureThread = new Thread(runner);
				captureThread.start();
			}
			catch (LineUnavailableException e)
			{
				System.out.println("Line unavailable: " + e);
				System.exit(-2);
			}
		}
	}

	/**
	 Die Aufnahme des Tons wird gestoppt.
	 */
	public void stoppeAufnahme()
	{
		if (zNimmtAuf)
			zNimmtAuf = false;
	}

	/**
	 Der Ton wird von der Datei pDatei geladen. Bei Erfolg wird
	 true zurueckgegeben.
	 */
	public boolean ladeTon(String pDatei)
	{
		File soundFile = new File(pDatei);
		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
		hatFormat = audioInputStream.getFormat();
		int bufferSize = (int) hatFormat.getSampleRate()
				* hatFormat.getFrameSize();
		byte buffer[] = new byte[bufferSize];
		hatOutStream = new ByteArrayOutputStream();
		try
		{
			int nBytesRead = 0;
			while (nBytesRead != -1)
			{
				nBytesRead = audioInputStream.read(buffer, 0, buffer.length);
				if (nBytesRead > 0)
					hatOutStream.write(buffer, 0, nBytesRead);
			}
			hatOutStream.close();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 Der Ton wird von einer zu waehlenden Datei geladen.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean ladeTon()
	{
		FileDialog ladendialog;
		String dateiname, pfadname;

		ladendialog = new FileDialog(Bildschirm.topFenster, "Ton laden",
				FileDialog.LOAD);
		ladendialog.setVisible(true);
		dateiname = ladendialog.getFile();
		if (dateiname != null) // Es wurde ok geklickt
		{
			pfadname = ladendialog.getDirectory();
			return this.ladeTon(pfadname + dateiname);
		}
		else
			return false;
	}

	/**
	 Der Ton wird abgespielt.
	 */
	public void spieleTon()
	{
		if (!zNimmtAuf && hatOutStream != null)
		{
			try
			{
				byte audio[] = hatOutStream.toByteArray();
				InputStream input = new ByteArrayInputStream(audio);
				final AudioInputStream ais = new AudioInputStream(input,
						hatFormat, audio.length / hatFormat.getFrameSize());
				DataLine.Info info = new DataLine.Info(SourceDataLine.class,
						hatFormat);
				final SourceDataLine line = (SourceDataLine) AudioSystem
						.getLine(info);
				line.open(hatFormat);
				line.start();

				Runnable runner = new Runnable()
				{
					int bufferSize = (int) hatFormat.getSampleRate()
							* hatFormat.getFrameSize();
					byte buffer[] = new byte[bufferSize];

					public void run()
					{
						zSpieltAb = true;
						try
						{
							int count;
							while ((count = ais.read(buffer, 0, buffer.length)) != -1
									&& zSpieltAb)
							{
								if (count > 0)
									line.write(buffer, 0, count);
							}
							if (zSpieltAb)
								line.drain();
							zSpieltAb = false;
						}
						catch (IOException e)
						{
							System.out.println("I/O problems: " + e);
							System.exit(-3);
						}
					}
				};
				Thread playThread = new Thread(runner);
				playThread.start();
			}
			catch (LineUnavailableException e)
			{
				System.out.println("Line unavailable: " + e);
				System.exit(-4);
			}
		}
	}

	/**
	 Das Abspielen des Tons wird gestoppt.
	 */
	public void stoppeAbspielen()
	{
		if (zSpieltAb)
			zSpieltAb = false;
	}

	/**
	 Der Ton wird als WAF-Datei pDatei gespeichert.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean speichereTon(String pDatei)
	{
		String fileName = pDatei + ".wav";
		File outputFile = new File(fileName);
		AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
		if (!zNimmtAuf && !zSpieltAb && hatOutStream != null)
		{
			byte audio[] = hatOutStream.toByteArray();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					audio);
			AudioInputStream audioInputStream = new AudioInputStream(
					byteArrayInputStream, hatFormat, audio.length
							/ hatFormat.getFrameSize());
			try
			{
				AudioSystem.write(audioInputStream, targetType, outputFile);
				return true;
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
				e.printStackTrace();
				return false;
			}
		}
		else
			return false;
	}

	/**
	 Der Ton wird mit einem zu waehlenden Namen gespeichert.
	 Bei Erfolg wird true zurueckgegeben.
	 */
	public boolean speichereTon()
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
			return this.speichereTon(pfadname + dateiname);
		}
		else
			return false;
	}

	/**
	 Der Ton wird freigegeben.
	 */
	public void gibFrei()
	{
	}
}