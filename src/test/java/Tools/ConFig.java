package Tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConFig {
    private Properties prop;

    public ConFig(String filename){
        prop = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConfig(String key){
        return prop.getProperty(key);
    }

/*    public static void main(String[] args) {
        ConFig conFig = new ConFig("config.properties");
        System.out.println(conFig.getConfig("browser"));
    }*/
}
