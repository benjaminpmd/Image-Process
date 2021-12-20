package gui;

/**
 * Graphic Interface ! Uses Core.
 * @author Alice MABILLE
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
import javax.swing.filechooser.FileNameExtensionFilter;
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

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;	
	private Core core = new Core();	
	private Container guiComponent;	
	private JButton readButton, writeButton;
	private ImageIcon icon;
	private JTextArea messageToWrite, messageFound, metadata;
	private JTextArea helpDisplay = new JTextArea(core.getGUIHelp());	
	private JMenu menuFichier, menuAide;
	private JMenuItem ouvrir, voirAide;
	private JMenuBar menuBar = new JMenuBar();
	private Dimension minSize = new Dimension(800, 500);
	private JFileChooser fileChooser = new JFileChooser();
	private RecentFileMenu recents;
	private String path = "";
	
	public GUI(String title) {
		super(title);
		init();
	}
	
	private void init(){
		guiComponent = getContentPane();
		
		icon = new ImageIcon("assets/Image4.png");
		
		messageToWrite = new JTextArea("Message a cacher");
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
		
		menuAide = new JMenu("Aide");
		voirAide = new JCheckBoxMenuItem("Afficher l'aide");
		voirAide.addItemListener(new DisplayHelpAction());
		
		menuFichier.add(ouvrir);
		menuFichier.add(recents);
		menuAide.add(voirAide);
		menuBar.add(menuFichier);
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
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpeg", "jpg");
			fileChooser.setFileFilter(filter);
			int returnVal = fileChooser.showOpenDialog(guiComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				path=file.getPath();
				recents.addEntry(path);
				metadata.setText(core.getExifContent(path));
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
			if (!path.isEmpty()) {
				core.hideMessage(path, messageToWrite.getText());
			}
			else {
				new ChooseFileAction().actionPerformed(e);;
				core.hideMessage(path, messageToWrite.getText());
			}
		}
	}

	private class ReadMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!path.isEmpty()) {
				messageFound.setText(core.readMessage(path));
			}
			else {
				new ChooseFileAction().actionPerformed(e);
				messageFound.setText(core.readMessage(path));
			}
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
		new GUI("Steganographe");
	}

}
