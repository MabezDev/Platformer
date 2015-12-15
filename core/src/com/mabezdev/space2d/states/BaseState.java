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
public abstract class BaseState {

    public static String stateName;
    protected GameStateManager GSManager;
    protected SpriteBatch batch;
    protected GlyphLayout layout;
    protected BitmapFont menuFont;
    private static final float gap = 40;

    public BaseState(GameStateManager gsm){
        this.GSManager = gsm;
        batch = new SpriteBatch();
        menuFont = ResourceManager.getFont("defaultFont");
        layout = new GlyphLayout();
        stateName = GSManager.getCurrentState().toString();
    }


    public abstract void update(float dt);
    public abstract void render();
    public abstract void handleInput();
    public abstract void dispose();

    protected void displayMenu(String[] menuOptions,int index,float x,float y){
        for(int i = 0; i < menuOptions.length;i++){
            layout.setText(menuFont,menuOptions[i]);
            if(index == i){
                menuFont.setColor(Color.RED);
            } else {
                menuFont.setColor(Color.WHITE);
            }
            menuFont.draw(batch,menuOptions[i],x - (layout.width/2),y - (i * (layout.height*1.4f)));
        }
    }


}
