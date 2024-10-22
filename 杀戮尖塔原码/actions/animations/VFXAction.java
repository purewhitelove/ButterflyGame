// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VFXAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class VFXAction extends AbstractGameAction
{

    public VFXAction(AbstractGameEffect effect)
    {
        this(null, effect, 0.0F);
    }

    public VFXAction(AbstractGameEffect effect, float duration)
    {
        this(null, effect, duration);
    }

    public VFXAction(AbstractCreature source, AbstractGameEffect effect, float duration)
    {
        isTopLevelEffect = false;
        setValues(source, source);
        this.effect = effect;
        this.duration = duration;
        startingDuration = duration;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public VFXAction(AbstractCreature source, AbstractGameEffect effect, float duration, boolean topLevel)
    {
        isTopLevelEffect = false;
        setValues(source, source);
        this.effect = effect;
        this.duration = duration;
        startingDuration = duration;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        isTopLevelEffect = topLevel;
    }

    public void update()
    {
        if(duration == startingDuration)
            if(isTopLevelEffect)
                AbstractDungeon.topLevelEffects.add(effect);
            else
                AbstractDungeon.effectList.add(effect);
        tickDuration();
    }

    private AbstractGameEffect effect;
    private float startingDuration;
    private boolean isTopLevelEffect;
}
