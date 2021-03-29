package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TargetEntity implements Command{

    @Override
    public String callName() {
        return "targetEntity";
    }

    @Override
    public String permissionName(){
        return "towny";
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
            ent.setCustomNameVisible(true);
            ent.setCustomName("§r");
            p.sendMessage(core.utilMSG.getInfo(ent.getCustomName()));
        }
        return true;
    }
}
