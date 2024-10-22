// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Scatterbrain.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Scatterbrain extends AbstractBlight
{

    public Scatterbrain()
    {
        super("Scatterbrain", NAME, (new StringBuilder()).append(DESC[0]).append(1).append(DESC[1]).toString(), "scatter.png", false);
        counter = 1;
    }

    public void stack()
    {
        AbstractDungeon.player.masterHandSize--;
        counter++;
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

    public void onEquip()
    {
        AbstractDungeon.player.masterHandSize--;
    }

    public static final String ID = "Scatterbrain";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("Scatterbrain");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
