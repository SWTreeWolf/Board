<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	Date now = new Date();
%>


<!-- 글 일반작성 -->
<input type="hidden" id="index" value="${index}" />
<div class="titlecontainer">
	<div class="form-group row">
		<div class="col">
			<label for="writer" class="col-sm-1 col-form-label" id="label">작성자</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="writer"
					placeholder="작성자">
			</div>
		</div>

		<div class="col" id="data-container">
			<label for="date" class="col-sm-2 col-form-label" id="label">날짜</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="date"
					value='<fmt:formatDate value="<%=now%>" pattern="yyyy-MM-dd"/>'
					readonly>
			</div>
		</div>
	</div>
	<div class="form-group row">
		<label for="title" class="col-sm-1 col-form-label" id="label">제목</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="title" placeholder="제목">
		</div>
	</div>
</div>
<form method="post" class="contentcontainer" action="write.do"
	id="fileForm">
	<textarea name="editordata" id="summernote"></textarea>
	<div class="btnContainer">
		<button type="button" id="createBtn" class="btn btn-secondary">작성</button>
		<button type="button" id="resetBtn" class="btn btn-secondary">취소</button>
	</div>
</form>

