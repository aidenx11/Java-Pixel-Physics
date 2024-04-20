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

/**
 * Class to manage Steel elements. Extends the Immovable class.
 * 
 * Steel has a specific sequence of colors it alternates through in the
 * steelColors enumeration. The color alternates in an algorithmic pattern so
 * give large sections of steel a geometric look.
 * 
 * Steel can turn into rust if it is in contact with water for an extended
 * period of time, or if it is in contact with air and other rust. Lava cannot melt Steel.
 * 
 * Lava cannot melt steel.
 * 
 * @author Aiden Schroeder
 */
public class Steel extends Immovable {

	public static ElementTypes type = ElementTypes.STEEL;
	private static float chanceToRust = 0.0001f;
	private static int colorIdx;

	private static int[][] steelColors = new int[][] { { 206, 211, 212 }, { 192, 198, 199 }, { 168, 176, 178 },
			{ 153, 163, 163 } };

	public Steel(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.STEEL, true), false, 5, false, false, 0, false, 0);
		setColor();
	}

	@Override
	public void update() {
		this.actOnOther();
		super.update();
	}

	/**
	 * Sets the color of each steel in a linear pattern to give an interesting
	 * texture
	 */
	public void setColor() {
		setColor(new CustomColor(steelColors[colorIdx]));
		if (colorIdx < 3) {
			colorIdx++;
		} else {
			colorIdx = 0;
		}
	}

	/**
	 * Returns the chance for steel to rust
	 * 
	 * @return the chance for steel to rust
	 */
	public float getChanceToRust() {
		return chanceToRust;
	}

	/**
	 * Checks if this steel is exposed to the air or to water. If it is exposed to
	 * air and there is rust near it, it will rust. If it is exposed to water for an
	 * amount of time, it will rust.
	 */
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
