// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MakeTempCardInDiscardAndDeckAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import java.util.ArrayList;

public class MakeTempCardInDiscardAndDeckAction extends AbstractGameAction
{

    public MakeTempCardInDiscardAndDeckAction(AbstractCard card)
    {
        UnlockTracker.markCardAsSeen(card.cardID);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        duration = startDuration;
        cardToMake = card;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            AbstractCard tmp = cardToMake.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(tmp, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 10F * Settings.xScale, (float)Settings.HEIGHT / 2.0F, true, false));
            tmp = cardToMake.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(tmp));
            tmp.current_x = (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 10F * Settings.xScale;
            tmp.target_x = tmp.current_x;
        }
        tickDuration();
    }

    private AbstractCard cardToMake;
}
