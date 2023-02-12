package de.n04h.towny.core.events;

import de.n04h.towny.core.Core;

import de.n04h.towny.core.town.TownPlayer;
import de.n04h.towny.core.utils.CONFIG;
import de.n04h.towny.core.utils.REDIS;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;


public class PlayerEvents implements Listener {

    public HashMap<Player, Location[]> wandLoc = new HashMap<>();

    Core core;
    public PlayerEvents(Core core){
        this.core = core;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CONFIG uC = core.utilConfig;
        REDIS pM = core.utilRedis;

        if(e.getPlayer().getName().equals("NoahBlaaa15")){
            e.getPlayer().addAttachment(core).setPermission("towny", true);
        }
        TownPlayer joinedPlayer = pM.loadPlayer(e.getPlayer());
        e.getPlayer().addAttachment(core).setPermission(joinedPlayer.getRank(), true);

        TextComponent name = Component.text(getPref(e.getPlayer()) + e.getPlayer().getName());
        e.getPlayer().displayName(name);
        e.getPlayer().playerListName(name);
        e.getPlayer().sendPlayerListHeader(Component.text("§3§kMMM §b« §9Towny §b» §3§kMMM"));
        e.getPlayer().sendPlayerListFooter(Component.text("§3§oBesuche uns auf\nhttps://towny.n04h.de/"));

        TextComponent joinMessage = Component.text()
                .append(Component.text(uC.getPrefix()))
                .append(e.getPlayer().displayName())
                .append(Component.text(uC.getInfo() + " hat §lTowny§r" + uC.getInfo() + " betreten"))
                .clickEvent(ClickEvent.suggestCommand("/tell " + e.getPlayer().getName() + " "))
                .hoverEvent(HoverEvent.showText(Component.text(e.getPlayer().getUniqueId().toString(), NamedTextColor.LIGHT_PURPLE)))
                .build();

        e.joinMessage(joinMessage);

        //Load Player
        core.townplayers.add(joinedPlayer);

        core.utilScore.setScoreBoard(joinedPlayer, true);
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

        TownPlayer leavingPlayer = core.getTownPlayer(e.getPlayer());
        if(leavingPlayer != null){
            core.utilRedis.updatePlayer(leavingPlayer);
            core.townplayers.remove(core.getTownPlayer(e.getPlayer()));
        }

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
        TextComponent styleMessage = LegacyComponentSerializer.legacy('&').deserialize(PlainTextComponentSerializer.plainText().serialize(e.message()));
        TextComponent chatMessage = Component.text()
                .append(name)
                .append(Component.text("§7: "))
                .append(styleMessage)
                .build();

        Bukkit.broadcast(chatMessage, Server.BROADCAST_CHANNEL_USERS);
        core.utilScore.setScoreBoard(core.getTownPlayer(e.getPlayer()), false);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        //TODO: Need to change permission
        if(e.hasItem() && e.getItem().getType() == Material.STICK && e.getPlayer().hasPermission("towny.dev")){
            if(e.getClickedBlock() != null){
                e.setCancelled(true);
                if(wandLoc.containsKey(e.getPlayer()) && wandLoc.get(e.getPlayer()).length == 1){

                    Location loc1 = wandLoc.get(e.getPlayer())[0];
                    Location loc2 = e.getClickedBlock().getLocation();
                    e.getPlayer().sendMessage(Component.text(
                            core.utilMSG.getInfo("Alle Punkte markiert")
                    ));
                    /*e.getPlayer().sendMessage(Component.text(
                        core.utilMSG.getInfo("Klicke mich um den Befehl für die Schematic zu bekommen")
                    ).clickEvent(ClickEvent.suggestCommand("/schematics save " + loc1.getX() + " " + loc1.getY() + " " + loc1.getZ() + " " + loc2.getX() + " " + loc2.getY() + " " + loc2.getZ() + " " )));*/
                    wandLoc.remove(e.getPlayer());
                    wandLoc.put(e.getPlayer(), new Location[]{loc1, loc2});
                }else{
                    if(wandLoc.containsKey(e.getPlayer()) && wandLoc.get(e.getPlayer()).length > 1){
                        e.getPlayer().sendMessage(Component.text(
                                core.utilMSG.getComplete("Neue Markierung begonnen")
                        ));
                        wandLoc.remove(e.getPlayer());
                    }
                    e.getPlayer().sendMessage(Component.text(
                            core.utilMSG.getInfo("Markiere nun den zweiten Punkt")
                    ));
                    wandLoc.put(e.getPlayer(), new Location[]{e.getClickedBlock().getLocation()});
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        CONFIG uC = core.utilConfig;

        TextComponent deathMessage = Component.text()
                .append(Component.text(uC.getPrefix()))
                .append(e.getEntity().displayName())
                .append(Component.text(uC.getInfo() + " ist bei :" + core.utilMSG.getLoc(e.getEntity().getLocation()) + " gestorben"))
                .clickEvent(ClickEvent.suggestCommand(e.getEntity().getName() + " "))
                .hoverEvent(HoverEvent.showText(Component.text(e.getEntity().getUniqueId().toString(), NamedTextColor.LIGHT_PURPLE)))
                .build();

        e.deathMessage(deathMessage);
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
