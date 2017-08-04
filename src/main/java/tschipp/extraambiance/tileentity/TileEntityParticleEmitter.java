package tschipp.extraambiance.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.Constants;
import tschipp.extraambiance.block.BlockParticleEmitterAdvanced;
import tschipp.tschipplib.helper.ColorHelper;
import tschipp.tschipplib.helper.ItemHelper;

public class TileEntityParticleEmitter extends TileEntity implements ITickable
{
	// PARTICLE TYPE
	public String particleName = "flame";

	// MOVEMENT
	public double spawnX = 0;
	public double spawnY = 0;
	public double spawnZ = 0;

	public double spawnRadiusX = 0.25;
	public double spawnRadiusY = 0.25;
	public double spawnRadiusZ = 0.25;

	public double motionX = 0;
	public double motionY = 0;
	public double motionZ = 0;

	public boolean randomMotion = false;

	// VISUAL
	public int r = 255;
	public int g = 255;
	public int b = 255;

	public boolean changeColors = false;

	public float colorChangeAmount = 0.075f;

	public String particleTexture = "minecraft:blocks/stone";

	public String arguments = "minecraft:stone;0";
	
	public boolean ignoreRange = false;


	// OTHER
	public int spawnTime = 60;

	public int spawnCount = 5;

	public float particleSize = 1;

	public int particleMaxAge = 0;

	public boolean needsPower = false;

	private Random rand = new Random();
	public int timer = 0;
	private float[] rgb;
	public boolean useParticleTexture = false;

	@Override
	public void update()
	{
		rgb = ColorHelper.getRGB(r, g, b);
		useParticleTexture = particleName.equals("customTexture");

		if (timer >= spawnTime)
		{
			for (int i = 0; i < spawnCount; i++)
				spawnParticle();

			timer = 0;
		}

		timer++;
	}

	private void spawnParticle()
	{
		if (world.isRemote)
		{
			if (needsPower ? world.getBlockState(pos).getValue(BlockParticleEmitterAdvanced.POWERED) : true)
			{
				Particle particle = null;

				EnumParticleTypes type = EnumParticleTypes.getByName(particleName);
				if (type != null)
				{
					int id = type.getParticleID();

					int arg = type.getArgumentCount();
					if (arg == 0)
						particle = EA.proxy.spawnVanillaParticle(id, ignoreRange, false, pos.getX() + 0.5 + spawnX + (rand.nextGaussian() * spawnRadiusX), pos.getY() + 0.5 + spawnY + (rand.nextGaussian() * spawnRadiusY), pos.getZ() + 0.5 + spawnZ + (rand.nextGaussian() * spawnRadiusZ), motionX * (randomMotion ? rand.nextGaussian() : 1), motionY * (randomMotion ? rand.nextGaussian() : 1), motionZ * (randomMotion ? rand.nextGaussian() : 1));
					else if (arg == 1)
						particle = EA.proxy.spawnVanillaParticle(id, ignoreRange, false, pos.getX() + 0.5 + spawnX + (rand.nextGaussian() * spawnRadiusX), pos.getY() + 0.5 + spawnY + (rand.nextGaussian() * spawnRadiusY), pos.getZ() + 0.5 + spawnZ + (rand.nextGaussian() * spawnRadiusZ), motionX * (randomMotion ? rand.nextGaussian() : 1), motionY * (randomMotion ? rand.nextGaussian() : 1), motionZ * (randomMotion ? rand.nextGaussian() : 1), Block.getStateId(ItemHelper.getBlockStateFromString(arguments)));
					else if (arg == 2)
						particle = EA.proxy.spawnVanillaParticle(id, ignoreRange, false, pos.getX() + 0.5 + spawnX + (rand.nextGaussian() * spawnRadiusX), pos.getY() + 0.5 + spawnY + (rand.nextGaussian() * spawnRadiusY), pos.getZ() + 0.5 + spawnZ + (rand.nextGaussian() * spawnRadiusZ), motionX * (randomMotion ? rand.nextGaussian() : 1), motionY * (randomMotion ? rand.nextGaussian() : 1), motionZ * (randomMotion ? rand.nextGaussian() : 1), Item.getIdFromItem(ItemHelper.getItemFromString(arguments)), ItemHelper.getMetaFromString(arguments));
					
				}
				else if (useParticleTexture)
				{
					particle = EA.proxy.getCustomTextureParticle(world, pos.getX() + 0.5 + spawnX + (rand.nextGaussian() * spawnRadiusX), pos.getY() + 0.5 + spawnY + (rand.nextGaussian() * spawnRadiusY), pos.getZ() + 0.5 + spawnZ + (rand.nextGaussian() * spawnRadiusZ), motionX * (randomMotion ? rand.nextGaussian() : 1), motionY * (randomMotion ? rand.nextGaussian() : 1), motionZ * (randomMotion ? rand.nextGaussian() : 1), ignoreRange);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
				
				if (particle != null)
				{
					if (changeColors)
					{
						rgb = ColorHelper.changeBrightness((float) (rand.nextGaussian() * colorChangeAmount), rgb);
						particle.setRBGColorF(rgb[0], rgb[1], rgb[2]);
					}

					if (particleMaxAge != 0)
						particle.setMaxAge(particleMaxAge);

					if (useParticleTexture)
					{
						TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(particleTexture);
						particle.setParticleTexture(sprite);
					}
					particle.multipleParticleScaleBy(particleSize);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound particle = new NBTTagCompound();
		particle.setDouble("spawnX", spawnX);
		particle.setDouble("spawnY", spawnY);
		particle.setDouble("spawnZ", spawnZ);
		particle.setDouble("spawnRadiusX", spawnRadiusX);
		particle.setDouble("spawnRadiusY", spawnRadiusY);
		particle.setDouble("spawnRadiusZ", spawnRadiusZ);
		particle.setDouble("motionX", motionX);
		particle.setDouble("motionY", motionY);
		particle.setDouble("motionZ", motionZ);
		particle.setInteger("red", r);
		particle.setInteger("green", g);
		particle.setInteger("blue", b);
		particle.setString("particleName", particleName);
		particle.setInteger("spawnTime", spawnTime);
		particle.setInteger("spawnCount", spawnCount);
		particle.setFloat("particleSize", particleSize);
		particle.setFloat("colorChangeAmount", colorChangeAmount);
		particle.setBoolean("changeColors", changeColors);
		particle.setString("particleTexture", particleTexture);
		particle.setInteger("particleMaxAge", particleMaxAge);
		particle.setBoolean("randomMotion", randomMotion);
		particle.setInteger("timer", timer);
		particle.setBoolean("needsPower", needsPower);
		particle.setString("arguments", arguments);
		particle.setBoolean("ignoreRange", ignoreRange);

		compound.setTag(Constants.LIGHT_DATA_KEY, particle);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagCompound particle = compound.getCompoundTag(Constants.LIGHT_DATA_KEY);
		this.readParticleData(particle);

	}

	public void readParticleData(NBTTagCompound particle)
	{
		spawnX = particle.getDouble("spawnX");
		spawnY = particle.getDouble("spawnY");
		spawnZ = particle.getDouble("spawnZ");
		spawnRadiusX = particle.getDouble("spawnRadiusX");
		spawnRadiusY = particle.getDouble("spawnRadiusY");
		spawnRadiusZ = particle.getDouble("spawnRadiusZ");
		motionX = particle.getDouble("motionX");
		motionY = particle.getDouble("motionY");
		motionZ = particle.getDouble("motionZ");
		r = particle.getInteger("red");
		g = particle.getInteger("green");
		b = particle.getInteger("blue");
		particleName = particle.getString("particleName");
		spawnTime = particle.getInteger("spawnTime");
		spawnCount = particle.getInteger("spawnCount");
		particleSize = particle.getFloat("particleSize");
		colorChangeAmount = particle.getFloat("colorChangeAmount");
		particleTexture = particle.getString("particleTexture");
		changeColors = particle.getBoolean("changeColors");
		particleMaxAge = particle.getInteger("particleMaxAge");
		randomMotion = particle.getBoolean("randomMotion");
		timer = particle.getInteger("timer");
		needsPower = particle.getBoolean("needsPower");
		arguments = particle.getString("arguments");
		ignoreRange = particle.getBoolean("ignoreRange");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag = this.writeToNBT(tag);
		return tag;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}
	
	public boolean usesArguments()
	{
		EnumParticleTypes type = EnumParticleTypes.getByName(particleName);
		if (type != null)
		{
			return type.getArgumentCount() > 0;
		}
		
		return false;
	}

}
