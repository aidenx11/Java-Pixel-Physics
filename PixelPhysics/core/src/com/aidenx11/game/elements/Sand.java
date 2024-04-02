package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends MovableSolid {

	public static ElementTypes type = ElementTypes.SAND;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 10f;
	private static float density = 7f;
	private static float inertialResistance = 0.01f;
	private static float friction = 0.09f;

	public Sand(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.SAND_COLOR, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction);
		super.setFreeFalling(true);
	}

	@Override
	public void update() {
		this.actOnOther();
		super.updateMovementLogic();
	}

	public void actOnOther() {

		Element left = parentMatrix.getElement(getRow(), getColumn() - 1);
		Element right = parentMatrix.getElement(getRow(), getColumn() + 1);
		Element downLeft = parentMatrix.getElement(getRow() - 1, getColumn() - 1);
		Element downRight = parentMatrix.getElement(getRow() - 1, getColumn() + 1);
		Element down = parentMatrix.getElement(getRow() - 1, getColumn());
		Element above = parentMatrix.getElement(getRow() + 1, getColumn());
		Element aboveLeft = parentMatrix.getElement(getRow() + 1, getColumn() - 1);
		Element aboveRight = parentMatrix.getElement(getRow() + 1, getColumn() + 1);

		if (down instanceof Water) {
			parentMatrix.setNewElement(down, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (downRight instanceof Water) {
			parentMatrix.setNewElement(downRight, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (downLeft instanceof Water) {
			parentMatrix.setNewElement(downLeft, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (left instanceof Water) {
			parentMatrix.setNewElement(left, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (right instanceof Water) {
			parentMatrix.setNewElement(right, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (above instanceof Water) {
			parentMatrix.setNewElement(above, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (aboveLeft instanceof Water) {
			parentMatrix.setNewElement(aboveLeft, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		} else if (aboveRight instanceof Water) {
			parentMatrix.setNewElement(aboveRight, ElementTypes.WET_SAND);
			parentMatrix.clearElement(this);
		}
	}

}
