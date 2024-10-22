// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinWarrior.java

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
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.ArrayList;

public class GremlinWarrior extends AbstractMonster
{

    public GremlinWarrior(float x, float y)
    {
        super(NAME, "GremlinWarrior", 24, -4F, 12F, 130F, 194F, null, x, y);
        dialogY = 30F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(21, 25);
        else
            setHp(20, 24);
        if(AbstractDungeon.ascensionLevel >= 2)
            damage.add(new DamageInfo(this, 5));
        else
            damage.add(new DamageInfo(this, 4));
        loadAnimation("images/monsters/theBottom/angryGremlin/skeleton.atlas", "images/monsters/theBottom/angryGremlin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 17)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngryPower(this, 2)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngryPower(this, 1)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if(escapeNext)
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ESCAPE));
            else
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
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
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINANGRY_1C"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_GREMLINANGRY_2A");
        else
            CardCrawlGame.sound.play("VO_GREMLINANGRY_2B");
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
        setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
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

    public static final String ID = "GremlinWarrior";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int SCRATCH_DAMAGE = 4;
    private static final int A_2_SCRATCH_DAMAGE = 5;
    private static final byte SCRATCH = 1;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 24;
    private static final int A_2_HP_MIN = 21;
    private static final int A_2_HP_MAX = 25;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWarrior");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
