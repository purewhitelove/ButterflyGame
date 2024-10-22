// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Shield.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Shield extends AbstractBlight
{

    public Shield()
    {
        super("ToughEnemies", NAME, (new StringBuilder()).append(DESC[0]).append(50).append(DESC[1]).toString(), "shield.png", true);
        toughMod = 1.5F;
        counter = 1;
    }

    public void incrementUp()
    {
        toughMod += 0.5F;
        increment++;
        counter++;
        description = (new StringBuilder()).append(DESC[0]).append((int)((toughMod - 1.0F) * 100F)).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public float effectFloat()
    {
        return toughMod;
    }

    public static final String ID = "ToughEnemies";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];
    public float toughMod;

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("ToughEnemies");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
