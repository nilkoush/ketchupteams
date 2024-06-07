package pro.rajce.ketchupteams.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.rajce.ketchupteams.managers.LocationManager;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(LocationManager.getInstance().getSpawn());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        Group group = participant.getGroup();
        if (group != null) {
            event.setCancelled(!group.isCanBuild());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        Group group = participant.getGroup();
        if (group != null) {
            event.setCancelled(!group.isCanBuild());
        }
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        participant.setDeaths(participant.getDeaths() + 1);
        if (player.getKiller() != null) {
            Participant killer = ParticipantManager.getInstance().getParticipant(player.getKiller());
            killer.setKills(killer.getKills() + 1);
        }
    }
}
