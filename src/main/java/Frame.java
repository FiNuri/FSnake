import javax.swing.*;
import java.awt.*;

class Frame extends JFrame {

    Dimension dimension;

    Frame(String title){

        super(title);
        this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(615,592);
        this.setLocation((dimension.width  - 615) / 2,(dimension.height  - 592) / 2);
        this.setVisible(true);
        setIconImage(new ImageIcon(this.getClass().getResource("icon.png")).getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new Game());
        this.setResizable(false);

    }

}
