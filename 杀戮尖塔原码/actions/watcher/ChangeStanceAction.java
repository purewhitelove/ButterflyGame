// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChangeStanceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.CannotChangeStancePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import java.util.*;

public class ChangeStanceAction extends AbstractGameAction
{

    public ChangeStanceAction(String stanceId)
    {
        newStance = null;
        duration = Settings.ACTION_DUR_FAST;
        id = stanceId;
    }

    public ChangeStanceAction(AbstractStance newStance)
    {
        this(newStance.ID);
        this.newStance = newStance;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.hasPower("CannotChangeStancePower"))
            {
                isDone = true;
                return;
            }
            AbstractStance oldStance = AbstractDungeon.player.stance;
            if(!oldStance.ID.equals(id))
            {
                if(newStance == null)
                    newStance = AbstractStance.getStanceFromName(id);
                AbstractPower p;
                for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onChangeStance(oldStance, newStance))
                    p = (AbstractPower)iterator.next();

                AbstractRelic r;
                for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onChangeStance(oldStance, newStance))
                    r = (AbstractRelic)iterator1.next();

                oldStance.onExitStance();
                AbstractDungeon.player.stance = newStance;
                newStance.onEnterStance();
                if(AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(newStance.ID))
                {
                    int currentCount = ((Integer)AbstractDungeon.actionManager.uniqueStancesThisCombat.get(newStance.ID)).intValue();
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(newStance.ID, Integer.valueOf(currentCount + 1));
                } else
                {
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(newStance.ID, Integer.valueOf(1));
                }
                AbstractDungeon.player.switchedStance();
                AbstractCard c;
                for(Iterator iterator2 = AbstractDungeon.player.discardPile.group.iterator(); iterator2.hasNext(); c.triggerExhaustedCardsOnStanceChange(newStance))
                    c = (AbstractCard)iterator2.next();

                AbstractDungeon.player.onStanceChange(id);
            }
            AbstractDungeon.onModifyPower();
            if(Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        tickDuration();
    }

    private String id;
    private AbstractStance newStance;
}
