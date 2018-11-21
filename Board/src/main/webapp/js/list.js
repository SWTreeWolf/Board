$(document).ready(function(){
    $('#delete').click(function(){
        if(!$('.table > thead > tr > td:last-of-type').length){
            $('.table > thead > tr').append('<td></td>');
            for(var i=1; i<11; i++){
                var num = parseInt($('.table > tbody > tr:nth-of-type('+(i)+') > th:first-of-type').text());
                $('.table > tbody > tr:nth-of-type('+(i)+')').append('<td><input type="checkbox" value='+(num)+'></td>');
            }
        }else if($('.table > tbody > tr > td:last-of-type > input').is(":checked")){
            $('input:checked').each(function() {
                $('.table > tbody > tr[id='+this.value+']').remove();
                remove(this.value);
            });
            $('.table > thead > tr > td:last-of-type').remove();
            $('.table > tbody > tr > td:last-of-type').remove();
        }else{
            $('.table > thead > tr > td:last-of-type').remove();
            $('.table > tbody > tr > td:last-of-type').remove();
        }
        return false;
    });
})

function remove(index){
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
}