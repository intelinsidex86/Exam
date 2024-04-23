<%-- 成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			<%-- 成績一覧用フィルターコンポーネントをインポート --%>
			<c:import url="test_list_filter.jsp" />
			<div class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</div>
		</section>
	</c:param>
</c:import>

