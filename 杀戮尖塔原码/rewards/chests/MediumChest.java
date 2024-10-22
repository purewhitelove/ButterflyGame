// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MediumChest.java

package com.megacrit.cardcrawl.rewards.chests;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.rewards.chests:
//            AbstractChest

public class MediumChest extends AbstractChest
{

    public MediumChest()
    {
        img = ImageMaster.M_CHEST;
        openedImg = ImageMaster.M_CHEST_OPEN;
        hb = new Hitbox(256F * Settings.scale, 270F * Settings.scale);
        hb.move(CHEST_LOC_X, CHEST_LOC_Y - 90F * Settings.scale);
        COMMON_CHANCE = 35;
        UNCOMMON_CHANCE = 50;
        RARE_CHANCE = 15;
        GOLD_CHANCE = 35;
        GOLD_AMT = 50;
        randomizeReward();
    }
}
