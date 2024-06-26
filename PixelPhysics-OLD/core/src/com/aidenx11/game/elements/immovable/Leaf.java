package com.aidenx11.game.elements.immovable;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

/**
 * Class to manage Leaf elements. Extends the Immovable class.
 * 
 * Leaf elements have a high chance to catch on fire, and a short lifetime. Upon
 * being caught on fire, they are set to have a limited life. After they burn
 * up, they turn into smoke.
 * 
 * Leaf elements can be melted by lava.
 * 
 * @author Aiden Schroeder
 */
public class Leaf extends Immovable {

	public static ElementTypes type = ElementTypes.LEAF;
	private static float chanceToCatch = 0.03f;
	private static int lifetime = 50;

	public Leaf(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.LEAF, true), false, lifetime, true, false, chanceToCatch,
				false, -1);
	}

}
