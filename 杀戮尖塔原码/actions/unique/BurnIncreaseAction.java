// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BurnIncreaseAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class BurnIncreaseAction extends AbstractGameAction
{

    public BurnIncreaseAction()
    {
        gotBurned = false;
        duration = 3F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(duration == 3F)
        {
            Iterator iterator = AbstractDungeon.player.discardPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c instanceof Burn)
                    c.upgrade();
            } while(true);
            iterator = AbstractDungeon.player.drawPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c instanceof Burn)
                    c.upgrade();
            } while(true);
        }
        if(duration < 1.5F && !gotBurned)
        {
            gotBurned = true;
            Burn b = new Burn();
            b.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(b));
            Burn c = new Burn();
            c.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
            Burn d = new Burn();
            d.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(d));
        }
        tickDuration();
    }

    private static final float DURATION = 3F;
    private boolean gotBurned;
}
