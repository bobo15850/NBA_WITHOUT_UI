package data.players;

import java.util.HashMap;
import java.util.TreeMap;

import common.mydatastructure.GeneralInfoOfPlayer;
import common.mydatastructure.MyDate;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.statics.Age;
import common.statics.League;
import common.statics.Position;

import databaseutility.MEM;
import dataservice.players.PlayerInfoDataService;

public class PlayerInfoData implements PlayerInfoDataService {
	private static PlayerInfoData playerInfoData = null;

	private PlayerInfoData() {
	}

	public static PlayerInfoData getInstance() {
		if (playerInfoData == null) {
			playerInfoData = new PlayerInfoData();
		}
		return playerInfoData;
	}

	public GeneralInfoOfPlayer getGeneralInfoOfOnePlayer(String nameOfPlayer) {
		GeneralInfoOfPlayer resultPo;
		if (MEM.PLAYER_GENERALINFO.containsKey(nameOfPlayer)) {
			resultPo = MEM.PLAYER_GENERALINFO.get(nameOfPlayer);
		}
		else {
			resultPo = new GeneralInfoOfPlayer();
			resultPo.setName(nameOfPlayer);
			resultPo.setAge(Age.UNKNOWN_AGE);
			resultPo.setPosition(Position.UNKUOWN_POSITION);
		}
		return resultPo;
	}

	public String getLeague(String playerName) {
		PlayerPerformOfOneMatch lastMatch = MEM.PLAYERS_PERFORM.get(playerName).lastEntry().getValue();
		String team = lastMatch.getTeamName();
		String league = League.UNKNOWN_LEAGUE;
		if (MEM.TEAM_LEAGUE.containsKey(team)) {
			league = MEM.TEAM_LEAGUE.get(team);
		}
		return league;
	}

	public HashMap<String, TreeMap<MyDate, PlayerPerformOfOneMatch>> getAllPlayerAllPerform() {
		return MEM.PLAYERS_PERFORM;
	}
}
