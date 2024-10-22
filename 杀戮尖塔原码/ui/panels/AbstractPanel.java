// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractPanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPanel
{

    public AbstractPanel(float show_x, float show_y, float hide_x, float hide_y, float shadow_offset_x, float shadow_offset_y, Texture img, 
            boolean startHidden)
    {
        isHidden = false;
        doneAnimating = true;
        this.img = img;
        this.show_x = show_x;
        this.show_y = show_y;
        this.hide_x = hide_x;
        this.hide_y = hide_y;
        if(img != null)
        {
            img_width = (float)img.getWidth() * Settings.scale;
            img_height = (float)img.getHeight() * Settings.scale;
        }
        if(startHidden)
        {
            current_x = hide_x;
            current_y = hide_y;
            target_x = hide_x;
            target_y = hide_y;
            isHidden = true;
        } else
        {
            current_x = show_x;
            current_y = show_y;
            target_x = show_x;
            target_y = show_y;
            isHidden = false;
        }
    }

    public AbstractPanel(float show_x, float show_y, float hide_x, float hide_y, Texture img, boolean startHidden)
    {
        this(show_x, show_y, hide_x, hide_y, 0.0F, 0.0F, img, startHidden);
    }

    public void show()
    {
        if(isHidden)
        {
            target_x = show_x;
            target_y = show_y;
            isHidden = false;
            doneAnimating = false;
        } else
        if(Settings.isDebug)
            logger.info("Attempting to show panel through already shown");
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_x = hide_x;
            target_y = hide_y;
            isHidden = true;
            doneAnimating = false;
        }
    }

    public void updatePositions()
    {
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 7F);
            if(Math.abs(current_x - target_x) < 0.5F)
            {
                current_x = target_x;
                doneAnimating = true;
            } else
            {
                doneAnimating = false;
            }
        }
        if(current_y != target_y)
        {
            current_y = MathUtils.lerp(current_y, target_y, Gdx.graphics.getDeltaTime() * 7F);
            if(Math.abs(current_y - target_y) < 0.5F)
            {
                current_y = target_y;
                doneAnimating = true;
            } else
            {
                doneAnimating = false;
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        if(img != null)
        {
            sb.setColor(Color.WHITE);
            sb.draw(img, current_x, current_y, img_width, img_height, 0, 0, img.getWidth(), img.getHeight(), false, false);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/ui/panels/AbstractPanel.getName());
    private static final float SNAP_THRESHOLD = 0.5F;
    private static final float LERP_SPEED = 7F;
    public float hide_x;
    public float hide_y;
    public float show_x;
    public float show_y;
    protected Texture img;
    protected float img_width;
    protected float img_height;
    public boolean isHidden;
    public float target_x;
    public float target_y;
    public float current_x;
    public float current_y;
    public boolean doneAnimating;

}
