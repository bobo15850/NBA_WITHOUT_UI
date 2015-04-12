package autoTestService;

import common.mydatastructure.Filter;
import common.mydatastructure.SortCell;
import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;

public interface PlayerAutoTestService {
	public PlayerHotInfo[] getPlayerHot(int number, String field);// 得到热门球员数组

	public PlayerKingInfo[] getPlayerKingOfSeason(int number, String field);// 得到赛季数据王数组

	public PlayerKingInfo[] getPlayerKingOfDaily(int number, String field);// 得到当日数据王数组

	public PlayerHighInfo[] getPlayerHigh(int number, SortCell[] sortcells);// 得到球员高阶数据

	public PlayerNormalInfo[] getPlayerNormal_avg(int number, Filter filter, SortCell[] sortcells);// 得到球员平均普通数据

	public PlayerNormalInfo[] getPlayerNormal_tot(int number, Filter filter, SortCell[] sortcells);// 得到球员总和普通数据
}
