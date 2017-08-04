package tschipp.extraambiance.tileentity;

import java.util.Random;

import tschipp.extraambiance.api.Constants;
import tschipp.extraambiance.block.BlockParticleEmitterAdvanced;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TileEntitySoundEmitter extends TileEntity implements ITickable
{

	// SOUND
	public String soundName = "block.dispenser.launch";
	public float volume = 1.0f;
	public float pitch = 1.0f;
	public boolean useRandomVolume = false;
	public boolean useRandomPitch = true;

	// POSITION
	public double spawnX = 0;
	public double spawnY = 0;
	public double spawnZ = 0;

	// OTHER
	public int spawnTime = 60;
	public boolean needsPower = false;

	private Random rand = new Random();
	private int timer = 0;

	@Override
	public void update()
	{
		if (timer >= spawnTime)
		{

			makeSound();

			timer = 0;
		}

		timer++;
	}

	private void makeSound()
	{
		if (needsPower ? world.getBlockState(pos).getValue(BlockParticleEmitterAdvanced.POWERED) : true)
		{
			SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundName));

			if (sound != null)
			{
				world.playSound(pos.getX() + spawnX, pos.getY() + spawnY, pos.getZ() + spawnZ, sound, SoundCategory.BLOCKS, (float) (volume + (useRandomVolume ? rand.nextGaussian() * 0.3 : 0)), (float) (pitch + (useRandomPitch ? rand.nextGaussian() * 0.3 : 0)), true);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound sound = new NBTTagCompound();
		sound.setDouble("spawnX", spawnX);
		sound.setDouble("spawnY", spawnY);
		sound.setDouble("spawnZ", spawnZ);
		sound.setString("soundName", soundName);
		sound.setInteger("spawnTime", spawnTime);
		sound.setFloat("volume", volume);
		sound.setFloat("pitch", pitch);
		sound.setBoolean("useRandomVolume", useRandomVolume);
		sound.setBoolean("useRandomPitch", useRandomPitch);
		sound.setInteger("timer", timer);
		sound.setBoolean("needsPower", needsPower);
		compound.setTag(Constants.LIGHT_DATA_KEY, sound);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagCompound sound = compound.getCompoundTag(Constants.LIGHT_DATA_KEY);
		this.readSoundData(sound);

	}

	private void readSoundData(NBTTagCompound sound)
	{
		spawnX = sound.getDouble("spawnX");
		spawnY = sound.getDouble("spawnY");
		spawnZ = sound.getDouble("spawnZ");
		soundName = sound.getString("soundName");
		spawnTime = sound.getInteger("spawnTime");
		volume = sound.getFloat("volume");
		pitch = sound.getFloat("pitch");
		useRandomVolume = sound.getBoolean("useRandomVolume");
		useRandomPitch = sound.getBoolean("useRandomPitch");
		timer = sound.getInteger("timer");
		needsPower = sound.getBoolean("needsPower");
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

}
