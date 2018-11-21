$(document).ready(function() {
	var index = $('#index').text();
	var title = $('#title').text();
	var writer = $('#writer').text();
	var date = $('#date').text();
	var contents = $('#contents').text();
	
	$('#listBtn').click(function() {
		window.location='list.do';
	})
	
	$('#modifiedBtn').click(function(){
		$('#modifiedForm').submit();
	})
	
	$('#delete').click(function(e){
		e.preventDefault();
		$.ajax({
			url : "delete.do",
			type : "POST",
			data : {index : index},
			success : function(res) {
				if(res == "true"){
					window.location.href = "list.do";
				}else{
					alert('삭제가 되지 않았습니다.');
				}
			},
			error : function(requestObject, error, errorThrown) {
				console.log(error);
				console.log(errorThrown);
			}
		});
	})
});