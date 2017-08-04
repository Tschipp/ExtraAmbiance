package tschipp.extraambiance.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.client.gui.FontRenderer;

public class NumericTextFieldWithLimit extends NumericTextField
{
	private int limit;

	public NumericTextFieldWithLimit(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height, boolean intOnly, boolean allowNegatives, int limit)
	{
		super(componentId, fontrendererObj, x, y, par5Width, par6Height, intOnly, allowNegatives);
		this.limit = limit;
	}

	@Override
	public void writeText(String textToWrite)
	{
		textToWrite = filterAllowedCharacters(textToWrite);
		int cursor = this.getCursorPosition();
		String text = this.getText();
		String after = text.substring(cursor);
		String before = text.replace(after, "");
		if (NumberUtils.isNumber(before + textToWrite + after))
			if (Double.parseDouble(before + textToWrite + after) <= limit)
				super.writeText(textToWrite);
			else
				this.setText("" + limit);
	}

}
