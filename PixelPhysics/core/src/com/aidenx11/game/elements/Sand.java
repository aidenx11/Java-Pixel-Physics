package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends MovableSolid {

	public static ElementTypes type = ElementTypes.SAND;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 5f;
	private static float density = 7f;
	private static float inertialResistance = 0.01f;
	private static float friction = 0.09f;

	public Sand(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.SAND_COLOR, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}

	@Override
	public void update() {
		this.actOnOther();
		super.updateMovementLogic();
	}

	public void actOnOther() {
		
		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		
		for (int i = 0; i < adjacentElements.length; i++) {
			if (adjacentElements[i] instanceof Water) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.WET_SAND);
				parentMatrix.clearElement(this);
				return;
			}
		}
	}

}
