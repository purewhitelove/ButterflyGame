// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JawWorm.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;

public class JawWorm extends AbstractMonster
{

    public JawWorm(float x, float y)
    {
        this(x, y, false);
    }

    public JawWorm(float x, float y, boolean hard)
    {
        super(NAME, "JawWorm", 44, 0.0F, -25F, 260F, 170F, null, x, y);
        firstMove = true;
        hardMode = hard;
        if(hardMode)
            firstMove = false;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(42, 46);
        else
            setHp(40, 44);
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            bellowStr = 5;
            bellowBlock = 9;
            chompDmg = 12;
            thrashDmg = 7;
            thrashBlock = 5;
        } else
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            bellowStr = 4;
            bellowBlock = 6;
            chompDmg = 12;
            thrashDmg = 7;
            thrashBlock = 5;
        } else
        {
            bellowStr = 3;
            bellowBlock = 6;
            chompDmg = 11;
            thrashDmg = 7;
            thrashBlock = 5;
        }
        damage.add(new DamageInfo(this, chompDmg));
        damage.add(new DamageInfo(this, thrashDmg));
        loadAnimation("images/monsters/theBottom/jawWorm/skeleton.atlas", "images/monsters/theBottom/jawWorm/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        if(hardMode)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, bellowStr), bellowStr));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, bellowBlock));
        }
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "chomp"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 2: // '\002'
            state.setAnimation(0, "tailslam", false);
            state.addAnimation(0, "idle", true, 0.0F);
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_JAW_WORM_BELLOW"));
            AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2F, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, bellowStr), bellowStr));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, bellowBlock));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, thrashBlock));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            firstMove = false;
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(num < 25)
        {
            if(lastMove((byte)1))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.5625F))
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                else
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base);
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            }
        } else
        if(num < 55)
        {
            if(lastTwoMoves((byte)3))
            {
                if(AbstractDungeon.aiRng.randomBoolean(0.357F))
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
                else
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
            } else
            {
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base);
            }
        } else
        if(lastMove((byte)2))
        {
            if(AbstractDungeon.aiRng.randomBoolean(0.416F))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base);
        } else
        {
            setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
        }
    }

    public void die()
    {
        super.die();
        CardCrawlGame.sound.play("JAW_WORM_DEATH");
    }

    public static final String ID = "JawWorm";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 40;
    private static final int HP_MAX = 44;
    private static final int A_2_HP_MIN = 42;
    private static final int A_2_HP_MAX = 46;
    private static final float HB_X = 0F;
    private static final float HB_Y = -25F;
    private static final float HB_W = 260F;
    private static final float HB_H = 170F;
    private static final int CHOMP_DMG = 11;
    private static final int A_2_CHOMP_DMG = 12;
    private static final int THRASH_DMG = 7;
    private static final int THRASH_BLOCK = 5;
    private static final int BELLOW_STR = 3;
    private static final int A_2_BELLOW_STR = 4;
    private static final int A_17_BELLOW_STR = 5;
    private static final int BELLOW_BLOCK = 6;
    private static final int A_17_BELLOW_BLOCK = 9;
    private int bellowBlock;
    private int chompDmg;
    private int thrashDmg;
    private int thrashBlock;
    private int bellowStr;
    private static final byte CHOMP = 1;
    private static final byte BELLOW = 2;
    private static final byte THRASH = 3;
    private boolean firstMove;
    private boolean hardMode;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("JawWorm");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
