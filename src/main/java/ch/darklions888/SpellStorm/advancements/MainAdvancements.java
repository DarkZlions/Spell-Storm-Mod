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
						new TranslationTextComponent("advancement.spellstorm:hmm_crystals.title"),
						new TranslationTextComponent("advancement.spellstorm:hmm_crystals.description"),
						new ResourceLocation("textures/blocks/magical_wood_log.png"),
						FrameType.TASK,
						false,
						false,
						false)
				.withCriterion("has_magical_item",
						InventoryChangeTrigger.Instance.forItems(
								ItemInit.CRYSTAL.get(),
								ItemInit.CRYSTAL_PIECES.get(),
								ItemInit.MAGICAL_WOOD_LOG.get()))
				.register(consumer, "main/root");
	}
}
