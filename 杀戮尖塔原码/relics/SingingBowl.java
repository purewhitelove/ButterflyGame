// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingingBowl.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SingingBowl extends AbstractRelic
{

    public SingingBowl()
    {
        super("Singing Bowl", "singingBowl.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void update()
    {
        super.update();
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.playA("SINGING_BOWL", MathUtils.random(-0.2F, 0.1F));
            flash();
        }
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new SingingBowl();
    }

    public static final String ID = "Singing Bowl";
    public static final int HP_AMT = 2;
}
