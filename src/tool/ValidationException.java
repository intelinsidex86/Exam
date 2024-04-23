package tool;

public class ValidationException extends Exception {
	/**
	 * 属性:String
	 */
	private String attr;

	/**
	 * コンストラクタ 属性を指定せずにエラーメッセージをセット
	 *
	 * @param message:String
	 *            メッセージ
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ 属性とメッセージを指定してエラーメッセージをセット
	 *
	 * @param attr:String
	 *            属性
	 * @param message:String
	 *            エラーメッセージ
	 */
	public ValidationException(String attr, String message) {
		this(message);
		this.attr = attr;
	}

	/**
	 * ゲッター
	 *
	 * @return
	 */

	public String getAttribute() {
		return attr;
	}
}
