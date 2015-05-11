package common.mydatastructure;

import businesslogic.teams.CalculationOfTeamPerform;
import test.data.TeamNormalInfo;

public class TeamNormalInfo_Expand extends TeamNormalInfo {
	private static final long serialVersionUID = 1L;
	private double totalHit = 0;// 总命中数
	private double totalShot = 0;// 总出手数
	private double threeHit = 0;// 三分命中数
	private double threeShot = 0;// 三分出手数
	private double freeHit = 0;// 罚球命中数
	private double freeShot = 0;// 罚球出手数
	private int numOfWin = 0;// 胜利场数
	private double minute = 0;// 比赛时间
	// 以下为对手数据
	private double oppOffendRebound = 0;
	private double oppDefendRebound = 0;
	private double oppPoint = 0;
	private double oppTotalShot = 0;
	private double oppTotalHit = 0;
	private double oppFreeShot = 0;
	private double oppFault = 0;

	public TeamNormalInfo_Expand getTeamNormal_avg() {
		TeamNormalInfo_Expand teamNormal_avg = new TeamNormalInfo_Expand();
		int numOfGame = getNumOfGame();
		teamNormal_avg.setTeamName(getTeamName());
		teamNormal_avg.setNumOfGame(numOfGame);
		teamNormal_avg.setPenalty(getPenalty());
		teamNormal_avg.setShot(getShot());
		teamNormal_avg.setThree(getThree());
		teamNormal_avg.setNumOfWin(numOfWin);
		teamNormal_avg.setAssist(CalculationOfTeamPerform.cutTail(getAssist() / numOfGame));
		teamNormal_avg.setBlockShot(CalculationOfTeamPerform.cutTail(getBlockShot() / numOfGame));
		teamNormal_avg.setDefendRebound(CalculationOfTeamPerform.cutTail(getDefendRebound() / numOfGame));
		teamNormal_avg.setFault(CalculationOfTeamPerform.cutTail(getFault() / numOfGame));
		teamNormal_avg.setFoul(CalculationOfTeamPerform.cutTail(getFoul() / numOfGame));
		teamNormal_avg.setFreeHit(CalculationOfTeamPerform.cutTail(getFreeHit() / numOfGame));
		teamNormal_avg.setFreeShot(CalculationOfTeamPerform.cutTail(getFreeShot() / numOfGame));
		teamNormal_avg.setOffendRebound(CalculationOfTeamPerform.cutTail(getOffendRebound() / numOfGame));
		teamNormal_avg.setPoint(CalculationOfTeamPerform.cutTail(getPoint() / numOfGame));
		teamNormal_avg.setRebound(CalculationOfTeamPerform.cutTail(getRebound() / numOfGame));
		teamNormal_avg.setSteal(CalculationOfTeamPerform.cutTail(getSteal() / numOfGame));
		teamNormal_avg.setThreeHit(CalculationOfTeamPerform.cutTail(threeHit / numOfGame));
		teamNormal_avg.setThreeShot(CalculationOfTeamPerform.cutTail(threeShot / numOfGame));
		teamNormal_avg.setTotalHit(CalculationOfTeamPerform.cutTail(totalHit / numOfGame));
		teamNormal_avg.setTotalShot(CalculationOfTeamPerform.cutTail(totalShot / numOfGame));
		teamNormal_avg.setFreeHit(freeHit / numOfGame);
		teamNormal_avg.setFreeShot(freeShot / numOfGame);
		teamNormal_avg.setMinute(minute / numOfGame);
		//
		teamNormal_avg.setOppOffendRebound(oppOffendRebound / numOfGame);
		teamNormal_avg.setOppDefendRebound(oppDefendRebound / numOfGame);
		teamNormal_avg.setOppPoint(oppPoint / numOfGame);
		teamNormal_avg.setOppFault(oppFault / numOfGame);
		teamNormal_avg.setOppTotalHit(oppTotalHit / numOfGame);
		teamNormal_avg.setOppTotalShot(oppTotalShot / numOfGame);
		teamNormal_avg.setOppFreeShot(oppFreeShot / numOfGame);
		return teamNormal_avg;

	}

	public double getTotalHit() {
		return totalHit;
	}

	public void setTotalHit(double totalHit) {
		this.totalHit = totalHit;
	}

	public double getTotalShot() {
		return totalShot;
	}

	public void setTotalShot(double totalShot) {
		this.totalShot = totalShot;
	}

	public double getThreeHit() {
		return threeHit;
	}

	public void setThreeHit(double threeHit) {
		this.threeHit = threeHit;
	}

	public double getThreeShot() {
		return threeShot;
	}

	public void setThreeShot(double threeShot) {
		this.threeShot = threeShot;
	}

	public double getFreeHit() {
		return freeHit;
	}

	public void setFreeHit(double freeHit) {
		this.freeHit = freeHit;
	}

	public double getFreeShot() {
		return freeShot;
	}

	public void setFreeShot(double freehot) {
		this.freeShot = freehot;
	}

	public int getNumOfWin() {
		return numOfWin;
	}

	public void setNumOfWin(int numOfWin) {
		this.numOfWin = numOfWin;
	}

	public double getMinute() {
		return minute;
	}

	public void setMinute(double minute) {
		this.minute = minute;
	}

	public double getOppOffendRebound() {
		return oppOffendRebound;
	}

	public void setOppOffendRebound(double oppOffendRebound) {
		this.oppOffendRebound = oppOffendRebound;
	}

	public double getOppDefendRebound() {
		return oppDefendRebound;
	}

	public void setOppDefendRebound(double oppDefendRebound) {
		this.oppDefendRebound = oppDefendRebound;
	}

	public double getOppPoint() {
		return oppPoint;
	}

	public void setOppPoint(double oppPoint) {
		this.oppPoint = oppPoint;
	}

	public double getOppTotalShot() {
		return oppTotalShot;
	}

	public void setOppTotalShot(double oppTotalShot) {
		this.oppTotalShot = oppTotalShot;
	}

	public double getOppTotalHit() {
		return oppTotalHit;
	}

	public void setOppTotalHit(double oppTotalHit) {
		this.oppTotalHit = oppTotalHit;
	}

	public double getOppFault() {
		return oppFault;
	}

	public void setOppFault(double oppFault) {
		this.oppFault = oppFault;
	}

	public double getOppFreeShot() {
		return oppFreeShot;
	}

	public void setOppFreeShot(double oppFreeShot) {
		this.oppFreeShot = oppFreeShot;
	}
}
