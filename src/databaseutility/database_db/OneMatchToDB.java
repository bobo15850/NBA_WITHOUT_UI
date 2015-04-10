package databaseutility.database_db;

import po.PlayerPerformanceOfOneMatchPo;
import common.statics.ResultMessage;
import databaseutility.OneMatch;

public class OneMatchToDB extends OneMatch {
	private OperationOfTeamsDB dbOfTeam = OperationOfTeamsDB.getTeamDB();
	private OperationOfPlayersDB dbOfPlayer = OperationOfPlayersDB.getPlayerDB();

	public OneMatchToDB(String nameOfFile) {
		super(nameOfFile);
	}

	public void writeDetailInfoOfPlayerAndTeamToDB() {
		System.out.println(nameOfFile);
		if (isDataCorrect) {
			PlayerPerformanceOfOneMatchPo playerPo;
			for (int i = 0; i < listOfFirstTeamPlayerPerformance.size(); i++) {
				playerPo = listOfFirstTeamPlayerPerformance.get(i);
				writeDetailInfoOfPlayerPerform(playerPo);
			}
			for (int i = 0; i < listOfSecondTeamPlayerPerformance.size(); i++) {
				playerPo = listOfSecondTeamPlayerPerformance.get(i);
				writeDetailInfoOfPlayerPerform(playerPo);
			}
			writeDetailInfoOfTeamPerform();
		}
	}// 初始化比赛信息数据库

	private void writeDetailInfoOfTeamPerform() {
		if (dbOfTeam.isTableExist(firstTeam).equals(ResultMessage.EXIST)) {
			dbOfTeam.add(firstTeam, firstTeamPerformance.toDBString());
		} else if (dbOfTeam.isTableExist(firstTeam).equals(ResultMessage.NOT_EXIST)) {
			dbOfTeam.createTable(firstTeam);
			dbOfTeam.add(firstTeam, firstTeamPerformance.toDBString());
		}
		if (dbOfTeam.isTableExist(secondTeam).equals(ResultMessage.EXIST)) {
			dbOfTeam.add(secondTeam, secondTeamPerformance.toDBString());
		} else if (dbOfTeam.isTableExist(secondTeam).equals(ResultMessage.NOT_EXIST)) {
			dbOfTeam.createTable(secondTeam);
			dbOfTeam.add(secondTeam, secondTeamPerformance.toDBString());
		}
	}// 写入一个球队一场比赛的数据到数据库

	private void writeDetailInfoOfPlayerPerform(PlayerPerformanceOfOneMatchPo playerPo) {
		String nameOfPlayer = playerPo.getNameOfPlayer();
		if (dbOfPlayer.isTableExist(nameOfPlayer).equals(ResultMessage.EXIST)) {
			dbOfPlayer.add(nameOfPlayer, playerPo.toDBString());
		} else if (dbOfPlayer.isTableExist(playerPo.getNameOfPlayer()).equals(ResultMessage.NOT_EXIST)) {
			dbOfPlayer.createTable(nameOfPlayer);
			dbOfPlayer.add(nameOfPlayer, playerPo.toDBString());
		}
	}// 写入一个球员一场比赛的数据到数据库
}
