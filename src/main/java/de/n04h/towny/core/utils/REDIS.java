package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.town.Town;
import de.n04h.towny.core.town.TownPlayer;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import javax.swing.plaf.ButtonUI;
import java.util.UUID;

public class REDIS {

    public Jedis jedis;
    Core core;

    public REDIS(Core core, Jedis jedis){
        this.jedis = jedis;
        this.core = core;
    }

    public TownPlayer createPlayer(TownPlayer tP, String uuid){
        jedis.hset("user/" + uuid, "coins", "0");
        jedis.hset("user/" + uuid, "level", "0");
        jedis.hset("user/" + uuid, "job", "0");
        jedis.hset("user/" + uuid, "town", "0");
        jedis.hset("user/" + uuid, "rank", "towny.verified");

        return tP;
    }

    public TownPlayer loadPlayer(Player p){
        String uuid = p.getUniqueId().toString();
        if(jedis.exists("user/" + uuid)) {
            return new TownPlayer(core,
                    p,
                    jedis.hget("user/" + uuid, "rank"),
                    Integer.parseInt(jedis.hget("user/" + uuid, "coins")),
                    Integer.parseInt(jedis.hget("user/" + uuid, "level")),
                    Integer.parseInt(jedis.hget("user/" + uuid, "job")),
                    Integer.parseInt(jedis.hget("user/" + uuid, "town")));
        }else{
            return createPlayer(new TownPlayer(core, p, "towny.verified", 0, 0, 0, 0), uuid);
        }
    }

    public TownPlayer updatePlayer(TownPlayer tP){
        String uuid = tP.getPlayer().getUniqueId().toString();

        jedis.hset("user/" + uuid, "coins", String.valueOf(tP.getCoins()));
        jedis.hset("user/" + uuid, "level", String.valueOf(tP.getLevel()));
        jedis.hset("user/" + uuid, "job", String.valueOf(tP.getJob().iD()));
        jedis.hset("user/" + uuid, "town", String.valueOf(tP.getTown().getiD()));
        jedis.hset("user/" + uuid, "rank", tP.getRank());

        jedis.bgsave();
        return tP;
    }


    public Town createTown(Town t){
        jedis.hset("town/" + t.getiD(), "name", t.getName());
        return t;
    }

    public Town loadTown(String uuid) {
        if(jedis.exists("town/" + uuid)) {
            return new Town(Integer.parseInt(uuid), jedis.hget("town/" + uuid, "name"));
        }else{
            return createTown(new Town(Integer.parseInt(uuid), UUID.randomUUID().toString()));
        }
    }

    public Town updateTown(Town t, String uuid){
        jedis.hset("town/" + uuid, "name", t.getName());
        return t;
    }

}
