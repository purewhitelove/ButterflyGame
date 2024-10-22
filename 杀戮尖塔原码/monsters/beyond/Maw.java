// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Maw.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;

public class Maw extends AbstractMonster
{

    public Maw(float x, float y)
    {
        super(NAME, "Maw", 300, 0.0F, -40F, 430F, 360F, null, x, y);
        roared = false;
        turnCount = 1;
        loadAnimation("images/monsters/theForest/maw/skeleton.atlas", "images/monsters/theForest/maw/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        dialogX = -160F * Settings.scale;
        dialogY = 40F * Settings.scale;
        strUp = 3;
        terrifyDur = 3;
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            strUp += 2;
            terrifyDur += 2;
        }
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            slamDmg = 30;
            nomDmg = 5;
        } else
        {
            slamDmg = 25;
            nomDmg = 5;
        }
        damage.add(new DamageInfo(this, slamDmg));
        damage.add(new DamageInfo(this, nomDmg));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MAW_DEATH", 0.1F));
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, terrifyDur, true), terrifyDur));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, terrifyDur, true), terrifyDur));
            roared = true;
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strUp), strUp));
            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            for(int i = 0; i < turnCount / 2; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50F, 50F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale, Color.SKY.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            }

            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        turnCount++;
        if(!roared)
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(num < 50 && !lastMove((byte)5))
        {
            if(turnCount / 2 <= 1)
                setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            else
                setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, turnCount / 2, true);
            return;
        }
        if(lastMove((byte)3) || lastMove((byte)5))
        {
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        } else
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
    }

    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("MAW_DEATH");
    }

    public static final String ID = "Maw";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP = 300;
    private static final float HB_X = 0F;
    private static final float HB_Y = -40F;
    private static final float HB_W = 430F;
    private static final float HB_H = 360F;
    private static final int SLAM_DMG = 25;
    private static final int NOM_DMG = 5;
    private static final int A_2_SLAM_DMG = 30;
    private int slamDmg;
    private int nomDmg;
    private static final byte ROAR = 2;
    private static final byte SLAM = 3;
    private static final byte DROOL = 4;
    private static final byte NOMNOMNOM = 5;
    private boolean roared;
    private int turnCount;
    private int strUp;
    private int terrifyDur;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Maw");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
