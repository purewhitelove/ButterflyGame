// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BossChest.java

package com.megacrit.cardcrawl.rewards.chests;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.BlightChests;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Matryoshka;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.rewards.chests:
//            AbstractChest

public class BossChest extends AbstractChest
{

    public BossChest()
    {
        relics = new ArrayList();
        blights = new ArrayList();
        img = ImageMaster.BOSS_CHEST;
        openedImg = ImageMaster.BOSS_CHEST_OPEN;
        hb = new Hitbox(256F * Settings.scale, 200F * Settings.scale);
        hb.move(CHEST_LOC_X, CHEST_LOC_Y - 100F * Settings.scale);
        if(AbstractDungeon.actNum < 4 || !AbstractPlayer.customMods.contains("Blight Chests"))
        {
            relics.clear();
            for(int i = 0; i < 3; i++)
                relics.add(AbstractDungeon.returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS));

        } else
        {
            blights.clear();
            blights.add(BlightHelper.getRandomBlight());
            ArrayList exclusion = new ArrayList();
            exclusion.add(((AbstractBlight)blights.get(0)).blightID);
            blights.add(BlightHelper.getRandomChestBlight(exclusion));
        }
    }

    public void open(boolean bossChest)
    {
        if(AbstractDungeon.actNum < 4 || !AbstractPlayer.customMods.contains("Blight Chests"))
        {
            Iterator iterator = AbstractDungeon.player.relics.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator.next();
                if(!(r instanceof Matryoshka))
                    r.onChestOpen(true);
            } while(true);
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
            CardCrawlGame.sound.play("CHEST_OPEN");
            AbstractDungeon.bossRelicScreen.open(relics);
        } else
        {
            CardCrawlGame.sound.play("CHEST_OPEN");
            AbstractDungeon.bossRelicScreen.openBlight(blights);
        }
    }

    public void close()
    {
        CardCrawlGame.sound.play("CHEST_OPEN");
        isOpen = false;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public ArrayList relics;
    public ArrayList blights;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("BossChest");
        TEXT = uiStrings.TEXT;
    }
}
