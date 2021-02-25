package me.Dadudze.FishingAPI;

import net.minecraft.server.v1_8_R3.WeightedRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFish;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class FishingAPI extends JavaPlugin implements Listener {

    private static ArrayList<FishingResult> results = new ArrayList<>();
    private static Entity swap = null;
    private static boolean privateDrops = false;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSpawn(PlayerFishEvent event) {
        if(!results.isEmpty() && event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            Random random = new Random();
            FishingResult fishingResult = WeightedRandom.a(random, results);
            ItemStack is = fishingResult.getItemStack(random, event.getCaught(), event.getPlayer());
            ((CraftItem) event.getCaught()).setItemStack(is);

            if(privateDrops) event.getCaught().setMetadata("FishOwner",
                    new FixedMetadataValue(this, event.getPlayer().getName()));

            if(is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && fishingResult.doShowName()) {
                event.getCaught().setCustomName(is.getItemMeta().getDisplayName());
                event.getCaught().setCustomNameVisible(true);
            }
            Bukkit.getPluginManager().callEvent(new FishSwapEvent(swap, event.getCaught(), event.getPlayer(), random));
            swap = null;
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if(privateDrops) {
            if(event.getItem().hasMetadata("FishOwner")) {
                if(!event.getItem().getMetadata("FishOwner").get(0).asString().equals(event.getPlayer().getName())) event.setCancelled(true);
            }
        }
    }

    public static void enablePrivateDrops() {
        privateDrops = true;
    }

    public static void disablePrivateDrops() {
        privateDrops = false;
    }

    public static ArrayList<FishingResult> getFishingResults() {
        return results;
    }

    public static void swap(Entity item, Entity entity) {
        swap = entity;
        new BukkitRunnable() {
            @Override
            public void run() {
                entity.setVelocity(item.getVelocity().multiply(2));
                item.remove();
            }
        }.runTaskLater(JavaPlugin.getPlugin(FishingAPI.class), 1);
    }

    public static FishingHookEntity getHook(Entity entity) {
        if(entity instanceof FishHook) return new FishingHookEntity(((CraftFish) entity).getHandle());
        return null;
    }
}
