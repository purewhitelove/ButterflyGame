// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Looter.java

package com.megacrit.cardcrawl.monsters.exordium;

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
import com.megacrit.cardcrawl.powers.ThieveryPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import java.util.ArrayList;

public class Looter extends AbstractMonster
{

    public Looter(float x, float y)
    {
        super(NAME, "Looter", 48, 0.0F, 0.0F, 200F, 220F, null, x, y);
        escapeDef = 6;
        slashCount = 0;
        stolenGold = 0;
        dialogX = -30F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 17)
            goldAmt = 20;
        else
            goldAmt = 15;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(46, 50);
        else
            setHp(44, 48);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            swipeDmg = 11;
            lungeDmg = 14;
        } else
        {
            swipeDmg = 10;
            lungeDmg = 12;
        }
        damage.add(new DamageInfo(this, swipeDmg));
        damage.add(new DamageInfo(this, lungeDmg));
        loadAnimation("images/monsters/theBottom/looter/skeleton.atlas", "images/monsters/theBottom/looter/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThieveryPower(this, goldAmt)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            if(slashCount == 0 && AbstractDungeon.aiRng.randomBoolean(0.6F))
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SLASH_MSG1, 0.3F, 2.0F));
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

                public void update()
                {
                    stolenGold = stolenGold + Math.min(goldAmt, AbstractDungeon.player.gold);
                    isDone = true;
                }

                final Looter this$0;

            
            {
                this.this$0 = Looter.this;
                super();
            }
            }
);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), goldAmt));
            slashCount++;
            if(slashCount == 2)
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.5F))
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
                else
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base));
            } else
            {
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            }
            break;

        case 4: // '\004'
            playSfx();
            slashCount++;
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

                public void update()
                {
                    stolenGold = stolenGold + Math.min(goldAmt, AbstractDungeon.player.gold);
                    isDone = true;
                }

                final Looter this$0;

            
            {
                this.this$0 = Looter.this;
                super();
            }
            }
);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), goldAmt));
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SMOKE_BOMB_MSG, 0.75F, 2.5F));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, escapeDef));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, RUN_MSG, 0.3F, 2.5F));
            AbstractDungeon.getCurrRoom().mugged = true;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(hb.cX, hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            break;
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1C"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_LOOTER_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_LOOTER_2B");
        else
            CardCrawlGame.sound.play("VO_LOOTER_2C");
    }

    public void die()
    {
        playDeathSfx();
        state.setTimeScale(0.1F);
        useShakeAnimation(5F);
        if(MathUtils.randomBoolean(0.3F))
        {
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.0F, DEATH_MSG1, false));
            if(!Settings.FAST_MODE)
                deathTimer += 1.5F;
        }
        if(stolenGold > 0)
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(stolenGold);
        super.die();
    }

    protected void getMove(int num)
    {
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    public static final String ID = "Looter";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 44;
    private static final int HP_MAX = 48;
    private static final int A_2_HP_MIN = 46;
    private static final int A_2_HP_MAX = 50;
    private int swipeDmg;
    private int lungeDmg;
    private int escapeDef;
    private int goldAmt;
    private static final byte MUG = 1;
    private static final byte SMOKE_BOMB = 2;
    private static final byte ESCAPE = 3;
    private static final byte LUNGE = 4;
    private static final String SLASH_MSG1;
    private static final String DEATH_MSG1;
    private static final String SMOKE_BOMB_MSG;
    private static final String RUN_MSG;
    private int slashCount;
    private int stolenGold;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Looter");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SLASH_MSG1 = DIALOG[0];
        DEATH_MSG1 = DIALOG[1];
        SMOKE_BOMB_MSG = DIALOG[2];
        RUN_MSG = DIALOG[3];
    }



}
