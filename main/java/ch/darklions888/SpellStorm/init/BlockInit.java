package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.blocks.CrystalOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit 
{
	public static final DeferredRegister<Block> BLOCKSREGISTER = new DeferredRegister<>(ForgeRegistries.BLOCKS, SpellStormMain.MODID);
	
	public static final Block crystal_ore = new CrystalOreBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).hardnessAndResistance(3.0f, 6.0f).harvestLevel(3).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE));
	
	public static final RegistryObject<Block> CRYSTAL_ORE = BLOCKSREGISTER.register("crystal_ore", () -> crystal_ore);
}
