// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeEater.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import java.util.ArrayList;

public class TimeEater extends AbstractMonster
{

    public TimeEater()
    {
        super(NAME, "TimeEater", 456, -10F, -30F, 476F, 410F, null, -50F, 30F);
        usedHaste = false;
        firstTurn = true;
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(480);
        else
            setHp(456);
        loadAnimation("images/monsters/theForest/timeEater/skeleton.atlas", "images/monsters/theForest/timeEater/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -200F * Settings.scale;
        dialogY = 10F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            reverbDmg = 8;
            headSlamDmg = 32;
        } else
        {
            reverbDmg = 7;
            headSlamDmg = 26;
        }
        damage.add(new DamageInfo(this, reverbDmg, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL));
        damage.add(new DamageInfo(this, headSlamDmg, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL));
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        UnlockTracker.markBossAsSeen("WIZARD");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
    }

    public void takeTurn()
    {
        if(firstTurn)
        {
            if(AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER)
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
            else
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            firstTurn = false;
        }
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            for(int i = 0; i < 3; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Settings.BLUE_TEXT_COLOR, com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            }

            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            if(AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));
            if(AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1], 0.5F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, maxHealth / 2 - currentHealth));
            if(AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, headSlamDmg));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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
            state.setAnimation(0, "Attack", false);
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

    protected void getMove(int num)
    {
        if(currentHealth < maxHealth / 2 && !usedHaste)
        {
            usedHaste = true;
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(num < 45)
            if(!lastTwoMoves((byte)2))
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
                return;
            } else
            {
                getMove(AbstractDungeon.aiRng.random(50, 99));
                return;
            }
        if(num < 80)
        {
            if(!lastMove((byte)4))
            {
                setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                return;
            }
            if(AbstractDungeon.aiRng.randomBoolean(0.66F))
            {
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 3, true);
                return;
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_DEBUFF);
                return;
            }
        }
        if(!lastMove((byte)3))
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_DEBUFF);
            return;
        } else
        {
            getMove(AbstractDungeon.aiRng.random(74));
            return;
        }
    }

    public void die()
    {
        if(!AbstractDungeon.getCurrRoom().cannotLose)
        {
            useFastShakeAnimation(5F);
            CardCrawlGame.screenShake.rumble(4F);
            super.die();
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("WIZARD");
            UnlockTracker.unlockAchievement("TIME_EATER");
            onFinalBossVictoryLogic();
        }
    }

    public static final String ID = "TimeEater";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP = 456;
    public static final int A_2_HP = 480;
    private static final byte REVERBERATE = 2;
    private static final byte RIPPLE = 3;
    private static final byte HEAD_SLAM = 4;
    private static final byte HASTE = 5;
    private static final int REVERB_DMG = 7;
    private static final int REVERB_AMT = 3;
    private static final int A_2_REVERB_DMG = 8;
    private static final int RIPPLE_BLOCK = 20;
    private static final int HEAD_SLAM_DMG = 26;
    private static final int A_2_HEAD_SLAM_DMG = 32;
    private int reverbDmg;
    private int headSlamDmg;
    private static final int HEAD_SLAM_STICKY = 1;
    private static final int RIPPLE_DEBUFF_TURNS = 1;
    private boolean usedHaste;
    private boolean firstTurn;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TimeEater");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
