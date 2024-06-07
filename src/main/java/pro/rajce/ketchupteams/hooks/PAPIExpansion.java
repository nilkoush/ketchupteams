package pro.rajce.ketchupteams.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.rajce.ketchupteams.KetchupTeamsPlugin;
import pro.rajce.ketchupteams.managers.ParticipantManager;
import pro.rajce.ketchupteams.objects.Group;
import pro.rajce.ketchupteams.objects.Participant;

public class PAPIExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "ketchupteams";
    }

    @Override
    public @NotNull String getAuthor() {
        return "pro.rajce";
    }

    @Override
    public @NotNull String getVersion() {
        return KetchupTeamsPlugin.getInstance().getPluginMeta().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }

        Participant participant = ParticipantManager.getInstance().getParticipant(player);
        Group group = participant.getGroup();

        return switch (params) {
            case "group_name" -> group == null ? "????" : group.getName();
            case "group_color" -> group == null ? "????" : group.getColor().asHexString();
            case "participant_kills" -> String.valueOf(participant.getKills());
            case "participant_deaths" -> String.valueOf(participant.getDeaths());
            case "participant_is_spectator" -> String.valueOf(participant.isSpectator());
            case "participant_is_supervisor" -> String.valueOf(participant.isSupervisor());
            default -> "????";
        };
    }
}
