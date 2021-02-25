# FishingAPI
API for fishing (Bukkit 1.8.9)

Simple API for fishing customization. Drops based on weight (higher weight = higher chance).

Fish action lengths:
```
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.FISHING)) {
            FishingHookEntity hook = FishingAPI.getHook(event.getHook());
            hook.setCatchTimeInWater(20); //Time for fish to start swimming to hook
            hook.setSwimTimeInWater(10); //Swimming time
            hook.setReelOutTimeInWater(2000); //Time to reel out
        }
    }
```

Adding item fishing result:
```
FishingAPI.getFishingResults().add(new FishingResult(new ItemStack(Material.OBSIDIAN) /*item*/, 10 /*weight*/));
```

Adding entity as a fishing result:
```
FishingAPI.getFishingResults().add(new SimpleEntityFishingResult(EntityType.ZOMBIE /*entity type*/, 20 /*weight*/));
```
or if you want ot spawn custom entity
```
FishingAPI.getFishingResults().add(new EntityFishingResult(100 /*weight*/) {
    @Override
    public Entity spawn(Player fisher, Location location) {
        return location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
    }
});
```

if you want to get mob or item someone caught you should use `FishSwapEvent` like normal bukkit event
