// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Accursed.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Accursed extends AbstractBlight
{

    public Accursed()
    {
        super("Accursed", NAME, (new StringBuilder()).append(DESC[0]).append(2).append(DESC[1]).toString(), "accursed.png", false);
        counter = 2;
    }

    public void stack()
    {
        counter += 2;
        updateDescription();
        flash();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESC[0]).append(counter).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onBossDefeat()
    {
        flash();
        Random posRandom = new Random();
        for(int i = 0; i < counter; i++)
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCardWithoutRng(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE), (float)Settings.WIDTH / 2.0F + posRandom.random(-((float)Settings.WIDTH / 3F), (float)Settings.WIDTH / 3F), (float)Settings.HEIGHT / 2.0F + posRandom.random(-((float)Settings.HEIGHT / 3F), (float)Settings.HEIGHT / 3F)));

    }

    public static final String ID = "Accursed";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("Accursed");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
