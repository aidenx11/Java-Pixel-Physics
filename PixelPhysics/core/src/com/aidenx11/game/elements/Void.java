package com.aidenx11.game.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

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

	private void actOnOther() {
		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		
		for (int i = 0; i < shuffledElements.size(); i++) {
			if (adjacentElements[i] != null && !(adjacentElements[i] instanceof Empty) && !(adjacentElements[i] instanceof Void)) {
				parentMatrix.clearElement(adjacentElements[i]);
			}
			
		}
	}

}
