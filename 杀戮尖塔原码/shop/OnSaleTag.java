// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OnSaleTag.java

package com.megacrit.cardcrawl.shop;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class OnSaleTag
{

    public OnSaleTag(AbstractCard c)
    {
        card = c;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage = new int[com.megacrit.cardcrawl.core.Settings.GameLanguage.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.EPO.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FIN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.THA.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
            }
        }

        if(img == null)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.loadImage("images/npcs/sale_tag/deu.png");
                break;

            case 2: // '\002'
                img = ImageMaster.loadImage("images/npcs/sale_tag/epo.png");
                break;

            case 3: // '\003'
                img = ImageMaster.loadImage("images/npcs/sale_tag/fin.png");
                break;

            case 4: // '\004'
                img = ImageMaster.loadImage("images/npcs/sale_tag/fra.png");
                break;

            case 5: // '\005'
                img = ImageMaster.loadImage("images/npcs/sale_tag/ita.png");
                break;

            case 6: // '\006'
                img = ImageMaster.loadImage("images/npcs/sale_tag/jpn.png");
                break;

            case 7: // '\007'
                img = ImageMaster.loadImage("images/npcs/sale_tag/kor.png");
                break;

            case 8: // '\b'
                img = ImageMaster.loadImage("images/npcs/sale_tag/rus.png");
                break;

            case 9: // '\t'
                img = ImageMaster.loadImage("images/npcs/sale_tag/tha.png");
                break;

            case 10: // '\n'
                img = ImageMaster.loadImage("images/npcs/sale_tag/ukr.png");
                break;

            case 11: // '\013'
                img = ImageMaster.loadImage("images/npcs/sale_tag/zhs.png");
                break;

            default:
                img = ImageMaster.loadImage("images/npcs/sale_tag/eng.png");
                break;
            }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(img, card.current_x + 30F * Settings.scale + (card.drawScale - 0.75F) * 60F * Settings.scale, card.current_y + 70F * Settings.scale + (card.drawScale - 0.75F) * 90F * Settings.scale, W * card.drawScale, W * card.drawScale);
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, (MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) + 1.25F) / 3F));
        sb.draw(img, card.current_x + 30F * Settings.scale + (card.drawScale - 0.75F) * 60F * Settings.scale, card.current_y + 70F * Settings.scale + (card.drawScale - 0.75F) * 90F * Settings.scale, W * card.drawScale, W * card.drawScale);
        sb.setBlendFunction(770, 771);
    }

    public AbstractCard card;
    private static final float W;
    public static Texture img = null;

    static 
    {
        W = 128F * Settings.scale;
    }
}
