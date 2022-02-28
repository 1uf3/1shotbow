package com.example.examplemod.mymod;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mc_11_explosive_arrow.EntityExplosiveArrow;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemOneShotArrow extends ItemArrow {
    public ItemOneShotArrow() {
        super();
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName("OneShotArrow");
        setUnlocalizedName(ExampleMod.MODID + "_one_shot_arrow");
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntityOneShotArrow(worldIn, shooter);
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.EntityPlayer player) {
        return this.getClass() == ItemOneShotArrow.class;
    }
}
