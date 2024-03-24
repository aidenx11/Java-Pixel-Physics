package com.aidenx11.game.elements;

public class Empty extends Element {

	public static ElementTypes type = ElementTypes.EMPTY;
	private float density = -1;

	public Empty(int row, int column) {
		super(row, column, null, true);
		super.setMovable(false);
		super.setDensity(density);
		super.setLimitedLife(false);
	}

	@Override
	public ElementTypes getType() {
		return type;
	}

	@Override
	public void resetVelocity() {
	}

	@Override
	public void setVelocity(float f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxSpeed(float maxSpeed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAcceleration(float acceleration) {
		// TODO Auto-generated method stub
		
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
