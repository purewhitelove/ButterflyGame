// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomTrial.java

package com.megacrit.cardcrawl.trials;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.megacrit.cardcrawl.trials:
//            AbstractTrial

public class CustomTrial extends AbstractTrial
{

    public CustomTrial()
    {
        isKeepingStarterRelic = true;
        relicIds = new ArrayList();
        isKeepingStarterCards = true;
        cardIds = new ArrayList();
        dailyModIds = new ArrayList();
        maxHpOverride = null;
    }

    public void setMaxHpOverride(int maxHp)
    {
        maxHpOverride = Integer.valueOf(maxHp);
    }

    public void addStarterCards(List moreCardIds)
    {
        cardIds.addAll(moreCardIds);
    }

    public void setStarterCards(List starterCards)
    {
        cardIds.clear();
        cardIds.addAll(starterCards);
        isKeepingStarterCards = false;
    }

    public void addStarterRelic(String relicId)
    {
        relicIds.add(relicId);
    }

    public void addStarterRelics(List moreRelics)
    {
        relicIds.addAll(moreRelics);
    }

    public void setStarterRelics(List starterRelics)
    {
        relicIds.clear();
        relicIds.addAll(starterRelics);
        isKeepingStarterRelic = false;
    }

    public void setShouldKeepStarterRelic(boolean shouldKeep)
    {
        isKeepingStarterRelic = shouldKeep;
    }

    public void addDailyMod(String modId)
    {
        dailyModIds.add(modId);
    }

    public void addDailyMods(List moreDailyMods)
    {
        dailyModIds.addAll(moreDailyMods);
    }

    public void setRandomDailyMods()
    {
        useRandomDailyMods = true;
    }

    public AbstractPlayer setupPlayer(AbstractPlayer player)
    {
        if(maxHpOverride != null)
        {
            player.maxHealth = maxHpOverride.intValue();
            player.currentHealth = maxHpOverride.intValue();
        }
        return player;
    }

    public boolean keepStarterRelic()
    {
        return isKeepingStarterRelic;
    }

    public List extraStartingRelicIDs()
    {
        return relicIds;
    }

    public boolean keepsStarterCards()
    {
        return isKeepingStarterCards;
    }

    public List extraStartingCardIDs()
    {
        return cardIds;
    }

    public boolean useRandomDailyMods()
    {
        return useRandomDailyMods;
    }

    public ArrayList dailyModIDs()
    {
        return dailyModIds;
    }

    private boolean isKeepingStarterRelic;
    private ArrayList relicIds;
    private boolean isKeepingStarterCards;
    private ArrayList cardIds;
    private boolean useRandomDailyMods;
    private ArrayList dailyModIds;
    private Integer maxHpOverride;
}
