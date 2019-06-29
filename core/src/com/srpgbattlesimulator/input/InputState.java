package com.srpgbattlesimulator.input;

import com.badlogic.gdx.Input;

/**
 * Created by Carl on 24/05/2019.
 */
public class InputState
{
    private boolean[] keys;

    public InputState()
    {
        keys = new boolean[Input.Keys.class.getFields().length];
    }

    public void keyDown(int keyCode)
    {
        keys[keyCode] = true;
    }

    public void keyUp(int keyCode)
    {
        keys[keyCode] = false;
    }

    public boolean isKeyDown(int keyCode)
    {
        return keys[keyCode];
    }
}
