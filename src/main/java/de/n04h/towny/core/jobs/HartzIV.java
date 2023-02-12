package de.n04h.towny.core.jobs;

import java.util.HashMap;

public class HartzIV implements Job{
    @Override
    public String name() {
        return "Hartz IV";
    }

    @Override
    public Integer iD() {
        return 0;
    }

    @Override
    public Integer regularMoney() {
        return 10;
    }

    @Override
    public JobAction jobAction() {
        return JobAction.nothing;
    }

    @Override
    public HashMap<Object, Integer> blocksOrEntityRewards() {
        return null;
    }
}
