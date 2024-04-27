package com.aidenx11.game.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

/**
 * Class to manage Void elements. Void elements are unique in the way that they
 * destroy adjacent non-Void and non-Empty elements, and do not interact with
 * other Elements in any other way.
 * 
 * @author Aiden Schroeder
 */
public class Void extends Element {

	public static ElementTypes type = ElementTypes.VOID;

	public Void(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.VOID, true), false, 0, false, false, 0, false, 0);
		super.setDensity(9999);
	}

	@Override
	public void update() {
		actOnOther();
	}

	/**
	 * Method to handle destruction of adjacent Elements.
	 */
	private void actOnOther() {

		Element[] adjacentElements = pixelPhysicsGame.matrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);

		for (int i = 0; i < shuffledElements.size(); i++) {
			if (adjacentElements[i] != null && !(adjacentElements[i] instanceof Empty)
					&& !(adjacentElements[i] instanceof Void)) {
				pixelPhysicsGame.matrix.clearElement(adjacentElements[i]);
			}

		}
	}

}
