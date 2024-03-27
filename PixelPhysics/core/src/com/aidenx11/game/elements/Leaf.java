package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Leaf extends Element {
	
	public static ElementTypes type = ElementTypes.LEAF;
	private float chanceToCatch = 0.03f;
	private int burningTime = 50;

	public Leaf(int row, int column) {
		super(row, column, new CustomColor(ColorValues.LEAF), false, type);
		super.setMovable(false);
		super.setLimitedLife(false);
		super.setLifetime(burningTime);
	}

	@Override
	public void resetVelocity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVelocity(float f) {
		// TODO Auto-generated method stub
		
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
		return true;
	}

	@Override
	public boolean burnsThings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean extinguishesThings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getChanceToCatch() {
		// TODO Auto-generated method stub
		return chanceToCatch;
	}

}
