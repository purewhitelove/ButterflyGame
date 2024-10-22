// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BronzeOrb.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.monsters.city:
//            BronzeAutomaton

public class BronzeOrb extends AbstractMonster
{

    public BronzeOrb(float x, float y, int count)
    {
        super(monsterStrings.NAME, "BronzeOrb", AbstractDungeon.monsterHpRng.random(52, 58), 0.0F, 0.0F, 160F, 160F, "images/monsters/theCity/automaton/orb.png", x, y);
        usedStasis = false;
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(54, 60);
        else
            setHp(52, 58);
        this.count = count;
        damage.add(new DamageInfo(this, 8));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, hb.cX, hb.cY), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.getMonsters().getMonster("BronzeAutomaton"), this, 12));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(this));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void update()
    {
        super.update();
        if(count % 2 == 0)
            animY = MathUtils.cosDeg((System.currentTimeMillis() / 6L) % 360L) * 6F * Settings.scale;
        else
            animY = -MathUtils.cosDeg((System.currentTimeMillis() / 6L) % 360L) * 6F * Settings.scale;
    }

    protected void getMove(int num)
    {
        if(!usedStasis && num >= 25)
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            usedStasis = true;
            return;
        }
        if(num >= 70 && !lastTwoMoves((byte)2))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            return;
        }
        if(!lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 8);
            return;
        } else
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND);
            return;
        }
    }

    public static final String ID = "BronzeOrb";
    private static final MonsterStrings monsterStrings;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 52;
    private static final int HP_MAX = 58;
    private static final int A_2_HP_MIN = 54;
    private static final int A_2_HP_MAX = 60;
    private static final int BEAM_DMG = 8;
    private static final int BLOCK_AMT = 12;
    private static final byte BEAM = 1;
    private static final byte SUPPORT_BEAM = 2;
    private static final byte STASIS = 3;
    private boolean usedStasis;
    private int count;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BronzeOrb");
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
