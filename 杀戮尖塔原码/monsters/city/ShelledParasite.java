// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShelledParasite.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;

public class ShelledParasite extends AbstractMonster
{

    public ShelledParasite(float x, float y)
    {
        super(NAME, "Shelled Parasite", 72, 20F, -6F, 350F, 260F, null, x, y);
        firstMove = true;
        loadAnimation("images/monsters/theCity/shellMonster/skeleton.atlas", "images/monsters/theCity/shellMonster/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTimeScale(0.8F);
        dialogX = -50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(70, 75);
        else
            setHp(68, 72);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            doubleStrikeDmg = 7;
            fellDmg = 21;
            suckDmg = 12;
        } else
        {
            doubleStrikeDmg = 6;
            fellDmg = 18;
            suckDmg = 10;
        }
        damage.add(new DamageInfo(this, doubleStrikeDmg));
        damage.add(new DamageInfo(this, fellDmg));
        damage.add(new DamageInfo(this, suckDmg));
    }

    public ShelledParasite()
    {
        this(-20F, 10F);
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 14)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 14));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
            break;

        case 2: // '\002'
            for(int i = 0; i < 2; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }

            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25F, 25F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25F, 25F) * Settings.scale, Color.GOLD.cpy()), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 4: // '\004'
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.STUNNED));
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

        case 1280154047: 
            if(s.equals("ARMOR_BREAK"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STUN);
            createIntent();
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
        if(firstMove)
        {
            firstMove = false;
            if(AbstractDungeon.ascensionLevel >= 17)
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                return;
            }
            if(AbstractDungeon.aiRng.randomBoolean())
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo)damage.get(2)).base);
            return;
        }
        if(num < 20)
        {
            if(!lastMove((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
            else
                getMove(AbstractDungeon.aiRng.random(20, 99));
        } else
        if(num < 60)
        {
            if(!lastTwoMoves((byte)2))
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo)damage.get(2)).base);
        } else
        if(!lastTwoMoves((byte)3))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo)damage.get(2)).base);
        else
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, 2, true);
    }

    public static final String ID = "Shelled Parasite";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 68;
    private static final int HP_MAX = 72;
    private static final int A_2_HP_MIN = 70;
    private static final int A_2_HP_MAX = 75;
    private static final float HB_X_F = 20F;
    private static final float HB_Y_F = -6F;
    private static final float HB_W = 350F;
    private static final float HB_H = 260F;
    private static final int PLATED_ARMOR_AMT = 14;
    private static final int FELL_DMG = 18;
    private static final int DOUBLE_STRIKE_DMG = 6;
    private static final int SUCK_DMG = 10;
    private static final int A_2_FELL_DMG = 21;
    private static final int A_2_DOUBLE_STRIKE_DMG = 7;
    private static final int A_2_SUCK_DMG = 12;
    private int fellDmg;
    private int doubleStrikeDmg;
    private int suckDmg;
    private static final int DOUBLE_STRIKE_COUNT = 2;
    private static final int FELL_FRAIL_AMT = 2;
    private static final byte FELL = 1;
    private static final byte DOUBLE_STRIKE = 2;
    private static final byte LIFE_SUCK = 3;
    private static final byte STUNNED = 4;
    private boolean firstMove;
    public static final String ARMOR_BREAK = "ARMOR_BREAK";

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Shelled Parasite");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
