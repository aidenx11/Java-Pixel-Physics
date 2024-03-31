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

	public UIStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {

		Gdx.gl.glLineWidth(3);

		Table elementTable = new Table();
		Table brushTypeTable = new Table();

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		circleBrushButton = new TextButton("Circle", skinButton, "default");
		circleBrushButton.setColor(Color.LIGHT_GRAY);
		circleBrushButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushType(BrushTypes.CIRCLE);
				circleBrushButton.setColor(Color.RED);
				squareBrushButton.setColor(Color.LIGHT_GRAY);
			}
		});
		
		squareBrushButton = new TextButton("Square", skinButton, "default");
		squareBrushButton.setColor(Color.LIGHT_GRAY);
		squareBrushButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushType(BrushTypes.SQUARE);
				circleBrushButton.setColor(Color.LIGHT_GRAY);
				squareBrushButton.setColor(Color.RED);
			}
		});

		clearCanvasButton = new TextButton("Clear Canvas", skinButton, "default");
		clearCanvasButton.setColor(Color.CORAL);
		clearCanvasButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				matrix.clear();
			}
		});

		emptyToolButton = new TextButton("Eraser", skinButton, "default");
		emptyToolButton.setColor(Color.LIGHT_GRAY);
		emptyToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.EMPTY);
				emptyToolButton.setColor(Color.RED);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.setColor(Color.RED);
		sandToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.SAND);
				sandToolButton.setColor(Color.RED);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		woodToolButton = new TextButton("Wood", skinButton, "default");
		woodToolButton.setColor(Color.LIGHT_GRAY);
		woodToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WOOD);
				woodToolButton.setColor(Color.RED);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		fireToolButton = new TextButton("Fire", skinButton, "default");
		fireToolButton.setColor(Color.LIGHT_GRAY);
		fireToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.FIRE);
				fireToolButton.setColor(Color.RED);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		waterToolButton = new TextButton("Water", skinButton, "default");
		waterToolButton.setColor(Color.LIGHT_GRAY);
		waterToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WATER);
				waterToolButton.setColor(Color.RED);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		leafToolButton = new TextButton("Leaves", skinButton, "default");
		leafToolButton.setColor(Color.LIGHT_GRAY);
		leafToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.LEAF);
				leafToolButton.setColor(Color.RED);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				dirtToolButton.setColor(Color.LIGHT_GRAY);
			}
		});
		
		dirtToolButton = new TextButton("Dirt", skinButton, "default");
		dirtToolButton.setColor(Color.LIGHT_GRAY);
		dirtToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.DIRT);
				dirtToolButton.setColor(Color.RED);
				waterToolButton.setColor(Color.LIGHT_GRAY);
				sandToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				emptyToolButton.setColor(Color.LIGHT_GRAY);
				leafToolButton.setColor(Color.LIGHT_GRAY);
			}
		});

		final Slider brushSizeSlider = new Slider(1f, 50f, 1f, true, skinButton);
		brushSizeSlider.setWidth(10f);
		brushSizeSlider.setHeight(200f);
		brushSizeSlider.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 15,
				640f);
		brushSizeSlider.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushSize((int) brushSizeSlider.getValue());
				mouseInput.setCursorSize((int) brushSizeSlider.getValue());
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
		
		brushTypeTable.add(circleBrushButton).minWidth(60f).pad(5f);
		brushTypeTable.row();
		brushTypeTable.add(squareBrushButton).minWidth(60f).pad(5f);
		
		squareBrushButton.setColor(Color.RED);
		
		brushTypeTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset + 35, Gdx.graphics.getHeight() - 300f);
		elementTable.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset / 2 + 15, Gdx.graphics.getHeight() - 155f);
		this.addActor(brushTypeTable);
		this.addActor(elementTable);
		this.addActor(brushSizeSlider);
	}

}
