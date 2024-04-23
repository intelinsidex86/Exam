package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

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

		// リクエストパラメータ―の取得 2
		//なし
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
		req.setAttribute("class_num_set", list);//クラス番号の一覧をセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のリストをセット
		req.setAttribute("subjects", subjects);//科目の一覧をセット
		req.setAttribute("num_set", numSet);//テストの回数リストをセット

		// フォワード 7
		// 成績一覧画面(初期表示)にフォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}
