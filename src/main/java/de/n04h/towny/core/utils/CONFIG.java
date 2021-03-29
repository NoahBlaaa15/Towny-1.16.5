package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;

public class CONFIG {

    Core core;

    public CONFIG(Core core){
        this.core = core;
    }

    public String getError(){
        return core.config.getString("error");
    }

    public String getComplete(){
        return core.config.getString("complete");
    }

    public String getInfo(){
        return core.config.getString("info");
    }

    public String getHearts(){
        return core.config.getString("hearts");
    }

    public String getDamage(){
        return core.config.getString("damage");
    }

    public String getDefence(){
        return core.config.getString("defence");
    }

    public String getCoinbonus(){
        return core.config.getString("coinbonus");
    }

    public String getExpbonus(){
        return core.config.getString("expbonus");
    }

    public String getPrefix(){
        return core.config.getString("prefix");
    }

    public String getAdmin(){
        return core.config.getString("admin");
    }

    public String getDev(){
        return core.config.getString("dev");
    }

    public String getMod(){
        return core.config.getString("mod");
    }

    public String getVIP(){
        return core.config.getString("vip");
    }

    public String getGlobal(){
        return core.config.getString("global");
    }

    public String getPlayer(){
        return core.config.getString("player");
    }



}
