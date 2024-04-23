package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class SubjectUpdateAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		//なし

		//リクエストパラメータ―の取得 2
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		//DBからデータ取得 3
		//なし
		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		//JSPへフォワード 7
		// 科目更新画面へ遷移
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}
