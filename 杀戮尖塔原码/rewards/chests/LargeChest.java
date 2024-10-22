// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LargeChest.java

package com.megacrit.cardcrawl.rewards.chests;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.rewards.chests:
//            AbstractChest

public class LargeChest extends AbstractChest
{

    public LargeChest()
    {
        img = ImageMaster.L_CHEST;
        openedImg = ImageMaster.L_CHEST_OPEN;
        hb = new Hitbox(340F * Settings.scale, 200F * Settings.scale);
        hb.move(CHEST_LOC_X, CHEST_LOC_Y - 120F * Settings.scale);
        COMMON_CHANCE = 0;
        UNCOMMON_CHANCE = 75;
        RARE_CHANCE = 25;
        GOLD_CHANCE = 50;
        GOLD_AMT = 75;
        randomizeReward();
    }
}
