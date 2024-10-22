// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnhoverCardAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;

public class UnhoverCardAction extends AbstractGameAction
{

    public UnhoverCardAction()
    {
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST && AbstractDungeon.player.hoveredCard != null)
        {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(AbstractDungeon.player.hoveredCard));
            AbstractDungeon.player.hoveredCard = null;
        }
    }
}
