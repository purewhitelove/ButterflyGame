// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LiftOption.java

package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireLiftEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.campfire:
//            AbstractCampfireOption

public class LiftOption extends AbstractCampfireOption
{

    public LiftOption(boolean active)
    {
        label = TEXT[0];
        usable = active;
        description = active ? TEXT[1] : TEXT[2];
        img = ImageMaster.CAMPFIRE_TRAIN_BUTTON;
    }

    public void useOption()
    {
        if(usable)
            AbstractDungeon.effectList.add(new CampfireLiftEffect());
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Lift Option");
        TEXT = uiStrings.TEXT;
    }
}
