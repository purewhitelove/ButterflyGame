// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThoughtBubble.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.DialogWord;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, SpeechTextEffect, CloudBubble

public class ThoughtBubble extends AbstractGameEffect
{

    public ThoughtBubble(float x, float y, String msg, boolean isPlayer)
    {
        this(x, y, 2.0F, msg, isPlayer);
    }

    public ThoughtBubble(float x, float y, float duration, String msg, boolean isPlayer)
    {
        bubbles = new ArrayList();
        if(msg == null)
        {
            isDone = true;
            return;
        }
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if((e instanceof ThoughtBubble) && !e.equals(this))
                ((ThoughtBubble)e).killClouds();
        } while(true);
        this.duration = duration;
        y += OFFSET_Y;
        if(isPlayer)
            x += OFFSET_X;
        else
            x -= OFFSET_X;
        AbstractDungeon.effectsQueue.add(new SpeechTextEffect(x, y, duration, msg, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(0.7F, 1.1F), y + CLOUD_H * MathUtils.random(0.1F, 0.3F), MathUtils.random(1.0F, 1.2F)));
        bubbles.add(new CloudBubble(x - CLOUD_W * MathUtils.random(0.7F, 1.1F), y + CLOUD_H * MathUtils.random(0.1F, 0.3F), MathUtils.random(1.0F, 1.2F)));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(0.7F, 1.1F), y + CLOUD_H * MathUtils.random(-0.1F, -0.3F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x - CLOUD_W * MathUtils.random(0.7F, 1.1F), y + CLOUD_H * MathUtils.random(-0.1F, -0.3F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(-0.2F, 0.2F), y + CLOUD_H * MathUtils.random(0.65F, 0.72F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(-0.2F, 0.2F), y - CLOUD_H * MathUtils.random(0.65F, 0.72F), MathUtils.random(1.0F, 1.2F)));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(0.3F, 0.8F), y + CLOUD_H * MathUtils.random(0.3F, 0.7F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x - CLOUD_W * MathUtils.random(0.3F, 0.8F), y + CLOUD_H * MathUtils.random(0.3F, 0.7F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x + CLOUD_W * MathUtils.random(0.3F, 0.8F), y - CLOUD_H * MathUtils.random(0.3F, 0.7F), MathUtils.random(0.9F, 1.1F)));
        bubbles.add(new CloudBubble(x - CLOUD_W * MathUtils.random(0.3F, 0.8F), y - CLOUD_H * MathUtils.random(0.3F, 0.7F), MathUtils.random(0.9F, 1.1F)));
        float off_x;
        if(isPlayer)
            off_x = OFFSET_X;
        else
            off_x = -OFFSET_X;
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(-0.05F, -0.15F), y - OFFSET_Y * MathUtils.random(0.67F, 0.72F), MathUtils.random(0.4F, 0.45F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.07F, 0.15F), y - OFFSET_Y * MathUtils.random(0.65F, 0.7F), MathUtils.random(0.4F, 0.45F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.1F, 0.2F), y - OFFSET_Y * MathUtils.random(0.9F, 1.02F), MathUtils.random(0.35F, 0.4F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.3F, 0.35F), y - OFFSET_Y * MathUtils.random(1.05F, 1.1F), MathUtils.random(0.18F, 0.23F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.35F, 0.45F), y - OFFSET_Y * MathUtils.random(1.1F, 1.2F), MathUtils.random(0.1F, 0.13F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.45F, 0.5F), y - OFFSET_Y * MathUtils.random(1.1F, 1.16F), MathUtils.random(0.08F, 0.09F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.5F, 0.6F), y - OFFSET_Y * MathUtils.random(1.1F, 1.16F), MathUtils.random(0.08F, 0.09F)));
        bubbles.add(new CloudBubble(x - off_x * MathUtils.random(0.6F, 0.65F), y - OFFSET_Y * MathUtils.random(1.05F, 1.12F), MathUtils.random(0.08F, 0.09F)));
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.3F)
            killClouds();
        CloudBubble b;
        for(Iterator iterator = bubbles.iterator(); iterator.hasNext(); b.update())
            b = (CloudBubble)iterator.next();

    }

    private void killClouds()
    {
        CloudBubble b;
        for(Iterator iterator = bubbles.iterator(); iterator.hasNext(); b.kill())
            b = (CloudBubble)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        CloudBubble b;
        for(Iterator iterator = bubbles.iterator(); iterator.hasNext(); b.render(sb))
            b = (CloudBubble)iterator.next();

    }

    public void dispose()
    {
    }

    private static final float DEFAULT_DURATION = 2F;
    private static final float OFFSET_X;
    private static final float OFFSET_Y;
    private static final float CLOUD_W;
    private static final float CLOUD_H;
    private ArrayList bubbles;

    static 
    {
        OFFSET_X = 190F * Settings.scale;
        OFFSET_Y = 124F * Settings.scale;
        CLOUD_W = 100F * Settings.scale;
        CLOUD_H = 50F * Settings.scale;
    }
}
