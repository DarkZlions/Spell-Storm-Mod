package ch.darklions888.SpellStorm.world.gen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class OreConfig implements IFeatureConfig {
    public static final Codec<OreConfig> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(BlockState.CODEC.fieldOf("target").forGetter((config) -> config.state),
                    BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state),
                    Codec.FLOAT.fieldOf("chance").orElse(0f).forGetter((config) -> config.chance))
            .apply(instance, OreConfig::new));
    
    public final BlockState target;
    public final BlockState state;
    public final float chance;
    
    public OreConfig(BlockState target, BlockState state, float chance) {
    	this.target = target;
    	this.chance = chance;
    	this.state = state;
    }
}
