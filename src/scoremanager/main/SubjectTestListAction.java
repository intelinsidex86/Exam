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
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class SubjectTestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の初期化 1
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
		HttpSession session = req.getSession(true);// セッションを取得
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();// 入学年度のリストを初期化
		List<Integer> numSet = new ArrayList<>();// テストの回数リストを初期化
		//画面下の成績一覧用変数
		int entYear = 0; // 入学年度
		String entYearStr = null; // 入学年度文字列
		String classNum = null; // クラス番号
		String subjectCd = null; //科目番号
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ文字列
		List<TestListSubject> tests = new ArrayList<>(); //成績のリスト
		TestListSubjectDao testDao = new TestListSubjectDao(); //科目成績表示用Daoを初期化
		SubjectDao subjectDao = new SubjectDao(); //科目Dao
		Subject subject = null; //科目

		// リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		// 入学年度
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
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
		req.setAttribute("f1", entYear);
		// クラス番号
		req.setAttribute("f2", classNum);
		// 科目コード
		req.setAttribute("f3", subjectCd);
		req.setAttribute("class_num_set", list);//クラス番号の一覧をセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のリストをセット
		req.setAttribute("subjects", subjects);//科目の一覧をセット
		req.setAttribute("num_set", numSet);//テストの回数リストをセット

		// 全てのパラメータがnullの場合は画面初期表示
		if (entYearStr != null || classNum != null || subjectCd != null) {
			// 全てのパラメーターが存在しているかチェック
			if (entYear != 0 && !classNum.equals("0") && !subjectCd.equals("0")) {
				// ログインユーザーの学校を取得
				School school = teacher.getSchool();

				// 科目コードから科目インスタンスを取得
				subject = subjectDao.get(subjectCd, school);
				// 条件に該当する成績を抽出
				tests = testDao.filter(entYear, classNum, subject, school);

				// リクエストに科目をセット
				req.setAttribute("subject", subject);
				// リクエストに成績をセット
				req.setAttribute("tests", tests);
			} else {
				// 未選択のパラメーターが存在する場合エラー
				errors.put("filter", "入学年度とクラスと科目を選択してください");
				req.setAttribute("errors", errors);
			}
		}

		// フォワード 7
		// 科目別成績一覧画面にフォワード
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}
