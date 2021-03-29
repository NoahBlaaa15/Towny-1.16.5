package de.n04h.towny.core.commands;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;

public class Color implements Command{
    @Override
    public String callName() {
        return "color";
    }

    @Override
    public String permissionName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args, Core core) {
        TextComponent message = Component.text()
                .append(Component.text(core.utilMSG.getInfo("Folgende Farben kannst du verwenden:")))
                .append(Component.newline())
                .append(Component.text("§0&0§f §1&1§f §2&2§f\n§3&3§f §4&4§f §5&5§f\n§6&6§f §7&7§f §8&8§f\n§9&9§f §a&a§f §b&b§f\n§c&c§f §d&d§f §e&e§f\n§f&f§f §l&l§f §m&m§f\n§n&n§f §o&o§f "))
                .build();
        sender.sendMessage(message);
        return true;
    }
}
