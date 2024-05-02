package com.aidenx11.JavaPixelPhysics.elements.immovable;

import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;

/**
 * Class to manage Stone elements. Extends the Immovable class.
 * 
 * Stone elements have a medium melting point, above which they will be melted
 * by lava. 
 * 
 * @author Aiden Schroeder
 */
public class Stone extends Immovable {

	public static ElementTypes type = ElementTypes.STONE;
	private static int meltingPoint = 150;
	
	public static CustomColor color = new CustomColor(ColorValues.STONE, true);

	public Stone(int row, int column) {
		super(type, row, column, color, false, 0, false, false, 0, false,
				meltingPoint);
	}

}
