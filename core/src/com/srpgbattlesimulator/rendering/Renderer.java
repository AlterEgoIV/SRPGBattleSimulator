package com.srpgbattlesimulator.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.srpgbattlesimulator.rendering.Renderable;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.rendering.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 06/05/2019.
 */
public class Renderer
{
    private List<Renderable> renderables;
    private List<Sprite> sprites;
    private List<Shape> shapes;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    public Renderer(List<Renderable> renderables)
    {
        this.renderables = renderables;
        this.sprites = new ArrayList<Sprite>();
        this.shapes = new ArrayList<Shape>();
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    public void render()
    {
        initialiseRenderables();
        renderSprites();
        renderShapes();
        clearRenderables();
    }

    private void renderSprites()
    {
        spriteBatch.begin();
        for(Sprite sprite : sprites)
        {

        }
        spriteBatch.end();
    }

    private void renderShapes()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin();
        for(Shape shape : shapes)
        {
            renderShape(shape, false);
            Gdx.gl20.glLineWidth(shape.getOutlineThickness());
            renderShape(shape, true);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderShape(Shape shape, boolean renderOutline)
    {
        shapeRenderer.set(renderOutline ? ShapeType.Line : ShapeType.Filled);
        shapeRenderer.setColor(renderOutline ? shape.outlineColor : shape.fillColor);

        switch(shape.getShapeName())
        {
            case RECT:
            {
                shapeRenderer.rect(shape.position.x - shape.getWidth() / 2, shape.position.y - shape.getHeight() / 2, shape.getWidth(), shape.getHeight());
                break;
            }

            case ELLIPSE:
            {
                shapeRenderer.ellipse(shape.position.x - shape.getWidth() / 2, shape.position.y - shape.getHeight() / 2, shape.getWidth(), shape.getHeight());
                break;
            }
        }
    }

    private void initialiseRenderables()
    {
        for(Renderable renderable : renderables)
        {
            if(renderable instanceof Sprite)
            {
                sprites.add((Sprite)renderable);
            }
            else if(renderable instanceof Shape)
            {
                shapes.add((Shape)renderable);
            }
        }
    }

    private void clearRenderables()
    {
        renderables.clear();
        sprites.clear();
        shapes.clear();
    }

    public void dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
