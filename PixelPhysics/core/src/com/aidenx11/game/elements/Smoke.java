package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Smoke extends Element {
	
	public static ElementTypes type = ElementTypes.SMOKE;
	private float velocity = 0f;
	private float acceleration = -0.1f;
	private float maxSpeed = 0.35f;
	private float density = 1f;
	
	private boolean flickers = Math.random() < 0.03;
	
	static CustomColor[] fireColors = new CustomColor[] { new CustomColor(253, 207, 88), new CustomColor(242, 125, 12),
			new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	public Smoke(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SMOKE, true), false, type);
		super.setMovable(true);
		super.setMovesDown(false);
		super.setDensity(density);
		super.setMovesSideways(true);
		super.setLimitedLife(true);
		super.setLifetime(200 + (int) (300 * Math.random()));
	}
	
	public void flicker() {
		this.setColor(fireColors[(int) Math.round(Math.random() * 3)]);
		parentMatrix.setFramesSinceLastModifiedElement(0);
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

	@Override
	public boolean isFlammable() {
		return false;
	}

	public boolean flickers() {
		return flickers;
	}
	
	@Override
	public boolean burnsThings() {
		if (flickers() && Math.random() < 0.8) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean extinguishesThings() {
		return false;
	}

	@Override
	public float getChanceToCatch() {
		return 0;
	}
}
