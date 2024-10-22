// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlimeAnimListener.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Event;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SlimeAnimListener
    implements com.esotericsoftware.spine.AnimationState.AnimationStateListener
{

    public SlimeAnimListener()
    {
    }

    public void event(int trackIndex, Event event)
    {
        if(!AbstractDungeon.isScreenUp)
        {
            int roll = MathUtils.random(3);
            if(roll == 0)
                CardCrawlGame.sound.play("SLIME_BLINK_1");
            else
            if(roll == 1)
                CardCrawlGame.sound.play("SLIME_BLINK_2");
            else
            if(roll == 2)
                CardCrawlGame.sound.play("SLIME_BLINK_3");
            else
                CardCrawlGame.sound.play("SLIME_BLINK_4");
        }
    }

    public void complete(int i, int j)
    {
    }

    public void start(int i)
    {
    }

    public void end(int i)
    {
    }
}
