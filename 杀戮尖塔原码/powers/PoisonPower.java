// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PoisonPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class PoisonPower extends AbstractPower
{

    public PoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
    {
        name = NAME;
        ID = "Poison";
        this.owner = owner;
        this.source = source;
        amount = poisonAmt;
        if(amount >= 9999)
            amount = 9999;
        updateDescription();
        loadRegion("poison");
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription()
    {
        if(owner == null || owner.isPlayer)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if(amount > 98 && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
            UnlockTracker.unlockAchievement("CATALYST");
    }

    public void atStartOfTurn()
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flashWithoutSound();
            addToBot(new PoisonLoseHpAction(owner, source, amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
        }
    }

    public static final String POWER_ID = "Poison";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private AbstractCreature source;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Poison");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
