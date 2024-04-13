package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Dirt extends MovableSolid {

	public static ElementTypes type = ElementTypes.DIRT;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 10f;
	private static int density = 7;
	private static float inertialResistance = 0.5f;
	private static float friction = 0.4f;
	private static int meltingPoint = -1;

	public Dirt(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WET_DIRT, true), false, -1, false, Math.random() < 0.8, 0,
				false, 0, acceleration, maxSpeed, density, false, inertialResistance, friction, meltingPoint);
		super.setFreeFalling(true);
	}

//	@Override
//	public void update() {
//		if (!actOnOther()) {
//			super.update();
//		}
//	}
//
//	public boolean actOnOther() {
//
//		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
//
//		for (int i = 0; i < adjacentElements.length; i++) {
//			if (adjacentElements[i] instanceof Water) {
//				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.WET_DIRT);
//				parentMatrix.clearElement(this);
//				return true;
//			}
//		}
//		return false;
//	}

}
