<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table">
	<thead class="thead-light">
		<tr>
			<th class="col">목록</th>
			<th class="col-5">제목</th>
			<th class="col">작성자</th>
			<th class="col">날짜</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="list">
			<tr id="${list.index}">
				<th class="col">${list.index}</th>
				<td class="col"><a href="read.do?index=${list.index}">${list.title}</a></td>
				<td class="col">${list.writer}</td>
				<td class="col">${list.date}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="pag_container">
	<ul class="pagination">
		<c:if test="${pv.startPage > 1}">
			<li class="page-item"><a class="page-link"
				href="list.do?currentPage=${pv.startPage - pv.blockPage}">
					Previous </a></li>
		</c:if>

		<c:forEach var="i" begin="${pv.startPage}" end="${pv.endPage}">
			<li class="page-item"><c:choose>
					<c:when test="${currentPage eq i}">
						<a class="page-link">${i}</a>
					</c:when>
					<c:otherwise>
						<a class="page-link" href="list.do?currentPage=${i}"> ${i} </a>
					</c:otherwise>
				</c:choose></li>
		</c:forEach>

		<c:if test="${pv.endPage < pv.totalPage}">
			<li class="page-item"><a class="page-link"
				href="list.do?currentPage=${pv.endPage + pv.blockPage}"> Next </a></li>
		</c:if>
	</ul>
</div>