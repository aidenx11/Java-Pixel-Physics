package com.aidenx11.game.ui;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.aidenx11.game.input.MouseInput.BrushTypes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 */
public class UIStage extends Stage {

	private Skin skinButton;

	final TextButton clearCanvasButton;
	final TextButton emptyToolButton;
	final TextButton sandToolButton;
	final TextButton woodToolButton;
	final TextButton fireToolButton;
	final TextButton waterToolButton;
	final TextButton leafToolButton;
	final TextButton squareBrushButton;
	final TextButton circleBrushButton;
	final TextButton dirtToolButton;
	final TextButton stoneToolButton;
	final TextButton canvasColorButton;

	MouseInput mouse;

	boolean lightsOn = pixelPhysicsGame.lightsOn;

	public UIStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {

		mouse = mouseInput;

		Gdx.gl.glLineWidth(3);

		Table elementTable = new Table();
		Table brushTypeTable = new Table();

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));

		circleBrushButton = new TextButton("Circle", skinButton, "default");
		circleBrushButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushType(BrushTypes.CIRCLE);
				resetButtonColors();
			}
		});

		squareBrushButton = new TextButton("Square", skinButton, "default");
		squareBrushButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushType(BrushTypes.SQUARE);
				resetButtonColors();
			}
		});

		clearCanvasButton = new TextButton("Clear Canvas", skinButton, "default");
		clearCanvasButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				matrix.clear();
			}
		});

		emptyToolButton = new TextButton("Eraser", skinButton, "default");
		emptyToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.EMPTY);
				resetButtonColors();
			}
		});

		sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.SAND);
				resetButtonColors();
			}
		});

		woodToolButton = new TextButton("Wood", skinButton, "default");
		woodToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WOOD);
				resetButtonColors();
			}
		});

		fireToolButton = new TextButton("Fire", skinButton, "default");
		fireToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.FIRE);
				resetButtonColors();
			}
		});

		waterToolButton = new TextButton("Water", skinButton, "default");
		waterToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WATER);
				resetButtonColors();
			}
		});

		leafToolButton = new TextButton("Leaves", skinButton, "default");
		leafToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.LEAF);
				resetButtonColors();
			}
		});

		dirtToolButton = new TextButton("Dirt", skinButton, "default");
		dirtToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.DIRT);
				resetButtonColors();
			}
		});

		stoneToolButton = new TextButton("Stone", skinButton, "default");
		stoneToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.STONE);
				resetButtonColors();
			}
		});

		final Slider brushSizeSlider = new Slider(1f, 40f, 1f, true, skinButton);
		brushSizeSlider.setWidth(10f);
		brushSizeSlider.setHeight(200f);
		brushSizeSlider.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 15, 640f);
		brushSizeSlider.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushSize((int) brushSizeSlider.getValue());
				mouseInput.setCursorSize((int) brushSizeSlider.getValue());
			}
		});

		canvasColorButton = new TextButton("Lights off", skinButton, "default");
		canvasColorButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (canvasColorButton.getColor().equals(Color.NAVY)) {
					pixelPhysicsGame.lightsOn = true;
					lightsOn = true;
					canvasColorButton.setColor(Color.TEAL);
					canvasColorButton.setText("Lights off");
					resetButtonColors();
				} else {
					pixelPhysicsGame.lightsOn = false;
					lightsOn = false;
					canvasColorButton.setColor(Color.NAVY);
					canvasColorButton.setText("Lights on");
					resetButtonColors();
				}
			}
		});

		elementTable.add(clearCanvasButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(emptyToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(sandToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(woodToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(fireToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(waterToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(leafToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(dirtToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(stoneToolButton).minWidth(120f).pad(5f);

		brushTypeTable.add(circleBrushButton).minWidth(60f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(squareBrushButton).minWidth(60f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(canvasColorButton).minWidth(70f).pad(5f);

		resetButtonColors();

		brushTypeTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 40,
				Gdx.graphics.getHeight() - 400f);
		elementTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset / 2 + 15,
				Gdx.graphics.getHeight() - 175f);
		this.addActor(brushTypeTable);
		this.addActor(elementTable);
		this.addActor(brushSizeSlider);
	}

	public void resetButtonColors() {
		if (lightsOn) {
			canvasColorButton.setColor(Color.TEAL);
			if (mouse.getElementType() != ElementTypes.DIRT) {
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				dirtToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WATER) {
				waterToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				waterToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.SAND) {
				sandToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				sandToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WOOD) {
				woodToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				woodToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.FIRE) {
				fireToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				fireToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.EMPTY) {
				emptyToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				emptyToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.LEAF) {
				leafToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				leafToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STONE) {
				stoneToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				stoneToolButton.setColor(Color.RED);
			}
			clearCanvasButton.setColor(Color.LIGHT_GRAY);
			if (mouse.getBrushType() == BrushTypes.CIRCLE) {
				circleBrushButton.setColor(Color.RED);
			} else {
				circleBrushButton.setColor(Color.LIGHT_GRAY);
			}
			if (mouse.getBrushType() == BrushTypes.SQUARE) {
				squareBrushButton.setColor(Color.RED);
			} else {
				squareBrushButton.setColor(Color.LIGHT_GRAY);
			}
		} else {
			canvasColorButton.setColor(Color.NAVY);
			if (mouse.getElementType() != ElementTypes.DIRT) {
				dirtToolButton.setColor(Color.GRAY);
			} else {
				dirtToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WATER) {
				waterToolButton.setColor(Color.GRAY);
			} else {
				waterToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.SAND) {
				sandToolButton.setColor(Color.GRAY);
			} else {
				sandToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WOOD) {
				woodToolButton.setColor(Color.GRAY);
			} else {
				woodToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.FIRE) {
				fireToolButton.setColor(Color.GRAY);
			} else {
				fireToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.EMPTY) {
				emptyToolButton.setColor(Color.GRAY);
			} else {
				emptyToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.LEAF) {
				leafToolButton.setColor(Color.GRAY);
			} else {
				leafToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STONE) {
				stoneToolButton.setColor(Color.GRAY);
			} else {
				stoneToolButton.setColor(Color.RED);
			}
			clearCanvasButton.setColor(Color.GRAY);
			if (mouse.getBrushType() == BrushTypes.CIRCLE) {
				circleBrushButton.setColor(Color.RED);
			} else {
				circleBrushButton.setColor(Color.GRAY);
			}
			if (mouse.getBrushType() == BrushTypes.SQUARE) {
				squareBrushButton.setColor(Color.RED);
			} else {
				squareBrushButton.setColor(Color.GRAY);
			}
		}
	}
}