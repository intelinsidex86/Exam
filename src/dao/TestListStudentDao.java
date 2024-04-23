package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "SELECT SJ.name as sj_name, SJ.cd as sj_cd, T.no as t_no, T.point as t_point "
			+ "FROM student ST inner join (test T inner join subject SJ on T.subject_cd=SJ.cd) "
			+ "on ST.no=T.student_no ";

	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @return 学生成績表示用のリスト:List<TestListStudent> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();

		while (rSet.next()) {
			TestListStudent test = new TestListStudent();
			test.setNum(rSet.getInt("t_no"));
			test.setPoint(rSet.getInt("t_point"));
			test.setSubjectCd(rSet.getString("sj_cd"));
			test.setSubjectName(rSet.getString("sj_name"));

			list.add(test);
		}
		return list;
	}

	/**
	 * filterメソッド 学生を指定して学生成績表示用の一覧を取得する
	 *
	 * @param student:Student
	 *            学生
	 * @return 学生成績表示用のリスト:List<TestListStudent> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		String condition = "where ST.no=?";
		String order = " order by SJ.cd asc, T.no asc";

		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, student.getNo());
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
