package me.Dadudze.FishingAPI.Results;

import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FishingResult extends WeightedRandom.WeightedRandomChoice {

    private ItemStack itemStack;
    private float maxDamage;
    private boolean enchantable;
    private boolean showName;

    public FishingResult(ItemStack itemStack, int weight) {
        super(weight);
        this.itemStack = itemStack;
    }

    public FishingResult setEnchantable(boolean enchantable) {
        this.enchantable = enchantable;
        return this;
    }

    public boolean isEnchantable() {
        return enchantable;
    }

    public FishingResult setShowName(boolean showName) {
        this.showName = showName;
        return this;
    }

    public boolean doShowName() {
        return showName;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public FishingResult setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public int getWeight() {
        return a;
    }

    public FishingResult setWeight(int weight) {
        super.a = weight;
        return this;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public FishingResult setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public ItemStack getItemStack(Random random, Entity item, Player fisher)
    {
        net.minecraft.server.v1_8_R3.ItemStack itemstack = CraftItemStack.asNMSCopy(this.itemStack);

        if (this.maxDamage > 0.0F)
        {
            int i = (int)(this.maxDamage * (float) itemstack.j());
            int j = itemstack.j() - random.nextInt(random.nextInt(i) + 1);

            if (j > i)
            {
                j = i;
            }

            if (j < 1)
            {
                j = 1;
            }

            itemstack.setData(j);
        }

        if (this.enchantable)
        {
            EnchantmentManager.a(random, itemstack, 30);
        }

        return CraftItemStack.asBukkitCopy(itemstack);
    }
}
