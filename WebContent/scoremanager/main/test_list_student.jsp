<%-- 学生成績一覧JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>
			<%-- 成績一覧用フィルターコンポーネントをインポート --%>
			<c:import url="test_list_filter.jsp" />
			<c:choose>
				<%-- 学生情報が存在する場合 --%>
				<c:when test="${!empty student}">
					<div>氏名：${student.name} (${student.no})</div>
					<c:choose>
						<%-- 成績情報が存在する場合 --%>
						<c:when test="${tests.size()>0}">
							<table class="table table-hover">
								<tr>
									<th>科目名</th>
									<th>科目コード</th>
									<th>回数</th>
									<th>点数</th>
								</tr>
								<c:forEach var="test" items="${tests}">
									<tr>
										<td>${test.subjectName}</td>
										<td>${test.subjectCd}</td>
										<td>${test.num}</td>
										<td>${test.point}</td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<%-- 成績情報が存在しない場合 --%>
						<c:when test="${tests.size()==0}">
							<p>成績情報が存在しませんでした</p>
						</c:when>
					</c:choose>
				</c:when>
				<%-- 学生情報が存在しない場合 --%>
				<c:otherwise>
					<div>学生情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>

