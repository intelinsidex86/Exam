package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の初期化 1
		HttpSession session = req.getSession(true);// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
		// ログインユーザーの学校を取得
		School school = teacher.getSchool();
		// 学生Daoを初期化
		StudentDao studentDao = new StudentDao();
		// 科目Daoを初期化
		SubjectDao subjectDao = new SubjectDao();
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化

		// エラーメッセージ格納用マップ
		Map<String, String> errors = new HashMap<>();
		// 入力された得点用マップ
		Map<String, String> inputPoints = new HashMap<>();
		// 成績リストを初期化
		List<Test> gradeList = new ArrayList<>();
		// 削除リストを初期化
		List<Test> delList = new ArrayList<>();

		// リクエストパラメータ―の取得 2
		// 入力された科目コードを取得
		String subjectCd = req.getParameter("subject_cd");
		// 入力された回数を取得
		int num = Integer.parseInt(req.getParameter("num"));
		// 入力された学生番号の一覧を取得
		String[] studentNoSet = req.getParameterValues("student_no_set[]");

		// DBからデータ取得 3
		//DBからデータ取得 3
		List<String> list = cNumDao.filter(school);// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		// 科目コードから科目インスタンスを取得
		Subject subject = subjectDao.get(subjectCd, school);

		// ビジネスロジック 4
		// DBへデータ保存 5
		// 学生を全件走査
		for (String studentNo : studentNoSet) {
			// 成績インスタンスを初期化
			Test test = new Test();

			// 得点文字列
			String pointStr = null;

			// 入力された「point_学生番号」の得点文字列を取得
			pointStr = req.getParameter("point_" + studentNo);
			// 得点用マップに学生番号と得点文字列を格納
			inputPoints.put(studentNo, pointStr);

			if (pointStr.isEmpty()) {
				continue;
			}

			// 得点
			int point = 0;

			try {
				// 得点文字列を整数に変換
				point = Integer.parseInt(pointStr);
			} catch (NumberFormatException e) {
				// 整数に変換できなかった場合エラー
				errors.put(studentNo, "整数以外入力できません");
				break;
			}

			if (point < 0 || 100 < point) {
				// 得点が0～100の範囲にない場合エラー
				errors.put(studentNo, "0～100の範囲で入力してください");
				break;
			}

			// 成績インスタンスに値をセット
			test.setNo(num);
			test.setPoint(point);
			test.setSchool(school);
			test.setStudent(studentDao.get(studentNo));
			test.setSubject(subject);

			// 削除フラグ
			boolean del = false;

			// 入力された「delete_学生番号」の削除フラグが存在する場合
			if (req.getParameter("delete_" + studentNo) != null) {
				// 削除フラグを立てる
				del = true;
			}

			// 削除フラグチェック
			if (del) {
				// 削除フラグが立っている場合は削除リストに成績を追加
				delList.add(test);
			} else {
				// 削除フラグが立っていない場合は成績リストに成績を追加
				gradeList.add(test);
			}
		}

		// レスポンス値をセット 6
		// フォワード 7
		if (errors.size() > 0) {
			// エラーが発生していた場合
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("error.jsp").forward(req, res);
		} else {
			// 正常終了時の処理

			// リクエストに登録対象の得点用マップを設定
			req.setAttribute("input_points", inputPoints);

			// 成績Daoを初期化
			TestDao testDao = new TestDao();
			// 成績リストから成績を保存
			testDao.save(gradeList);
			// 削除リストから成績を削除
			testDao.delete(delList);

			// 「登録して続ける」ボタンが押されていた場合
			if (req.getParameter("continue") != null) {
				// 完了メッセージをセット
				req.setAttribute("done", "登録が完了しました");
				// 入力画面にフォワード
				req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			} else {
				// 完了ページにリダイレクト
				req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
			}
		}
	}
}
