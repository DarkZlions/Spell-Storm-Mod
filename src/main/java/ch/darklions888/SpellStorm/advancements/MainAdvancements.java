package ch.darklions888.SpellStorm.advancements;

import java.util.function.Consumer;

import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class MainAdvancements implements Consumer<Consumer<Advancement>>{

	@SuppressWarnings("unused")
	@Override
	public void accept(Consumer<Advancement> consumer) {
		Advancement rootAdvancement = Advancement.Builder.builder()
				.withDisplay(ItemInit.CRYSTAL.get(),
						new TranslationTextComponent("advancement.spellstorm:root.title"),
						new TranslationTextComponent("advancement.spellstorm:root.description"),
						new ResourceLocation("textures/blocks/magical_wood_log.png"),
						FrameType.TASK,
						false,
						false,
						false)
				.withCriterion("has_magical_item",
						InventoryChangeTrigger.Instance.forItems(
								ItemInit.CRYSTAL.get(),
								ItemInit.CRYSTAL_PIECES.get(),
								ItemInit.MAGICAL_WOOD_LOG.get(),
								ItemInit.MAGICAL_TREE_SAPLING.get(),
								ItemInit.CORRUPTED_CRYSTAL.get()))
				.register(consumer, "main/root");
		
//		Advancement craftForge = Advancement.Builder.builder()
//				.withParent(rootAdvancement)
//				.withDisplay(ItemInit.MAGICAL_FORGE_BLOCK.get())

		
		Advancement hmmCrystal = Advancement.Builder.builder()
				.withParent(rootAdvancement)
				.withDisplay(ItemInit.BLUE_CRYSTALS.get(),
						new TranslationTextComponent("advancement.spellstorm:hmm_crystals.title"),
						new TranslationTextComponent("advancement.spellstorm:hmm_crystals.description"),
						null,
						FrameType.GOAL,
						true,
						true,
						false)
				.withCriterion("cooking_crystal", InventoryChangeTrigger.Instance.forItems(ItemInit.BLUE_CRYSTALS.get()))
				.register(consumer, "main/hmm_crystals");
	}
}
