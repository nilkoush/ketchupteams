package pro.rajce.ketchupteams.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pro.rajce.ketchupteams.managers.EventManager;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(EventManager.getInstance().getLobby());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Participant participant = ParticipantManager.getInstance().getParticipant(player);

        if (participant.isSupervisor()) return;

        Group group = participant.getGroup();
        if (group != null) {
            event.setCancelled(!group.isCanBuild());
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Participant participant = ParticipantManager.getInstance().getParticipant(player);

        if (participant.isSupervisor()) return;

        Group group = participant.getGroup();
        if (group != null) {
            event.setCancelled(!group.isCanBuild());
        } else {
            event.setCancelled(true);
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

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager) {
            if (event.getEntity() instanceof Player damaged) {
                Participant damagerParticipant = ParticipantManager.getInstance().getParticipant(damager);
                Participant damagedParticipant = ParticipantManager.getInstance().getParticipant(damaged);

                if (!damagerParticipant.getGroup().getName().equals(damagedParticipant.getGroup().getName())) {
                    event.setCancelled(!EventManager.getInstance().isPvp());
                }

                if (damagerParticipant.getGroup().getName().equals(damagedParticipant.getGroup().getName())) {
                    event.setCancelled(!damagerParticipant.getGroup().isIntraPvp());
                }
            }
        }
    }
}
