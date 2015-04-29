package data.teams;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import common.mydatastructure.MyDate;
import common.mydatastructure.TeamPerformOfOneMatch;
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

	public ArrayList<TeamPerformOfOneMatch[]> getOneTeamPerformOfOneSeason(String teamName) {
		ArrayList<TeamPerformOfOneMatch[]> resultList = new ArrayList<TeamPerformOfOneMatch[]>(128);
		Map<MyDate, TeamPerformOfOneMatch> oneTeamPerform = MEM.TEAM_PERFORM.get(teamName);
		Set<MyDate> dateSet = oneTeamPerform.keySet();
		for (MyDate date : dateSet) {
			TeamPerformOfOneMatch selfTeamPo = oneTeamPerform.get(date);
			String opponentTeam = selfTeamPo.getOpponentTeamName();
			TeamPerformOfOneMatch opponentTeamPo = MEM.TEAM_PERFORM.get(opponentTeam).get(date);
			resultList.add(new TeamPerformOfOneMatch[] { selfTeamPo, opponentTeamPo });
		}
		return resultList;
	}

	public ArrayList<String> getNamesForShortOfAllTeam() {
		Set<String> teamNameSet = MEM.TEAM_PERFORM.keySet();
		ArrayList<String> resultList = new ArrayList<String>(32);
		for (String name : teamNameSet) {
			resultList.add(name);
		}
		return resultList;
	}

	public String getLeagueOfTeam(String teamNameForShort) {
		if (MEM.TEAM_LEAGUE.containsKey(teamNameForShort)) {
			return MEM.TEAM_LEAGUE.get(teamNameForShort);
		}
		else {
			return null;
		}
	}

	public TeamPerformOfOneMatch getOneMatchTeamPerform(String teamName, MyDate date) {
		if (MEM.TEAM_PERFORM.containsKey(teamName)) {
			if (MEM.TEAM_PERFORM.get(teamName).containsKey(date)) {
				return MEM.TEAM_PERFORM.get(teamName).get(date);
			}
		}
		return null;
	}
}
