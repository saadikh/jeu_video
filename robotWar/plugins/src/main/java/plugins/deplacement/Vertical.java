package plugins.deplacement;

import miage.m1.pa2017.Util.Type;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 *
 * @author Mamadou THIAW
 * @version 0.1.0 02/01/2018
 */


@Plugin(nom = "Deplacement vertical", type = Type.DEPLACEMENT)
public class Vertical {

    public void action(Object obj, int maxAbscisse, int maxOrdonnee) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Random rand = new Random();

        int valeur;
        // On déplace le robot sur l'axe des Ordonnées en veillant à ce qu'il ne dépasse pas du Conteneur
        Method setY = obj.getClass().getDeclaredMethod("setY", int.class);
        do{
            Method getY = obj.getClass().getDeclaredMethod("getY");
            valeur = (int)getY.invoke(obj) + rand.nextInt((1 - (-1)) + 1) + -1;
        }while(valeur < 0 || valeur > maxOrdonnee);
        setY.invoke(obj, valeur);
    }


    public String nom() {
        return "plugin de deplacement vertical est active";
    }


}
