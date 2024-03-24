package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends Element {

	public static ElementTypes type = ElementTypes.SAND;
	private float velocity = 0.2f;
	private float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private float maxSpeed = 7f;
	private float density = 5;

	public static CellularMatrix matrix = pixelPhysicsGame.matrix;

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), false);
		super.setMovable(true);
		super.setModified(true);
		super.setMovesDown(true);
		super.setDensity(density);
		super.setMovesSideways(false);
		super.setLimitedLife(false);
	}

	public Sand(int row, int column, boolean isEmpty, boolean rainbow) {
		super(row, column, new CustomColor(ColorManager.generateRainbowColor()), isEmpty);
		super.setMovable(true);
		super.setModified(true);
		super.setMovesDown(true);
		super.setDensity(density);
		super.setMovesSideways(false);
		super.setLimitedLife(false);
	}

	public Sand(int row, int column, boolean isEmpty, int r, int g, int b) {
		super(row, column, new CustomColor(r, g, b), isEmpty);
		super.setMovable(true);
		super.setModified(true);
		super.setMovesDown(true);
		super.setDensity(density);
		super.setMovesSideways(false);
		super.setLimitedLife(false);
	}

	@Override
	public ElementTypes getType() {
		return type;
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
	
}
