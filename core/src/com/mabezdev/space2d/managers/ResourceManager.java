package com.mabezdev.space2d.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mabez on 14/12/2015.
 */
public class ResourceManager {

    private static HashMap<String,Texture> textures = new HashMap<String, Texture>();
    private static HashMap<String,BitmapFont> fonts = new HashMap<String, BitmapFont>();

    public static final ResourceManager INSTANCE = new ResourceManager();

    public static void loadTexture(String key,String path){
        Texture temp = new Texture(Gdx.files.internal(path));
        textures.put(key,temp);
    }

    public static Texture getTexture(String key){
        return textures.get(key);
    }

    public static void removeTexture(String key){
        Texture t = textures.get(key);
        if(t!=null){
            t.dispose();
        }
    }

    public static void loadFont(String key,int size,String path){
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = gen.generateFont(parameter);
        font.setColor(Color.WHITE);
        fonts.put(key,font);
    }

    public static BitmapFont getFont(String key){
        return fonts.get(key);
    }

    public static void removeFont(String key){
        BitmapFont f = fonts.get(key);
        if(f!=null){
            f.dispose();
        }
    }
}
