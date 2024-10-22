// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayTopCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            EmptyDeckShuffleAction

public class PlayTopCardAction extends AbstractGameAction
{

    public PlayTopCardAction(AbstractCreature target, boolean exhausts)
    {
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        source = AbstractDungeon.player;
        this.target = target;
        exhaustCards = exhausts;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0)
            {
                isDone = true;
                return;
            }
            if(AbstractDungeon.player.drawPile.isEmpty())
            {
                addToTop(new PlayTopCardAction(target, exhaustCards));
                addToTop(new EmptyDeckShuffleAction());
                isDone = true;
                return;
            }
            if(!AbstractDungeon.player.drawPile.isEmpty())
            {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200F * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0F + 200F * Settings.xScale;
                card.target_y = (float)Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                addToTop(new NewQueueCardAction(card, target, false, true));
                addToTop(new UnlimboAction(card));
                if(!Settings.FAST_MODE)
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                else
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
            isDone = true;
        }
    }

    private boolean exhaustCards;
}
