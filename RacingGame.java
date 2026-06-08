import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class RacingGame extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    private final int CAR_WIDTH = 40;
    private final int CAR_HEIGHT = 60;
    private Rectangle player;
    private ArrayList<Rectangle> obstacles;
    private Timer timer;
    private boolean left, right;
    private int score = 0;
    private Random rand;

    public RacingGame() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(this);

        rand = new Random();
        obstacles = new ArrayList<>();
        player = new Rectangle(WIDTH / 2 - CAR_WIDTH / 2, HEIGHT - CAR_HEIGHT - 10, CAR_WIDTH, CAR_HEIGHT);

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw player car
        g.setColor(Color.BLUE);
        g.fillRect(player.x, player.y, player.width, player.height);

        // draw obstacles
        g.setColor(Color.RED);
        for (Rectangle obs : obstacles) {
            g.fillRect(obs.x, obs.y, obs.width, obs.height);
        }

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
    }

    private void movePlayer() {
        if (left && player.x > 0) player.x -= 5;
        if (right && player.x < WIDTH - CAR_WIDTH) player.x += 5;
    }

    private void moveObstacles() {
        Iterator<Rectangle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Rectangle obs = iter.next();
            obs.y += 5;
            if (obs.y > HEIGHT) {
                iter.remove();
                score++;
            }
            // collision
            if (player.intersects(obs)) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }
    }

    private void spawnObstacles() {
        if (obstacles.size() < 5) {
            int x = rand.nextInt(WIDTH - CAR_WIDTH);
            Rectangle obs = new Rectangle(x, -CAR_HEIGHT, CAR_WIDTH, CAR_HEIGHT);
            obstacles.add(obs);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePlayer();
        moveObstacles();
        spawnObstacles();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Racing Game");
        RacingGame game = new RacingGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
