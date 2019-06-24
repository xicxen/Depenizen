package com.denizenscript.depenizen.bukkit.bridges;

import com.denizenscript.depenizen.bukkit.Bridge;
import com.denizenscript.depenizen.bukkit.properties.griefprevention.GriefPreventionPlayerProperties;
import com.denizenscript.depenizen.bukkit.objects.griefprevention.GriefPreventionClaim;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.dPlayer;
import com.denizenscript.depenizen.bukkit.events.griefprevention.GPClaimEnterEvent;
import com.denizenscript.depenizen.bukkit.properties.griefprevention.GriefPreventionLocationProperties;
import net.aufdemrand.denizencore.events.ScriptEvent;
import net.aufdemrand.denizencore.objects.ObjectFetcher;
import net.aufdemrand.denizencore.objects.properties.PropertyParser;

public class GriefPreventionBridge extends Bridge {

    @Override
    public void init() {
        ObjectFetcher.registerWithObjectFetcher(GriefPreventionClaim.class);
        PropertyParser.registerProperty(GriefPreventionPlayerProperties.class, dPlayer.class);
        PropertyParser.registerProperty(GriefPreventionLocationProperties.class, dLocation.class);
        ScriptEvent.registerScriptEvent(new GPClaimEnterEvent());
    }
}