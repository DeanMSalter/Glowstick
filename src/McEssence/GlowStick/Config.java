package McEssence.GlowStick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config{
    private final Main main;
    public Config(Main mainTemp){
        main = mainTemp;
    }

    public Boolean getEnabled(){
        return main.getConfig().getBoolean("general.enabled");
    }

    public String getGlowstickItemString(){
        return main.getConfig().getString("general.glowstickItem");
    }

    public Material getGlowstickMaterial(){
        return Material.getMaterial(getGlowstickItemString());
    }

    public int getThrowDistanceMultiplier(){
        return main.getConfig().getInt("general.throwDistanceMultiplier");
    }

    public int getGlowstickDurationSeconds(){
        return main.getConfig().getInt("general.glowstickDurationSeconds");
    }
}
