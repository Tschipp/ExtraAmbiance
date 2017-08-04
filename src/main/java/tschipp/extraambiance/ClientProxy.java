package tschipp.extraambiance;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tschipp.extraambiance.handler.BlockHandler;
import tschipp.extraambiance.handler.ItemHandler;
import tschipp.extraambiance.particle.ParticleCustomTexture;
import tschipp.extraambiance.particle.ParticleLight;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		BlockHandler.Rendering.preInitRender();
		ItemHandler.Rendering.preInitRender();

	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}

	@Override
	public void spawnParticle(Particle particle)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);	
	}
	
	@Override
	public Particle getLightParticle(World world, BlockPos pos, float colorChangeAmount, float... rgb)
	{
		Particle particle = new ParticleLight(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, colorChangeAmount, rgb);
		return particle;
	}
	
	@Override
	public Particle getCustomTextureParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, boolean ignoreRange)
	{
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

		if (Minecraft.getMinecraft() != null && entity != null && Minecraft.getMinecraft().effectRenderer != null)
		{
			int i = this.calculateParticleLevel(false);
			double d0 = entity.posX - x;
			double d1 = entity.posY - y;
			double d2 = entity.posZ - z;
			Particle particle = new ParticleCustomTexture(world, x, y, z, motionX, motionY, motionZ);
			return ignoreRange ? particle : (d0 * d0 + d1 * d1 + d2 * d2 > 1024.0D ? null : (i > 1 ? null : particle));
		} else
		{
			return null;
		}
	}

	@Nullable
	@Override
	public Particle spawnVanillaParticle(int particleID, boolean ignoreRange, boolean minParticles, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
	{
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

		if (Minecraft.getMinecraft() != null && entity != null && Minecraft.getMinecraft().effectRenderer != null)
		{
			int i = this.calculateParticleLevel(minParticles);
			double d0 = entity.posX - xCoord;
			double d1 = entity.posY - yCoord;
			double d2 = entity.posZ - zCoord;
			return ignoreRange ? Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters) : (d0 * d0 + d1 * d1 + d2 * d2 > 1024.0D ? null : (i > 1 ? null : Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters)));
		} else
		{
			return null;
		}
	}
	
	private int calculateParticleLevel(boolean bool)
    {
        int i = Minecraft.getMinecraft().gameSettings.particleSetting;

        if (bool && i == 2 && Minecraft.getMinecraft().world.rand.nextInt(10) == 0)
        {
            i = 1;
        }

        if (i == 1 && Minecraft.getMinecraft().world.rand.nextInt(3) == 0)
        {
            i = 2;
        }

        return i;
    }

}
