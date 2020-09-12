package ch.darklions888.SpellStorm.objects.items;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Foods {

	public static final Food BLUE_CRYSTALS = (new Food.Builder()).hunger(8).saturation(0.9F).effect(() -> new EffectInstance(Effects.INSTANT_DAMAGE, 1, 0), 1F).effect(() -> new EffectInstance(Effects.SPEED, 400, 1), 1F).effect(() -> new EffectInstance(Effects.NAUSEA, 300, 0), 1F).setAlwaysEdible().build();
}
