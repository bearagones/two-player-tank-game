package tankgame.gamescreens;

import tankgame.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BackgroundPanel extends JPanel {

    private BufferedImage mainBackground;
    private Launcher lf;

    public BackgroundPanel(Launcher lf) {
        this.lf = lf;
        try {
            mainBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("Background.bmp")));
        } catch (IOException e) {
            System.out.println("Error can't read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.mainBackground,0,0,null);
    }
}
