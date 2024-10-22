// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnceasingTop.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class UnceasingTop extends AbstractRelic
{

    public UnceasingTop()
    {
        super("Unceasing Top", "top.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
        canDraw = false;
        disabledUntilEndOfTurn = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        canDraw = false;
    }

    public void atTurnStart()
    {
        canDraw = true;
        disabledUntilEndOfTurn = false;
    }

    public void disableUntilTurnEnds()
    {
        disabledUntilEndOfTurn = true;
    }

    public void onRefreshHand()
    {
        if(AbstractDungeon.actionManager.actions.isEmpty() && AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && canDraw && !AbstractDungeon.player.hasPower("No Draw") && !AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !disabledUntilEndOfTurn && (AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0))
        {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new UnceasingTop();
    }

    public static final String ID = "Unceasing Top";
    private boolean canDraw;
    private boolean disabledUntilEndOfTurn;
}
