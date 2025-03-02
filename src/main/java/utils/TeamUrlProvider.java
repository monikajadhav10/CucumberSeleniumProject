package utils;

import java.util.HashMap;
import java.util.Map;

public class TeamUrlProvider {
    private static final Map<String, String> teamUrls = new HashMap<>();

    static {
        teamUrls.put("Warriors", "https://www.nba.com/warriors/");
        teamUrls.put("Sixers", "https://www.nba.com/sixers/");
        teamUrls.put("Bulls", "https://www.nba.com/bulls/ ");
    }

    public static String getTeamUrl(String teamName) {
        return teamUrls.getOrDefault(teamName, "https://www.nba.com/");
    }
}
