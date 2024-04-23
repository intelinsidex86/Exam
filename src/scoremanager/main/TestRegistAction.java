package scoremanager.main;

import java.time.LocalDate;
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
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の初期化 1
		TestDao testDao = new TestDao();// 成績Daoを初期化
		List<Test> tests = null;// 成績リスト
		SubjectDao subjectDao = new SubjectDao();// 科目Daoを初期化
		Subject subject = null;// 科目インスタンス
		Map<String, String> errors = new HashMap<>();// エラーリスト
		HttpSession session = req.getSession(true);// セッションを取得
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		School school = teacher.getSchool();// ログインユーザーの学校を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();// 入学年度のリストを初期化
		List<Integer> numSet = new ArrayList<>();// テストの回数リストを初期化

		// リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");	// 入学年度
		String classNum = req.getParameter("f2");	// クラス番号
		String subjectCd = req.getParameter("f3");	// 科目コード
		String numStr = req.getParameter("f4");		// 回数

		// 入学年度
		int entYear = 0;
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		// 回数
		int num = 0;
		if (numStr != null) {
			num = Integer.parseInt(numStr);
		}

		// DBからデータ取得 3
		List<String> list = cNumDao.filter(teacher.getSchool());//クラス番号の一覧を取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());//科目の一覧を取得

		// ビジネスロジック 4
		// 現在の前後10年を入学年度のリストに追加
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}
		// 全2回分のテスト回数をリストに追加
		for (int i = 1; i <= 2; i++) {
			numSet.add(i);
		}

		// DBへデータ保存 5
		// なし

		// レスポンス値をセット 6
		req.setAttribute("f1", entYear);// 入学年度
		req.setAttribute("f2", classNum);// クラス番号
		req.setAttribute("f3", subjectCd);// 科目コード
		req.setAttribute("f4", num);// 回数
		req.setAttribute("class_num_set", list);//クラス番号の一覧をセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のリストをセット
		req.setAttribute("subjects", subjects);//科目の一覧をセット
		req.setAttribute("num_set", numSet);//テストの回数リストをセット

		// 全てのパラメータがnullの場合は画面初期表示
		if (entYearStr != null && classNum != null && subjectCd != null && numStr != null) {
			// 全てのパラメーターが選択されている場合
			if (entYear != 0 && !classNum.equals("0") && !subjectCd.equals("0") && num != 0) {
				System.out.println("全パラメータ指定されてます！");

				// 科目コードから科目インスタンスを取得
				subject = subjectDao.get(subjectCd, school);
				// 成績を取得
				tests = testDao.filter(entYear, classNum, subject, num, school);
				// リクエストに回数をセット設定
				req.setAttribute("num", num);
				// リクエストに科目を設定
				req.setAttribute("subject", subject);
				// リクエストに成績を設定
				req.setAttribute("tests", tests);
			} else {
				// 未選択がある場合はエラーメッセージを表示して画面再表示
				errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
				req.setAttribute("errors", errors);
			}
		}

		// フォワード 7
		// 成績管理一覧画面を表示
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
