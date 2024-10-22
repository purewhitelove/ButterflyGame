// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShaderEffect.java

package com.megacrit.cardcrawl.vfx.shader;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.*;
import java.util.ArrayList;

public class ShaderEffect
{

    public ShaderEffect(FrameBuffer frameBuffer)
    {
        time = 0.0F;
        coords = new Vector3(0.0F, 0.0F, 0.0F);
        heatRegions = new ArrayList();
        heatCoords = new ArrayList();
        heatDimensions = new ArrayList();
        shader = new ShaderProgram(Gdx.files.internal("shaders/water/vertex.vs").readString(), Gdx.files.internal("shaders/water/fragment.fs").readString());
        heatRegions.add(new TextureRegion((Texture)frameBuffer.getColorBufferTexture()));
        heatCoords.add(new Vector2(0.0F, 0.0F));
        heatDimensions.add(new Vector2(32F, 32F));
    }

    public void update()
    {
        float dt = Gdx.graphics.getDeltaTime();
        time += dt;
        float angle = time * 6.283185F;
        if(angle > 6.283185F)
            angle -= 6.283185F;
        Gdx.gl20.glBlendFunc(770, 771);
        Gdx.gl20.glEnable(3042);
        shader.begin();
        shader.setUniformf("timedelta", -angle);
        shader.end();
    }

    public void render(SpriteBatch sb, FrameBuffer frameBuffer)
    {
        sb.begin();
        for(int i = 0; i < heatRegions.size(); i++)
        {
            TextureRegion region = (TextureRegion)heatRegions.get(i);
            coords.set(((Vector2)heatCoords.get(i)).x, ((Vector2)heatCoords.get(i)).y, 0.0F);
            region.setTexture((Texture)frameBuffer.getColorBufferTexture());
            region.setRegion(coords.x, coords.y, ((Vector2)heatDimensions.get(i)).x * 1.0F, ((Vector2)heatDimensions.get(i)).y * 1.0F);
            sb.draw(region, coords.x, coords.y, ((Vector2)heatDimensions.get(i)).x * 1.0F, ((Vector2)heatDimensions.get(i)).y * 1.0F);
        }

    }

    private ArrayList heatRegions;
    private ArrayList heatCoords;
    private ArrayList heatDimensions;
    private Vector3 coords;
    private float time;
    private ShaderProgram shader;
}
