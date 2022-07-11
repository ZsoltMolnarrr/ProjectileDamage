package net.projectiledamage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.projectiledamage.ProjectileDamage;
import org.slf4j.Logger;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    static final Logger LOGGER = LogUtils.getLogger();
    public static Config currentConfig = Default.config;

    public static void initialize() {
        var config = Default.config;
        var configFileName = ProjectileDamage.MODID + ".json";
        Path configDir = FabricLoader.getInstance().getConfigDir();

        try {
            var gson = new Gson();
            var filePath = configDir.resolve(configFileName);
            if (Files.exists(filePath)) {
                // Read
                Reader reader = Files.newBufferedReader(filePath);
                config = gson.fromJson(reader, Config.class);
                reader.close();
                LOGGER.info("ProjectileDamage config loaded: " + gson.toJson(config));
            } else {
                // Write
                var prettyGson = new GsonBuilder().setPrettyPrinting().create();
                Writer writer = Files.newBufferedWriter(filePath);
                writer.write(prettyGson.toJson(config));
                writer.close();
                LOGGER.info("ProjectileDamage default config written: " + gson.toJson(config));
            }
        } catch(Exception e) {
            LOGGER.error("Failed loading ProjectileDamage config: " + e.getMessage());
        }

        currentConfig = config;
    }
}
