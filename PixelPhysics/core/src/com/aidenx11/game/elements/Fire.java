package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Fire extends Element {

	ElementTypes type = ElementTypes.FIRE;
	int maxLife = 75 + (int) (75 * Math.random());

	static CustomColor[] colors = new CustomColor[] { new CustomColor(253, 207, 88), new CustomColor(242, 125, 12),
			new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	public Fire(int row, int column) {
		super(row, column, new CustomColor(ColorValues.FIRE), false);
		super.setMovable(false);
		super.setLimitedLife(true);
		super.setLifetime(maxLife);
	}

	public void flicker() {
		this.setColor(colors[(int) Math.round(Math.random() * 3)]);
		parentMatrix.setModifiedElements(true);
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
	}

	@Override
	public float getMaxSpeed() {
		return 0;
	}

	@Override
	public void setMaxSpeed(float maxSpeed) {
	}

	@Override
	public float getAcceleration() {
		return 0;
	}

	@Override
	public void setAcceleration(float acceleration) {
	}

	@Override
	public boolean isFlammable() {
		return false;
	}
	
	@Override
	public boolean burnsThings() {
		return true;
	}

}
