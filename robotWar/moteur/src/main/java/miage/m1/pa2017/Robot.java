package miage.m1.pa2017;

import miage.m1.pa2017.jeu.IRobot;
import java.awt.*;

public class Robot extends IRobot {

    public final static int MAX_ENERGIE = 250;
    public final static int MAX_VIE     = 100;
    private int id;
    private Color couleur;
    private Image image;
    private boolean actif;
    private int energie;
    private int vie;
    private Arme equipement;
    // coordonnées
    private int x;
    private int y;



    public Robot(int id, int x, int y) {
        setLayout(new BorderLayout());
        this.id = id;
        this.couleur = randomColor();
        this.image = null;
        this.actif = false;
        this.energie = MAX_ENERGIE;
        this.vie     = MAX_VIE;
        this.equipement = new Arme(2, 10);
        this.x = x;
        this.y = y;
    }

    public Robot(int id, int x, int y, Arme arme){
        this(id, x, y);
        this.equipement = arme;

    }

    void tirer(Robot cible) {
        int dommages = this.equipement.getDegats();
        cible.setVie(cible.getVie() - this.equipement.getDegats());
        System.out.println("le robot tire " + this.getEnergie());
        this.energie = this.energie -10;
        System.out.println("le robot tire " + this.getEnergie());
        if(cible.getVie() <= 0){
            cible.setVisible(false);
        }
    }

    public int getId() {
        return id;
    }

    public boolean isAlive(){
        if(this.getVie() > 0){
            return true;
        } else{
            this.setVie(0);
            return false;
        }
    }
    public boolean isVisible() {
        return this.actif;
    }

    public void setVisible(boolean actif) {
        this.actif = actif;
    }

    public int getVie(){ return this.vie; }
    public void setVie(int vie) {
        this.vie = vie;
    }

    public Image getImage() {
        return image;
    }

    public Color getCouleur(){ return this.couleur; }

    @Override
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }


    @Override
    public void repaintRobot() {
        repaint();
    }

    // dessiner le robots
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(this.isVisible()){
            if (this.image != null) {
                g.drawImage(this.image, 2, 2, 50, 50, null);
            } else if(this.couleur != null){
                g.setColor(this.couleur);
                g.fillOval(2, 2, 50, 50);
            }
        }
    }

    public int getX() { return this.x;}
    public int getY() { return this.y;}



    public String toString(){
        return "Le robot est a la position X: "+this.getX()+" et Y: "+this.getY();
    }

    public Arme getEquipement() { return this.equipement;}

    public void setX(int newX) { this.x = newX;}

    public void setY(int newY) { this.y = newY;}

    public int getDegats(){ return this.equipement.getDegats();}
    public int getPortee(){ return this.equipement.getPortee();}
    public int getEnergie() { return energie; }

    public void setEnergie(int energie) { this.energie = energie; }

    public Color randomColor() {
        int R = (int) (Math.random() *256);
        int G = (int) (Math.random() *256);
        int B = (int) (Math.random() *256);
        return new Color(R, G, B);
    }

}
