package McEssence.GlowStick;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Listeners implements Listener {
    private final Config config;

    public Listeners(Config configTemp){
        config = configTemp;
    }
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent event){
        if (event.getItemDrop().getItemStack().getType() == config.getGlowstickMaterial()) {
            event.getItemDrop().setVelocity(event.getItemDrop().getVelocity().multiply(config.getThrowDistanceMultiplier()));
        }
    }
}
