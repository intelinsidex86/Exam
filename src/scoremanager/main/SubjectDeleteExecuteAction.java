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

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String cd = "";
		Subject subject = null;
		SubjectDao dao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");//科目コード

		//DBからデータ取得 3
		subject = dao.get(cd, teacher.getSchool());//科目

		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//レスポンス値をセット 6
		//JSPへフォワード 7
		//エラーがあったかどうかで手順5~7の内容が分岐
		if (subject == null) {
			// 削除対象の科目が存在しないのでエラーページへ遷移
			errors.put("cd", "科目が存在していません");
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
		} else {
			// 科目の削除を実行して科目削除完了画面へ遷移
			dao.delete(subject);
			req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
		}
	}
}
