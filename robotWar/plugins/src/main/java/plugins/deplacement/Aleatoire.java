package plugins.deplacement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import miage.m1.pa2017.Util.Type;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

/**
 *
 * @author Flavien
 * @version 0.1.0 29/12/17
 * @Updated by Mamarou THIAW 01/01/2018
 * Updated by Mamadou Thiaw 02/01/2018
 */


@Plugin(nom = "Deplacement aleatoire", type = Type.DEPLACEMENT)
public class Aleatoire {

    public void action(Object obj, int maxAbscisse, int maxOrdonnee) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Random rand = new Random();

        int valeur;
        // 1 chance sur 2 qu'il se déplace sur l'axe des abscisses
        if (rand.nextBoolean()) {
            Method getX = obj.getClass().getDeclaredMethod("getX");
            Method setX = obj.getClass().getDeclaredMethod("setX", int.class);
            // Si on bouge sur l'axe des abscisse on récupère la valeur X du Robot à laquelle on ajoute un nombre aléatoire entre -1 et 1 pour le déplacer
            // sur l'axe des abscisse (On vérifie dans la condition du while que la nouvelle valeur ne sort pas de l'aire de jeu)
            do{
                valeur = (int)getX.invoke(obj) + rand.nextInt((1 - (-1)) + 1) + -1;
            }while(valeur < 0 || valeur > maxAbscisse);
            setX.invoke(obj, valeur);
        } else {
            // Si on bouge sur l'axe des ordonnées on récupère la valeur Y du Robot à laquelle on ajoute un nombre aléatoire entre -1 et 1 pour le déplacer
            // sur l'axe des ordonnées (On vérifie dans la condition du while que la nouvelle valeur ne sort pas de l'aire de jeu)
            // ON charge les méthodes de l'objet Robot
            Method getY = obj.getClass().getDeclaredMethod("getY");
            Method setY = obj.getClass().getDeclaredMethod("setY", int.class);
            do{
                valeur = (int)getY.invoke(obj) + rand.nextInt((1 - (-1)) + 1) + -1;
            }while(valeur < 0 || valeur > maxOrdonnee);
            setY.invoke(obj, valeur);
        }
        System.out.println("robot " + obj.getClass().getDeclaredMethod("getId").invoke(obj)+ " se déplace !");
    }


    public String nom() {
        return "plugin de deplacement aleatoire";
    }


}
