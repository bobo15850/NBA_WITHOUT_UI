package databaseutility;

import test.data.PlayerHighInfo;
import test.data.TeamHighInfo;
import businesslogic.CACHE;
import businesslogic.players.CalculationOfPlayerPerform;
import businesslogic.teams.CalculationOfTeamPerform;
import common.mydatastructure.PlayerNormalInfo_Expand;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.mydatastructure.TeamNormalInfo_Expand;
import common.mydatastructure.TeamPerformOfOneMatch;
import common.statics.Age;
import common.statics.Position;

public class OneMatch_add extends OneMatch_init {
	public OneMatch_add(String matchName) {
		super(matchName);
	}

	public void writePlayerPerformToday() {
		if (this.date.compareTo(MEM.LATEST_DATE) > 0) {
			MEM.LATEST_DATE = this.date;
			CACHE.PLAYER_TODAY.clear();
		}
		for (int i = 0; i < super.listOfFirstTeamPlayerPerformance.size(); i++) {
			PlayerPerformOfOneMatch player = super.listOfFirstTeamPlayerPerformance.get(i);
			CACHE.PLAYER_TODAY.put(player.getName(), player);
		}
		for (int i = 0; i < super.listOfSecondTeamPlayerPerformance.size(); i++) {
			PlayerPerformOfOneMatch player = super.listOfSecondTeamPlayerPerformance.get(i);
			CACHE.PLAYER_TODAY.put(player.getName(), player);
		}
	}// 更新CACHE中的今日球员数据

	public void writeTeamInfoToCACHE() {
		this.writeOneTeamInfoToCACHE(firstTeamPerformance, secondTeamPerformance);
		this.writeOneTeamInfoToCACHE(secondTeamPerformance, firstTeamPerformance);
	}// 更新cache中的球队普通信息

	private void writeOneTeamInfoToCACHE(TeamPerformOfOneMatch teamP, TeamPerformOfOneMatch oppTeamP) {
		String teamName = teamP.getTeamName();
		if (!CACHE.TEAM_NORMAL.containsKey(teamName)) {
			TeamNormalInfo_Expand teamInfo = new TeamNormalInfo_Expand();
			teamInfo.setTeamName(teamName);
			CACHE.TEAM_NORMAL.put(teamName, teamInfo);
		}
		TeamNormalInfo_Expand teamN = CACHE.TEAM_NORMAL.get(teamName);
		//
		double assist = (teamN.getAssist() + teamP.getAssist());
		double blockShot = (teamN.getBlockShot() + teamP.getBlock());
		double defendRebound = (teamN.getDefendRebound() + teamP.getDefendRebound());
		double fault = (teamN.getFault() + teamP.getFault());
		double foul = (teamN.getFoul() + teamP.getFoul());
		int numOfGame = (teamN.getNumOfGame() + 1);
		double offendRebound = (teamN.getOffendRebound() + teamP.getOffendRebound());
		double point = (teamN.getPoint() + teamP.getPoint());
		double rebound = (teamN.getRebound() + teamP.getRebound());
		double steal = (teamN.getSteal() + teamP.getSteal());
		//
		double freeHit = (teamN.getFreeHit() + teamP.getFreeHit());
		double freeShot = (teamN.getFreeShot() + teamP.getFreeShot());
		double threeHit = (teamN.getThreeHit() + teamP.getThreeHit());
		double threeShot = (teamN.getThreeShot() + teamP.getThreeShot());
		double totalHit = (teamN.getTotalHit() + teamP.getTotalHit());
		double totalShot = (teamN.getTotalShot() + teamP.getTotalShot());
		int numOfWin = (teamN.getNumOfWin() + teamP.getWin());
		double minute = (teamN.getMinute() + teamP.getMinute());
		//
		double penalty = (CalculationOfPlayerPerform.calHitRate(freeHit, freeShot));
		double shot = (CalculationOfPlayerPerform.calHitRate(totalHit, totalShot));
		double three = (CalculationOfPlayerPerform.calHitRate(threeHit, threeShot));
		//
		double oppDefendRebound = (teamN.getOppDefendRebound() + oppTeamP.getDefendRebound());
		double oppFault = (teamN.getOppFault() + oppTeamP.getFault());
		double oppOffendRebound = (teamN.getOppOffendRebound() + oppTeamP.getOffendRebound());
		double oppPoint = (teamN.getOppPoint() + oppTeamP.getPoint());
		double oppTotalHit = (teamN.getOppTotalHit() + oppTeamP.getTotalHit());
		double oppTotalShot = (teamN.getOppTotalShot() + oppTeamP.getTotalShot());
		double oppFreeShot = (teamN.getOppFreeShot() + oppTeamP.getFreeShot());
		//
		teamN.setTeamName(teamName);
		teamN.setAssist(assist);
		teamN.setBlockShot(blockShot);
		teamN.setDefendRebound(defendRebound);
		teamN.setFault(fault);
		teamN.setFoul(foul);
		teamN.setNumOfGame(numOfGame);
		teamN.setOffendRebound(offendRebound);
		teamN.setPoint(point);
		teamN.setRebound(rebound);
		teamN.setSteal(steal);
		//
		teamN.setFreeHit(freeHit);
		teamN.setFreeShot(freeShot);
		teamN.setThreeHit(threeHit);
		teamN.setThreeShot(threeShot);
		teamN.setTotalHit(totalHit);
		teamN.setTotalShot(totalShot);
		teamN.setNumOfWin(numOfWin);
		teamN.setMinute(minute);
		//
		teamN.setPenalty(penalty);
		teamN.setShot(shot);
		teamN.setThree(three);
		//
		teamN.setOppDefendRebound(oppDefendRebound);
		teamN.setOppFault(oppFault);
		teamN.setOppOffendRebound(oppOffendRebound);
		teamN.setOppPoint(oppPoint);
		teamN.setOppTotalHit(oppTotalHit);
		teamN.setOppTotalShot(oppTotalShot);
		teamN.setOppFreeShot(oppFreeShot);
		//
		if (!CACHE.TEAM_HIGH.containsKey(teamName)) {
			TeamHighInfo teamHighInfo = new TeamHighInfo();
			teamHighInfo.setTeamName(teamName);
			CACHE.TEAM_HIGH.put(teamName, teamHighInfo);
		}
		TeamHighInfo teamH = CACHE.TEAM_HIGH.get(teamName);
		//
		teamH.setTeamName(teamName);
		double offendRound = CalculationOfTeamPerform.calOffendRound(totalShot, totalHit, freeShot, offendRebound, oppDefendRebound, fault);// 进攻回合数
		double oppOffendRound = CalculationOfTeamPerform.calOffendRound(oppTotalShot, oppTotalHit, oppFreeShot, oppOffendRebound, defendRebound, oppFault);// 防守回合数（对手进攻回合数）
		teamH.setWinRate(CalculationOfTeamPerform.calWinRate(numOfWin, numOfGame));// 胜率
		teamH.setOffendRound(offendRound / numOfGame);// 进攻回合数
		teamH.setOffendEfficient(CalculationOfTeamPerform.calOffendEfficient(point, offendRound));// 进攻效率
		teamH.setDefendEfficient(CalculationOfTeamPerform.calDeffendEfficient(oppPoint, oppOffendRound));// 防守效率
		teamH.setOffendReboundEfficient(CalculationOfTeamPerform.calOffendReboundEfficient(offendRebound, oppDefendRebound));// 进攻篮板效率
		teamH.setDefendReboundEfficient(CalculationOfTeamPerform.calDefendReboundEfficient(defendRebound, oppOffendRebound));// 防守篮板效率
		teamH.setAssistEfficient(CalculationOfTeamPerform.calAssistEfficient(assist, offendRound));// 助攻率
		teamH.setStealEfficient(CalculationOfTeamPerform.calStealEfficient(steal, oppOffendRound));// 抢断率
	}

	public void writePlayerInfoToCACHE() {
		for (int i = 0; i < listOfFirstTeamPlayerPerformance.size(); i++) {
			PlayerPerformOfOneMatch playerPerform = listOfFirstTeamPlayerPerformance.get(i);
			if (playerPerform != null) {
				this.writeOnePlayerInfoToCACHE(playerPerform, firstTeamPerformance, secondTeamPerformance);
			}

		}
		for (int i = 0; i < listOfSecondTeamPlayerPerformance.size(); i++) {
			PlayerPerformOfOneMatch playerPerform = listOfSecondTeamPlayerPerformance.get(i);
			if (playerPerform != null) {
				this.writeOnePlayerInfoToCACHE(playerPerform, secondTeamPerformance, firstTeamPerformance);
			}
		}
	}// 更新cache中的球员普通信息

	private void writeOnePlayerInfoToCACHE(PlayerPerformOfOneMatch playerP, TeamPerformOfOneMatch selfTeam, TeamPerformOfOneMatch oppTeam) {
		String playerName = playerP.getName();
		int doubleOfOneMatch = 0;
		int doubleTwo = 0;
		int tripleTwo = 0;
		if (playerP.getPoint() >= 9.9) {
			doubleOfOneMatch++;
		}
		if (playerP.getRebound() >= 9.9) {
			doubleOfOneMatch++;
		}
		if (playerP.getAssist() >= 9.9) {
			doubleOfOneMatch++;
		}
		if (playerP.getBlockShot() >= 9.9) {
			doubleOfOneMatch++;
		}
		if (playerP.getSteal() >= 9.9) {
			doubleOfOneMatch++;
		}
		if (doubleOfOneMatch >= 2) {
			doubleTwo = 1;
		}
		else if (doubleOfOneMatch >= 3) {
			tripleTwo = 1;
		}
		if (!CACHE.PLAYER_NORMAL.containsKey(playerName)) {
			PlayerNormalInfo_Expand playerInfo = new PlayerNormalInfo_Expand();
			if (MEM.PLAYER_GENERALINFO.containsKey(playerName)) {
				playerInfo.setAge(MEM.PLAYER_GENERALINFO.get(playerName).getAge());
			}
			else {
				playerInfo.setAge(Age.UNKNOWN_AGE);
			}
			playerInfo.setName(playerName);
			CACHE.PLAYER_NORMAL.put(playerName, playerInfo);
		}
		PlayerNormalInfo_Expand playerN = CACHE.PLAYER_NORMAL.get(playerName);
		double assist = (playerN.getAssist() + playerP.getAssist());
		double blockShot = (playerN.getBlockShot() + playerP.getBlockShot());
		double defendRebound = (playerN.getDefend() + playerP.getDefendRebound());
		double fault = (playerN.getFault() + playerP.getFault());
		double foul = (playerN.getFoul() + playerP.getFoul());
		double freeHit = (playerN.getFreeHit() + playerP.getFreeHit());
		double freeShot = (playerN.getFreeShot() + playerP.getFreeShot());
		double minute = (playerN.getMinute() + playerP.getMinute());
		int numOfGame = (playerN.getNumOfGame() + 1);
		double offendRebound = (playerN.getOffend() + playerP.getOffendRebound());
		double point = (playerN.getPoint() + playerP.getPoint());
		double totalRebound = (playerN.getRebound() + playerP.getRebound());
		int start = (playerN.getStart() + playerP.getStart());
		double steal = (playerN.getSteal() + playerP.getSteal());
		double threeHit = (playerN.getThreeHit() + playerP.getThreeHit());
		double threeShot = (playerN.getThreeShot() + playerP.getThreeShot());
		double totalHit = (playerN.getTotalHit() + playerP.getTotalHit());
		double totalShot = (playerN.getTotalShot() + playerP.getTotalShoot());
		tripleTwo = (playerN.getTripleTwo() + tripleTwo);
		doubleTwo = (playerN.getDoubleTwo() + doubleTwo);
		//
		double penalty = (CalculationOfTeamPerform.calHitRate(freeHit, freeShot));
		double shot = (CalculationOfTeamPerform.calHitRate(totalHit, totalShot));
		double three = (CalculationOfTeamPerform.calHitRate(threeHit, threeShot));
		double efficiency = (CalculationOfPlayerPerform.calEfficiency(point, totalRebound, assist, steal, blockShot, threeShot, totalHit, freeShot, freeHit, fault, numOfGame));
		// 以下为所在球队比赛数据
		double teamDefendRebound = (playerN.getTeamDefendRebound() + selfTeam.getDefendRebound());
		double teamFault = (playerN.getTeamFault() + selfTeam.getFault());
		double teamFreeShot = (playerN.getTeamFreeShot() + selfTeam.getFreeShot());
		double teamMinute = (playerN.getTeamMinute() + selfTeam.getMinute());
		double teamOffendRebound = (playerN.getTeamOffendRebound() + selfTeam.getOffendRebound());
		double teamTotalHit = (playerN.getTeamTotalHit() + selfTeam.getTotalHit());
		double teamTotalRebound = (playerN.getTeamTotalRebound() + selfTeam.getRebound());
		double teamTotalshot = (playerN.getTotalShot() + selfTeam.getTotalShot());
		// 以下为对手数据
		double oppTotalShot = (playerN.getOppTotalShot() + oppTeam.getTotalShot());
		double oppThreeShot = (playerN.getOppThreeShot() + oppTeam.getThreeShot());
		double oppTotalRebound = (playerN.getOppRebound() + oppTeam.getRebound());
		double oppOffendRebound = (playerN.getOppOffendRebound() + oppTeam.getOffendRebound());
		double oppDefendRebound = (playerN.getOppDefendRebound() + oppTeam.getDefendRebound());
		double oppOffendRound = CalculationOfTeamPerform.calOffendRound(totalShot, totalHit, freeShot, offendRebound, defendRebound, fault);// 对手进攻回合数（防守回合数）
		// /////
		// ////
		playerN.setTeamName(playerP.getTeamName());
		playerN.setAssist(assist);
		playerN.setBlockShot(blockShot);
		playerN.setDefend(defendRebound);
		playerN.setFault(fault);
		playerN.setFoul(foul);
		playerN.setFreeHit(freeHit);
		playerN.setFreeShot(freeShot);
		playerN.setMinute(minute);
		playerN.setNumOfGame(numOfGame);
		playerN.setOffend(offendRebound);
		playerN.setPoint(point);
		playerN.setRebound(totalRebound);
		playerN.setStart(start);
		playerN.setSteal(steal);
		playerN.setThreeHit(threeHit);
		playerN.setThreeShot(threeShot);
		playerN.setTotalHit(totalHit);
		playerN.setTotalShot(totalShot);
		playerN.setTripleTwo(tripleTwo);
		playerN.setDoubleTwo(doubleTwo);
		//
		playerN.setPenalty(penalty);
		playerN.setShot(shot);
		playerN.setThree(three);
		playerN.setEfficiency(efficiency);
		// 以下为所在球队比赛数据
		playerN.setTeamDefendRebound(teamDefendRebound);
		playerN.setTeamFault(teamFault);
		playerN.setTeamFreeShot(teamFreeShot);
		playerN.setTeamMinute(teamMinute);
		playerN.setTeamOffendRebound(teamOffendRebound);
		playerN.setTeamTotalHit(teamTotalHit);
		playerN.setTeamTotalRebound(teamTotalRebound);
		playerN.setTeamTotalshot(totalShot);
		// 以下为对手数据
		playerN.setOppTotalShot(oppTotalShot);
		playerN.setOppThreeShot(oppThreeShot);
		playerN.setOppRebound(oppTotalRebound);
		playerN.setOppOffendRebound(oppOffendRebound);
		playerN.setOppDefendRebound(oppDefendRebound);

		if (!CACHE.PLAYER_HIGH.containsKey(playerName)) {
			PlayerHighInfo playerHigh = new PlayerHighInfo();
			playerHigh.setName(playerName);
			if (MEM.PLAYER_GENERALINFO.containsKey(playerName)) {
				playerHigh.setPosition(MEM.PLAYER_GENERALINFO.get(playerName).getPosition());
			}
			else {
				playerHigh.setPosition(Position.UNKUOWN_POSITION);
			}
			CACHE.PLAYER_HIGH.put(playerHigh.getName(), playerHigh);
		}
		PlayerHighInfo playerH = CACHE.PLAYER_HIGH.get(playerName);
		playerH.setTeamName(playerName);
		playerH.setLeague(MEM.TEAM_LEAGUE.get(playerN.getTeamName()));
		//
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
	}
}