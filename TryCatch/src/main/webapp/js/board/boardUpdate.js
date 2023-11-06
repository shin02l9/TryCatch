
console.log("boardUpdate 들어왔다")

$(document).ready(function() {
  // $('#summernote').summernote( {설정객체} );
  $('#summernote').summernote( { 
	lang : 'ko-KR', // 한글 적용 [ 한글.JS CDN 필요]  
	height : 500 , 
	placeholder : '남을 비방하는 글은 사전 안내 없이 삭제될수 있음을 알려드립니다.'
  });
});

let urlParams = new URL(location.href).searchParams;
let bno = urlParams.get("bno");

getBoardInfo()
function getBoardInfo(){
	
	let subcno = document.querySelector('.categorySelect');
	let btitle =  document.querySelector('.btitle');
	let bcontent = document.querySelector('#summernote');
	let writerBox = document.querySelector('.writerBox');
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "get",
	async : false ,
	data : {type : 2 , bno : bno} ,
	success : r => {
		console.log("contentView : "+r.bDto.btitle);
		writerBox.innerHTML = `${r.bDto.mname} 님 TryCatch 에서 즐거운 커뮤니티 활동이 되길 try 합니다.
`
		btitle.value = r.bDto.btitle;
		bcontent.innerHTML = r.bDto.bcontent;
		
	} ,
	error : e =>{
		console.log(e)
	}
	})
	
}

function onUpdate(){
	
	console.log('onUpdate 실행')
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
						type : "onUpdate" ,
						bno : bno ,
						subcno : subcno ,
						btitle : btitle ,
						bcontent : bcontent ,
						};
	
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "put",
	data : boardObject ,
	success : r => {
		console.log(r);
		alert('글 수정 성공했습니다.')
		location.href =`/TryCatch/jsp/boardView.jsp?subcno=${subcno}&bno=${bno}`;
	} ,
	error : e =>{
		console.log(e);
	}
	})
}
		