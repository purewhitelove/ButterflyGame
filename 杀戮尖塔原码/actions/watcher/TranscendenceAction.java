// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TranscendenceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class TranscendenceAction extends AbstractGameAction
{

    public TranscendenceAction()
    {
        duration = Settings.ACTION_DUR_MED;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED)
        {
            AbstractPlayer p = AbstractDungeon.player;
            Iterator iterator = p.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.canUpgrade() && (c.retain || c.selfRetain))
                {
                    c.superFlash();
                    c.upgrade();
                    c.applyPowers();
                }
            } while(true);
            isDone = true;
        }
    }
}
