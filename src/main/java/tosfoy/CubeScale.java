package tosfoy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class CubeScale extends JavaPlugin implements Listener {

    private double ratio;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ratio = getConfig().getDouble("ratio", 2.0);

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("CubeScale enabled: Custom Nether portal ratio " + (int) ratio + ":1 active.");
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (event.getCause() != TeleportCause.NETHER_PORTAL) {
            return;
        }

        Location from = event.getFrom();
        World world = from.getWorld();
        if (world == null) {
            return;
        }

        Location to = getCustomRatioLocation(world, from);
        if (to != null) {
            event.setTo(to);
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        Location from = event.getFrom();
        World world = from.getWorld();
        if (world == null) {
            return;
        }

        // Only handle Nether <-> Overworld travel, skip The End
        Environment env = world.getEnvironment();
        if (env != Environment.NETHER && env != Environment.NORMAL) {
            return;
        }

        // Handles mounted entities (horses, striders, etc.) so they also use the custom ratio
        Location to = getCustomRatioLocation(world, from);
        if (to != null) {
            event.setTo(to);
        }
    }

    private Location getCustomRatioLocation(World fromWorld, Location from) {
        Environment env = fromWorld.getEnvironment();
        double x = from.getX();
        double z = from.getZ();

        if (env == Environment.NETHER) {
            // Nether -> Overworld: multiply coordinates by ratio
            World overworld = getWorldByEnvironment(Environment.NORMAL);
            return overworld != null
                    ? new Location(overworld, x * ratio, from.getY(), z * ratio)
                    : null;
        } else if (env == Environment.NORMAL) {
            // Overworld -> Nether: divide coordinates by ratio
            World nether = getWorldByEnvironment(Environment.NETHER);
            return nether != null
                    ? new Location(nether, x / ratio, from.getY(), z / ratio)
                    : null;
        }

        return null;
    }

    private World getWorldByEnvironment(Environment environment) {
        return Bukkit.getWorlds().stream()
                .filter(w -> w.getEnvironment() == environment)
                .findFirst()
                .orElse(null);
    }
}
