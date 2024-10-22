// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightningOrbActivateEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class LightningOrbActivateEffect extends AbstractGameEffect
{

    public LightningOrbActivateEffect(float x, float y)
    {
        img = null;
        index = 0;
        renderBehind = false;
        color = Settings.LIGHT_YELLOW_COLOR.cpy();
        if(regions == null)
        {
            regions = new ArrayList();
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb1"));
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb2"));
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb3"));
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb4"));
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb5"));
            regions.add(ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb6"));
        }
        img = (com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion)regions.get(index);
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        scale = 2.0F * Settings.scale;
        duration = 0.03F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            index++;
            if(index > regions.size() - 1)
            {
                isDone = true;
                return;
            }
            img = (com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion)regions.get(index);
            duration = 0.03F;
        }
        color.a -= Gdx.graphics.getDeltaTime() * 2.0F;
        if(color.a < 0.0F)
            color.a = 0.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static ArrayList regions = null;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private int index;
    private float x;
    private float y;

}
