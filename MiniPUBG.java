import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class MiniPUBG {
    int x, y;
    final int width = 5, height = 10, speed = 10;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() { y -= speed; }

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}

class Enemy {
    int x, y;
    final int width = 40, height = 40, speed = 2;

    public Enemy(int x, int y) { this.x = x; this.y = y; }

    public void move() { y += speed; }

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}

public class MiniPUBG extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 500, HEIGHT = 600;
    private Rectangle player;
    private int playerSpeed = 5;
    private boolean left, right, up, down;
    private Timer timer;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private Random rand;
    private int score = 0;

    public MiniPUBG() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(this);

        player = new Rectangle(WIDTH/2 - 20, HEIGHT - 80, 40, 40);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
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
        for(Bullet b : bullets) g.fillRect(b.x, b.y, b.width, b.height);

        // enemies
        g.setColor(Color.RED);
        for(Enemy e : enemies) g.fillRect(e.x, e.y, e.width, e.height);

        // score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
    }

    private void movePlayer() {
        if(left && player.x > 0) player.x -= playerSpeed;
        if(right && player.x < WIDTH - player.width) player.x += playerSpeed;
        if(up && player.y > 0) player.y -= playerSpeed;
        if(down && player.y < HEIGHT - player.height) player.y += playerSpeed;
    }

    private void moveBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while(it.hasNext()) {
            Bullet b = it.next();
            b.move();
            if(b.y < 0) it.remove();
        }
    }

    private void spawnEnemies() {
        if(enemies.size() < 5) {
            int x = rand.nextInt(WIDTH - 40);
            enemies.add(new Enemy(x, -40));
        }
    }

    private void moveEnemies() {
        Iterator<Enemy> it = enemies.iterator();
        while(it.hasNext()) {
            Enemy e = it.next();
            e.move();
            if(e.y > HEIGHT) it.remove();
            if(player.intersects(e.getBounds())) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                System.exit(0);
            }
        }
    }

    private void checkCollisions() {
        Iterator<Bullet> bIt = bullets.iterator();
        while(bIt.hasNext()) {
            Bullet b = bIt.next();
            Iterator<Enemy> eIt = enemies.iterator();
            while(eIt.hasNext()) {
                Enemy e = eIt.next();
                if(b.getBounds().intersects(e.getBounds())) {
                    bIt.remove();
                    eIt.remove();
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
        spawnEnemies();
        moveEnemies();
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
        JFrame frame = new JFrame("Mini PUBG 2D Shooter");
        MiniPUBG game = new MiniPUBG();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
