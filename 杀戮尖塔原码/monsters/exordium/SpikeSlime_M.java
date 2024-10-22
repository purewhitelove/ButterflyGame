// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpikeSlime_M.java

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
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

public class SpikeSlime_M extends AbstractMonster
{

    public SpikeSlime_M(float x, float y)
    {
        this(x, y, 0, 32);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(29, 34);
        else
            setHp(28, 32);
    }

    public SpikeSlime_M(float x, float y, int poisonAmount, int newHealth)
    {
        super(NAME, "SpikeSlime_M", newHealth, 0.0F, -25F, 170F, 130F, null, x, y, true);
        if(AbstractDungeon.ascensionLevel >= 2)
            damage.add(new DamageInfo(this, 10));
        else
            damage.add(new DamageInfo(this, 8));
        if(poisonAmount >= 1)
            powers.add(new PoisonPower(this, this, poisonAmount));
        loadAnimation("images/monsters/theBottom/slimeAltM/skeleton.atlas", "images/monsters/theBottom/slimeAltM/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 1));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num < 30)
            {
                if(lastTwoMoves((byte)1))
                    setMove(FRAIL_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
                else
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            } else
            if(lastMove((byte)4))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            else
                setMove(FRAIL_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
        } else
        if(num < 30)
        {
            if(lastTwoMoves((byte)1))
                setMove(FRAIL_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
        } else
        if(lastTwoMoves((byte)4))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
        else
            setMove(FRAIL_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
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

    public static final String ID = "SpikeSlime_M";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 28;
    public static final int HP_MAX = 32;
    public static final int A_2_HP_MIN = 29;
    public static final int A_2_HP_MAX = 34;
    public static final int TACKLE_DAMAGE = 8;
    public static final int WOUND_COUNT = 1;
    public static final int A_2_TACKLE_DAMAGE = 10;
    public static final int FRAIL_TURNS = 1;
    private static final byte FLAME_TACKLE = 1;
    private static final byte FRAIL_LICK = 4;
    private static final String FRAIL_NAME;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_M");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        FRAIL_NAME = MOVES[0];
    }
}
