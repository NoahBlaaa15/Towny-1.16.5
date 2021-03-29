package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class SCOREBOARD {

    Core core;

    public SCOREBOARD(Core core){
        this.core = core;
    }

    public void setScoreBoard(Player player, boolean first) {
        Scoreboard board;
        Objective obj;
        if(!first){
            board = player.getScoreboard();
            obj = board.getObjective("test");
        }else{
            board = Bukkit.getScoreboardManager().getNewScoreboard();
            obj = board.registerNewObjective("test", "dummy", Component.text("§b« §f§lTowny §7by §f§ln04h.de §b»"));
        }

        obj.getScore("").setScore(15);
        obj.getScore(core.utilConfig.getCoinbonus() + "§l» Coins:").setScore(14);
        obj.getScore(core.utilConfig.getInfo() + "   10").setScore(13);
        obj.getScore(" ").setScore(12);
        obj.getScore(core.utilConfig.getExpbonus() + "§l» Level:").setScore(11);
        obj.getScore(core.utilConfig.getInfo() + "   50").setScore(10);
        obj.getScore("  ").setScore(9);
        obj.getScore(core.utilConfig.getError() + "§o§l» Job:").setScore(8);
        obj.getScore(core.utilConfig.getInfo() + "   Master of Waifus").setScore(7);
        obj.getScore("   ").setScore(6);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }


}
