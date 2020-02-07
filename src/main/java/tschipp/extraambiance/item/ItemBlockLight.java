package tschipp.extraambiance.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.tschipplib.item.TSItemBlock;

public class ItemBlockLight extends TSItemBlock implements ILightViewer
{

	public ItemBlockLight(Block block)
	{
		super(block, block.getRegistryName().getResourcePath(), EA.MODID);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(I18n.translateToLocal("desc." + block.getRegistryName().getResourcePath()));
	}

}
