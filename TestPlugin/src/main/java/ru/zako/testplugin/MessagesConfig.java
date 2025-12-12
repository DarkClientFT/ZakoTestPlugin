package ru.zako.testplugin;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

@Getter
public class MessagesConfig {
    private final JavaPlugin plugin;

    private File file;
    private FileConfiguration config;

    public MessagesConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        reloadFile();
        loadConfig();
        if (config == null) {
            throw new IllegalStateException("Error messages.yml parse");
        }

        parse();
    }

    private String test;

    private void parse() {
        test = config.getString("test");
    }

    private void loadConfig() {
        if (file == null) return;

        config = YamlConfiguration.loadConfiguration(file);

        InputStream stream = plugin.getResource("messages.yml");
        if (stream == null) return;

        Reader reader = new InputStreamReader(stream);
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);

        config.setDefaults(defaultConfig);
    }

    private void reloadFile() {
        if (file == null) {
            this.file = new File(plugin.getDataFolder()+"/messages.yml");
        }
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }
}
