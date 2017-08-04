package tschipp.extraambiance.handler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.block.BlockLightComparator;
import tschipp.extraambiance.block.BlockLightDimmable;
import tschipp.extraambiance.block.BlockLightDimmableRedstone;
import tschipp.extraambiance.block.BlockLightRedstone;
import tschipp.extraambiance.block.BlockLightSimple;
import tschipp.extraambiance.block.BlockParticleEmitterAdvanced;
import tschipp.extraambiance.block.BlockParticleEmitterSimple;
import tschipp.extraambiance.block.BlockSoundEmitterAdvanced;
import tschipp.extraambiance.block.BlockSoundEmitterSimple;
import tschipp.tschipplib.block.IMetaBlockName;
import tschipp.tschipplib.util.TSBlockRendering;

public class BlockHandler {

	public static Block simpleLight;
	public static Block redstoneLight;
	public static Block redstoneLightInverted;
	public static Block dimmableLight;
	public static Block dimmableLightRedstone;
	public static Block dimmableLightRedstoneInverted;
	public static Block comparatorLight;
	public static Block comparatorLightInverted;

	public static Block particleEmitterSimple;
	public static Block particleEmitterSimpleLight;
	public static Block particleEmitterAdvanced;
	
	public static Block soundEmitterSimple;
	public static Block soundEmitterSimpleLight;
	public static Block soundEmitterAdvanced;

	public static void preInit() 
	{		
		simpleLight = new BlockLightSimple();
		redstoneLight = new BlockLightRedstone(false);
		redstoneLightInverted = new BlockLightRedstone(true);
		dimmableLight = new BlockLightDimmable();
		dimmableLightRedstone = new BlockLightDimmableRedstone(false);
		dimmableLightRedstoneInverted = new BlockLightDimmableRedstone(true);
		comparatorLight = new BlockLightComparator(false);
		comparatorLightInverted = new BlockLightComparator(true);

		particleEmitterSimple = new BlockParticleEmitterSimple(false);
		particleEmitterSimpleLight = new BlockParticleEmitterSimple(true);
		particleEmitterAdvanced = new BlockParticleEmitterAdvanced();

		soundEmitterSimple = new BlockSoundEmitterSimple(false);
		soundEmitterSimpleLight = new BlockSoundEmitterSimple(true);
		soundEmitterAdvanced = new BlockSoundEmitterAdvanced();

	}
	
	@SideOnly(Side.CLIENT)
	public static class Rendering extends TSBlockRendering
	{
		public static void preInitRender() 
		{
			reg(simpleLight);
			reg(redstoneLight);
			regOtherName(redstoneLightInverted, redstoneLight);
			regParticleEmitter();
			reg(dimmableLight);
			reg(dimmableLightRedstone);
			regOtherName(dimmableLightRedstoneInverted, dimmableLightRedstone);
			reg(particleEmitterAdvanced);
			reg(comparatorLight);
			regOtherName(comparatorLightInverted, comparatorLight);
			regSoundEmitter();
			reg(soundEmitterAdvanced);
			
			ignoreProperties(redstoneLight, BlockLightRedstone.POWERED);
			ignoreProperties(redstoneLightInverted, BlockLightRedstone.POWERED);
			ignoreProperties(particleEmitterSimple, BlockParticleEmitterSimple.PARTICLE);
			ignoreProperties(particleEmitterSimpleLight, BlockParticleEmitterSimple.PARTICLE);
			ignoreProperties(dimmableLight, BlockLightDimmable.LIGHT);
			ignoreProperties(dimmableLightRedstone, BlockLightDimmableRedstone.LIGHT, BlockLightDimmableRedstone.POWERED);
			ignoreProperties(dimmableLightRedstoneInverted, BlockLightDimmableRedstone.LIGHT, BlockLightDimmableRedstone.POWERED);
			ignoreProperties(particleEmitterAdvanced, BlockParticleEmitterAdvanced.POWERED);
			ignoreProperties(comparatorLight, BlockLightDimmable.LIGHT);
			ignoreProperties(comparatorLightInverted, BlockLightDimmable.LIGHT);
			ignoreProperties(soundEmitterSimple, BlockSoundEmitterSimple.SOUND);
			ignoreProperties(soundEmitterSimpleLight, BlockSoundEmitterSimple.SOUND);
			ignoreProperties(soundEmitterAdvanced, BlockSoundEmitterAdvanced.POWERED);


			ModelBakery.registerItemVariants(ItemHandler.lighteditor, new ResourceLocation(EA.MODID + ":" + "particles"));

		}
		
		
		public static void regParticleEmitter()
		{
			for(int i = 0; i < 16; i++)
			{
				regSpecial(particleEmitterSimple, i, "particle_emitter/particle_emitter_" + ((IMetaBlockName) particleEmitterSimple).getSpecialName(new ItemStack(particleEmitterSimple, 1, i)));
				regSpecial(particleEmitterSimpleLight, i, "particle_emitter_light/particle_emitter_" + ((IMetaBlockName) particleEmitterSimpleLight).getSpecialName(new ItemStack(particleEmitterSimpleLight, 1, i)));
			}
		
		}
		
		public static void regSoundEmitter()
		{
			for(int i = 0; i < 16; i++)
			{
				regSpecial(soundEmitterSimple, i, "sound_emitter/sound_emitter_" + ((IMetaBlockName) soundEmitterSimple).getSpecialName(new ItemStack(soundEmitterSimple, 1, i)));
				regSpecial(soundEmitterSimpleLight, i, "sound_emitter_light/sound_emitter_" + ((IMetaBlockName) soundEmitterSimpleLight).getSpecialName(new ItemStack(soundEmitterSimpleLight, 1, i)));

			}
		}
		
		
	}


	
	

}
