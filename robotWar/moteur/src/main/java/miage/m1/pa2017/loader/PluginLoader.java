package miage.m1.pa2017.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

import miage.m1.pa2017.plugin.Plugin;

/**
 *
 * @author Mamadou THIAW
 * @version 0.1.0 01/01/2018
 * update 02/01/2018
 */
public class PluginLoader {

    private ArrayList attaquePluginsList;
    private ArrayList deplacementPluginsList;
    private ArrayList graphiquePluginsList;

    private String fileDir;
    public static ArrayList<Object> pNames;

    public PluginLoader(String fileDir) {
        this.fileDir = fileDir;
        attaquePluginsList = new ArrayList<>();
        deplacementPluginsList = new ArrayList<>();
        graphiquePluginsList = new ArrayList<>();
    }

    public void loadAllPlugins() throws Exception {
        if (this.fileDir == null) {
            System.out.println("Pas de fichier spécifié");
        }
    }

    public ArrayList<Object> initializePluginLoader() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Object> plugins = new ArrayList<>();
        CustomClassLoader ccl = new CustomClassLoader();
        ccl.setPath(new File(fileDir));
        System.out.println(ccl.getPath().toString());

        String className;
        Class pluginInstance;
        JarFile jar = new JarFile(ccl.getPath());
        System.out.println(jar.getName());
        Enumeration enumeration;
        enumeration = jar.entries();
        pNames = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            className = enumeration.nextElement().toString();
            if (className.endsWith(".class")) {
                pluginInstance = ccl.loadClass(className.substring(0, className.length() - 6).replace("/", "."));
                Constructor co = pluginInstance.getConstructor();
                Object p = co.newInstance();
                String pluginName = p.getClass().getName();

                if(!pNames.contains(pluginName)){
                    pNames.add(pluginName);
                    plugins.add(p);
                }else{
                    System.out.println("deja chargé!");
                }
            }
        }
        System.out.println(plugins.size());
        return plugins;
    }

    public boolean addPlugin(Plugin toAdd, ArrayList list) {
        for (Object plugin : list) {
            if (((Plugin) plugin).getClass().getName() == toAdd.getClass().getName()) {
                System.out.println("deja chargé: " + toAdd.nom());
                return false;
            }
        }
        list.add(toAdd);
        return true;
    }

}
