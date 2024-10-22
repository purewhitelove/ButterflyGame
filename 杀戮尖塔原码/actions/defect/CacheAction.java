// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheAction.java

package com.megacrit.cardcrawl.actions.defect;

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

public class CacheAction extends AbstractGameAction
{

    public CacheAction(int amount)
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
            if(AbstractDungeon.player.drawPile.size() <= 1)
            {
                isDone = true;
                return;
            }
            if(amount == 1)
            {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.drawPile, amount, TEXT[0], false);
            } else
            {
                if(AbstractDungeon.player.drawPile.size() > amount)
                    amount = AbstractDungeon.player.drawPile.size();
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.drawPile, amount, TEXT[1], false);
            }
            tickDuration();
            return;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
        {
            for(int i = AbstractDungeon.gridSelectScreen.selectedCards.size() - 1; i > -1; i--)
            {
                ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(i)).unhover();
                p.drawPile.moveToDeck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(i), false);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CacheAction");
        TEXT = uiStrings.TEXT;
    }
}
