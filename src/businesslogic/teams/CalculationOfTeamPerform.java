package businesslogic.teams;

public class CalculationOfTeamPerform {
	private final static double STD_NUM = 100;

	public static double average(double num, int time) {
		if (time == 0) {
			return 0;
		}
		else {
			double result = num / time;
			return cutTail(result);
		}
	}// 计算平均数

	public static double calHitRate(double hitNum, double shootNum) {
		if (shootNum == 0) {
			return 0;
		}
		else {
			double result = 0;
			result = hitNum / shootNum;
			return cutTail(result);
		}
	}// 计算命中率

	public static double calWinRate(int winNum, int totalNum) {
		if (totalNum == 0) {
			return 0;
		}
		else {
			double result = (double) winNum / (double) totalNum;
			return cutTail(result);
		}
	}// 计算胜率

	public static double calOffendRound(double totalShot, double totalHit, double freeShot, double offendRebound, double oppDefendRebound, double fault) {
		double total = offendRebound + oppDefendRebound;
		if (total == 0) {
			return 0;
		}
		else {
			double result = totalShot + 0.4 * freeShot + 1.07 * (fault - (totalShot - totalHit) * offendRebound / total);
			return cutTail(result);
		}
	}// 计算进攻回合数

	public static double calOffendEfficient(double point, double offendNum) {
		if (offendNum == 0) {
			return 0;
		}
		else {
			double result = STD_NUM * point / offendNum;
			return cutTail(result);
		}
	}// 计算进攻效率

	public static double calDeffendEfficient(double oppPoint, double defendNum) {
		if (defendNum == 0) {
			return 0;
		}
		else {
			double result = STD_NUM * oppPoint / defendNum;
			return cutTail(result);
		}
	}// 计算防守效率

	public static double calOffendReboundEfficient(double offendRebound, double oppDefendRebound) {
		double total = offendRebound + oppDefendRebound;
		if (total == 0) {
			return 0;
		}
		else {
			double result = offendRebound / total;
			return cutTail(result);
		}
	}// 计算进攻篮板效率

	public static double calDefendReboundEfficient(double defendRebound, double oppOffendRebound) {
		double total = defendRebound + oppOffendRebound;
		if (total == 0) {
			return 0;
		}
		else {
			double result = defendRebound / total;
			return cutTail(result);
		}
	}// 计算防守篮板效率

	public static double calStealEfficient(double steal, double oppOffendNum) {
		if (oppOffendNum == 0) {
			return 0;
		}
		else {
			double result = STD_NUM * steal / oppOffendNum;
			return cutTail(result);
		}
	}// 抢断效率

	public static double calAssistEfficient(double assist, double offendNum) {
		if (offendNum == 0) {
			return 0;
		}
		else {
			double result = STD_NUM * assist / offendNum;
			return cutTail(result);
		}
	}// 助攻效率

	public static double cutTail(double number) {
		return number;
	}// 保留八位小数
}
