import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CandyCrush extends JPanel {
    private final int rows = 8, cols = 8;
    private final int size = 60; 
    private Color[][] board;

    public CandyCrush() {
        board = new Color[rows][cols];
        String[] colors = {"RED", "BLUE", "GREEN", "YELLOW", "ORANGE"};
        
        // Fill board with random candies
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = Color.decode(getRandomColor(colors));
            }
        }
    }

    private String getRandomColor(String[] colors) {
        int rand = (int)(Math.random() * colors.length);
        switch (colors[rand]) {
            case "RED": return "#FF0000";
            case "BLUE": return "#0000FF";
            case "GREEN": return "#00FF00";
            case "YELLOW": return "#FFFF00";
            case "ORANGE": return "#FFA500";
        }
        return "#FFFFFF";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g.setColor(board[r][c]);
                g.fillRect(c * size, r * size, size, size);
                g.setColor(Color.BLACK);
                g.drawRect(c * size, r * size, size, size);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Candy Crush Demo");
        CandyCrush game = new CandyCrush();
        frame.add(game);
        frame.setSize(500, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
