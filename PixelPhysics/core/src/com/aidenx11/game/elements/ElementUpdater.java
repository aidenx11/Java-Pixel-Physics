package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element.ElementTypes;

public class ElementUpdater {

	public static CellularMatrix matrix = pixelPhysicsGame.matrix;

	public static void updateVelocity(Element element) {
		float newVelocity = element.getVelocity() + element.getAcceleration();

		if (Math.abs(newVelocity) > element.getMaxSpeed()) {
			newVelocity = Math.signum(newVelocity) * element.getMaxSpeed();
		}

		element.setVelocity(newVelocity);

	}

	public static int getUpdateCount(Element element) {
		float abs = Math.abs(element.getVelocity());
		int floored = (int) Math.floor(abs);
		float mod = abs - floored;

		return floored + (Math.random() < mod ? 1 : 0);
	}

	public static void setNewElement(Element element, ElementTypes newElement) {
		switch (newElement) {
		case EMPTY:
			matrix.setElement(new Empty(element.getRow(), element.getColumn()));
			break;
		case FIRE:
			matrix.setElement(new Fire(element.getRow(), element.getColumn()));
			break;
		case SAND:
			matrix.setElement(new Sand(element.getRow(), element.getColumn()));
			break;
		case SMOKE:
			matrix.setElement(new Smoke(element.getRow(), element.getColumn()));
			break;
		case WOOD:
			matrix.setElement(new Wood(element.getRow(), element.getColumn()));
			break;
		case WATER:
			matrix.setElement(new Water(element.getRow(), element.getColumn()));
			break;
		default:
			break;
		}
	}

	private static void findAndSwapNextEmptyElement(Element element) {
		boolean blocked1 = false;
		boolean blocked2 = false;
		for (int i = 0; i < 30; i++) {
			int randDirection = Math.random() < 0.5 ? i : -i;
			Element element1 = matrix.getElement(element.getRow(), element.getColumn() - randDirection);
			Element element2 = matrix.getElement(element.getRow(), element.getColumn() + randDirection);
			if (!(element1 instanceof Empty) || !(element1 instanceof Water)) {
				blocked1 = true;
			}
			if (!(element2 instanceof Empty) || !(element2 instanceof Water)) {
				blocked2 = true;
			}
			if (element1 instanceof Empty && !blocked1) {
				matrix.swap(element, element1);
				return;
			} else if (element2 instanceof Empty && !blocked2) {
				matrix.swap(element, element2);
				return;
			}
		}
	}

	public static void updateElementLife(Element element) {
		if (element.getLifetime() == 0) {
			switch (element.getType()) {
			case SMOKE:
				setNewElement(element, ElementTypes.EMPTY);
				break;
			case FIRE:
				setNewElement(element, ElementTypes.SMOKE);
				break;
			default:
				setNewElement(element, ElementTypes.EMPTY);
				break;
			}
		}

		if ((element instanceof Fire || (element instanceof Smoke && ((Smoke) element).flickers()))
				&& Math.random() < 0.1) {
			element.flicker();
		}

		int lifetime = element.getLifetime();

		element.setLifetime(lifetime - 1);
	}

	public static void updateBurningLogic(Element element) {
		boolean extinguished = false;
		int numberOfFire = 0;
		Element otherElement = matrix.getElement(element.getRow() + 1, element.getColumn());
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow(), element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow(), element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn());
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			extinguished = true;
		}

		float chanceToCatch = element.getChanceToCatch() * numberOfFire;
		if (Math.random() < chanceToCatch) {
			setNewElement(element, ElementTypes.FIRE);
		}

		if (extinguished) {
			if (element instanceof Fire) {
				setNewElement(element, ElementTypes.SMOKE);
			}
		}
	}

	public static void checkWetness(Element element1, Element element2) {
		if (element1 == null || element2 == null) {
			return;
		}
		if ((element1 instanceof Sand && element2 instanceof Water)
				|| (element2 instanceof Sand && element1 instanceof Water)) {
			if (element1 instanceof Sand && element1.getDensity() != 6f) {
				element1.setColor(new CustomColor(ColorValues.WET_SAND));
				element1.setDensity(6f);
				element2.setLimitedLife(true);
				element2.setLifetime(0);
			} else if (element2.getDensity() != 6f) {
				element2.setColor(new CustomColor(ColorValues.WET_SAND));
				element2.setDensity(6f);
				element1.setLimitedLife(true);
				element1.setLifetime(0);
			}
		}
	}

	public static void updateMovementLogic(Element element) {
		updateVelocity(element);

		for (int v = 0; v < getUpdateCount(element); v++) {

			int delta = (int) Math.signum(element.getVelocity());
			Element nextVertical = matrix.getElement(element.getRow() - delta, element.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element nextVertical1 = matrix.getElement(element.getRow() - delta, element.getColumn() - randDirection);
			Element nextVertical2 = matrix.getElement(element.getRow() - delta, element.getColumn() + randDirection);

			checkWetness(element, nextVertical);
			checkWetness(element, nextVertical1);
			checkWetness(element, nextVertical2);

			if (nextVertical != null && nextVertical.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical2);
			} else {
				element.setVelocity(0.5f);
			}

			if (element.movesSideways()) {
				if (element instanceof Water) {
					findAndSwapNextEmptyElement(element);
				}
				Element sideways1 = matrix.getElement(element.getRow(), element.getColumn() - randDirection);
				Element sideways2 = matrix.getElement(element.getRow(), element.getColumn() + randDirection);

				if (sideways1 != null && sideways1.getDensity() < element.getDensity()) {
					matrix.swap(element, sideways1);
				} else if (sideways2 != null && sideways2.getDensity() < element.getDensity()) {
					matrix.swap(element, sideways2);
				}
			}

		}
	}

	public static void update(Element element) {
		if (element.isMovable()) {
			updateMovementLogic(element);
		}
		element.setModified(element.getVelocity() != 0 || element.limitedLife());
		if (element.limitedLife()) {
			updateElementLife(element);
		}
		if (element.isFlammable()) {
			updateBurningLogic(element);
		}

	}
}
