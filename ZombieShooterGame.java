import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class Bullet {
    int x, y;
    final int width = 5, height = 10, speed = 10;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

class Zombie {
    int x, y;
    final int width = 40, height = 40, speed = 2;

    public Zombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

public class ZombieShooterGame extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    private Rectangle player;
    private int playerSpeed = 5;
    private boolean left, right, up, down;
    private Timer timer;
    private ArrayList<Bullet> bullets;
    private ArrayList<Zombie> zombies;
    private Random rand;
    private int score = 0;

    public ZombieShooterGame() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        player = new Rectangle(WIDTH / 2 - 20, HEIGHT - 80, 40, 40);
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        rand = new Random();

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // player
        g.setColor(Color.BLUE);
        g.fillRect(player.x, player.y, player.width, player.height);

        // bullets
        g.setColor(Color.YELLOW);
        for (Bullet b : bullets) g.fillRect(b.x, b.y, b.width, b.height);

        // zombies
        g.setColor(Color.GREEN);
        for (Zombie z : zombies) g.fillRect(z.x, z.y, z.width, z.height);

        // score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
    }

    private void movePlayer() {
        if (left && player.x > 0) player.x -= playerSpeed;
        if (right && player.x < WIDTH - player.width) player.x += playerSpeed;
        if (up && player.y > 0) player.y -= playerSpeed;
        if (down && player.y < HEIGHT - player.height) player.y += playerSpeed;
    }

    private void moveBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.move();
            if (b.y < 0) it.remove();
        }
    }

    private void spawnZombies() {
        if (zombies.size() < 5) {
            int x = rand.nextInt(WIDTH - 40);
            zombies.add(new Zombie(x, -40));
        }
    }

    private void moveZombies() {
        Iterator<Zombie> it = zombies.iterator();
        while (it.hasNext()) {
            Zombie z = it.next();
            z.move();
            if (z.y > HEIGHT) it.remove();
            if (player.intersects(z.getBounds())) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }
    }

    private void checkCollisions() {
        Iterator<Bullet> bIt = bullets.iterator();
        while (bIt.hasNext()) {
            Bullet b = bIt.next();
            Iterator<Zombie> zIt = zombies.iterator();
            while (zIt.hasNext()) {
                Zombie z = zIt.next();
                if (b.getBounds().intersects(z.getBounds())) {
                    bIt.remove();
                    zIt.remove();
                    score++;
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePlayer();
        moveBullets();
        spawnZombies();
        moveZombies();
        checkCollisions();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT: left = true; break;
            case KeyEvent.VK_RIGHT: right = true; break;
            case KeyEvent.VK_UP: up = true; break;
            case KeyEvent.VK_DOWN: down = true; break;
            case KeyEvent.VK_SPACE: bullets.add(new Bullet(player.x + player.width/2 - 2, player.y)); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT: left = false; break;
            case KeyEvent.VK_RIGHT: right = false; break;
            case KeyEvent.VK_UP: up = false; break;
            case KeyEvent.VK_DOWN: down = false; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zombie Shooter Game");
        ZombieShooterGame game = new ZombieShooterGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
