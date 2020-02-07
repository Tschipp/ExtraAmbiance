package tschipp.extraambiance.api;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * All Blocks that have their Hitboxes appear when a {@link ILightViewer} is
 * held in hand, and generally work with ExtraAmbiance, implement this
 * Interface.
 * 
 * @author Tschipp
 *
 */
public interface ILight
{

	/**
	 * This method gets called every tick when a player is hovering over a
	 * {@link ILight} with a {@link ILightViewer} in hand. Only gets called on
	 * the Client side.
	 * 
	 * @param player The {@link EntityPlayer} hovering over the Block.
	 * @param pos The Position of the block that was hovered
	 * @param editor true, if the held item was a {@link ILightEditor} and false
	 *        if it was a {@link ILightViewer}
	 * @param hand The hand that is holding the {@link ILightViewer} or
	 *        {@link ILightEditor}
	 */
	@SideOnly(Side.CLIENT)
	public void onHover(EntityPlayer player, BlockPos pos, EnumHand hand, boolean editor);

	/**
	 * This method gets called every time the player breaks an {@link ILight}
	 * with a {@link ILightEditor}. Mostly used to manipulate drops.
	 * 
	 * @param world
	 * @param pos
	 */
	default void onBreak(EntityPlayer player, World world, BlockPos pos)
	{
		Item item = Item.getItemFromBlock((Block) this);
		boolean subtypes = item.getHasSubtypes();
		ItemStack drop = new ItemStack(item, 1, subtypes ? ((Block) this).getMetaFromState(world.getBlockState(pos)) : 0);

		if (!player.isCreative())
			Block.spawnAsEntity(world, pos, drop);
	}

	/**
	 * This method gets called every time a player Shift+Right-Clicks a
	 * {@link ILight} with a {@link ILightEditor}
	 * 
	 * @param player The player performing the action
	 * @param world The world
	 * @param pos The position of the Light
	 * @param stack The ItemStack performing the action
	 * @param hand The EnumHand performing the action
	 */
	public void onShiftRightClick(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumHand hand);

	/**
	 * This method needs to be called in
	 * {@link Block#randomDisplayTick(IBlockState, World, BlockPos, Random)}. It
	 * registers the block for spawning particles, meaning when the player is
	 * holding a {@link ILightViewer}, particles will show up.
	 * 
	 * @param world
	 * @param state
	 * @param pos
	 * @param rand
	 */
	@SideOnly(Side.CLIENT)
	default void generateViewingParticles(World world, IBlockState state, BlockPos pos, Random rand)
	{
		List<EntityPlayerSP> players = world.getEntitiesWithinAABB(EntityPlayerSP.class, new AxisAlignedBB(pos).expand(Constants.PARTICLE_SPAWN_RANGE, Constants.PARTICLE_SPAWN_RANGE, Constants.PARTICLE_SPAWN_RANGE).expand(-Constants.PARTICLE_SPAWN_RANGE, -Constants.PARTICLE_SPAWN_RANGE, -Constants.PARTICLE_SPAWN_RANGE));
		for (int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			if (world.isRemote)
			{
				if (Constants.hasItemHeld(ILightViewer.class, player))
				{
					Particle particle = this.getViewingParticle(world, state, pos, rand);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);			
				}
			}
		}
	}

	/**
	 * This gets the actual particle that gets spawned in
	 * {@link ILight#generateViewingParticles(World, IBlockState, BlockPos, Random)}
	 * .
	 * 
	 * @param world
	 * @param state
	 * @param pos
	 * @param rand
	 * @return The Particle that will be spawned
	 */
	@SideOnly(Side.CLIENT)
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand);

}
