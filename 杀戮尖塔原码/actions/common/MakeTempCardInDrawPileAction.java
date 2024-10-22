// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MakeTempCardInDrawPileAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import java.util.ArrayList;

public class MakeTempCardInDrawPileAction extends AbstractGameAction
{

    public MakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom, float cardX, float cardY)
    {
        UnlockTracker.markCardAsSeen(card.cardID);
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        duration = startDuration;
        cardToMake = card;
        this.randomSpot = randomSpot;
        this.autoPosition = autoPosition;
        this.toBottom = toBottom;
        x = cardX;
        y = cardY;
    }

    public MakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom)
    {
        this(card, amount, randomSpot, autoPosition, toBottom, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
    }

    public MakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean shuffleInto, boolean autoPosition)
    {
        this(card, amount, shuffleInto, autoPosition, false);
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(amount < 6)
            {
                for(int i = 0; i < amount; i++)
                {
                    AbstractCard c = cardToMake.makeStatEquivalentCopy();
                    if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
                        c.upgrade();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, x, y, randomSpot, autoPosition, toBottom));
                }

            } else
            {
                for(int i = 0; i < amount; i++)
                {
                    AbstractCard c = cardToMake.makeStatEquivalentCopy();
                    if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
                        c.upgrade();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, randomSpot, toBottom));
                }

            }
            duration -= Gdx.graphics.getDeltaTime();
        }
        tickDuration();
    }

    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean autoPosition;
    private boolean toBottom;
    private float x;
    private float y;
}
