import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * SimpleLudo.java
 * साधी Ludo सारखी खेळ आवृत्ती (2 players: RED आणि BLUE).
 *
 * Controls:
 * - "Roll Dice" बटण -> डाइस रोल करा.
 * - नंतर ज्याचा टर्न आहे त्याचा टोकन क्लिक करून चालवा (जर चालवण्यायोग्य असेल तर).
 * - टर्न, रोल, स्कोअर इ. स्क्रीनवर दिसेल.
 *
 * नोट: या सोप्या आवृत्तीत ट्रॅक length = 20 आहे. टोकनला बाहेर आणण्यासाठी 6 येणे आवश्यक.
 */
public class SimpleLudo extends JPanel implements MouseListener, ActionListener {
    // UI constants
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;
    private static final int BOARD_PADDING = 60;
    private static final int CELL_SIZE = 60;

    // Game path: circle-like 20 positions coordinates (clockwise)
    private final Point[] path;
    private final int PATH_LEN = 20;

    // Players
    enum PlayerColor { RED, BLUE }
    private final Color RED_COLOR = new Color(200, 30, 30);
    private final Color BLUE_COLOR = new Color(30, 60, 200);

    // Each player has 4 tokens
    class Token {
        int pos; // -1 means at home, >=0 means index on path, >= PATH_LEN means finished
        boolean finished;
        Token() { pos = -1; finished = false; }
    }

    private final Map<PlayerColor, Token[]> tokens = new HashMap<>();

    // Game state
    private PlayerColor currentTurn = PlayerColor.RED;
    private int diceValue = 0;
    private boolean rolled = false;
    private String message = "Start: RED's turn. Press Roll Dice.";
    private Random rand = new Random();

    // UI controls
    private final JButton rollButton;
    private final JButton newGameButton;

    public SimpleLudo() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(245, 245, 230));
        addMouseListener(this);

        // create path points (rough circle/rectangle around board center)
        path = new Point[PATH_LEN];
        int cx = WIDTH/2;
        int cy = HEIGHT/2;
        int radiusX = 220;
        int radiusY = 220;
        for (int i = 0; i < PATH_LEN; i++) {
            double angle = 2 * Math.PI * i / PATH_LEN - Math.PI/2;
            int x = cx + (int)(radiusX * Math.cos(angle));
            int y = cy + (int)(radiusY * Math.sin(angle));
            path[i] = new Point(x, y);
        }

        // init tokens
        tokens.put(PlayerColor.RED, new Token[4]);
        tokens.put(PlayerColor.BLUE, new Token[4]);
        for (int i = 0; i < 4; i++) {
            tokens.get(PlayerColor.RED)[i] = new Token();
            tokens.get(PlayerColor.BLUE)[i] = new Token();
        }

        // Buttons
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(this);
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> resetGame());

        // Add buttons to panel with layout
        setLayout(null);
        rollButton.setBounds(20, 20, 120, 30);
        newGameButton.setBounds(150, 20, 120, 30);
        add(rollButton);
        add(newGameButton);
    }

    private void resetGame() {
        for (Token t : tokens.get(PlayerColor.RED)) { t.pos = -1; t.finished = false; }
        for (Token t : tokens.get(PlayerColor.BLUE)) { t.pos = -1; t.finished = false; }
        currentTurn = PlayerColor.RED;
        diceValue = 0;
        rolled = false;
        message = "New game: RED's turn. Press Roll Dice.";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Title
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Simple Ludo (2 Players) - RED vs BLUE", 260, 30);

        // Draw central board area
        drawPath(g);

        // Draw tokens
        drawTokens(g);

        // Draw UI info: turn, dice, message
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.drawString("Current Turn: " + (currentTurn == PlayerColor.RED ? "RED" : "BLUE"), 20, 80);
        g.drawString("Dice: " + (rolled ? diceValue : "-"), 20, 110);
        g.drawString("Message: " + message, 20, 140);

        // Draw if token at home
        g.drawString("RED home: " + countAtHome(PlayerColor.RED) + "  finished: " + countFinished(PlayerColor.RED), 20, 170);
        g.drawString("BLUE home: " + countAtHome(PlayerColor.BLUE) + "  finished: " + countFinished(PlayerColor.BLUE), 20, 200);

        // Draw instructions
        g.setFont(new Font("SansSerif", Font.ITALIC, 12));
        g.drawString("Instructions: Roll dice. If you get 6 you can bring a token out from home.", 20, HEIGHT - 40);
        g.drawString("Click a token to move it after rolling. First to finish all tokens wins.", 20, HEIGHT - 20);
    }

    private void drawPath(Graphics g) {
        // draw path nodes
        for (int i = 0; i < PATH_LEN; i++) {
            Point p = path[i];
            int r = 18;
            // mark starting squares for players (RED at index 0, BLUE at index PATH_LEN/2)
            if (i == 0) {
                g.setColor(RED_COLOR);
                g.fillOval(p.x - r, p.y - r, r*2, r*2);
                g.setColor(Color.BLACK);
                g.drawString("R", p.x - 6, p.y + 6);
            } else if (i == PATH_LEN/2) {
                g.setColor(BLUE_COLOR);
                g.fillOval(p.x - r, p.y - r, r*2, r*2);
                g.setColor(Color.BLACK);
                g.drawString("B", p.x - 6, p.y + 6);
            } else {
                g.setColor(new Color(230,230,230));
                g.fillOval(p.x - r, p.y - r, r*2, r*2);
            }
            // outline
            g.setColor(Color.GRAY);
            g.drawOval(p.x - r, p.y - r, r*2, r*2);
        }

        // Connect path
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < PATH_LEN; i++) {
            Point a = path[i];
            Point b = path[(i+1) % PATH_LEN];
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }

    private void drawTokens(Graphics g) {
        // For each token, draw at position; if at home, draw little home stacks
        drawPlayerTokens(g, PlayerColor.RED, RED_COLOR, WIDTH/2 - 250, HEIGHT/2 - 250);
        drawPlayerTokens(g, PlayerColor.BLUE, BLUE_COLOR, WIDTH/2 + 200, HEIGHT/2 + 200);
    }

    private void drawPlayerTokens(Graphics g, PlayerColor pcolor, Color color, int hx, int hy) {
        Token[] arr = tokens.get(pcolor);
        // draw home area
        g.setColor(color.darker());
        g.drawRect(hx-10, hy-10, 140, 80);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(pcolor + " HOME", hx+10, hy-20);

        for (int i = 0; i < arr.length; i++) {
            Token t = arr[i];
            int sx = hx + (i%2) * 60;
            int sy = hy + (i/2) * 40;
            if (t.pos == -1) {
                // at home
                g.setColor(color);
                g.fillOval(sx, sy, 30, 30);
                g.setColor(Color.BLACK);
                g.drawString("" + (i+1), sx+10, sy+20);
            } else if (t.finished) {
                // finished area: small tick
                g.setColor(Color.GRAY);
                g.fillOval(sx, sy, 30, 30);
                g.setColor(Color.WHITE);
                g.drawString("✓", sx+10, sy+20);
            } else {
                // on path
                int posIndex = t.pos % PATH_LEN;
                Point p = path[posIndex];
                int size = 28;
                g.setColor(color);
                g.fillOval(p.x - size/2, p.y - size/2, size, size);
                g.setColor(Color.BLACK);
                g.drawString("" + (i+1), p.x - 4, p.y + 4);
            }
        }
    }

    // Mouse click to select and move a token (if rolled and valid)
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!rolled) {
            message = "पहिले Roll Dice दाबा.";
            repaint();
            return;
        }

        // find which token of current player was clicked (either on path or at home)
        Token[] myTokens = tokens.get(currentTurn);
        for (int i = 0; i < myTokens.length; i++) {
            Token t = myTokens[i];

            // check home click
            Point homeClick = getHomeClickPoint(currentTurn, i);
            Rectangle homeRect = new Rectangle(homeClick.x, homeClick.y, 30, 30);
            if (t.pos == -1 && homeRect.contains(e.getPoint())) {
                // attempt to bring out if dice==6
                if (diceValue == 6) {
                    t.pos = getStartIndexFor(currentTurn);
                    message = currentTurn + " token " + (i+1) + " moved out!";
                    // check capture
                    checkCapture(currentTurn, i);
                    endTurnAfterMove(diceValue);
                } else {
                    message = "बाहेर आणण्यासाठी 6 लागेल.";
                }
                repaint();
                return;
            }

            // check path click
            if (t.pos >= 0 && !t.finished) {
                Point p = path[t.pos % PATH_LEN];
                Rectangle r = new Rectangle(p.x - 14, p.y - 14, 28, 28);
                if (r.contains(e.getPoint())) {
                    // try move
                    if (canMoveToken(t, diceValue)) {
                        t.pos += diceValue;
                        // finished if completed full loop: we consider finish when pos >= start + PATH_LEN
                        int startIndex = getStartIndexFor(currentTurn);
                        if (t.pos >= startIndex + PATH_LEN) {
                            t.finished = true;
                            t.pos = -2; // mark as finished
                            message = currentTurn + " token " + (i+1) + " finished!";
                        } else {
                            message = currentTurn + " token " + (i+1) + " moved " + diceValue;
                        }
                        // capture opponent if landed on their token (and not on safe squares start)
                        checkCapture(currentTurn, i);
                        endTurnAfterMove(diceValue);
                    } else {
                        message = "हा token चालवता येत नाही.";
                    }
                    repaint();
                    return;
                }
            }
        }

        // if clicked empty place, ignore
    }

    private Point getHomeClickPoint(PlayerColor p, int index) {
        if (p == PlayerColor.RED) {
            int hx = WIDTH/2 - 250;
            int hy = HEIGHT/2 - 250;
            return new Point(hx + (index%2)*60, hy + (index/2)*40);
        } else {
            int hx = WIDTH/2 + 200;
            int hy = HEIGHT/2 + 200;
            return new Point(hx + (index%2)*60, hy + (index/2)*40);
        }
    }

    // check if token can move given dice
    private boolean canMoveToken(Token t, int dice) {
        if (t.pos == -1) {
            // at home, only move if dice==6
            return dice == 6;
        }
        if (t.finished) return false;
        // simulate new pos
        int startIndex = getStartIndexFor(currentTurn);
        int newPos = t.pos + dice;
        return newPos <= startIndex + PATH_LEN; // allow exact finish
    }

    private int getStartIndexFor(PlayerColor p) {
        return (p == PlayerColor.RED) ? 0 : PATH_LEN/2;
    }

    // capture opponent tokens if landed on them (not on start safe square)
    private void checkCapture(PlayerColor mover, int moverTokenIndex) {
        Token moverToken = tokens.get(mover)[moverTokenIndex];
        if (moverToken.pos < 0) return;
        int landedIndex = moverToken.pos % PATH_LEN;

        // safe squares are start indexes
        int safe1 = getStartIndexFor(PlayerColor.RED);
        int safe2 = getStartIndexFor(PlayerColor.BLUE);

        PlayerColor opponent = (mover == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
        Token[] oppTokens = tokens.get(opponent);
        for (Token t : oppTokens) {
            if (t.pos >= 0 && !t.finished && t.pos % PATH_LEN == landedIndex) {
                // if landed on opponent and not safe square, send opponent home
                if (landedIndex != safe1 && landedIndex != safe2) {
                    t.pos = -1; // send home
                    message += " captured " + opponent + "!";
                }
            }
        }
    }

    private void endTurnAfterMove(int dice) {
        // if dice==6, same player gets another roll (typical Ludo rule)
        if (dice == 6) {
            rolled = false;
            message += " (6! Roll again.)";
        } else {
            // change turn
            currentTurn = (currentTurn == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
            rolled = false;
            message += " Now " + currentTurn + "'s turn.";
        }
        // check win
        if (countFinished(currentTurn == PlayerColor.RED ? PlayerColor.BLUE : PlayerColor.RED) == 4) {
            // previous player won (because we switched already)
            PlayerColor winner = (currentTurn == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
            message = winner + " WON! Press New Game to play again.";
            rolled = false;
            // disable roll
        }
    }

    private int countAtHome(PlayerColor p) {
        int cnt = 0;
        for (Token t : tokens.get(p)) if (t.pos == -1) cnt++;
        return cnt;
    }

    private int countFinished(PlayerColor p) {
        int cnt = 0;
        for (Token t : tokens.get(p)) if (t.finished) cnt++;
        return cnt;
    }

    // Action listener for Roll Dice button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton) {
            // if someone already won, ignore
            if (message.contains("WON")) {
                return;
            }
            diceValue = rand.nextInt(6) + 1;
            rolled = true;
            message = currentTurn + " rolled a " + diceValue + ". Click token to move.";
            // if no possible move, auto-end turn (except when dice==6 and at least can bring out)
            if (!hasAnyMove(currentTurn, diceValue)) {
                message = currentTurn + " rolled a " + diceValue + " but has no moves.";
                if (diceValue == 6) {
                    // if no moves even with 6 then still stay on turn? We'll allow turn to stay and let player click NewGame or press roll again.
                    rolled = false;
                } else {
                    currentTurn = (currentTurn == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
                    rolled = false;
                    message += " Now " + currentTurn + "'s turn.";
                }
            }
            repaint();
        }
    }

    private boolean hasAnyMove(PlayerColor p, int dice) {
        Token[] arr = tokens.get(p);
        for (Token t : arr) {
            if (canMoveToken(t, dice)) return true;
        }
        return false;
    }

    // Unused mouse events
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Ludo - RED vs BLUE");
            SimpleLudo game = new SimpleLudo();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
