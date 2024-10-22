// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContemplateAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class ContemplateAction extends AbstractGameAction
{

    public ContemplateAction(boolean upgraded)
    {
        cannotUpgrade = new ArrayList();
        duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        p = AbstractDungeon.player;
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
                    if(!c.canUpgrade())
                        cannotUpgrade.add(c);
                } while(true);
                if(cannotUpgrade.size() == p.hand.group.size())
                {
                    isDone = true;
                    return;
                }
                if(p.hand.group.size() - cannotUpgrade.size() != 1)
                    break label1;
                iterator = p.hand.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break label1;
                    c = (AbstractCard)iterator.next();
                } while(!c.canUpgrade());
                c.upgrade();
                c.superFlash();
                isDone = true;
                return;
            }
            if(upgraded)
            {
                p.hand.group.removeAll(cannotUpgrade);
                if(p.hand.group.size() > 1)
                {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, true);
                    tickDuration();
                    return;
                }
                if(p.hand.group.size() == 1)
                {
                    p.hand.getTopCard().upgrade();
                    p.hand.getTopCard().superFlash();
                    returnCards();
                    isDone = true;
                }
            } else
            {
                AbstractCard c = (AbstractCard)p.hand.group.get(AbstractDungeon.cardRandomRng.random(0, p.hand.group.size() - 1));
                c.upgrade();
                c.superFlash();
                isDone = true;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator1.hasNext(); p.hand.addToTop(c))
            {
                c = (AbstractCard)iterator1.next();
                c.upgrade();
                c.superFlash();
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
        for(Iterator iterator = cannotUpgrade.iterator(); iterator.hasNext(); p.hand.addToTop(c))
            c = (AbstractCard)iterator.next();

        p.hand.refreshHandLayout();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private boolean upgraded;
    private AbstractPlayer p;
    private ArrayList cannotUpgrade;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
        TEXT = uiStrings.TEXT;
    }
}
