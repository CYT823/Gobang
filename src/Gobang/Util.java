package Gobang;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class Util {
	final static boolean checkPixelBound(int x, int y) { // 檢查滑鼠點選位置
		if (x < 0 || x > 16 || y < 0 || y > 16) {
			return false;
		}
		return true;
	}

	final static void drawLine(Graphics g) { // 畫棋盤
		g.setColor(Color.BLACK);
		for (int i = 40; i <= 520; i += 30) { // 畫行
			g.drawLine(i, 20, i, 500);
			g.drawLine(i + 1, 20, i + 1, 500);
			g.drawLine(i + 2, 20, i + 2, 500);
		}
		for (int i = 20; i <= 500; i += 30) { // 畫列
			g.drawLine(40, i, 520, i);
			g.drawLine(40, i + 1, 520, i + 1);
			g.drawLine(40, i + 2, 520, i + 2);
		}
	}

	final static boolean drawGobang(Graphics g, int x, int y, int clickCount, int[][] data) { // 畫棋子
		// 檢查有沒有下過
		if (checkPixelBound(x - 1, y) && data[x - 1][y] == 0) {
			if (clickCount % 2 == 1) { // 黑棋
				g.setColor(Color.BLACK);
				data[x - 1][y] = 1;
			} else {// 白棋
				data[x - 1][y] = 2;
				g.setColor(Color.WHITE);
			}
			g.fillOval((x * 30 + 2), (y * 30 + 10), 20, 20); // 修正圓座標
			return true;// 表示有下棋countClick不用-1
		} else {
			return false;
		}
	}

	final static boolean  checkHorizontalWin(int[][] data, int x, int y) { // 左右連線
		int count = 1;
		int i = 1;
		while (x + i < 17) {
			if (data[x + i][y] == data[x][y]) { // 向右
				count++;
				i++;
			} else
				break;
		}

		int j = -1;
		while (x + j >= 0) {
			if (data[x + j][y] == data[x][y]) { // 向左
				count++;
				j--;
			} else
				break;
		}

		if (count >= 5)  // 結束
			return true;
		else
			return false;
	}

	final static boolean checkVerticalWin(int[][] data, int x, int y) { // 上下連線
		int count = 1;
		int i = 1;
		while (y + i < 17) {
			if (data[x][y + i] == data[x][y]) { // 向下
				count++;
				i++;
			} else
				break;
		}

		int j = -1;
		while (y + j >= 0) {
			if (data[x][y + j] == data[x][y]) { // 向上
				count++;
				j--;
			} else
				break;
		}

		if (count >= 5)  // 結束
			return true;
		else
			return false;
	}

	final static boolean checkTopLeftToBottomRight(int[][] data, int x, int y) { // 左上到右下連線連線
		int count = 1;
		int i = 1;
		while (x + i < 17 && y + i < 17) {
			if (data[x + i][y + i] == data[x][y]) { // 向右下
				count++;
				i++;
			} else
				break;
		}

		int j = -1;
		while (x + j >= 0 && y + j >= 0) {
			if (data[x + j][y + j] == data[x][y]) { // 向左上
				count++;
				j--;
			} else
				break;
		}

		if (count >= 5)  // 結束
			return true;
		else
			return false;
	}
	
	final static boolean checkTopRightToBottomLeft(int[][] data, int x, int y) { // 右上到左下連線連線
		int count = 1;
		int i = 1;
		while (x + i < 17 && y - i >= 0 ) {
			if (data[x + i][y - i] == data[x][y]) { // 向右上
				count++;
				i++;
			} else
				break;
		}

		int j = -1;
		while (x + j >= 0 && y - j < 17) {
			if (data[x + j][y - j] == data[x][y]) { // 向左下
				count++;
				j--;
			} else
				break;
		}

		if (count >= 5)  // 結束
			return true;
		else
			return false;
	}
	
	final static void restartGame(Graphics g) {
		g.setColor(new Color(210,165,0));
		g.fillRect(0, 0, 600, 600);
		Util.drawLine(g);
	}
	
	final static int getWeight(int[][] data, int x, int y, HashMap<String,Integer> weightMap) {
		int value = 0;
		String chess = "";
		int color = 0;
		
		for (int i = x + 1; i < 17; i++) { // 往右
			if (data[i][y] == 0) {
				break;
			} else {
				if (color == 0) {
					color = data[i][y]; // 紀錄右邊那顆color
					chess += data[i][y];
				} else if (color == data[i][y]) {
					chess += data[i][y];
				} else if (color != data[i][y]) {
					chess += data[i][y];
					break;
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = x - 1; i >= 0; i--) { // 往左
			if (data[i][y] == 0) {
				break;
			} else {
				if (color == 0) {
					color = data[i][y]; // 紀錄左邊那顆color
					chess += data[i][y];
				} else if (color == data[i][y]) {
					chess += data[i][y];
				} else if (color != data[i][y]) {
					chess += data[i][y];
					break;
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = y - 1; i >= 0; i--) { // 往上
			if (data[x][i] == 0) {
				break;
			} else {
				if (color == 0) {
					color = data[x][i]; // 紀錄上面那顆color
					chess += data[x][i];
				} else if (color == data[x][i]) {
					chess += data[x][i];
				} else if (color != data[x][i]) {
					chess += data[x][i];
					break;
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = y + 1; i < 17; i++) { // 往下
			if (data[x][i] == 0) {
				break;
			} else {
				if (color == 0) {
					color = data[x][i]; // 紀錄下面那顆color
					chess += data[x][i];
				} else if (color == data[x][i]) {
					chess += data[x][i];
				} else if (color != data[x][i]) {
					chess += data[x][i];
					break;
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = 1; i < 17; i++) {// 往左上
			if (x - i < 0 || y - i < 0) {
				break;
			} else {
				if (data[x - i][y - i] == 0) {
					break;
				} else {
					if (color == 0) {
						color = data[x - i][y - i]; // 紀錄左上那顆color
						chess += data[x - i][y - i];
					} else if (color == data[x - i][y - i]) {
						chess += data[x - i][y - i];
					} else if (color != data[x - i][y - i]) {
						chess += data[x - i][y - i];
						break;
					}
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = 1; i < 17; i++) {// 往左下
			if (x - i < 0 || y + i > 16) {
				break;
			} else {
				if (data[x - i][y + i] == 0) {
					break;
				} else {
					if (color == 0) {
						color = data[x - i][y + i]; // 紀錄左下那顆color
						chess += data[x - i][y + i];
					} else if (color == data[x - i][y + i]) {
						chess += data[x - i][y + i];
					} else if (color != data[x - i][y + i]) {
						chess += data[x - i][y + i];
						break;
					}
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = 1; i < 17; i++) {// 往右上
			if (x + i > 16 || y - i < 0) {
				break;
			} else {
				if (data[x + i][y - i] == 0) {
					break;
				} else {
					if (color == 0) {
						color = data[x + i][y - i]; // 紀錄右上那顆color
						chess += data[x + i][y - i];
					} else if (color == data[x + i][y - i]) {
						chess += data[x + i][y - i];
					} else if (color != data[x + i][y - i]) {
						chess += data[x + i][y - i];
						break;
					}
				}
			}
		}
		value += weightMap.get(chess);
		
		chess = "";
		color = 0;
		for (int i = 1; i < 17; i++) {// 往右下
			if (x + i > 16 || y + i > 16) {
				break;
			} else {
				if (data[x + i][y + i] == 0) {
					break;
				} else {
					if (color == 0) {
						color = data[x + i][y + i]; // 紀錄右下那顆color
						chess += data[x + i][y + i];
					} else if (color == data[x + i][y + i]) {
						chess += data[x + i][y + i];
					} else if (color != data[x + i][y + i]) {
						chess += data[x + i][y + i];
						break;
					}
				}
			}
		}
		value += weightMap.get(chess);
		
		return value;
	}
}
