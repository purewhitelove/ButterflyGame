// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LosePercentHPAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            LoseHPAction

public class LosePercentHPAction extends AbstractGameAction
{

    public LosePercentHPAction(int percent)
    {
        amount = percent;
    }

    public void update()
    {
        float percentConversion = (float)amount / 100F;
        int amountToLose = (int)((float)AbstractDungeon.player.currentHealth * percentConversion);
        addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, amountToLose, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        isDone = true;
    }
}
