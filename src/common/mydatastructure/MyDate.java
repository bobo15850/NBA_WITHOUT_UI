package common.mydatastructure;

public class MyDate implements Comparable<MyDate> {
	private int year;
	private int month;
	private int day;

	public MyDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public MyDate(String formatString) {
		String part[] = formatString.split("-");
		this.year = Integer.parseInt(part[0]);
		this.month = Integer.parseInt(part[1]);
		this.day = Integer.parseInt(part[2]);
	}

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public int getDay() {
		return this.day;
	}

	public String getFormatString() {
		return this.year + "-" + this.month + "-" + this.day;
	}

	public int compareTo(MyDate o) {

		if (this.year == o.year) {
			if (this.month == o.month) {
				if (this.day == o.day) {
					return 0;
				}
				else if (this.day > o.day) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else if (this.month > o.month) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else if (this.year > o.year) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public boolean equals(Object date) {
		MyDate d = (MyDate) date;
		if ((this.year == d.year) && (this.month == d.month) && (this.day == d.day)) {
			return true;
		}
		else {
			return false;
		}
	}
}
