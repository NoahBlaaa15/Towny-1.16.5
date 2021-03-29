package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Worlds implements Command{


    @Override
    public String callName() {
        return "worlds";
    }

    @Override
    public String permissionName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        if(args.length < 1 || !sender.hasPermission("towny.worlds")){
            sender.sendMessage(core.utilMSG.getInfo(Bukkit.getWorlds().toString()));
            return true;
        }

        World w = core.getServer().createWorld(new WorldCreator(args[0]));
        ((Player) sender).teleport(w.getSpawnLocation());
        sender.sendMessage(core.utilMSG.getInfo("Wilkommen auf " + args[0]));
        return true;
    }
}
