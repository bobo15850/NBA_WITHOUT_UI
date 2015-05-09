package dataservice.players;

import java.util.HashMap;
import java.util.TreeMap;

import common.mydatastructure.GeneralInfoOfPlayer;
import common.mydatastructure.MyDate;
import common.mydatastructure.PlayerPerformOfOneMatch;

public interface PlayerInfoDataService {
	// 查找出所有的球员所有比赛数据
	public HashMap<String, TreeMap<MyDate, PlayerPerformOfOneMatch>> getAllPlayerAllPerform();

	// 根据球员姓名查找某一球员具体基本自然信息
	public GeneralInfoOfPlayer getGeneralInfoOfOnePlayer(String nameOfPlayer);

	// 通过球员姓名得到球队基本信息
	public String getLeague(String playerName);
}
