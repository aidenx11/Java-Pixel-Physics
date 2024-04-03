package com.aidenx11.game.elements;

public class Empty extends Element {

	public static ElementTypes type = ElementTypes.EMPTY;
	
	public Empty(int row, int column) {
		super(type, row, column, null, false, 0, false, false, 0, false, false, -1);
	}
	
	@Override
	public void update() {
	}
	
}
