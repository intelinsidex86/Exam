package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String cd = "";
		String name = "";
		SubjectDao dao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");
		name = req.getParameter("name");

		//DBからデータ取得 3
		//科目コードから科目インスタンスを取得
		Subject subject = dao.get(cd, teacher.getSchool());

		//ビジネスロジック 4
		//DBへデータ保存 5
		//レスポンス値をセット 6
		if (subject == null) {
			// 更新対象の科目が存在しないのでエラーページへ遷移
			errors.put("cd", "科目が存在していません");
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("error.jsp").forward(req, res);
		} else {
			// 科目の更新を実行して科目更新完了画面へ遷移
			subject.setName(name);
			// 科目情報を更新
			dao.save(subject);
			// 科目更新完了画面へ遷移
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
		}
	}
}
