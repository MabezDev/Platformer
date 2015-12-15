package com.mabezdev.space2d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;

/**
 * Created by Mabez on 15/12/2015.
 */
public class OptionsState extends BaseState {

    private String[] menuOptions = {"Controls","Video","Sounds","Back to Menu"};
    private int index;

    public OptionsState(GameStateManager gsm) {
        super(gsm);
        index = 0;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        batch.begin();
        {
            displayMenu(menuOptions, index, Variables.WIDTH/2,Variables.HEIGHT - 100);
        }
        batch.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            if(index > 0){
                index--;
            } else {
                index = menuOptions.length -1;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            if(index < menuOptions.length - 1){
                index++;
            } else {
                index = 0;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(index == 0){
                System.out.println("Controls");
            }
            if(index == 1){
                System.out.println("Video");
            }
            if(index == 2){
                System.out.println("Sound");
            }
            if(index == 3){
                GSManager.setCurrentState(GameStateManager.State.MENU);
            }

        }
    }

    @Override
    public void dispose() {

    }
}
