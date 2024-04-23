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

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String cd = "";
		String name = "";
		Subject subject = null;
		SubjectDao subDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");// 科目コード
		name = req.getParameter("name");// 科目名

		//DBからデータ取得 3
		//なし
		//ビジネスロジック 4
		if (cd.length() != 3) {// 科目コードが3文字以外だった場合エラー
			errors.put("cd", "科目コードは3文字で入力してください");
		} else {
			// 科目コードを元に科目情報を取得
			subject = subDao.get(cd, teacher.getSchool());
			if (subject != null) {// 科目が存在していた場合はエラー
				errors.put("cd", "科目コードが重複しています");
			}
		}

		//DBへデータ保存 5
		//レスポンス値をセット 6
		//JSPへフォワード 7
		//エラーがあったかどうかで手順5~7の内容が分岐
		if(errors.isEmpty()){
			// 登録用の科目インスタンスを生成
			subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());
			// 科目情報を登録
			subDao.save(subject);
			// 正常終了時は科目登録完了画面へ遷移
			req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
		} else {
			// エラー発生時はエラーページへ遷移
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
		}
	}
}