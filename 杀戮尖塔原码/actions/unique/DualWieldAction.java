// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DualWieldAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class DualWieldAction extends AbstractGameAction
{

    public DualWieldAction(AbstractCreature source, int amount)
    {
        dupeAmount = 1;
        cannotDuplicate = new ArrayList();
        setValues(AbstractDungeon.player, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        duration = 0.25F;
        p = AbstractDungeon.player;
        dupeAmount = amount;
    }

    public void update()
    {
label0:
        {
label1:
            {
                if(duration != Settings.ACTION_DUR_FAST)
                    break label0;
                Iterator iterator = p.hand.group.iterator();
                AbstractCard c;
                do
                {
                    if(!iterator.hasNext())
                        break;
                    c = (AbstractCard)iterator.next();
                    if(!isDualWieldable(c))
                        cannotDuplicate.add(c);
                } while(true);
                if(cannotDuplicate.size() == p.hand.group.size())
                {
                    isDone = true;
                    return;
                }
                if(p.hand.group.size() - cannotDuplicate.size() != 1)
                    break label1;
                iterator = p.hand.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break label1;
                    c = (AbstractCard)iterator.next();
                } while(!isDualWieldable(c));
                for(int i = 0; i < dupeAmount; i++)
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));

                isDone = true;
                return;
            }
            p.hand.group.removeAll(cannotDuplicate);
            if(p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if(p.hand.group.size() == 1)
            {
                for(int i = 0; i < dupeAmount; i++)
                    addToTop(new MakeTempCardInHandAction(p.hand.getTopCard().makeStatEquivalentCopy()));

                returnCards();
                isDone = true;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for(Iterator iterator1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator1.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator1.next();
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                int i = 0;
                while(i < dupeAmount) 
                {
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                    i++;
                }
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }
        tickDuration();
    }

    private void returnCards()
    {
        AbstractCard c;
        for(Iterator iterator = cannotDuplicate.iterator(); iterator.hasNext(); p.hand.addToTop(c))
            c = (AbstractCard)iterator.next();

        p.hand.refreshHandLayout();
    }

    private boolean isDualWieldable(AbstractCard card)
    {
        return card.type.equals(com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK) || card.type.equals(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private int dupeAmount;
    private ArrayList cannotDuplicate;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = uiStrings.TEXT;
    }
}
