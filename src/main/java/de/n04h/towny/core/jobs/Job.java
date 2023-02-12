package de.n04h.towny.core.jobs;

import java.util.HashMap;

public interface Job {

    public String name();
    public Integer iD();
    public Integer regularMoney();
    public JobAction jobAction();
    public HashMap<Object, Integer> blocksOrEntityRewards();

}
