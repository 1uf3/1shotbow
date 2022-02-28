package com.example.examplemod.mymod;


import com.example.examplemod.ExampleMod;
import com.example.examplemod.mymod.mob.Entityfuwawa;
import jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;


public class ItemStartShooting extends Block{
    // Game is starting or not 
    private boolean isGame = false;
    // 2000 tickcount is 100 secounds 
    private int tickcount = 2000;
    // 20 tickcount is 1 secound 
    private final int PER_SECOND = 20;
    // 
    private final int LIMIT_TIME = 60;
    private BlockPos startPlayerPos = new BlockPos(0, 0, 0);
    private BlockPos entitySpawnPos = new BlockPos(0, 0, 0);


    public ItemStartShooting (){
        super(Material.ROCK);
        setCreativeTab(CreativeTabs.MISC);
        setRegistryName("start_shooting");
        setUnlocalizedName(ExampleMod.MODID + "_start_shooting");
    }

    // Right Click to this block
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            this.BuildStructure(player.getPosition(), world);
        }
        if(isGame){
            super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
        }
        System.out.println("ゲーム開始");
        isGame = true;
        startPlayerPos = player.getPosition();
        world.scheduleBlockUpdate(pos.toImmutable(), this, 0, 100);
        System.out.println("テレポートしたよ");
        this.TeleportPlayer(player, 101);
        // score and time reset
        tickcount = 0;
        player.setScore(0);
        entitySpawnPos = player.getPosition();
        // 落下ダメージ無効化
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,10,5));
        Minecraft.getMinecraft().thePlayer.setPosition(startPlayerPos.getX(),
                startPlayerPos.getY()+102, startPlayerPos.getZ());
        // アイテムを渡す。
        player.inventory.addItemStackToInventory(new ItemStack(Items.BOW, 1));
        player.inventory.addItemStackToInventory(new ItemStack(ExampleMod.itemOneShotArrow, 1));
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
    }

    // Build Structure 
    public boolean BuildStructure(BlockPos pos, World world){
        WorldServer worldServer = (WorldServer) world;
        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation[] location;
        location = new ResourceLocation[4];
        location[0] = new ResourceLocation(ExampleMod.MODID, "Build1");
        location[1] = new ResourceLocation( ExampleMod.MODID, "Build2");
        location[2] = new ResourceLocation(ExampleMod.MODID,  "Build3");
        location[3] = new ResourceLocation(ExampleMod.MODID, "Build4");
        Template template1 = manager.get(minecraftServer, location[0]);
        Template template2 = manager.get(minecraftServer, location[1]);
        Template template3 = manager.get(minecraftServer, location[2]);
        Template template4 = manager.get(minecraftServer, location[3]);

        /*
         *for(int i=0; i < 4; i++){
         *   System.out.println(location[i]);
         *}
         */

        if(template1 != null && template2 != null && template3 != null && template4 != null){
            int positionY = 100;
            IBlockState iBlockState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, iBlockState, iBlockState, 3);
            PlacementSettings placementSettings = (new PlacementSettings()).setMirror(Mirror.NONE)
                    .setRotation(Rotation.NONE).setIgnoreEntities(true).setChunk((ChunkPos) null)
                    .setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

            template1.addBlocksToWorld(world, pos.add(-25,positionY,-25), placementSettings);
            template2.addBlocksToWorld(world, pos.add(-25,positionY,1), placementSettings);
            template3.addBlocksToWorld(world, pos.add(1,positionY,-25), placementSettings);
            template4.addBlocksToWorld(world, pos.add(1,positionY,1), placementSettings);
            return true;
        }
        System.out.println("ミスってるよ");
        return false;
    }

    public void TeleportPlayer (EntityPlayer player, int pos){
        player.setPosition(player.posX,player.posY+pos,player.posZ);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        tickcount++;
        // time counting
        int time = tickcount / PER_SECOND;
        // Count down 
        if (time <= LIMIT_TIME) {
            if(tickcount == 60 * PER_SECOND || tickcount == 30 * PER_SECOND || tickcount == 40 * PER_SECOND ||
                    tickcount == 50 * PER_SECOND || tickcount ==  55 * PER_SECOND){
                Minecraft.getMinecraft().thePlayer.sendChatMessage("後" + (60 - time) + "秒!");
            }
            // After 60 seconds Finish Game and Print score
            if(time == 60) {
               Minecraft.getMinecraft().thePlayer.sendChatMessage("FINISH!");
               player.setPosition(startPlayerPos.getX(), startPlayerPos.getY(), startPlayerPos.getZ());
               Minecraft.getMinecraft().thePlayer.sendChatMessage("************************************");
               Minecraft.getMinecraft().thePlayer.sendChatMessage("         あなたのスコア： "+ String.valueOf(player.getScore()));
               Minecraft.getMinecraft().thePlayer.sendChatMessage("************************************");
               player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 10, 5);
                isGame = false;
                return;
            }
            // Terminal Printing "tickcount"
            System.out.println(tickcount);
            world.scheduleBlockUpdate(pos.toImmutable(), this, 0, 100);
            //  spawn 2 mobs per secound
            if(tickcount % 20 == 0) {
                Random random = new Random();
                Entity entity1 = new Entityfuwawa(world);
                Entity entity2 = new Entityfuwawa(world);
                entity1.setPosition(entitySpawnPos.getX() + random.nextInt(30) - 15,
                        entitySpawnPos.getY() + 20 + random.nextInt(10),
                        entitySpawnPos.getZ() + random.nextInt(30) - 15);
                world.spawnEntityInWorld(entity1);
                entity2.setPosition(entitySpawnPos.getX() + random.nextInt(30) - 15,
                        entitySpawnPos.getY() + 20 + random.nextInt(10),
                        entitySpawnPos.getZ() + random.nextInt(30) - 15);
                world.spawnEntityInWorld(entity2);
            }
        }
    }
}

