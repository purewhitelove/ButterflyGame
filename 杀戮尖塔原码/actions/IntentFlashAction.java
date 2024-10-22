// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntentFlashAction.java

package com.megacrit.cardcrawl.actions;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.actions:
//            AbstractGameAction

public class IntentFlashAction extends AbstractGameAction
{

    public IntentFlashAction(AbstractMonster m)
    {
        if(Settings.FAST_MODE)
            startDuration = Settings.ACTION_DUR_MED;
        else
            startDuration = Settings.ACTION_DUR_XLONG;
        duration = startDuration;
        this.m = m;
        actionType = AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(duration == startDuration)
            m.flashIntent();
        tickDuration();
    }

    private AbstractMonster m;
}
