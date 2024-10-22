// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawPileToHandAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawPileToHandAction extends AbstractGameAction
{

    public DrawPileToHandAction(int amount, com.megacrit.cardcrawl.cards.AbstractCard.CardType type)
    {
        p = AbstractDungeon.player;
        setValues(p, p, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_MED;
        typeToCheck = type;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED)
        {
            if(p.drawPile.isEmpty())
            {
                isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            Iterator iterator = p.drawPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == typeToCheck)
                    tmp.addToRandomSpot(c);
            } while(true);
            if(tmp.size() == 0)
            {
                isDone = true;
                return;
            }
            for(int i = 0; i < amount; i++)
            {
                if(tmp.isEmpty())
                    continue;
                tmp.shuffle();
                AbstractCard card = tmp.getBottomCard();
                tmp.removeCard(card);
                if(p.hand.size() == 10)
                {
                    p.drawPile.moveToDiscardPile(card);
                    p.createHandIsFullDialog();
                } else
                {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    p.drawPile.removeCard(card);
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
            }

            isDone = true;
        }
        tickDuration();
    }

    private AbstractPlayer p;
    private com.megacrit.cardcrawl.cards.AbstractCard.CardType typeToCheck;
}
