package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	/**
	 * getメソッド 科目コードと学校を指定して科目インスタンスを1件取得する
	 *
	 * @param cd:String
	 *            科目コード
	 * @param school:School
	 *            学校
	 * @return 科目クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Subject get(String cd, School school) throws Exception {
		// 科目インスタンスを初期化
		Subject subject = new Subject();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
			// プリペアードステートメントに科目コードをバインド
			statement.setString(1, cd);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(2, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
			} else {
				// リザルトセットが存在しない場合
				// 科目インスタンスにnullをセット
				subject = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return subject;
	}

	/**
	 * filterメソッド 学校を指定して科目の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @return 科目のリスト:List<Subject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		// リストを初期化
		List<Subject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントに値をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? order by cd");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// リザルトセットを全件走査
			while (rSet.next()) {
				// 科目インスタンスの初期化
				Subject subject = new Subject();
				// 科目インスタンスに値をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				// リストに追加
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
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

	/**
	 * saveメソッド 科目インスタンスをデータベースに保存する データが存在する場合は更新、存在しない場合は登録
	 *
	 * @param subject:Subject
	 *            学生
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
	public boolean save(Subject subject) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		int count = 0;

		try {
			// データベースから科目を取得
			Subject old = get(subject.getCd(), subject.getSchool());
			if (old == null) {
				// 科目が存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("insert into subject(name, cd, school_cd) values(?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getSchool().getCd());
			} else {
				// 科目が存在する場合
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("update subject set name=? where cd=?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getCd());
			}

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	/**
	 * deleteメソッド 科目をデータベースから削除する
	 *
	 * @param subject:Subject
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
	public boolean delete(Subject subject) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// プリペアードステートメントにDELETE文をセット
			statement = connection.prepareStatement("delete from subject where cd=?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, subject.getCd());
			// プリペアードステートメントを実行
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}
