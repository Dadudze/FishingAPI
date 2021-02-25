package me.Dadudze.FishingAPI;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Random;

public class FishSwapEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Entity newEntity;
    private Entity oldEntity;
    private Player player;
    private Random random;

    public FishSwapEvent(Entity newEntity, Entity oldEntity, Player player, Random random) {
        this.newEntity = newEntity;
        this.oldEntity = oldEntity;
        this.player = player;
        this.random = random;
    }

    public Entity getNewEntity() {
        return newEntity;
    }

    public Entity getOldEntity() {
        return oldEntity;
    }

    public Player getPlayer() {
        return player;
    }

    public Random getRandom() {
        return random;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
