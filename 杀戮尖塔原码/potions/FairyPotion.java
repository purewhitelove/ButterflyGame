// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FairyPotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class FairyPotion extends AbstractPotion
{

    public FairyPotion()
    {
        super(potionStrings.NAME, "FairyPotion", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.FAIRY, AbstractPotion.PotionColor.FAIRY);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        float percent = (float)potency / 100F;
        int healAmt = (int)((float)AbstractDungeon.player.maxHealth * percent);
        if(healAmt < 1)
            healAmt = 1;
        AbstractDungeon.player.heal(healAmt, true);
        AbstractDungeon.topPanel.destroyPotion(slot);
    }

    public boolean canUse()
    {
        return false;
    }

    public int getPotency(int ascensionLevel)
    {
        return 30;
    }

    public AbstractPotion makeCopy()
    {
        return new FairyPotion();
    }

    public static final String POTION_ID = "FairyPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("FairyPotion");
    }
}
