package plugins.graphique;

import miage.m1.pa2017.Util;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author Mamadou THIAW
 */
@Plugin(nom = "Graphique aleatoire", type = Util.Type.GRAPHIQUE)
public class Aleatoire{

    public void action(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method isVisible  = obj.getClass().getDeclaredMethod("isVisible");
        Method getCouleur = obj.getClass().getDeclaredMethod("getCouleur");
        Method setImage   = obj.getClass().getDeclaredMethod("setImage", Image.class);
        Method setCouleur = obj.getClass().getDeclaredMethod("setCouleur", Color.class);

        Method setVisible = obj.getClass().getDeclaredMethod("setVisible", boolean.class);
        if(!(boolean)isVisible.invoke(obj)){
            setVisible.invoke(obj, true);
        }
        if((Color)getCouleur.invoke(obj) == null){
            setImage.invoke(obj, null);
            setCouleur.invoke(obj, couleurAleatoire());
        }
    }


    public Color couleurAleatoire(){
        int R = (int) (Math.random()*256);
        int G = (int) (Math.random()*256);
        int B = (int) (Math.random()*256);
        return new Color(R, G, B);
    }
    public String nom() {
        return "plugin de couleur aleatoire";
    }

}

