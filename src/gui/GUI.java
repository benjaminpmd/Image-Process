package gui;

/**
 * Graphic Interface ! Uses Core.
 * @authors Alice MABILLE
 */
import core.Core;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenu;
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

	private JButton readButton;
	private JButton writeButton;
	private JMenu menu = new JMenu("Fichier");
	private ImageIcon icon;
	private JTextArea messageToWrite;
	private JTextArea messageFound;
	
	Core core = new Core();
	
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

		//Layout avec alignement
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		Container contentPane = getContentPane();
		contentPane.setLayout(flow);
		
		contentPane.add(messageToWrite);
		contentPane.add(menu);
		readButton.addActionListener(new ReadMessageAction());
		contentPane.add(readButton);
		writeButton.addActionListener(new WriteMessageAction());
		contentPane.add(writeButton);
		contentPane.add(messageFound);
		
		//Quelques polices, couleurs
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 20);
		messageFound.setFont(font);
		messageFound.setForeground(Color.BLUE);
		contentPane.setBackground(Color.PINK);
				
				
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

	public static void main(String[] args) {
		new GUI("Stéganographe");
	}

}
