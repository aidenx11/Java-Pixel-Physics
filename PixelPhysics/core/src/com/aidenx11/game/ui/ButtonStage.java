package com.aidenx11.game.ui;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ButtonStage extends Stage {
	
	private Stage uiStage;
	private Skin skinButton;
	
	public ButtonStage(Viewport viewport, MouseInput mouseInput, CellularMatrix matrix) {
		
		uiStage = new Stage(viewport);

		Gdx.gl.glLineWidth(3);

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));

		uiStage = new Stage();
		
		final TextButton sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.setWidth(150f);
		sandToolButton.setHeight(20f);
		sandToolButton.setPosition(335f, Gdx.graphics.getHeight() - 25f);
		sandToolButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                mouseInput.setElementType(ElementTypes.SAND);
            }
        });
		
		final TextButton emptyToolButton = new TextButton("Empty", skinButton, "default");
		emptyToolButton.setWidth(150f);
		emptyToolButton.setHeight(20f);
		emptyToolButton.setPosition(175f, Gdx.graphics.getHeight() - 25f);
		emptyToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.EMPTY);
			}
		});
		
		final TextButton clearCanvasButton = new TextButton("Clear Canvas", skinButton, "default");
		clearCanvasButton.setWidth(150f);
		clearCanvasButton.setHeight(20f);
		clearCanvasButton.setPosition(15f, Gdx.graphics.getHeight() - 25f);
		clearCanvasButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				matrix.clear();
			}
		});
		

		final TextButton woodToolButton = new TextButton("poop", skinButton, "default");
		woodToolButton.setWidth(150f);
		woodToolButton.setHeight(20f);
		woodToolButton.setPosition(495f, Gdx.graphics.getHeight() - 25f);
		woodToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.WOOD);
			}
		});
       
		uiStage.addActor(emptyToolButton);
        uiStage.addActor(sandToolButton);
        uiStage.addActor(clearCanvasButton);
        uiStage.addActor(woodToolButton);
        mouseInput.setUiRows((int) ((Gdx.graphics.getHeight() - 50f) / pixelPhysicsGame.pixelSizeModifier));
	}
	
	public Stage getStage() {
		return uiStage;
	}

}