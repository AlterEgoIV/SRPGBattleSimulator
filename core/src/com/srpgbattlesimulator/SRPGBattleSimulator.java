package com.srpgbattlesimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.srpgbattlesimulator.gamemodes.Battle;
import com.srpgbattlesimulator.input.InputState;
import com.srpgbattlesimulator.rendering.Renderable;
import com.srpgbattlesimulator.rendering.Renderer;

import java.util.ArrayList;
import java.util.List;

public class SRPGBattleSimulator extends ApplicationAdapter implements InputProcessor
{
	private InputState inputState;
	private List<Renderable> renderables;
	private Battle battle;
	private Renderer renderer;

	@Override
	public void create()
	{
		Gdx.input.setInputProcessor(this);
		inputState = new InputState();
		renderables = new ArrayList<Renderable>();
		battle = new Battle(inputState, renderables);
		renderer = new Renderer(renderables);
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!battle.isOver())
		{
			battle.update();
		}

		renderer.render();
	}
	
	@Override
	public void dispose()
	{
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		inputState.keyDown(keycode);

		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		inputState.keyUp(keycode);

		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
