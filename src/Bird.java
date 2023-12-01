public class Bird {
    private int x, y, velocity;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    public void flap() {
        velocity = -10;
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
}
