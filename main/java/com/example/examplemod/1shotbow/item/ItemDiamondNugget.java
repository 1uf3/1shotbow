package com.example.examplemod.mymod.item;

import com.example.examplemod.ExampleMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDiamondNugget extends Item {
    public ItemDiamondNugget() {
        super();
        setCreativeTab(CreativeTabs.MATERIALS);
        setRegistryName("Diamond_Nugget");
        setUnlocalizedName(ExampleMod.MODID + "_Diamond_Nugget");
    }
}
