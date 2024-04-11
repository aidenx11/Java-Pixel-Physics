package com.aidenx11.game.elements.immovable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.movable.liquid.Water;
import com.aidenx11.game.elements.movable.movable_solid.Rust;

public class Steel extends Immovable {

	public static ElementTypes type = ElementTypes.STEEL;
	private static float chanceToRust = 0.0001f;
	private static int colorIdx;
	private boolean colorSet = false;

	private static int[][] steelColors = new int[][] { { 206, 211, 212 }, { 192, 198, 199 },
			{ 168, 176, 178 }, { 153, 163, 163 } };

	public Steel(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.STEEL, true), false, 5, false, false, 0, false, 0);
		colorIdx = 0;
	}

	@Override
	public void update() {
		if (!colorSet) {
			setColor();
		}
		this.actOnOther();
		super.update();
	}

	public void setColor() {
		setColor(new CustomColor(steelColors[colorIdx]));
		if (colorIdx < 3) {
			colorIdx++;
		} else {
			colorIdx = 0;
		}
		colorSet = true;
	}

	public float getChanceToRust() {
		return chanceToRust;
	}

	public void actOnOther() {
		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		Element nextElement;
		boolean exposed = false;

		for (int i = 0; i < shuffledElements.size(); i++) {
			if (shuffledElements.get(i) instanceof Empty || shuffledElements.get(i) instanceof Water) {
				exposed = true;
			}
		}

		for (int i = 0; i < shuffledElements.size(); i++) {
			nextElement = shuffledElements.get(i);
			if (nextElement instanceof Rust && exposed && !this.limitedLife() && Math.random() < chanceToRust) {
				this.setLimitedLife(true);
				return;
			}
		}
	}

}
