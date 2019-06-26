import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String filePath;

    public MyClassLoader(String filePath, ClassLoader parent) {
        super(parent);
        this.filePath = filePath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        File file = new File(filePath + name + ".class");
        if (!file.isFile()) throw new ClassNotFoundException("Class " + name + " not found");

        InputStream in = null;

        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            while (in.available() > 0) {
                in.read(bytes);
            }
            return defineClass(name, bytes, 0, bytes.length);
        }
        catch (FileNotFoundException e) {
            System.err.println("File " + file + " not found");
            return super.findClass(name);
        } catch (IOException e) {
            return super.findClass(name);
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
