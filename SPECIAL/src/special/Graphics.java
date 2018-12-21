package special;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Graphics extends JFrame {
	
	private static final long serialVersionUID = 6966407076592298516L;

	JTextArea tArea;
	
	public Graphics() {
		super("SPECIAL v0.1");
		
		setBounds(100, 100, 400, 220);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		JPanel panel = new JPanel();
		tArea = new JTextArea(5, 20);
		JScrollPane areaSP = new JScrollPane(tArea);
		areaSP.setPreferredSize(new Dimension(334, 100));
		areaSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JTextField tCommend = new JTextField(30);
		JButton bInput = new JButton("run");
		
		tArea.setEditable(false);
		
		bInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tArea.setText(tArea.getText()+tCommend.getText()+"\n");
				tCommend.setText("");
			}
		});

		tCommend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tArea.setText(tArea.getText()+tCommend.getText()+"\n");
				tCommend.setText("");
				
			}
		});
		panel.add(areaSP);
		panel.add(tCommend);
		panel.add(bInput);
		contentPane.add(panel);
		
		setVisible(true);
	}
	
	void getTexts(String input)
	{
		tArea.setText(tArea.getText() + input +"\n");
	}
}
