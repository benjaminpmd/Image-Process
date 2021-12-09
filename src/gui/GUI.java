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
import javax.swing.JMenu;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton readButton;
	private JMenu menu = new JMenu("Fichier");
	private JLabel label;
	private ImageIcon icon;
	
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
		
		label = new JLabel("Message");

		//Layout avec alignement
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		Container contentPane = getContentPane();
		contentPane.setLayout(flow);
		
		contentPane.add(menu);
		contentPane.add(readButton);
		readButton.addActionListener(new ReadMessageAction());
		
		contentPane.add(label);
		
		//Quelques polices, couleurs
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 20);
		label.setFont(font);
		label.setForeground(Color.BLUE);
		contentPane.setBackground(Color.PINK);
				
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setIconImage(icon.getImage());
		setVisible(true);
		
	}
	
	private class WriteMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	private class ReadMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			label.setText(core.readMessage(path));
		}

	}

	public static void main(String[] args) {
		new GUI("Stéganographe");
	}

}
