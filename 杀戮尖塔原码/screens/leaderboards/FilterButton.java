// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FilterButton.java

package com.megacrit.cardcrawl.screens.leaderboards;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.leaderboards:
//            LeaderboardScreen

public class FilterButton
{
    public static final class RegionSetting extends Enum
    {

        public static RegionSetting[] values()
        {
            return (RegionSetting[])$VALUES.clone();
        }

        public static RegionSetting valueOf(String name)
        {
            return (RegionSetting)Enum.valueOf(com/megacrit/cardcrawl/screens/leaderboards/FilterButton$RegionSetting, name);
        }

        public static final RegionSetting GLOBAL;
        public static final RegionSetting FRIEND;
        private static final RegionSetting $VALUES[];

        static 
        {
            GLOBAL = new RegionSetting("GLOBAL", 0);
            FRIEND = new RegionSetting("FRIEND", 1);
            $VALUES = (new RegionSetting[] {
                GLOBAL, FRIEND
            });
        }

        private RegionSetting(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class LeaderboardType extends Enum
    {

        public static LeaderboardType[] values()
        {
            return (LeaderboardType[])$VALUES.clone();
        }

        public static LeaderboardType valueOf(String name)
        {
            return (LeaderboardType)Enum.valueOf(com/megacrit/cardcrawl/screens/leaderboards/FilterButton$LeaderboardType, name);
        }

        public static final LeaderboardType HIGH_SCORE;
        public static final LeaderboardType FASTEST_WIN;
        public static final LeaderboardType CONSECUTIVE_WINS;
        public static final LeaderboardType AVG_FLOOR;
        public static final LeaderboardType AVG_SCORE;
        public static final LeaderboardType SPIRE_LEVEL;
        private static final LeaderboardType $VALUES[];

        static 
        {
            HIGH_SCORE = new LeaderboardType("HIGH_SCORE", 0);
            FASTEST_WIN = new LeaderboardType("FASTEST_WIN", 1);
            CONSECUTIVE_WINS = new LeaderboardType("CONSECUTIVE_WINS", 2);
            AVG_FLOOR = new LeaderboardType("AVG_FLOOR", 3);
            AVG_SCORE = new LeaderboardType("AVG_SCORE", 4);
            SPIRE_LEVEL = new LeaderboardType("SPIRE_LEVEL", 5);
            $VALUES = (new LeaderboardType[] {
                HIGH_SCORE, FASTEST_WIN, CONSECUTIVE_WINS, AVG_FLOOR, AVG_SCORE, SPIRE_LEVEL
            });
        }

        private LeaderboardType(String s, int i)
        {
            super(s, i);
        }
    }


    public FilterButton(String imgUrl, boolean active, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass, LeaderboardType lType, RegionSetting rType)
    {
        this.lType = null;
        this.rType = null;
        this.active = false;
        this.pClass = null;
        hb = new Hitbox(100F * Settings.scale, 100F * Settings.scale);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];
            static final int $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[];
            static final int $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$RegionSetting[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$RegionSetting = new int[RegionSetting.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$RegionSetting[RegionSetting.FRIEND.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$RegionSetting[RegionSetting.GLOBAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType = new int[LeaderboardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.CONSECUTIVE_WINS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.FASTEST_WIN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.HIGH_SCORE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.SPIRE_LEVEL.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.AVG_FLOOR.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[LeaderboardType.AVG_SCORE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
            }
        }

        if(pClass != null)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[pClass.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.FILTER_IRONCLAD;
                break;

            case 2: // '\002'
                img = ImageMaster.FILTER_SILENT;
                break;

            case 3: // '\003'
                img = ImageMaster.FILTER_DEFECT;
                break;

            case 4: // '\004'
                img = ImageMaster.FILTER_WATCHER;
                break;

            default:
                img = ImageMaster.FILTER_IRONCLAD;
                break;
            }
        else
        if(lType != null)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType[lType.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.FILTER_CHAIN;
                break;

            case 2: // '\002'
                img = ImageMaster.FILTER_TIME;
                break;

            case 3: // '\003'
                img = ImageMaster.FILTER_SCORE;
                break;

            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            default:
                img = ImageMaster.FILTER_CHAIN;
                break;
            }
        else
        if(rType != null)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting[rType.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.FILTER_FRIENDS;
                break;

            case 2: // '\002'
            default:
                img = ImageMaster.FILTER_GLOBAL;
                break;
            }
        this.lType = lType;
        this.rType = rType;
        this.active = active;
        this.pClass = pClass;
    }

    public FilterButton(String imgUrl, boolean active, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass)
    {
        this(imgUrl, active, pClass, null, null);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[pClass.ordinal()])
        {
        case 1: // '\001'
            label = TEXT[0];
            break;

        case 2: // '\002'
            label = TEXT[1];
            break;

        case 3: // '\003'
            label = TEXT[2];
            break;

        case 4: // '\004'
            label = TEXT[11];
            break;

        default:
            label = TEXT[0];
            break;
        }
        if(active)
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.charLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[2]).append(":  ").append(label).toString();
    }

    public FilterButton(String imgUrl, boolean active, LeaderboardType lType)
    {
        this(imgUrl, active, null, lType, null);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType[lType.ordinal()])
        {
        case 5: // '\005'
            label = TEXT[3];
            break;

        case 6: // '\006'
            label = TEXT[4];
            break;

        case 1: // '\001'
            label = TEXT[5];
            break;

        case 2: // '\002'
            label = TEXT[6];
            break;

        case 3: // '\003'
            label = TEXT[7];
            break;

        case 4: // '\004'
            label = TEXT[8];
            break;

        default:
            label = TEXT[7];
            break;
        }
        if(active)
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.typeLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[4]).append(":  ").append(label).toString();
    }

    public FilterButton(String imgUrl, boolean active, RegionSetting rType)
    {
        this(imgUrl, active, null, null, rType);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting[rType.ordinal()])
        {
        case 1: // '\001'
            label = TEXT[9];
            break;

        case 2: // '\002'
            label = TEXT[10];
            break;

        default:
            label = TEXT[9];
            break;
        }
        if(active)
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.regionLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[3]).append(":  ").append(label).toString();
    }

    public void update()
    {
        hb.update();
        if(hb.justHovered && !active)
            CardCrawlGame.sound.play("UI_HOVER");
        if(Settings.isControllerMode)
        {
            if(!active && hb.hovered && CInputActionSet.select.isJustPressed())
            {
                CInputActionSet.select.unpress();
                hb.clicked = true;
            }
        } else
        if(!active && hb.hovered && InputHelper.justClickedLeft && !CardCrawlGame.mainMenuScreen.leaderboardsScreen.waiting)
        {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
            hb.clickStarted = true;
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            if(!active)
                toggle(true);
        }
    }

    private void toggle(boolean refresh)
    {
        active = true;
        CardCrawlGame.mainMenuScreen.leaderboardsScreen.refresh = true;
        if(pClass != null)
        {
            Iterator iterator = CardCrawlGame.mainMenuScreen.leaderboardsScreen.charButtons.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                FilterButton b = (FilterButton)iterator.next();
                if(b != this)
                    b.active = false;
            } while(true);
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.charLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[2]).append(":  ").append(label).toString();
            return;
        }
        if(rType != null)
        {
            Iterator iterator1 = CardCrawlGame.mainMenuScreen.leaderboardsScreen.regionButtons.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                FilterButton b = (FilterButton)iterator1.next();
                if(b != this)
                    b.active = false;
            } while(true);
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.regionLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[3]).append(":  ").append(label).toString();
            return;
        }
        if(lType != null)
        {
            Iterator iterator2 = CardCrawlGame.mainMenuScreen.leaderboardsScreen.typeButtons.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                FilterButton b = (FilterButton)iterator2.next();
                if(b != this)
                    b.active = false;
            } while(true);
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.typeLabel = (new StringBuilder()).append(LeaderboardScreen.TEXT[4]).append(":  ").append(label).toString();
            return;
        } else
        {
            return;
        }
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        if(active)
        {
            sb.setColor(new Color(1.0F, 0.8F, 0.2F, 0.5F + (MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) + 1.25F) / 5F));
            sb.draw(ImageMaster.FILTER_GLOW_BG, x - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }
        if(hb.hovered || active)
        {
            sb.setColor(Color.WHITE);
            sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        } else
        {
            sb.setColor(Color.GRAY);
            sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
            sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        }
        hb.move(x, y);
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public LeaderboardType lType;
    public RegionSetting rType;
    public boolean active;
    public com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass;
    private Texture img;
    private static final int W = 128;
    public Hitbox hb;
    public String label;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("LeaderboardFilters");
        TEXT = uiStrings.TEXT;
    }
}
