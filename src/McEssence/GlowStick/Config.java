package McEssence.GlowStick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config{
    private final FileConfiguration config;
    private Boolean enabled;
    private String glowstickItemString;
    private Material glowstickMaterial;
    private int throwDistanceMultiplier;
    private int glowstickDurationSeconds;
    public Config(FileConfiguration configtemp){
        config = configtemp;
    }

    private void setEnabled(){
        enabled = config.getBoolean("general.enabled");
    }
    public Boolean getEnabled(){
        return enabled;
    }

    private void setGlowstickItemString(){
        glowstickItemString = config.getString("general.glowstickItem");
    }

    public Material getGlowstickMaterial(){
        return glowstickMaterial;
    }
    private void setGlowstickMaterial(){
        try {
            glowstickMaterial = Material.getMaterial(glowstickItemString);
        }catch(Exception e){
            Bukkit.getLogger().info(ChatColor.RED + " glowstickItem not valid");
        }
    }

    public int getThrowDistanceMultiplier(){
        return throwDistanceMultiplier;
    }
    private void setThrowDistanceMultiplier(){
        throwDistanceMultiplier = config.getInt("general.throwDistanceMultiplier");
    }

    public int getGlowstickDurationSeconds(){
        return glowstickDurationSeconds;
    }
    private void setGlowstickDurationSeconds(){
        glowstickDurationSeconds = config.getInt("general.glowstickDurationSeconds");
    }

    public void initVariables() {
        setEnabled();
        setGlowstickItemString();
        setGlowstickMaterial();
        setThrowDistanceMultiplier();
        setGlowstickDurationSeconds();
    }
}
