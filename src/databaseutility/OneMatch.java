package databaseutility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import common.mydatastructure.MyDate;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.mydatastructure.TeamPerformOfOneMatch;
import common.statics.NUMBER;
import common.statics.PathOfFile;

class OneMatch {
	protected String nameOfFile;
	protected String firstTeam;// 第一支球队
	protected String secondTeam;// 第二支球队
	protected MyDate date;// 比赛时间
	protected int firstTeamSocre;// 第一支球队得分
	protected int secondTeamScore;// 第二支球队得分
	protected String quarterPoint;
	protected boolean isDataCorrect = true;// 判断数据是否为脏数据
	protected ArrayList<PlayerPerformOfOneMatch> listOfFirstTeamPlayerPerformance;// 第一支球队每个队员各项成绩
	protected ArrayList<PlayerPerformOfOneMatch> listOfSecondTeamPlayerPerformance;// 第二支球每个球员各项成绩
	protected TeamPerformOfOneMatch firstTeamPerformance;// 第一支球队总成绩
	protected TeamPerformOfOneMatch secondTeamPerformance;// 第二支球队总成绩

	public OneMatch(String nameOfFile) {
		this.nameOfFile = nameOfFile;
		this.listOfFirstTeamPlayerPerformance = new ArrayList<PlayerPerformOfOneMatch>(15);
		this.listOfSecondTeamPlayerPerformance = new ArrayList<PlayerPerformOfOneMatch>(15);
		try {
			BufferedReader matchReader = new BufferedReader(new FileReader(PathOfFile.MATCH_INFO + nameOfFile));
			String temp;
			String mainInfo = matchReader.readLine();
			if (mainInfo != null) {
				this.quarterPoint = matchReader.readLine();
				this.getMainInfoOfMatch(mainInfo);// 得到比赛的主要信息，包括得分,时间
				if ((temp = matchReader.readLine()) != null) {
					temp = temp.trim();
					if (!temp.equals("null")) {
						this.firstTeam = temp;// 初始化第一个队名
					}
				}
				for (int j = 0; j < NUMBER.NUMBER_OF_FIRST; j++) {
					temp = matchReader.readLine();
					if (temp != null) {
						listOfFirstTeamPlayerPerformance.add(this.getFirstTeamFirstPlayerPo(temp));
					}
				}
				while (((temp = matchReader.readLine()) != null) && (temp.length() != 3)) {
					listOfFirstTeamPlayerPerformance.add(this.getFirstTeamReplacePlayerPo(temp));
				}
				if (temp != null && temp.length() == 3) {
					String secondTeam = temp.trim();
					if (!secondTeam.equals("null")) {
						this.secondTeam = secondTeam;// 初始化第二个队名
					}
				}
				for (int i = 0; i < NUMBER.NUMBER_OF_FIRST; i++) {
					if ((temp = matchReader.readLine()) != null) {
						listOfSecondTeamPlayerPerformance.add(this.getSecondTeamFirstPlayerPo(temp));
					}

				}
				while ((temp = matchReader.readLine()) != null) {
					listOfSecondTeamPlayerPerformance.add(this.getSecondTeamReplacePlayerPo(temp));
				}
				matchReader.close();
				this.firstTeamPerformance = new TeamPerformOfOneMatch(this.firstTeam, this.secondTeam, this.date, isFirstWin(), this.listOfFirstTeamPlayerPerformance);
				this.secondTeamPerformance = new TeamPerformOfOneMatch(this.secondTeam, this.firstTeam, this.date, 1 - isFirstWin(), this.listOfSecondTeamPlayerPerformance);
				this.firstTeamPerformance.setPoint(firstTeamSocre);
				this.secondTeamPerformance.setPoint(secondTeamScore);
				this.correctTeamMinute(firstTeamPerformance);
				this.correctTeamMinute(secondTeamPerformance);
			}
			else {
				isDataCorrect = false;
			}
		} catch (IOException e) {
		}
	}

	private int isFirstWin() {
		if (this.firstTeamSocre > this.secondTeamScore) {
			return 1;
		}
		else {
			return 0;
		}
	}

	private void getMainInfoOfMatch(String formatString) {
		try {
			String[] part = formatString.split(";");
			String monthString = part[0].substring(0, 2);
			String dayString = part[0].substring(3);
			int month = this.toInt(monthString);
			int day = this.toInt(dayString);
			int year;
			String[] scores = part[2].split("-");
			this.firstTeamSocre = this.toInt(scores[0]);
			this.secondTeamScore = this.toInt(scores[1]);
			String team[] = part[1].split("-");
			if (!(team[0].equals("null") || team[1].equals("null"))) {
				this.firstTeam = team[0];
				this.secondTeam = team[1];
			}
			String startYear = nameOfFile.substring(0, 2);
			String finishYear = nameOfFile.substring(3, 5);
			if (month >= NUMBER.START_MONTH_OF_SEASON) {
				year = toInt(startYear);
			}
			else {
				year = toInt(finishYear);
			}
			this.date = new MyDate(year, month, day);
		} catch (Exception e) {
		}
	}// 根据第一行文本得到比赛主要信息

	private PlayerPerformOfOneMatch getFirstTeamFirstPlayerPo(String temp) {
		PlayerPerformOfOneMatch resultPo = new PlayerPerformOfOneMatch();
		try {
			String part[] = temp.split(";");
			resultPo.setTeamName(firstTeam);
			resultPo.setDate(date);
			resultPo.setStart(1);
			resultPo.setName(part[0]);
			resultPo.setMinute(toMinute(part[2]));
			resultPo.setTotalHit(this.toInt(part[3]));
			resultPo.setTotalShot(this.toInt(part[4]));
			resultPo.setThreeHit(this.toInt(part[5]));
			resultPo.setThreeShot(this.toInt(part[6]));
			resultPo.setFreeHit(this.toInt(part[7]));
			resultPo.setFreeShot(this.toInt(part[8]));
			resultPo.setOffendRebound(this.toInt(part[9]));
			resultPo.setDefendRebound(this.toInt(part[10]));
			resultPo.setRebound(this.toInt(part[11]));
			resultPo.setAssist(this.toInt(part[12]));
			resultPo.setSteal(this.toInt(part[13]));
			resultPo.setBlockShot(this.toInt(part[14]));
			resultPo.setFault(this.toInt(part[15]));
			resultPo.setFoul(this.toInt(part[16]));
			resultPo.setPoint(this.toInt(part[17]));
		} catch (Exception e) {
		}
		return resultPo;
	}// 第一队首发

	private PlayerPerformOfOneMatch getFirstTeamReplacePlayerPo(String temp) {
		PlayerPerformOfOneMatch resultPo = this.getFirstTeamFirstPlayerPo(temp);
		resultPo.setStart(0);
		return resultPo;
	}// 第一队替补

	private PlayerPerformOfOneMatch getSecondTeamFirstPlayerPo(String temp) {
		PlayerPerformOfOneMatch resultPo = this.getFirstTeamFirstPlayerPo(temp);
		resultPo.setTeamName(secondTeam);
		return resultPo;
	}// 第二队首发

	private PlayerPerformOfOneMatch getSecondTeamReplacePlayerPo(String temp) {
		PlayerPerformOfOneMatch resultPo = this.getFirstTeamFirstPlayerPo(temp);
		resultPo.setTeamName(secondTeam);
		resultPo.setStart(0);
		return resultPo;
	}// 第二队替补

	protected int toInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	protected double toMinute(String str) {
		double result = 0;
		try {
			String part[] = str.split(":");
			result = Double.parseDouble(part[0]) + Double.parseDouble(part[1]) / 60;
			BigDecimal bigDecimal = new BigDecimal(result);
			return bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	private void correctTeamMinute(TeamPerformOfOneMatch teamPerformance) {
		for (int i = 0; i < 5; i++) {
			double std = 240.5 + 25 * i;
			if (teamPerformance.getMinute() < std) {
				teamPerformance.setMinute(240 + 25 * i);
				break;
			}
		}
	}
}