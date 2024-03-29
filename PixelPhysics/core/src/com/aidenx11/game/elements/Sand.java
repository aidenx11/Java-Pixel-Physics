package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends Element {

	public static ElementTypes type = ElementTypes.SAND;
	private float velocity = 0.1f;
	private float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private float maxSpeed = 7f;
	private float density = 5f;
	private boolean wet = false;

	public static CellularMatrix matrix = pixelPhysicsGame.matrix;

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR, true), false, type);
		super.setMovable(true);
		super.setModified(true);
		super.setMovesDown(true);
		super.setDensity(density);
		super.setMovesSideways(false);
		super.setLimitedLife(false);
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float newVelocity) {
		this.velocity = newVelocity;
	}
	
	public void resetVelocity() {
		setVelocity(0);
	}

	@Override
	public float getMaxSpeed() {
		return maxSpeed;
	}

	@Override
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public float getAcceleration() {
		return acceleration;
	}

	@Override
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public boolean isFlammable() {
		return false;
	}

	@Override
	public boolean burnsThings() {
		return false;
	}

	@Override
	public boolean extinguishesThings() {
		if (Math.random() < 0.1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float getChanceToCatch() {
		return 0;
	}

	@Override
	public boolean isWet() {
		return wet;
	}

	public void setWet(boolean wet) {
		this.wet = wet;
	}

	@Override
	public ElementTypes getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
