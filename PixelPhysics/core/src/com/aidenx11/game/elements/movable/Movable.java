package com.aidenx11.game.elements.movable;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.movable.liquid.Water;
import com.aidenx11.game.elements.movable.movable_solid.MovableSolid;

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

	/**
	 * Whether or not this element moved last frame. Used to skip calculations if
	 * the element has not moved.
	 */
	private boolean movedLastFrame = true;

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
	}

	@Override
	public void update() {
		this.updateMovementLogic();
		super.updateElementLife();
		super.updateBurningLogic();
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

		return floored + (Math.random() < mod ? 1 : 0);
	}

	public void updateVerticalVelocity() {

		float newVelocity = this.getVerticalVelocity() + this.getAcceleration();

		if (Math.abs(newVelocity) > this.getMaxSpeed()) {
			newVelocity = Math.signum(newVelocity) * this.getMaxSpeed();
		}

		this.setVerticalVelocity(newVelocity);

		if (parentMatrix.getElement(this.getRow() - 1, this.getColumn()) instanceof Water
				&& this.getVerticalVelocity() > 0.7f) {
			this.setVerticalVelocity(0.7f);
		}

	}

	public void updateHorizontalVelocity() {
		float newAbsVelocity = Math.abs(getHorizontalVelocity()) - this.getFriction();

		if (newAbsVelocity < 0) {
			newAbsVelocity = 0;
		}

		this.setHorizontalVelocity(newAbsVelocity * getDirection());
	}

	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1;
			Element nextVertical2;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			nextVertical1 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() - randDirection);
			nextVertical2 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() + randDirection);

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity()
					&& this.isFreeFalling()) {
				parentMatrix.swap(this, nextVertical2);
			} else {
				this.setVerticalVelocity(0f);
			}

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			if (this.movesSideways()) {

				sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
				sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

				if (sideways1 != null && sideways1.getDensity() < this.getDensity()) {
					parentMatrix.swap(this, sideways1);
				} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()) {
					parentMatrix.swap(this, sideways2);
				}
			}

		}
	}

	public boolean setElementFreeFalling(MovableSolid sideways1) {
		if (Math.random() > sideways1.getInertialResistance()) {
			sideways1.setFreeFalling(true);
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
		if (!(this instanceof MovableSolid)) {
			return true;
		}
		return isFreeFalling;
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

	public boolean MovedLastFrame() {
		return movedLastFrame;
	}

	public void setMovedLastFrame(boolean movedLastFrame) {
		this.movedLastFrame = movedLastFrame;
	}

}
