// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreasureRoomBoss.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.BlightChests;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.scene.SpookierChestEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class TreasureRoomBoss extends AbstractRoom
{

    public TreasureRoomBoss()
    {
        shinyTimer = 0.0F;
        choseRelic = false;
        CardCrawlGame.nextDungeon = getNextDungeonName();
        if(AbstractDungeon.actNum < 4 || !AbstractPlayer.customMods.contains("Blight Chests"))
            phase = AbstractRoom.RoomPhase.COMPLETE;
        else
            phase = AbstractRoom.RoomPhase.INCOMPLETE;
        mapImg = ImageMaster.MAP_NODE_TREASURE;
        mapImgOutline = ImageMaster.MAP_NODE_TREASURE_OUTLINE;
    }

    private String getNextDungeonName()
    {
        String s = AbstractDungeon.id;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return "TheCity";

        case 1: // '\001'
            return "TheBeyond";

        case 2: // '\002'
            if(Settings.isEndless)
                return "Exordium";
            else
                return null;
        }
        return null;
    }

    public void onPlayerEntry()
    {
        CardCrawlGame.music.silenceBGM();
        if(AbstractDungeon.actNum < 4 || !AbstractPlayer.customMods.contains("Blight Chests"))
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        playBGM("SHRINE");
        chest = new BossChest();
    }

    public void update()
    {
        super.update();
        chest.update();
        updateShiny();
    }

    private void updateShiny()
    {
        if(!chest.isOpen)
        {
            shinyTimer -= Gdx.graphics.getDeltaTime();
            if(shinyTimer < 0.0F && !Settings.DISABLE_EFFECTS)
            {
                shinyTimer = 0.02F;
                AbstractDungeon.effectList.add(new SpookierChestEffect());
                AbstractDungeon.effectList.add(new SpookierChestEffect());
            }
        }
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
    }

    public void render(SpriteBatch sb)
    {
        chest.render(sb);
        super.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public AbstractChest chest;
    private float shinyTimer;
    private static final float SHINY_INTERVAL = 0.02F;
    public boolean choseRelic;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("TreasureRoomBoss");
        TEXT = uiStrings.TEXT;
    }
}
