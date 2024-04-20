package com.aidenx11.game.elements.movable.gas;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

/**
 * Class to manage Smoke elements. Smoke elements are a simple gas that
 * disappear relatively quickly.
 * 
 * There is a small chance that Smoke elements can set other elements on fire,
 * as defined in the Element.updateElementLife() method.
 * 
 * @author Aiden Schroeder
 */
public class Smoke extends Gas {

	public static ElementTypes type = ElementTypes.SMOKE;
	private static float acceleration = -0.1f;
	private static float maxSpeed = 0.35f;
	private static int density = 1;

	static CustomColor[] fireColors = new CustomColor[] { new CustomColor(253, 207, 88), new CustomColor(242, 125, 12),
			new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	public Smoke(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.SMOKE, true), true, (int) (150 + (Math.random() * 50)),
				false, false, 0, false, 0, acceleration, maxSpeed, density, true);
		super.setOnFire(Math.random() < 0.1);
	}
}
