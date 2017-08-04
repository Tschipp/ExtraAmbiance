package tschipp.extraambiance.item;

import tschipp.tschipplib.block.IMetaBlockName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockLightSubtypes extends ItemBlockLight {

	public ItemBlockLightSubtypes(Block block) {
		super(block);
		this.hasSubtypes = true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		return block.getUnlocalizedName() + "." + ((IMetaBlockName)this.block).getSpecialName(stack);
		
	}

}
