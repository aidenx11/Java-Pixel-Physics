package com.aidenx11.game.elements.movable.liquid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.immovable.Steel;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.movable_solid.Dirt;
import com.aidenx11.game.elements.movable.movable_solid.Sand;

/**
 * Class to manage Water elements. Water is a relatively simple liquid with a
 * high dispersion rate.
 * 
 * Water accelerates faster than the rest of the elements to give a sense of a
 * faster settling logic, helped by its high dispersion rate.
 * 
 * Water can saturate Sand and Dirt to turn into WetSand and WetDirt.
 * 
 * Water will cause Steel to turn into Rust, given some time.
 * 
 * Lava can turn Water into Steam, but it takes time to do so. Water will
 * generally overpower lava and cool it.
 * 
 * @author Aiden Schroeder
 */
public class Water extends Liquid {

	public static ElementTypes type = ElementTypes.WATER;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION + 0.2f;
	private static float maxSpeed = 12f;
	private static int density = 6;
	private static int dispersionRate = 9;

	public Water(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WATER, false), false, 1, true, true, 0, false, 0,
				acceleration, maxSpeed, density, true, dispersionRate, -1);
		super.setFallingThroughAir(true);
	}

	@Override
	public void update() {
		this.actOnOther();
		super.update();
	}

	/**
	 * Method to control water saturating Dirt and Sand.
	 * 
	 * @return true if an element was saturated
	 */
	public boolean actOnOther() {

		Element[] adjacentElements = PixelPhysicsGame.matrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		Element nextElement;

		for (int i = 0; i < shuffledElements.size(); i++) {
			nextElement = shuffledElements.get(i);
			if (nextElement instanceof Sand) {
				Element newElement = PixelPhysicsGame.matrix.setNewElement(nextElement, ElementTypes.WET_SAND);
				((Movable) newElement).setVerticalVelocity(((Movable) nextElement).getVerticalVelocity());
				PixelPhysicsGame.matrix.clearElement(this);
				adjacentElements = null;
				return true;
			}
			if (nextElement instanceof Dirt) {
				Element newElement = PixelPhysicsGame.matrix.setNewElement(nextElement, ElementTypes.WET_DIRT);
				((Movable) newElement).setVerticalVelocity(((Movable) nextElement).getVerticalVelocity());
				PixelPhysicsGame.matrix.clearElement(this);
				adjacentElements = null;
				return true;
			}
			if (nextElement instanceof Steel) {
				causeRust(nextElement);
			}

		}
		adjacentElements = null;
		return false;
	}

}
