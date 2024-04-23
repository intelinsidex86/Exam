package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "SELECT ST.ent_year as st_ent_year, ST.no as st_no, ST.name as st_name, "
			+ "ST.class_num as st_class_num, T.no as t_no, T.point as t_point "
			+ "FROM student ST left outer join (test T inner join subject SJ on T.subject_cd=SJ.cd) "
			+ "on ST.no=T.student_no ";

	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @return 科目成績表示用のリスト:List<TestListSubject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		TestListSubject test = new TestListSubject();

		// 現在の学生番号
		String currentStudentNo = null;
		while (rSet.next()) {
			// 学生番号を取得
			String studentNo = rSet.getString("st_no");

			if (currentStudentNo == null) {
				// 最初のデータの場合
				// 現在の学生番号に学生番号をセット
				currentStudentNo = studentNo;

				// インスタンスに値をセット
				test.setStudentNo(studentNo);
				test.setEntYear(rSet.getInt("st_ent_year"));
				test.setClassNum(rSet.getString("st_class_num"));
				test.setStudentName(rSet.getString("st_name"));
			} else if (!studentNo.equals(currentStudentNo)) {
				// 学生が変わった場合
				// リストに追加
				list.add(test);
				// インスタンスを初期化
				test = new TestListSubject();

				// 現在の学生番号に学生番号をセット
				currentStudentNo = studentNo;
				// インスタンスに値をセット
				test.setStudentNo(studentNo);
				test.setEntYear(rSet.getInt("st_ent_year"));
				test.setClassNum(rSet.getString("st_class_num"));
				test.setStudentName(rSet.getString("st_name"));
			}

			// 回数と得点を取得
			int num = rSet.getInt("t_no");
			int point = rSet.getInt("t_point");
			// 得点マップにセット
			test.putPoint(num, point);
		}
		if (currentStudentNo != null) {
			// 結果が0件でなかった場合
			// 最後のデータをリストに追加
			list.add(test);
		}
		return list;
	}

	/**
	 * filterメソッド 入学年度、クラス番号、科目、学校を指定して科目成績表示用の一覧を取得する
	 *
	 * @param entYear:int
	 *            入学年度
	 * @param classNum:String
	 *            クラス番号
	 * @param subject:Subject
	 *            科目
	 * @param school：School
	 *            学校
	 * @return 科目成績表示用のリスト:List<TestListSubject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;
		List<TestListSubject> list = new ArrayList<>();
		ResultSet rSet = null;

		String condition = "and T.subject_cd=? "
				+ "where ST.ent_year=? and ST.class_num=? and ST.school_cd=? and ST.is_attend=true";
		String order = " order by ST.no asc, T.no asc";

		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, subject.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, school.getCd());
			rSet = statement.executeQuery();
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
