package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    Core core;
    List<Command> commands = new LinkedList<>(); //Can possible be optimised with HashMap<String CallName, Void Command> but not sure if faster

    public CommandManager(Core core){
        this.core = core;

        //commands.add(new );
        commands.add(new Reload());
        commands.add(new TargetEntity());
        commands.add(new PermissionTester());
        commands.add(new Color());
        commands.add(new Town());
        commands.add(new Schematics());
        commands.add(new Worlds());
        commands.add(new Chunkloading());
        commands.add(new Economy());

        for (Command comm: commands
             ) {
            core.getCommand(comm.callName()).setExecutor(this);
        }
        //TODO: Help befehl mit erkennung für befehle
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (Command comm: commands
             ) {
            if(comm.callName().equalsIgnoreCase(label)){
                if(comm.permissionName() == null || sender.hasPermission(comm.permissionName())) {
                    return comm.onCommand(sender, command, label, args, core);
                }
                sender.sendMessage (core.utilMSG.getError("Dafür hast du keine Berechtigung"));
                return true;
            }
        }
        return false;
    }
}
