// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttackFromDeckToHandAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class AttackFromDeckToHandAction extends AbstractGameAction
{

    public AttackFromDeckToHandAction(int amount)
    {
        p = AbstractDungeon.player;
        setValues(p, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_MED;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED)
        {
            CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            Iterator iterator1 = p.drawPile.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
                    tmp.addToRandomSpot(c);
            } while(true);
            if(tmp.size() == 0)
            {
                isDone = true;
                return;
            }
            if(tmp.size() == 1)
            {
                AbstractCard card = tmp.getTopCard();
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
                isDone = true;
                return;
            } else
            {
                AbstractDungeon.gridSelectScreen.open(tmp, amount, TEXT[0], false);
                tickDuration();
                return;
            }
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
        {
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); p.hand.applyPowers())
            {
                AbstractCard c = (AbstractCard)iterator.next();
                c.unhover();
                if(p.hand.size() == 10)
                {
                    p.drawPile.moveToDiscardPile(c);
                    p.createHandIsFullDialog();
                } else
                {
                    p.drawPile.removeCard(c);
                    p.hand.addToTop(c);
                }
                p.hand.refreshHandLayout();
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AttackFromDeckToHandAction");
        TEXT = uiStrings.TEXT;
    }
}
