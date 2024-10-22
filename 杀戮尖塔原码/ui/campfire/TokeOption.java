// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TokeOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class TokeOption extends AbstractCampfireOption
{

    public TokeOption(boolean active)
    {
        label = TEXT[0];
        usable = active;
        description = TEXT[1];
        img = ImageMaster.CAMPFIRE_TOKE_BUTTON;
    }

    public void useOption()
    {
        if(usable)
            AbstractDungeon.effectList.add(new CampfireTokeEffect());
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Toke Option");
        TEXT = uiStrings.TEXT;
    }
}
