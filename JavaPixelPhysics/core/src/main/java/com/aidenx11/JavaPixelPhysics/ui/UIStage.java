package com.aidenx11.JavaPixelPhysics.ui;

import com.aidenx11.JavaPixelPhysics.CellularMatrix;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;
import com.aidenx11.JavaPixelPhysics.elements.Element.ElementTypes;
import com.aidenx11.JavaPixelPhysics.input.MouseInput;
import com.aidenx11.JavaPixelPhysics.input.MouseInput.BrushTypes;
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
	public static TextButton clearCanvasButton;
	public static TextButton emptyToolButton;
	public static TextButton sandToolButton;
	public static TextButton woodToolButton;
	public static TextButton fireToolButton;
	public static TextButton waterToolButton;
	public static TextButton leafToolButton;
	public static TextButton squareBrushButton;
	public static TextButton circleBrushButton;
	public static TextButton rectBrushButton;
	public static TextButton dirtToolButton;
	public static TextButton stoneToolButton;
	public static TextButton canvasColorButton;
	public static TextButton lavaToolButton;
	public static TextButton obsidianToolButton;
	public static TextButton steelToolButton;
	public static TextButton voidToolButton;
	public static TextButton pauseButton;

	public static TextButton moveTablesButton;

	public static Slider brushSizeSlider;

	/** Input that the buttons should detect */
	MouseInput mouse;

	/** Keeps track of whether the simulation is in light or dark mode */
	boolean lightsOn = PixelPhysicsGame.lightsOn;

	boolean elementTableMoved = false;

	boolean brushTypeTableMoved = false;

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

		clearCanvasButton = new TextButton("Clear", skinButton, "default");
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
		sandToolButton.getLabel().setColor(new Color(ColorValues.SAND_COLOR.getRGB()[0] / 255f,
				ColorValues.SAND_COLOR.getRGB()[1] / 255f, ColorValues.SAND_COLOR.getRGB()[2] / 255f, 1));
		sandToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.SAND);
				resetButtonColors();
			}
		});

		woodToolButton = new TextButton("Wood", skinButton, "default");
		woodToolButton.getLabel().setColor(new Color(ColorValues.WOOD_COLOR.getRGB()[0] / 255f,
				ColorValues.WOOD_COLOR.getRGB()[1] / 255f, ColorValues.WOOD_COLOR.getRGB()[2] / 255f, 1));
		woodToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WOOD);
				resetButtonColors();
			}
		});

		fireToolButton = new TextButton("Fire", skinButton, "default");
		fireToolButton.getLabel().setColor(new Color(ColorValues.FIRE.getRGB()[0] / 255f,
				ColorValues.FIRE.getRGB()[1] / 255f, ColorValues.FIRE.getRGB()[2] / 255f, 1));
		fireToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.FIRE);
				resetButtonColors();
			}
		});

		waterToolButton = new TextButton("Water", skinButton, "default");
		waterToolButton.getLabel().setColor(new Color(ColorValues.WATER.getRGB()[0] / 255f,
				ColorValues.WATER.getRGB()[1] / 255f, ColorValues.WATER.getRGB()[2] / 255f, 1));
		waterToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WATER);
				resetButtonColors();
			}
		});

		leafToolButton = new TextButton("Leaves", skinButton, "default");
		leafToolButton.getLabel().setColor(new Color(ColorValues.LEAF.getRGB()[0] / 255f,
				ColorValues.LEAF.getRGB()[1] / 255f, ColorValues.LEAF.getRGB()[2] / 255f, 1));
		leafToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.LEAF);
				resetButtonColors();
			}
		});

		dirtToolButton = new TextButton("Dirt", skinButton, "default");
		dirtToolButton.getLabel().setColor(new Color(ColorValues.DIRT.getRGB()[0] / 255f,
				ColorValues.DIRT.getRGB()[1] / 255f, ColorValues.DIRT.getRGB()[2] / 255f, 1));
		dirtToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.DIRT);
				resetButtonColors();
			}
		});

		stoneToolButton = new TextButton("Stone", skinButton, "default");
		stoneToolButton.getLabel().setColor(new Color(ColorValues.STONE.getRGB()[0] / 255f,
				ColorValues.STONE.getRGB()[1] / 255f, ColorValues.STONE.getRGB()[2] / 255f, 1));
		stoneToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.STONE);
				resetButtonColors();
			}
		});

		lavaToolButton = new TextButton("Lava", skinButton, "default");
		lavaToolButton.getLabel().setColor(new Color(ColorValues.LAVA_RED.getRGB()[0] / 255f,
				ColorValues.LAVA_RED.getRGB()[1] / 255f, ColorValues.LAVA_RED.getRGB()[2] / 255f, 1));
		lavaToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.LAVA);
				resetButtonColors();
			}
		});

		obsidianToolButton = new TextButton("Obsidian", skinButton, "default");
		obsidianToolButton.getLabel().setColor(new Color(ColorValues.OBSIDIAN.getRGB()[0] / 255f,
				ColorValues.OBSIDIAN.getRGB()[1] / 255f, ColorValues.OBSIDIAN.getRGB()[2] / 255f, 1));
		obsidianToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.OBSIDIAN);
				resetButtonColors();
			}
		});

		steelToolButton = new TextButton("Steel", skinButton, "default");
		steelToolButton.getLabel().setColor(new Color(ColorValues.STEEL.getRGB()[0] / 255f,
				ColorValues.STEEL.getRGB()[1] / 255f, ColorValues.STEEL.getRGB()[2] / 255f, 1));
		steelToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.STEEL);
				resetButtonColors();
			}
		});

		voidToolButton = new TextButton("Void", skinButton, "default");
		voidToolButton.getLabel().setColor(new Color(ColorValues.VOID.getRGB()[0] / 255f,
				ColorValues.VOID.getRGB()[1] / 255f, ColorValues.VOID.getRGB()[2] / 255f, 1));
		voidToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.VOID);
				resetButtonColors();
			}
		});

		brushSizeSlider = new Slider(1f, 75f, 1f, true, skinButton);
		brushSizeSlider.setWidth(10f);
		brushSizeSlider.setHeight(Gdx.graphics.getHeight() > 400f + 15f ? 400f : Gdx.graphics.getHeight() - 30f);
		brushSizeSlider.setPosition(Gdx.graphics.getWidth() - PixelPhysicsGame.uiOffset + 20,
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
					PixelPhysicsGame.lightsOn = true;
					lightsOn = true;
					canvasColorButton.setColor(Color.TEAL);
					canvasColorButton.setText("Lights off");
					resetButtonColors();
				} else {
					PixelPhysicsGame.lightsOn = false;
					lightsOn = false;
					canvasColorButton.setColor(Color.NAVY);
					canvasColorButton.setText("Lights on");
					resetButtonColors();
				}
			}
		});

		pauseButton = new TextButton(PixelPhysicsGame.isPaused ? "Resume" : "Pause", skinButton, "default");
		pauseButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (PixelPhysicsGame.isPaused) {
					pauseButton.setText("Pause");
					PixelPhysicsGame.isPaused = false;
				} else {
					pauseButton.setText("Resume");
					PixelPhysicsGame.isPaused = true;
				}
				resetButtonColors();
			}
		});

//		moveTablesButton = new TextButton("test", skinButton, "default");
//		moveTablesButton.setPosition(100f, 100f);
//		moveTablesButton.addListener(new ClickListener() {
//			public void clicked(InputEvent event, float x, float y) {
//				if (!elementTableMoved) {
//					elementTable
//							.addAction(Actions.moveTo(Gdx.graphics.getWidth() + 100, elementTable.getY(), 0.2f));
//					elementTableMoved = true;
//				} else if (elementTableMoved) {
//					elementTable.addAction(Actions.moveTo(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset / 2 + 15,
//							Gdx.graphics.getHeight() - 245f, 0.2f));
//					elementTableMoved = false;
//				}
//
//				if (!brushTypeTableMoved) {
//					brushTypeTable
//							.addAction(Actions.moveTo(Gdx.graphics.getWidth() + 100, brushTypeTable.getY(), 0.2f));
//					brushTypeTableMoved = true;
//				} else if (brushTypeTableMoved) {
//					brushTypeTable
//							.addAction(Actions.moveTo(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 85, 100f, 0.2f));
//					brushTypeTableMoved = false;
//				}
//			}
//		});

		elementTable.add(clearCanvasButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(emptyToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(sandToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(woodToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(fireToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(waterToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(leafToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(dirtToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(stoneToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(lavaToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(obsidianToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(steelToolButton).minWidth(80f).pad(2f);
		elementTable.row();
		elementTable.add(voidToolButton).minWidth(80f).pad(2f);

		brushTypeTable.add(circleBrushButton).minWidth(65f).pad(2f);
		brushTypeTable.row();
		brushTypeTable.add(squareBrushButton).minWidth(65f).pad(2f);
		brushTypeTable.row();
		brushTypeTable.add(rectBrushButton).minWidth(85f).pad(2f);
		brushTypeTable.row();
		brushTypeTable.add(canvasColorButton).minWidth(75f).pad(2f);
		brushTypeTable.row();
		brushTypeTable.add(pauseButton).minWidth(65f).pad(2f);

		resetButtonColors();

		brushTypeTable.setPosition(Gdx.graphics.getWidth() - PixelPhysicsGame.uiOffset + 65, 85f);
		elementTable.setPosition(Gdx.graphics.getWidth() - PixelPhysicsGame.uiOffset / 2 + 15,
				Gdx.graphics.getHeight() - 215f);
		this.addActor(brushTypeTable);
		this.addActor(elementTable);
		this.addActor(brushSizeSlider);

//		this.addActor(moveTablesButton);
	}

	/**
	 * Resets the color of all buttons in the UI to their defaults, and to red if
	 * the button is selected.
	 */
	public void resetButtonColors() {
		if (lightsOn) {
			canvasColorButton.setColor(Color.TEAL);
			if (mouse.getElementType() != ElementTypes.DIRT) {
				dirtToolButton.setColor(Color.WHITE);
			} else {
				dirtToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WATER) {
				waterToolButton.setColor(Color.WHITE);
			} else {
				waterToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.SAND) {
				sandToolButton.setColor(Color.WHITE);
			} else {
				sandToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.WOOD) {
				woodToolButton.setColor(Color.WHITE);
			} else {
				woodToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.FIRE) {
				fireToolButton.setColor(Color.WHITE);
			} else {
				fireToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.EMPTY) {
				emptyToolButton.setColor(Color.WHITE);
			} else {
				emptyToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.LEAF) {
				leafToolButton.setColor(Color.WHITE);
			} else {
				leafToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STONE) {
				stoneToolButton.setColor(Color.WHITE);
			} else {
				stoneToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.LAVA) {
				lavaToolButton.setColor(Color.WHITE);
			} else {
				lavaToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.OBSIDIAN) {
				obsidianToolButton.setColor(Color.WHITE);
			} else {
				obsidianToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.STEEL) {
				steelToolButton.setColor(Color.WHITE);
			} else {
				steelToolButton.setColor(Color.RED);
			}
			if (mouse.getElementType() != ElementTypes.VOID) {
				voidToolButton.setColor(Color.WHITE);
			} else {
				voidToolButton.setColor(Color.RED);
			}
			clearCanvasButton.setColor(Color.WHITE);
			if (mouse.getBrushType() == BrushTypes.CIRCLE) {
				circleBrushButton.setColor(Color.RED);
			} else {
				circleBrushButton.setColor(Color.WHITE);
			}
			if (mouse.getBrushType() == BrushTypes.SQUARE) {
				squareBrushButton.setColor(Color.RED);
			} else {
				squareBrushButton.setColor(Color.WHITE);
			}
			if (mouse.getBrushType() == BrushTypes.RECTANGLE) {
				rectBrushButton.setColor(Color.RED);
			} else {
				rectBrushButton.setColor(Color.WHITE);
			}
			if (PixelPhysicsGame.isPaused) {
				pauseButton.setColor(Color.RED);
			} else {
				pauseButton.setColor(Color.WHITE);
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
			if (PixelPhysicsGame.isPaused) {
				pauseButton.setColor(Color.RED);
			} else {
				pauseButton.setColor(Color.GRAY);
			}
		}
	}
}