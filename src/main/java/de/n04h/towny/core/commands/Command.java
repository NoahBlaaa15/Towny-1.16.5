package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.command.CommandSender;

public interface Command {

    public String callName();

    public String permissionName();

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core);

}
