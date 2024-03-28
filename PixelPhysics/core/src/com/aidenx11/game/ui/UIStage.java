package com.aidenx11.game.ui;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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

	public UIStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {

		Gdx.gl.glLineWidth(3);

		Table table = new Table();

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));

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
			}
		});

		final Slider brushSizeSlider = new Slider(0f, 50f, 1f, true, skinButton);
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

		table.add(clearCanvasButton).minWidth(120f).pad(5f);
		table.row();
		table.add(emptyToolButton).minWidth(120f).pad(5f);
		table.row();
		table.add(sandToolButton).minWidth(120f).pad(5f);
		table.row();
		table.add(woodToolButton).minWidth(120f).pad(5f);
		table.row();
		table.add(fireToolButton).minWidth(120f).pad(5f);
		table.row();
		table.add(waterToolButton).minWidth(120f).pad(5f);
		table.row();
		table.add(leafToolButton).minWidth(120f).pad(5f);

		table.setPosition(Gdx.graphics.getWidth() - pixelPhysicsGame.uiOffset / 2 + 15, Gdx.graphics.getHeight() - 130f);
		this.addActor(table);
		this.addActor(brushSizeSlider);
	}

}
