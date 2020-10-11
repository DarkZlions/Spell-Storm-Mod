package ch.darklions888.SpellStorm.events;

import java.util.Random;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.registries.EffectInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID, bus = Bus.FORGE)
public class EventHandler {
	
	/*
	 * I cannot figure out how to implement a loottable that checks if the creeper is powered/charged.
	 */
	@SubscribeEvent
	public static void onEntityDeathEvent(LivingDropsEvent event) {
		if (event.getEntityLiving() instanceof CreeperEntity) {
			CreeperEntity creeper = (CreeperEntity) event.getEntityLiving();
			
			Random rand = new Random();
			if (creeper.isCharged() && rand.nextInt(2) == 1) {
				creeper.entityDropItem(new ItemStack(ItemInit.CHARGED_CREEPER_PIECES.get()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityTickEvent(PlayerTickEvent event) {
		if (event.player instanceof PlayerEntity) {
			PlayerEntity player = event.player;
			
			if (player.isPotionActive(EffectInit.DISINTERGRATED.get())) {

			}
		}
	}
}
