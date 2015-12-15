package com.mabezdev.space2d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 14/12/2015.
 */
public class MenuState extends BaseState {

    private String[] options = {"Play","Options","Exit"};
    private int index;
    private BitmapFont logo;
    private GlyphLayout layout;

    public MenuState(GameStateManager gsm){
        super(gsm);
        ResourceManager.loadFont("logoFont",96,"orangejuice2.ttf");
        logo = ResourceManager.getFont("logoFont");
        layout = new GlyphLayout();
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        batch.begin();
        {
            displayMenu(options, index,(Variables.WIDTH/2),Variables.WIDTH/2 - 50);
            layout.setText(logo, Variables.NAME);
            logo.draw(batch,Variables.NAME, (Variables.WIDTH - layout.width)/2,Variables.HEIGHT - layout.height - 20);
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
            if (index == 0) {
                this.GSManager.setCurrentState(GameStateManager.State.PLAY);
            }
            if (index == 1) {
                this.GSManager.setCurrentState(GameStateManager.State.OPTIONS);
            }
            if (index == 2) {
                System.exit(0);
            }
        }
    }

    @Override
    public void dispose(){
    }
}
