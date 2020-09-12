package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.blocks.CrystalOreBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalForgeBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalLeavesBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalTreeSaplingBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalWoodLogBlock;
import ch.darklions888.SpellStorm.objects.blocks.MagicalWoodPlanksBlock;
import ch.darklions888.SpellStorm.objects.blocks.ManaInfuserBlock;
import ch.darklions888.SpellStorm.objects.blocks.SoulExtractorBlock;
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

public class BlockInit  {
	public static final DeferredRegister<Block> REGISTER_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lib.MOD_ID);

	public static final RegistryObject<Block> CRYSTAL_ORE = REGISTER_BLOCKS.register("crystal_ore", () -> new CrystalOreBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).setRequiresTool().hardnessAndResistance(3.0f, 6.0f).harvestLevel(3).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));
	public static final RegistryObject<Block> CORRUPTED_CRYSTAL_ORE = REGISTER_BLOCKS.register("corrupted_crystal_ore", () -> new CrystalOreBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).setRequiresTool().hardnessAndResistance(4.0f, 7.0f).harvestLevel(4).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));
	public static final RegistryObject<Block> MANAINFUSER = REGISTER_BLOCKS.register("manainfuser", () -> new ManaInfuserBlock(Block.Properties.create(Material.IRON, DyeColor.BLACK).hardnessAndResistance(2.5f).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> SOUL_EXTRACTOR = REGISTER_BLOCKS.register("soul_extractor", () -> new SoulExtractorBlock(Block.Properties.from(Blocks.ENCHANTING_TABLE)));
	public static final RegistryObject<Block> MAGICAL_WOOD_PLANK = REGISTER_BLOCKS.register("magical_planks", () -> new MagicalWoodPlanksBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> MAGICAL_WOOD_LOG = REGISTER_BLOCKS.register("magical_wood_log", () -> new MagicalWoodLogBlock(MaterialColor.MAGENTA ,Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> MAGICAL_LEAVES = REGISTER_BLOCKS.register("magical_leaves", () -> new MagicalLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final RegistryObject<Block> MAGICAL_TREE_SAPLING = REGISTER_BLOCKS.register("magical_tree_sapling", () -> new MagicalTreeSaplingBlock(() -> new MagicalTree(), Block.Properties.from(Blocks.OAK_SAPLING)));
	public static final RegistryObject<Block> MAGICAL_FORGE = REGISTER_BLOCKS.register("magical_forge", () -> new MagicalForgeBlock(Block.Properties.create(Material.ANVIL, DyeColor.BLACK).setRequiresTool().hardnessAndResistance(3.0f, 6.0f).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));
}
