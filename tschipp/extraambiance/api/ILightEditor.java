package tschipp.extraambiance.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * All Items that need to be able to change data of lights, like
 * increasing/decreasing light values or editing Particles, need to implement
 * this interface.
 * 
 * @author Tschipp
 *
 */
public interface ILightEditor extends ILightViewer
{

	/**
	 * Gets called every time a player edits a light, meaning
	 * increases/decreases light values or editing Particle Emitters. Needs to
	 * be manually called.
	 * 
	 * @param player The Player that edited the Light
	 * @param hand The EnumHand that is holding the ILightEditor
	 * @param pos The Position of the ILight
	 */
	public void onLightEdit(EntityPlayer player, BlockPos pos, EnumHand hand);

	/**
	 * Called when a player breaks a {@link ILight}. If you override it, make
	 * sure to call {@link ILight#onBreak(World, BlockPos)}.
	 * 
	 * @param pos The position of the Light
	 * @param hand The hand that is holding the ILightEditor, always
	 *        {@link EnumHand#MAIN_HAND}
	 * @param block The ILight that is broken
	 */
	default void onLightBreak(ILight block, EntityPlayer player, BlockPos pos, EnumHand hand)
	{
		block.onBreak(player, player.world, pos);
	}

	/**
	 * Called every time when a {@link ILight} is Shift+Right-Clicked.
	 * 
	 * @param block The Light
	 * @param player The Player performing the action
	 * @param pos The Position of the Block
	 * @param stack The ItemStack performing the action
	 * @param hand The EnumHand performing the action
	 */
	default void onShiftRightClickLight(ILight block, EntityPlayer player, BlockPos pos, ItemStack stack, EnumHand hand)
	{
		LightData lightData = LightData.getLightDataFromStack(stack);
		if (lightData != null)
		{
			if (lightData.apply(player.world, pos))
				player.swingArm(hand);
			return;

		}
		block.onShiftRightClick(player, player.world, pos, stack, hand);
	}

	/**
	 * Called every time when air is Shift+Right-Clicked. Used mostly to clear
	 * any Light Data from Light Editors
	 * 
	 * @param player
	 * @param hand
	 */
	default void onShiftRightClickAir(EntityPlayer player, EnumHand hand)
	{
		if (LightData.hasLightData(player.getHeldItem(hand)))
			LightData.clearLightData(player.getHeldItem(hand));
	}

}
