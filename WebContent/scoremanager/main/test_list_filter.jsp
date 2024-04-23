<%-- 成績一覧用フィルターコンポーネント --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
	<form method="get" action="SubjectTestList.action">
		<div class="row align-items-center mx-3 border-bottom">
			<div class="col-2">科目情報</div>
			<div class="col-2">
				<label class="form-label" for="test-f1-select">入学年度</label>
				<select class="form-select" id="test-f1-select" name="f1">
					<option value="0">--------</option>
					<c:forEach var="year" items="${ent_year_set}">
						<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
						<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-2">
				<label class="form-label" for="test-f2-select">クラス</label>
				<select class="form-select" id="test-f2-select" name="f2">
					<option value="0">--------</option>
					<c:forEach var="num" items="${class_num_set}">
						<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
						<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-4">
				<label class="form-label" for="test-f3-select">科目</label>
				<select class="form-select" id="test-f3-select" name="f3">
					<option value="0">--------</option>
					<c:forEach var="subject" items="${subjects}">
						<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
						<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-2 text-center">
				<!-- 科目情報から成績を表示するための識別コード -->
				<input type="hidden" name="f" value="sj">
				<button class="btn btn-secondary" id="filter-subject-button">検索</button>
			</div>
			<div class="mt-2 text-warning">${errors.get("filter")}</div>
		</div>
	</form>
	<form method="get" action="StudentTestList.action">
		<div class="row align-items-center mx-3 mt-3">
			<div class="col-2">学生情報</div>
			<div class="col-4">
				<label class="form-label" for="test-f4-input">学生番号</label>
				<input class="form-control" type="text" id="test-f4-input" name="f4"
					placeholder="学生番号を入力してください" maxlength="10" value="${f4}" required />
				<div class="mt-2 text-warning">${errors.get("no")}</div>
			</div>
			<div class="col-2 text-center">
				<!-- 学生情報から成績を表示するための識別コード -->
				<input type="hidden" name="f" value="st">
				<button class="btn btn-secondary" id="filter-student-button">検索</button>
			</div>
		</div>
	</form>
</div>