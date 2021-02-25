package me.Dadudze.FishingAPI;

import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

public class FishingHookEntity {

    private static String OBF_REEL_OUT = "av";
    private static String OBF_CATCH_TIME = "aw";
    private static String OBF_SWIM_TIME = "ax";

    private static String OBF_FISH_X = "aA";
    private static String OBF_FISH_Y = "aB";
    private static String OBF_FISH_Z = "aC";

    private EntityFishingHook hook;

    public FishingHookEntity(EntityFishingHook hook) {
        this.hook = hook;
    }

    public int getCatchTime() {
        return (int) get(OBF_CATCH_TIME);
    }

    public void setCatchTime(int ticks) {
        set(OBF_CATCH_TIME, ticks);
    }

    public void setCatchTimeInWater(int ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!hook.isAlive()) cancel();
                if(getCatchTime() != 0) {
                    setCatchTime(ticks);
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(FishingAPI.class), 1, 1);
    }

    public int getSwimTime() {
        return (int) get(OBF_SWIM_TIME);
    }

    public void setSwimTime(int ticks) {
        set(OBF_SWIM_TIME, ticks);
    }

    public void setSwimTimeInWater(int ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!hook.isAlive()) cancel();
                if(getSwimTime() != 0) {
                    setSwimTime(ticks);
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(FishingAPI.class), 1, 1);
    }

    public int getReelOutTime() {
        return (int) get(OBF_REEL_OUT);
    }

    public void setReelOutTime(int ticks) {
        set(OBF_REEL_OUT, ticks);
    }

    public void setReelOutTimeInWater(int ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!hook.isAlive()) cancel();
                if(getReelOutTime() != 0) {
                    setReelOutTime(ticks);
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(FishingAPI.class), 1, 1);
    }

    public Vector getFishLocation() {
        return new Vector((double) get(OBF_FISH_X), (double) get(OBF_FISH_Y), (double) get(OBF_FISH_Z));
    }

    public void setFishLocation(Vector location) {
        set(OBF_FISH_X, location.getX());
        set(OBF_FISH_Y, location.getY());
        set(OBF_FISH_Z, location.getZ());
    }

    private void set(String name, Object value) {
        try {
            Field f = hook.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(hook, value);
        } catch (Exception e) {}
    }

    private Object get(String name) {
        try {
            Field f = hook.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(hook);
        } catch (Exception e) {}
        return null;
    }

}
