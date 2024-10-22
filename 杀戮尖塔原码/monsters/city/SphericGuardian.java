// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SphericGuardian.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import java.util.ArrayList;

public class SphericGuardian extends AbstractMonster
{

    public SphericGuardian()
    {
        this(0.0F, 0.0F);
    }

    public SphericGuardian(float x, float y)
    {
        super(NAME, "SphericGuardian", 20, 0.0F, 10F, 280F, 280F, null, x, y);
        firstMove = true;
        secondMove = true;
        if(AbstractDungeon.ascensionLevel >= 2)
            dmg = 11;
        else
            dmg = 10;
        damage.add(new DamageInfo(this, dmg));
        loadAnimation("images/monsters/theCity/sphere/skeleton.atlas", "images/monsters/theCity/sphere/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        stateData.setMix("Idle", "Attack", 0.1F);
        state.setTimeScale(0.8F);
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 40));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;

        case 2: // '\002'
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 35));
            else
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 25));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
            if(MathUtils.randomBoolean())
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_1"));
            else
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_2"));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 15));
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 5, true), 5));
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
            state.setTimeScale(0.8F);
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
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            firstMove = false;
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            return;
        }
        if(secondMove)
        {
            secondMove = false;
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(lastMove((byte)1))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(0)).base);
        else
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
    }

    public void die()
    {
        super.die();
        if(MathUtils.randomBoolean())
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_1"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_2"));
    }

    public static final String ID = "SphericGuardian";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final float HB_X = 0F;
    private static final float HB_Y = 10F;
    private static final float HB_W = 280F;
    private static final float HB_H = 280F;
    private static final int DMG = 10;
    private static final int A_2_DMG = 11;
    private int dmg;
    private static final int SLAM_AMT = 2;
    private static final int HARDEN_BLOCK = 15;
    private static final int FRAIL_AMT = 5;
    private static final int ACTIVATE_BLOCK = 25;
    private static final int ARTIFACT_AMT = 3;
    private static final int STARTING_BLOCK_AMT = 40;
    private static final byte BIG_ATTACK = 1;
    private static final byte INITIAL_BLOCK_GAIN = 2;
    private static final byte BLOCK_ATTACK = 3;
    private static final byte FRAIL_ATTACK = 4;
    private boolean firstMove;
    private boolean secondMove;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SphericGuardian");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
