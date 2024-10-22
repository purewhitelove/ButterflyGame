// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScryAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.GoldenEye;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class ScryAction extends AbstractGameAction
{

    public ScryAction(int numCards)
    {
        amount = numCards;
        if(AbstractDungeon.player.hasRelic("GoldenEye"))
        {
            AbstractDungeon.player.getRelic("GoldenEye").flash();
            amount += 2;
        }
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public void update()
    {
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
            return;
        }
        if(duration == startingDuration)
        {
            AbstractPower p;
            for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.onScry())
                p = (AbstractPower)iterator.next();

            if(AbstractDungeon.player.drawPile.isEmpty())
            {
                isDone = true;
                return;
            }
            CardGroup tmpGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            if(amount != -1)
            {
                for(int i = 0; i < Math.min(amount, AbstractDungeon.player.drawPile.size()); i++)
                    tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));

            } else
            {
                AbstractCard c;
                for(Iterator iterator3 = AbstractDungeon.player.drawPile.group.iterator(); iterator3.hasNext(); tmpGroup.addToBottom(c))
                    c = (AbstractCard)iterator3.next();

            }
            AbstractDungeon.gridSelectScreen.open(tmpGroup, amount, true, TEXT[0]);
        } else
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c;
            for(Iterator iterator1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator1.hasNext(); AbstractDungeon.player.drawPile.moveToDiscardPile(c))
                c = (AbstractCard)iterator1.next();

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        AbstractCard c;
        for(Iterator iterator2 = AbstractDungeon.player.discardPile.group.iterator(); iterator2.hasNext(); c.triggerOnScry())
            c = (AbstractCard)iterator2.next();

        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private float startingDuration;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReprogramAction");
        TEXT = uiStrings.TEXT;
    }
}
