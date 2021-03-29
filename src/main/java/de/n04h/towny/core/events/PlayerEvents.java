package de.n04h.towny.core.events;

import de.n04h.towny.core.Core;

import de.n04h.towny.core.utils.CONFIG;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.time.LocalDate;
import java.util.HashMap;


public class PlayerEvents implements Listener {

    private HashMap<Player, Location> wandLoc = new HashMap<>();

    Core core;
    public PlayerEvents(Core core){
        this.core = core;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CONFIG uC = core.utilConfig;

        //TODO: Get Mysql and look for rank
        if(e.getPlayer().getName().equals("NoahBlaaa15")){
            e.getPlayer().addAttachment(core).setPermission("towny", true);
        }

        TextComponent name = Component.text(getPref(e.getPlayer()) + e.getPlayer().getName());
        e.getPlayer().displayName(name);
        e.getPlayer().playerListName(name);
        e.getPlayer().sendPlayerListHeader(Component.text("§3§kMMM §b« §9Towny §1by §9n04h.de §b» §3§kMMM"));
        e.getPlayer().sendPlayerListFooter(Component.text("§3§oBesuche uns auf\nhttps://towny.n04h.de/"));

        TextComponent joinMessage = Component.text()
                .append(Component.text(uC.getPrefix()))
                .append(e.getPlayer().displayName())
                .append(Component.text(uC.getInfo() + " hat §lTowny§r" + uC.getInfo() + " betreten"))
                .clickEvent(ClickEvent.suggestCommand("/tell " + e.getPlayer().getName() + " "))
                .hoverEvent(HoverEvent.showText(Component.text(e.getPlayer().getUniqueId().toString(), NamedTextColor.LIGHT_PURPLE)))
                .build();

        e.joinMessage(joinMessage);

        core.utilScore.setScoreBoard(e.getPlayer(), true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        CONFIG uC = core.utilConfig;

        TextComponent quitMessage = Component.text()
                .append(Component.text(uC.getPrefix()))
                .append(e.getPlayer().displayName())
                .append(Component.text(uC.getInfo() + " hat §lTowny§r" + uC.getInfo() + " verlassen"))
                .clickEvent(ClickEvent.suggestCommand(e.getPlayer().getName() + " "))
                .hoverEvent(HoverEvent.showText(Component.text(e.getPlayer().getUniqueId().toString(), NamedTextColor.LIGHT_PURPLE)))
                .build();

        e.quitMessage(quitMessage);
    }

    @EventHandler
    public void onChat(AsyncChatEvent e){
        e.setCancelled(true);
        TextComponent name = Component.text(getPref(e.getPlayer()) + e.getPlayer().getName())
                .clickEvent(ClickEvent.suggestCommand("/tell " + e.getPlayer().getName() + " "))
                .hoverEvent(HoverEvent.showText(Component.text(e.getPlayer().getUniqueId().toString(), NamedTextColor.LIGHT_PURPLE)));
        e.getPlayer().displayName(name);
        e.getPlayer().playerListName(name);
        TextComponent styleMessage = LegacyComponentSerializer.legacy('&').deserialize(PlainComponentSerializer.plain().serialize(e.message()));
        TextComponent chatMessage = Component.text()
                .append(name)
                .append(Component.text("§7: "))
                .append(styleMessage)
                .build();

        Bukkit.broadcast(chatMessage, Server.BROADCAST_CHANNEL_USERS);
        core.utilScore.setScoreBoard(e.getPlayer(), false);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        //TODO: Need to change permission
        if(e.hasItem() && e.getItem().getType() == Material.STICK && e.getPlayer().hasPermission("towny")){
            if(e.getClickedBlock() != null){
                e.setCancelled(true);
                if(wandLoc.containsKey(e.getPlayer())){
                    Location loc1 = wandLoc.get(e.getPlayer());
                    Location loc2 = e.getClickedBlock().getLocation();
                    e.getPlayer().sendMessage(Component.text(
                        core.utilMSG.getInfo("Klicke mich um den Befehl für die Schematic zu bekommen")
                    ).clickEvent(ClickEvent.suggestCommand("/schematics save " + loc1.getX() + " " + loc1.getY() + " " + loc1.getZ() + " " + loc2.getX() + " " + loc2.getY() + " " + loc2.getZ() + " " )));
                    wandLoc.remove(e.getPlayer());
                }else{
                    e.getPlayer().sendMessage(Component.text(
                            core.utilMSG.getInfo("Markiere nun den zweiten Punkt")
                    ));
                    wandLoc.put(e.getPlayer(), e.getClickedBlock().getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        e.deathMessage(null);
    }


    public String getPref(Player player){
        if(player.hasPermission("towny.admin")){
            return core.utilConfig.getAdmin();
        }
        if(player.hasPermission("towny.dev")){
            return core.utilConfig.getDev();
        }
        if(player.hasPermission("towny.mod")){
            return core.utilConfig.getMod();
        }
        if(player.hasPermission("towny.vip")){
            return core.utilConfig.getVIP();
        }
        if(player.hasPermission("towny.global")){
            return core.utilConfig.getGlobal();
        }

        return core.utilConfig.getPlayer();
    }





}
