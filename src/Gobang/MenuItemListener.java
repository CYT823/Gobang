package Gobang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuItemListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("雙人P2P")) {
			GameFrame gf = new GameFrame();
			gf.setTitle("Gobang_P2P");
			gf.setSize(600, 615);
			gf.setLocationRelativeTo(null);
			gf.setResizable(false);
			gf.setVisible(true);
		} else if (e.getActionCommand().equals("人機P2C")) {
			GameFrame_AI afa = new GameFrame_AI();
			afa.setTitle("Gobang_P2C");
			afa.setSize(600, 615);
			afa.setLocationRelativeTo(null);
			afa.setResizable(false);
			afa.setVisible(true);
		}  else if (e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
	}
}
