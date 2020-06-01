package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.blocks.CrystalOreBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalLeavesBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalTreeSaplingBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalWoodLogBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalWoodPlanksBlock;
import ch.darklions888.SpellStorm.objects.blocks.ManaInfuserBlock;
import ch.darklions888.SpellStorm.world.gen.biome.trees.MagicalTree;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit 
{
	public static final DeferredRegister<Block> BLOCKSREGISTER = new DeferredRegister<>(ForgeRegistries.BLOCKS, SpellStormMain.MODID);

	public static final RegistryObject<Block> CRYSTAL_ORE = BLOCKSREGISTER.register("crystal_ore", () -> new CrystalOreBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).hardnessAndResistance(3.0f, 6.0f).harvestLevel(3).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));
	public static final RegistryObject<Block> MANAINFUSER = BLOCKSREGISTER.register("manainfuser", () -> new ManaInfuserBlock(Block.Properties.create(Material.IRON, DyeColor.BLACK).hardnessAndResistance(2.5f).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MAGICAL_WOOD_PLANK = BLOCKSREGISTER.register("magical_wood_plank", () -> new MagicalWoodPlanksBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> MAGICAL_WOOD_LOG = BLOCKSREGISTER.register("magical_wood_log", () -> new MagicalWoodLogBlock(MaterialColor.MAGENTA ,Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> MAGICAL_LEAVES = BLOCKSREGISTER.register("magical_leaves", () -> new MagicalLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final RegistryObject<Block> MAGICAL_TREE_SAPLING = BLOCKSREGISTER.register("magical_tree_sapling", () -> new MagicalTreeSaplingBlock(() -> new MagicalTree(), Block.Properties.from(Blocks.OAK_SAPLING)));
	
}
