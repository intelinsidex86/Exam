package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		//なし
		//リクエストパラメータ―の取得 2
		String cd = req.getParameter("cd");//科目コード
		String name = req.getParameter("name");//科目名
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
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}
