<%-- 科目削除JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			<form action="SubjectDeleteExecute.action" method="post">
				<div class="mx-3 py-2">
					<div class="my-3">「${name}(${cd})」を削除してもよろしいですか</div>
					<input class="btn btn-danger" type="submit" value="削除">
					<input type="hidden" name="cd" value="${cd}">
				</div>
			</form>
			<div class="lh-lg row" style="margin-top: 4rem;">
				<div class="mx-3 col-2">
					<a href="SubjectList.action">戻る</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>