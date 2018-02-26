package miage.m1.pa2017.jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public abstract class IArene extends JPanel{
    protected int Xmax = 15;
    protected int Ymax = 15;
    protected JPanel [][] contentPan;


    public IArene(){
        this.contentPan = new JPanel[this.Xmax][this.Ymax];
        setLayout(new GridLayout(this.Xmax, this.Ymax, 0, 0));
        setVisible(true);
    }

    public abstract void initialise();
    public abstract void setRobots(ArrayList<IRobot> robots);
    public abstract ArrayList<IRobot> getRobots();
    public abstract ArrayList<Image> getAllImages();
    public abstract void refresh();
    public abstract void remove(IRobot robot);


    public abstract int getmaxAbscisse();
    public abstract int getMaxOrdonnee();
}
