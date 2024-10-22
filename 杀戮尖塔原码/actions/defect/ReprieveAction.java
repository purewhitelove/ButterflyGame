// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReprieveAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import java.util.ArrayList;

public class ReprieveAction extends AbstractGameAction
{

    public ReprieveAction(int increaseAmt, AbstractMonster m)
    {
        duration = 0.0F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        focusIncrease = increaseAmt;
        targetMonster = m;
    }

    public void update()
    {
        if(targetMonster != null && targetMonster.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK && targetMonster.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF && targetMonster.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF && targetMonster.intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, focusIncrease), focusIncrease));
        else
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3F, TEXT[0], true));
        isDone = true;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private int focusIncrease;
    private AbstractMonster targetMonster;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReprieveAction");
        TEXT = uiStrings.TEXT;
    }
}
