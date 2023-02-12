package de.n04h.towny.core.town;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Town {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    int iD;
    List<Chunk> claimedChunks = new ArrayList<>();

    public Town(int iD, String name){
        this.iD = iD;
        this.name = name;
    }

    public Town(int iD, String name, Chunk... cH){
        this.iD = iD;
        this.name = name;
        for (Chunk c: cH
             ) {
            claimedChunks.add(c);
        }
    }

    public void addChunk(Chunk c){
        claimedChunks.add(c);
    }

    public void removeChunk(Chunk c){
        claimedChunks.remove(c);
    }

    public List<Chunk> getClaimedChunks() {
        return claimedChunks;
    }

    public int getiD() {
        return iD;
    }
}
