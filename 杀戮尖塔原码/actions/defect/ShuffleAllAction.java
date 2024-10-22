// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShuffleAllAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.FtueTip;
import java.util.*;

public class ShuffleAllAction extends AbstractGameAction
{

    public ShuffleAllAction()
    {
        shuffled = false;
        vfxDone = false;
        count = 0;
        setValues(null, null, 0);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SHUFFLE;
        if(!((Boolean)TipTracker.tips.get("SHUFFLE_TIP")).booleanValue())
        {
            AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, com.megacrit.cardcrawl.ui.FtueTip.TipType.SHUFFLE);
            TipTracker.neverShowAgain("SHUFFLE_TIP");
        }
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onShuffle())
            r = (AbstractRelic)iterator.next();

    }

    public void update()
    {
        if(!shuffled)
        {
            shuffled = true;
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new PutOnDeckAction(p, p, 99, true));
            p.discardPile.shuffle(AbstractDungeon.shuffleRng);
        }
        if(!vfxDone)
        {
            Iterator c = AbstractDungeon.player.discardPile.group.iterator();
            if(c.hasNext())
            {
                count++;
                AbstractCard e = (AbstractCard)c.next();
                c.remove();
                if(count < 11)
                    AbstractDungeon.getCurrRoom().souls.shuffle(e, false);
                else
                    AbstractDungeon.getCurrRoom().souls.shuffle(e, true);
                return;
            }
            vfxDone = true;
        }
        isDone = true;
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private boolean shuffled;
    private boolean vfxDone;
    private int count;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
    }
}
