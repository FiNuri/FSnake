import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class Game extends JPanel implements ActionListener {

    private int[] snakeX, snakeY;
    private int dotsSize, snakeLength, appleX, appleY, widthSize, heightSize, gameSpeed;
    private Image snakeImage, appleImage;
    private Random random;
    private boolean alive, up, down, left, right;
    private Timer timer;


    public Game() {
        this.dotsSize = 24;
        this.snakeLength = 3;
        this.random = new Random();
        this.alive = true;
        this.widthSize = 576;
        this.heightSize = 576 - (24 * 2);
        this.snakeX = new int[widthSize];
        this.snakeY = new int[heightSize];
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = true;
        this.gameSpeed = 600;
        this.addKeyListener(new KeyListner());
        this.setFocusable(true);
        downloadImage();
        startGame();

    }

    public void downloadImage() {
        snakeImage = new ImageIcon(getClass().getResource("snake.png")).getImage();
        appleImage = new ImageIcon(getClass().getResource("apple.png")).getImage();
    }

    public void startGame() {
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = 72 - i * dotsSize;
            snakeY[i] = 72;
        }
        timer = new Timer(gameSpeed, this);
        timer.start();
        randomApple();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (alive) {
            g.drawImage(appleImage, appleX, appleY, this);
            for (int i = 0; i < snakeLength; i++) {
                g.drawImage(snakeImage, snakeX[i], snakeY[i], this);
            }
        } else {
            String str = "Game Over";
//            Font f = new Font("Arial", 50, Font.BOLD);
            g.setColor(Color.black);
//            g.setFont(f);
            g.drawString(str, widthSize / 2, heightSize / 2);
        }

    }

    void randomApple() {
        this.appleX = random.nextInt(22) * 24;
        this.appleY = random.nextInt(22) * 24;
    }

    void checkApple() {
        if (appleX == snakeX[0] && appleY == snakeY[0]) {
            snakeLength++;
            randomApple();
            if (!(gameSpeed == 100)) {
                gameSpeed -= 100;
                timer.setDelay(gameSpeed);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (alive) {
            move();
            checkApple();
            checkLive();
        }
        repaint();

    }

    void checkLive() {
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                alive = false;
            }
        }
    }

    void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (up) {
            if (snakeY[0] == 0) {
                snakeY[0] = heightSize;
            } else snakeY[0] -= dotsSize;
        } else if (down) {
            if (snakeY[0] == heightSize) {
                snakeY[0] = 0;
            } else snakeY[0] += dotsSize;
        } else if (left) {
            if (snakeX[0] == 0) {
                snakeX[0] = widthSize;
            } else
                snakeX[0] -= dotsSize;
        } else if (right) {
            if (snakeX[0] == widthSize) {
                snakeX[0] = 0;
            } else
                snakeX[0] += dotsSize;
        }

    }

    class KeyListner extends KeyAdapter {

        private int keyKod;

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);

            keyKod = e.getKeyCode();
            if (keyKod == KeyEvent.VK_UP && !down) {
                up = true;
                down = false;
                left = false;
                right = false;
            } else if (keyKod == KeyEvent.VK_DOWN && !up) {
                up = false;
                down = true;
                left = false;
                right = false;
            } else if (keyKod == KeyEvent.VK_RIGHT && !left) {
                up = false;
                down = false;
                left = false;
                right = true;
            } else if (keyKod == KeyEvent.VK_LEFT && !right) {
                up = false;
                down = false;
                left = true;
                right = false;
            }
        }
    }
}
