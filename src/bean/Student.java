package bean;

import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
	/**
	 * 学生番号:String
	 */
	private String no;

	/**
	 * 氏名:String
	 */
	private String name;

	/**
	 * 入学年度:int
	 */
	private int entYear;

	/**
	 * クラス番号:String
	 */
	private String classNum;

	/**
	 * 在学中フラグ:boolean true:在学中
	 */
	private boolean isAttend;

	/**
	 * 所属校:School
	 */
	private School school;

	/**
	 * ゲッター、セッター
	 */
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public boolean isAttend() {
		return isAttend;
	}

	public void setAttend(boolean isAttend) {
		this.isAttend = isAttend;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * getSchoolYearメソッド 現在の年度と入学年度から現在の学年を求める
	 *
	 * @return 現在の学年:int
	 */
	public int getSchoolYear() {
//		LocalDateインスタンスを取得
		LocalDate todaysDate = LocalDate.now();
//		現在の月と年を取得
		int month = todaysDate.getMonthValue();
		int year = todaysDate.getYear();
//		現在の月が１月から３月までの場合
		if (1 <= month && month <= 3) {
//			現在の年を1減らす
			year--;
		}
//		現在の年と入学年度から算出した現在の学年を返却
		return year - entYear + 1;
	}

}
