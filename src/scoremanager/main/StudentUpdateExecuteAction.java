package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		StudentDao sDao = new StudentDao();// 学生Dao
		boolean isAttend = false;//在学フラグ
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String entYearStr= req.getParameter("ent_year");
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		// 在学フラグにチェックが入っていた場合
		if (isAttendStr != null) {
			// 在学フラグを立てる
			isAttend = true;
		}

		//DBからデータ取得 3
		Student student = sDao.get(no);// 学生番号から学生インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		if (student != null) {
			// 学生が存在していた場合
			// インスタンスに値をセット
			student.setName(name);
			student.setClassNum(classNum);
			student.setAttend(isAttend);
			// 学生を保存
			sDao.save(student);
		} else {
			errors.put("no", "学生が存在していません");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("class_num_set", list);

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);
			req.setAttribute("is_attend", isAttendStr);
			req.getRequestDispatcher("student_update.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}
