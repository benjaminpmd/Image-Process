package gui;

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

	private JButton button;
	private JMenu menu = new JMenu("Fichier");
	private JLabel label;
	private ImageIcon icon;
	
	public GUI(String title) {
		super(title);
		init();
	}

	private void init(){
		//initialisation des Jtrucs
		icon = new ImageIcon("assets/Image4.png");
		
		button = new JButton("bouton");
		
		label = new JLabel("Message");

		//Layout avec alignement
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		Container contentPane = getContentPane();
		contentPane.setLayout(flow);
		
		//mise en page
		contentPane.setLayout(new GridLayout(0, 1));
		contentPane.add(menu);
		contentPane.add(button);
		contentPane.add(label);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//il faut faire this.pack() en dernier afin qu'il prenne en compte tous les ï¿½lï¿½ments dans cette frame.
		pack();
		setIconImage(icon.getImage());
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new GUI("Stéganographe");
	}

}
