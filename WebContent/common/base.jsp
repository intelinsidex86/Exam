<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<title>${param.title}</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
${param.scripts}
</head>
<body>
	<div id="wrapper" class="container">
		<header
			class="d-flex flex-wrap justify-content-center py-3 px-5 mb-4 border-bottom border-2 bg-primary bg-opacity-10 bg-gradient">
			<c:import url="/common/header.jsp" />
		</header>

		<div class="row justify-content-center">
			<c:choose>
				<%-- ログイン済みの場合 --%>
				<c:when test="${user.isAuthenticated()}">
					<nav class="col-3" style="height:40rem;">
						<c:import url="/common/navigation.jsp" />
					</nav>
					<main class="col-9 border-start"> ${param.content} </main>
				</c:when>
				<%-- 未ログインの場合 --%>
				<c:otherwise>
					<main class="col-8"> ${param.content} </main>
				</c:otherwise>
			</c:choose>
		</div>
		<footer class="py-2 my-4 bg-dark bg-opacity-10 border-top border-3 align-bottom">
			<c:import url="/common/footer.jsp" />
		</footer>

	</div>
</body>
</html>
