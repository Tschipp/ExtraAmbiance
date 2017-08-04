package tschipp.extraambiance.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.tschipplib.item.TSItemBlock;

public class ItemBlockLight extends TSItemBlock implements ILightViewer {

	
	public ItemBlockLight(Block block) {
		super(block, block.getRegistryName().getResourcePath(), EA.MODID);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(I18n.translateToLocal("desc." + block.getRegistryName().getResourcePath()));
	}

}
