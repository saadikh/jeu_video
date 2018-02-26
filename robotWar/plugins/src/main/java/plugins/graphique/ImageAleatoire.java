package plugins.graphique;

import miage.m1.pa2017.Util;
import miage.m1.pa2017.jeu.IArene;
import miage.m1.pa2017.jeu.IRobot;
import miage.m1.pa2017.plugin.Plugin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Fabien on 05/01/2018.
 */
@Plugin(nom = "Graphique imageAleatoire", type = Util.Type.GRAPHIQUE)
public class ImageAleatoire {

    public void action(Object obj, ArrayList<Object> arrayImgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Image> contentAlreadyLoad = new ArrayList<Image>();
        for(Object img : arrayImgs){
            contentAlreadyLoad.add((Image)img);
        }
        File resources = new File("plugins/src/main/resources/");
        Method isVisible  = obj.getClass().getDeclaredMethod("isVisible");
        Method getImage   = obj.getClass().getDeclaredMethod("getImage");
        Method getCouleur = obj.getClass().getDeclaredMethod("getCouleur");
        Method setImage   = obj.getClass().getDeclaredMethod("setImage", Image.class);
        Method setCouleur = obj.getClass().getDeclaredMethod("setCouleur", Color.class);


        Method setVisible = obj.getClass().getDeclaredMethod("setVisible", boolean.class);

        if(!(boolean)isVisible.invoke(obj)){
            setVisible.invoke(obj, true);
        }

        if((Image)getImage.invoke(obj) == null){
            setCouleur.invoke(obj, null);
            Image img = ImageAleatoire();
            if(!contentAlreadyLoad.contains(img)){
                setImage.invoke(obj, ImageAleatoire());
            }
        }
    }

    public Image ImageAleatoire() {
        Image img = null;
        File resources = new File("plugins/src/main/resources/");
        if (resources.isDirectory()) {
            int min = 0;
            int max = resources.list().length - 1;
            int nombreAleatoire = min + (int) (Math.random() * ((max - min) + 1));

            String imgFileName = resources.list()[nombreAleatoire];
            File imageFile = new File(Paths.get(resources.toString(), imgFileName).toAbsolutePath().toString());
            try {
                img = new ImageIcon(imageFile.toURI().toURL()).getImage();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return img;
    }

    public String nom() {
        return "plugin d'image aleatoire";
    }
}
