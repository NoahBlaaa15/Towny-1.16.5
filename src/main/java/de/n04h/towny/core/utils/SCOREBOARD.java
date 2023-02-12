package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.town.TownPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

public class SCOREBOARD {

    Core core;

    public SCOREBOARD(Core core){
        this.core = core;
    }

    public void setScoreBoard(TownPlayer player, boolean first) {
        Scoreboard board;
        Objective obj;
        if(!first){
            board = player.getPlayer().getScoreboard();
            obj = board.getObjective("test");
            resetValueSlots(player);
        }else{
            board = Bukkit.getScoreboardManager().getNewScoreboard();
            obj = board.registerNewObjective("test", "dummy", Component.text("§b« §f§lTowny §b»"));
        }

        obj.getScore("").setScore(15);
        obj.getScore(core.utilConfig.getCoinbonus() + "§l» Coins:").setScore(14);
        obj.getScore(core.utilConfig.getInfo() + "   " + player.getCoins()).setScore(13);
        obj.getScore(" ").setScore(12);
        obj.getScore(core.utilConfig.getExpbonus() + "§l» Level:").setScore(11);
        obj.getScore(core.utilConfig.getInfo() + "   " + player.getLevel()).setScore(10);
        obj.getScore("  ").setScore(9);
        obj.getScore(core.utilConfig.getError() + "§o§l» Job:").setScore(8);
        obj.getScore(core.utilConfig.getInfo() + "   " + player.getJob().name()).setScore(7);
        obj.getScore("   ").setScore(6);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.getPlayer().setScoreboard(board);
    }

    public void resetValueSlots(TownPlayer player){
        Scoreboard board = player.getPlayer().getScoreboard();
        Objective obj = board.getObjective("test");
        for(String entry : board.getEntries()) {
            if(Arrays.asList(7, 10, 13).contains(obj.getScore(entry).getScore())) {
                board.resetScores(entry);
            }
        }
    }

}
