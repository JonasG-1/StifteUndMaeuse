package sum.multimedia;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import sum.ereignis.*;

/**
 Ein Film ist eine Komponente zur Darstellung eines Quicktime-Films.
 @author Bernard Schriek
 @version 7.5 vom 29.10.2013
 */
public class Film extends Frame implements Serializable
{
    private Frame hatFrame = null;
    private File hatFile;
    private Player hatPlayer;
    private Applet kenntApplet;

    private class FensterTester extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            schliessen();
        }
    }

    /**
     Eine neu Filmkomponente wird erzeugt.
     Der Pfad des Films wird als Parameter uebergeben.
     */
    public Film(String pPfad)
    {
        this.ladeFilm(pPfad);
    }

    /**
     Eine neu Filmkomponente wird erzeugt.
     Der Film wird in einem Dateidialog ausgewaehlt.
     */
    public Film(Applet pApplet)
    {
        kenntApplet = pApplet;
        this.ladeFilm();
    }

    /**
     Eine neu Filmkomponente wird erzeugt.
     Der Film wird in einem Dateidialog ausgewaehlt.
     */
    public Film()
    {
        this.ladeFilm();
    }

    /**
     Ein Film wird vom Pfad pPfad geladen.
     Bei Erfolg wird true zurueckgegeben.
     */
    public boolean ladeFilm(String pPfad)
    {
        if (hatFrame == null)
        {
            hatFrame = new Frame("SuM-Film");
            hatFrame.addWindowListener(new FensterTester());
        }
        try
        {
            haFile = new File(pPfad);
            hatPlayer = Manager.createRealizedPlayer(hatFile.toURI().toURL());
            hatFrame.add(hatPlayer.getVisualComponent);
            hatFrame.pack();
            hatFrame.setVisible(true);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     Der Film wird von einer zu waehlenden Datei (mov, aif, mpg) geladen.
     Bei Erfolg wird true zurueckgegeben.
     */
    public boolean ladeFilm()
    {
        FileDialog ladendialog;
        String dateiname, pfadname;

        ladendialog = new FileDialog(Bildschirm.topFenster, "Film laden",
                FileDialog.LOAD);
        ladendialog.setVisible(true);
        dateiname = ladendialog.getFile();
        if (dateiname != null) // Es wurde ok geklickt
        {
            pfadname = ladendialog.getDirectory();
            return this.ladeFilm(pfadname + dateiname);
        }
        else
            return false;
    }

    /** 
     Das Fenster wird geschlossen
     */
    private void schliessen()
    {
        QTSession.close();
        hatFrame.dispose();
        hatFrame = null;
        hatQTC = null;
        hatController = null;
        hatMovie = null;
    }

    /** 
     aus Kompatibilitaetsgruenden
     */
    public void gibFrei()
    {
    }

}