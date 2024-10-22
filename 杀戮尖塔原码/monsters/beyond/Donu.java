// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Donu.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

public class Donu extends AbstractMonster
{

    public Donu()
    {
        super(NAME, "Donu", 250, 0.0F, -20F, 390F, 390F, null, 100F, 20F);
        loadAnimation("images/monsters/theForest/donu/skeleton.atlas", "images/monsters/theForest/donu/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        stateData.setMix("Attack_2", "Idle", 0.1F);
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -200F * Settings.scale;
        dialogY = 10F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(265);
        else
            setHp(250);
        if(AbstractDungeon.ascensionLevel >= 4)
            beamDmg = 12;
        else
            beamDmg = 10;
        damage.add(new DamageInfo(this, beamDmg));
        isAttacking = false;
    }

    public void changeState(String stateName)
    {
        String s = stateName;
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
            state.setAnimation(0, "Attack_2", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 19)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 0: // '\0'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            for(int i = 0; i < 2; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));

            isAttacking = false;
            break;

        case 2: // '\002'
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 3), 3)))
            {
                m = (AbstractMonster)iterator.next();
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_DONU_DEFENSE"));
            }

            isAttacking = true;
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(isAttacking)
            setMove((byte)0, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
        else
            setMove(CIRCLE_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
    }

    public void die()
    {
        super.die();
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            useFastShakeAnimation(5F);
            CardCrawlGame.screenShake.rumble(4F);
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("DONUT");
            UnlockTracker.unlockAchievement("SHAPES");
            onFinalBossVictoryLogic();
        }
    }

    public static final String ID = "Donu";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP = 250;
    public static final int A_2_HP = 265;
    private static final byte BEAM = 0;
    private static final byte CIRCLE_OF_PROTECTION = 2;
    private static final int ARTIFACT_AMT = 2;
    private static final int BEAM_DMG = 10;
    private static final int BEAM_AMT = 2;
    private static final int A_2_BEAM_DMG = 12;
    private int beamDmg;
    private static final String CIRCLE_NAME;
    private static final int CIRCLE_STR_AMT = 3;
    private boolean isAttacking;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Donu");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        CIRCLE_NAME = MOVES[0];
    }
}
