// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cauldron.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Cauldron extends AbstractRelic
{

    public Cauldron()
    {
        super("Cauldron", "cauldron.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        for(int i = 0; i < 5; i++)
            AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());

        AbstractDungeon.combatRewardScreen.open(DESCRIPTIONS[1]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
        int remove = -1;
        int i = 0;
        do
        {
            if(i >= AbstractDungeon.combatRewardScreen.rewards.size())
                break;
            if(((RewardItem)AbstractDungeon.combatRewardScreen.rewards.get(i)).type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD)
            {
                remove = i;
                break;
            }
            i++;
        } while(true);
        if(remove != -1)
            AbstractDungeon.combatRewardScreen.rewards.remove(remove);
    }

    public AbstractRelic makeCopy()
    {
        return new Cauldron();
    }

    public static final String ID = "Cauldron";
}
