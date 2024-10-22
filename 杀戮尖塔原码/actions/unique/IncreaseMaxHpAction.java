// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IncreaseMaxHpAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IncreaseMaxHpAction extends AbstractGameAction
{

    public IncreaseMaxHpAction(AbstractMonster m, float increasePercent, boolean showEffect)
    {
        if(Settings.FAST_MODE)
            startDuration = Settings.ACTION_DUR_XFAST;
        else
            startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        this.showEffect = showEffect;
        this.increasePercent = increasePercent;
        target = m;
    }

    public void update()
    {
        if(duration == startDuration)
            target.increaseMaxHp(MathUtils.round((float)target.maxHealth * increasePercent), showEffect);
        tickDuration();
    }

    private boolean showEffect;
    private float increasePercent;
}
