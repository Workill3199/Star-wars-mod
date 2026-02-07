package com.starwars.client;

import java.util.HashMap;
import java.util.Map;

public class ClientSkillData {
    private static Map<String, Integer> skills = new HashMap<>();

    public static void setSkills(Map<String, Integer> skills) {
        ClientSkillData.skills = skills;
    }

    public static int getSkillLevel(String skillId) {
        return skills.getOrDefault(skillId, 0);
    }
    
    public static boolean hasSkill(String skillId) {
        return getSkillLevel(skillId) > 0;
    }
}
