// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Spear.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Spear extends AbstractBlight
{

    public Spear()
    {
        super("DeadlyEnemies", NAME, (new StringBuilder()).append(DESC[0]).append(100).append(DESC[1]).toString(), "spear.png", true);
        damageMod = 2.0F;
        counter = 1;
    }

    public void incrementUp()
    {
        damageMod += 0.75F;
        increment++;
        counter++;
        description = (new StringBuilder()).append(DESC[0]).append((int)((damageMod - 1.0F) * 100F)).append(DESC[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public float effectFloat()
    {
        return damageMod;
    }

    public static final String ID = "DeadlyEnemies";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];
    public float damageMod;

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("DeadlyEnemies");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
