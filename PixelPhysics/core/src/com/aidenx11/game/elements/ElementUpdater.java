package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;

public class ElementUpdater {
	
	
	public static void update(Element element, CellularMatrix matrix) {
		element.updateVelocity();
		
		for (int v = 0; v < element.getUpdateCount(); v++) {

			Element below = matrix.getElement(element.getRow() - 1, element.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element below1 = matrix.getElement(element.getRow() - 1, element.getColumn() - randDirection);
			Element below2 = matrix.getElement(element.getRow() - 1, element.getColumn() + randDirection);

			if (below instanceof Empty) {
				matrix.swap(element, below);
			} else if (below1 instanceof Empty) {
				matrix.swap(element, below1);
				element.setVelocity((float) (element.getVelocity() - 0.1));
			} else if (below2 instanceof Empty) {
				matrix.swap(element, below2);
				element.setVelocity((float) (element.getVelocity() - 0.1));
			} else {
				element.setVelocity(0);
			}
		}

		element.setModified(element.getVelocity() != 0);
		
	}
}
