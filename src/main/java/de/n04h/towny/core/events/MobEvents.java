package de.n04h.towny.core.events;

import de.n04h.towny.core.Core;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;

public class MobEvents implements Listener {

    Core core;
    public MobEvents(Core core){
        this.core = core;
    }

}
