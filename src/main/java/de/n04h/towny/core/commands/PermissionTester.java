package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

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
        if(sender.hasPermission(permission)){
            sender.addAttachment(core).setPermission(permission, false);
            sender.sendMessage(Component.text(core.utilMSG.getComplete("Du hast nun nicht mehr die Permission: " + permission)));
            return true;
        }
        sender.addAttachment(core).setPermission(permission, true);
        sender.sendMessage(Component.text(core.utilMSG.getComplete("Du hast nun die Permission: " + permission)));
        return true;
    }
}
