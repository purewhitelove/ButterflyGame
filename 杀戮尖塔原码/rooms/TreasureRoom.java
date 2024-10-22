// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreasureRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.ChestShineEffect;
import com.megacrit.cardcrawl.vfx.scene.SpookyChestEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class TreasureRoom extends AbstractRoom
{

    public TreasureRoom()
    {
        shinyTimer = 0.0F;
        phase = AbstractRoom.RoomPhase.COMPLETE;
        mapSymbol = "T";
        mapImg = ImageMaster.MAP_NODE_TREASURE;
        mapImgOutline = ImageMaster.MAP_NODE_TREASURE_OUTLINE;
    }

    public void onPlayerEntry()
    {
        playBGM(null);
        chest = AbstractDungeon.getRandomChest();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
    }

    public void update()
    {
        super.update();
        if(chest != null)
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
                shinyTimer = 0.2F;
                AbstractDungeon.topLevelEffects.add(new ChestShineEffect());
                AbstractDungeon.effectList.add(new SpookyChestEffect());
                AbstractDungeon.effectList.add(new SpookyChestEffect());
            }
        }
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
    }

    public void render(SpriteBatch sb)
    {
        if(chest != null)
            chest.render(sb);
        super.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public AbstractChest chest;
    private float shinyTimer;
    private static final float SHINY_INTERVAL = 0.2F;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("TreasureRoom");
        TEXT = uiStrings.TEXT;
    }
}
