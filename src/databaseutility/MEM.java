package databaseutility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import po.GeneralInfoOfPlayerPo;
import po.GeneralInfoOfTeamPo;
import po.PlayerPerformanceOfOneMatchPo;
import po.TeamPerformanceOfOneMatchPo;

import common.mydatastructure.Height;
import common.mydatastructure.MyDate;
import common.statics.Method;
import common.statics.NUMBER;
import common.statics.PathOfFile;

public class MEM {
	public static Map<String, Map<MyDate, PlayerPerformanceOfOneMatchPo>> PLAYERS_PERFORM = new HashMap<String, Map<MyDate, PlayerPerformanceOfOneMatchPo>>();
	// 球员数据存储
	public static Map<String, GeneralInfoOfPlayerPo> PLAYER_GENERALINFO = new HashMap<String, GeneralInfoOfPlayerPo>();
	// 求援基本信息存储
	public static Map<String, Map<MyDate, TeamPerformanceOfOneMatchPo>> TEAM_PERFORM = new HashMap<String, Map<MyDate, TeamPerformanceOfOneMatchPo>>();
	// 球队数据存储
	public static Map<String, GeneralInfoOfTeamPo> TEAM_GENERALINFO = new HashMap<String, GeneralInfoOfTeamPo>();
	// 球队基本信息存储
	static {
		MEM.handleFileOfMatches();
		MEM.handleFileOfPlayers();
		MEM.handleFileOfTeams();
	}

	private static void handleFileOfMatches() {
		File matchFile = new File(PathOfFile.MATCH_INFO);
		String matchName[] = matchFile.list();
		for (int i = 0; i < matchName.length; i++) {
			OneMatchToMEM match = new OneMatchToMEM(matchName[i]);
			match.writeDetailInfoOfPlayerAndTeamToMEN();
		}
	}// 读取每场比赛信息，并分析后写入数据库

	private static void handleFileOfPlayers() {
		File playerFile = new File(PathOfFile.PLAYER_INFO);
		String playerName[] = playerFile.list();
		for (int i = 0; i < playerName.length; i++) {
			try {
				writePlayerInfoToMEM(playerName[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void handleFileOfTeams() {
		try {
			BufferedReader teamReader = new BufferedReader(new FileReader(PathOfFile.TEAM_INFO + "teams"));
			teamReader.readLine();
			String formatdetail;
			for (int i = 0; i < NUMBER.NUMBER_OF_TEAM; i++) {
				formatdetail = teamReader.readLine();
				writeTeamInfoToMEM(formatdetail);
			}
			teamReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writePlayerInfoToMEM(String playerName) throws IOException {
		String part[];
		String[] element = new String[9];
		BufferedReader playerReader = new BufferedReader(new FileReader(PathOfFile.PLAYER_INFO + playerName));
		playerReader.readLine();
		String temp;
		for (int i = 0; i < 9; i++) {
			temp = playerReader.readLine();
			part = temp.split("│");
			element[i] = part[1].substring(0, part[1].length() - 2).trim();
			if (element[i].contains("'")) {
				element[i] = element[i].replace("'", " ");
			}
			if (element[i].contains("(")) {
				element[i] = element[i].replace("(", " ");
				element[i] = element[i].replace(")", " ");
			}
			playerReader.readLine();
		}
		GeneralInfoOfPlayerPo playerInfoPo = new GeneralInfoOfPlayerPo();
		playerInfoPo.setName(element[0]);
		playerInfoPo.setNumber(element[1]);
		playerInfoPo.setPosition(element[2]);
		playerInfoPo.setHeight(new Height(element[3]));
		playerInfoPo.setWeight(toInt(element[4]));
		int month = Method.toMonthInt(element[5].substring(0, 3));
		String[] dates = element[5].split(",");
		int year = toInt(dates[1].trim());
		int day = toInt(dates[0].substring(4).trim());
		playerInfoPo.setBirthday(new MyDate(year, month, day));
		playerInfoPo.setAge(toInt(element[6]));
		playerInfoPo.setTrainingYear(toInt(element[7]));
		playerInfoPo.setShool(element[8]);
		playerReader.close();
		MEM.PLAYER_GENERALINFO.put(playerName, playerInfoPo);
	}

	private static void writeTeamInfoToMEM(String formatdetail) {
		GeneralInfoOfTeamPo TeamInfoPo = new GeneralInfoOfTeamPo();
		String[] part = formatdetail.split("│");
		String teamNameForShort = part[1].trim();
		TeamInfoPo.setTeamName(part[0].trim().substring(1));
		TeamInfoPo.setTeamNameForShort(teamNameForShort);
		TeamInfoPo.setLocation(part[2].trim());
		TeamInfoPo.setConference(part[3].trim());
		TeamInfoPo.setDivision(part[4].trim());
		TeamInfoPo.setHomeField(part[5].trim());
		TeamInfoPo.setEstablishYear(toInt(part[6].trim().substring(0, 4)));
		MEM.TEAM_GENERALINFO.put(teamNameForShort, TeamInfoPo);
	}

	private static int toInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
