package me.Dadudze.FishingAPI;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SimpleEntityFishingResult extends EntityFishingResult {

    private EntityType type;

    public SimpleEntityFishingResult(EntityType type, int weight) {
        super(weight);
        this.type = type;
    }

    @Override
    public Entity spawn(Player fisher, Location location) {
        return location.getWorld().spawnEntity(location, type);
    }
}
