package de.n04h.towny.core.mobs;

import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.EntityVillager;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;


public class Berater extends EntityVillager {

    public Berater(Location loc, BlockFace bFace, String Stadtname) {
        super(EntityTypes.aV, ((CraftWorld) loc.getWorld()).getHandle());

        this.setPosition(loc.getX()+ 0.5D, loc.getY(), loc.getZ()+ 0.5D);
        this.setCustomName(new ChatComponentText("§dNPC: §7Berater von §b" + Stadtname));
        this.setCustomNameVisible(true);
        this.setSilent(true);
        this.setAbsorptionHearts(1200000000);
        switch(bFace){
            case EAST:
                this.setHeadRotation(90);
                break;
            case SOUTH:
                this.setHeadRotation(180);
                break;
            case WEST:
                this.setHeadRotation(270);
                break;
        }
        this.setCanPickupLoot(false);
        this.setNoAI(true);
        this.setNoGravity(true);
    }

}
