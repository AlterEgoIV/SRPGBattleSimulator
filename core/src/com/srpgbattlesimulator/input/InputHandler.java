package com.srpgbattlesimulator.input;

/**
 * Created by Carl on 29/07/2019.
 */
public abstract class InputHandler
{
    protected InputState inputState;

    public InputHandler(InputState inputState)
    {
        this.inputState = inputState;
    }

    public abstract void update();
}
