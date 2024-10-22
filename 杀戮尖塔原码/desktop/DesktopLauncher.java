// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DesktopLauncher.java

package com.megacrit.cardcrawl.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.DisplayConfig;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.SystemStats;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.desktop:
//            STSDisplayModeCallback

public class DesktopLauncher
{

    public DesktopLauncher()
    {
    }

    static void OverrideLibraryLoadingFix()
    {
        if(!System.getProperty("os.name").toLowerCase().contains("windows"))
            return;
        Path normalPath = Paths.get(System.getProperty("java.io.tmpdir"), new String[] {
            "libgdx", System.getProperty("user.name")
        });
        if(Is7bitAscii(normalPath.toAbsolutePath().toString()))
            return;
        System.out.println((new StringBuilder()).append("Detected invalid path: ").append(normalPath).toString());
        GdxNativesLoader.disableNativesLoading = true;
        Path lib = Paths.get(System.getProperty("user.dir"), new String[] {
            "lib"
        }).toAbsolutePath();
        System.out.println((new StringBuilder()).append("Loading libs extracted to custom path: ").append(lib).toString());
        System.setProperty("org.lwjgl.librarypath", lib.toString());
        System.setProperty("com.codedisaster.steamworks.SharedLibraryExtractPath", lib.toString());
        System.setProperty("com.codedisaster.steamworks.SDKLibraryPath", lib.toString());
        try
        {
            Files.createDirectories(lib, new FileAttribute[0]);
            SharedLibraryLoader loader = new SharedLibraryLoader();
            loader.extractFileTo(SharedLibraryLoader.is64Bit ? "gdx64.dll" : "gdx.dll", new File(lib.toString()));
            loader.extractFileTo(SharedLibraryLoader.is64Bit ? "lwjgl64.dll" : "lwjgl.dll", new File(lib.toString()));
            if(!LwjglApplicationConfiguration.disableAudio)
                loader.extractFileTo(SharedLibraryLoader.is64Bit ? "OpenAL64.dll" : "OpenAL32.dll", new File(lib.toString()));
        }
        catch(IOException e)
        {
            logger.info("Exception occurred while initializing application!");
            ExceptionHandler.handleException(e, logger);
            Gdx.app.exit();
        }
        System.load(Paths.get(lib.toString(), new String[] {
            "gdx64.dll"
        }).toAbsolutePath().toString());
    }

    public static boolean Is7bitAscii(String str)
    {
        char ac[] = str.toCharArray();
        int i = ac.length;
        for(int j = 0; j < i; j++)
        {
            char c = ac[j];
            if(c > '\177')
                return false;
        }

        return true;
    }

    public static void main(String arg[])
    {
        logger.info((new StringBuilder()).append("time: ").append(System.currentTimeMillis()).toString());
        logger.info((new StringBuilder()).append("version: ").append(CardCrawlGame.TRUE_VERSION_NUM).toString());
        logger.info("libgdx:  1.9.5");
        logger.info((new StringBuilder()).append("default_locale: ").append(Locale.getDefault()).toString());
        logger.info((new StringBuilder()).append("default_charset: ").append(Charset.defaultCharset()).toString());
        logger.info((new StringBuilder()).append("default_encoding: ").append(System.getProperty("file.encoding")).toString());
        logger.info((new StringBuilder()).append("java_version: ").append(System.getProperty("java.version")).toString());
        logger.info((new StringBuilder()).append("os_arch: ").append(System.getProperty("os.arch")).toString());
        logger.info((new StringBuilder()).append("os_name: ").append(System.getProperty("os.name")).toString());
        logger.info((new StringBuilder()).append("os_version: ").append(System.getProperty("os.version")).toString());
        SystemStats.logMemoryStats();
        SystemStats.logDiskStats();
        try
        {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.setDisplayModeCallback = new STSDisplayModeCallback();
            config.addIcon("images/ui/icon.png", com.badlogic.gdx.Files.FileType.Internal);
            config.resizable = false;
            config.title = "Slay the Spire";
            loadSettings(config);
            logger.info("Launching application...");
            new LwjglApplication(new CardCrawlGame(config.preferencesDirectory), config);
        }
        catch(Exception e)
        {
            logger.info("Exception occurred while initializing application!");
            ExceptionHandler.handleException(e, logger);
            Gdx.app.exit();
        }
    }

    private static void loadSettings(LwjglApplicationConfiguration config)
    {
        DisplayConfig displayConf = DisplayConfig.readConfig();
        if(displayConf.getWidth() < 800 || displayConf.getHeight() < 450)
        {
            logger.info("[ERROR] Display Config set lower than minimum allowed, resetting config.");
            config.width = 1280;
            config.height = 720;
            DisplayConfig.writeDisplayConfigFile(1280, 720, displayConf.getMaxFPS(), displayConf.getIsFullscreen(), displayConf.getWFS(), displayConf.getIsVsync());
        } else
        {
            config.height = displayConf.getHeight();
            config.width = displayConf.getWidth();
        }
        config.foregroundFPS = displayConf.getMaxFPS();
        config.backgroundFPS = config.foregroundFPS;
        if(displayConf.getIsFullscreen())
        {
            logger.info("[FULLSCREEN_MODE]");
            System.setProperty("org.lwjgl.opengl.Display.enableOSXFullscreenModeAPI", "true");
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            config.fullscreen = true;
            config.height = displayConf.getHeight();
            config.width = displayConf.getWidth();
            logger.info((new StringBuilder()).append("Running the game in: ").append(config.width).append(" x ").append(config.height).toString());
        } else
        {
            config.fullscreen = false;
            if(displayConf.getWFS() && config.width == LwjglApplicationConfiguration.getDesktopDisplayMode().width && config.height == LwjglApplicationConfiguration.getDesktopDisplayMode().height)
            {
                logger.info("[BORDERLESS_FULLSCREEN_MODE]");
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
                config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
                logger.info((new StringBuilder()).append("Running the game in: ").append(config.width).append(" x ").append(config.height).toString());
            } else
            {
                logger.info("[WINDOWED_MODE]");
            }
        }
        if(config.fullscreen && (displayConf.getWidth() > LwjglApplicationConfiguration.getDesktopDisplayMode().width || displayConf.getHeight() > LwjglApplicationConfiguration.getDesktopDisplayMode().height))
        {
            logger.info("[ERROR] Monitor resolution is lower than config, resetting config.");
            config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
            config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
            DisplayConfig.writeDisplayConfigFile(config.width, config.height, displayConf.getMaxFPS(), false, false, displayConf.getIsVsync());
        }
        config.vSyncEnabled = displayConf.getIsVsync();
        logger.info("Settings successfully loaded");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/desktop/DesktopLauncher.getName());

    static 
    {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        OverrideLibraryLoadingFix();
    }
}
