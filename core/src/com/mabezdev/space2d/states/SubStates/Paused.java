package com.mabezdev.space2d.states.SubStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.util.Log;

/**
 * Created by Mabez on 21/12/2015.
 */
public class Paused extends BaseSubState {

    private Player accessor;
    private String[] options = {"Resume","Go to menu","Quit"};
    private int index = 0;

    public Paused(GameStateManager gsm) {
        super(gsm);
        /*
        Paused state, stop the player moving only, entities are still updated and the player can still die (this is for multi-player purposes)
         */
        Log.print("Opening pause state!");
        this.accessor = PlayState.getPlayer();
        this.accessor.setCanMove(false);

        batch = new SpriteBatch();
        //menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        batch.begin();
        {
            displayMenu(options, index, Variables.WIDTH / 2,  ((Variables.HEIGHT*2)/3));
        }
        batch.end();
    }

    @Override
    public void handleInput() {
        //handle indexing
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            if(index > 0){
                index--;
            } else {
                index = options.length -1;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            if(index < options.length - 1){
                index++;
            } else {
                index = 0;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(index == 0){
                accessor.setIsPaused(false);
            }
            if(index == 1){
                PlayState.getGSM().removeAllSubStates();
                PlayState.getGSM().setCurrentState(GameStateManager.State.MENU);
            }
            if (index == 2) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        this.accessor.setCanMove(true);
        Log.print("Closing pause state!");
    }
}
