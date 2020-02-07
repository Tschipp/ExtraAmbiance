package tschipp.extraambiance.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tschipp.tschipplib.helper.ColorHelper;

public class ParticleLight extends Particle
{

	public ParticleLight(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float colorChangeAmount, float... rgb)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
		this.setParticleTextureIndex(0);
		float[] color = ColorHelper.changeBrightness((float) (rand.nextGaussian() * colorChangeAmount), rgb);
		this.setRBGColorF(color[0], color[1], color[2]);
		this.particleScale = 2f;
		this.particleMaxAge = 30 + rand.nextInt(20);
		this.particleAlpha = 1f;

		motionX = 0.005 * rand.nextGaussian();
		motionY = 0.005 * rand.nextGaussian();
		motionZ = 0.005 * rand.nextGaussian();

	}

	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}
		move(motionX, motionY, motionZ);

		this.setParticleTextureIndex(950 - this.particleAge * 8 / this.particleMaxAge);
		// 7 for dust, 952 for endrod
	}

	@Override
	public int getFXLayer()
	{
		return 0;
	}


}
