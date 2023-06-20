package com.runicrealms.runicwarps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class responsible for IO and caching warps
 */
public final class WarpManager {
    private final Map<String, Location> warps;

    public WarpManager() {
        this.warps = new LinkedHashMap<>(); //preserve order
        this.read();
    }

    /**
     * A method that returns the location of the given warp or null if that warp does not exist
     *
     * @param warp the specified warp
     * @return the location of the given warp or null if that warp does not exist
     */
    @Nullable
    public Location getWarp(@NotNull String warp) {
        return this.warps.get(warp);
    }

    /**
     * A method that returns the names of all the registered warps
     *
     * @return names of all the registered warps
     */
    @NotNull
    public Set<String> getWarps() {
        return this.warps.keySet();
    }

    /**
     * A method that adds a new warp
     *
     * @param warp     the name of the warp
     * @param location the location of that warp
     * @return a boolean that is true if the warp was added successfully
     */
    public boolean addWarp(@NotNull String warp, @NotNull Location location) {
        if (this.warps.containsKey(warp)) {
            return false;
        }

        this.warps.put(warp, location);
        this.write();
        return true;
    }

    /**
     * A method that removes the given warp
     *
     * @param warp the name of the given warp to remove
     * @return a boolean that is true if the warp was removed successfully
     */
    public boolean removeWarp(@NotNull String warp) {
        boolean success = this.warps.remove(warp) != null;

        if (success) {
            this.write();
        }

        return success;
    }

    /**
     * A method that updates the cache to match the disk
     */
    private void read() {
        YamlConfiguration config;
        try {
            config = YamlConfiguration.loadConfiguration(this.getConfig());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.warps.clear();

        ConfigurationSection warps = config.getConfigurationSection("warps");

        if (warps == null) {
            return;
        }

        for (String name : warps.getKeys(false)) {
            ConfigurationSection warp = warps.getConfigurationSection(name);

            if (warp == null) {
                continue;
            }

            String worldName = warp.getString("world");
            //wrapper types because primitives cannot be null
            Double x = warp.isDouble("x") ? warp.getDouble("x") : null;
            Double y = warp.isDouble("y") ? warp.getDouble("y") : null;
            Double z = warp.isDouble("z") ? warp.getDouble("z") : null;
            Double yaw = warp.isDouble("yaw") ? warp.getDouble("yaw") : null;
            Double pitch = warp.isDouble("pitch") ? warp.getDouble("pitch") : null;

            if (worldName == null || x == null || y == null || z == null || yaw == null || pitch == null) {
                continue;
            }

            this.warps.put(name, new Location(Bukkit.getWorld(worldName), x, y, z, yaw.floatValue(), pitch.floatValue()));
        }
    }

    /**
     * A method that updates disk to match the cache
     */
    private void write() {
        YamlConfiguration config = new YamlConfiguration();

        ConfigurationSection warps = config.createSection("warps");

        for (String name : this.warps.keySet()) {
            ConfigurationSection warp = warps.createSection(name);
            Location location = this.warps.get(name);

            warp.set("world", location.getWorld().getName());
            warp.set("x", location.getX());
            warp.set("y", location.getY());
            warp.set("z", location.getZ());
            warp.set("yaw", location.getYaw());
            warp.set("pitch", location.getPitch());
        }

        try {
            config.save(this.getConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that gets and builds the main config
     *
     * @return the main config
     * @throws IOException if there was a problem creating the main config file
     */
    @NotNull
    private File getConfig() throws IOException {
        File home = RunicWarps.getInstance().getDataFolder();

        if (!home.exists()) {
            home.mkdir();
        }

        File file = new File(RunicWarps.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}
