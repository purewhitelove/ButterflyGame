// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnimatedNpc.java

package com.megacrit.cardcrawl.characters;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;

public class AnimatedNpc
{

    public AnimatedNpc(float x, float y, String atlasUrl, String skeletonUrl, String trackName)
    {
        atlas = null;
        loadAnimation(atlasUrl, skeletonUrl, 1.0F);
        skeleton.setPosition(x, y);
        state.setAnimation(0, trackName, true);
        state.setTimeScale(1.0F);
    }

    private void loadAnimation(String atlasUrl, String skeletonUrl, float scale)
    {
        atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(Settings.renderScale / scale);
        com.esotericsoftware.spine.SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        skeleton = new Skeleton(skeletonData);
        skeleton.setColor(Color.WHITE);
        stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
    }

    public void render(SpriteBatch sb)
    {
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(skeleton);
        skeleton.updateWorldTransform();
        skeleton.setFlip(false, false);
        skeleton.setColor(Color.WHITE);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
    }

    public void render(SpriteBatch sb, Color color)
    {
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(skeleton);
        skeleton.updateWorldTransform();
        skeleton.setFlip(false, false);
        skeleton.setColor(color);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
        atlas.dispose();
    }

    public void setTimeScale(float setScale)
    {
        state.setTimeScale(setScale);
    }

    public void addListener(HeartAnimListener listener)
    {
        state.addListener(listener);
    }

    private TextureAtlas atlas;
    public Skeleton skeleton;
    private AnimationState state;
    private AnimationStateData stateData;
}
