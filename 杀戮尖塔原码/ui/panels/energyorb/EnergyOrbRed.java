// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyOrbRed.java

package com.megacrit.cardcrawl.ui.panels.energyorb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.ui.panels.energyorb:
//            EnergyOrbInterface

public class EnergyOrbRed
    implements EnergyOrbInterface
{

    public EnergyOrbRed()
    {
    }

    public void updateOrb(int orbCount)
    {
        if(orbCount == 0)
        {
            angle5 += Gdx.graphics.getDeltaTime() * -5F;
            angle4 += Gdx.graphics.getDeltaTime() * 5F;
            angle3 += Gdx.graphics.getDeltaTime() * -8F;
            angle2 += Gdx.graphics.getDeltaTime() * 8F;
            angle1 += Gdx.graphics.getDeltaTime() * 72F;
        } else
        {
            angle5 += Gdx.graphics.getDeltaTime() * -20F;
            angle4 += Gdx.graphics.getDeltaTime() * 20F;
            angle3 += Gdx.graphics.getDeltaTime() * -40F;
            angle2 += Gdx.graphics.getDeltaTime() * 40F;
            angle1 += Gdx.graphics.getDeltaTime() * 360F;
        }
    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        if(enabled)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.ENERGY_RED_LAYER1, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER2, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER3, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER4, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER5, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER6, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
        } else
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.ENERGY_RED_LAYER1D, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER2D, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER3D, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER4D, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER5D, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
            sb.draw(ImageMaster.ENERGY_RED_LAYER6, current_x - 64F, current_y - 64F, 64F, 64F, 128F, 128F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
        }
    }

    private static final int ORB_W = 128;
    public static float fontScale = 1.0F;
    private static final float ORB_IMG_SCALE;
    private float angle5;
    private float angle4;
    private float angle3;
    private float angle2;
    private float angle1;

    static 
    {
        ORB_IMG_SCALE = 1.15F * Settings.scale;
    }
}
