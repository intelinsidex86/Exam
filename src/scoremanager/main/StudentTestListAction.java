package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class StudentTestListAction extends Action {

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
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ文字列
		Student student = null;
		List<TestListStudent> tests = new ArrayList<>();

		// リクエストパラメータから値を取得
		String f = req.getParameter("f");
		String studentNo = req.getParameter("f4");

		// リクエストにセットされたエラーメッセージを取得
		errors = (HashMap<String, String>)req.getAttribute("errors");

		// DBへデータ保存 5
		// なし

		// DBからデータ取得 3
		// ビジネスロジック 4
		// レスポンス値をセット 6
		// 状況によって分岐
		if (studentNo != null) {
			// リクエストパラメーターが存在している場合学生番号から学生情報を取得
			StudentDao studentDao = new StudentDao();
			student = studentDao.get(studentNo);

			// 学生情報が存在していた場合、その学生の成績を取得
			if (student != null) {
				// 学生成績表示用Daoを初期化
				TestListStudentDao testDao = new TestListStudentDao();
				tests = testDao.filter(student);
			}
		} else {
			errors.put("filter", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("error.jsp").forward(req, res);
		}
		// リクエストに学生をセット
		req.setAttribute("student", student);
		// リクエストに成績をセット
		req.setAttribute("tests", tests);

		// フォワード 7
		// 学生成績一覧画面に遷移
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

	}

	/**
	 * 引数に指定された項目がnullではいか、および0ではないかをチェックして結果を返却する
	 *
	 * @param year 年度(数値)
	 * @param yearStr 年度(文字列)
	 * @param classNum クラス番号
	 * @param subjectCd 科目コード
	 *
	 * @return 指定された引数が全てOKだった場合はtrue。その他の場合はfalse
	 * @throws Exception
	 */
	private boolean existsAllParam(int entYear, String entYearStr, String classNum, String subjectCd) {

		boolean result = false;

		if (entYearStr != null || classNum != null || subjectCd != null) {
			// 全てのパラメーターが存在している場合
			if (entYear != 0 && !classNum.equals("0") && !subjectCd.equals("0")) {
				result = true;
			}
		}
		return result;
	}
}
