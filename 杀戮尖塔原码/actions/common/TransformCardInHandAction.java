// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransformCardInHandAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class TransformCardInHandAction extends AbstractGameAction
{

    public TransformCardInHandAction(int index, AbstractCard replacement)
    {
        handIndex = index;
        this.replacement = replacement;
        if(Settings.FAST_MODE)
            startDuration = 0.05F;
        else
            startDuration = 0.15F;
        duration = startDuration;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            AbstractCard target = (AbstractCard)AbstractDungeon.player.hand.group.get(handIndex);
            replacement.current_x = target.current_x;
            replacement.current_y = target.current_y;
            replacement.target_x = target.target_x;
            replacement.target_y = target.target_y;
            replacement.drawScale = 1.0F;
            replacement.targetDrawScale = target.targetDrawScale;
            replacement.angle = target.angle;
            replacement.targetAngle = target.targetAngle;
            replacement.superFlash(Color.WHITE.cpy());
            AbstractDungeon.player.hand.group.set(handIndex, replacement);
            AbstractDungeon.player.hand.glowCheck();
        }
        tickDuration();
    }

    private AbstractCard replacement;
    private int handIndex;
}
