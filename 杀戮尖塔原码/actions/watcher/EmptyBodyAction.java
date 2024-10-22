// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyBodyAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class EmptyBodyAction extends AbstractGameAction
{

    public EmptyBodyAction(int additionalDraw)
    {
        this.additionalDraw = additionalDraw;
    }

    public void update()
    {
        if(AbstractDungeon.player.stance.ID.equals("Neutral"))
        {
            addToBot(new ChangeStanceAction("Neutral"));
            addToBot(new DrawCardAction(1 + additionalDraw));
        } else
        {
            addToBot(new DrawCardAction(1));
        }
        isDone = true;
    }

    private int additionalDraw;
}
