// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OldFissionAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.actions.defect:
//            AnimateOrbAction, EvokeOrbAction, IncreaseMaxOrbAction

public class OldFissionAction extends AbstractGameAction
{

    public OldFissionAction()
    {
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            int orbCount = AbstractDungeon.player.filledOrbCount();
            for(int i = 0; i < orbCount; i++)
            {
                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeOrbAction(1));
            }

            addToBot(new IncreaseMaxOrbAction(orbCount));
        }
        tickDuration();
    }
}
