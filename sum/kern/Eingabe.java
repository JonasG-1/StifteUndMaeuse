package sum.kern;

 

import sum.kern.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.FontMetrics;

/**
Die Eingabe dient dazu, in einem Fenster Eingaben mit den
Anfragen holeText, holeGanzeZahl und holeZahl der Klasse
Bildschirm zu empfangen,
um diese an das Programm weiterzugeben.
@author Bernard Schriek
@version 7.5 vom 29.10.2013
*/
class Eingabe extends JDialog implements ActionListener
{
	JButton hatButton;
	JLabel hatLabel;
	JTextField hatEingabe;
	String zEingabe;
	
	protected Eingabe(JFrame pFenster, String pMeldung)
	{
		super(pFenster, "Eingabe", true);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setSize(300, 130);
		this.setLocation(200, 100);
		hatButton = new JButton("Ok");
		hatButton.addActionListener(this);
		hatButton.setLocation(120, 70);
		hatButton.setSize(60, 30);
		this.getContentPane().add(hatButton);
		FontMetrics fm = pFenster.getGraphics().getFontMetrics();
		int laenge = fm.stringWidth(pMeldung);
		hatLabel = new JLabel(pMeldung);
		hatLabel.setLocation(10, 10);
		hatLabel.setSize(280, 20);
		this.getContentPane().add(hatLabel);
		hatEingabe = new JTextField("");
		hatEingabe.setLocation(10, 40);
		hatEingabe.setSize(280, 20);
		this.getContentPane().add(hatEingabe);
		zEingabe = "";
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent pEvent)
	{
	    boolean lOk;
	    
		lOk = pEvent.getActionCommand().equals("Ok");
		zEingabe = hatEingabe.getText();
		this.setVisible(false);
		this.dispose();
	}
	
	protected String eingabe()
	{
		return zEingabe;
	}
}