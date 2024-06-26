package com.aidenx11.JavaPixelPhysics.elements.movable;

import com.aidenx11.JavaPixelPhysics.CellularMatrix;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.elements.Element;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Gas;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Lava;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Liquid;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Water;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.MovableSolid;

/**
 * Superclass of all Movable elements in the simulation.
 * 
 * Includes fields to manage density, vertical and horizontal velocity,
 * acceleration, max speed, inertial resistance, friction, and direction, as
 * well as flags for whether or not the element is free falling and whether or
 * not the element moved last frame.
 */
public abstract class Movable extends Element {

	/** Density of this element */
	private int density;

	/** Vertical velocity of this element */
	private float verticalVelocity;

	/** Horizontal velocity of this element */
	private float horizontalVelocity;

	/** Acceleration of this element */
	private float acceleration;

	/** Maximum speed of this element */
	private float maxSpeed;

	protected boolean movedLastFrame = false;

	/**
	 * Inertial resistance of this element in the range 0-1 with 0 being the least
	 * resistance, and 1 being the most. High inertial resistance will cause an
	 * element to resist being set into a free falling state by other elements more.
	 */
	private float inertialResistance;

	/**
	 * Friction of this element in the range 0-1 with 0 being no friction, and 1
	 * being maximum.
	 */
	private float friction;

	/**
	 * Direction this element is moving. -1 is left, 1 is right. 0 means it moves
	 * neither direction
	 */
	private int direction;

	/**
	 * Whether or not this element moves sideways. For example, Water moves sideways
	 * because even if it is 'settled', it can move left and right. Sand, for
	 * example, does not move sideways.
	 */
	private boolean movesSideways;

	/**
	 * Whether or not this element is free falling. Used mainly along with inertial
	 * resistance to imitate a basic concept of inertia in movable elements
	 */
	private boolean isFreeFalling;

	protected int previousRow;
	protected int previousCol;

	public Movable(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, int density, boolean movesSideways, boolean movesDown, float friction,
			int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, movesDown,
				temperature);
		setVerticalVelocity(velocity);
		setAcceleration(acceleration);
		setMaxSpeed(maxSpeed);
		setDensity(density);
		setMovesSideways(movesSideways);
		setFriction(friction);
		super.setFallingThroughAir(true);
		this.movedLastFrame = true;
		CellularMatrix.activateChunk(row, column);
	}

	public abstract void updateMovementLogic();

	@Override
	public void update() {
		if (this instanceof Gas) {
			CellularMatrix.activateChunk(getRow(), getColumn());
		}
		if ((this instanceof Liquid || this instanceof Lava)
				&& PixelPhysicsGame.matrix.getElement(getRow() + 1, getColumn(), true, false) instanceof Empty) {
			CellularMatrix.activateChunk(getRow(), getColumn());
		}
		if (this instanceof MovableSolid
				&& PixelPhysicsGame.matrix.getElement(getRow() + 1, getColumn(), true, false) instanceof Liquid
				&& PixelPhysicsGame.matrix.getElement(getRow() - 1, getColumn(), true, false) instanceof Liquid) {
			CellularMatrix.activateChunk(getRow(), getColumn());
		}
		if (PixelPhysicsGame.matrix.getElement(this.getRow() - 1, this.getColumn(), true, false) instanceof Empty) {
			CellularMatrix.activateChunk(getRow(), getColumn());
		}
		if (CellularMatrix.getChunk(getRow(), getColumn()).activeThisFrame) {
			this.updateMovementLogic();
			if (this.limitedLife()) {
				super.updateElementLife();
			}
			if (this.isFlammable()) {
				super.updateBurningLogic();
			}
		}
	}

	public int getVerticalUpdateCount() {
		float abs = Math.abs(getVerticalVelocity());
		int floored = (int) Math.floor(abs);
		float mod = abs - floored;

		return floored + (Math.random() < mod ? 1 : 0);
	}

	public int getHorizontalUpdateCount() {
		float abs = Math.abs(getHorizontalVelocity());
		int floored = (int) Math.floor(abs);
		float mod = abs - floored;

		if (floored > 0) {
			return floored + (Math.random() < mod ? 1 : 0);
		} else {
			return 0;
		}
	}

	public void updateVerticalVelocity() {

		float newVelocity = this.getVerticalVelocity() + this.getAcceleration();

		if (Math.abs(newVelocity) > this.getMaxSpeed()) {
			newVelocity = Math.signum(newVelocity) * this.getMaxSpeed();
		}

		this.setVerticalVelocity(newVelocity);

		if (this.getRow() > 0 && PixelPhysicsGame.matrix.getElement(this.getRow() - 1, this.getColumn(), false,
				false) instanceof Water && this.getVerticalVelocity() > 0.7f) {
			this.setVerticalVelocity(this.getVerticalVelocity() - 0.1f);

		}

	}

	public void updateHorizontalVelocity() {
		float newAbsVelocity = Math.abs(getHorizontalVelocity()) * (1f - this.getFriction());

		if (newAbsVelocity < 0) {
			newAbsVelocity = 0;
		}

		this.setHorizontalVelocity(newAbsVelocity * getDirection());
	}

	public boolean setElementFreeFalling(MovableSolid sideways1) {
		if (Math.random() > sideways1.getInertialResistance()) {
			sideways1.setFreeFalling(true);
			sideways1.movedLastFrame = true;
//			CellularMatrix.activateChunk(sideways1.getRow(), sideways1.getColumn());
			return true;
		}
		return false;
	}

	public float getInertialResistance() {
		return inertialResistance;
	}

	public void setInertialResistance(float inertialResistance) {
		this.inertialResistance = inertialResistance;
	}

	public boolean isFreeFalling() {
		return isFreeFalling || !(this instanceof MovableSolid);
	}

	public void setFreeFalling(boolean isFreeFalling) {
		if (this instanceof MovableSolid) {
			this.isFreeFalling = isFreeFalling;
		} else {
			this.isFreeFalling = true;
		}
	}

	public float getVerticalVelocity() {
		return verticalVelocity;
	}

	public void resetVelocity() {
		setVerticalVelocity(0);
	}

	public void setVerticalVelocity(float velocity) {
		this.verticalVelocity = velocity;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public boolean movesSideways() {
		return movesSideways;
	}

	public void setMovesSideways(boolean movesSideways) {
		this.movesSideways = movesSideways;
	}

	public float getHorizontalVelocity() {
		return horizontalVelocity;
	}

	public void setHorizontalVelocity(float horizontalVelocity) {
		this.horizontalVelocity = horizontalVelocity;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
