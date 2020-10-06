package ch.darklions888.SpellStorm.objects.items.weapons;

import java.util.function.Supplier;

import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ItemTierCollection implements IItemTier {
	MANA_INFUSED_TIER(800, 8.5f, 2.5f, 3, 23, () -> {
		return Ingredient.fromItems(ItemInit.MANA_INFUSED_INGOT.get());
		});

	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int harvestLevel;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;
	
	ItemTierCollection(int maxUsesIn, float efficiencyIn, float attackDamageIn, int harvestLevelIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.harvestLevel = harvestLevelIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}
	
	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairMaterial.getValue();
	}

}
