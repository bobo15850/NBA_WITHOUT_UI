package data.teams;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import po.GeneralInfoOfTeamPo;
import po.TeamPerformanceOfOneMatchPo;
import common.mydatastructure.MyDate;
import common.mydatastructure.Season;
import common.statics.ResultMessage;
import databaseutility.database_memory.MEM;
import dataservice.teams.TeamInfoDataService;

public class TeamInfoData_Memory implements TeamInfoDataService {
	private static TeamInfoData_Memory teamInfoData = null;

	private TeamInfoData_Memory() {
	}

	public static TeamInfoData_Memory getInstance() {
		if (teamInfoData == null) {
			teamInfoData = new TeamInfoData_Memory();
		}
		return teamInfoData;
	}

	public ArrayList<TeamPerformanceOfOneMatchPo[]> getOneTeamPerformOfOneSeason(String teamName, Season season) {
		ArrayList<TeamPerformanceOfOneMatchPo[]> resultList = new ArrayList<TeamPerformanceOfOneMatchPo[]>(128);
		Map<MyDate, TeamPerformanceOfOneMatchPo> oneTeamPerform = MEM.TEAM_PERFORM.get(teamName);
		Set<MyDate> dateSet = oneTeamPerform.keySet();
		for (MyDate date : dateSet) {
			if (season.hasDate(date)) {
				TeamPerformanceOfOneMatchPo selfTeamPo = oneTeamPerform.get(date);
				String opponentTeam = selfTeamPo.getOpponentTeamNameForShort();
				TeamPerformanceOfOneMatchPo opponentTeamPo = MEM.TEAM_PERFORM.get(opponentTeam).get(date);
				resultList.add(new TeamPerformanceOfOneMatchPo[] { selfTeamPo, opponentTeamPo });
			}
		}
		return resultList;
	}

	public GeneralInfoOfTeamPo getBaseInformationOfOneTeam(String teamNameForShort) {
		GeneralInfoOfTeamPo resultPo;
		if (MEM.TEAM_GENERALINFO.containsKey(teamNameForShort)) {
			resultPo = MEM.TEAM_GENERALINFO.get(teamNameForShort);
		} else {
			resultPo = ResultMessage.NOTEXIST_GENERAL_TEAM_PO;
		}
		return resultPo;
	}

	public ArrayList<String> getNamesForShortOfAllTeam() {
		Set<String> teamNameSet = MEM.TEAM_PERFORM.keySet();
		ArrayList<String> resultList = new ArrayList<String>(32);
		for (String name : teamNameSet) {
			resultList.add(name);
		}
		return resultList;
	}
}
