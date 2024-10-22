// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BuildSettings.java

package com.megacrit.cardcrawl.core;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            BuildSettingsException

class BuildSettings
{

    BuildSettings(Reader reader)
        throws IOException
    {
        prop.load(reader);
    }

    String getDistributor()
        throws BuildSettingsException
    {
        String distributor = prop.getProperty("distributor");
        if(distributor != null)
            return distributor;
        else
            throw new BuildSettingsException("The key 'distributor' is null in file=build.properties");
    }

    private final Properties prop = new Properties();
    public static final String defaultFilename = "build.properties";
}
