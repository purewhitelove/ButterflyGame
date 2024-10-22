// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cultist.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;

public class Cultist extends AbstractMonster
{

    public Cultist(float x, float y, boolean talk)
    {
        super(NAME, "Cultist", 54, -8F, 10F, 230F, 240F, null, x, y);
        firstMove = true;
        saidPower = false;
        ritualAmount = 0;
        talky = true;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(50, 56);
        else
            setHp(48, 54);
        dialogX = -50F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 2)
            ritualAmount = 4;
        else
            ritualAmount = 3;
        damage.add(new DamageInfo(this, 6));
        talky = talk;
        if(Settings.FAST_MODE)
            talky = false;
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public Cultist(float x, float y)
    {
        this(x, y, true);
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 3: // '\003'
            int temp = MathUtils.random(1, 10);
            if(talky)
            {
                playSfx();
                if(temp < 4)
                {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                    saidPower = true;
                } else
                if(temp < 7)
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 1.0F, 2.0F));
            }
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, ritualAmount + 1, false)));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, ritualAmount, false)));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_CULTIST_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_CULTIST_2B");
        else
            CardCrawlGame.sound.play("VO_CULTIST_2C");
    }

    public void die()
    {
        playDeathSfx();
        state.setTimeScale(0.1F);
        useShakeAnimation(5F);
        if(talky && saidPower)
        {
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.5F, DIALOG[2], false));
            deathTimer += 1.5F;
        }
        super.die();
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            firstMove = false;
            setMove(INCANTATION_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        } else
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
    }

    public static final String ID = "Cultist";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String MURDER_ENCOUNTER_KEY = "Murder of Cultists";
    private static final String INCANTATION_NAME;
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 54;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 56;
    private static final float HB_X = -8F;
    private static final float HB_Y = 10F;
    private static final float HB_W = 230F;
    private static final float HB_H = 240F;
    private static final int ATTACK_DMG = 6;
    private boolean firstMove;
    private boolean saidPower;
    private static final int RITUAL_AMT = 3;
    private static final int A_2_RITUAL_AMT = 4;
    private int ritualAmount;
    private static final byte DARK_STRIKE = 1;
    private static final byte INCANTATION = 3;
    private boolean talky;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Cultist");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        INCANTATION_NAME = MOVES[2];
    }
}
