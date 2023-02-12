package de.n04h.towny.core.events;

import de.n04h.towny.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerEvents implements Listener {

    Core core;
    public SpawnerEvents(Core core){
        this.core = core;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SPAWNER && e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)){
            CreatureSpawner cs = (CreatureSpawner) e.getBlock().getState();

            ItemStack spawner = new ItemStack(Material.SPAWNER);
            ItemMeta spawnerMeta = spawner.getItemMeta();
            spawnerMeta.displayName(Component.text(core.utilConfig.getDamage() + "Spawner: " + cs.getSpawnedType().toString()));
            spawner.setItemMeta(spawnerMeta);

            e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), spawner);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.SPAWNER){
            CreatureSpawner cs = (CreatureSpawner) e.getBlock().getState();
            String type = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().displayName().toString().split(" ")[1].replaceAll("[\",]", "");
            cs.setSpawnedType(EntityType.valueOf(type));
            cs.update();
        }
    }

}
