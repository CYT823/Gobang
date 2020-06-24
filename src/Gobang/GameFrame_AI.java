package Gobang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameFrame_AI extends JFrame {
	JPanel controlPanel;
	JPanel boardPanel;
	JButton startBtn;
	JButton restartBtn;
	int width;
	int height;
	int clickCount = 0;
	int[][] data = new int[17][17];
	boolean gameState = false; // 判斷遊戲開始

	GameFrame_AI() {
		HashMap<String,Integer> weightMap = getMap();
		
		controlPanel = new JPanel();
		startBtn = new JButton("START");
		restartBtn = new JButton("RESTART");
		restartBtn.setEnabled(false);
		controlPanel.add(startBtn);
		controlPanel.add(restartBtn);

		boardPanel = new JPanel();
		boardPanel.setBackground(new Color(210, 165, 0));
		width = boardPanel.getWidth();
		height = boardPanel.getHeight();

		add(controlPanel, BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);

		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartBtn.setEnabled(true);
				startBtn.setEnabled(false);
				gameState = true;

				Graphics g = boardPanel.getGraphics();
				Util.drawLine(g);
			}
		});

		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
				if (gameState) {
					int mouseX = mouseEvent.getX()/30;
					int mouseY = mouseEvent.getY()/30;
					
					Graphics g = boardPanel.getGraphics();
					clickCount++;
					boolean check = Util.drawGobang(g, mouseX, mouseY, clickCount, data); // 有重複下點的可能
					if (!check) {
						clickCount--;
					}
					
					boolean ch1 = Util.checkHorizontalWin(data, mouseX - 1, mouseY); //左右連線檢查
					boolean ch2 = Util.checkVerticalWin(data, mouseX - 1, mouseY);//上下連線檢查
					boolean ch3 = Util.checkTopLeftToBottomRight(data, mouseX - 1, mouseY);//左上到右下連線檢查
					boolean ch4 = Util.checkTopRightToBottomLeft(data, mouseX - 1, mouseY);//由上到左下連線檢查
					if(ch1 || ch2 || ch3 || ch4) {
						if(data[mouseX - 1][mouseY] == 1) 
							JOptionPane.showMessageDialog(null, "黑方獲勝","",JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "白方獲勝","",JOptionPane.INFORMATION_MESSAGE);
						
						Util.restartGame(g);//初始化
						clickCount = 0; 
						data = new int[17][17]; 
						gameState = false;
						restartBtn.setEnabled(false);
						startBtn.setEnabled(true);
						return;
					}
					
					int[][] weightArray = new int[17][17];
					for (int x = 0; x < 17; x++) { //收集weight
						for (int y = 0; y < 17; y++) {
							if(data[x][y]==0) {
								weightArray[x][y] = Util.getWeight(data, x, y, weightMap);
							}
						}
					}
					int max = weightArray[0][0];
					int valueX = 0;
					int valueY = 0;
					for (int x = 0; x < 17; x++) { //找最大
						for (int y = 0; y < 17; y++) {
							if(weightArray[x][y] > max) {
								max = weightArray[x][y];
								valueX = x;
								valueY = y;
							}
						}
					}
					
					clickCount++;
					Util.drawGobang(g, valueX+1, valueY, clickCount, data);
					ch1 = Util.checkHorizontalWin(data, valueX, valueY); // 左右連線檢查
					ch2 = Util.checkVerticalWin(data, valueX, valueY);// 上下連線檢查
					ch3 = Util.checkTopLeftToBottomRight(data, valueX, valueY);// 左上到右下連線檢查
					ch4 = Util.checkTopRightToBottomLeft(data, valueX, valueY);//由上到左下連線檢查
					if(ch1 || ch2 || ch3 || ch4) {
						if(data[valueX+1][valueY] == 1) 
							JOptionPane.showMessageDialog(null, "黑方獲勝","",JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "白方獲勝","",JOptionPane.INFORMATION_MESSAGE);
						
						Util.restartGame(g);//初始化
						clickCount = 0; 
						data = new int[17][17]; 
						gameState = false;
						restartBtn.setEnabled(false);
						startBtn.setEnabled(true);
					}
				}
			}
		});

		restartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = boardPanel.getGraphics();
				Util.restartGame(g);
				//初始化
				clickCount = 0; 
				data = new int[17][17]; 
			}
		});
	}
	
	private HashMap<String,Integer> getMap() {
		HashMap<String,Integer> weightMap = new HashMap<String, Integer>();
		weightMap.put("", 0);
		weightMap.put("2", 20);
		weightMap.put("1", 10);
		weightMap.put("22", 200);
		weightMap.put("21", 15);
		weightMap.put("11", 190);
		weightMap.put("12", 15);
		weightMap.put("222", 3000);
		weightMap.put("221", 10);
		weightMap.put("112", 15);
		weightMap.put("111", 2000);
		weightMap.put("2222", 50000);
		weightMap.put("2221", 200);
		weightMap.put("1111", 20000);
		weightMap.put("1112", 200);
		weightMap.put("22221", 30000);
		weightMap.put("11112", 20000);
		
		return weightMap;
	}
}
