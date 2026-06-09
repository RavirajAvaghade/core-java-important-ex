import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // Game constants
    private final int WIDTH = 800, HEIGHT = 600;
    private final int GROUND_HEIGHT = 100;
    private final int PIPE_WIDTH = 80;
    private final int GAP_HEIGHT = 200;

    // Bird
    private int birdX = 150, birdY = 300, birdSize = 30;
    private double velocity = 0, gravity = 0.5, jumpStrength = -8;

    // Pipes
    private ArrayList<Rectangle> pipes;
    private Random rand;
    private int pipeSpeed = 4;

    // Game state
    private boolean gameOver = false;
    private int score = 0;

    private Timer timer;

    public FlappyBird() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        pipes = new ArrayList<>();
        rand = new Random();

        // Add first pipes
        addPipe(true);

        timer = new Timer(20, this); // 50 FPS
        timer.start();
    }

    private void addPipe(boolean start) {
        int height = 100 + rand.nextInt(HEIGHT - GAP_HEIGHT - GROUND_HEIGHT - 200);
        if (start) {
            pipes.add(new Rectangle(WIDTH + PIPE_WIDTH, 0, PIPE_WIDTH, height)); // Top
            pipes.add(new Rectangle(WIDTH + PIPE_WIDTH, height + GAP_HEIGHT, PIPE_WIDTH, HEIGHT - height - GAP_HEIGHT - GROUND_HEIGHT)); // Bottom
        } else {
            int lastX = pipes.get(pipes.size() - 1).x;
            pipes.add(new Rectangle(lastX + 300, 0, PIPE_WIDTH, height));
            pipes.add(new Rectangle(lastX + 300, height + GAP_HEIGHT, PIPE_WIDTH, HEIGHT - height - GAP_HEIGHT - GROUND_HEIGHT));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Bird physics
            velocity += gravity;
            birdY += velocity;

            // Move pipes
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= pipeSpeed;
            }

            // Remove off-screen pipes
            if (pipes.get(0).x + PIPE_WIDTH < 0) {
                pipes.remove(0);
                pipes.remove(0);
                addPipe(false);
                score++;
            }

            // Collision detection
            for (Rectangle pipe : pipes) {
                if (pipe.intersects(new Rectangle(birdX, birdY, birdSize, birdSize))) {
                    gameOver = true;
                }
            }

            if (birdY + birdSize > HEIGHT - GROUND_HEIGHT || birdY < 0) {
                gameOver = true;
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw ground
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, 20);

        // Draw pipes
        g.setColor(Color.green.darker());
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        // Draw bird
        g.setColor(Color.YELLOW);
        g.fillOval(birdX, birdY, birdSize, birdSize);

        // Draw score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial
