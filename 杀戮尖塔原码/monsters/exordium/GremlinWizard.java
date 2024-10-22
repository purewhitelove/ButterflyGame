// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinWizard.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;

public class GremlinWizard extends AbstractMonster
{

    public GremlinWizard(float x, float y)
    {
        super(NAME, "GremlinWizard", 25, 40F, -5F, 130F, 180F, null, x - 35F, y);
        currentCharge = 1;
        dialogX = 0.0F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(22, 26);
        else
            setHp(21, 25);
        if(AbstractDungeon.ascensionLevel >= 2)
            damage.add(new DamageInfo(this, 30));
        else
            damage.add(new DamageInfo(this, 25));
        loadAnimation("images/monsters/theBottom/wizardGremlin/skeleton.atlas", "images/monsters/theBottom/wizardGremlin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            currentCharge++;
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
            if(escapeNext)
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            else
            if(currentCharge == 3)
            {
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.5F, 3F));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            } else
            {
                setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            }
            break;

        case 1: // '\001'
            currentCharge = 0;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            if(escapeNext)
            {
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
                break;
            }
            if(AbstractDungeon.ascensionLevel >= 17)
                setMove(MOVES[1], (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            break;

        case 99: // 'c'
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.5F, DIALOG[3], false));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            break;
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINDOPEY_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINDOPEY_1B"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2B");
        else
            CardCrawlGame.sound.play("VO_GREMLINDOPEY_2C");
    }

    public void die()
    {
        super.die();
        playDeathSfx();
    }

    public void escapeNext()
    {
        if(!cannotEscape && !escapeNext)
        {
            escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 3F, DIALOG[4], false));
        }
    }

    protected void getMove(int num)
    {
        setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
    }

    public void deathReact()
    {
        if(intent != com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE && !isDying)
        {
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 3F, DIALOG[4], false));
            setMove((byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE);
            createIntent();
        }
    }

    public static final String ID = "GremlinWizard";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 21;
    private static final int HP_MAX = 25;
    private static final int A_2_HP_MIN = 22;
    private static final int A_2_HP_MAX = 26;
    private static final int MAGIC_DAMAGE = 25;
    private static final int A_2_MAGIC_DAMAGE = 30;
    private static final int CHARGE_LIMIT = 3;
    private int currentCharge;
    private static final byte DOPE_MAGIC = 1;
    private static final byte CHARGE = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWizard");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
