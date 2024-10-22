// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObtainPotionAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;

public class ObtainPotionAction extends AbstractGameAction
{

    public ObtainPotionAction(AbstractPotion potion)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_XFAST;
        this.potion = potion;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_XFAST)
            if(AbstractDungeon.player.hasRelic("Sozu"))
                AbstractDungeon.player.getRelic("Sozu").flash();
            else
                AbstractDungeon.player.obtainPotion(potion);
        tickDuration();
    }

    private AbstractPotion potion;
    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractPotion");
        TEXT = uiStrings.TEXT;
    }
}
