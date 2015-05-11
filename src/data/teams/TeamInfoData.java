package data.teams;

import databaseutility.MEM;
import dataservice.teams.TeamInfoDataService;

public class TeamInfoData implements TeamInfoDataService {
	private static TeamInfoData teamInfoData = null;

	private TeamInfoData() {
	}

	public static TeamInfoData getInstance() {
		if (teamInfoData == null) {
			teamInfoData = new TeamInfoData();
		}
		return teamInfoData;
	}

	public String getLeagueOfTeam(String teamNameForShort) {
		if (MEM.TEAM_LEAGUE.containsKey(teamNameForShort)) {
			return MEM.TEAM_LEAGUE.get(teamNameForShort);
		}
		else {
			return null;
		}
	}
}
