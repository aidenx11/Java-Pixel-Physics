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
 * Class to handle all elements of the UI. Contains fields for all buttons and
 * sliders in the UI, and methods for detecting input and controlling colors.
 * 
 * @author Aiden Schroeder
 */
public class UIStage extends Stage {

	/** Handles the skin for the UI */
	private Skin skinButton;

	/** Declaration for all buttons in the UI */
	final TextButton clearCanvasButton;
	final TextButton emptyToolButton;
	final TextButton sandToolButton;
	final TextButton woodToolButton;
	final TextButton fireToolButton;
	final TextButton waterToolButton;
	final TextButton leafToolButton;
	final TextButton squareBrushButton;
	final TextButton circleBrushButton;
	final TextButton rectBrushButton;
	final TextButton dirtToolButton;
	final TextButton stoneToolButton;
	final TextButton canvasColorButton;
	final TextButton lavaToolButton;
	final TextButton obsidianToolButton;
	final TextButton steelToolButton;
	final TextButton voidToolButton;

	/** Input that the buttons should detect */
	MouseInput mouse;

	/** Keeps track of whether the simulation is in light or dark mode */
	boolean lightsOn = pixelPhysicsGame.lightsOn;

	/**
	 * Constructs the UI by initializing and placing all buttons and sliders.
	 * 
	 * @param viewport   viewport the UI is in
	 * @param mouseInput input the UI should detect
	 * @param matrix     matrix the UI is being drawn on
	 */
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

		rectBrushButton = new TextButton("Rectangle", skinButton, "default");
		rectBrushButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushType(BrushTypes.RECTANGLE);
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

		lavaToolButton = new TextButton("Lava", skinButton, "default");
		lavaToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.LAVA);
				resetButtonColors();
			}
		});

		obsidianToolButton = new TextButton("Obsidian", skinButton, "default");
		obsidianToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.OBSIDIAN);
				resetButtonColors();
			}
		});

		steelToolButton = new TextButton("Steel", skinButton, "default");
		steelToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.STEEL);
				resetButtonColors();
			}
		});

		voidToolButton = new TextButton("Void", skinButton, "default");
		voidToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.VOID);
				resetButtonColors();
			}
		});

		final Slider brushSizeSlider = new Slider(1f, 75f, 1f, true, skinButton);
		brushSizeSlider.setWidth(10f);
		brushSizeSlider.setHeight(Gdx.graphics.getHeight() > 400f + 15f ? 400f : Gdx.graphics.getHeight() - 30f);
		brushSizeSlider.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 15,
				Gdx.graphics.getHeight() - brushSizeSlider.getHeight() - 15);
		brushSizeSlider.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushSize((int) brushSizeSlider.getValue());
				mouseInput.setCursorSize((int) brushSizeSlider.getValue());
			}
		});

		canvasColorButton = new TextButton(lightsOn ? "Lights off" : "Lights on", skinButton, "default");
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
		elementTable.row();
		elementTable.add(lavaToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(obsidianToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(steelToolButton).minWidth(120f).pad(5f);
		elementTable.row();
		elementTable.add(voidToolButton).minWidth(120f).pad(5f);

		brushTypeTable.add(circleBrushButton).minWidth(60f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(squareBrushButton).minWidth(60f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(rectBrushButton).minWidth(75f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(canvasColorButton).minWidth(70f).pad(5f);

		resetButtonColors();

		brushTypeTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 45,
				Gdx.graphics.getHeight() - 560f);
		elementTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset / 2 + 15,
				Gdx.graphics.getHeight() - 245f);
		this.addActor(brushTypeTable);
		this.addActor(elementTable);
		this.addActor(brushSizeSlider);
	}

	/**
	 * Resets the color of all buttons in the UI to their defaults, and to red if
	 * the button is selected.
	 */
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
			if (mouse.getElementType() != ElementTypes.LAVA) {
				lavaToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				lavaToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.OBSIDIAN) {
				obsidianToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				obsidianToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STEEL) {
				steelToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				steelToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.VOID) {
				voidToolButton.setColor(Color.LIGHT_GRAY);
			} else {
				voidToolButton.setColor(Color.RED);
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
			if (mouse.getBrushType() == BrushTypes.RECTANGLE) {
				rectBrushButton.setColor(Color.RED);
			} else {
				rectBrushButton.setColor(Color.LIGHT_GRAY);
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
			if (mouse.getElementType() != ElementTypes.LAVA) {
				lavaToolButton.setColor(Color.GRAY);
			} else {
				lavaToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.OBSIDIAN) {
				obsidianToolButton.setColor(Color.GRAY);
			} else {
				obsidianToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STEEL) {
				steelToolButton.setColor(Color.GRAY);
			} else {
				steelToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.VOID) {
				voidToolButton.setColor(Color.GRAY);
			} else {
				voidToolButton.setColor(Color.RED);
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
			if (mouse.getBrushType() == BrushTypes.RECTANGLE) {
				rectBrushButton.setColor(Color.RED);
			} else {
				rectBrushButton.setColor(Color.GRAY);
			}
		}
	}
}