$(document).ready(function() {
	$('#summernote').summernote({
		height : 500,
		minHeight : 300,
		maxHeight : 500,
		focus : true,
		placeholder : 'Hello World!',
		callbacks : {
			onImageUpload : function(files) {
				var that = $(this);
				sendFile(files[0], that);
			},
			onMediaDelete : function($target, editor, $editable) {
				var filename = ($target[0].src).substring(($target[0].src).lastIndexOf("/")+1);
				console.log(filename); // img
				console.log(typeof filename)
				deleteFile(filename);
				
				// remove element in editor
				$target.remove(filename);
			}
		}
	});

	$('#createBtn').click(function() {
		var index = $('#index').val();
		var title = $('#title').val();
		var writer = $('#writer').val();
		var contents = $('#summernote').summernote('code');
		var date = $('#date').val();

		if (title == '' || writer == '' || date == '') {
			alert('모두 작성하셔야 합니다');
		} else {
			$.ajax({
				url : "write.do",
				type : "post",
				data : {
					index : index,
					contents : contents,
					title : title,
					writer : writer,
					date : date
				},
				success : function(result) {
					console.log(result);
					if (result == "true") {
						$('.contentcontainer').submit();
						window.location.href = "list.do";
					} else {
						alert('에러가 발생하였습니다. 다시 시도해 주세요');
					}
				},
				error : function(requestObject, error, errorThrown) {
					console.log(error);
					console.log(errorThrown);
					alert('작성되지 않았습니다.');
				}
			});
		}
	});

	$('#resetBtn').click(function() {
		window.location.href = "list.do";
	});
});

function sendFile(file, that) {
	uploadfile = new FormData();
	uploadfile.append("file", file);
	console.log(uploadfile);

	$.ajax({
		data : uploadfile,
		type : "POST",
		url : "upload/images.do",
		cache : false,
		contentType : false,
		processData : false,
		success : function(url) {
			console.log(location.origin + '/' + url);
			$(that).summernote('insertImage',location.origin + '/myresource/resources/images/'+ url, '')
		},
		error : function(error) {
			console.log(error);
		}
	});
}

function deleteFile(filename){
	alert(filename);
	$.ajax({
		data : filename,
		type : "POST",
		url : "upload/delete.do",
		success : function() {
			console.log('success');
		},
		error : function(error) {
			console.log(error);
		}
	})
}