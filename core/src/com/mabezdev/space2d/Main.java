package com.mabezdev.space2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.util.DesktopInputProccessor;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.util.MyMouse;

public class Main extends ApplicationAdapter {
	private OrthographicCamera Camera;
	private float dt;
	private GameStateManager myGsm;
	@Override
	public void create () {
		Variables.WIDTH = Gdx.graphics.getWidth();
		Variables.HEIGHT = Gdx.graphics.getHeight();
		Camera = new OrthographicCamera(Variables.WIDTH,Variables.HEIGHT);
		myGsm = new GameStateManager(Camera);
		// do check for what os later ie have an android input processor if we get there
		Gdx.input.setInputProcessor(new DesktopInputProccessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		dt = Gdx.graphics.getDeltaTime();
		myGsm.update(dt);
		myGsm.render();
		Gdx.graphics.setTitle("SPACE2D FPS: "+Integer.toString(Gdx.graphics.getFramesPerSecond()));
		//make sure key updates are done last
		MyMouse.update();
	}

	@Override
	public void dispose(){
		myGsm.dispose();
		//disposes the current states ready for exit
	}
}
