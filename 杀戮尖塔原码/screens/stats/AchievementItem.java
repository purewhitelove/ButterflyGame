// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AchievementItem.java

package com.megacrit.cardcrawl.screens.stats;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

// Referenced classes of package com.megacrit.cardcrawl.screens.stats:
//            StatsScreen, AchievementGrid

public class AchievementItem
{

    public AchievementItem(String title, String desc, String imgUrl, String key, boolean hidden)
    {
        hb = new Hitbox(160F * Settings.scale, 160F * Settings.scale);
        isUnlocked = UnlockTracker.achievementPref.getBoolean(key, false);
        this.key = key;
        if(isUnlocked)
        {
            CardCrawlGame.publisherIntegration.unlockAchievement(key);
            this.title = title;
            this.desc = desc;
            if(StatsScreen.atlas != null)
                img = StatsScreen.atlas.findRegion((new StringBuilder()).append("unlocked/").append(imgUrl).toString());
        } else
        if(hidden)
        {
            this.title = AchievementGrid.NAMES[26];
            this.desc = AchievementGrid.TEXT[26];
            if(StatsScreen.atlas != null)
                img = StatsScreen.atlas.findRegion((new StringBuilder()).append("locked/").append(imgUrl).toString());
        } else
        {
            this.title = title;
            this.desc = desc;
            if(StatsScreen.atlas != null)
                img = StatsScreen.atlas.findRegion((new StringBuilder()).append("locked/").append(imgUrl).toString());
        }
    }

    public AchievementItem(String title, String desc, String imgUrl, String key)
    {
        this(title, desc, imgUrl, key, false);
    }

    public void update()
    {
        if(hb != null)
        {
            hb.update();
            if(hb.hovered)
                TipHelper.renderGenericTip((float)InputHelper.mX + 100F * Settings.scale, InputHelper.mY, title, desc);
        }
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        if(!isUnlocked)
            sb.setColor(LOCKED_COLOR);
        else
            sb.setColor(Color.WHITE);
        if(hb.hovered)
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, Settings.scale * 1.1F, Settings.scale * 1.1F, 0.0F);
        else
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, Settings.scale, Settings.scale, 0.0F);
        hb.move(x, y);
        hb.render(sb);
    }

    public void reloadImg()
    {
        img = StatsScreen.atlas.findRegion(img.name);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    public Hitbox hb;
    private String title;
    private String desc;
    public String key;
    public boolean isUnlocked;
    private static final Color LOCKED_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.8F);

}
