package com.aidenx11.game.elements.movable.liquid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.movable_solid.Dirt;
import com.aidenx11.game.elements.movable.movable_solid.Sand;

public class Water extends Liquid {

	public static ElementTypes type = ElementTypes.WATER;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION + 0.2f;
	private static float maxSpeed = 15f;
	private static float density = 7f;
	private static int dispersionRate = 8;

	int[] darkerWater = new int[] { 15, 94, 156 };

	public Water(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WATER, false), false, 1, true, true, 0, false, 2f,
				acceleration, maxSpeed, density, true, dispersionRate, -1);
	}

	@Override
	public void update() {
		this.actOnOther();
		causeRust();
		super.update();
	}

	public boolean actOnOther() {

		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		Element nextElement;

		for (int i = 0; i < shuffledElements.size(); i++) {
			nextElement = shuffledElements.get(i);
			if (nextElement instanceof Sand) {
				Element newElement = parentMatrix.setNewElement(nextElement, ElementTypes.WET_SAND);
				((Movable) newElement).setVerticalVelocity(((Movable) nextElement).getVerticalVelocity());
				parentMatrix.clearElement(this);
				return true;
			}
			if (nextElement instanceof Dirt) {
				Element newElement = parentMatrix.setNewElement(nextElement, ElementTypes.WET_DIRT);
				((Movable) newElement).setVerticalVelocity(((Movable) nextElement).getVerticalVelocity());
				parentMatrix.clearElement(this);
				return true;
			}

		}
		return false;
	}

}
