// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnimationTestEffect.java

package com.megacrit.cardcrawl.vfx.deprecated;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AnimationTestEffect extends AbstractGameEffect
{

    public AnimationTestEffect()
    {
        scale = 1.0F;
        rotation = 0.0F;
        renderBehind = false;
        duration = 3F;
        atlas = new TextureAtlas(Gdx.files.internal("animations/skeleton.atlas"));
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(Settings.scale / 2.0F);
        com.esotericsoftware.spine.SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("animations/skeleton.json"));
        skeleton = new Skeleton(skeletonData);
        skeleton.setPosition(1250F, 20F);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
        state.setAnimation(0, "animation", true);
    }

    public void update()
    {
        skeleton.setPosition(InputHelper.mX, InputHelper.mY);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            atlas.dispose();
            isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
        atlas.dispose();
    }

    public float duration;
    public float startingDuration;
    protected Color color;
    protected float scale;
    protected float rotation;
    public boolean renderBehind;
    TextureAtlas atlas;
    Skeleton skeleton;
    AnimationState state;
}
