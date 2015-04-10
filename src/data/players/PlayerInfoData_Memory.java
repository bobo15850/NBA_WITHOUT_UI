package data.players;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import po.GeneralInfoOfPlayerPo;
import po.GeneralInfoOfTeamPo;
import po.PlayerPerformanceOfOneMatchPo;
import po.TeamPerformanceOfOneMatchPo;
import common.mydatastructure.MyDate;
import common.mydatastructure.Season;
import common.statics.ResultMessage;
import databaseutility.database_memory.MEM;
import dataservice.players.PlayerInfoDataService;

public class PlayerInfoData_Memory implements PlayerInfoDataService {
	private static PlayerInfoData_Memory playerInfoData = null;

	private PlayerInfoData_Memory() {
	}

	public static PlayerInfoData_Memory getInstance() {
		if (playerInfoData == null) {
			playerInfoData = new PlayerInfoData_Memory();
		}
		return playerInfoData;
	}

	public ArrayList<PlayerPerformanceOfOneMatchPo> getOnePlayerPerformOfOneSeasonPo(String nameOfPlayer, Season season) {
		ArrayList<PlayerPerformanceOfOneMatchPo> resultList = new ArrayList<PlayerPerformanceOfOneMatchPo>(128);
		Map<MyDate, PlayerPerformanceOfOneMatchPo> onePlayerPerform = MEM.PLAYERS_PERFORM.get(nameOfPlayer);
		Set<MyDate> dateSet = onePlayerPerform.keySet();
		for (MyDate date : dateSet) {
			if (season.hasDate(date)) {
				resultList.add(onePlayerPerform.get(date));
			}
		}
		return resultList;
	}

	public ArrayList<String> getNamesOfAllPlayer() {
		Set<String> playerNameSet = MEM.PLAYERS_PERFORM.keySet();
		ArrayList<String> resultList = new ArrayList<String>(512);
		for (String name : playerNameSet) {
			resultList.add(name);
		}
		return resultList;
	}

	public GeneralInfoOfPlayerPo getGeneralInfoOfOnePlayer(String nameOfPlayer) {
		GeneralInfoOfPlayerPo resultPo;
		if (MEM.PLAYER_GENERALINFO.containsKey(nameOfPlayer)) {
			resultPo = MEM.PLAYER_GENERALINFO.get(nameOfPlayer);
		} else {
			resultPo = ResultMessage.NOTEXIST_GENERAL_PLAYER_PO;
		}
		return resultPo;
	}

	public GeneralInfoOfTeamPo getGeneralInfoOfOneTeam(String playerName, Season season) {
		return null;
	}

	public ArrayList<TeamPerformanceOfOneMatchPo[]> getOneTeamPerformOfOneSeason(String playerName, Season season) {
		ArrayList<TeamPerformanceOfOneMatchPo[]> resultList = new ArrayList<TeamPerformanceOfOneMatchPo[]>();
		Map<MyDate, PlayerPerformanceOfOneMatchPo> onePlayerOfOneSeason = MEM.PLAYERS_PERFORM.get(playerName);
		Set<MyDate> dateSet = onePlayerOfOneSeason.keySet();
		PlayerPerformanceOfOneMatchPo temp;
		TeamPerformanceOfOneMatchPo selfTeamPo;
		TeamPerformanceOfOneMatchPo opponentTeamPo;
		for (MyDate date : dateSet) {
			temp = onePlayerOfOneSeason.get(date);
			String teamNameForShort = temp.getTeamNameForShort();
			selfTeamPo = MEM.TEAM_PERFORM.get(teamNameForShort).get(date);
			String opponentTeamNameForShort = selfTeamPo.getOpponentTeamNameForShort();
			opponentTeamPo = MEM.TEAM_PERFORM.get(opponentTeamNameForShort).get(date);
			resultList.add(new TeamPerformanceOfOneMatchPo[] { selfTeamPo, opponentTeamPo });
		}
		return resultList;
	}
}
