// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PowerTip.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PowerTip
{

    public PowerTip()
    {
    }

    public PowerTip(String header, String body)
    {
        this.header = header;
        this.body = body;
        img = null;
        imgRegion = null;
    }

    public PowerTip(String header, String body, Texture img)
    {
        this.header = header;
        this.body = body;
        this.img = img;
        imgRegion = null;
    }

    public PowerTip(String header, String body, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region48)
    {
        this.header = header;
        this.body = body;
        imgRegion = region48;
        img = null;
    }

    public Texture img;
    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion imgRegion;
    public String header;
    public String body;
}
