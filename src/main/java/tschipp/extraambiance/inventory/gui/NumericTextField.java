package tschipp.extraambiance.inventory.gui;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;

public class NumericTextField extends GuiTextField
{

	private boolean intOnly;
	private boolean allowNegatives;

	public NumericTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height, boolean intOnly, boolean allowNegatives)
	{
		super(componentId, fontrendererObj, x, y, par5Width, par6Height);
		this.intOnly = intOnly;
		this.allowNegatives = allowNegatives;
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
			super.writeText(textToWrite);
	}

	public String filterAllowedCharacters(String input)
	{
		StringBuilder stringbuilder = new StringBuilder();

		for (char c0 : input.toCharArray())
		{
			if (isAllowedCharacter(c0))
			{
				stringbuilder.append(c0);
			}
		}

		return stringbuilder.toString();
	}

	public boolean isAllowedCharacter(char c0)
	{
		boolean num = StringUtils.isNumeric("" + c0);
		if (!intOnly)
		{
			boolean period = (this.getText().contains(".") ? false : c0 == '.');
			if (allowNegatives)
			{
				boolean negative = (this.getText().contains("-") ? false : c0 == '-');
				return num || period || negative;
			}
			else
			{
				return num || period;
			}
		}
		else
		{
			if (allowNegatives)
			{
				boolean negative = (this.getText().contains("-") ? false : c0 == '-');
				return num || negative;

			}
			else
				return num;
		}
	}

}
