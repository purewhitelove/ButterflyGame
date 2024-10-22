// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowEye.java

package com.megacrit.cardcrawl.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class NeowEye
{

    public NeowEye(int eyePosition)
    {
        currentFrame = 0;
        if(lid1 == null)
        {
            lid1 = ImageMaster.loadImage("images/scenes/neow/lid1.png");
            lid2 = ImageMaster.loadImage("images/scenes/neow/lid2.png");
            lid3 = ImageMaster.loadImage("images/scenes/neow/lid3.png");
            lid4 = ImageMaster.loadImage("images/scenes/neow/lid4.png");
            lid5 = ImageMaster.loadImage("images/scenes/neow/lid5.png");
            lid6 = ImageMaster.loadImage("images/scenes/neow/lid6.png");
            eyeImg = ImageMaster.loadImage("images/scenes/neow/eye.png");
        }
        switch(eyePosition)
        {
        case 0: // '\0'
            leftX = 1410F * Settings.xScale - 128F;
            rightX = 510F * Settings.xScale - 128F;
            y = (float)Settings.HEIGHT / 2.0F - 180F * Settings.scale - 128F;
            oY = 20F * Settings.scale;
            scale = Settings.scale;
            angle = -10F;
            eyeLidTimer = 3F;
            break;

        case 1: // '\001'
            leftX = 1610F * Settings.xScale - 128F;
            rightX = 310F * Settings.xScale - 128F;
            y = ((float)Settings.HEIGHT / 2.0F + 80F * Settings.scale) - 128F;
            oY = 15F * Settings.scale;
            scale = 0.75F * Settings.scale;
            angle = 10F;
            eyeLidTimer = 3.15F;
            break;

        case 2: // '\002'
            leftX = 1710F * Settings.xScale - 128F;
            rightX = 210F * Settings.xScale - 128F;
            y = ((float)Settings.HEIGHT / 2.0F + 290F * Settings.scale) - 128F;
            oY = 10F * Settings.scale;
            scale = 0.5F * Settings.scale;
            angle = 15F;
            eyeLidTimer = 3.3F;
            break;
        }
        eyeLid = lid1;
    }

    public void update()
    {
        eyeLidTimer -= Gdx.graphics.getDeltaTime();
        if(eyeLidTimer < 0.0F)
        {
            currentFrame++;
            if(currentFrame > 9)
                currentFrame = 0;
            switch(currentFrame)
            {
            case 0: // '\0'
                eyeLid = lid1;
                eyeLidTimer += 5F;
                break;

            case 1: // '\001'
                eyeLid = lid2;
                eyeLidTimer += 0.04F;
                break;

            case 2: // '\002'
                eyeLid = lid3;
                eyeLidTimer += 0.04F;
                break;

            case 3: // '\003'
                eyeLid = lid4;
                eyeLidTimer += 0.04F;
                break;

            case 4: // '\004'
                eyeLid = lid5;
                eyeLidTimer += 0.04F;
                break;

            case 5: // '\005'
                eyeLid = lid6;
                eyeLidTimer = 0.25F;
                break;

            case 6: // '\006'
                eyeLid = lid5;
                eyeLidTimer += 0.06F;
                break;

            case 7: // '\007'
                eyeLid = lid4;
                eyeLidTimer += 0.06F;
                break;

            case 8: // '\b'
                eyeLid = lid3;
                eyeLidTimer += 0.06F;
                break;

            case 9: // '\t'
                eyeLid = lid2;
                eyeLidTimer += 0.06F;
                break;
            }
        }
    }

    public void renderRight(SpriteBatch sb)
    {
        sb.draw(eyeImg, leftX, y + MathUtils.cosDeg((System.currentTimeMillis() / 12L) % 360L) * oY, 128F, 128F, 256F, 256F, scale, scale, angle, 0, 0, 256, 256, false, false);
        sb.draw(eyeLid, leftX, y + MathUtils.cosDeg((System.currentTimeMillis() / 12L) % 360L) * oY, 128F, 128F, 256F, 256F, scale, scale, angle, 0, 0, 256, 256, false, false);
    }

    public void renderLeft(SpriteBatch sb)
    {
        sb.draw(eyeImg, rightX, y + MathUtils.cosDeg((System.currentTimeMillis() / 12L) % 360L) * oY, 128F, 128F, 256F, 256F, scale, scale, -angle, 0, 0, 256, 256, true, false);
        sb.draw(eyeLid, rightX, y + MathUtils.cosDeg((System.currentTimeMillis() / 12L) % 360L) * oY, 128F, 128F, 256F, 256F, scale, scale, -angle, 0, 0, 256, 256, true, false);
    }

    private static Texture lid1;
    private static Texture lid2;
    private static Texture lid3;
    private static Texture lid4;
    private static Texture lid5;
    private static Texture lid6;
    private static Texture eyeImg;
    private Texture eyeLid;
    private int currentFrame;
    private float eyeLidTimer;
    private float leftX;
    private float rightX;
    private float y;
    private float oY;
    private float scale;
    private float angle;
}
