package de.n04h.towny.core;

import de.n04h.towny.core.commands.CommandManager;
import de.n04h.towny.core.events.MobEvents;
import de.n04h.towny.core.events.PlayerEvents;
import de.n04h.towny.core.utils.CONFIG;
import de.n04h.towny.core.utils.MSG;
import de.n04h.towny.core.utils.SCHEMATICA;
import de.n04h.towny.core.utils.SCOREBOARD;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin implements Listener {

    public FileConfiguration config;

    CommandManager comMan;

    //Events
    PlayerEvents playerEvents = new PlayerEvents(this);
    MobEvents mobEvents = new MobEvents(this);

    public MSG utilMSG;
    public CONFIG utilConfig;
    public SCOREBOARD utilScore;
    public SCHEMATICA utilSchema;

    @Override
    public void onEnable() {
        //Enable Config
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        //Load from Configuration

        utilMSG = new MSG(this);
        utilConfig = new CONFIG(this);
        utilScore = new SCOREBOARD(this);
        utilSchema = new SCHEMATICA(this);
        comMan = new CommandManager(this);

        Bukkit.getPluginManager().registerEvents(playerEvents, this);
        Bukkit.getPluginManager().registerEvents(mobEvents, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
