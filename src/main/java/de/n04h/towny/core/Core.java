package de.n04h.towny.core;

import de.n04h.towny.core.commands.CommandManager;
import de.n04h.towny.core.events.MobEvents;
import de.n04h.towny.core.events.PlantEvents;
import de.n04h.towny.core.events.PlayerEvents;
import de.n04h.towny.core.events.SpawnerEvents;
import de.n04h.towny.core.jobs.JobManager;
import de.n04h.towny.core.town.Town;
import de.n04h.towny.core.town.TownPlayer;
import de.n04h.towny.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class Core extends JavaPlugin implements Listener {

    public FileConfiguration config;
    public Jedis protMemory;

    public CommandManager comMan;
    public JobManager jobMan;

    //Events
    public PlayerEvents playerEvents;
    public MobEvents mobEvents;
    public SpawnerEvents spawnerEvents;
    public PlantEvents plantEvents;

    //Town
    public List<TownPlayer> townplayers = new LinkedList<>();
    public List<Town> towns = new LinkedList<>();

    public MSG utilMSG;
    public CONFIG utilConfig;
    public SCOREBOARD utilScore;
    public SCHEMATICA utilSchema;
    public REDIS utilRedis;

    @Override
    public void onEnable() {
        //Enable Config
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        File schemaFolder = new File("schemas");
        schemaFolder.mkdir();
        //Load from Configuration
        protMemory = new Jedis();

        utilMSG = new MSG(this);
        utilConfig = new CONFIG(this);
        utilScore = new SCOREBOARD(this);
        utilSchema = new SCHEMATICA(this);
        utilRedis = new REDIS(this, protMemory);
        comMan = new CommandManager(this);
        jobMan = new JobManager(this);

        playerEvents = new PlayerEvents(this);
        mobEvents = new MobEvents(this);
        spawnerEvents = new SpawnerEvents(this);
        plantEvents = new PlantEvents(this);

        Bukkit.getPluginManager().registerEvents(playerEvents, this);
        Bukkit.getPluginManager().registerEvents(mobEvents, this);
        Bukkit.getPluginManager().registerEvents(spawnerEvents, this);
        Bukkit.getPluginManager().registerEvents(plantEvents, this);
        Bukkit.getPluginManager().registerEvents(jobMan, this);

    }

    public List<Town> getTowns() {
        return towns;
    }

    public TownPlayer getTownPlayer(Player p){
        for (TownPlayer tp: townplayers
             ) {
            if(tp.getPlayer() == p)
                return tp;
        }
        return null;
    }

    public List<TownPlayer> getTownplayers() {
        return townplayers;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
