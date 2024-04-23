<%-- 学生更新JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form action = "StudentUpdateExecute.action" method="post">
				<div class="mx-3 py-2">
					<div class="mb-3">
						<label class="form-label" for="student-ent_year-input">入学年度</label>
						<input readonly class="form-control-plaintext ms-3" type="text"
							id="student-ent_year-input" name="ent_year" value="${ent_year}" />
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-no-input">学生番号</label>
						<input readonly class="form-control-plaintext ms-3" type="text"
							id="student-no-input" name="no" value="${no}" />
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-name-input">氏名</label>
						<input class="form-control" type="text" id="student-name-input"
							name="name" placeholder="氏名を入力してください" maxlength="10"
							value="${name}" required />
						<div class="mt-2 text-warning">${errors.get("name")}</div>
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-class_num-select">クラス</label>
						<select class="form-select" id="student-class_num-select" name="class_num">
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-attend-check"> 在学中</label>
						<%-- is_attendがtrueの場合checkedを追記 --%>
						<input class="form-check-input" type="checkbox"
							id="student-attend-check" name="is_attend"
							<c:if test="${is_attend}">checked</c:if>>
					</div>
					<div class="mt-3">
						<input class="btn btn-primary" type="submit" value="変更">
					</div>
				</div>
			</form>
			<div class="lh-lg row">
				<div class="mx-3 col-1">
					<a href="StudentList.action">戻る</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>