package com.mabezdev.space2d.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Mabez on 22/12/2015.
 */
public class DesktopInputProccessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            MyMouse.setKeyState(MyMouse.LEFT,true);
        }
        if (button == Input.Buttons.RIGHT) {
            MyMouse.setKeyState(MyMouse.RIGHT,true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            MyMouse.setKeyState(MyMouse.LEFT,false);
        }
        if (button == Input.Buttons.RIGHT) {
            MyMouse.setKeyState(MyMouse.RIGHT,false);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if(amount == 1){
            MyMouse.setKeyState(MyMouse.MWHEEL_UP,true);
        } else if(amount == -1){
            MyMouse.setKeyState(MyMouse.MWHEEL_DOWN,true);
        } else {
            MyMouse.setKeyState(MyMouse.MWHEEL_UP,false);
            MyMouse.setKeyState(MyMouse.MWHEEL_DOWN,false);
        }


        return false;
    }
}