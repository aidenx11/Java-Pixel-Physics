package com.aidenx11.game.ui;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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
	
	public UIStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {

		Gdx.gl.glLineWidth(3);

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		clearCanvasButton = new TextButton("Clear Canvas", skinButton, "default");
		clearCanvasButton.setWidth(115f);
		clearCanvasButton.setHeight(20f);
		clearCanvasButton.setPosition(15f, Gdx.graphics.getHeight() - 30f);
		clearCanvasButton.setColor(Color.LIGHT_GRAY);
		clearCanvasButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				matrix.clear();
			}
		});
		
		emptyToolButton = new TextButton("Eraser", skinButton, "default");
		emptyToolButton.setWidth(60f);
		emptyToolButton.setHeight(20f);
		emptyToolButton.setPosition(140f, Gdx.graphics.getHeight() - 30f);
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
			}
		});
		
		sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.setWidth(60f);
		sandToolButton.setHeight(20f);
		sandToolButton.setPosition(210f, Gdx.graphics.getHeight() - 30f);
		sandToolButton.setColor(Color.RED);
		sandToolButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                mouseInput.setElementType(ElementTypes.SAND);
                sandToolButton.setColor(Color.RED);
                emptyToolButton.setColor(Color.LIGHT_GRAY);
				woodToolButton.setColor(Color.LIGHT_GRAY);
				fireToolButton.setColor(Color.LIGHT_GRAY);
				waterToolButton.setColor(Color.LIGHT_GRAY);
            }
        });

		woodToolButton = new TextButton("Wood", skinButton, "default");
		woodToolButton.setWidth(60f);
		woodToolButton.setHeight(20f);
		woodToolButton.setPosition(280f, Gdx.graphics.getHeight() - 30f);
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
			}
		});
		
//		final TextButton randomizeColorButton = new TextButton("Make it rainbow!", skinButton, "default");
//		randomizeColorButton.setWidth(150f);
//		randomizeColorButton.setHeight(20f);
//		randomizeColorButton.setPosition(350f, Gdx.graphics.getHeight() - 30f);
//		randomizeColorButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				if (!mouseInput.isRandomizeColor()) {
//					randomizeColorButton.setText("Stop the rainbow");
//					mouseInput.setRandomizeColor(true);
//				} else {
//					randomizeColorButton.setText("Make it rainbow");
//					mouseInput.setRandomizeColor(false);
//				}
//			}
//		});
		
//		final TextButton smokeToolButton = new TextButton("Smoke", skinButton, "default");
//		smokeToolButton.setWidth(70f);
//		smokeToolButton.setHeight(20f);
//		smokeToolButton.setPosition(510, Gdx.graphics.getHeight() - 30f);
//		smokeToolButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				mouseInput.setElementType(ElementTypes.SMOKE);
//			}
//		});
		
		fireToolButton = new TextButton("Fire", skinButton, "default");
		fireToolButton.setWidth(60f);
		fireToolButton.setHeight(20f);
		fireToolButton.setPosition(350, Gdx.graphics.getHeight() - 30f);
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
			}
		});
		
		waterToolButton = new TextButton("Water", skinButton, "default");
		waterToolButton.setWidth(70f);
		waterToolButton.setHeight(20f);
		waterToolButton.setPosition(420, Gdx.graphics.getHeight() - 30f);
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
			}
		});
		
		final Slider brushSizeSlider = new Slider(1f, 50f, 1f, false, skinButton);
		brushSizeSlider.setWidth(300f);
		brushSizeSlider.setHeight(10f);
		brushSizeSlider.setPosition(Gdx.graphics.getWidth() - 350f, Gdx.graphics.getHeight() - 30f);
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
//      this.addActor(randomizeColorButton);
        this.addActor(brushSizeSlider);
//        this.addActor(smokeToolButton);
        this.addActor(fireToolButton);
        this.addActor(waterToolButton);
	}
	

}
