<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<form id="modifiedForm" action="modified.do" method="GET">
	<input name="index" type="hidden" value="${file.index}" />
</form>
<table class="table">
	<thead class="thead-light">
		<tr>
			<th class="col" id="index">${file.index}</th>
			<th class="col-5" id="title">${file.title}</th>
			<th class="col" id="writer">${file.writer}</th>
			<th class="col" id="date">${file.date}</th>
		</tr>
	</thead>
</table>
<div class="contents col-sm">${file.contents}</div>
<div class="BtnContainer col-sm">
	<button type="button" id="listBtn" class="btn btn-secondary">목록</button>
	<button type="button" id="modifiedBtn" class="btn btn-secondary">수정</button>
</div>
