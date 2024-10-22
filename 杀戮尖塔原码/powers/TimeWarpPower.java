// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeWarpPower.java

package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class TimeWarpPower extends AbstractPower
{

    public TimeWarpPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Time Warp";
        this.owner = owner;
        amount = 0;
        updateDescription();
        loadRegion("time");
        type = AbstractPower.PowerType.BUFF;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESC[0]).append(12).append(DESC[1]).append(2).append(DESC[2]).toString();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        flashWithoutSound();
        amount++;
        if(amount == 12)
        {
            amount = 0;
            playApplyPowerSfx();
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 2), 2)))
                m = (AbstractMonster)iterator.next();

        }
        updateDescription();
    }

    public static final String POWER_ID = "Time Warp";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESC[];
    private static final int STR_AMT = 2;
    private static final int COUNTDOWN_AMT = 12;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Time Warp");
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
