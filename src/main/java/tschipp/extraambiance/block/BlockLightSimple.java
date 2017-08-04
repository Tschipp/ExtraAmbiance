package tschipp.extraambiance.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.Constants;
import tschipp.extraambiance.api.ILightViewer;

public class BlockLightSimple extends BlockLightBase {

	public BlockLightSimple() {
		super("light_simple");
		this.setLightLevel(1f);
	}
	
	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		return 	EA.proxy.getLightParticle(world, pos, 0.065f, 0.929411f, 0.8862745f, 0.50980392f);

	}

}
