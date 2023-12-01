public class Obstacle {
    private int x, height;

    public Obstacle(int x, int height) {
        this.x = x;
        this.height = height;
    }

    public void move() {
        x -= 5;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }
}
