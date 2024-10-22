// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlaverRed.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;
import java.util.ArrayList;

public class SlaverRed extends AbstractMonster
{

    public SlaverRed(float x, float y)
    {
        super(NAME, "SlaverRed", 50, 0.0F, 0.0F, 170F, 230F, null, x, y);
        VULN_AMT = 1;
        usedEntangle = false;
        firstTurn = true;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(48, 52);
        else
            setHp(46, 50);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            stabDmg = 14;
            scrapeDmg = 9;
        } else
        {
            stabDmg = 13;
            scrapeDmg = 8;
        }
        damage.add(new DamageInfo(this, stabDmg));
        damage.add(new DamageInfo(this, scrapeDmg));
        loadAnimation("images/monsters/theBottom/redSlaver/skeleton.atlas", "images/monsters/theBottom/redSlaver/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        firstTurn = true;
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Use Net"));
            if(hb != null && AbstractDungeon.player.hb != null && !Settings.FAST_MODE)
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new EntangleEffect(hb.cX - 70F * Settings.scale, hb.cY + 10F * Settings.scale, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new EntanglePower(AbstractDungeon.player)));
            usedEntangle = true;
            break;

        case 1: // '\001'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VULN_AMT + 1, true), VULN_AMT + 1));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, VULN_AMT, true), VULN_AMT));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERRED_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERRED_1B"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_SLAVERRED_2A");
        else
            CardCrawlGame.sound.play("VO_SLAVERRED_2B");
    }

    public void changeState(String stateName)
    {
        float tmp = state.getCurrent(0).getTime();
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idleNoNet", true);
        e.setTime(tmp);
    }

    protected void getMove(int num)
    {
        if(firstTurn)
        {
            firstTurn = false;
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, stabDmg);
            return;
        }
        if(num >= 75 && !usedEntangle)
        {
            setMove(ENTANGLE_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(num >= 55 && usedEntangle && !lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(AbstractDungeon.ascensionLevel >= 17)
            if(!lastMove((byte)3))
            {
                setMove(SCRAPE_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                return;
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
                return;
            }
        if(!lastTwoMoves((byte)3))
        {
            setMove(SCRAPE_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
            return;
        } else
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
    }

    public void die()
    {
        super.die();
        playDeathSfx();
    }

    public static final String ID = "SlaverRed";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 46;
    private static final int HP_MAX = 50;
    private static final int A_2_HP_MIN = 48;
    private static final int A_2_HP_MAX = 52;
    private static final int STAB_DMG = 13;
    private static final int A_2_STAB_DMG = 14;
    private static final int SCRAPE_DMG = 8;
    private static final int A_2_SCRAPE_DMG = 9;
    private int stabDmg;
    private int scrapeDmg;
    private int VULN_AMT;
    private static final byte STAB = 1;
    private static final byte ENTANGLE = 2;
    private static final byte SCRAPE = 3;
    private static final String SCRAPE_NAME;
    private static final String ENTANGLE_NAME;
    private boolean usedEntangle;
    private boolean firstTurn;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlaverRed");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SCRAPE_NAME = MOVES[0];
        ENTANGLE_NAME = MOVES[1];
    }
}
