package gui;

/**
 * Graphic Interface ! Uses Core.
 * @authors Alice MABILLE
 */
import core.Core;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;	
	private Core core = new Core();	
	private Container guiComponent;	
	private JButton readButton;
	private JButton writeButton;
	private ImageIcon icon;
	private JTextArea messageToWrite, messageFound, metadata;
	private JTextArea helpDisplay = new JTextArea(core.getGUIHelp());	
	private JMenu menuFichier, menuEdition, menuAffichage, menuAide;
	private JMenuItem ouvrir, fichier0, fichier1, fichier2, lire, ecrire, voirAide;
	private JCheckBoxMenuItem imgVisibleBtn; 
	private JMenuBar menuBar = new JMenuBar();	
	private Dimension minSize = new Dimension(800, 500);
	private JFileChooser fileChooser = new JFileChooser();
	private RecentFileMenu recents;
	private String path;	
	private ArrayList<String> paths = new ArrayList<String>();
	private ArrayList<String> cachePaths = new ArrayList<String>();
	
	public GUI(String title) {
		super(title);
		init();
	}
	
	private void init(){
		
		//initialisation des Jtrucs
		guiComponent = getContentPane();
		
		icon = new ImageIcon("assets/Image4.png");
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("cache/recentFilesData.tmp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			cachePaths = (ArrayList<String>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		messageToWrite = new JTextArea("Message à cacher");
		messageToWrite.setLineWrap(true);
		messageToWrite.setWrapStyleWord(true);
		messageToWrite.setEditable(true);
		JScrollPane areaScrollPane = new JScrollPane(messageToWrite);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		messageToWrite.setAlignmentX(LEFT_ALIGNMENT);
		
		metadata = new JTextArea("metadata");
		metadata.setLineWrap(true);
		metadata.setWrapStyleWord(true);
		metadata.setEditable(false);
		
		readButton = new JButton("Lire le message");
		writeButton = new JButton("Cacher le message dans l'image");
		readButton.addActionListener(new ReadMessageAction());
		writeButton.addActionListener(new WriteMessageAction());
		
		messageFound = new JTextArea("Message");
		messageFound.setLineWrap(true);
		messageFound.setWrapStyleWord(true);
		messageFound.setEditable(false);
		
		
		helpDisplay.setLineWrap(true);
		helpDisplay.setWrapStyleWord(true);
		helpDisplay.setEditable(false);
		helpDisplay.setVisible(false);
		add(helpDisplay);
		
		menuFichier = new JMenu("Fichier");
		ouvrir = new JMenuItem("Ouvrir...");
		ouvrir.addActionListener(new ChooseFileAction());
		recents=new RecentFileMenu("recents_files",10){
		public void onSelectFile(String filePath){
		        onRecentFile(filePath);
		    }
		};
		menuFichier.add(recents);

		
		menuEdition = new JMenu("Edition");
		lire = new JMenuItem("Lire le message");
		lire.addActionListener(new ReadMessageAction());
		ecrire = new JMenuItem("Ecrire un message...");
		ecrire.addActionListener(new WriteMessageAction());
		
		menuAffichage = new JMenu("Affichage");
		imgVisibleBtn = new JCheckBoxMenuItem("Image visible");
		
		menuAide = new JMenu("Aide");
		voirAide = new JCheckBoxMenuItem("Afficher l'aide");
		voirAide.addItemListener(new DisplayHelpAction());
		
		menuFichier.add(ouvrir);
		menuFichier.add(recents);
		menuEdition.add(lire);
		menuEdition.add(ecrire);
		menuAffichage.add(imgVisibleBtn);
		menuAide.add(voirAide);
		menuBar.add(menuFichier);
		menuBar.add(menuEdition);
		menuBar.add(menuAffichage);
		menuBar.add(menuAide);
		setJMenuBar(menuBar);
		
		JPanel writingPane = new JPanel();
		writingPane.setLayout(new BoxLayout(writingPane, BoxLayout.PAGE_AXIS));
		writingPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		writingPane.add(writeButton);
		writingPane.add(messageToWrite);

		JPanel readingPane = new JPanel();
		readingPane.setLayout(new BoxLayout(readingPane, BoxLayout.PAGE_AXIS));
		readingPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		readingPane.add(readButton);
		readingPane.add(messageFound);
		readingPane.add(metadata);

		//Put everything together, using the content pane's BorderLayout.
		add(writingPane, BorderLayout.LINE_START);
		add(readingPane, BorderLayout.LINE_END);
		
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
		messageFound.setFont(font);
				
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(minSize);
		pack();
		setIconImage(icon.getImage());
		setVisible(true);
		
	}

	private class ChooseFileAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChooser.showOpenDialog(guiComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				path=file.getPath();
				recents.addEntry(path);
				metadata.setText(core.getExifContent(path));
				FileOutputStream fos;
				try {
					fos = new FileOutputStream("cache/recentFilesData.tmp");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
			        oos.writeObject(paths);
			        oos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void onRecentFile(String filePath){
	   path=filePath;
	   recents.addEntry(path);
	   metadata.setText(core.getExifContent(path));
	}
	
	private class WriteMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
			if (!path.equals(null)) {
				core.hideMessage(path, messageToWrite.getText());
			}
			else {
				throw new IllegalArgumentException();
			}
		}
	}

	private class ReadMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFound.setText(core.readMessage(path));
		}

	}
	
	private class DisplayHelpAction implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				helpDisplay.setVisible(false);
			}
			else helpDisplay.setVisible(true);
		}

	}

	public static void main(String[] args) {
		new GUI("Stéganographe");
	}

}
