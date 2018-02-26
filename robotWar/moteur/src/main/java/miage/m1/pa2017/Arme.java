package miage.m1.pa2017;

/**
 *
 * @author Mamadou THIAW
 * @version 0.1.0 01/01/2018
 */

public class Arme {
    private int portee;
    private int degats;

    public Arme(int po, int damage){
        this.portee = po;
        this.degats = damage;
    }

    public int getPortee(){ return this.portee; }

    public int getDegats(){ return this.degats; }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }
}
