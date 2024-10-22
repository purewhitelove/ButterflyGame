// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhumeAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.CorruptionPower;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class ExhumeAction extends AbstractGameAction
{

    public ExhumeAction(boolean upgrade)
    {
        exhumes = new ArrayList();
        this.upgrade = upgrade;
        p = AbstractDungeon.player;
        setValues(p, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.hand.size() == 10)
            {
                AbstractDungeon.player.createHandIsFullDialog();
                isDone = true;
                return;
            }
            if(p.exhaustPile.isEmpty())
            {
                isDone = true;
                return;
            }
            Iterator c;
            if(p.exhaustPile.size() == 1)
            {
                if(((AbstractCard)p.exhaustPile.group.get(0)).cardID.equals("Exhume"))
                {
                    isDone = true;
                    return;
                }
                c = p.exhaustPile.getTopCard();
                c.unfadeOut();
                p.hand.addToHand(c);
                if(AbstractDungeon.player.hasPower("Corruption") && ((AbstractCard) (c)).type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.setCostForTurn(-9);
                p.exhaustPile.removeCard(c);
                if(upgrade && c.canUpgrade())
                    c.upgrade();
                c.unhover();
                c.fadingOut = false;
                isDone = true;
                return;
            }
            AbstractCard c;
            for(c = p.exhaustPile.group.iterator(); c.hasNext(); c.unfadeOut())
            {
                c = (AbstractCard)c.next();
                c.stopGlowing();
                c.unhover();
            }

            c = p.exhaustPile.group.iterator();
            do
            {
                if(!c.hasNext())
                    break;
                AbstractCard derp = (AbstractCard)c.next();
                if(derp.cardID.equals("Exhume"))
                {
                    c.remove();
                    exhumes.add(derp);
                }
            } while(true);
            if(p.exhaustPile.isEmpty())
            {
                p.exhaustPile.group.addAll(exhumes);
                exhumes.clear();
                isDone = true;
                return;
            } else
            {
                AbstractDungeon.gridSelectScreen.open(p.exhaustPile, 1, TEXT[0], false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); c.unhover())
            {
                c = (AbstractCard)iterator.next();
                p.hand.addToHand(c);
                if(AbstractDungeon.player.hasPower("Corruption") && c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                    c.setCostForTurn(-9);
                p.exhaustPile.removeCard(c);
                if(upgrade && c.canUpgrade())
                    c.upgrade();
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            p.exhaustPile.group.addAll(exhumes);
            exhumes.clear();
            for(Iterator iterator1 = p.exhaustPile.group.iterator(); iterator1.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator1.next();
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }

        }
        tickDuration();
    }

    private AbstractPlayer p;
    private final boolean upgrade;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private ArrayList exhumes;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
