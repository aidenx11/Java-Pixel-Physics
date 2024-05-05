package com.aidenx11.JavaPixelPhysics.elements.immovable;

import com.aidenx11.JavaPixelPhysics.elements.Element;

/**
 * Class to manage Fire elements. Extends the Immovable class.
 * 
 * Fire elements have a random color from the Element.fireColors enumeration.
 * Fire has a random short life span after which it will turn into smoke. All
 * fire elements are automatically set on fire.
 * 
 * Contains a method to check for elements near fire that can extinguish it.
 * 
 * @author Aiden Schroeder
 */
public class Fire extends Immovable {

	public static ElementTypes type = ElementTypes.FIRE;

	public Fire(int row, int column) {
		super(type, row, column, Element.fireColors[(int) Math.round(Math.random() * 3)], true,
				75 + (int) (75 * Math.random()), true, false, 0, true, -1);
		super.setOnFire(true);
	}


}
