package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
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
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        Player p = ((Player) sender);
        if(args.length < 1){
            sender.sendMessage(core.utilMSG.getError("Gebe /schematics help ein wenn du dir nicht sicher bist"));
            return true;
        }
        switch (args[0]) {
            case "save":
                Location loc1 = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                Location loc2 = new Location(p.getWorld(), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
                String blocks = core.utilSchema.blocksToString(((Player) sender), loc1, loc2);
                if(blocks.equals("PlayerError-1")){
                    p.sendMessage(Component.text(
                            core.utilMSG.getError("Du musst genauer in eine Richtung gucken")
                    ));
                    break;
                }


                try {
                    PrintWriter out = new PrintWriter("schema.txt");
                    out.write(blocks);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                p.sendMessage(Component.text(
                        core.utilMSG.getComplete("Schematic gespeichert, klicke mich um sie zu laden")
                ).clickEvent(ClickEvent.suggestCommand("/schematics load")));

                break;
            case "load":
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("schema.txt")));

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
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }

        return true;
    }
}
