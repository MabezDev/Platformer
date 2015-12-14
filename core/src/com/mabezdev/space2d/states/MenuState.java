package com.mabezdev.space2d.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 14/12/2015.
 */
public class MenuState extends BaseState {

    private String[] options = {"Play","Options","Exit"};

    private BitmapFont menuFont;
    private SpriteBatch batch;
    private GlyphLayout layout;
    private int index;

    public MenuState(GameStateManager gsm){
        super(gsm);
        stateName = "MENU";
        System.out.println(stateName);
        batch = new SpriteBatch();
        menuFont = ResourceManager.getFont("defaultFont");
        layout = new GlyphLayout();
        index = 0;

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        batch.begin();
        for(int i =0;i < options.length;i++){
            layout.setText(menuFont,options[i]);
            int width = (int)layout.width;
            if(index == i){
                menuFont.setColor(Color.RED);
            } else {
                menuFont.setColor(Color.WHITE);
            }
            menuFont.draw(batch,options[i],(Variables.WIDTH - (width))/2 ,Variables.HEIGHT/2 - (i * 40));
        }
        batch.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose(){

    }
}
