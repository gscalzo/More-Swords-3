package net.darkhax.moreswords.enchantment;

import net.darkhax.moreswords.util.Utils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEnderPulse extends EnchantmentCore {

	protected EnchantmentEnderPulse(int id, int weight, String unlocalizedName, int minLevel, int maxLevel, Item item) {

		super(id, weight, unlocalizedName, minLevel, maxLevel, item);
		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * Teleports the player where they are looking on right click, provided that
	 * space is within 18 multiplied by enchantment level blocks away. Damages the
	 * held item by 50 and gives the player 1 fall damage. 
	 */
	@SubscribeEvent
	public void onItemUsed(PlayerInteractEvent event) {
		
		if ((event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR))) { 
			
			if (isValidPlayer(event.entityPlayer)) {
				
				ItemStack stack = event.entityPlayer.getHeldItem();
				EntityPlayer player = event.entityPlayer;
				int distance = level(stack) * 18;	
		        MovingObjectPosition position = Utils.rayTrace(player.worldObj, player, distance); 
		        
		        if ((position != null) && (position.typeOfHit == MovingObjectType.BLOCK)) {      
		        	
		        	int x = position.blockX;             
		        	int y = position.blockY;             
		        	int z = position.blockZ;
		     
		        	switch (position.sideHit) {             
		        	case 0:                
		        		y--;              
		        		break;              
		        	case 1:
		                y++;
		                break;             
		        	case 2:
		                z--;
		                break;             
		        	case 3:
		                z++;
		                break;             
		        	case 4:
		                x--;
		                break;             
		        	case 5:
		                x++;
		                break;            
		        	default:
		                y++;          
		        	}
		         
		        	stack.damageItem(50, player);        
		        	player.setPositionAndUpdate(x, y, z);        
		        	player.attackEntityFrom(DamageSource.fall, 1);          
		        }       
			}
		}
	}
}