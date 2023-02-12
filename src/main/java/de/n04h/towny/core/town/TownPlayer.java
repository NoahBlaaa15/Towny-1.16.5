package de.n04h.towny.core.town;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.jobs.Job;
import org.bukkit.entity.Player;

public class TownPlayer {

    Player p;
    String rank;
    int coins;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Player getPlayer(){
        return p;
    }

    int level;
    Job job;
    Town town;

    Core core;

    public TownPlayer(Core core, Player p, String rank, int coins, int level, int job, int town){
        this.core = core;
        this.p = p;
        this.rank = rank;
        this.coins = coins;
        this.level = level;
        this.job = core.jobMan.getJobById(job);
        this.town = core.utilRedis.loadTown(String.valueOf(town));
    }



}
