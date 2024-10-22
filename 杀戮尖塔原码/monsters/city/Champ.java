// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Champ.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import java.util.ArrayList;

public class Champ extends AbstractMonster
{

    public Champ()
    {
        super(NAME, "Champ", 420, 0.0F, -15F, 400F, 410F, null, -90F, 0.0F);
        numTurns = 0;
        forgeTimes = 0;
        forgeThreshold = 2;
        thresholdReached = false;
        firstTurn = true;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -100F * Settings.scale;
        dialogY = 10F * Settings.scale;
        loadAnimation("images/monsters/theCity/champ/skeleton.atlas", "images/monsters/theCity/champ/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.8F);
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(440);
        else
            setHp(420);
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            slashDmg = 18;
            executeDmg = 10;
            slapDmg = 14;
            strAmt = 4;
            forgeAmt = 7;
            blockAmt = 20;
        } else
        if(AbstractDungeon.ascensionLevel >= 9)
        {
            slashDmg = 18;
            executeDmg = 10;
            slapDmg = 14;
            strAmt = 3;
            forgeAmt = 6;
            blockAmt = 18;
        } else
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            slashDmg = 18;
            executeDmg = 10;
            slapDmg = 14;
            strAmt = 3;
            forgeAmt = 5;
            blockAmt = 15;
        } else
        {
            slashDmg = 16;
            executeDmg = 10;
            slapDmg = 12;
            strAmt = 2;
            forgeAmt = 5;
            blockAmt = 15;
        }
        damage.add(new DamageInfo(this, slashDmg));
        damage.add(new DamageInfo(this, executeDmg));
        damage.add(new DamageInfo(this, slapDmg));
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        UnlockTracker.markBossAsSeen("CHAMP");
    }

    public void takeTurn()
    {
        float vfxSpeed = 0.1F;
        if(Settings.FAST_MODE)
            vfxSpeed = 0.0F;
        if(firstTurn)
        {
            firstTurn = false;
            if(AbstractDungeon.player.hasRelic("Champion Belt"))
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[8], 0.5F, 2.0F));
        }
        switch(nextMove)
        {
        case 7: // '\007'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_CHARGE"));
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, getLimitBreak(), 2.0F, 3F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt * 3), strAmt * 3));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60F * Settings.scale, AbstractDungeon.player.hb.cY, false), vfxSpeed));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, forgeAmt), forgeAmt));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(this));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX + 60F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_SLAP"));
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt), strAmt));
            break;

        case 6: // '\006'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CHAMP_2A"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, getTaunt()));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
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

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    private String getTaunt()
    {
        ArrayList derp = new ArrayList();
        derp.add(DIALOG[0]);
        derp.add(DIALOG[1]);
        derp.add(DIALOG[2]);
        derp.add(DIALOG[3]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    private String getLimitBreak()
    {
        ArrayList derp = new ArrayList();
        derp.add(DIALOG[4]);
        derp.add(DIALOG[5]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    private String getDeathQuote()
    {
        ArrayList derp = new ArrayList();
        derp.add(DIALOG[6]);
        derp.add(DIALOG[7]);
        return (String)derp.get(MathUtils.random(derp.size() - 1));
    }

    protected void getMove(int num)
    {
        numTurns++;
        if(currentHealth < maxHealth / 2 && !thresholdReached)
        {
            thresholdReached = true;
            setMove((byte)7, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(!lastMove((byte)3) && !lastMoveBefore((byte)3) && thresholdReached)
        {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, getDeathQuote(), 2.0F, 2.0F));
            setMove(EXECUTE_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, 2, true);
            return;
        }
        if(numTurns == 4 && !thresholdReached)
        {
            setMove((byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            numTurns = 0;
            return;
        }
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            if(!lastMove((byte)2) && forgeTimes < forgeThreshold && num <= 30)
            {
                forgeTimes++;
                setMove(STANCE_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                return;
            }
        } else
        if(!lastMove((byte)2) && forgeTimes < forgeThreshold && num <= 15)
        {
            forgeTimes++;
            setMove(STANCE_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
            return;
        }
        if(!lastMove((byte)5) && !lastMove((byte)2) && num <= 30)
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(!lastMove((byte)4) && num <= 55)
        {
            setMove(SLAP_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(2)).base);
            return;
        }
        if(!lastMove((byte)1))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        else
            setMove(SLAP_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(2)).base);
    }

    public void die()
    {
        useFastShakeAnimation(5F);
        CardCrawlGame.screenShake.rumble(4F);
        super.die();
        if(MathUtils.randomBoolean())
            CardCrawlGame.sound.play("VO_CHAMP_3A");
        else
            CardCrawlGame.sound.play("VO_CHAMP_3B");
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("CHAMP");
        UnlockTracker.unlockAchievement("CHAMP");
    }

    public static final String ID = "Champ";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP = 420;
    public static final int A_9_HP = 440;
    private static final byte HEAVY_SLASH = 1;
    private static final byte DEFENSIVE_STANCE = 2;
    private static final byte EXECUTE = 3;
    private static final byte FACE_SLAP = 4;
    private static final byte GLOAT = 5;
    private static final byte TAUNT = 6;
    private static final byte ANGER = 7;
    private static final String STANCE_NAME;
    private static final String EXECUTE_NAME;
    private static final String SLAP_NAME;
    public static final int SLASH_DMG = 16;
    public static final int EXECUTE_DMG = 10;
    public static final int SLAP_DMG = 12;
    public static final int A_2_SLASH_DMG = 18;
    public static final int A_2_SLAP_DMG = 14;
    private int slashDmg;
    private int executeDmg;
    private int slapDmg;
    private int blockAmt;
    private static final int DEBUFF_AMT = 2;
    private static final int EXEC_COUNT = 2;
    private static final int FORGE_AMT = 5;
    private static final int BLOCK_AMT = 15;
    private static final int A_9_FORGE_AMT = 6;
    private static final int A_9_BLOCK_AMT = 18;
    private static final int A_19_FORGE_AMT = 7;
    private static final int A_19_BLOCK_AMT = 20;
    private static final int STR_AMT = 2;
    private static final int A_4_STR_AMT = 3;
    private static final int A_19_STR_AMT = 4;
    private int strAmt;
    private int forgeAmt;
    private int numTurns;
    private int forgeTimes;
    private int forgeThreshold;
    private boolean thresholdReached;
    private boolean firstTurn;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Champ");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        STANCE_NAME = MOVES[0];
        EXECUTE_NAME = MOVES[1];
        SLAP_NAME = MOVES[2];
    }
}
