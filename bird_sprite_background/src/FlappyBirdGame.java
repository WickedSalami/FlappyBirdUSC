import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlappyBirdGame extends JPanel implements ActionListener {
    private Bird bird;
    private List<Obstacle> obstacles;
    private Timer timer;
    private boolean gameStarted;
    private boolean gameOver;
    private GameOverScreen gameOverScreen;
    private Image backgroundImage;

    public FlappyBirdGame() {
        bird = new Bird(100, 200);
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(400, 100));
        gameStarted = false;
        gameOver = false;
        gameOverScreen = new GameOverScreen();

        timer = new Timer(20, this);
        timer.start();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (gameOver) {
                    restartGame();
                } else if (!gameStarted) {
                    gameStarted = true;
                } else {
                    bird.flap();
                }
            }
        });

        setFocusable(true);

        try {
            InputStream imageStream = getClass().getResourceAsStream("/background.jpg");
            if (imageStream != null) {
                backgroundImage = ImageIO.read(imageStream);
            } else {
                System.err.println("Background image not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        if (!gameStarted && !gameOver) {
            // Draw start screen
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String message = "Click to Start";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, getWidth() / 2 - messageWidth / 2, getHeight() / 2);
        } else if (gameOver) {
            // Draw game over screen
            gameOverScreen.paintComponent(g);
        } else {
            // Draw background
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    
            // Draw bird
            g.drawImage(bird.getBirdImage(), bird.getX(), bird.getY(), 20, 20, null);
    
            // Draw obstacles
            g.setColor(Color.GREEN);
            for (Obstacle obstacle : obstacles) {
                int obstacleHeight = obstacle.getHeight();
                int gapHeight = 150;
    
                // Draw the upper part of the obstacle
                g.fillRect(obstacle.getX(), 0, 30, obstacleHeight);
    
                // Draw the lower part of the obstacle
                g.fillRect(obstacle.getX(), obstacleHeight + gapHeight, 30, getHeight() - obstacleHeight - gapHeight);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            bird.update();

            // Move obstacles
            for (Obstacle obstacle : obstacles) {
                obstacle.move();
            }

            // Check for collisions
            for (Obstacle obstacle : obstacles) {
                if (bird.getX() + 20 > obstacle.getX() && bird.getX() < obstacle.getX() + 30) {
                    if (bird.getY() < obstacle.getHeight() || bird.getY() + 20 > obstacle.getHeight() + 150) {
                        gameOver = true;
                    }
                }
            }

            // Add a new obstacle when the first one is almost off the screen
            if (obstacles.get(0).getX() < -30) {
                obstacles.remove(0);
                obstacles.add(new Obstacle(400, (int) (Math.random() * 200) + 50));
            }
        }

        repaint();
    }

    private void restartGame() {
        bird = new Bird(100, 200);
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(400, 100));
        gameStarted = false;
        gameOver = false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBirdGame game = new FlappyBirdGame();
        frame.add(game);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}