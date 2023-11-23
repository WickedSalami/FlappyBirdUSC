import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {

    public void drawGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String message = "Game Over! Click to restart.";
        int messageWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, getWidth() / 2 - messageWidth / 2, getHeight() / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameOver(g);
    }
}
