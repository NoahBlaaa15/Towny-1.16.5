package de.n04h.towny.core.events;

import de.n04h.towny.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlantEvents implements Listener {

    Core core;
    public PlantEvents(Core core){
        this.core = core;
    }

    @EventHandler
    public void onBreak(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            switch (e.getClickedBlock().getType()) {
                case WHEAT:
                    replantSeed(e.getClickedBlock(), Material.WHEAT_SEEDS, e.getPlayer(), Material.WHEAT);
                    break;
                case CARROTS:
                    replantSeed(e.getClickedBlock(), Material.CARROT, e.getPlayer(), Material.CARROTS);
                    break;
                case POTATOES:
                    replantSeed(e.getClickedBlock(), Material.POTATO, e.getPlayer(), Material.POTATOES);
                    break;
                case NETHER_WART:
                    replantSeed(e.getClickedBlock(), Material.NETHER_WART, e.getPlayer(), Material.NETHER_WART);
                    break;
            }
        }
    }

    public void replantSeed(Block bl, Material seed, Player p, Material plant){
        Ageable ag = (Ageable) bl.getBlockData();
        if(ag.getAge() == ag.getMaximumAge() && hasSeedRemove(p, seed)) {
            bl.breakNaturally();
            bl.getLocation().getBlock().setType(plant);
        }
    }

    public boolean hasSeedRemove(Player p, Material seed){
        for (ItemStack it:  p.getInventory().getContents()
             ) {
            if(it != null && it.getType() == seed){
                it.setAmount(it.getAmount() - 1);
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onDespawn(ItemDespawnEvent e){
        //Material.OAK_SAPLING
        if(e.getEntity().getItemStack().getType().toString().contains("SAPLING")){
            if(e.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIRT || e.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GRASS_BLOCK){
                e.getLocation().getBlock().setType(e.getEntity().getItemStack().getType());
            }
        }
    }

}
