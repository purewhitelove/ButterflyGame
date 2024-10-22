// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardAtEndOfTurnAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            DiscardAction

public class DiscardAtEndOfTurnAction extends AbstractGameAction
{

    public DiscardAtEndOfTurnAction()
    {
        duration = DURATION;
    }

    public void update()
    {
        if(duration == DURATION)
        {
            Iterator c = AbstractDungeon.player.hand.group.iterator();
            do
            {
                if(!c.hasNext())
                    break;
                AbstractCard e = (AbstractCard)c.next();
                if(e.retain || e.selfRetain)
                {
                    AbstractDungeon.player.limbo.addToTop(e);
                    c.remove();
                }
            } while(true);
            addToTop(new RestoreRetainedCardsAction(AbstractDungeon.player.limbo));
            if(!AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium"))
            {
                int tempSize = AbstractDungeon.player.hand.size();
                for(int i = 0; i < tempSize; i++)
                    addToTop(new DiscardAction(AbstractDungeon.player, null, AbstractDungeon.player.hand.size(), true, true));

            }
            ArrayList cards = (ArrayList)AbstractDungeon.player.hand.group.clone();
            Collections.shuffle(cards);
            AbstractCard c;
            for(Iterator iterator = cards.iterator(); iterator.hasNext(); c.triggerOnEndOfPlayerTurn())
                c = (AbstractCard)iterator.next();

            isDone = true;
        }
    }

    private static final float DURATION;

    static 
    {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
