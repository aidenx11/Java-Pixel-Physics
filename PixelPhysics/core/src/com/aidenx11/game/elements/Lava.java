package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Lava extends Liquid {

	public static ElementTypes type = ElementTypes.LAVA;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION - 0.07f;
	private static float maxSpeed = 3f;
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
		this.actOnOther();
		this.distributeHeat();
		this.updateColor();
		this.updateMovementLogic();
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
				if (!(nextVertical instanceof Empty) && !(nextVertical instanceof Lava)
						&& !(nextVertical instanceof Gas)) {
					this.setVerticalVelocity(0.01f);
				}
				parentMatrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical2);
			} else {
				this.setVerticalVelocity(2f);
			}

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

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
		int newIdx = 0;
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
		Element newElement;

		for (int i = 0; i < adjacentElements.length; i++) {

			if (getNumberOfMeltsToHarden() < 1) {
				parentMatrix.setNewElement(this, ElementTypes.OBSIDIAN);
				return;
			}
			if (adjacentElements[i] instanceof Empty) {
				if (Math.random() < 0.0003) {
					parentMatrix.setNewElement(adjacentElements[i], ElementTypes.SMOKE);

				}
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				return;
			}
			if (adjacentElements[i] instanceof Obsidian) {
				if (this.numberOfMeltsToHarden <= 300) {
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 1);
				} else if (Math.random() < chanceToMeltObsidian) {
					newElement = parentMatrix.setNewElement(adjacentElements[i], ElementTypes.LAVA);
					setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 250);
					((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());

				}
				return;
			}
			if (adjacentElements[i] instanceof WetSand) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.SAND);
				parentMatrix.setNewElement(this, ElementTypes.OBSIDIAN);
				return;
			}
			if (adjacentElements[i] instanceof WetDirt) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.DIRT);
				parentMatrix.setNewElement(this, ElementTypes.OBSIDIAN);
				return;
			}
			if (adjacentElements[i] instanceof Water) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.STEAM);
				parentMatrix.setNewElement(this, ElementTypes.OBSIDIAN);
				return;
			} else if (adjacentElements[i] instanceof Stone && Math.random() < chanceToMeltStone) {
				newElement = parentMatrix.setNewElement(adjacentElements[i], ElementTypes.LAVA);
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 150);
				((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				return;
			} else if (adjacentElements[i] instanceof Sand && Math.random() < chanceToMeltSand) {
				newElement = parentMatrix.setNewElement(adjacentElements[i], ElementTypes.LAVA);
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 120);
				((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				return;
			} else if (adjacentElements[i] instanceof Dirt && Math.random() < chanceToMeltDirt) {
				newElement = parentMatrix.setNewElement(adjacentElements[i], ElementTypes.LAVA);
				setNumberOfMeltsToHarden(getNumberOfMeltsToHarden() - 100);
				((Lava) newElement).setNumberOfMeltsToHarden(this.getNumberOfMeltsToHarden());
				return;
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

}
