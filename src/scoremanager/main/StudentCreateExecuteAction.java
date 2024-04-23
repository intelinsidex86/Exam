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
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		StudentDao sDao = new StudentDao();//学生Dao
		int entYear;//入学年度
		String no = "";//学生番号
		String name = "";//氏名
		String classNum = "";//クラス番号
		Student student = null;//学生
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化

		//リクエストパラメータ―の取得 2
		entYear = Integer.parseInt(req.getParameter("ent_year"));//入学年度
		no = req.getParameter("no");//学生番号
		name = req.getParameter("name");//氏名
		classNum = req.getParameter("class_num");//クラス番号

		//DBからデータ取得 3
		student = sDao.get(no);// 学生番号から学生インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}// 現在を起点に前後10年をリストに追加

		if (entYear == 0) {// 入学年度が選択されていない場合
			errors.put("ent_year", "入学年度を選択してください");
		}else{
			if (student == null) {// 学生が未登録だった場合
				// 学生インスタンスを初期化
				student = new Student();
				// インスタンスに値をセット
				student.setNo(no);
				student.setName(name);
				student.setEntYear(entYear);
				student.setClassNum(classNum);
				student.setAttend(true);
				student.setSchool(((Teacher)session.getAttribute("user")).getSchool());
				// 学生を保存
				sDao.save(student);
			} else {//入力された学番がDBに保存されていた場合
				errors.put("no", "学生番号が重複しています");
			}
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("class_num_set", list);//クラス番号のlistをセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のlistをセット

		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("ent_year", entYear);
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);
			req.getRequestDispatcher("student_create.jsp").forward(req, res);
			return;
		}
		req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
	}
}
