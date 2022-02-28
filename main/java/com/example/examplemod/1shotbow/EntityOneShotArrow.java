package com.example.examplemod.mymod;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mymod.item.ItemDiamondNugget;
import com.example.examplemod.mymod.mob.Entityfuwawa;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import scala.collection.parallel.ParIterableLike;

import java.util.UUID;

public class EntityOneShotArrow extends EntityArrow {


    public EntityOneShotArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }



    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }


    @Override
    protected void  onHit(RayTraceResult raytraceResultIn) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ItemStack itemstack = new ItemStack(ExampleMod.itemDiamondNugget, 1 );
        Entity hitted_entity = raytraceResultIn.entityHit;
        if(!worldObj.isRemote && hitted_entity instanceof Entityfuwawa) {
            player.addScore(1);
            player.inventory.addItemStackToInventory(itemstack);
            player.inventoryContainer.detectAndSendChanges();
            hitted_entity.setDead();
            this.setDead();
            hitted_entity.playSound(SoundEvents.ENTITY_BAT_DEATH, 10, 5);
        }
    }
}
