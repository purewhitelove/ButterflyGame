// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplyBulletTimeAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class ApplyBulletTimeAction extends AbstractGameAction
{

    public ApplyBulletTimeAction()
    {
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.setCostForTurn(-9))
            c = (AbstractCard)iterator.next();

        isDone = true;
    }
}
