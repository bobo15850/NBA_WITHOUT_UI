package businesslogic.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import businesslogic.CACHE;
import businesslogic.MySort;
import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import common.mydatastructure.Filter;
import common.mydatastructure.GeneralInfoOfPlayer;
import common.mydatastructure.PlayerNormalInfo_Expand;
import common.mydatastructure.PlayerPerformOfOneMatch;
import common.mydatastructure.SortCell;
import common.statics.Age;
import common.statics.Field;
import common.statics.League;
import common.statics.Position;
import data.players.PlayerInfoData;
import dataservice.players.PlayerInfoDataService;
import autoTestService.PlayerAutoTestService;

public class PlayerAutoTest implements PlayerAutoTestService {
	private PlayerInfoDataService playerData = PlayerInfoData.getInstance();

	public ArrayList<PlayerHotInfo> getPlayerHot(int number, String field) {

		ArrayList<PlayerHotInfo> playerHotList = new ArrayList<PlayerHotInfo>();
		ArrayList<String> playerNames = this.playerData.getNamesOfAllPlayer();
		for (int i = 0; i < playerNames.size(); i++) {
			String playerName = playerNames.get(i);
			ArrayList<PlayerPerformOfOneMatch> onePlayerPerform = this.playerData.getOnePlayerPerformOfOneSeasonPo(playerName);
			int numOfGame = onePlayerPerform.size();
			if (numOfGame > 5) {
				PlayerHotInfo tempHotInfo = new PlayerHotInfo();
				tempHotInfo.setField(field);
				tempHotInfo.setName(playerName);
				tempHotInfo.setPosition(playerData.getGeneralInfoOfOnePlayer(playerName).getPosition());
				tempHotInfo.setTeamName(onePlayerPerform.get(numOfGame - 1).getTeamName());
				double before = 0;
				double latestFive = 0;
				double all = 0;
				ArrayList<Double> dataList = new ArrayList<Double>(128);
				if (field.equals(Field.point)) {
					dataList = this.getPerform(onePlayerPerform, new Point());
				}
				else if (field.equals(Field.rebound)) {
					dataList = this.getPerform(onePlayerPerform, new Rebond());
				}
				else if (field.equals(Field.assist)) {
					dataList = this.getPerform(onePlayerPerform, new Assist());
				}
				else if (field.equals(Field.steal)) {
					dataList = this.getPerform(onePlayerPerform, new Steal());
				}
				else if (field.equals(Field.blockShot)) {
					dataList = this.getPerform(onePlayerPerform, new BlockShot());
				}
				for (int j = 0; j < dataList.size() - 5; j++) {
					before += dataList.get(j);
				}
				for (int j = dataList.size() - 5; j < dataList.size(); j++) {
					latestFive += dataList.get(j);
				}
				if (before != 0) {
					all = before + latestFive;
					// 以上都为总和，以下为平均
					all /= numOfGame;
					before /= (numOfGame - 5);
					latestFive /= 5;
					double upGradeRate = (CalculationOfPlayerPerform.cutTail(latestFive - before) / before);
					tempHotInfo.setValue(CalculationOfPlayerPerform.cutTail(all));
					tempHotInfo.setUpgradeRate(CalculationOfPlayerPerform.cutTail(upGradeRate));
					playerHotList.add(tempHotInfo);
				}
			}
		}
		Collections.sort(playerHotList, new Comparator<PlayerHotInfo>() {
			public int compare(PlayerHotInfo o1, PlayerHotInfo o2) {
				if (o1.getUpgradeRate() < o2.getUpgradeRate()) {
					return 1;
				}
				else if (o1.getUpgradeRate() == o2.getUpgradeRate()) {
					return 0;
				}
				else {
					return -1;
				}
			}
		});
		if (number > playerHotList.size()) {
			number = playerHotList.size();
		}
		ArrayList<PlayerHotInfo> resultList = new ArrayList<PlayerHotInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add(playerHotList.get(i));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerKingInfo> getPlayerKingOfSeason(int number, String field) {
		ArrayList<PlayerNormalInfo_Expand> playerNormal_avg = new ArrayList<PlayerNormalInfo_Expand>(512);
		for (Entry<String, PlayerNormalInfo_Expand> temp : CACHE.PLAYER_NORMAL.entrySet()) {
			playerNormal_avg.add(temp.getValue().getPlayerNormal_avg());
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		sortFields.add(new BeanComparator(field));
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(playerNormal_avg, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > playerNormal_avg.size()) {
			number = playerNormal_avg.size();
		}
		ArrayList<PlayerKingInfo> resultList = new ArrayList<PlayerKingInfo>(number);
		for (int i = playerNormal_avg.size() - 1; i > playerNormal_avg.size() - number - 1; i--) {
			PlayerKingInfo tempKing = new PlayerKingInfo();
			PlayerNormalInfo_Expand tempNormal_avg = playerNormal_avg.get(i);
			double value = 0;
			if (field.equals(Field.point)) {
				value = tempNormal_avg.getPoint();
			}
			else if (field.equals(Field.rebound)) {
				value = tempNormal_avg.getRebound();
			}
			else if (field.equals(Field.assist)) {
				value = tempNormal_avg.getAssist();
			}
			else if (field.equals(Field.steal)) {
				value = tempNormal_avg.getSteal();
			}
			else if (field.equals(Field.blockShot)) {
				value = tempNormal_avg.getBlockShot();
			}
			else if (field.equals(Field.shot)) {
				value = tempNormal_avg.getShot();
			}
			else if (field.equals(Field.three)) {
				value = tempNormal_avg.getThree();
			}
			else if (field.equals(Field.penalty)) {
				value = tempNormal_avg.getPenalty();
			}
			tempKing.setName(tempNormal_avg.getName());
			tempKing.setTeamName(tempNormal_avg.getTeamName());
			tempKing.setField(field);
			tempKing.setValue(value);
			tempKing.setPosition(this.playerData.getGeneralInfoOfOnePlayer(tempNormal_avg.getName()).getPosition());
			resultList.add(tempKing);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerKingInfo> getPlayerKingOfDaily(int number, String field) {
		ArrayList<PlayerPerformOfOneMatch> playerPerformOneDay = new ArrayList<PlayerPerformOfOneMatch>();
		for (Entry<String, PlayerPerformOfOneMatch> playerPerformOfOneMatch : CACHE.PLAYER_TODAY.entrySet()) {
			playerPerformOneDay.add(playerPerformOfOneMatch.getValue());
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		sortFields.add(new BeanComparator(field));
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(playerPerformOneDay, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > playerPerformOneDay.size()) {
			number = playerPerformOneDay.size();
		}
		ArrayList<PlayerKingInfo> resultList = new ArrayList<PlayerKingInfo>(number);
		for (int i = playerPerformOneDay.size() - 1; i > playerPerformOneDay.size() - number - 1; i--) {
			PlayerKingInfo tempKing = new PlayerKingInfo();
			PlayerPerformOfOneMatch tempPerformOfOneMatch = playerPerformOneDay.get(i);
			double value = 0;
			if (field.equals(Field.point)) {
				value = tempPerformOfOneMatch.getPoint();
			}
			else if (field.equals(Field.rebound)) {
				value = tempPerformOfOneMatch.getRebound();
			}
			else if (field.equals(Field.assist)) {
				value = tempPerformOfOneMatch.getAssist();
			}
			else if (field.equals(Field.steal)) {
				value = tempPerformOfOneMatch.getSteal();
			}
			else if (field.equals(Field.blockShot)) {
				value = tempPerformOfOneMatch.getBlockShot();
			}
			tempKing.setField(field);
			tempKing.setName(tempPerformOfOneMatch.getName());
			tempKing.setTeamName(tempPerformOfOneMatch.getTeamName());
			tempKing.setPosition(this.playerData.getGeneralInfoOfOnePlayer(tempPerformOfOneMatch.getName()).getPosition());
			tempKing.setValue(value);
			resultList.add(tempKing);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerHighInfo> getPlayerHigh(int number, SortCell[] sortCells) {
		ArrayList<PlayerHighInfo> playerHighList = new ArrayList<PlayerHighInfo>(512);// 合格的球员链表
		for (Entry<String, PlayerHighInfo> temp : CACHE.PLAYER_HIGH.entrySet()) {
			PlayerHighInfo tempHighInfo = temp.getValue();
			PlayerNormalInfo tempNormalInfo = CACHE.PLAYER_NORMAL.get(tempHighInfo.getName());
			double averageMinute = tempNormalInfo.getMinute() / tempNormalInfo.getNumOfGame();
			if (averageMinute >= 15) {
				playerHighList.add(tempHighInfo);
			}
		}// 筛选出合格的球员，根据上场时间筛选，>=15分钟为合格
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(playerHighList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > playerHighList.size()) {
			number = playerHighList.size();
		}
		ArrayList<PlayerHighInfo> resultList = new ArrayList<PlayerHighInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add(playerHighList.get(i));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerNormalInfo> getPlayerNormal_avg(int number, Filter filter, SortCell[] sortCells) {
		ArrayList<PlayerNormalInfo_Expand> playerNormalList = new ArrayList<PlayerNormalInfo_Expand>(512);
		for (Entry<String, PlayerNormalInfo_Expand> temp : CACHE.PLAYER_NORMAL.entrySet()) {
			PlayerNormalInfo_Expand playerNormal_avg = temp.getValue().getPlayerNormal_avg();
			PlayerInfoDataService playerData = PlayerInfoData.getInstance();
			GeneralInfoOfPlayer generalInfo = playerData.getGeneralInfoOfOnePlayer(playerNormal_avg.getName());
			String league = playerData.getLeague(playerNormal_avg.getName());
			if (this.isAgeSuit(filter.getAge(), generalInfo.getAge()) && this.isLeagueSuit(filter.getLeague(), league)
					&& this.isPositionSuit(filter.getPosition(), generalInfo.getPosition()) && playerNormal_avg.getMinute() >= 15) {
				playerNormalList.add(playerNormal_avg);
			}
		}// 三个筛选条件加上一个默认的出场时间>=15
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(playerNormalList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > playerNormalList.size()) {
			number = playerNormalList.size();
		}
		ArrayList<PlayerNormalInfo> resultList = new ArrayList<PlayerNormalInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add((PlayerNormalInfo) playerNormalList.get(i));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PlayerNormalInfo> getPlayerNormal_tot(int number, Filter filter, SortCell[] sortCells) {
		ArrayList<PlayerNormalInfo_Expand> playerNormalList = new ArrayList<PlayerNormalInfo_Expand>(512);
		for (Entry<String, PlayerNormalInfo_Expand> temp : CACHE.PLAYER_NORMAL.entrySet()) {
			PlayerNormalInfo_Expand playerNormal = temp.getValue();
			PlayerInfoDataService playerData = PlayerInfoData.getInstance();
			GeneralInfoOfPlayer generalInfo = playerData.getGeneralInfoOfOnePlayer(playerNormal.getName());
			String league = playerData.getLeague(playerNormal.getName());
			double averageMinute = playerNormal.getMinute() / playerNormal.getNumOfGame();
			if (this.isAgeSuit(filter.getAge(), generalInfo.getAge()) && this.isLeagueSuit(filter.getLeague(), league)
					&& this.isPositionSuit(filter.getPosition(), generalInfo.getPosition()) && averageMinute >= 15) {
				playerNormalList.add(playerNormal);
			}
		}// 三个筛选条件加上一个默认的出场时间>=15
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(playerNormalList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > playerNormalList.size()) {
			number = playerNormalList.size();
		}

		ArrayList<PlayerNormalInfo> resultList = new ArrayList<PlayerNormalInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add((PlayerNormalInfo) playerNormalList.get(i));
		}
		return resultList;
	}

	private boolean isAgeSuit(String AgeFilter, int age) {
		if (AgeFilter.equals(Age.All)) {
			return true;
		}
		else if (AgeFilter.equals(Age.M_22_L_E_25)) {
			if (age > 22 && age <= 25) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (AgeFilter.equals(Age.M_25_L_E_30)) {
			if (age > 25 && age <= 30) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (AgeFilter.equals(Age.L_E_22)) {
			if (age <= 22) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (AgeFilter.equals(Age.M_30)) {
			if (age >= 30) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	private boolean isLeagueSuit(String leagueFilter, String league) {
		if (leagueFilter.equals(League.All)) {
			return true;
		}
		else if (leagueFilter.equals(league)) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isPositionSuit(String positionFilter, String position) {
		if (positionFilter.equals(Position.All)) {
			return true;
		}
		else if (positionFilter.equals(position)) {
			return true;
		}
		else if (position.contains(positionFilter)) {
			return true;
		}
		else {
			return false;
		}
	}

	private ArrayList<Double> getPerform(ArrayList<PlayerPerformOfOneMatch> playerPerformList, PlayerPerform perform) {
		ArrayList<Double> dataList = new ArrayList<Double>(128);
		for (int i = 0; i < playerPerformList.size(); i++) {
			dataList.add(perform.getPerformance(playerPerformList.get(i)));
		}
		return dataList;
	}

	interface PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player);
	}

	class Rebond implements PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player) {
			return player.getRebound();
		}
	}// 总篮板

	class Assist implements PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player) {
			return player.getAssist();
		}
	}// 总助攻数

	class Steal implements PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player) {
			return player.getSteal();
		}
	}// 总抢断数

	class BlockShot implements PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player) {
			return player.getBlockShot();
		}
	}// 总盖帽数

	class Point implements PlayerPerform {
		public double getPerformance(PlayerPerformOfOneMatch player) {
			return player.getPoint();
		}
	}// 得分
}
