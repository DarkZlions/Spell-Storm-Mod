package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.registries.ParticlesInit;
import ch.darklions888.SpellStorm.registries.SoundInit;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoulCatcherItem extends Item implements IHasSoul {

	public SoulCatcherItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

		if (target instanceof MobEntity && getEntityType(stack) == null) {
			World world = playerIn.getEntityWorld();

			if (!world.isRemote()) {
				ServerWorld serverWorld = (ServerWorld) world;
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();

				serverWorld.playSound(null, x, y, z, SoundInit.ETERNAL_SCREAMING.get(), SoundCategory.PLAYERS, 0.2f,
						1.0f);

				for (int i = 0; i < 15; i++) {
					serverWorld.spawnParticle(ParticlesInit.SOULS_PARTICLE.get(), target.getPosXRandom(.5d),
							target.getPosYRandom(), target.getPosZRandom(.5d), 2, 0.0d, .5d, 0.0d, 1.0d);
				}

				this.setSoulAndId(playerIn.getHeldItem(hand), target);
//				setEntityId(stack, target.getEntityId());
//				storeEntity(playerIn.getHeldItem(hand), target.getType());
				serverWorld.removeEntity(target);
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		Entity entity = getEntityType(stack) != null ? getEntityType(stack).create(Minecraft.getInstance().world) : null;
		
		if (entity != null && entity instanceof MobEntity) {		
			tooltip.add(new StringTextComponent(" ")); // Create a space between tooltip and display name
			
			MagicSource source = getSourceFromEntity(getEntityType(stack).create(worldIn));			
			String sourceName = source.getSourceName().getString();
			MobEntity mob = (MobEntity) entity;
			
			StringTextComponent text = new StringTextComponent(String.valueOf(Lib.TextComponents.SOULCATCHER_CONTAINS.getString()));
			StringTextComponent text1 = new StringTextComponent(String.valueOf(Lib.TextComponents.SOULCATCHER_MOB_HAS.getString()));
			String text2 = new String(FormattingHelper.GetSourceColor(source) + FormattingHelper.GetFontFormat(source) + sourceName);
			String value = String.valueOf((int) Math.ceil(mob.getHealth()));
			
			tooltip.add(text.append(new StringTextComponent(": " + "\u00A7l" + getEntityType(stack).getName().getString())));
			tooltip.add(text1.appendString(value + " " + text2).appendString(" " + Lib.TextComponents.MANA.getString()));
		} else {
			tooltip.add(new StringTextComponent(" "));
			TranslationTextComponent text = Lib.TextComponents.SOULCATCHER_IS_EMPTY;
			tooltip.add(text);
		}	
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		TranslationTextComponent name = new TranslationTextComponent(this.getTranslationKey(stack));
		EntityType<?> entity = this.getEntityType(stack);
		if (entity != null) {
			return new TranslationTextComponent(name.getString() + "[" + this.getEntityType(stack).getName().getString() + "]");	
		} else {
			return name;
		}
	
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		if (getEntityType(stack) != null) {
			return true;
		} else {
			return false;
		}
	}
}
