package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.utils.CONFIG;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chunkloading implements Command{

    @Override
    public String callName() {
        return "chunckload";
    }

    @Override
    public String permissionName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        Player p = ((Player) sender);
        CONFIG uC = core.utilConfig;
        if(args.length < 1){
            sender.sendMessage(core.utilMSG.getError("Gebe /chunckload help ein wenn du dir nicht sicher bist"));
            return true;
        }
        switch (args[0]){
            case "list":
                TextComponent.Builder message = Component.text()
                        .append(Component.text(core.utilMSG.getInfo("Folgende Farben kannst du verwenden:")))
                        .append(Component.newline());
                for(Chunk ch: p.getWorld().getForceLoadedChunks()){
                    ch.setForceLoaded(false);
                }
                for (String chunk: uC.getChunks()
                     ) {
                    p.getWorld().loadChunk(Integer.parseInt(chunk.split("#")[0]), Integer.parseInt(chunk.split("#")[1]));
                    p.getWorld().setChunkForceLoaded(Integer.parseInt(chunk.split("#")[0]),Integer.parseInt(chunk.split("#")[1]), true);
                    message.append(Component.text(" - X:" + chunk.split("#")[0] + " | Y:" + chunk.split("#")[1]))
                            .append(Component.newline());
                }

                sender.sendMessage(message.build());
                break;
            case "add":
                break;
            case "remove":
                break;
        }
        return true;
    }

}
