package ch.darklions888.SpellStorm.util.helpers.warp;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class TeleportHelper {
	  /*
	   * Created a own changeDimension function, because the vanilla own is broken
	   */
	   public static Entity changeDimension(ServerPlayerEntity entityIn, ServerWorld server, ITeleporter teleporter) {		   
	      if (!ForgeHooks.onTravelToDimension(entityIn, server.getDimensionKey())) return null;
	      ServerWorld serverworld = entityIn.getServerWorld();
	      RegistryKey<World> registrykey = serverworld.getDimensionKey();
	      if (registrykey == World.THE_END && server.getDimensionKey() == World.OVERWORLD && teleporter.isVanilla()) { //Forge: Fix non-vanilla teleporters triggering end credits
	    	  entityIn.detach();
	    	  entityIn.getServerWorld().removePlayer(entityIn, true); //Forge: The player entity is cloned so keep the data until after cloning calls copyFrom
	         if (!entityIn.queuedEndExit) {
	        	 entityIn.queuedEndExit = true;
	        	 entityIn.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0.0f));
	         }

	         return entityIn;
	      } else {
	    	  
	         IWorldInfo iworldinfo = server.getWorldInfo();
	         entityIn.connection.sendPacket(new SRespawnPacket(server.getDimensionType(), server.getDimensionKey(), BiomeManager.getHashedSeed(server.getSeed()), entityIn.interactionManager.getGameType(), entityIn.interactionManager.func_241815_c_(), server.isDebug(), server.func_241109_A_(), true));
	         entityIn.connection.sendPacket(new SServerDifficultyPacket(iworldinfo.getDifficulty(), iworldinfo.isDifficultyLocked()));
	         PlayerList playerlist = entityIn.server.getPlayerList();
	         playerlist.updatePermissionLevel(entityIn);
	         serverworld.removeEntity(entityIn, true); //Forge: the player entity is moved to the new world, NOT cloned. So keep the data alive with no matching invalidate call.
	         entityIn.revive();
	         if (!teleporter.isVanilla()) {
	            
	        	 Entity e = teleporter.placeEntity(entityIn, serverworld, server, entityIn.rotationYaw, spawnPortal -> {//Forge: Start vanilla logic
	            serverworld.getProfiler().startSection("moving");
	            serverworld.getProfiler().endSection();
	            serverworld.getProfiler().startSection("placing");
	            entityIn.setWorld(server);
	            server.addDuringPortalTeleport(entityIn);
	            setRotation(entityIn, entityIn.rotationYaw, entityIn.rotationPitch);
	            entityIn.moveForced(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ());
	            serverworld.getProfiler().endSection();
	            return entityIn;//forge: this is part of the ITeleporter patch
	            
	        	});//Forge: End vanilla logic
	            
	            if (e != entityIn) throw new IllegalArgumentException(String.format("Teleporter %s returned not the player entity but instead %s, expected PlayerEntity %s", teleporter, e, entityIn));
	            entityIn.interactionManager.setWorld(server);
	            entityIn.connection.sendPacket(new SPlayerAbilitiesPacket(entityIn.abilities));
	            playerlist.sendWorldInfo(entityIn, server);
	            playerlist.sendInventory(entityIn);

	            for(EffectInstance effectinstance : entityIn.getActivePotionEffects()) {
	            	entityIn.connection.sendPacket(new SPlayEntityEffectPacket(entityIn.getEntityId(), effectinstance));
	            }

				entityIn.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
				BasicEventHooks.firePlayerChangedDimensionEvent(entityIn, registrykey, server.getDimensionKey());
			}
	         return entityIn;
	      }
	   }
	   
		public static void setRotation(Entity entityIn, float yaw, float pitch) {
			entityIn.rotationYaw = yaw % 360.0F;
			entityIn.rotationPitch = pitch % 360.0F;
		}

		public static void func_242110_a(ServerWorld serverWorld, BlockPos pos) {
			BlockPos.Mutable blockpos$mutable = pos.toMutable();

			for (int i = -2; i <= 2; ++i) {
				for (int j = -2; j <= 2; ++j) {
					for (int k = -1; k < 3; ++k) {
						BlockState blockstate = k == -1 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
						serverWorld.setBlockState(blockpos$mutable.setPos(pos).move(j, k, i), blockstate);
					}
				}
			}
		}
	}
