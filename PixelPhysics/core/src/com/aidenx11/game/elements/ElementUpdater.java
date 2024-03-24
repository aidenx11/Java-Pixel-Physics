package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element.ElementTypes;

public class ElementUpdater {

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

	public static void setNewElement(Element element, ElementTypes newElement, CellularMatrix matrix) {
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
		}
	}

	public static void updateElementLife(Element element, CellularMatrix matrix) {

		if (element.getLifetime() < 0) {
			return;
		}
		if (element.getLifetime() == 0) {
			switch (element.getType()) {
			case SMOKE:
				setNewElement(element, ElementTypes.EMPTY, matrix);
				break;
			case FIRE:
				setNewElement(element, ElementTypes.SMOKE, matrix);
				break;
			default:
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

	public static void updateBurningLogic(Element element, CellularMatrix matrix) {
		if (element.isFlammable()) {
			int numberOfFire = 0;
			Element otherElement = matrix.getElement(element.getRow() + 1, element.getColumn());
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() + 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() - 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow(), element.getColumn() - 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow(), element.getColumn() + 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow() - 1, element.getColumn());
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() + 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}
			otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() - 1);
			if (otherElement != null && otherElement.burnsThings()) {
				numberOfFire++;
			}

			float chanceToCatch = ((Wood) element).getChanceToCatch() * numberOfFire;

			if (Math.random() < chanceToCatch) {
				setNewElement(element, ElementTypes.FIRE, matrix);
			}
		}
	}

	public static void updateMovementLogic(Element element, CellularMatrix matrix) {
		updateVelocity(element);

		for (int v = 0; v < getUpdateCount(element); v++) {

			int delta = (int) Math.signum(element.getVelocity());
			Element nextVertical = matrix.getElement(element.getRow() - delta, element.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element nextVertical1 = matrix.getElement(element.getRow() - delta, element.getColumn() - randDirection);
			Element nextVertical2 = matrix.getElement(element.getRow() - delta, element.getColumn() + randDirection);

			Element sideways1 = matrix.getElement(element.getRow(), element.getColumn() - randDirection);
			Element sideways2 = matrix.getElement(element.getRow(), element.getColumn() + randDirection);

			if (nextVertical != null && nextVertical.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical2);
			} else if (sideways1 != null && element.movesSideways() && sideways1.getDensity() < element.getDensity()) {
				matrix.swap(element, sideways1);
			} else if (sideways2 != null && element.movesSideways() && sideways2.getDensity() < element.getDensity()) {
				matrix.swap(element, sideways2);
			} else {
				element.setVelocity(0);
			}

		}
	}

	public static void update(Element element, CellularMatrix matrix) {

		updateMovementLogic(element, matrix);
		element.setModified(element.getVelocity() != 0);
		updateElementLife(element, matrix);
		updateBurningLogic(element, matrix);

	}
}
