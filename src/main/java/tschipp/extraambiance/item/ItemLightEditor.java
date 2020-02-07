package tschipp.extraambiance.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILight;
import tschipp.extraambiance.api.ILightEditor;
import tschipp.extraambiance.api.LightData;
import tschipp.tschipplib.item.TSItem;

public class ItemLightEditor extends TSItem implements ILightEditor
{

	public ItemLightEditor()
	{
		super("light_editor", EA.MODID);
		this.setCreativeTab(EA.extraambiance);
		this.setMaxDamage(2000);
		this.setMaxStackSize(1);
	}

	@Override
	public void onLightEdit(EntityPlayer player, BlockPos pos, EnumHand hand)
	{
		ItemStack held = player.getHeldItem(hand);

		held.damageItem(1, player);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return !repair.isEmpty() && repair.getItem() == Items.IRON_INGOT;
	}

	@Override
	public void onLightBreak(ILight block, EntityPlayer player, BlockPos pos, EnumHand hand)
	{
		ItemStack held = player.getHeldItem(hand);
		held.damageItem(1, player);

		ILightEditor.super.onLightBreak(block, player, pos, hand);

	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (isSelected)
		{
			LightData lightData = LightData.getLightDataFromStack(stack);
			if (lightData != null)
			{
				if (entityIn instanceof EntityPlayer)
				{
					((EntityPlayer) entityIn).sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.stored")), true);
				}
			}
		}
	}

	@Override
	public void onShiftRightClickAir(EntityPlayer player, EnumHand hand)
	{
		if (LightData.hasLightData(player.getHeldItem(hand)))
			player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.cleared")), true);

		ILightEditor.super.onShiftRightClickAir(player, hand);

	}
	
	@Override
 public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(I18n.translateToLocal("desc.light_editor"));
		
		if(LightData.hasLightData(stack))
			tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.stored"));
	}

}
