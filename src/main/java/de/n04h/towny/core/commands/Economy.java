package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.town.TownPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Economy implements Command{
    @Override
    public String callName() {
        return "eco";
    }

    @Override
    public String permissionName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(core.utilMSG.getError("Du bist kein Spieler!"));
            return true;
        }

        Player player = (Player) sender;
        TownPlayer townPlayer = core.getTownPlayer(player);

        if(townPlayer == null) {
            sender.sendMessage(core.utilMSG.getError("Du bist kein Spieler!"));
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(core.utilMSG.getError("Gebe /eco help ein wenn du dir nicht sicher bist"));
            return true;
        }


        switch(args[0]){
            default:
            case "bal":
                sender.sendMessage(core.utilMSG.getInfo("Coins: " + townPlayer.getCoins()));
                break;
            case "pay":
                if(args.length < 2) {
                    sender.sendMessage(core.utilMSG.getError("Gebe /eco help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                Player receiver = Bukkit.getPlayer(args[1]);
                TownPlayer receiverTownPlayer = core.getTownPlayer(receiver);
                if(receiver == null || receiverTownPlayer == null) {
                    sender.sendMessage(core.utilMSG.getError("Der ausgewählte Spieler ist aktuell nicht online"));
                    return true;
                }

                int amount = 10;
                if(args.length >= 3) {
                    amount = Integer.parseInt(args[2]);
                }

                townPlayer.setCoins(townPlayer.getCoins() - amount);
                receiverTownPlayer.setCoins(receiverTownPlayer.getCoins() + amount);

                core.utilScore.setScoreBoard(townPlayer, false);
                core.utilScore.setScoreBoard(receiverTownPlayer, false);

                sender.sendMessage(core.utilMSG.getComplete("Du hast " + amount + " Coins an " + receiver.displayName() + " gezahlt"));
                return true;
        }

        return true;
    }
}
