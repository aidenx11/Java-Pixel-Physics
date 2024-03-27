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
		if (element instanceof WetSand) {
			if (element.adjacentTo(ElementTypes.EMPTY) > 0) {
				element.setMaxSpeed(7f);
				element.setVelocity(4f);
				element.setMovesSideways(false);
			} else if (matrix.getElement(element.getRow() - 1, element.getColumn()) instanceof Water) {
				element.setMaxSpeed(7f);
				element.setVelocity(4f);
				element.setMovesSideways(true);
			} else if (element.adjacentTo(ElementTypes.WATER) > 4) {
				element.setMaxSpeed(3f);
				element.setVelocity(2f);
				element.setMovesSideways(true);
			} else if (element.adjacentTo(ElementTypes.WATER) > 3) {
				element.setMaxSpeed(0.1f);
				element.setVelocity(0.1f);
				element.setMovesSideways(false);
			} else {
				element.setMaxSpeed(0.05f);
				if (element.adjacentTo(ElementTypes.WET_SAND) > 1) {
					element.setMovesSideways(false);
				} else {
					element.setMovesSideways(true);
				}
			}
		}

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
		case STEAM:
			matrix.setElement(new Steam(element.getRow(), element.getColumn()));
			break;
		case WET_SAND:
			matrix.setElement(new WetSand(element.getRow(), element.getColumn()));
			break;
		case LEAF:
			matrix.setElement(new Leaf(element.getRow(), element.getColumn()));
			break;
		default:
			break;
		}
	}

	private static void findAndSwapNextEmptyElement(Element element) {
		boolean blocked1 = false;
		boolean blocked2 = false;
		for (int i = 0; i < 70; i++) {
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
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow() + 1, element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow(), element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow(), element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn());
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() + 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = matrix.getElement(element.getRow() - 1, element.getColumn() - 1);
		if (otherElement != null && otherElement.burnsThings()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (element instanceof Fire && otherElement instanceof Water) {
				setNewElement(otherElement, ElementTypes.STEAM);
			} else if (element instanceof Fire && otherElement instanceof WetSand) {
				setNewElement(element, ElementTypes.STEAM);
				setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}

		float chanceToCatch = element.getChanceToCatch() * numberOfFire;
		if (Math.random() < chanceToCatch) {
			setNewElement(element, ElementTypes.FIRE);
			matrix.getElement(element.getRow(), element.getColumn()).setLifetime(element.getLifetime());
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
			if (element1 instanceof Sand && element1.getDensity() < 6f) {
				setNewElement(element1, ElementTypes.WET_SAND);
				element2.setLimitedLife(true);
				element2.setLifetime(0);
				return;
			} else if (element2 instanceof Sand && element2.getDensity() < 6f) {
				setNewElement(element2, ElementTypes.WET_SAND);
				element1.setLimitedLife(true);
				element1.setLifetime(0);
				return;
			}
		}
	}

	public static void updateMovementLogic(Element element) {
		updateVelocity(element);

		for (int v = 0; v < getUpdateCount(element); v++) {

			boolean moveThisLoop;

			int delta = (int) Math.signum(element.getVelocity());
			Element nextVertical = matrix.getElement(element.getRow() - delta, element.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element nextVertical1 = matrix.getElement(element.getRow() - delta, element.getColumn() - randDirection);
			Element nextVertical2 = matrix.getElement(element.getRow() - delta, element.getColumn() + randDirection);

			if (nextVertical != null && nextVertical.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < element.getDensity()) {
				matrix.swap(element, nextVertical2);
			} else {
				element.setVelocity(0.5f);
			}

			if (!(element instanceof WetSand)) {
				checkWetness(element, nextVertical);
				checkWetness(element, nextVertical1);
				checkWetness(element, nextVertical2);
			}

			if (element instanceof Water) {
				findAndSwapNextEmptyElement(element);
			}

			if (element instanceof WetSand) {
				moveThisLoop = Math.random() < 0.6;
			} else {
				moveThisLoop = true;
			}

			if (element.movesSideways() && moveThisLoop) {

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
