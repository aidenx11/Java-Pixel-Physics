package com.aidenx11.game.elements.movable.liquid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.immovable.Steel;
import com.aidenx11.game.elements.immovable.Stone;
import com.aidenx11.game.elements.immovable.Wood;
import com.aidenx11.game.elements.movable.movable_solid.Dirt;
import com.aidenx11.game.elements.movable.movable_solid.MovableSolid;
import com.aidenx11.game.elements.movable.movable_solid.Obsidian;
import com.aidenx11.game.elements.movable.movable_solid.Sand;
import com.aidenx11.game.elements.movable.movable_solid.WetDirt;
import com.aidenx11.game.elements.movable.movable_solid.WetSand;

public class Lava extends Liquid {

	public static ElementTypes type = ElementTypes.LAVA;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 4f;
	private float currentMaxSpeed;
	private static float density = 7f;
	private static int dispersionRate = 5;
	private static float chanceToMeltStone = 0.02f;
	private static float chanceToMeltObsidian = 0.01f;
	private float chanceToMeltSand = 0.03f;
	private static int meltingPoint = 150;
	private int numberOfMeltsToHarden = 2000;
	private float chanceToMeltDirt = 0.035f;
	private int heatTransferCoefficient = 1;
	private int colorIdx;
	private float idxDifference;

	public static float[] speeds = new float[] { (maxSpeed), (maxSpeed - maxSpeed / 11), (maxSpeed - maxSpeed / 10),
			(maxSpeed - maxSpeed / 9), (maxSpeed - maxSpeed / 8), (maxSpeed - maxSpeed / 7), (maxSpeed - maxSpeed / 6),
			(maxSpeed - maxSpeed / 5), (maxSpeed - maxSpeed / 4), (maxSpeed - maxSpeed / 3), (maxSpeed - maxSpeed / 2),
			(maxSpeed - maxSpeed) };

	public static int[][] lavaColorsRGB = new int[][] { { 236, 168, 61 }, { 236, 156, 61 }, { 236, 145, 61 },
			{ 236, 127, 61 }, { 236, 117, 61 }, { 236, 102, 61 }, { 236, 86, 61 }, { 226, 59, 45 }, { 209, 46, 34 },
			{ 176, 35, 23 }, { 151, 28, 18 }, { 117, 18, 38 } };

	public Lava(int row, int column) {
		super(type, row, column, new CustomColor(lavaColorsRGB[0]), false, 1, false, false, 0, true, 0.9f, acceleration,
				maxSpeed, density, false, dispersionRate, meltingPoint);
		super.setOnFire(true);
		colorIdx = -1;
		idxDifference = (float) numberOfMeltsToHarden / lavaColorsRGB.length;
		this.updateColor();
	}

	@Override
	public void update() {
		this.updateColor();
		this.actOnOther();
		this.distributeHeat();
		this.updateSpeed();
		this.updateMovementLogic();
	}

	public void updateSpeed() {
		if (this.getCurrentMaxSpeed() != speeds[colorIdx]) {
			this.setCurrentMaxSpeed(speeds[colorIdx]);
			super.setMaxSpeed(currentMaxSpeed);
		}
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1 = null;
			Element nextVertical2 = null;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() - randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical1 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() - randDirection * (i));
					break;
				}
			}

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() + randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical2 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() + randDirection * (i));
					break;
				}
			}

			if (nextVertical == null) {
				this.setVerticalVelocity(0.01f);
			}

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				if (nextVertical instanceof Liquid && !(nextVertical instanceof Lava)) {
					this.setVerticalVelocity(0.7f);
				}
				parentMatrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical2);
			} else {
				this.setVerticalVelocity(2.5f);
			}

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);
			nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());

			if (nextVertical == null) {
				return;
			}

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			if (sideways1 != null && sideways1.getDensity() < this.getDensity()
					&& ((nextVertical != null && nextVertical.getDensity() == this.getDensity())
							|| nextVertical == null)) {
				parentMatrix.swap(this, sideways1);
			} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()
					&& ((nextVertical != null && nextVertical.getDensity() == this.getDensity())
							|| nextVertical == null)) {
				parentMatrix.swap(this, sideways2);
			}
		}
	}

	public void updateColor() {
		int newIdx = lavaColorsRGB.length - 1;
		for (int i = 0; i < lavaColorsRGB.length; i++) {
			if (idxDifference * i <= numberOfMeltsToHarden) {
				newIdx = lavaColorsRGB.length - i - 1;
			}
		}
		if (newIdx != colorIdx) {
			colorIdx = newIdx;
			CustomColor color = new CustomColor(lavaColorsRGB[newIdx]);
			this.setColor(color.varyColor());
		}
	}

	public void distributeHeat() {
		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		Element nextElement;
		for (int i = 0; i < adjacentElements.length; i++) {
			nextElement = adjacentElements[i];
			if (nextElement instanceof Lava) {
				if (this.getTemperature() < nextElement.getTemperature()) {
					nextElement.setTemperature(nextElement.getTemperature() - heatTransferCoefficient);
					this.setTemperature(this.getTemperature() + heatTransferCoefficient);
				}
			}
		}
	}

	public void actOnOther() {

		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		Element newElement;

		for (int i = 0; i < shuffledElements.size(); i++) {

			if (getNumberOfMeltsToHarden() < 1) {
				parentMatrix.setNewElement(this, ElementTypes.OBSIDIAN);
				return;
			}

			if (shuffledElements.get(i) == null) {
				continue;
			}

			if (shuffledElements.get(i) instanceof Empty) {
				if (Math.random() < 0.0003) {
					parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.SMOKE);
				}
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				continue;
			} else if (shuffledElements.get(i) instanceof WetSand) {
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 50);
				parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.SAND);
				continue;
			} else if (shuffledElements.get(i) instanceof WetDirt) {
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 50);
				parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.DIRT);
				continue;
			} else if (shuffledElements.get(i) instanceof Water) {
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 10);
				if (Math.random() < 0.007) {
					parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.STEAM);
				}
				continue;
			} else if (shuffledElements.get(i) instanceof Steel) {
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				continue;
			} else if (shuffledElements.get(i) instanceof Wood) {
				if (Math.random() < 0.01) {
					newElement = parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 25);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				}
				continue;
			}
			if (shuffledElements.get(i) instanceof Obsidian) {
				if (this.numberOfMeltsToHarden <= 300) {
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				} else if (Math.random() < chanceToMeltObsidian) {
					newElement = parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 250);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				}
				continue;
			}
			if (shuffledElements.get(i) instanceof Stone) {
				if (this.numberOfMeltsToHarden <= 175) {
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				} else if (Math.random() < chanceToMeltStone) {
					newElement = parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 150);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				}
				continue;
			}
			if (shuffledElements.get(i) instanceof Sand) {
				if (this.numberOfMeltsToHarden <= 150) {
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				} else if (Math.random() < chanceToMeltSand) {
					newElement = parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 100);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				}
				continue;
			}
			if (shuffledElements.get(i) instanceof Dirt) {
				if (this.numberOfMeltsToHarden <= 150) {
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				} else if (Math.random() < chanceToMeltDirt) {
					newElement = parentMatrix.setNewElement(shuffledElements.get(i), ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 100);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				}
				continue;
			} else if (Math.random() < 0.03) {
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
			}
		}
	}

	public int getNumberOfMeltsToHarden() {
		return numberOfMeltsToHarden;
	}

	public void setNumberOfMeltsToHarden(int numberOfMeltsToHarden) {
		this.numberOfMeltsToHarden = numberOfMeltsToHarden;
	}

	public float getCurrentMaxSpeed() {
		return currentMaxSpeed;
	}

	public void setCurrentMaxSpeed(float currentMaxSpeed) {
		this.currentMaxSpeed = currentMaxSpeed;
	}

}
