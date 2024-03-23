package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Smoke extends Element {
	
	public static ElementTypes type = ElementTypes.SMOKE;
	private float velocity = 0f;
	private float acceleration = -0.05f;
	private float maxSpeed = 0.2f;
	private float density = 1;

	public Smoke(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SMOKE), false);
		super.setMovable(true);
		super.setMovesDown(false);
		super.setDensity(density);
		super.setMovesSideways(true);
		super.setLimitedLife(true);
		super.setLifetime(200 + (int) (300 * Math.random()));
	}

	@Override
	public ElementTypes getType() {
		return type;
	}

	@Override
	public void resetVelocity() {
		setVelocity(0);
	}

	@Override
	public void setVelocity(float velocity) {
		this.velocity = velocity;

	}

	@Override
	public float getVelocity() {
		return velocity;
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

}
