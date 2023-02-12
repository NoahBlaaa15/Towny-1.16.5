package de.n04h.towny.core.jobs;

import de.n04h.towny.core.Core;
import de.n04h.towny.core.town.TownPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.LinkedList;
import java.util.List;

public class JobManager implements Listener {

    List<Job> jobs = new LinkedList<>();
    Core core;

    public JobManager(Core core){
        this.core = core;

        jobs.add(new HartzIV());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(core, new Runnable() {
            @Override
            public void run() {
                for (TownPlayer townPlayer: core.townplayers
                     ) {
                    if(townPlayer.getJob() != null) {
                        townPlayer.setCoins(
                                townPlayer.getCoins() +
                                        townPlayer.getJob().regularMoney()
                        );
                        core.utilScore.setScoreBoard(townPlayer, false);
                    }
                }
            }
        }, 6000L, 6000L);
    }

    public Job getJobById(int id){
        for (Job job: jobs
             ) {
            if(job.iD() == id){
                return job;
            }
        }
        return null;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

    }


}
