// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StancePotion.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class StancePotion extends AbstractPotion
{

    public StancePotion()
    {
        super(potionStrings.NAME, "StancePotion", AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.WEAK);
        labOutlineColor = Settings.PURPLE_RELIC_COLOR;
        description = potionStrings.DESCRIPTIONS[0];
        isThrown = false;
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.CALM.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.CALM.NAMES[0])));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.WRATH.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.WRATH.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        InputHelper.moveCursorToNeutralPosition();
        ArrayList stanceChoices = new ArrayList();
        stanceChoices.add(new ChooseWrath());
        stanceChoices.add(new ChooseCalm());
        addToBot(new ChooseOneAction(stanceChoices));
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return new StancePotion();
    }

    public static final String POTION_ID = "StancePotion";
    public static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("StancePotion");
    }
}
