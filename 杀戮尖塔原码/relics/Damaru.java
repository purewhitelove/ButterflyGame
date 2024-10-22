// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Damaru.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Damaru extends AbstractRelic
{

    public Damaru()
    {
        super("Damaru", "damaru.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void atTurnStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, null, new MantraPower(AbstractDungeon.player, 1), 1));
    }

    public void update()
    {
        super.update();
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.playA("DAMARU", MathUtils.random(-0.2F, 0.1F));
            flash();
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Damaru();
    }

    public static final String ID = "Damaru";
}
