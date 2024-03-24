package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Wood extends Element {
	
	public static ElementTypes type = ElementTypes.WOOD;
	private float chanceToCatch = 0.01f;
	
	public Wood(int row, int column, boolean isEmpty) {
		super(row, column, new CustomColor(ColorValues.WOOD_COLOR), isEmpty);
		super.setMovable(false);
		super.setLimitedLife(false);
	}

	public Wood(int row, int column) {
		super(row, column, new CustomColor(ColorValues.WOOD_COLOR), false);
		super.setMovable(false);
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

	public float getChanceToCatch() {
		return chanceToCatch;
	}

	public void setChanceToCatch(float chanceToCatch) {
		this.chanceToCatch = chanceToCatch;
	}

	@Override
	public boolean isFlammable() {
		return true;
	}

	@Override
	public boolean burnsThings() {
		return false;
	}
	
}
