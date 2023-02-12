package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionTester implements Command{
    @Override
    public String callName() {
        return "testpermission";
    }

    @Override
    public String permissionName() {
        return "towny";
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        if(args.length < 1){
            sender.sendMessage(Component.text(core.utilMSG.getError("Du musst eine Permission angeben")));
            return true;
        }
        String permission = args[0];
        Player p = null;
        if(args.length > 1){
            p = Bukkit.getPlayer(args[1]);
        }else{
            p = (Player) sender;
        }

        if(p.hasPermission(permission)){
            p.addAttachment(core).setPermission(permission, false);
            sender.sendMessage(Component.text(core.utilMSG.getComplete(p.getName() + " hat nun nicht mehr die Permission: " + permission)));
            return true;
        }
        p.addAttachment(core).setPermission(permission, true);
        sender.sendMessage(Component.text(core.utilMSG.getComplete(p.getName() + " hat nun die Permission: " + permission)));
        return true;
    }
}
