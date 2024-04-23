package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession(true);// セッションを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化

		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}// 現在を起点に前後10年をリストに追加

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("class_num_set", list);//クラス番号のlistをセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のlistをセット

		//JSPへフォワード 7
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}
