// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InnerPeaceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class InnerPeaceAction extends AbstractGameAction
{

    public InnerPeaceAction(int amount)
    {
        this.amount = amount;
    }

    public void update()
    {
        if(AbstractDungeon.player.stance.ID.equals("Calm"))
            addToTop(new DrawCardAction(amount));
        else
            addToTop(new ChangeStanceAction("Calm"));
        isDone = true;
    }
}
