package ui;

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
	
	public ButtonStage(Viewport viewport, MouseInput mouseInput) {
		
		uiStage = new Stage(viewport);

		Gdx.gl.glLineWidth(3);

		skinButton = new Skin(Gdx.files.internal("skin/uiskin.json"));

		uiStage = new Stage();
		
		final TextButton sandToolButton = new TextButton("Sand", skinButton, "default");
		sandToolButton.setWidth(150f);
		sandToolButton.setHeight(20f);
		sandToolButton.setPosition(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() - 100f);
		sandToolButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                mouseInput.setElementType(ElementTypes.SAND);
            }
        });
		
		final TextButton emptyToolButton = new TextButton("Empty", skinButton, "default");
		emptyToolButton.setWidth(150f);
		emptyToolButton.setHeight(20f);
		emptyToolButton.setPosition(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() - 50);
		emptyToolButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mouseInput.setElementType(ElementTypes.EMPTY);
			}
		});
        
		uiStage.addActor(emptyToolButton);
        uiStage.addActor(sandToolButton);
	}
	
	public Stage getStage() {
		return uiStage;
	}

}
