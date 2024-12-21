package testGUI_SE;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatBox implements ActionListener{
	JFrame mainFrame;
	JPanel panel;
	JLabel label;
	JButton send;
	JButton reset;
	JTextField tf;
	JTextArea ta;

	/*
	public static void main(String[] args) {
		ChatBox swingGUIMain = new ChatBox();
	}
	*/
	

	public ChatBox() {
		// Creating frame and assigning its attributes
		mainFrame = new JFrame("Chat Box");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setBounds(450, 190, 610, 300);
		mainFrame.getRootPane().setBorder(new EmptyBorder(15, 15, 15, 15));
		// Creating the panel at bottom and adding components
		// the panel is not visible in output
		panel = new JPanel();

		// JLabel to show text eg. title/header
		label = new JLabel("Enter Text");
		// JTextField to input text, accepts upto 10 characters
		tf = new JTextField(30);

		// Buttons and adding ActionListener
		send = new JButton("Send");
		send.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);

		// Components Added to JPanel using Flow Layout
		panel.add(label);
		panel.add(tf);
		panel.add(send);
		panel.add(reset);

		// Text Area at the Center
		ta = new JTextArea();

		// Adding Components to the frame.
		mainFrame.getContentPane().add(BorderLayout.CENTER, ta);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, panel);
		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// Action event for send button
		if (event.getSource() == send) {
			String enteredText = tf.getText();
			tf.setText(" ");
			ta.setText(enteredText);
		}

		// Action event for reset button
		if (event.getSource() == reset) {
			tf.setText("");
			ta.setText("");
		}
	}
}
