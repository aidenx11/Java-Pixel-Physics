package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.*;

public class Sand extends Element {

	public static ElementTypes type = ElementTypes.SAND;
	private float velocity = 1f;
	private float ACCELERATION = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private float MAX_SPEED = 5f;

	public static CellularMatrix matrix = pixelPhysicsGame.matrix;

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), false);
		super.setMovable(true);
		super.setModified(true);
	}

	public Sand(int row, int column, boolean isEmpty, boolean rainbow) {
		super(row, column, new CustomColor(ColorManager.generateRainbowColor()), isEmpty);
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

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float newVelocity) {
		this.velocity = newVelocity;
	}
	
	public void resetVelocity() {
		setVelocity(0);
	}

	public void updateVelocity() {
		float newVelocity = getVelocity() + ACCELERATION;

		if (Math.abs(newVelocity) > MAX_SPEED) {
			newVelocity = Math.signum(newVelocity) * MAX_SPEED;
		}

		setVelocity(newVelocity);

	}

	public int getUpdateCount() {
		float abs = Math.abs(getVelocity());
		int floored = (int) Math.floor(abs);
		float mod = abs - floored;

		return floored + (Math.random() < mod ? 1 : 0);
	}

	public void update() {
		this.updateVelocity();

		for (int v = 0; v < this.getUpdateCount(); v++) {

			Element below = matrix.getElement(this.getRow() - 1, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element below1 = matrix.getElement(this.getRow() - 1, this.getColumn() - randDirection);
			Element below2 = matrix.getElement(this.getRow() - 1, this.getColumn() + randDirection);

			if (below instanceof Empty) {
				matrix.swap(this, below);
			} else if (below1 instanceof Empty) {
				matrix.swap(this, below1);
				this.setVelocity((float) (this.getVelocity() - 0.1));
			} else if (below2 instanceof Empty) {
				matrix.swap(this, below2);
				this.setVelocity((float) (this.getVelocity() - 0.1));
			} else {
				this.setVelocity(0);
			}
		}

		this.setModified(this.getVelocity() != 0);
	}

}
