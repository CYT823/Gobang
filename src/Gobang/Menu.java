package Gobang;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Menu {
	private JFrame frame;
	private JPanel controlPanel;
	Menu(){
		frame = new JFrame("GOBANG");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		final JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("選單");
		MenuItemListener menuItemListener = new MenuItemListener();
		
		JMenuItem gobangFrameMenuItem = new JMenuItem("雙人P2P");
		gobangFrameMenuItem.setMnemonic(KeyEvent.VK_P);
		gobangFrameMenuItem.setActionCommand("雙人P2P");
		gobangFrameMenuItem.addActionListener(menuItemListener);
		
		JMenuItem gobang_AIFrameMenuItem = new JMenuItem("人機P2C");
		gobang_AIFrameMenuItem.setMnemonic(KeyEvent.VK_C);
		gobang_AIFrameMenuItem.setActionCommand("人機P2C");
		gobang_AIFrameMenuItem.addActionListener(menuItemListener);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setActionCommand("EXIT");
		exitMenuItem.addActionListener(menuItemListener);
		
		optionMenu.add(gobangFrameMenuItem);
		optionMenu.add(gobang_AIFrameMenuItem);
		optionMenu.addSeparator();
		optionMenu.add(exitMenuItem);
		
		menuBar.add(optionMenu);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}
}
