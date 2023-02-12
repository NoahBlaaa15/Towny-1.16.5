package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Town implements Command{

    @Override
    public String callName() {
        return "town";
    }

    @Override
    public String permissionName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        if(args.length < 1){
            sender.sendMessage(core.utilMSG.getError("Gebe /town help ein wenn du dir nicht sicher bist"));
            return true;
        }
        switch (args[0]){
            case "create":
                if(args.length < 2){
                    sender.sendMessage(core.utilMSG.getError("Benutze /town create <Stadtname>"));
                    return true;
                }
                //core.utilSchema.stringToBlocks("STONE:0:0:0#OAK_LOG:0:1:0", (Player) sender, false);

                break;
            default:
                sender.sendMessage(core.utilMSG.getInfo(core.getTownPlayer(((Player) sender)).getTown().getName()));
        }


        return true;
    }
}
