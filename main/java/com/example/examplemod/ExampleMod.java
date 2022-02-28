package com.example.examplemod;

import com.example.examplemod.1shotbow.EntityOneShotArrow;
import com.example.examplemod.1shotbow.ItemOneShotArrow;
import com.example.examplemod.1shotbow.ItemStartShooting;
import com.example.examplemod.1shotbow.item.ItemDiamondNugget;
import com.example.examplemod.1shotbow.mob.Entityfuwawa;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        boolean isClient = event.getSide().isClient();
        registerOneShotArrow(isClient);
        registerItem(itemDiamondNugget, isClient);
        registerBlock(blockStartShooting, isClient);
        registerfuwawa();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        registerWoodCut();
    }

    private void registerfuwawa() {
        EntityRegistry.registerModEntity(
                Entityfuwawa.class, "fuwawa",
                Entityfuwawa.ENTITY_ID,
                this, 100, 1, true,
                0xFF0000, 0x00FFFF);
    }

    private void registerOneShotArrow(boolean isClient) {
        GameRegistry.register(itemOneShotArrow);
        EntityRegistry.registerModEntity(EntityOneShotArrow.class, "one_shot_arrow", EntityExplosiveArrow.ENTITY_ID,
                this, 10, 10, true);

        if (isClient) {
            ModelResourceLocation modelName = new ModelResourceLocation(itemOneShotArrow.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(itemOneShotArrow, 0, modelName);
        }
    }

    private void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(Items.DIAMOND),
                "AAA",
                        "AAA",
                        "AAA",
                        'A', new ItemStack(itemDiamondNugget)
        );
    }


    private void registerBlock(Block block, boolean isClient) {
        ItemBlock itemBlockInput = new ItemBlock(block);

        GameRegistry.register(block);
        GameRegistry.register(itemBlockInput, block.getRegistryName());

        if (isClient) {
            ModelResourceLocation modelName = new ModelResourceLocation(block.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBlockInput, 0, modelName);
        }
    }

    private void registerItem(Item item, boolean isClient) {
        GameRegistry.register(item);
        if (isClient) {
            ModelResourceLocation modelName = new ModelResourceLocation(item.getRegistryName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, 0, modelName);
        }
    }

    private void registerTipsDeathAbsorb() {
        MinecraftForge.EVENT_BUS.register(new PlayerDeathEventHandler());
    }
}
