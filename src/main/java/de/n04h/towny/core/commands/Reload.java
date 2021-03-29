package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Reload implements Command{

    @Override
    public String callName() {
        return "townyreload";
    }

    @Override
    public String permissionName(){
        return "towny";
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        core.config = YamlConfiguration.loadConfiguration(new File(core.getDataFolder(), "config.yml"));
        sender.sendMessage(core.utilMSG.getComplete("Die Config wurde neugeladen"));
        return true;
    }
}
