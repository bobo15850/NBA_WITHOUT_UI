package businesslogic.teams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import businesslogic.CACHE;
import businesslogic.MySort;
import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import common.mydatastructure.SortCell;
import common.mydatastructure.TeamNormalInfo_Expand;
import common.statics.Field;
import data.teams.TeamInfoData;
import dataservice.teams.TeamInfoDataService;
import autoTestService.TeamAutoTestService;

public class TeamAutoTest implements TeamAutoTestService {
	private TeamInfoDataService teamData = TeamInfoData.getInstance();

	@SuppressWarnings("unchecked")
	public ArrayList<TeamHotInfo> getTeamHot(int number, String field) {
		ArrayList<TeamNormalInfo_Expand> teamNormal_avg = new ArrayList<TeamNormalInfo_Expand>(32);
		for (Entry<String, TeamNormalInfo_Expand> temp : CACHE.TEAM_NORMAL.entrySet()) {
			teamNormal_avg.add(temp.getValue().getTeamNormal_avg());
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		sortFields.add(new BeanComparator(field));
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(teamNormal_avg, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > teamNormal_avg.size()) {
			number = teamNormal_avg.size();
		}
		ArrayList<TeamHotInfo> resultList = new ArrayList<TeamHotInfo>(number);
		for (int i = teamNormal_avg.size() - 1; i > teamNormal_avg.size() - number - 1; i--) {
			TeamHotInfo tempKing = new TeamHotInfo();
			tempKing.setField(field);
			TeamNormalInfo_Expand tempNormal_avg = teamNormal_avg.get(i);
			tempKing.setTeamName(tempNormal_avg.getTeamName());
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
			tempKing.setValue(value);
			tempKing.setLeague(this.teamData.getLeagueOfTeam(tempKing.getTeamName()));
			resultList.add(tempKing);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TeamHighInfo> getTeamHigh(int number, SortCell[] sortCells) {
		ArrayList<TeamHighInfo> teamHighList = new ArrayList<TeamHighInfo>();
		for (Entry<String, TeamHighInfo> temp : CACHE.TEAM_HIGH.entrySet()) {
			TeamHighInfo tempHighInfo = temp.getValue();
			teamHighList.add(tempHighInfo);
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(teamHighList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > teamHighList.size()) {
			number = teamHighList.size();
		}
		ArrayList<TeamHighInfo> resultList = new ArrayList<TeamHighInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add(teamHighList.get(i));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TeamNormalInfo> getTeamNormal_avg(int number, SortCell[] sortCells) {
		ArrayList<TeamNormalInfo_Expand> teamNormalList = new ArrayList<TeamNormalInfo_Expand>();
		for (Entry<String, TeamNormalInfo_Expand> temp : CACHE.TEAM_NORMAL.entrySet()) {
			TeamNormalInfo_Expand tempNormalInfo = temp.getValue().getTeamNormal_avg();
			teamNormalList.add(tempNormalInfo);
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(teamNormalList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > teamNormalList.size()) {
			number = teamNormalList.size();
		}
		ArrayList<TeamNormalInfo> resultList = new ArrayList<TeamNormalInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add((TeamNormalInfo) teamNormalList.get(i));
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TeamNormalInfo> getTeamNormal_tot(int number, SortCell[] sortCells) {
		ArrayList<TeamNormalInfo_Expand> teamNormalList = new ArrayList<TeamNormalInfo_Expand>();
		for (Entry<String, TeamNormalInfo_Expand> temp : CACHE.TEAM_NORMAL.entrySet()) {
			TeamNormalInfo_Expand tempNormalInfo = temp.getValue();
			teamNormalList.add(tempNormalInfo);
		}
		ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();// 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
		for (int i = 0; i < sortCells.length; i++) {
			sortFields.add(MySort.getBeanComparator(sortCells[i]));
		}
		ComparatorChain comChain = new ComparatorChain(sortFields);// 创建一个排序链
		Collections.sort(teamNormalList, comChain);// 开始真正的排序，按照先主，后副的规则
		if (number > teamNormalList.size()) {
			number = teamNormalList.size();
		}
		ArrayList<TeamNormalInfo> resultList = new ArrayList<TeamNormalInfo>(number);
		for (int i = 0; i < number; i++) {
			resultList.add((TeamNormalInfo) teamNormalList.get(i));
		}
		return resultList;
	}
}
