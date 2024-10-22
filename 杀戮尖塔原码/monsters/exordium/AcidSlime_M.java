// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AcidSlime_M.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

public class AcidSlime_M extends AbstractMonster
{

    public AcidSlime_M(float x, float y)
    {
        this(x, y, 0, 32);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(29, 34);
        else
            setHp(28, 32);
    }

    public AcidSlime_M(float x, float y, int poisonAmount, int newHealth)
    {
        super(NAME, "AcidSlime_M", newHealth, 0.0F, 0.0F, 170F, 130F, null, x, y, true);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            damage.add(new DamageInfo(this, 8));
            damage.add(new DamageInfo(this, 12));
        } else
        {
            damage.add(new DamageInfo(this, 7));
            damage.add(new DamageInfo(this, 10));
        }
        if(poisonAmount >= 1)
            powers.add(new PoisonPower(this, this, poisonAmount));
        loadAnimation("images/monsters/theBottom/slimeM/skeleton.atlas", "images/monsters/theBottom/slimeM/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        state.addListener(new SlimeAnimListener());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 1));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;
        }
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num < 40)
            {
                if(lastTwoMoves((byte)1))
                {
                    if(AbstractDungeon.aiRng.randomBoolean())
                        setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                    else
                        setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                } else
                {
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                }
            } else
            if(num < 80)
            {
                if(lastTwoMoves((byte)2))
                {
                    if(AbstractDungeon.aiRng.randomBoolean(0.5F))
                        setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                    else
                        setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                } else
                {
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                }
            } else
            if(lastMove((byte)4))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            } else
            {
                setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            }
        } else
        if(num < 30)
        {
            if(lastTwoMoves((byte)1))
            {
                if(AbstractDungeon.aiRng.randomBoolean())
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                else
                    setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            } else
            {
                setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            }
        } else
        if(num < 70)
        {
            if(lastMove((byte)2))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                    setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
                else
                    setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            } else
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            }
        } else
        if(lastTwoMoves((byte)4))
        {
            if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                setMove(WOUND_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            else
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
        } else
        {
            setMove(WEAK_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
        }
    }

    public void die()
    {
        super.die();
        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead() && (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
        {
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("SLIME");
            UnlockTracker.unlockAchievement("SLIME_BOSS");
        }
    }

    public static final String ID = "AcidSlime_M";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final String WOUND_NAME;
    private static final String WEAK_NAME;
    public static final int HP_MIN = 28;
    public static final int HP_MAX = 32;
    public static final int A_2_HP_MIN = 29;
    public static final int A_2_HP_MAX = 34;
    public static final int W_TACKLE_DMG = 7;
    public static final int WOUND_COUNT = 1;
    public static final int N_TACKLE_DMG = 10;
    public static final int A_2_W_TACKLE_DMG = 8;
    public static final int A_2_N_TACKLE_DMG = 12;
    public static final int WEAK_TURNS = 1;
    private static final byte WOUND_TACKLE = 1;
    private static final byte NORMAL_TACKLE = 2;
    private static final byte WEAK_LICK = 4;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AcidSlime_M");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        WOUND_NAME = MOVES[0];
        WEAK_NAME = MOVES[1];
    }
}
