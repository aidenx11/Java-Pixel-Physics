package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends Element {
	
	public static ElementTypes type = ElementTypes.SAND;
	private int velocity = 1;
	private int ACCELERATION = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private int MAX_SPEED = 5;
	
	public Sand(int row, int column, boolean isEmpty) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), isEmpty);
		super.setMovable(true);
		super.setModified(true);
	}
	
	public Sand(int row, int column, boolean isEmpty, boolean rainbow) {
		super(row, column, new CustomColor(ColorManager.generateRainbowColor()), isEmpty);
		super.setMovable(true);
		super.setModified(true);
	}

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), false);
		super.setMovable(true);
		super.setModified(true);
	}
	
	public Sand(int row, int column, boolean isEmpty, int r, int g, int b) {
		super(row, column, new CustomColor(r, g, b), isEmpty);
		super.setMovable(true);
		super.setModified(true);
	}

	@Override
	public ElementTypes getType() {
		return type;
	}
	
	
	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public void updateVelocity() {
		int newVelocity = getVelocity() + ACCELERATION;
		
		if (Math.abs(newVelocity) > MAX_SPEED) {
			newVelocity = (int) (Math.signum(newVelocity) * MAX_SPEED);
		}
		
		setVelocity(newVelocity);
		
	}
	
	public void resetVelocity() {
		setVelocity(0);
	}
	
	public int getUpdateCount() {
		int abs = Math.abs(getVelocity());
		int floored = (int) Math.floor(abs);
		int mod = abs - floored;
		
		return floored + (Math.random() < mod ? 1 : 0);
	}

}
