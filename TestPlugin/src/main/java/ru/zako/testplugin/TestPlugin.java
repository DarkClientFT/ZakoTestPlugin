package ru.zako.testplugin;

import org.bukkit.plugin.java.JavaPlugin;
import ru.zako.testplugin.command.GetDbUser;
import ru.zako.testplugin.command.GiveItemCommand;
import ru.zako.testplugin.command.SetUserAge;
import ru.zako.testplugin.storage.MysqlStorage;
import ru.zako.testplugin.storage.Storage;

import java.io.File;

public final class TestPlugin extends JavaPlugin {
    private File testSchem;

    private Storage storage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.load(getConfig());

        getServer().getPluginManager().registerEvents(new BukkitListener(), this);

        storage = new MysqlStorage();

        getCommand("giveitem").setExecutor(new GiveItemCommand(this));
        getCommand("getdbuser").setExecutor(new GetDbUser(storage, this));
        getCommand("setuserage").setExecutor(new SetUserAge(storage, this));

    }

    @Override
    public void onDisable() {
        if (storage != null) {
            storage.unload();
        }
    }

    private File getTestSchem() {
        testSchem = new File(getDataFolder(),"test.schem");
        if (!testSchem.exists()) {
            saveResource("test.schem", false);
        }
        return testSchem;
    }
}
