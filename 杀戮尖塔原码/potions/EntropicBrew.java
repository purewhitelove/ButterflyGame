// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntropicBrew.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class EntropicBrew extends AbstractPotion
{

    public EntropicBrew()
    {
        super(potionStrings.NAME, "EntropicBrew", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.M, AbstractPotion.PotionEffect.RAINBOW, Color.WHITE, null, null);
        description = potionStrings.DESCRIPTIONS[0];
        potency = getPotency();
        isThrown = false;
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            for(int i = 0; i < AbstractDungeon.player.potionSlots; i++)
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));

        } else
        if(AbstractDungeon.player.hasRelic("Sozu"))
        {
            AbstractDungeon.player.getRelic("Sozu").flash();
        } else
        {
            for(int i = 0; i < AbstractDungeon.player.potionSlots; i++)
                AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(AbstractDungeon.returnRandomPotion()));

        }
    }

    public boolean canUse()
    {
        if(AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            return false;
        return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
    }

    public int getPotency(int ascensionLevel)
    {
        if(AbstractDungeon.player != null)
            return AbstractDungeon.player.potionSlots;
        else
            return 3;
    }

    public AbstractPotion makeCopy()
    {
        return new EntropicBrew();
    }

    public static final String POTION_ID = "EntropicBrew";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("EntropicBrew");
    }
}
