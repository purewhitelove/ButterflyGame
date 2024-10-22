// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShakeScreenAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;

public class ShakeScreenAction extends AbstractGameAction
{

    public ShakeScreenAction(float duration, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur dur, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity intensity)
    {
        this.duration = duration;
        startDur = duration;
        shakeDur = dur;
        this.intensity = intensity;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(duration == startDur)
            CardCrawlGame.screenShake.shake(intensity, shakeDur, false);
        tickDuration();
    }

    private float startDur;
    com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur shakeDur;
    com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity intensity;
}
