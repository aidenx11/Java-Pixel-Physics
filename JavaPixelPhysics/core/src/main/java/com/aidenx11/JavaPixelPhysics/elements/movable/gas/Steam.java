package com.aidenx11.JavaPixelPhysics.elements.movable.gas;

import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;

/**
 * Class to manage Steam elements. Steam is a simple gas that disappears
 * relatively quickly.
 * 
 * @author Aiden Schroeder
 */
public class Steam extends Gas {

	public static ElementTypes type = ElementTypes.STEAM;
	private static float acceleration = -0.3f;
	private static float maxSpeed = 0.75f;
	private static int density = 1;
	
	private static CustomColor color = new CustomColor(ColorValues.STEAM, false);

	public Steam(int row, int column) {
		super(type, row, column, color, true, (int) (150 + Math.random() * 100),
				false, false, 0, false, -0.15f, acceleration, maxSpeed, density, true);
	}

}
