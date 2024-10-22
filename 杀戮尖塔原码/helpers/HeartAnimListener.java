// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeartAnimListener.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            ScreenShake

public class HeartAnimListener
    implements com.esotericsoftware.spine.AnimationState.AnimationStateListener
{

    public HeartAnimListener()
    {
    }

    public void event(int trackIndex, Event event)
    {
        if(!AbstractDungeon.isScreenUp && event.getData().getName().equals("maxbeat"))
        {
            CardCrawlGame.sound.playAV("HEART_SIMPLE", MathUtils.random(-0.05F, 0.05F), 0.75F);
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
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
