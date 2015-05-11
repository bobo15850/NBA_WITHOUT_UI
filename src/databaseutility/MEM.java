package databaseutility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

import common.mydatastructure.GeneralInfoOfPlayer;
import common.mydatastructure.MyDate;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.mydatastructure.TeamPerformOfOneMatch;
import common.statics.Age;
import common.statics.League;
import common.statics.NUMBER;
import common.statics.PathOfFile;
import common.statics.Position;

public class MEM {

	public static HashMap<String, GeneralInfoOfPlayer> PLAYER_GENERALINFO = new HashMap<String, GeneralInfoOfPlayer>();
	// 球员基本信息存储
	public static HashMap<String, String> TEAM_LEAGUE = new HashMap<String, String>();
	// 球队联盟信息存储
	/*
	 * 以上信息只要初始化，以下信息需要更新
	 */
	public static HashMap<String, TreeMap<MyDate, PlayerPerformOfOneMatch>> PLAYERS_PERFORM = new HashMap<String, TreeMap<MyDate, PlayerPerformOfOneMatch>>();
	// 球员数据存储
	public static HashMap<String, TreeMap<MyDate, TeamPerformOfOneMatch>> TEAM_PERFORM = new HashMap<String, TreeMap<MyDate, TeamPerformOfOneMatch>>();
	// 球队数据存储
	public static MyDate LATEST_DATE = new MyDate(0, 0, 0);
	// 指示当前的日期
	static {
		MEM.handleFileOfPlayers();
		MEM.handleFileOfTeams();
		MEM.handleFileOfMatches();
	}

	private static void handleFileOfMatches() {
		File matchFile = new File(PathOfFile.MATCH_INFO);
		String matchName[] = matchFile.list();
		for (int i = 0; i < matchName.length; i++) {
			OneMatch_init match = new OneMatch_init(matchName[i]);
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
			BufferedReader teamReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PathOfFile.TEAM_INFO + "teams")), "UTF-8"));
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
		BufferedReader playerReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(PathOfFile.PLAYER_INFO + playerName)), "UTF-8"));
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
		GeneralInfoOfPlayer playerInfoPo = new GeneralInfoOfPlayer();
		if (!element[0].equals("null")) {
			playerInfoPo.setName(element[0]);
		}
		else {
			playerInfoPo.setName(playerName);
		}
		if (element[2].equals("null")) {
			element[2] = Position.UNKUOWN_POSITION;
		}
		playerInfoPo.setPosition(element[2]);
		playerInfoPo.setAge(toAge(element[6]));
		playerReader.close();
		MEM.PLAYER_GENERALINFO.put(playerName, playerInfoPo);
	}

	private static void writeTeamInfoToMEM(String formatdetail) {
		String[] part = formatdetail.split("│");
		String league = part[3].trim();
		if (part[1].equals("NOP")) {
			part[1] = "NOH";
		}
		if (league.equals("E")) {
			MEM.TEAM_LEAGUE.put(part[1].trim(), League.East);
		}
		else if (league.equals("W")) {
			MEM.TEAM_LEAGUE.put(part[1].trim(), League.West);
		}
		else {
			MEM.TEAM_LEAGUE.put(part[1].trim(), League.UNKNOWN_LEAGUE);
		}
	}

	private static int toAge(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return Age.UNKNOWN_AGE;
		}
	}
}
