package com.aidenx11.game.ui;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIStage extends Stage {
	
	private Skin skinButton;
	
	public UIStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {

		Gdx.gl.glLineWidth(3);

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		
		
		final TextButton clearCanvasButton = new TextButton("Clear Canvas", skinButton, "default");
		clearCanvasButton.setWidth(115f);
		clearCanvasButton.setHeight(20f);
		clearCanvasButton.setPosition(15f, Gdx.graphics.getHeight() - 25f);
		clearCanvasButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				matrix.clear();
			}
		});
		
		final TextButton emptyToolButton = new TextButton("Empty", skinButton, "default");
		emptyToolButton.setWidth(60f);
		emptyToolButton.setHeight(20f);
		emptyToolButton.setPosition(140f, Gdx.graphics.getHeight() - 25f);
		emptyToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.EMPTY);
			}
		});
		
		final TextButton sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.setWidth(60f);
		sandToolButton.setHeight(20f);
		sandToolButton.setPosition(210f, Gdx.graphics.getHeight() - 25f);
		sandToolButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                mouseInput.setElementType(ElementTypes.SAND);
            }
        });

		final TextButton woodToolButton = new TextButton("Wood", skinButton, "default");
		woodToolButton.setWidth(60f);
		woodToolButton.setHeight(20f);
		woodToolButton.setPosition(280f, Gdx.graphics.getHeight() - 25f);
		woodToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WOOD);
			}
		});
		
		final TextButton randomizeColorButton = new TextButton("Make it rainbow!", skinButton, "default");
		randomizeColorButton.setWidth(150f);
		randomizeColorButton.setHeight(20f);
		randomizeColorButton.setPosition(350f, Gdx.graphics.getHeight() - 25f);
		randomizeColorButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!mouseInput.isRandomizeColor()) {
					randomizeColorButton.setText("Stop the rainbow");
					mouseInput.setRandomizeColor(true);
				} else {
					randomizeColorButton.setText("Make it rainbow!");
					mouseInput.setRandomizeColor(false);
				}
			}
		});
		
		final TextButton smokeToolButton = new TextButton("Smoke", skinButton, "default");
		smokeToolButton.setWidth(70f);
		smokeToolButton.setHeight(20f);
		smokeToolButton.setPosition(510, Gdx.graphics.getHeight() - 25f);
		smokeToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.SMOKE);
			}
		});
		
		final Slider brushSizeSlider = new Slider(1f, 15f, 1f, false, skinButton);
		brushSizeSlider.setWidth(100f);
		brushSizeSlider.setHeight(10f);
		brushSizeSlider.setPosition(850f, Gdx.graphics.getHeight() - 25f);
		brushSizeSlider.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setBrushSize((int) brushSizeSlider.getValue());
				mouseInput.setCursorSize((int) brushSizeSlider.getValue());
			}
		});
		
       
		this.addActor(emptyToolButton);
        this.addActor(sandToolButton);
        this.addActor(clearCanvasButton);
        this.addActor(woodToolButton);
        this.addActor(randomizeColorButton);
        this.addActor(brushSizeSlider);
        this.addActor(smokeToolButton);
	}
	
	public void setFrameText(int frame) {
		
	}

}
