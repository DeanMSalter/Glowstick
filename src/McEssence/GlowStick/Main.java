package McEssence.GlowStick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    HashMap<Block, Integer> glowsticks = new HashMap<Block, Integer>();
    Config config;
    @Override
    public void onEnable() {
        File f = new File(this.getDataFolder() + "/");
        if(!f.exists()) {
            f.mkdir();
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
        config = new Config(getConfig());
        config.initVariables();
        if (!config.getEnabled()){
            Bukkit.getLogger().info(ChatColor.RED + " Disabled" + this.getName() + " As not enabled in config");
            return;
        }
        Bukkit.getLogger().info(ChatColor.GREEN + " Enabled" + this.getName());
        getServer().getPluginManager().registerEvents(new Listeners(config), this);

        Bukkit.getScheduler().runTaskTimer(this,() -> {
            for(World world : Bukkit.getServer().getWorlds()){
                for(Entity entity : world.getEntities()){
                    if (entity.getType() == EntityType.DROPPED_ITEM && entity.isOnGround()) {
                        Item i = (Item) entity;
                        if (i.getItemStack().getType().equals(config.getGlowstickMaterial())) {
                            Location loc = entity.getLocation();
                            Location aboveTorch = entity.getLocation();
                            aboveTorch.setY(aboveTorch.getY() + 1);

                            Bukkit.getLogger().info(loc.getBlock().getType().toString());
                            Bukkit.getLogger().info(aboveTorch.getBlock().getType().toString());

                            if ((loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getType().equals(Material.CAVE_AIR)) && (aboveTorch.getBlock().getType().equals(Material.AIR) || aboveTorch.getBlock().getType().equals(Material.CAVE_AIR))) {
                                loc.getBlock().setType(config.getGlowstickMaterial());
                                glowsticks.put(loc.getBlock(), 0);
                                aboveTorch.getBlock().setType(Material.LIGHT);
                                glowsticks.put(aboveTorch.getBlock(), 0);
                                Bukkit.getLogger().info("Glowstick created at " + loc);

                                entity.remove();
                            }
                        }
                    }
                }
            }

        },20, 5);


        Bukkit.getScheduler().runTaskTimer(this,() -> {
            List<Block> removedGlowSticks = new ArrayList<>();
            for (Map.Entry<Block, Integer> entry : glowsticks.entrySet()) {
                Block block = entry.getKey();
                Integer iterations = entry.getValue();
                if (iterations >= config.getGlowstickDurationSeconds()) {
                    block.setType(Material.AIR);
                    removedGlowSticks.add(block);
                    Bukkit.getLogger().info("Glowstick removed at " + block.getLocation());
                } else {
                    entry.setValue(iterations + 1);
                }
            }

            for (Block block : removedGlowSticks) {
                glowsticks.remove(block);
            }
        },20, 20);

    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Disabled " + this.getName());
        for (Map.Entry<Block, Integer> entry : glowsticks.entrySet()) {
            // put key and value separated by a colon
            entry.getKey().setType(Material.AIR);
        }
    }
}
