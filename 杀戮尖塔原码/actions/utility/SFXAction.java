// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SFXAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SFXAction extends AbstractGameAction
{

    public SFXAction(String key)
    {
        this(key, 0.0F, false);
    }

    public SFXAction(String key, float pitchVar)
    {
        this(key, pitchVar, false);
    }

    public SFXAction(String key, float pitchVar, boolean pitchAdjust)
    {
        this.pitchVar = 0.0F;
        adjust = false;
        this.key = key;
        this.pitchVar = pitchVar;
        adjust = pitchAdjust;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(!adjust)
            CardCrawlGame.sound.play(key, pitchVar);
        else
            CardCrawlGame.sound.playA(key, pitchVar);
        isDone = true;
    }

    private String key;
    private float pitchVar;
    private boolean adjust;
}
