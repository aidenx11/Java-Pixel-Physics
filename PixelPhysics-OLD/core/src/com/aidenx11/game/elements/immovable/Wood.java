package com.aidenx11.game.elements.immovable;

import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.color.CustomColor;

/**
 * Class to manage Wood elements. Extends the Immovable class.
 * 
 * Wood has a much lower chance to catch on fire than leaves, and a longer
 * lifetime upon being lit on fire. Like leaves, wood turns into smoke upon
 * burning up.
 * 
 * Wood elements can be melted by lava.
 * 
 * @author Aiden Schroeder
 */
public class Wood extends Immovable {

	public static ElementTypes type = ElementTypes.WOOD;
	private static int lifetime = 150;
	private static float chanceToCatch = 0.006f;

	public Wood(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WOOD_COLOR, true), false, lifetime, true, false,
				chanceToCatch, false, -1);
	}

}
