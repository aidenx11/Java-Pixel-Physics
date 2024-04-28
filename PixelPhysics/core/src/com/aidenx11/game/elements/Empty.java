package com.aidenx11.game.elements;

/**
 * Class to manage creation and updating of the Empty element. Empty elements
 * have no behavior, and do not interact with other elements. Empty elements are
 * set in the matrix when there is no other element at that location. Upon
 * creation of a CellularMatrix, the entire matrix is set to empty elements.
 */
public class Empty extends Element {

	public static ElementTypes type = ElementTypes.EMPTY;

	public Empty(int row, int column) {
		super(type, row, column, null, false, 0, false, false, 0, false, 0);
	}

	@Override
	public void update() {
		// Empty should do nothing
	}

}
