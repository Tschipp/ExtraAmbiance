package tschipp.extraambiance.handler;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.extraambiance.tileentity.TileEntitySoundEmitter;

public class TileEntityHandler {
	
	
	public static void preInit()
	{
		
		GameRegistry.registerTileEntity(TileEntityParticleEmitter.class, EA.MODID + ":particle_emitter");
		GameRegistry.registerTileEntity(TileEntitySoundEmitter.class, EA.MODID + ":sound_emitter");

	}

}
