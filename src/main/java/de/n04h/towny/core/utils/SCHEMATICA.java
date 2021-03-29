package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SCHEMATICA {

    Core core;

    public SCHEMATICA(Core core){
        this.core = core;
    }

    public String blocksToString(Player p, Location pos1, Location pos2){
        String blocks = "";
        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        int lowestX = Math.min(x1, x2);
        int lowestY = Math.min(y1, y2);
        int lowestZ = Math.min(z1, z2);

        int highestX = lowestX == x1 ? x2 : x1;
        int highestY = lowestY == y1 ? y2 : y1;
        int highestZ = lowestZ == z1 ? z2 : z1;

        /*if((p.getLocation().getBlockX() > lowestX) && (p.getLocation().getBlockX() < highestX)){
            if((p.getLocation().getBlockY() > lowestY) && (p.getLocation().getBlockY() < highestY)){
                if((p.getLocation().getBlockZ() > lowestZ) && (p.getLocation().getBlockZ() < highestZ)){
                    return "PlayerError-1";
                }
            }
        }*/

        //TODO: Name
        blocks += "*NAME*" + "$";
        BlockFace pFace = p.getFacing();
        for(int x = lowestX; x <= highestX; x++) {
            for (int y = lowestY; y <= highestY; y++) {
                for (int z = lowestZ; z <= highestZ; z++) {
                    Location loc = new Location(p.getWorld(), x, y, z);
                    Material blockMaterial = loc.getBlock().getType();
                    String data = loc.getBlock().getBlockData().getAsString();
                    loc = loc.subtract(p.getLocation().getBlock().getLocation());
                    switch(pFace){
                        case NORTH:
                            blocks += blockMaterial + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ() + "%" + data + "#";
                            break;
                        case EAST:
                            blocks += blockMaterial + "%" + loc.getZ() * -1D + "%" + loc.getY() + "%" + loc.getX() * -1D + "%" + data + "#";
                            break;
                        case SOUTH:
                            blocks += blockMaterial + "%" + loc.getX() * -1D + "%" + loc.getY() + "%" + loc.getZ() * -1D + "%" + data + "#";
                            break;
                        case WEST:
                            blocks += blockMaterial + "%" + loc.getZ() + "%" + loc.getY() + "%" + loc.getX() + "%" + data + "#";
                            break;
                        default:
                            return "PlayerError-1";
                    }
                }
            }
        }

        return replaceFacing(pFace, blocks);
    }


    //String: STONE:0:0:0#OAK_LOG:0:1:0
    public String stringToBlocks(String blockList, Player p){
        String name = blockList.split("\\$")[0];
        blockList = blockList.split("\\$")[1];
        Location pLoc = p.getLocation().getBlock().getLocation();
        BlockFace pFace = p.getFacing();
        switch (pFace) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                break;
            default:
                return "PlayerError-1";
        }
        blockList = replaceFacing(pFace, blockList);
        for (String block: blockList.split("#")
             ) {
            Material blockMaterial = Material.valueOf(block.split("%")[0]);
            Location location = null;
            switch (pFace) {
                case NORTH:
                    location = addLocation(pLoc, Double.parseDouble(block.split("%")[1]), Double.parseDouble(block.split("%")[2]), Double.parseDouble(block.split("%")[3]));
                    break;
                case EAST:
                    location = addLocation(pLoc, Double.parseDouble(block.split("%")[3]) * -1D, Double.parseDouble(block.split("%")[2]), Double.parseDouble(block.split("%")[1]) * -1D);
                    break;
                case SOUTH:
                    location = addLocation(pLoc, Double.parseDouble(block.split("%")[1]) * -1D, Double.parseDouble(block.split("%")[2]), Double.parseDouble(block.split("%")[3]) * -1D);
                    break;
                case WEST:
                    location = addLocation(pLoc, Double.parseDouble(block.split("%")[3]), Double.parseDouble(block.split("%")[2]), Double.parseDouble(block.split("%")[1]));
                    break;
            }

            if (location.getBlock().getType() != Material.BEDROCK && location.getBlock().getType() != Material.OBSIDIAN)
                location.getBlock().breakNaturally();
            location.getBlock().setType(blockMaterial);
            location.getBlock().setBlockData(core.getServer().createBlockData(block.split("%")[4]));
        }
        return "Erfolgreich";
    }

    public String replaceFacing(BlockFace bFace, String blocklist){
        switch (bFace) {
            case EAST:
                blocklist = blocklist.replaceAll("north", "ea1st");
                blocklist = blocklist.replaceAll("west", "sou1th");
                blocklist = blocklist.replaceAll("south", "we1st");
                blocklist = blocklist.replaceAll("east", "nor1th");
                blocklist = blocklist.replaceAll("we1st", "west");
                blocklist = blocklist.replaceAll("nor1th", "north");
                blocklist = blocklist.replaceAll("ea1st", "east");
                blocklist = blocklist.replaceAll("sou1th", "south");
                break;
            case SOUTH:
                blocklist = blocklist.replaceAll("north", "sou1th");
                blocklist = blocklist.replaceAll("west", "ea1st");
                blocklist = blocklist.replaceAll("south", "nor1th");
                blocklist = blocklist.replaceAll("east", "we1st");
                blocklist = blocklist.replaceAll("we1st", "west");
                blocklist = blocklist.replaceAll("nor1th", "north");
                blocklist = blocklist.replaceAll("ea1st", "east");
                blocklist = blocklist.replaceAll("sou1th", "south");
                break;
            case WEST:
                blocklist = blocklist.replaceAll("north", "we1st");
                blocklist = blocklist.replaceAll("west", "nor1th");
                blocklist = blocklist.replaceAll("south", "ea1st");
                blocklist = blocklist.replaceAll("east", "sou1th");
                blocklist = blocklist.replaceAll("we1st", "west");
                blocklist = blocklist.replaceAll("nor1th", "north");
                blocklist = blocklist.replaceAll("ea1st", "east");
                blocklist = blocklist.replaceAll("sou1th", "south");
                break;
        }
        return blocklist;
    }

    public Location addLocation(Location loc, double x, double y, double z) {
        Location loc1 = loc.clone();
        loc1.setX(loc1.getX() + x);
        loc1.setZ(loc1.getZ() + z);
        loc1.setY(loc1.getY() + y);
        return loc1;
    }


}
