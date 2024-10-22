// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractDailyMod.java

package com.megacrit.cardcrawl.daily.mods;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDataStringBuilder;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class AbstractDailyMod
{

    public AbstractDailyMod(String setId, String name, String description, String imgUrl, boolean positive)
    {
        this(setId, name, description, imgUrl, positive, null);
    }

    public AbstractDailyMod(String setId, String name, String description, String imgUrl, boolean positive, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass exclusion)
    {
        modID = setId;
        this.name = name;
        this.description = description;
        this.positive = positive;
        img = ImageMaster.loadImage((new StringBuilder()).append("images/ui/run_mods/").append(imgUrl).toString());
        classToExclude = exclusion;
    }

    public void effect()
    {
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData("name");
        sb.addFieldData("text");
        return sb.toString();
    }

    public String gameDataUploadData()
    {
        GameDataStringBuilder sb = new GameDataStringBuilder();
        sb.addFieldData(name);
        sb.addFieldData(description);
        return sb.toString();
    }

    public String name;
    public String description;
    public String modID;
    public Texture img;
    public boolean positive;
    public com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass classToExclude;
    private static final String IMG_DIR = "images/ui/run_mods/";
}
