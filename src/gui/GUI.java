package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class GUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
	private JButton button;
	private JLabel label;
	
	public GUI() {
		//initialisation des Jtrucs
		frame = new JFrame("Stéganographe");
		
		button = new JButton("bouton");
		button.addActionListener(this);
		
		label = new JLabel("Message");
		
		panel = new JPanel();
		
		//mise en page
		panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(button);
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//il faut faire frame.pack() en dernier afin qu'il prenne en compte tous les éléments dans cette frame.
		frame.pack();
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		new GUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
