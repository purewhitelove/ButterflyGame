// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinFat.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
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
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;

public class GremlinFat extends AbstractMonster
{

    public GremlinFat(float x, float y)
    {
        super(NAME, "GremlinFat", 17, 0.0F, 0.0F, 110F, 220F, null, x, y);
        dialogY = 30F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(14, 18);
        else
            setHp(13, 17);
        if(AbstractDungeon.ascensionLevel >= 2)
            damage.add(new DamageInfo(this, 5));
        else
            damage.add(new DamageInfo(this, 4));
        loadAnimation("images/monsters/theBottom/fatGremlin/skeleton.atlas", "images/monsters/theBottom/fatGremlin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
            if(escapeNext)
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            else
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 99: // 'c'
            playSfx();
            AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.5F, DIALOG[1], false));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            break;
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINFAT_1C"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_GREMLINFAT_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_GREMLINFAT_2B");
        else
            CardCrawlGame.sound.play("VO_GREMLINFAT_2C");
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
            AbstractDungeon.effectList.add(new SpeechBubble(dialogX, dialogY, 3F, DIALOG[2], false));
        }
    }

    protected void getMove(int num)
    {
        setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
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

    public static final String ID = "GremlinFat";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 13;
    private static final int HP_MAX = 17;
    private static final int A_2_HP_MIN = 14;
    private static final int A_2_HP_MAX = 18;
    private static final int BLUNT_DAMAGE = 4;
    private static final int A_2_BLUNT_DAMAGE = 5;
    private static final int WEAK_AMT = 1;
    private static final byte BLUNT = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinFat");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
