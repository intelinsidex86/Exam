package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();//科目Daoを初期化
		HttpSession session = req.getSession(true);//セッションを取得
		Teacher teacher = (Teacher) session.getAttribute("user");//ログインしているユーザーを取得

		//リクエストパラメータ―の取得 2
		//なし
		//DBからデータ取得 3
		//ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		req.setAttribute("subjects", subjects);
		//JSPへフォワード 7
		//リクエストにデータをセット
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}
