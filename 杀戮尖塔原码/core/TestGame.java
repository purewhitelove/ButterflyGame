// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestGame.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TestGame
    implements ApplicationListener
{

    public TestGame()
    {
        camera = new OrthographicCamera();
    }

    public void create()
    {
        viewport = new FitViewport(1600F, 900F, camera);
        viewport.apply();
        sb = new SpriteBatch();
        texture = new Texture("images/whiteScreen.png");
    }

    public void render()
    {
        sb.begin();
        sb.setColor(Color.RED);
        sb.draw(texture, 0.0F, 0.0F, 1600F, 900F);
        sb.end();
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
        camera.update();
    }

    public void pause()
    {
    }

    public void resume()
    {
    }

    public void dispose()
    {
    }

    private OrthographicCamera camera;
    public static FitViewport viewport;
    private SpriteBatch sb;
    private Texture texture;
}
