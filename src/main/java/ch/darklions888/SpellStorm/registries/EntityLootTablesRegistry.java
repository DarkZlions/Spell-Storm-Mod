package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID, bus = Bus.FORGE)
public class EntityLootTablesRegistry {

	@SubscribeEvent
	public static void onLootTableLoadEvent(LootTableLoadEvent event) {	
        if (event.getName().equals(new ResourceLocation("minecraft", "entities/evoker"))) {
            event.getTable().addPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.FANGS.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)).acceptCondition(KilledByPlayer.builder()))).build());
        }
        
        if (event.getName().equals(new ResourceLocation("minecraft", "entities/blaze"))) {
            event.getTable().addPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.MAGICAL_FIREBALL.get()).acceptFunction(SetCount.builder(RandomValueRange.of(-4.0f, 1.0f)).acceptCondition(KilledByPlayer.builder()))).build());
        }
	}
}
