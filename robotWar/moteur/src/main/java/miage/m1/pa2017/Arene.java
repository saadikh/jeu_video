package miage.m1.pa2017;

import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Arene extends IArene {
    private static final long serialVersionUID = 1L;
    private ArrayList<IRobot> contentRobots = null;

    public Arene() {
        super();
    }

    public void initialise(){
        int k = 1;
        if(!this.getRobots().isEmpty()){
            boolean robotInserted;
            for(int i = 0; i < super.contentPan.length; i++){
                for(int j = 0; j < super.contentPan[i].length; j++){
                    robotInserted = false;
                    for (IRobot rb : this.contentRobots) {
                        if(rb.getX() == i && rb.getY() == j){
                            if(rb.getVie() < 0) {
                                rb.setVie(0);
                            }
                            System.out.println("robot "+ rb.getId() + " : (x="+rb.getX()+", y="+rb.getY()+ ", vie=" + rb.getVie() + ", energie=" + rb.getEnergie() +")");
                            k++;
                            robotInserted = true;
                            this.contentPan[i][j] = rb;
                            this.contentPan[i][j].setSize(new Dimension(50, 50));
                        }
                    }
                    if(!robotInserted){
                        this.contentPan[i][j] = new JPanel();
                        this.contentPan[i][j].setBackground(Color.BLACK);
                    }
                    add(this.contentPan[i][j]);
                }
            }
        }
    }


    public void setRobots(ArrayList<IRobot> robots) {
        this.contentRobots = robots;
    }

    public ArrayList<IRobot> getRobots() {
        return this.contentRobots;
    }

    public ArrayList<Image> getAllImages(){
        ArrayList<Image> images = new ArrayList<Image>();
        for(IRobot rob : getRobots()){
            if(rob.getImage() != null){
                images.add(rob.getImage());
            }
        }
        return images;
    }


    public synchronized void refresh() {
        removeAll();
        initialise();
        revalidate();
        repaint();
    }

    public void remove(IRobot robot) {
        contentRobots.remove(robot.getId());
        for (IRobot rb : contentRobots) {
            if (!rb.isVisible()) {
                remove(rb);
            }
        }
    }

    public int getmaxAbscisse(){ return super.Xmax - 1;}

    public int getMaxOrdonnee(){ return super.Ymax - 1;}
}