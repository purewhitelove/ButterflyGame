// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VoidEssence.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class VoidEssence extends AbstractBlight
{

    public VoidEssence()
    {
        super("VoidEssence", NAME, (new StringBuilder()).append(DESC[0]).append("1").append(DESC[1]).toString(), "void.png", false);
        counter = 1;
        updateDescription();
    }

    public void stack()
    {
        counter++;
        updateDescription();
        if(AbstractDungeon.player.energy.energyMaster > 0)
            AbstractDungeon.player.energy.energyMaster--;
        flash();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = (new StringBuilder()).append(DESC[0]).append(counter).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void updateDescription()
    {
        if(AbstractDungeon.player != null)
            description = (new StringBuilder()).append(DESC[0]).append(counter).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onEquip()
    {
        if(AbstractDungeon.player.energy.energyMaster > 0)
            AbstractDungeon.player.energy.energyMaster--;
    }

    public static final String ID = "VoidEssence";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("VoidEssence");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
