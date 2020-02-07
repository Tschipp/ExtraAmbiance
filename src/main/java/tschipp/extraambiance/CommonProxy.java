package tschipp.extraambiance;

import javax.annotation.Nullable;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tschipp.extraambiance.event.LightEvents;
import tschipp.extraambiance.handler.BlockHandler;
import tschipp.extraambiance.handler.ConfigHandler;
import tschipp.extraambiance.handler.CraftingHandler;
import tschipp.extraambiance.handler.GuiHandler;
import tschipp.extraambiance.handler.ItemHandler;
import tschipp.extraambiance.handler.TileEntityHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.preInit(event);
		BlockHandler.preInit();
		ItemHandler.preInit();
		TileEntityHandler.preInit();
	}
	
	public void init(FMLInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new LightEvents());
		CraftingHandler.init();
	    NetworkRegistry.INSTANCE.registerGuiHandler(EA.instance, new GuiHandler());

	}

	public void postInit(FMLPostInitializationEvent event) {
	
	}
	
	public Particle getLightParticle(World world, BlockPos pos, float colorChangeAmount, float... rgb)
	{
		return null;
	}
	
	@Nullable
	public Particle spawnVanillaParticle(int particleID, boolean ignoreRange, boolean minParticles, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
	{
		return null;
	}
	@Nullable
	public Particle getCustomTextureParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, boolean ignoreRange)
	{
		return null;
	}

	public void spawnParticle(Particle particle)
	{
		
	}


}
