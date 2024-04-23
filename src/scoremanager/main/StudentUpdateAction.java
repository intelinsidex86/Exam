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

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		StudentDao sDao = new StudentDao();//学生Dao
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String no = req.getParameter("no");//学番

		//DBからデータ取得 3
		Student student = sDao.get(no);//学生番号から学生インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//レスポンス値をセット 6
		//条件で手順4~6の内容が分岐
		req.setAttribute("class_num_set", list);
		if (student != null) {// 学生が存在していた場合
			req.setAttribute("ent_year", student.getEntYear());
			req.setAttribute("no", student.getNo());
			req.setAttribute("name", student.getName());
			req.setAttribute("class_num", student.getClassNum());
			req.setAttribute("is_attend", student.isAttend());
		} else {// 学生が存在していなかった場合
			errors.put("no", "学生が存在していません");
			req.setAttribute("errors", errors);
		}
		//JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}
