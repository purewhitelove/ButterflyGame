// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tingsha.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Tingsha extends AbstractRelic
{

    public Tingsha()
    {
        super("Tingsha", "tingsha.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void onManualDiscard()
    {
        flash();
        CardCrawlGame.sound.play("TINGSHA");
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 3, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
    }

    public void update()
    {
        super.update();
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.playA("TINGSHA", MathUtils.random(-0.2F, 0.1F));
            flash();
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Tingsha();
    }

    public static final String ID = "Tingsha";
    private static final int DMG_AMT = 3;
}
