package miage.m1.pa2017.jeu;


import javax.swing.*;
import java.awt.*;


public abstract class IRobot extends JPanel {

    public abstract void setX(int x);
    public abstract void setY(int y);

    public abstract int getVie();
    public abstract void setVie(int life);
    public abstract int getX();
    public abstract int getY();
    public abstract void setEnergie(int nrj);
    public abstract int getEnergie();

    public abstract boolean isVisible();
    public abstract boolean isAlive();
    public abstract void setVisible(boolean actif);

    public abstract void repaintRobot();
    public abstract Color getCouleur();
    public abstract Image getImage();

    public abstract void setCouleur(Color couleur);
    public abstract void setImage(Image image);

    public abstract int getId();
}
