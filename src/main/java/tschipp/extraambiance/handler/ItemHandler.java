package tschipp.extraambiance.handler;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.item.ItemBlockLight;
import tschipp.extraambiance.item.ItemBlockLightSubtypes;
import tschipp.extraambiance.item.ItemBlockParticleEmitterAdvanced;
import tschipp.extraambiance.item.ItemBlockSoundEmitterAdvanced;
import tschipp.extraambiance.item.ItemLightEditor;
import tschipp.tschipplib.util.TSItemRendering;

public class ItemHandler {
	
	public static ItemBlock simpleLight;
	public static ItemBlock redstoneLight;
	public static ItemBlock redstoneLightInverted;
	public static ItemBlock dimmableLight;
	public static ItemBlock dimmableLightRedstone;
	public static ItemBlock dimmableLightRedstoneInverted;
	public static ItemBlock comparatorLight;
	public static ItemBlock comparatorLightInverted;

	public static ItemBlock particleEmitterSimple;
	public static ItemBlock particleEmitterSimpleLight;
	public static ItemBlock particleEmitterAdvanced;

	public static ItemBlock soundEmitterSimple;
	public static ItemBlock soundEmitterSimpleLight;
	public static ItemBlock soundEmitterAdvanced;
	
	public static Item lighteditor;
	
	
	public static void preInit()
	{
		lighteditor = new ItemLightEditor();
		
		simpleLight = new ItemBlockLight(BlockHandler.simpleLight);
		redstoneLight = new ItemBlockLight(BlockHandler.redstoneLight);
		redstoneLightInverted = new ItemBlockLight(BlockHandler.redstoneLightInverted);
		dimmableLight = new ItemBlockLight(BlockHandler.dimmableLight);
		dimmableLightRedstone = new ItemBlockLight(BlockHandler.dimmableLightRedstone);
		dimmableLightRedstoneInverted = new ItemBlockLight(BlockHandler.dimmableLightRedstoneInverted);
		comparatorLight = new ItemBlockLight(BlockHandler.comparatorLight);
		comparatorLightInverted = new ItemBlockLight(BlockHandler.comparatorLightInverted);

		particleEmitterSimple = new ItemBlockLightSubtypes(BlockHandler.particleEmitterSimple);
		particleEmitterSimpleLight = new ItemBlockLightSubtypes(BlockHandler.particleEmitterSimpleLight);
		particleEmitterAdvanced = new ItemBlockParticleEmitterAdvanced(BlockHandler.particleEmitterAdvanced);

		soundEmitterSimple = new ItemBlockLightSubtypes(BlockHandler.soundEmitterSimple);
		soundEmitterSimpleLight = new ItemBlockLightSubtypes(BlockHandler.soundEmitterSimpleLight);
		soundEmitterAdvanced = new ItemBlockSoundEmitterAdvanced(BlockHandler.soundEmitterAdvanced);

	}
	
	
	public static class Rendering extends TSItemRendering
	{
		public static void preInitRender()
		{
			reg(lighteditor);

		}
	}
	
	
	

}
