package miage.m1.pa2017.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Cette classe permet un chargement personnalisé des classes, soit via un repertoire ou bien le nom
 * du fichier uniquement
 * **/
public class CustomClassLoader extends SecureClassLoader {

    private File path;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassData(name);
        if (b != null) {
            return super.defineClass(name, b, 0, b.length);
        }
        throw new ClassNotFoundException(name);
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException {
        byte[] classBytes = null;
        if (path.isDirectory()) {
            return loadFromDirectory(name, path);
        } else {
            return loadFromCompressedFile(name, classBytes);
        }
    }

    private byte[] loadFromDirectory(String name, File file) {
        byte[] classBytes = null;
        String convert = name.replace('.', File.separatorChar).concat(".class");
        File classFile = new File(file, convert);
        if (classFile.exists()) {
            try (FileInputStream is = new FileInputStream(classFile)) {
                classBytes = new byte[is.available()];
                int data = is.read(classBytes);
                while (data != -1) {
                    is.read(classBytes, 0, is.available());
                    data = is.read();
                }
            } catch (IOException e) {
                System.out.println("probleme de lecture du fichier");
                return null;
            }
        }
        return classBytes;
    }

    private byte[] loadFromCompressedFile(String name, byte[] classBytes) {
        try {
            ZipFile zipFile = new ZipFile(path);
            ZipEntry entry = zipFile.getEntry(name.replace('.', '/').concat(".class"));

            if (entry != null) {
                try (InputStream is = zipFile.getInputStream(entry)) {
                    classBytes = new byte[is.available()];
                    int data = is.read(classBytes);
                    while (data != -1) {
                        is.read(classBytes, 0, is.available());
                        data = is.read();
                    }
                }
            }
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classBytes;
    }

    // GETTERs / SETTERS
    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

}
