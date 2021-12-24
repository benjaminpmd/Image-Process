package gui;

/**
 * Graphic Interface! Uses Core.
 * @author Alice MABILLE
 * @version 1.0.0
 */
import core.Core;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;	
	private Core core = new Core();	
	private Container guiComponent;	
	private JButton readButton, writeButton;
	private JTextArea messageToWrite, messageFound, metadata;
	private JTextArea helpDisplay = new JTextArea(core.getGUIHelp());	
	private JMenu menuFichier, menuAide, menuRecents;
	private JMenuItem ouvrir, voirAide;
	private JMenuBar menuBar = new JMenuBar();
	private Dimension minSize = new Dimension(800, 500);
	private JFileChooser fileChooser = new JFileChooser();
	private String path = "";
	private String tempFilePath;
	private ArrayList<String> fileHistory;
	
	public Gui(String title) {
		super(title);
		init();
	}
	
	private void init(){
		guiComponent = getContentPane();
		new File("./cache/").mkdir();
		tempFilePath = "./cache/recent.tmp";
		new File(tempFilePath);
		fileHistory = new ArrayList<String>();
		
		messageToWrite = new JTextArea("Message to hide");
		messageToWrite.setLineWrap(true);
		messageToWrite.setWrapStyleWord(true);
		messageToWrite.setEditable(true);
		JScrollPane areaScrollPane = new JScrollPane(messageToWrite);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		messageToWrite.setAlignmentX(LEFT_ALIGNMENT);
		
		metadata = new JTextArea("Metadata");
		metadata.setLineWrap(true);
		metadata.setWrapStyleWord(true);
		metadata.setEditable(false);
		
		readButton = new JButton("Read the message");
		writeButton = new JButton("Hide the message");
		writeButton.setPreferredSize(new Dimension(250, 26));
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
		
		menuFichier = new JMenu("File");
		ouvrir = new JMenuItem("Open...");
		ouvrir.addActionListener(new ChooseFileAction());	
		menuRecents = new JMenu("Recent files");
		readRecentFile();
		menuAide = new JMenu("Help");
		voirAide = new JCheckBoxMenuItem("Show help");
		voirAide.addItemListener(new DisplayHelpAction());
		
		menuFichier.add(ouvrir);
		menuFichier.add(menuRecents);
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
		setVisible(true);
	}

	private class ChooseFileAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpeg", "jpg");
			fileChooser.setFileFilter(filter);
			fileChooser.setCurrentDirectory(new File("."));
			int returnVal = fileChooser.showOpenDialog(guiComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				path=file.getPath();
				metadata.setText(core.getExifContent(path));
			}
		}
	}
	
	private class WriteMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
			if (!path.isEmpty()) {
				String result = core.hideMessage(path, messageToWrite.getText());
				addRecentFile(path);
				messageFound.setText(result);
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

	private class OpenRecentFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			path = e.getActionCommand();
			metadata.setText(core.getExifContent(path));
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

	public void readRecentFile() {
		fileHistory.clear();
		menuRecents.removeAll();
		try {
			File temp = new File(tempFilePath);
            if (!temp.exists()) temp.createNewFile();
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(tempFilePath));
			String fileName = null;
			while ((fileName = (String) oos.readObject()) != null) {
				fileHistory.add(fileName);
				JMenuItem fileOption = menuRecents.add(new JMenuItem(fileName));
				fileOption.addActionListener(new OpenRecentFile());
			}
			oos.close();
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void addRecentFile(String path) {
		menuRecents.add(new JMenuItem(path), 0);
		if (fileHistory.contains(path)) {
			fileHistory.remove(path);
		}
		fileHistory.add(0, path);
		ObjectOutputStream oos;
		try {
			new File(tempFilePath).createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(tempFilePath));
			for (String fileName : fileHistory) {
				oos.writeObject(fileName);
			}
			oos.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		readRecentFile();
	}

	public static void main(String[] args) {
		new Gui("Steganography");
	}
}
