package start;

import java.io.PrintStream;
import java.util.ArrayList;

import businesslogic.players.PlayerAutoTest;
import businesslogic.teams.TeamAutoTest;
import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import autoTestService.PlayerAutoTestService;
import autoTestService.TeamAutoTestService;
import common.mydatastructure.Filter;
import common.mydatastructure.SortCell;
import common.statics.Command;
import common.statics.Field;

public class Terminal {
	private PlayerAutoTestService playerBl = new PlayerAutoTest();
	private TeamAutoTestService teamBl = new TeamAutoTest();
	private String commandArray[];
	private PrintStream outStream;
	private int length;

	public void excute(PrintStream out, String args[]) {
		outStream = out;
		commandArray = args;
		length = args.length;
		if (args[0].equals(Command.player)) {// 进入球员模块
			if (length != 1) {
				if (args[1].equals(Command.high)) {
					this.playerHigh();
				}
				else if (args[1].equals(Command.hot)) {
					this.playerHot();
				}
				else if (args[1].equals(Command.king)) {
					this.playerKing();
				}
				else {
					this.playerAll();
				}
			}
			else {
				this.playerAll();
			}
		}
		else if (args[0].equals(Command.team)) {// 进入球队模块
			if (length != 1) {
				if (args[1].equals(Command.high)) {
					this.teamHigh();
				}
				else if (args[1].equals(Command.hot)) {
					this.teamHot();
				}
				else {
					this.teamAll();
				}
			}
			else {
				this.teamAll();
			}
		}
	}

	private void playerAll() {
		ArrayList<PlayerNormalInfo> resultList;
		String avg_or_tot = Command.average;
		int number = 50;// 数目
		Filter filter = new Filter();// 筛选器
		String sortCmd = Field.score + Command.dot + Command.descend;// 排序命令
		int mark = 1;
		if (mark != length) {
			if (commandArray[mark].equals(Command.total)) {// 展示总和数据
				avg_or_tot = Command.total;
				mark++;
			}
			else if (commandArray[mark].equals(Command.average)) {
				mark++;
			}
			if (mark != length) {
				if (commandArray[mark].equals(Command.all)) {
					mark++;
				}
				if (mark != length) {
					if (commandArray[mark].equals(Command.number)) {
						number = toInt(commandArray[++mark]);
						mark++;
					}
					if (mark != length) {
						if (commandArray[mark].equals(Command.filter)) {
							filter.setFilter(commandArray[++mark]);
							mark++;
						}
						if (mark != length) {
							sortCmd = commandArray[++mark];
						}
					}
				}
			}
		}
		if (avg_or_tot.equals(Command.total)) {
			resultList = this.playerBl.getPlayerNormal_tot(number, filter, this.toSortCells(sortCmd));
		}
		else {
			resultList = this.playerBl.getPlayerNormal_avg(number, filter, this.toSortCells(sortCmd));
		}
		this.addPrintStream(resultList, number);
		System.out.println(avg_or_tot + "--" + number + "--" + filter.toString() + "--" + sortCmd);
	}

	private void playerKing() {
		ArrayList<PlayerKingInfo> resultArray = null;
		String field = commandArray[2];
		int number = 5;
		if (length != 4) {
			number = this.toInt(commandArray[5]);
		}
		if (commandArray[3].equals(Command.season)) {
			resultArray = this.playerBl.getPlayerKingOfSeason(number, field);
			System.out.println(field + Command.season + "-" + number);
			// 赛季field平均数据王
		}
		else if (commandArray[3].equals(Command.daily)) {
			resultArray = this.playerBl.getPlayerKingOfDaily(number, field);
			System.out.println(field + Command.daily + "-" + number);
			// 当日field平均数据王
		}
		this.addPrintStream(resultArray, 1);
	}

	private void playerHot() {
		ArrayList<PlayerHotInfo> resultArray = null;
		int number = 5;
		String field = commandArray[2];
		if (length != 3) {
			if (commandArray[3].equals(Command.number)) {
				number = toInt(commandArray[4]);
			}
		}
		resultArray = this.playerBl.getPlayerHot(number, field);
		this.addPrintStream(resultArray, number);
		System.out.println(field + "--" + String.valueOf(number));
		// //// 热门球员。根据number以及field查找
	}

	private void playerHigh() {
		ArrayList<PlayerHighInfo> resultArray = null;
		int number = 50;
		String sortCmd = Field.realShot + Command.dot + Command.descend;
		if (length != 2) {
			if (commandArray[2].equals(Command.number)) {
				number = toInt(commandArray[3]);
				if (length != 4) {
					sortCmd = commandArray[5];// 按照给定的规则排序
				}
			}
			else {
				sortCmd = commandArray[3];
			}
		}
		resultArray = this.playerBl.getPlayerHigh(number, this.toSortCells(sortCmd));
		this.addPrintStream(resultArray, number);
		System.out.println(String.valueOf(number) + sortCmd);
		// 以number和sortCmd来筛选
	}

	private void teamHigh() {
		ArrayList<TeamHighInfo> resultArray = null;
		int number = 30;
		String sortCmd = Field.winRate + Command.dot + Command.descend;
		if (length != 2) {
			if (commandArray[2].equals(Command.number)) {
				number = toInt(commandArray[3]);
				if (length != 4) {
					sortCmd = commandArray[5];// 按照给定的规则排序
				}
			}
			else {
				sortCmd = commandArray[3];
			}
		}
		resultArray = this.teamBl.getTeamHigh(number, this.toSortCells(sortCmd));
		this.addPrintStream(resultArray, number);
		System.out.println(String.valueOf(number) + sortCmd);
		// 以number和sortCmd来筛选
	}

	private void teamHot() {
		ArrayList<TeamHotInfo> resultArray = null;
		int number = 5;
		String field = commandArray[2];
		if (length != 3) {
			if (commandArray[3].equals(Command.number)) {
				number = toInt(commandArray[4]);
			}
		}
		resultArray = this.teamBl.getTeamHot(number, field);
		this.addPrintStream(resultArray, number);
		System.out.println(field + "--" + String.valueOf(number));
		// //// 热门球队。根据number以及field查找
	}

	private void teamAll() {
		ArrayList<TeamNormalInfo> resultArray = null;
		String avg_or_tot = Command.average;
		int number = 30;// 数目
		String sortCmd = Field.score + Command.dot + Command.descend;// 排序命令
		int mark = 1;
		if (mark != length) {
			if (commandArray[mark].equals(Command.total)) {// 展示总和数据
				avg_or_tot = Command.total;
				mark++;
			}
			else if (commandArray[mark].equals(Command.average)) {
				mark++;
			}
			if (mark != length) {
				if (commandArray[mark].equals(Command.all)) {
					mark++;
				}
				if (mark != length) {
					if (commandArray[mark].equals(Command.number)) {
						number = toInt(commandArray[++mark]);
						mark++;
					}
					sortCmd = commandArray[++mark];
				}
			}
		}
		if (avg_or_tot.equals(Command.total)) {
			resultArray = this.teamBl.getTeamNormal_tot(number, this.toSortCells(sortCmd));
		}
		else {
			resultArray = this.teamBl.getTeamNormal_avg(number, this.toSortCells(sortCmd));
		}
		this.addPrintStream(resultArray, number);
		System.out.println(avg_or_tot + "--" + number + "--" + sortCmd);
		// 以以上条件来查找筛选
	}

	private int toInt(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (NumberFormatException n) {
			result = 0;
		}
		return result;
	}

	private SortCell[] toSortCells(String sortCmd) {
		String sortCmdCell[] = sortCmd.split(",");
		int length = sortCmdCell.length;
		SortCell[] sortcells = new SortCell[length];
		for (int i = 0; i < length; i++) {
			sortcells[i] = new SortCell(sortCmdCell[i]);
		}
		return sortcells;
	}

	private void addPrintStream(@SuppressWarnings("rawtypes") ArrayList resultList, int number) {
		if (resultList != null) {
			for (int i = 0; i < number; i++) {
				this.outStream.print(resultList.get(i));
			}
		}
	}
}