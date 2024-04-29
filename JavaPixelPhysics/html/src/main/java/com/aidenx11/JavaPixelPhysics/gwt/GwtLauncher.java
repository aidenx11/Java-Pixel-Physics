package com.aidenx11.JavaPixelPhysics.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            // Resizable application, uses available space in browser with no padding:
//            GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(false);
//            cfg.padVertical = 0;
//            cfg.padHorizontal = 0;
//            return cfg;
            // If you want a fixed size application, comment out the above resizable section,
            // and uncomment below:
            return new GwtApplicationConfiguration(1280, 720, GwtApplication.isMobileDevice());
        }

        @Override
        public ApplicationListener createApplicationListener () {
            return new PixelPhysicsGame();
        }
}
