package businesslogic.players;

public class CalculationOfPlayerPerform {

	public static double calHitRate(final double hitNum, final double shotNum) {
		if (shotNum == 0) {
			return 0;
		}
		else {
			double result = hitNum / shotNum;
			return cutTail(result);
		}
	}// 计算命中率

	public static double calEfficiency(double point, double rebound, double assist, double steal, double blockShot, double totalShot, double totalHit, double freeShot, double freeHit, double fault) {
		double result = (point + rebound + assist + steal + blockShot) - (totalShot - totalHit) - (freeShot - freeHit) - fault;
		return cutTail(result);
	}// 计算效率

	public static double calImproveRateInFiveMatch(double before, double after) {
		double result = (after - before) / before;
		return cutTail(result);
	}// 计算提升率

	public static double calGmSc(double point, double totalHit, double totalShot, double freeShot, double freeHit, double offendRebound, double defendRebound, double steal, double assist,
			double blockShot, double foul, double fault, int numOfGame) {
		if (numOfGame == 0) {
			return 0;
		}
		else {
			double result = (point + 0.4 * totalHit - 0.7 * totalShot - 0.4 * (freeShot - freeHit) + 0.7 * offendRebound + 0.3 * defendRebound + steal + 0.7 * assist + 0.7 * blockShot - 0.4 * foul - fault)
					/ numOfGame;
			return cutTail(result);
		}
	}// 计算GMSC效率

	public static double calRealShot(double point, double totalShot, double freeShot) {
		if (totalShot == 0 && freeShot == 0) {
			return 0;
		}
		else {
			double result = 0;
			result = point / (2 * (totalShot + 0.44 * freeShot));
			return cutTail(result);
		}
	}// 计算真实命中率

	public static double calShotEfficiency(double totalHit, double threeHit, double totalShot) {
		if (totalShot == 0) {
			return 0;
		}
		else {
			double result = 0;
			result = (totalHit + 0.5 * threeHit) / totalShot;
			return cutTail(result);
		}
	}// 计算投篮效率

	public static double calReboundEfficient(double rebound, double teamMinute, double minute, double teamRebound, double oppRebound) {
		double total = teamRebound + oppRebound;
		if (minute == 0 || total == 0) {
			return 0;
		}
		else {
			double result = (teamMinute / minute) / 5 * rebound / total;
			return cutTail(result);
		}
	}// 计算篮板率

	public static double calAssistEfficient(double assist, double minute, double teamMinute, double teamTotalHit, double totalHit) {
		if (minute == 0 || teamMinute == 0) {
			return 0;
		}
		else {
			double result = assist / ((minute / teamMinute) * 5 * teamTotalHit - totalHit);
			return cutTail(result);
		}
	}// 计算助攻率

	public static double calStealEfficient(double steal, double teamMinute, double minute, double oppOffendRound) {
		if (minute == 0 || oppOffendRound == 0) {
			return 0;
		}
		else {
			double result = steal * (teamMinute / minute) / 5 / oppOffendRound;
			return cutTail(result);
		}
	}// 计算抢断率

	public static double calBlockShotEfficient(double blockShot, double teamMinute, double minute, double oppTwoShot) {
		if (minute == 0 || oppTwoShot == 0) {
			return 0;
		}
		else {
			double result = blockShot * (teamMinute / minute) / 5 / oppTwoShot;
			return cutTail(result);
		}
	}// 计算该盖帽率

	public static double calFaultEfficient(double fault, double twoShot, double freeShot) {
		if (fault == 0 && twoShot == 0 && freeShot == 0) {
			return 0;
		}
		else {
			double result = fault / (twoShot + 0.44 * freeShot + fault);
			return cutTail(result);
		}
	}// 计算失误率

	public static double calFrequency(double totalShot, double freeShot, double fault, double teamMinute, double minute, double teamTotalShot, double teamFreeShot, double teamFault) {
		double temp = teamTotalShot + 0.44 * teamFreeShot + teamFault;
		if (temp == 0 || teamMinute == 0 || minute == 0) {
			return 0;
		}
		else {
			double result = (totalShot + 0.44 * freeShot + fault) * (teamMinute / minute) / 5 / temp;
			return cutTail(result);
		}
	}// 计算使用率

	public static double cutTail(double number) {
		return number;
	}// 保留四位小数
}
