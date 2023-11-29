import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Bird {
    private int x;
    private int y;
    private int velocity;
    private Image birdImage1;
    private Image birdImage2;
    private boolean isBird1;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.isBird1 = true;

        loadImage("/bird1.png", "/bird2.png");
    }

    private void loadImage(String bird1Path, String bird2Path) {
        URL bird1Url = getClass().getResource(bird1Path);
        URL bird2Url = getClass().getResource(bird2Path);

        if (bird1Url != null && bird2Url != null) {
            ImageIcon bird1Icon = new ImageIcon(bird1Url);
            ImageIcon bird2Icon = new ImageIcon(bird2Url);

            birdImage1 = bird1Icon.getImage();
            birdImage2 = bird2Icon.getImage();
        } else {
            System.err.println("Bird images not found!");
        }
    }

    public void flap() {
        // Toggle between bird1 and bird2 quickly
        isBird1 = !isBird1;

        // Adjust the velocity when the bird flaps
        velocity = -10;

        // Schedule a task to switch back to bird1 after a short delay
        Timer timer = new Timer(200, e -> isBird1 = true);
        timer.setRepeats(false);
        timer.start();
    }

    public void update() {
        y += velocity;
        velocity += 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getBirdImage() {
        return isBird1 ? birdImage1 : birdImage2;
    }
}
