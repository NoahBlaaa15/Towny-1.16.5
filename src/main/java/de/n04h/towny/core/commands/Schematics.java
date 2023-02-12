package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

public class Schematics implements Command{
    @Override
    public String callName() {
        return "schematics";
    }

    @Override
    public String permissionName() {
        return "towny.dev";
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        Player p = ((Player) sender);
        Location loc1;
        Location loc2;
        if(args.length < 1){
            sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
            return true;
        }
        switch (args[0]) {
            case "save":
                if(!core.playerEvents.wandLoc.containsKey(p) || core.playerEvents.wandLoc.get(p).length != 2 || args.length < 2){
                    sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                loc1 = core.playerEvents.wandLoc.get(p)[0];
                loc2 = core.playerEvents.wandLoc.get(p)[1];
                String blocks = core.utilSchema.blocksToString(((Player) sender), loc1, loc2);
                if(blocks.equals("PlayerError-1")){
                    p.sendMessage(Component.text(
                            core.utilMSG.getError("Du musst genauer in eine Richtung gucken")
                    ));
                    break;
                }

                try {
                    PrintWriter out = new PrintWriter("schemas/" + args[1] + ".txt");
                    out.write(blocks);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                p.sendMessage(Component.text(
                        core.utilMSG.getComplete("Schematic gespeichert, klicke mich um sie zu laden")
                ).clickEvent(ClickEvent.suggestCommand("/schematics load " + args[1])));

                break;
            case "load":
                if(args.length < 2){
                    sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("schemas/" + args[1] + ".txt")));

                    String complete = "";
                    String temp;
                    while ((temp = br.readLine()) != null){
                        complete += temp;
                    }

                    String returnMSG = core.utilSchema.stringToBlocks(complete, (Player) sender);
                    if(returnMSG.equals("PlayerError-1")){
                        p.sendMessage(Component.text(
                                core.utilMSG.getError("Du musst genauer in eine Richtung gucken")
                        ));
                        break;
                    }

                    p.sendMessage(Component.text(
                            core.utilMSG.getComplete("Schematic geladen")
                    ));

                    br.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

            case "help":
                break;
            case "clear":
                if(!core.playerEvents.wandLoc.containsKey(p) || core.playerEvents.wandLoc.get(p).length != 2){
                    sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                loc1 = core.playerEvents.wandLoc.get(p)[0];
                loc2 = core.playerEvents.wandLoc.get(p)[1];
                core.utilSchema.replaceArea(loc1,loc2, Material.AIR);

                p.sendMessage(Component.text(
                        core.utilMSG.getComplete("Bereich geleert")
                ));
                break;
            case "fill":
                if(!core.playerEvents.wandLoc.containsKey(p) || core.playerEvents.wandLoc.get(p).length != 2 || args.length < 2){
                    sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                loc1 = core.playerEvents.wandLoc.get(p)[0];
                loc2 = core.playerEvents.wandLoc.get(p)[1];
                core.utilSchema.replaceArea(loc1,loc2, Material.valueOf(args[1]));

                p.sendMessage(Component.text(
                        core.utilMSG.getComplete("Bereich gefüllt")
                ));
                break;
            case "replace":
                if(!core.playerEvents.wandLoc.containsKey(p) || core.playerEvents.wandLoc.get(p).length != 2 || args.length < 3){
                    sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
                    return true;
                }
                loc1 = core.playerEvents.wandLoc.get(p)[0];
                loc2 = core.playerEvents.wandLoc.get(p)[1];
                core.utilSchema.replaceBlockArea(loc1,loc2, Material.valueOf(args[2]), Material.valueOf(args[1]));

                p.sendMessage(Component.text(
                        core.utilMSG.getComplete("Bereich replaced")
                ));
                break;
        }

        return true;
    }
}
