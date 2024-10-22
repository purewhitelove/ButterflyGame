// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinTsundere.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;
import java.util.Iterator;

public class GremlinTsundere extends AbstractMonster
{

    public GremlinTsundere(float x, float y)
    {
        super(NAME, "GremlinTsundere", 15, 0.0F, 0.0F, 120F, 200F, null, x, y);
        dialogY = 60F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            setHp(13, 17);
            blockAmt = 11;
        } else
        if(AbstractDungeon.ascensionLevel >= 7)
        {
            setHp(13, 17);
            blockAmt = 8;
        } else
        {
            setHp(12, 15);
            blockAmt = 7;
        }
        if(AbstractDungeon.ascensionLevel >= 2)
            bashDmg = 8;
        else
            bashDmg = 6;
        damage.add(new DamageInfo(this, bashDmg));
        loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, blockAmt));
            int aliveCount = 0;
            Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(!m.isDying && !m.isEscaping)
                    aliveCount++;
            } while(true);
            if(escapeNext)
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            else
            if(aliveCount > 1)
                setMove(MOVES[0], (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            else
                setMove(MOVES[1], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if(escapeNext)
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            else
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            break;

        case 99: // 'c'
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.5F, DIALOG[1], false));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            break;
        }
    }

    public void die()
    {
        super.die();
    }

    public void escapeNext()
    {
        if(!cannotEscape && !escapeNext)
        {
            escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(dialogX, dialogY, 3F, DIALOG[2], false));
        }
    }

    protected void getMove(int num)
    {
        setMove(MOVES[0], (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
    }

    public void deathReact()
    {
        if(intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE && !isDying)
        {
            AbstractDungeon.effectList.add(new SpeechBubble(dialogX, dialogY, 3F, DIALOG[2], false));
            setMove((byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE);
            createIntent();
        }
    }

    public static final String ID = "GremlinTsundere";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 12;
    private static final int HP_MAX = 15;
    private static final int A_2_HP_MIN = 13;
    private static final int A_2_HP_MAX = 17;
    private static final int BLOCK_AMOUNT = 7;
    private static final int A_2_BLOCK_AMOUNT = 8;
    private static final int A_17_BLOCK_AMOUNT = 11;
    private static final int BASH_DAMAGE = 6;
    private static final int A_2_BASH_DAMAGE = 8;
    private int blockAmt;
    private int bashDmg;
    private static final byte PROTECT = 1;
    private static final byte BASH = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinTsundere");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
