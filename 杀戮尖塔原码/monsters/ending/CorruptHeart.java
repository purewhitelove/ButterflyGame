// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CorruptHeart.java

package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.ArrayList;

public class CorruptHeart extends AbstractMonster
{

    public CorruptHeart()
    {
        super(NAME, "CorruptHeart", 750, 30F, -30F, 476F, 410F, null, -50F, 30F);
        animListener = new HeartAnimListener();
        isFirstMove = true;
        moveCount = 0;
        buffCount = 0;
        loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTimeScale(1.5F);
        state.addListener(animListener);
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(800);
        else
            setHp(750);
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            damage.add(new DamageInfo(this, 45));
            damage.add(new DamageInfo(this, 2));
            bloodHitCount = 15;
        } else
        {
            damage.add(new DamageInfo(this, 40));
            damage.add(new DamageInfo(this, 2));
            bloodHitCount = 12;
        }
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");
        int invincibleAmt = 300;
        if(AbstractDungeon.ascensionLevel >= 19)
            invincibleAmt -= 100;
        int beatAmount = 1;
        if(AbstractDungeon.ascensionLevel >= 19)
            beatAmount++;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, invincibleAmt), invincibleAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, beatAmount), beatAmount));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartMegaDebuffEffect()));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, false, false, (float)Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, false, false, (float)Settings.WIDTH * 0.35F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Wound(), 1, true, false, false, (float)Settings.WIDTH * 0.5F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, false, (float)Settings.WIDTH * 0.65F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, false, false, (float)Settings.WIDTH * 0.8F, (float)Settings.HEIGHT / 2.0F));
            break;

        case 4: // '\004'
            int additionalAmount = 0;
            if(hasPower("Strength") && getPower("Strength").amount < 0)
                additionalAmount = -getPower("Strength").amount;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(new Color(0.8F, 0.5F, 1.0F, 1.0F))));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(hb.cX, hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, additionalAmount + 2), additionalAmount + 2));
            switch(buffCount)
            {
            case 0: // '\0'
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
                break;

            case 1: // '\001'
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, 1), 1));
                break;

            case 2: // '\002'
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
                break;

            case 3: // '\003'
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
                break;

            default:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 50), 50));
                break;
            }
            buffCount++;
            break;

        case 1: // '\001'
            if(Settings.FAST_MODE)
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(hb.cX, hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, bloodHitCount), 0.25F));
            else
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(hb.cX, hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, bloodHitCount), 0.6F));
            for(int i = 0; i < bloodHitCount; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));

            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(isFirstMove)
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            isFirstMove = false;
            return;
        }
        switch(moveCount % 3)
        {
        case 0: // '\0'
            if(AbstractDungeon.aiRng.randomBoolean())
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, bloodHitCount, true);
            else
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            break;

        case 1: // '\001'
            if(!lastMove((byte)2))
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base, bloodHitCount, true);
            break;

        default:
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            break;
        }
        moveCount++;
    }

    public void die()
    {
        if(!AbstractDungeon.getCurrRoom().cannotLose)
        {
            super.die();
            state.removeListener(animListener);
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
        }
    }

    public static final String ID = "CorruptHeart";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private HeartAnimListener animListener;
    private static final byte BLOOD_SHOTS = 1;
    private static final byte ECHO_ATTACK = 2;
    private static final byte DEBILITATE = 3;
    private static final byte GAIN_ONE_STRENGTH = 4;
    public static final int DEBUFF_AMT = -1;
    private int bloodHitCount;
    private boolean isFirstMove;
    private int moveCount;
    private int buffCount;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("CorruptHeart");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
