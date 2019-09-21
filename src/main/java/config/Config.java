package config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Config {
    private final static Logger logger = Logger.getLogger("config");
    public String resourcesDir;
    public String config;
    public String databaseServerPath;
    public String databaseClientPath;
    public int sessionLength;
    public String sessionDict;


    public Config() {
        // get databasePath
        Gson gson = new Gson();
//        this.basedir = (String) System.getProperties().get("basedir");
        this.resourcesDir = "src/main/resources";
        this.config = "config.json";
        Path fPath = Paths.get(resourcesDir, config);
        File jsonFile = fPath.toFile();
        try {
            JsonObject configJson = gson.fromJson(new FileReader(jsonFile), JsonObject.class);
            String dbFilename = configJson.get("Database").getAsJsonObject().get("SERVER_FILE_FILENAME").getAsString();
            this.databaseServerPath = Paths.get(this.resourcesDir, dbFilename).toString();
            dbFilename = configJson.get("Database").getAsJsonObject().get("CLIENT_FILE_FILENAME").getAsString();
            this.databaseClientPath = Paths.get(this.resourcesDir, dbFilename).toString();
            this.sessionLength = configJson.get("Session").getAsJsonObject().get("length").getAsInt();
            this.sessionDict = configJson.get("Session").getAsJsonObject().get("dict").getAsString();


        } catch (FileNotFoundException e) {
            logger.warning(String.format("[*] %s not found: %s", fPath.toString(), e.getMessage()));
        }

    }

    public static void main(String[] args) {
        Config config = new Config();
    }


}
