// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddCardToDeckAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

public class AddCardToDeckAction extends AbstractGameAction
{

    public AddCardToDeckAction(AbstractCard card)
    {
        cardToObtain = card;
        duration = 0.5F;
    }

    public void update()
    {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardToObtain, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        isDone = true;
    }

    AbstractCard cardToObtain;
}
