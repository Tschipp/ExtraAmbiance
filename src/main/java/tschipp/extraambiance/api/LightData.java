package tschipp.extraambiance.api;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Covenient way to store Data of lights, that includes meta values and nbt.
 * 
 * @author Tschipp
 *
 */
public class LightData
{

	/**
	 * The IBlockState that will be synced.
	 */
	private IBlockState state;

	/**
	 * The NBTTagCompound that will be synced. Can be null, can also be empty
	 * ({@code new NBTTagCompound()}).
	 */
	@Nullable
	private NBTTagCompound nbt;

	private LightData(IBlockState state, World world, BlockPos pos)
	{
		this.state = state;

		TileEntity tile = world.getTileEntity(pos);
		if (tile != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag = tile.writeToNBT(tag);

			if (tag.hasKey(Constants.LIGHT_DATA_KEY))
			{
				this.nbt = tag.getCompoundTag(Constants.LIGHT_DATA_KEY);
			}

		}
	}

	private LightData(IBlockState state, NBTTagCompound tag)
	{
		this.state = state;
		this.nbt = tag;
	}

	/**
	 * Gets the Light data of the Block at the BlockPos. It will always get the
	 * state and then try to get NBT. All NBTdata that needs to be synced needs
	 * to be in a separate NBTTagCompound and have the key
	 * {@link Constants#LIGHT_DATA_KEY}.
	 * 
	 * @param world
	 * @param pos
	 * @return The resulting LightData or null, if the Block isn't an
	 *         {@link ILight}.
	 */
	@Nullable
	public static LightData getLightData(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof ILight))
			return null;

		return new LightData(state, world, pos);
	}

	/**
	 * Gets the Light data from an ItemStack, or null if it doesn't have any.
	 * 
	 * @param stack
	 * @return The resulting LightData or null, if the ItemStack doesn't have a
	 *         "lightData" key.
	 */
	@Nullable
	public static LightData getLightDataFromStack(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			return null;

		if (!stack.getTagCompound().hasKey("lightData"))
			return null;

		NBTTagCompound lightData = stack.getTagCompound().getCompoundTag("lightData");
		Block block = Block.getBlockFromName(lightData.getString("block"));
		int meta = lightData.getInteger("meta");
		IBlockState state = block.getStateFromMeta(meta);
		NBTTagCompound toSync = lightData.getCompoundTag(Constants.LIGHT_DATA_KEY);

		return new LightData(state, toSync);

	}

	/**
	 * Saves the current LightData to the ItemStack
	 * 
	 * @param stack
	 */
	public void saveToStack(ItemStack stack)
	{
		int meta = this.state.getBlock().getMetaFromState(state);
		String name = this.state.getBlock().getRegistryName().toString();

		NBTTagCompound lightData = new NBTTagCompound();

		NBTTagCompound itemData;
		if (stack.hasTagCompound())
			itemData = stack.getTagCompound();
		else
			itemData = new NBTTagCompound();

		lightData.setInteger("meta", meta);
		lightData.setString("block", name);
		if (nbt == null ? false : nbt != new NBTTagCompound())
			lightData.setTag(Constants.LIGHT_DATA_KEY, nbt);

		itemData.setTag("lightData", lightData);
		stack.setTagCompound(itemData);
	}

	/**
	 * Applies the LightData to the Block at the BlockPos.
	 * 
	 * @param world
	 * @param pos
	 * @return true, if it was successful, false if it was unsuccessful.
	 */
	public boolean apply(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (this.state.getBlock() != state.getBlock())
			return false;

		world.setBlockState(pos, this.state);
		state.getBlock().neighborChanged(this.state, world, pos, state.getBlock(), pos.offset(EnumFacing.UP));

		if (nbt == null ? false : nbt != new NBTTagCompound())
		{
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tile.writeToNBT(tag);
				tag.setTag(Constants.LIGHT_DATA_KEY, nbt);

				tile.readFromNBT(tag);
			}
		}

		return true;
	}

	/**
	 * Clears the LightData of an ItemStack if it has any.
	 * 
	 * @param stack
	 */
	public static void clearLightData(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			return;

		if (!stack.getTagCompound().hasKey("lightData"))
			return;

		stack.getTagCompound().removeTag("lightData");
	}

	/**
	 * Returns whether or not the Stack has any LightData on it.
	 * 
	 * @param stack
	 * @return
	 */
	public static boolean hasLightData(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			return false;

		if (stack.getTagCompound().hasKey("lightData"))
			return true;

		return false;
	}

	/**
	 * Returns whether or not the LightData can be applied to this block.
	 * 
	 * @param block
	 * @return
	 */
	public boolean isDataCompatible(Block block)
	{
		return this.state.getBlock() == block;
	}

	/**
	 * Returns whether or not the LightData can be applied to this block.
	 * 
	 * @param stack
	 * @param block
	 * @return
	 */
	public static boolean isDataCompatible(ItemStack stack, Block block)
	{
		LightData light = getLightDataFromStack(stack);
		if (light == null)
			return false;

		return light.getState().getBlock() == block;
	}

	public IBlockState getState()
	{
		return state;
	}

	public NBTTagCompound getNbt()
	{
		return nbt;
	}

}
