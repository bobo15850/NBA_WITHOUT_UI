package businesslogic;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import test.data.PlayerHighInfo;
import test.data.TeamHighInfo;
import businesslogic.players.CalculationOfPlayerPerform;
import businesslogic.teams.CalculationOfTeamPerform;

import common.mydatastructure.GeneralInfoOfPlayer;
import common.mydatastructure.MyDate;
import common.mydatastructure.PlayerNormalInfo_Expand;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.mydatastructure.TeamNormalInfo_Expand;
import common.mydatastructure.TeamPerformOfOneMatch;
import common.statics.Age;
import common.statics.League;
import common.statics.Position;

import databaseutility.MEM;

public class CACHE {
	public static Map<String, PlayerNormalInfo_Expand> PLAYER_NORMAL = new TreeMap<String, PlayerNormalInfo_Expand>();
	public static Map<String, PlayerHighInfo> PLAYER_HIGH = new TreeMap<String, PlayerHighInfo>();
	//
	public static Map<String, TeamNormalInfo_Expand> TEAM_NORMAL = new TreeMap<String, TeamNormalInfo_Expand>();
	public static Map<String, TeamHighInfo> TEAM_HIGH = new TreeMap<String, TeamHighInfo>();
	//
	public static Map<String, PlayerPerformOfOneMatch> PLAYER_TODAY = new TreeMap<String, PlayerPerformOfOneMatch>();
	//
	static {
		initPlayerCache();
		initPlayerTodayCache();
		initTeamCache();
	}

	public static void initPlayerCache() {
		for (Entry<String, TreeMap<MyDate, PlayerPerformOfOneMatch>> temp : MEM.PLAYERS_PERFORM.entrySet()) {
			PlayerNormalInfo_Expand playerN = new PlayerNormalInfo_Expand();
			PlayerHighInfo playerH = new PlayerHighInfo();
			//
			String playerName = temp.getKey();// 球员姓名
			String position = Position.UNKUOWN_POSITION;// 位置
			int age = Age.UNKNOWN_AGE;// 年龄
			String league = League.UNKNOWN_LEAGUE;// 联盟
			//
			TreeMap<MyDate, PlayerPerformOfOneMatch> oneplayer = temp.getValue();
			PlayerPerformOfOneMatch latestMatchPo = oneplayer.lastEntry().getValue();// 最后一场比赛
			String teamName = latestMatchPo.getTeamName();// 得到所属球队
			if (MEM.TEAM_LEAGUE.containsKey(teamName)) {
				league = MEM.TEAM_LEAGUE.get(teamName);
			}// 得到联盟
			if (MEM.PLAYER_GENERALINFO.containsKey(playerName)) {
				GeneralInfoOfPlayer playerGeneralInfo = MEM.PLAYER_GENERALINFO.get(latestMatchPo.getName());
				age = playerGeneralInfo.getAge();
				position = playerGeneralInfo.getPosition();
			}// 得到年龄
			int numOfGame = oneplayer.size();// 比赛场数
			int start = 0;
			int totalHit = 0;// 总命中数
			int totalShot = 0;// 总出手数
			int threeHit = 0;// 三分命中数
			int threeShot = 0;// 三分出手数
			int freeHit = 0;// 罚球命中数
			int freeShot = 0;// 罚球出手数
			int doubleTwo = 0;// 两双次数
			int tripleTwo = 0;// 三双次数
			//
			int point = 0;// 总得分数
			int totalRebound = 0;// 总篮板
			int offendRebound = 0;// 进攻篮板数
			int defendRebound = 0;// 防守篮板数
			int assist = 0;// 总助攻
			double minute = 0;// 总上场时间
			int steal = 0;// 总抢断数
			int blockShot = 0;// 总 盖帽数
			int fault = 0;// 总失误数
			int foul = 0;// 总犯规数
			//
			double teamMinute = 0;// 球队所有球员上场时间
			int teamTotalRebound = 0;// 球队所有篮板数
			int teamOffendRebound = 0;
			int teamDefendRebound = 0;

			int teamTotalHit = 0; // 球队所有命中数
			int teamTotalshot = 0;// 球队所有出手数
			int teamFreeShot = 0;// 球队所有罚球数
			int teamFault = 0;// 球队所有失误数
			//
			int oppOffendRound = 0;// 对手进攻次数
			int oppTotalShot = 0;// 对手总出手数
			int oppThreeShot = 0;// 对手三分出手数
			int oppTotalRebound = 0;// 对手所有篮板数
			int oppOffendRebound = 0;// 对手进攻篮板数
			int oppDefendRebound = 0;// 对手防守篮板数
			//
			for (Entry<MyDate, PlayerPerformOfOneMatch> onematch : oneplayer.entrySet()) {
				PlayerPerformOfOneMatch tempMatch = onematch.getValue();
				MyDate tempDate = onematch.getKey();
				//
				start += tempMatch.getStart();
				totalHit += tempMatch.getTotalHit();
				totalShot += tempMatch.getTotalShoot();
				threeHit += tempMatch.getThreeHit();
				threeShot += tempMatch.getThreeShot();
				freeHit += tempMatch.getFreeHit();
				freeShot += tempMatch.getFreeShot();
				totalRebound += tempMatch.getRebound();
				assist += tempMatch.getAssist();
				minute += tempMatch.getMinute();
				steal += tempMatch.getSteal();
				blockShot += tempMatch.getBlockShot();
				fault += tempMatch.getFault();
				foul += tempMatch.getFoul();
				point += tempMatch.getPoint();
				offendRebound += tempMatch.getOffendRebound();
				defendRebound += tempMatch.getDefendRebound();
				//
				String tempTeam = tempMatch.getTeamName();// 球队名称
				TeamPerformOfOneMatch selfTeam = MEM.TEAM_PERFORM.get(tempTeam).get(tempDate);// 所属球队战绩
				TeamPerformOfOneMatch oppTeam = MEM.TEAM_PERFORM.get(selfTeam.getOpponentTeamName()).get(tempDate);// 对手球队战绩
				//
				teamMinute += selfTeam.getMinute();
				teamTotalRebound += selfTeam.getRebound();
				teamOffendRebound += selfTeam.getOffendRebound();
				teamDefendRebound += selfTeam.getDefendRebound();
				teamTotalshot += selfTeam.getTotalShot();
				teamFreeShot += selfTeam.getFreeShot();
				teamFault += selfTeam.getFault();
				teamTotalHit += selfTeam.getTotalHit();
				//
				oppOffendRound += CalculationOfTeamPerform.calOffendRound(oppTeam.getTotalShot(), oppTeam.getTotalHit(), oppTeam.getFreeShot(), oppTeam.getOffendRebound(),
						selfTeam.getDefendRebound(), oppTeam.getFault());// 对手进攻回合数（防守回合数）
				oppTotalRebound += oppTeam.getRebound();
				oppOffendRebound += oppTeam.getOffendRebound();
				oppDefendRebound += oppTeam.getDefendRebound();
				oppTotalShot += oppTeam.getTotalShot();
				oppThreeShot += oppTeam.getThreeShot();
				int doubleOfOneMatch = 0;// 一场比赛中上双的个数
				if (tempMatch.getPoint() >= 9.9) {
					doubleOfOneMatch++;
				}
				if (tempMatch.getRebound() >= 9.9) {
					doubleOfOneMatch++;
				}
				if (tempMatch.getAssist() >= 9.9) {
					doubleOfOneMatch++;
				}
				if (tempMatch.getBlockShot() >= 9.9) {
					doubleOfOneMatch++;
				}
				if (tempMatch.getSteal() >= 9.9) {
					doubleOfOneMatch++;
				}
				if (doubleOfOneMatch >= 2) {
					doubleTwo++;
				}
				if (doubleOfOneMatch >= 3) {
					tripleTwo++;
				}
			}
			playerN.setName(playerName);
			playerN.setAge(age);
			playerN.setTeamName(teamName);
			playerN.setStart(start);
			playerN.setNumOfGame(numOfGame);
			playerN.setPoint(point);
			playerN.setRebound(totalRebound);
			playerN.setOffend(offendRebound);
			playerN.setDefend(defendRebound);
			playerN.setAssist(assist);
			playerN.setSteal(steal);
			playerN.setBlockShot(blockShot);
			playerN.setFoul(foul);
			playerN.setFault(fault);
			playerN.setMinute(CalculationOfPlayerPerform.cutTail(minute));
			//
			playerN.setTotalHit(totalHit);
			playerN.setTotalShot(totalShot);
			playerN.setThreeHit(threeHit);
			playerN.setThreeShot(threeShot);
			playerN.setFreeHit(freeHit);
			playerN.setFreeShot(freeShot);
			playerN.setDoubleTwo(doubleTwo);
			playerN.setTripleTwo(tripleTwo);
			//
			playerN.setShot(CalculationOfPlayerPerform.calHitRate(totalHit, totalShot));
			playerN.setThree(CalculationOfPlayerPerform.calHitRate(threeHit, threeShot));
			playerN.setPenalty(CalculationOfPlayerPerform.calHitRate(freeHit, freeShot));
			playerN.setEfficiency(CalculationOfPlayerPerform.calEfficiency(point, totalRebound, assist, steal, blockShot, totalShot, totalHit, freeShot, freeHit, fault));
			// 以下为所在球队比赛数据
			playerN.setTeamDefendRebound(teamDefendRebound);
			playerN.setTeamFault(teamFault);
			playerN.setTeamFreeShot(teamFreeShot);
			playerN.setTeamMinute(teamMinute);
			playerN.setTeamOffendRebound(teamOffendRebound);
			playerN.setTeamTotalHit(teamTotalHit);
			playerN.setTeamTotalRebound(teamTotalRebound);
			playerN.setTeamTotalshot(teamTotalshot);
			// 以下为对手数据
			playerN.setOppTotalShot(oppTotalShot);
			playerN.setOppThreeShot(oppThreeShot);
			playerN.setOppRebound(oppTotalRebound);
			playerN.setOppOffendRebound(oppOffendRebound);
			playerN.setOppDefendRebound(oppDefendRebound);
			// 以下为球员高阶数据
			playerH.setName(playerName);
			playerH.setTeamName(teamName);
			playerH.setLeague(league);
			playerH.setPosition(position);
			playerH.setGmSc(CalculationOfPlayerPerform.calGmSc(point, totalHit, totalShot, freeShot, freeHit, offendRebound, defendRebound, steal, assist, blockShot, foul, fault, numOfGame));
			playerH.setShotEfficient(CalculationOfPlayerPerform.calShotEfficiency(totalHit, threeHit, totalShot));
			playerH.setRealShot(CalculationOfPlayerPerform.calRealShot(point, totalShot, freeShot));
			playerH.setOffendReboundEfficient(CalculationOfPlayerPerform.calReboundEfficient(offendRebound, teamMinute, minute, teamOffendRebound, oppDefendRebound));
			playerH.setDefendReboundEfficient(CalculationOfPlayerPerform.calReboundEfficient(defendRebound, teamMinute, minute, teamDefendRebound, oppOffendRebound));
			playerH.setReboundEfficient(CalculationOfPlayerPerform.calReboundEfficient(totalRebound, teamMinute, minute, teamTotalRebound, oppTotalRebound));
			playerH.setAssistEfficient(CalculationOfPlayerPerform.calAssistEfficient(assist, minute, teamMinute, teamTotalHit, totalHit));
			playerH.setStealEfficient(CalculationOfPlayerPerform.calStealEfficient(steal, teamMinute, minute, oppOffendRound));
			playerH.setBlockShotEfficient(CalculationOfPlayerPerform.calBlockShotEfficient(blockShot, teamMinute, minute, oppTotalShot - oppThreeShot));
			playerH.setFaultEfficient(CalculationOfPlayerPerform.calFaultEfficient(fault, totalShot - threeShot, freeShot));
			playerH.setFrequency(CalculationOfPlayerPerform.calFrequency(totalShot, freeShot, fault, teamMinute, minute, teamTotalshot, teamFreeShot, teamFault));
			CACHE.PLAYER_NORMAL.put(playerName, playerN);
			CACHE.PLAYER_HIGH.put(playerName, playerH);
		}
	}

	public static void initPlayerTodayCache() {
		for (Entry<String, TreeMap<MyDate, PlayerPerformOfOneMatch>> onePlayerPerform : MEM.PLAYERS_PERFORM.entrySet()) {
			String playerName = onePlayerPerform.getKey();
			TreeMap<MyDate, PlayerPerformOfOneMatch> playerPerform = onePlayerPerform.getValue();
			if (playerPerform.lastKey().equals(MEM.LATEST_DATE)) {
				CACHE.PLAYER_TODAY.put(playerName, playerPerform.lastEntry().getValue());
			}
		}
	}

	public static void initTeamCache() {
		for (Entry<String, TreeMap<MyDate, TeamPerformOfOneMatch>> temp : MEM.TEAM_PERFORM.entrySet()) {
			TeamNormalInfo_Expand teamN = new TeamNormalInfo_Expand();
			TeamHighInfo teamH = new TeamHighInfo();
			TreeMap<MyDate, TeamPerformOfOneMatch> oneTeam = temp.getValue();
			String teamName = temp.getKey();
			int numOfGame = oneTeam.size();
			int numOfWin = 0;
			//
			double minute = 0;
			int totalHit = 0;// 总命中数
			int totalShot = 0;// 总出手数
			int threeHit = 0;// 三分命中数
			int threeShot = 0;// 三分出手数
			int freeHit = 0;// 罚球命中数
			int freeShot = 0;// 罚球出手数
			//
			int point = 0;// 总得分数
			int totalRebound = 0;// 总篮板
			int offendRebound = 0;// 进攻篮板数
			int defendRebound = 0;// 防守篮板数
			int assist = 0;// 总助攻
			int steal = 0;// 总抢断数
			int blockShot = 0;// 总 盖帽数
			int fault = 0;// 总失误数
			int foul = 0;// 总犯规数
			//
			int oppOffendRebound = 0;
			int oppDefendRebound = 0;
			int oppPoint = 0;
			int oppTotalShot = 0;
			int oppTotalHit = 0;
			int oppFreeShot = 0;
			int oppFault = 0;
			//
			for (Entry<MyDate, TeamPerformOfOneMatch> oneMatch : oneTeam.entrySet()) {
				TeamPerformOfOneMatch tempMatch = oneMatch.getValue();
				MyDate tempDate = oneMatch.getKey();
				numOfWin += tempMatch.getWin();
				minute += tempMatch.getMinute();
				totalHit += tempMatch.getTotalHit();// 总命中数
				totalShot += tempMatch.getTotalShot();// 总出手数
				threeHit += tempMatch.getThreeHit();// 三分命中数
				threeShot += tempMatch.getThreeShot();// 三分出手数
				freeHit += tempMatch.getFreeHit();// 罚球命中数
				freeShot += tempMatch.getFreeShot();// 罚球出手数
				//
				point += tempMatch.getPoint();// 总得分数
				totalRebound += tempMatch.getRebound();// 总篮板
				offendRebound += tempMatch.getOffendRebound();// 进攻篮板数
				defendRebound += tempMatch.getDefendRebound();// 防守篮板数
				assist += tempMatch.getAssist();// 总助攻
				steal += tempMatch.getSteal();// 总抢断数
				blockShot += tempMatch.getBlock();// 总 盖帽数
				fault += tempMatch.getFault();// 总失误数
				foul += tempMatch.getFoul();// 总犯规数
				//
				TeamPerformOfOneMatch opponentTeam = MEM.TEAM_PERFORM.get(tempMatch.getOpponentTeamName()).get(tempDate);
				oppOffendRebound += opponentTeam.getOffendRebound();
				oppDefendRebound += opponentTeam.getDefendRebound();
				oppPoint += opponentTeam.getPoint();
				oppTotalShot += opponentTeam.getTotalShot();
				oppTotalHit += opponentTeam.getTotalHit();
				oppFreeShot += opponentTeam.getFreeShot();
				oppFault += opponentTeam.getFault();
			}
			teamN.setTeamName(teamName);
			teamN.setNumOfGame(numOfGame);
			teamN.setPoint(point);
			teamN.setRebound(totalRebound);
			teamN.setOffendRebound(offendRebound);
			teamN.setDefendRebound(defendRebound);
			teamN.setAssist(assist);
			teamN.setSteal(steal);
			teamN.setBlockShot(blockShot);
			teamN.setFault(fault);
			teamN.setFoul(foul);
			//
			teamN.setTotalHit(totalHit);
			teamN.setTotalShot(totalShot);
			teamN.setThreeHit(threeHit);
			teamN.setThreeShot(threeShot);
			teamN.setFreeHit(freeHit);
			teamN.setFreeShot(freeShot);
			teamN.setNumOfWin(numOfWin);
			teamN.setMinute(minute);
			//
			teamN.setShot(CalculationOfTeamPerform.calHitRate(totalHit, totalShot));
			teamN.setThree(CalculationOfTeamPerform.calHitRate(threeHit, threeShot));
			teamN.setPenalty(CalculationOfTeamPerform.calHitRate(freeHit, freeShot));
			// 以下为对手数据
			teamN.setOppDefendRebound(oppDefendRebound);
			teamN.setOppFault(oppFault);
			teamN.setOppOffendRebound(oppOffendRebound);
			teamN.setOppPoint(oppPoint);
			teamN.setOppTotalHit(oppTotalHit);
			teamN.setOppTotalShot(oppTotalShot);
			teamN.setOppFreeShot(oppFreeShot);
			// 以下为球队高阶比赛数据
			teamH.setTeamName(teamName);
			double offendRound = CalculationOfTeamPerform.calOffendRound(totalShot, totalHit, freeShot, offendRebound, oppDefendRebound, fault);// 进攻回合数
			double oppOffendRound = CalculationOfTeamPerform.calOffendRound(oppTotalShot, oppTotalHit, oppFreeShot, oppOffendRebound, defendRebound, oppFault);// 防守回合数（对手进攻回合数）
			teamH.setWinRate(CalculationOfTeamPerform.calWinRate(numOfWin, numOfGame));// 胜率
			teamH.setOffendRound(offendRound);// 进攻回合数
			teamH.setOffendEfficient(CalculationOfTeamPerform.calOffendEfficient(point, offendRound));// 进攻效率
			teamH.setDefendEfficient(CalculationOfTeamPerform.calDeffendEfficient(oppPoint, oppOffendRound));// 防守效率
			teamH.setOffendReboundEfficient(CalculationOfTeamPerform.calOffendReboundEfficient(offendRebound, oppDefendRebound));// 进攻篮板效率
			teamH.setDefendReboundEfficient(CalculationOfTeamPerform.calDefendReboundEfficient(defendRebound, oppOffendRebound));// 防守篮板效率
			teamH.setAssistEfficient(CalculationOfTeamPerform.calAssistEfficient(assist, offendRound));// 助攻率
			teamH.setStealEfficient(CalculationOfTeamPerform.calStealEfficient(steal, oppOffendRound));// 抢断率
			CACHE.TEAM_NORMAL.put(teamName, teamN);
			CACHE.TEAM_HIGH.put(teamName, teamH);
		}
	}
}