// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Byrd.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class Byrd extends AbstractMonster
{

    public Byrd(float x, float y)
    {
        super(NAME, "Byrd", 31, 0.0F, 50F, 240F, 180F, null, x, y);
        firstMove = true;
        isFlying = true;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(26, 33);
        else
            setHp(25, 31);
        if(AbstractDungeon.ascensionLevel >= 17)
            flightAmt = 4;
        else
            flightAmt = 3;
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            peckDmg = 1;
            peckCount = 6;
            swoopDmg = 14;
        } else
        {
            peckDmg = 1;
            peckCount = 5;
            swoopDmg = 12;
        }
        damage.add(new DamageInfo(this, peckDmg));
        damage.add(new DamageInfo(this, swoopDmg));
        damage.add(new DamageInfo(this, 3));
        loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle_flap", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, flightAmt)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            for(int i = 0; i < peckCount; i++)
            {
                playRandomBirdSFx();
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
            }

            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            return;

        case 2: // '\002'
            isFlying = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "FLYING"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, flightAmt)));
            break;

        case 6: // '\006'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("BYRD_DEATH"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.2F, 1.2F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "head_lift"));
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.STUNNED));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playRandomBirdSFx()
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction((new StringBuilder()).append("MONSTER_BYRD_ATTACK_").append(MathUtils.random(0, 5)).toString()));
    }

    public void changeState(String stateName)
    {
        String s = stateName;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 2076952207: 
            if(s.equals("FLYING"))
                byte0 = 0;
            break;

        case 1288933478: 
            if(s.equals("GROUNDED"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
        {
            loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0F);
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle_flap", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            updateHitbox(0.0F, 50F, 240F, 180F);
            break;
        }

        case 1: // '\001'
        {
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STUN);
            createIntent();
            isFlying = false;
            loadAnimation("images/monsters/theCity/byrd/grounded.atlas", "images/monsters/theCity/byrd/grounded.json", 1.0F);
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            updateHitbox(10F, -50F, 240F, 180F);
            break;
        }
        }
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            firstMove = false;
            if(AbstractDungeon.aiRng.randomBoolean(0.375F))
                setMove((byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, peckCount, true);
            return;
        }
        if(isFlying)
        {
            if(num < 50)
            {
                if(lastTwoMoves((byte)1))
                {
                    if(AbstractDungeon.aiRng.randomBoolean(0.4F))
                        setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                    else
                        setMove((byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
                } else
                {
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, peckCount, true);
                }
            } else
            if(num < 70)
            {
                if(lastMove((byte)3))
                {
                    if(AbstractDungeon.aiRng.randomBoolean(0.375F))
                        setMove((byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
                    else
                        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, peckCount, true);
                } else
                {
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                }
            } else
            if(lastMove((byte)6))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.2857F))
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                else
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, peckCount, true);
            } else
            {
                setMove((byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            }
        } else
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(2)).base);
        }
    }

    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("BYRD_DEATH");
    }

    public static final String ID = "Byrd";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String THREE_BYRDS = "3_Byrds";
    public static final String FOUR_BYRDS = "4_Byrds";
    public static final String IMAGE = "images/monsters/theCity/byrdFlying.png";
    private static final int HP_MIN = 25;
    private static final int HP_MAX = 31;
    private static final int A_2_HP_MIN = 26;
    private static final int A_2_HP_MAX = 33;
    private static final float HB_X_F = 0F;
    private static final float HB_X_G = 10F;
    private static final float HB_Y_F = 50F;
    private static final float HB_Y_G = -50F;
    private static final float HB_W = 240F;
    private static final float HB_H = 180F;
    private static final int PECK_DMG = 1;
    private static final int PECK_COUNT = 5;
    private static final int SWOOP_DMG = 12;
    private static final int A_2_PECK_COUNT = 6;
    private static final int A_2_SWOOP_DMG = 14;
    private int peckDmg;
    private int peckCount;
    private int swoopDmg;
    private int flightAmt;
    private static final int HEADBUTT_DMG = 3;
    private static final int CAW_STR = 1;
    private static final byte PECK = 1;
    private static final byte GO_AIRBORNE = 2;
    private static final byte SWOOP = 3;
    private static final byte STUNNED = 4;
    private static final byte HEADBUTT = 5;
    private static final byte CAW = 6;
    private boolean firstMove;
    private boolean isFlying;
    public static final String FLY_STATE = "FLYING";
    public static final String GROUND_STATE = "GROUNDED";

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Byrd");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
