package me.Dadudze.FishingAPI.Results;

import me.Dadudze.FishingAPI.FishingAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public abstract class EntityFishingResult extends FishingResult {

    public EntityFishingResult( int weight) {
        super(new ItemStack(Material.AIR), weight);
    }

    @Override
    public ItemStack getItemStack(Random random, Entity item, Player fisher) {
        ItemStack is = super.getItemStack(random, item, fisher);
        FishingAPI.swap(item, spawn(fisher, item.getLocation()));
        return is;
    }

    public abstract Entity spawn(Player fisher, Location location);
}
