package de.n04h.towny.core.jobs;

import java.util.HashMap;

public interface Job {

    public String name();
    public JobAction jobAction();
    public HashMap<Object, Integer> blocksOrEntityTypes();

}
