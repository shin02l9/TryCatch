console.log('boardWrite.js 실행')

// 1. 썸머노트 실행할때 사용되는 코드 
$(document).ready(function() {
  // $('#summernote').summernote( {설정객체} );
  $('#summernote').summernote( { 
	lang : 'ko-KR', // 한글 적용 [ 한글.JS CDN 필요]  
	height : 500 , 
	placeholder : '남을 비방하는 글은 사전 안내 없이 삭제될수 있음을 알려드립니다.'
  });
});

function comWriter(){
	
	let writerBox = document.querySelector('.writerBox')
	
	writerBox.innerHTML = `
								<div>
									${loginName}님 TryCatch 에서 즐거운 커뮤니티 활동이 되길 try 합니다.
								</div>
								`
	
}


function onWrite(){
	
	console.log('onWrite 실행')
	let subcno = document.querySelector('.categorySelect').value
	let btitle =  document.querySelector('.btitle').value;
	let bcontent = document.querySelector('#summernote').value;
	
	
	if( btitle.length == 0 || btitle == ' '){
		alert("제목을 작성해주세요")
		return;
	}
	
	if( bcontent.length == 0 || bcontent == ' ' ){
		alert("공백은 게시할수 없습니다.")
		return;
	}	
	let boardObject = {
						type : "onWrite" ,
						subcno : subcno ,
						btitle : btitle ,
						bcontent : bcontent ,
						};
	
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "post",
	data : boardObject ,
	success : r => {
		console.log(r);
		alert('글 쓰기 성공했습니다.')
		location.href =`/TryCatch/jsp/main.jsp`;
	} ,
	error : e =>{
		console.log(e);
	}
	})
			
	


}