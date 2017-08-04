package tschipp.extraambiance.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import tschipp.extraambiance.handler.ConfigHandler;

public class Constants {
	
	/**
	 * The AABB of the Lights
	 */
	public static final AxisAlignedBB LIGHT_AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);

	/**
	 * The key under which things like Particle Data gets saved. It is used by the Light Editor.
	 */
	public static final String LIGHT_DATA_KEY = "toSaveLightData";
	
	
	/**
	 * The Particle Spawn range for lights. You can replace this with a value from your config, or leave it.
	 */
	public static final int PARTICLE_SPAWN_RANGE = ConfigHandler.lightParticleViewDistance;

	
	/**
	 * Helper Method for convenience.
	 * @param type
	 * @param player
	 * @return
	 */
	public static boolean hasItemHeld(Class<?> type, EntityPlayer player)
	{
		ItemStack main = player.getHeldItemMainhand();
		ItemStack off = player.getHeldItemOffhand();

		if (!main.isEmpty() && type.isInstance(main.getItem()))
			return true;
		else if (!off.isEmpty() && type.isInstance(off.getItem()))
			return true;
		else
			return false;

	}
	
	
}
