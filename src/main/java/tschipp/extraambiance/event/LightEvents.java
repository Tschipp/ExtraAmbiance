package tschipp.extraambiance.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.extraambiance.api.ILight;
import tschipp.extraambiance.api.ILightEditor;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.extraambiance.api.LightData;
import tschipp.tschipplib.helper.ItemStackHelper;
import tschipp.tschipplib.helper.RayHelper;

public class LightEvents
{

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBlockRender(DrawBlockHighlightEvent event)
	{
		RayTraceResult ray = event.getTarget();
		if (ray != null && ray.typeOfHit == Type.BLOCK)
		{
			BlockPos pos = ray.getBlockPos();
			Block block = event.getPlayer().world.getBlockState(pos).getBlock();
			ItemStack held = event.getPlayer().getHeldItemMainhand();
			ItemStack off = event.getPlayer().getHeldItemOffhand();
			
			if (block instanceof ILight)
			{
				if (held.isEmpty() && off.isEmpty())
				{
					event.setCanceled(true);
					return;
				} else if (ItemStackHelper.hasItemHeld(ILightViewer.class, event.getPlayer()))
				{
					if (ItemStackHelper.hasItemHeld(ILightEditor.class, event.getPlayer()))
					{
						((ILight) block).onHover(event.getPlayer(), pos, ItemStackHelper.getHandForType(ILightEditor.class, event.getPlayer()), true);
						return;
					}
					((ILight) block).onHover(event.getPlayer(), pos, ItemStackHelper.getHandForType(ILightViewer.class, event.getPlayer()), false);
					return;
				} else
				{
					event.setCanceled(true);
					return;
					
				}

			}

		}

	}

	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		if (event.isCanceled())
			return;

		EntityPlayer player = event.getPlayer();
		IBlockState state = event.getState();
		if (player != null && state != null)
		{
			if (ItemStackHelper.hasTypeInHand(ILightEditor.class, player, EnumHand.MAIN_HAND))
			{
				if (state.getBlock() instanceof ILight)
				{
					ILightEditor editer = (ILightEditor) player.getHeldItem(EnumHand.MAIN_HAND).getItem();
					editer.onLightBreak((ILight) state.getBlock(), player, event.getPos(), EnumHand.MAIN_HAND);
					event.setCanceled(true);
					event.getWorld().setBlockToAir(event.getPos());
				}

			}
		}
	}

	@SubscribeEvent
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
	{
		
		if(event.isCanceled())
			return;
		
		EntityPlayer player = event.getEntityPlayer();
		EnumHand hand = event.getHand();
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		
		if (ItemStackHelper.hasTypeInHand(ILightEditor.class, player, hand) && player.isSneaking())
		{
			if (block instanceof ILight)
			{
				Item item = event.getItemStack().getItem();
				((ILightEditor) item).onShiftRightClickLight((ILight) block, player, event.getPos(), event.getItemStack(), hand);

			}

		}
		if (hand == EnumHand.OFF_HAND && (ItemStackHelper.hasTypeInHand(ILightEditor.class, player, EnumHand.MAIN_HAND) && player.isSneaking() && LightData.isDataCompatible(player.getHeldItemMainhand(), block)))
		{
			event.setUseItem(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onRightClickAir(PlayerInteractEvent.RightClickItem event)
	{	
		if(event.isCanceled())
			return;
		
		EntityPlayer player = event.getEntityPlayer();
		EnumHand hand = event.getHand();
		RayTraceResult ray = RayHelper.rayTrace(player, 4.5);
		if (ray == null || ray.typeOfHit != Type.BLOCK)
		{
			if (ItemStackHelper.hasTypeInHand(ILightEditor.class, player, hand) && player.isSneaking())
			{
				Item item = player.getHeldItem(hand).getItem();
				((ILightEditor) item).onShiftRightClickAir(player, hand);
			}
		}
		
		
	}
	
	

}
