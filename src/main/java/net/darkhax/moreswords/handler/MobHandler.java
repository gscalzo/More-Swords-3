package net.darkhax.moreswords.handler;

import net.darkhax.moreswords.item.SwordItems;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobHandler {

    ConfigurationHandler cfg;

    public MobHandler(Boolean status) {

        if (status) {

            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onEnemySpawn(EntityJoinWorldEvent event) {

        if (event.entity instanceof EntityLiving) {

            double rand = Math.random();

            EntityLiving living = (EntityLiving) event.entity;

            if (living.getEntityData() != null && !living.getEntityData().hasKey("spawned")) {

                if (event.entity instanceof EntityZombie && cfg.zombieSwords)
                    setEntityToHoldSwords(living, cfg.zombieChance);

                if (event.entity instanceof EntitySkeleton && cfg.skeletonSwords)
                    setEntityToHoldSwords(living, cfg.skeletonChance);

                if (event.entity instanceof EntityPigZombie && cfg.pigSwords)
                    setEntityToHoldSwords(living, cfg.pigChance);
            }
        }
    }

    public void setEntityToHoldSwords(EntityLiving entity, double odds) {

        if (SwordItems.swordList.size() > 0) {

            ItemStack stack = new ItemStack(SwordItems.getRandomSword());
            if (stack != null && stack.getItem() != SwordItems.swordAdmin && Math.random() < odds)
                entity.setCurrentItemOrArmor(0, stack);
        }

        entity.getEntityData().setBoolean("spawned", true);
    }
}
