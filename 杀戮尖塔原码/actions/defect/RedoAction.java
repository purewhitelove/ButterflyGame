// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RedoAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.actions.defect:
//            ChannelAction, EvokeOrbAction

public class RedoAction extends AbstractGameAction
{

    public RedoAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(!AbstractDungeon.player.orbs.isEmpty())
        {
            orb = (AbstractOrb)AbstractDungeon.player.orbs.get(0);
            if(orb instanceof EmptyOrbSlot)
            {
                isDone = true;
            } else
            {
                addToTop(new ChannelAction(orb, false));
                addToTop(new EvokeOrbAction(1));
            }
        }
        isDone = true;
    }

    private AbstractOrb orb;
}
