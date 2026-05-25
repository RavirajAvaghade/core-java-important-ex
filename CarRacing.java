import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CarRacing extends JPanel implements ActionListener, KeyListener {
    // Window
    private final int WIDTH = 500;
    private final int HEIGHT = 700;

    // Road / lanes
    private final int LANE_COUNT = 3;
    private final int LANE_WIDTH = 300 / LANE_COUNT; // road width smaller than panel width for border
    private final int ROAD_X = 100; // left offset of road
    private int laneCenterX[] = new int[LANE_COUNT];

    // Player car
    private final int CAR_WIDTH = 40;
    private final int CAR_HEIGHT = 70;
    private int playerX;
    private int playerY;
    private Color playerColor = new Color(0, 160, 0); // green

    // Obstacles / enemy cars
    private class Enemy {
        Rectangle rect;
        Color color;
        int speed;
        Enemy(Rectangle r, Color c, int s) { rect = r; color = c; speed = s; }
    }
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Random rand = new Random();

    // Road lane line animation
    private int laneOffset = 0;
    private int speed = 6; // base speed (also affects obstacle speed)

    // Game
    private boolean gameOver = false;
    private int score = 0;

    private Timer timer;
    private int spawnTimer = 0;

    public CarRacing() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(100, 200, 255)); // sky-ish
        setFocusable(true);
        addKeyListener(this);

        // compute lane centers
        for (int i = 0; i < LANE_COUNT; i++) {
            laneCenterX[i] = ROAD_X + (LANE_WIDTH / 2) + i * LANE_WIDTH;
        }

        // position player at middle lane bottom
        playerX = laneCenterX[1] - CAR_WIDTH / 2;
        playerY = HEIGHT - CAR_HEIGHT - 80;

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw roadside grass
        g.setColor(new Color(40, 140, 40));
        g.fillRect(0, 0, ROAD_X, HEIGHT);
        g.fillRect(ROAD_X + LANE_WIDTH * LANE_COUNT, 0, WIDTH - (ROAD_X + LANE_WIDTH * LANE_COUNT), HEIGHT);

        // Draw road
        g.setColor(new Color(50, 50, 50));
        g.fillRect(ROAD_X, 0, LANE_WIDTH * LANE_COUNT, HEIGHT);

        // Draw lane separators (dashed)
        g.setColor(Color.WHITE);
        int dashH = 40;
        int gapH = 30;
        for (int lane = 1; lane < LANE_COUNT; lane++) {
            int x = ROAD_X + lane * LANE_WIDTH;
            for (int y = -laneOffset % (dashH + gapH); y < HEIGHT; y += dashH + gapH) {
                g.fillRect(x - 2, y, 4, dashH);
            }
        }

        // Draw side borders
        g.setColor(Color.YELLOW.darker());
        g.fillRect(ROAD_X + 5, 0, 6, HEIGHT);
        g.fillRect(ROAD_X + LANE_WIDTH * LANE_COUNT - 11, 0, 6, HEIGHT);

        // Draw enemies
        for (Enemy en : enemies) {
            g.setColor(en.color);
            Rectangle r = en.rect;
            g.fillRect(r.x, r.y, r.width, r.height);
            // window
            g.setColor(new Color(220, 240, 255, 200));
            g.fillRect(r.x + 8, r.y + 10, r.width - 16, r.height - 30);
            // small wheels
            g.setColor(Color.BLACK);
            g.fillOval(r.x + 6, r.y + r.height - 14, 10, 10);
            g.fillOval(r.x + r.width - 16, r.y + r.height - 14, 10, 10);
        }

        // Draw player car
        g.setColor(playerColor);
        g.fillRoundRect(playerX, playerY, CAR_WIDTH, CAR_HEIGHT, 10, 10);
        // windscreen
        g.setColor(new Color(200, 230, 255));
        g.fillRect(playerX + 8, playerY + 10, CAR_WIDTH - 16, CAR_HEIGHT - 28);
        // wheels
        g.setColor(Color.BLACK);
        g.fillOval(playerX + 4, playerY + CAR_HEIGHT - 12, 10, 10);
        g.fillOval(playerX + CAR_WIDTH - 14, playerY + CAR_HEIGHT - 12, 10, 10);

        // HUD: Score & instructions
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 16, 28);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Left/Right: Move   Up: Faster   Down: Slower   Enter: Restart", 16, 48);

        if (gameOver) {
            g.setColor(new Color(255, 0, 0, 200));
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH/2 - 160, HEIGHT/2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ENTER to play again", WIDTH/2 - 130, HEIGHT/2 + 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // move lane dashes to create illusion of movement
            laneOffset += speed;
            if (laneOffset > 1000000) laneOffset = laneOffset % 1000;

            // move enemies down
            Iterator<Enemy> it = enemies.iterator();
            while (it.hasNext()) {
                Enemy en = it.next();
                en.rect.y += en.speed;
                // collision with player?
                Rectangle playerRect = new Rectangle(playerX, playerY, CAR_WIDTH, CAR_HEIGHT);
                if (en.rect.intersects(playerRect)) {
                    gameOver = true;
                    timer.stop();
                }
                // remove off-screen enemies
                if (en.rect.y > HEIGHT) {
                    it.remove();
                    score += 1;
                }
            }

            // spawn enemies periodically
            spawnTimer += 16;
            int spawnInterval = Math.max(700 - (speed * 20), 350); // as speed increases spawn quicker
            if (spawnTimer > spawnInterval) {
                spawnTimer = 0;
                spawnEnemy();
            }

            // repaint
            repaint();
        }
    }

    private void spawnEnemy() {
        // Random lane
        int lane = rand.nextInt(LANE_COUNT);
        int cx = laneCenterX[lane];
        int ex = cx - CAR_WIDTH / 2;
        int ey = -CAR_HEIGHT - rand.nextInt(200);
        Rectangle r = new Rectangle(ex, ey, CAR_WIDTH, CAR_HEIGHT);
        // random color for enemy car
        Color c = new Color(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200));
        // speed relative to base speed
        int espeed = speed + rand.nextInt(3) + 2;
        enemies.add(new Enemy(r, c, espeed));
    }

    private void movePlayerLeft() {
        int targetX = playerX - LANE_WIDTH;
        int minX = ROAD_X + (LANE_WIDTH - CAR_WIDTH) / 2;
        if (playerX > minX - 5) {
            // snap to previous lane center
            int currentLane = getPlayerLane();
            int newLane = Math.max(0, currentLane - 1);
            playerX = laneCenterX[newLane] - CAR_WIDTH / 2;
        }
    }

    private void movePlayerRight() {
        int maxX = ROAD_X + LANE_WIDTH * LANE_COUNT - (LANE_WIDTH + CAR_WIDTH)/2;
        if (playerX < maxX + 5) {
            int currentLane = getPlayerLane();
            int newLane = Math.min(LANE_COUNT - 1, currentLane + 1);
            playerX = laneCenterX[newLane] - CAR_WIDTH / 2;
        }
    }

    private int getPlayerLane() {
        // find closest lane center
        int best = 0;
        int bestDist = Math.abs((playerX + CAR_WIDTH/2) - laneCenterX[0]);
        for (int i = 1; i < LANE_COUNT; i++) {
            int d = Math.abs((playerX + CAR_WIDTH/2) - laneCenterX[i]);
            if (d < bestDist) {
                bestDist = d;
                best = i;
            }
        }
        return best;
    }

    private void restart() {
        enemies.clear();
        score = 0;
        gameOver = false;
        playerX = laneCenterX[1] - CAR_WIDTH / 2;
        playerY = HEIGHT - CAR_HEIGHT - 80;
        speed = 6;
        laneOffset = 0;
        spawnTimer = 0;
        timer.start();
    }

    // Key controls
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                movePlayerLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                movePlayerRight();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                speed = Math.min(14, speed + 2); // faster
                // increase existing enemies speed a bit
                for (Enemy en : enemies) en.speed = Math.min(20, en.speed + 1);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                speed = Math.max(2, speed - 2); // slower
                for (Enemy en : enemies) en.speed = Math.max(2, en.speed - 1);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) restart();
        }
        // quick color change: C
        if (e.getKeyCode() == KeyEvent.VK_C) {
            playerColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Car Racing - Swing");
            CarRacing game = new CarRacing();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
