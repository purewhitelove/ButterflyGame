// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheGuardian.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheGuardian extends AbstractMonster
{

    public TheGuardian()
    {
        super(NAME, "TheGuardian", 240, 0.0F, 95F, 440F, 350F, null, -50F, -100F);
        dmgThresholdIncrease = 10;
        whirlwindDamage = 5;
        twinSlamDamage = 8;
        whirlwindCount = 4;
        DEFENSIVE_BLOCK = 20;
        blockAmount = 9;
        thornsDamage = 3;
        VENT_DEBUFF = 2;
        isOpen = true;
        closeUpTriggered = false;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -100F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            setHp(250);
            dmgThreshold = 40;
        } else
        if(AbstractDungeon.ascensionLevel >= 9)
        {
            setHp(250);
            dmgThreshold = 35;
        } else
        {
            setHp(240);
            dmgThreshold = 30;
        }
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            fierceBashDamage = 36;
            rollDamage = 10;
        } else
        {
            fierceBashDamage = 32;
            rollDamage = 9;
        }
        damage.add(new DamageInfo(this, fierceBashDamage));
        damage.add(new DamageInfo(this, rollDamage));
        damage.add(new DamageInfo(this, whirlwindDamage));
        damage.add(new DamageInfo(this, twinSlamDamage));
        loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0F);
        state.setAnimation(0, "idle", true);
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
        {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, dmgThreshold)));
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
        UnlockTracker.markBossAsSeen("GUARDIAN");
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            useCloseUp();
            break;

        case 2: // '\002'
            useFierceBash();
            break;

        case 7: // '\007'
            useVentSteam();
            break;

        case 3: // '\003'
            useRollAttack();
            break;

        case 4: // '\004'
            useTwinSmash();
            break;

        case 5: // '\005'
            useWhirlwind();
            break;

        case 6: // '\006'
            useChargeUp();
            break;

        default:
            logger.info("ERROR");
            break;
        }
    }

    private void useFierceBash()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        setMove(VENTSTEAM_NAME, (byte)7, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
    }

    private void useVentSteam()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, VENT_DEBUFF, true), VENT_DEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VENT_DEBUFF, true), VENT_DEBUFF));
        setMove(WHIRLWIND_NAME, (byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, whirlwindDamage, whirlwindCount, true);
    }

    private void useCloseUp()
    {
        AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
        if(AbstractDungeon.ascensionLevel >= 19)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage + 1)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, thornsDamage)));
        setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
    }

    private void useTwinSmash()
    {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(3), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(3), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Sharp Hide"));
        setMove(WHIRLWIND_NAME, (byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, whirlwindDamage, whirlwindCount, true);
    }

    private void useRollAttack()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        setMove(TWINSLAM_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF, twinSlamDamage, 2, true);
    }

    private void useWhirlwind()
    {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        for(int i = 0; i < whirlwindCount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
        }

        setMove(CHARGEUP_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
    }

    private void useChargeUp()
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmount));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.0F, 2.5F));
        setMove(FIERCEBASH_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    protected void getMove(int num)
    {
        if(isOpen)
            setMove(CHARGEUP_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
        else
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
    }

    public void changeState(String stateName)
    {
        String s = stateName;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 631623152: 
            if(s.equals("Defensive Mode"))
                byte0 = 0;
            break;

        case -927957434: 
            if(s.equals("Offensive Mode"))
                byte0 = 1;
            break;

        case 786294426: 
            if(s.equals("Reset Threshold"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        default:
            break;

        case 0: // '\0'
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Mode Shift"));
            CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, DEFENSIVE_BLOCK));
            stateData.setMix("idle", "transition", 0.1F);
            state.setTimeScale(2.0F);
            state.setAnimation(0, "transition", false);
            state.addAnimation(0, "defensive", true, 0.0F);
            dmgThreshold += dmgThresholdIncrease;
            setMove(CLOSEUP_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            createIntent();
            isOpen = false;
            updateHitbox(0.0F, 95F, 440F, 250F);
            healthBarUpdatedEvent();
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, dmgThreshold)));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
            if(currentBlock != 0)
                AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, currentBlock));
            stateData.setMix("defensive", "idle", 0.2F);
            state.setTimeScale(1.0F);
            state.setAnimation(0, "idle", true);
            isOpen = true;
            closeUpTriggered = false;
            updateHitbox(0.0F, 95F, 440F, 350F);
            healthBarUpdatedEvent();
            break;

        case 2: // '\002'
            dmgTaken = 0;
            break;
        }
    }

    public void damage(DamageInfo info)
    {
        int tmpHealth = currentHealth;
        super.damage(info);
        if(isOpen && !closeUpTriggered && tmpHealth > currentHealth && !isDying)
        {
            dmgTaken += tmpHealth - currentHealth;
            if(getPower("Mode Shift") != null)
            {
                getPower("Mode Shift").amount -= tmpHealth - currentHealth;
                getPower("Mode Shift").updateDescription();
            }
            if(dmgTaken >= dmgThreshold)
            {
                dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(hb.cX, hb.cY, false), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                closeUpTriggered = true;
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        super.render(sb);
    }

    public void die()
    {
        useFastShakeAnimation(5F);
        CardCrawlGame.screenShake.rumble(4F);
        super.die();
        onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("GUARDIAN");
        UnlockTracker.unlockAchievement("GUARDIAN");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/exordium/TheGuardian.getName());
    public static final String ID = "TheGuardian";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final String DEFENSIVE_MODE = "Defensive Mode";
    private static final String OFFENSIVE_MODE = "Offensive Mode";
    private static final String RESET_THRESH = "Reset Threshold";
    public static final int HP = 240;
    public static final int A_2_HP = 250;
    private static final int DMG_THRESHOLD = 30;
    private static final int A_2_DMG_THRESHOLD = 35;
    private static final int A_19_DMG_THRESHOLD = 40;
    private int dmgThreshold;
    private int dmgThresholdIncrease;
    private int dmgTaken;
    private static final int FIERCE_BASH_DMG = 32;
    private static final int A_2_FIERCE_BASH_DMG = 36;
    private static final int ROLL_DMG = 9;
    private static final int A_2_ROLL_DMG = 10;
    private int fierceBashDamage;
    private int whirlwindDamage;
    private int twinSlamDamage;
    private int rollDamage;
    private int whirlwindCount;
    private int DEFENSIVE_BLOCK;
    private int blockAmount;
    private int thornsDamage;
    private int VENT_DEBUFF;
    private boolean isOpen;
    private boolean closeUpTriggered;
    private static final byte CLOSE_UP = 1;
    private static final byte FIERCE_BASH = 2;
    private static final byte ROLL_ATTACK = 3;
    private static final byte TWIN_SLAM = 4;
    private static final byte WHIRLWIND = 5;
    private static final byte CHARGE_UP = 6;
    private static final byte VENT_STEAM = 7;
    private static final String CLOSEUP_NAME;
    private static final String FIERCEBASH_NAME;
    private static final String TWINSLAM_NAME;
    private static final String WHIRLWIND_NAME;
    private static final String CHARGEUP_NAME;
    private static final String VENTSTEAM_NAME;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheGuardian");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        CLOSEUP_NAME = MOVES[0];
        FIERCEBASH_NAME = MOVES[1];
        TWINSLAM_NAME = MOVES[3];
        WHIRLWIND_NAME = MOVES[4];
        CHARGEUP_NAME = MOVES[5];
        VENTSTEAM_NAME = MOVES[6];
    }
}
