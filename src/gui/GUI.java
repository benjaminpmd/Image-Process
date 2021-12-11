package gui;

/**
 * Graphic Interface ! Uses Core.
 * @authors Alice MABILLE
 */
import core.Core;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Core core = new Core();
	
	private JButton readButton;
	private JButton writeButton;
	private ImageIcon icon;
	private JTextArea messageToWrite, messageFound;
	private JTextArea helpDisplay = new JTextArea(core.getGUIHelp());
	
	private JMenu menuFichier, menuEdition, menuAffichage, menuAide;
	private JMenuItem ouvrir, recents, enregistrer, enregistrer_sous, lire, ecrire, voirAide;
	private JCheckBoxMenuItem imgVisibleBtn; 
	private JMenuBar menuBar = new JMenuBar();
	private JPopupMenu menuPop = new JPopupMenu();
	

	//for testing
	private String path = "assets/Image4.png";
	
	public GUI(String title) {
		super(title);
		init();
	}

	private void init(){
		//initialisation des Jtrucs
		icon = new ImageIcon("assets/Image4.png");
		
		readButton = new JButton("Lire le message");
		writeButton = new JButton("Cacher le message dans l'image");
		
		messageFound = new JTextArea("Message");
		//messageToWrite.setLineWrap(true);
		//messageToWrite.setWrapStyleWord(true);
		//messageToWrite.setEditable(false);
		
		messageToWrite = new JTextArea("Message à cacher");
		messageToWrite.setLineWrap(true);
		messageToWrite.setWrapStyleWord(true);
		/*JScrollPane areaScrollPane = new JScrollPane(messageToWrite);
		areaScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		*/
		
		helpDisplay.setVisible(false);
		add(helpDisplay);
		
		menuFichier = new JMenu("Fichier");
		menuEdition = new JMenu("Edition");
		menuAffichage = new JMenu("Affichage");
		menuAide = new JMenu("Aide");
		ouvrir = new JMenuItem("Ouvrir...");
		recents = new JMenuItem("Récents");
		enregistrer = new JMenuItem("Enregistrer");
		enregistrer_sous = new JMenuItem("Enregistrer sous...");
		lire = new JMenuItem("Lire le message");
		ecrire = new JMenuItem("Ecrire un message...");
		imgVisibleBtn = new JCheckBoxMenuItem("Image visible");
		voirAide = new JMenuItem("Page d'aide");

		//Layout avec alignement
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		setLayout(flow);
		
		//this.getContentPane().add(everything);
		add(messageToWrite);
		readButton.addActionListener(new ReadMessageAction());
		add(readButton);
		writeButton.addActionListener(new WriteMessageAction());
		add(writeButton);
		add(messageFound);
		
		voirAide.addActionListener(new DisplayHelpAction());
		
		menuFichier.add(ouvrir);
		menuFichier.add(recents);
		menuFichier.add(enregistrer);
		menuFichier.add(enregistrer_sous);
		menuEdition.add(lire);
		menuEdition.add(ecrire);
		menuAffichage.add(imgVisibleBtn);
		menuAide.add(voirAide);
		menuBar.add(menuFichier);
		menuBar.add(menuEdition);
		menuBar.add(menuAffichage);
		menuBar.add(menuAide);
		setJMenuBar(menuBar);
		
		
		//pas compatible avec menubar??
		/*menuPop.add(menuFichier);
		menuPop.add(menuEdition);
		menuPop.add(menuAffichage);
		menuPop.add(menuAide);
		add(menuPop);
		*/
		
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 20);
		messageFound.setFont(font);
				
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setIconImage(icon.getImage());
		setVisible(true);
		
	}
	
	private class WriteMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			core.hideMessage(path, messageToWrite.getText());
		}
	}

	private class ReadMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFound.setText(core.readMessage(path));
		}

	}
	
	private class DisplayHelpAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			helpDisplay.setVisible(true);
		}

	}

	public static void main(String[] args) {
		new GUI("Stéganographe");
	}

}
