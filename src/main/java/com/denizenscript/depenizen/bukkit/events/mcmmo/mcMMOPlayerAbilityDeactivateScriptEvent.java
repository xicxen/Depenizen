package com.denizenscript.depenizen.bukkit.events.mcmmo;

import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class mcMMOPlayerAbilityDeactivateScriptEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // mcmmo player deactivates <'ability'> for <'skill'>
    //
    // @Location true
    //
    // @Triggers when a player deactivates mcmmo ability.
    //
    // @Context
    // <context.skill> returns the name of the skill that the ability comes from.
    // (Based on the mcMMO language file).
    // <context.ability> returns the name of the ability.
    // <context.skill_level> returns the skill level from the ability.
    //
    // @Plugin Depenizen, mcMMO
    //
    // @Player Always.
    //
    // @Group Depenizen
    //
    // -->

    public mcMMOPlayerAbilityDeactivateScriptEvent() {
        instance = this;
        registerCouldMatcher("mcmmo player deactivates <'ability'> for <'skill'>");
    }

    public static mcMMOPlayerAbilityDeactivateScriptEvent instance;
    public McMMOPlayerAbilityDeactivateEvent event;
    public PlayerTag player;
    public ElementTag skill;
    public ElementTag skill_level;
    public ElementTag ability;

    @Override
    public boolean matches(ScriptPath path) {
        String eAbility = path.eventArgLowerAt(3);
        String eSkill = path.eventArgLowerAt(5);
        if (!eSkill.equals("skill") && !eSkill.equals(CoreUtilities.toLowerCase(skill.asString()))) {
            return false;
        }
        if (!eAbility.equals("ability") && !eAbility.equals(CoreUtilities.toLowerCase(ability.asString()))) {
            return false;
        }

        if (!runInCheck(path, player.getLocation())) {
            return false;
        }

        return super.matches(path);
    }

    @Override
    public String getName() {
        return "McMMOPlayerAbilityDeactivateEvent";
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @Override
    public ObjectTag getContext(String name) {
        switch (name) {
            case "skill":
                return skill;
            case "skill_level":
                return skill_level;
            case "ability":
                return ability;
        }
        return super.getContext(name);
    }

    @EventHandler
    public void onMcMMOPlayerAbilityDeactivateEvent(McMMOPlayerAbilityDeactivateEvent event) {
        if (EntityTag.isNPC(event.getPlayer())) {
            return;
        }
        player = PlayerTag.mirrorBukkitPlayer(event.getPlayer());
        ability = new ElementTag(event.getAbility().getName());
        skill = new ElementTag(event.getSkill().getName());
        skill_level = new ElementTag(event.getSkillLevel());
        this.event = event;
        fire(event);
    }
}
