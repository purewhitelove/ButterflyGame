// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Lagavulin.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lagavulin extends AbstractMonster
{

    public Lagavulin(boolean setAsleep)
    {
        super(NAME, "Lagavulin", 111, 0.0F, -25F, 320F, 220F, null, 0.0F, 20F);
        isOut = false;
        isOutTriggered = false;
        idleCount = 0;
        debuffTurnCount = 0;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        dialogX = -100F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(112, 115);
        else
            setHp(109, 111);
        if(AbstractDungeon.ascensionLevel >= 3)
            attackDmg = 20;
        else
            attackDmg = 18;
        if(AbstractDungeon.ascensionLevel >= 18)
            debuff = -2;
        else
            debuff = -1;
        damage.add(new DamageInfo(this, attackDmg));
        asleep = setAsleep;
        loadAnimation("images/monsters/theBottom/lagavulin/skeleton.atlas", "images/monsters/theBottom/lagavulin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = null;
        if(!asleep)
        {
            isOut = true;
            isOutTriggered = true;
            e = state.setAnimation(0, "Idle_2", true);
            updateHitbox(0.0F, -25F, 320F, 370F);
        } else
        {
            e = state.setAnimation(0, "Idle_1", true);
        }
        stateData.setMix("Attack", "Idle_2", 0.25F);
        stateData.setMix("Hit", "Idle_2", 0.25F);
        stateData.setMix("Idle_1", "Idle_2", 0.5F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        if(asleep)
        {
            CardCrawlGame.music.precacheTempBgm("ELITE");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 8), 8));
        } else
        {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            CardCrawlGame.music.playTempBgmInstantly("ELITE");
            setMove(DEBUFF_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
        }
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 2: // '\002'
        default:
            break;

        case 1: // '\001'
            debuffTurnCount = 0;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DEBUFF"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, debuff), debuff));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, debuff), debuff));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 3: // '\003'
            debuffTurnCount++;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 5: // '\005'
            idleCount++;
            if(idleCount >= 3)
            {
                logger.info("idle happened");
                isOutTriggered = true;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            } else
            {
                setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.SLEEP);
            }
            switch(idleCount)
            {
            case 1: // '\001'
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;

            case 2: // '\002'
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            }
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.STUNNED));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 6: // '\006'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            createIntent();
            isOutTriggered = true;
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;
        }
    }

    public void changeState(String stateName)
    {
        if(stateName.equals("ATTACK"))
        {
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle_2", true, 0.0F);
        } else
        if(stateName.equals("DEBUFF"))
        {
            state.setAnimation(0, "Debuff", false);
            state.addAnimation(0, "Idle_2", true, 0.0F);
        } else
        if(stateName.equals("OPEN") && !isDying)
        {
            isOut = true;
            updateHitbox(0.0F, -25F, 320F, 360F);
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3], 0.5F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this, this, "Metallicize", 8));
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            CardCrawlGame.music.playPrecachedTempBgm();
            state.setAnimation(0, "Coming_out", false);
            state.addAnimation(0, "Idle_2", true, 0.0F);
        }
    }

    public void damage(DamageInfo info)
    {
        int previousHealth = currentHealth;
        super.damage(info);
        if(currentHealth != previousHealth && !isOutTriggered)
        {
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STUN);
            createIntent();
            isOutTriggered = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
        } else
        if(isOutTriggered && info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle_2", true, 0.0F);
        }
    }

    protected void getMove(int num)
    {
        if(isOut)
        {
            if(debuffTurnCount < 2)
            {
                if(lastTwoMoves((byte)3))
                    setMove(DEBUFF_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
                else
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            } else
            {
                setMove(DEBUFF_NAME, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            }
        } else
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.SLEEP);
        }
    }

    public void die()
    {
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        CardCrawlGame.music.fadeOutTempBGM();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/exordium/Lagavulin.getName());
    public static final String ID = "Lagavulin";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 109;
    private static final int HP_MAX = 111;
    private static final int A_2_HP_MIN = 112;
    private static final int A_2_HP_MAX = 115;
    private static final byte DEBUFF = 1;
    private static final byte STRONG_ATK = 3;
    private static final byte OPEN = 4;
    private static final byte IDLE = 5;
    private static final byte OPEN_NATURAL = 6;
    private static final String DEBUFF_NAME;
    private static final int STRONG_ATK_DMG = 18;
    private static final int DEBUFF_AMT = -1;
    private static final int A_18_DEBUFF_AMT = -2;
    private static final int A_2_STRONG_ATK_DMG = 20;
    private int attackDmg;
    private int debuff;
    private static final int ARMOR_AMT = 8;
    private boolean isOut;
    private boolean asleep;
    private boolean isOutTriggered;
    private int idleCount;
    private int debuffTurnCount;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Lagavulin");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        DEBUFF_NAME = MOVES[0];
    }
}
