<%-- 科目成績一覧JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
			<%-- 成績一覧用フィルターコンポーネントをインポート --%>
			<c:import url="test_list_filter.jsp" />
			<c:choose>
				<%-- 成績情報が存在する場合 --%>
				<c:when test="${tests.size()>0}">
					<div>科目：${subject.name}</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>１回</th>
							<th>２回</th>
						</tr>
						<c:forEach var="test" items="${tests}">
							<tr>
								<td>${test.entYear}</td>
								<td>${test.classNum}</td>
								<td>${test.studentNo}</td>
								<td>${test.studentName}</td>
								<td>${test.getPoint(1)}</td>
								<td>${test.getPoint(2)}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<%-- 成績情報が存在しない場合 --%>
				<c:when test="${tests.size()==0}">
					<div>学生情報が存在しませんでした</div>
				</c:when>
			</c:choose>
		</section>
	</c:param>
</c:import>

