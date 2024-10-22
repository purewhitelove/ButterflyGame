// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transient.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

public class Transient extends AbstractMonster
{

    public Transient()
    {
        super(NAME, "Transient", 999, 0.0F, -15F, 370F, 340F, null, 0.0F, 20F);
        count = 0;
        loadAnimation("images/monsters/theForest/transient/skeleton.atlas", "images/monsters/theForest/transient/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        gold = 1;
        dialogX = -100F * Settings.scale;
        dialogY -= 20F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 2)
            startingDeathDmg = 40;
        else
            startingDeathDmg = 30;
        damage.add(new DamageInfo(this, startingDeathDmg));
        damage.add(new DamageInfo(this, startingDeathDmg + 10));
        damage.add(new DamageInfo(this, startingDeathDmg + 20));
        damage.add(new DamageInfo(this, startingDeathDmg + 30));
        damage.add(new DamageInfo(this, startingDeathDmg + 40));
        damage.add(new DamageInfo(this, startingDeathDmg + 50));
        damage.add(new DamageInfo(this, startingDeathDmg + 60));
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 17)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FadingPower(this, 6)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FadingPower(this, 5)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShiftingPower(this)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(count), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            count++;
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, startingDeathDmg + count * 10);
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hurt", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1941037640: 
            if(s.equals("ATTACK"))
                byte0 = 0;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public void die()
    {
        super.die();
        UnlockTracker.unlockAchievement("TRANSIENT");
    }

    protected void getMove(int num)
    {
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, startingDeathDmg + count * 10);
    }

    public static final String ID = "Transient";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP = 999;
    private int count;
    private static final int DEATH_DMG = 30;
    private static final int INCREMENT_DMG = 10;
    private static final int A_2_DEATH_DMG = 40;
    private int startingDeathDmg;
    private static final byte ATTACK = 1;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Transient");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
