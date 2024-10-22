// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ModIcons.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.screens.stats.RunData;
import java.util.*;

public class ModIcons
{

    public ModIcons()
    {
        dailyModList = new ArrayList();
        hitboxes = new ArrayList();
    }

    public void setRunData(RunData runData)
    {
        dailyModList.clear();
        hitboxes.clear();
        if(runData.daily_mods != null)
        {
            for(Iterator iterator = runData.daily_mods.iterator(); iterator.hasNext(); hitboxes.add(new Hitbox(DAILY_MOD_VISIBLE_SIZE, DAILY_MOD_VISIBLE_SIZE)))
            {
                String modId = (String)iterator.next();
                dailyModList.add(ModHelper.getMod(modId));
            }

        }
    }

    public boolean hasMods()
    {
        return dailyModList.size() > 0;
    }

    public void update()
    {
        boolean isHovered = false;
        for(int i = 0; i < hitboxes.size(); i++)
        {
            AbstractDailyMod mod = (AbstractDailyMod)dailyModList.get(i);
            Hitbox hbox = (Hitbox)hitboxes.get(i);
            hbox.update();
            if(hbox.hovered)
            {
                isHovered = true;
                TipHelper.renderGenericTip(hbox.x + 64F * Settings.scale, hbox.y + DAILY_MOD_VISIBLE_SIZE / 2.0F, mod.name, mod.description);
            }
        }

        if(isHovered)
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
    }

    public void renderDailyMods(SpriteBatch sb, float x, float y)
    {
        float drawX = x;
        float drawY = y - DAILY_MOD_VISIBLE_SIZE;
        for(int i = 0; i < dailyModList.size(); i++)
        {
            AbstractDailyMod mod = (AbstractDailyMod)dailyModList.get(i);
            Hitbox hbox = (Hitbox)hitboxes.get(i);
            float halfSize = DAILY_MOD_ICON_SIZE / 2.0F;
            float cx = drawX + halfSize;
            float cy = drawY + halfSize;
            hbox.move(cx, cy);
            hbox.render(sb);
            if(mod != null && mod.img != null)
            {
                float drawSize = DAILY_MOD_ICON_SIZE;
                float offset = 0.0F;
                if(hbox.hovered)
                {
                    offset = drawSize * 0.25F;
                    drawSize *= 1.5F;
                }
                sb.draw(mod.img, hbox.x - offset, hbox.y - offset, drawSize, drawSize);
            }
            drawX += DAILY_MOD_VISIBLE_SIZE;
        }

    }

    public float approximateWidth()
    {
        return (float)dailyModList.size() * DAILY_MOD_VISIBLE_SIZE;
    }

    private static final float DAILY_MOD_ICON_SIZE;
    private static final float DAILY_MOD_VISIBLE_SIZE;
    private static final float HOVER_SCALE = 1.5F;
    private ArrayList dailyModList;
    private ArrayList hitboxes;

    static 
    {
        DAILY_MOD_ICON_SIZE = 52F * Settings.scale;
        DAILY_MOD_VISIBLE_SIZE = DAILY_MOD_ICON_SIZE * 0.75F;
    }
}
