
console.log( 'search JS 실행' )
// 현재 눈에 보이는 페이지에 해당하는 키워드 잠깐 담아두기 위한 전역 변수
let keywordNow = '';

// 헤더에서 시작하는 검색 | 초기에 넘어온 값으로 출력시작 되어야 함 --------------------------------------------
let urlParams = new URL(location.href).searchParams;
let keywordHeader = urlParams.get("keyword")
console.log("keyword : "+keywordHeader)
onSearchStartInPage( keywordHeader )

// 엔터로 검색이 되어야 함 ----------------------------------------------------------------------------- 
function enterInPage(){
	//console.log( 'enterInPage() 실행' )
	let keywordInPage = document.querySelector('.keywordInPage');
	if( event.keyCode == 13 ){
	onSearchStartInPage( keywordInPage.value );
	return;
	}
}
// 정규식으로 유효성 검사하기 ---------------------------------------------------------------------------
function onSearchStartInPage( keywordInPage ){
	//console.log( 'onSearchStartInPage() 실행' )
	if( keywordInPage == '' || /^[가-힣a-zA-Z0-9!@#$%^&*]{0,30}$/.test(keywordInPage.value) == false){
		alert('검색 키워드를 올바르게 입력해주세요.')
		keywordInPage = '';
	} else {
		// 검색창 공백으로 바꾸고 키워드 전달하기
		getSearchList(keywordInPage);
		keywordInPage = '';
	}
}


let arrayOfObjects = [];
// 검색결과 출력하기 ------------------------------------------------------------------
function getSearchList( keyword ){
	console.log( 'getSearchList() 실행' )
	console.log( 'keyword : ' +keyword )
	keywordNow = keyword; // 현재 눈에 보이는 페이지에 해당하는 키워드 잠깐 담아두기
	let TOPIC = document.querySelector('.printContTOPIC');  printnothingHTML(TOPIC);
	let QnA = document.querySelector('.printContQnA'); 		printnothingHTML(QnA);
	let Info = document.querySelector('.printContInfo'); 	printnothingHTML(Info);
	let Commu = document.querySelector('.printContCommu');  printnothingHTML(Commu);
	let printContG = document.querySelector('.printContG'); printnothingHTML(printContG);
		
	// 키워드 받아온 것 확인되면 서블릿과 통신하기 ( 키워드와 타입 전달 )
	$.ajax({
		url : "/TryCatch/SearchController",
		method : "get",
		data : { keyword : keyword } ,
		success : function f(r){
			//console.log("통신성공 : "+r)
			//console.log("r.length : "+ r.length)
			
			arrayOfObjects = r;
			
			// 배열 내의 각 객체의 bcontent 속성에서 이미지 태그 삭제
	        arrayOfObjects.forEach((obj) => {
	            obj.bcontent = removeImageTagsFromContent(obj.bcontent);
	            obj.bcontent = removeBRTagsFromContent(obj.bcontent);
	        });
	
	        // 결과 확인
	        console.log('arrayOfObjects: ', arrayOfObjects);
			
			
			
			let searchInputBox = document.querySelector('.searchInputBox');
			searchInputBox.innerHTML =`
			<img src ="/TryCatch/img/돋보기.PNG" />
			<input onkeydown="enterInPage()" class="keyword keywordInPage" type="text" placeholder="${keyword}">
			`;
			let searchCount = document.querySelector('.searchCount');
			searchCount.innerHTML =`
			<span class="searchKeywordPrint"> </span> 통합검색 결과 <span class="searchCountPrint"> ${r.length}</span>건
			`;
			if ( keyword != null ){
				let HTMLTOPIC = ``; let HTMLQnA = ``; let HTMLInfo = ``; let HTMLCommu = ``; let HTMLprintContG = ``;
				r.forEach( (b) => {
					let s = b.subcno;
					// 출력하기
					if( s == 1 || s == 2 || s == 3){ // TOPIC
						HTMLTOPIC += printHTML(b); 
						TOPIC.innerHTML = HTMLTOPIC;
					} else if( s == 6 ){ // Q&A
						HTMLQnA += printHTML(b); 
						QnA.innerHTML = HTMLQnA;
					} else if( s == 7 ){ // 정보마당
						HTMLInfo += printHTML(b); 
						Info.innerHTML = HTMLInfo;
					} else if( s == 4 || s == 5 ){ // 커뮤니티
						HTMLCommu += printHTML(b); 
						Commu.innerHTML = HTMLCommu;
					} else {
						HTMLprintContG += printHTML(b); 
						printContG.innerHTML = HTMLprintContG;
					}
				})
			}
		} 
	})
}
// HTML ----------------------------------------------------------------------------------------------
function printHTML(b){
	let s = b.subcno;
	return `
	<ul class="ContUI">
		<li class="bottomCont"> 
			<img src ="/TryCatch/memberimg/${b.mimg}" />
			<div class="contText">
				<div><a class="contTextA" href="/TryCatch/jsp/boardView.jsp?bno=${b.bno}"> ${b.btitle} </a></div>
				<div class="contTextP"> ${b.bcontent}</div>
				<div class="contLikeBox"> 작성자 <span class="contWriter"> ${b.mname} </span> | 조회수 <span class="contLike"> ${b.bview} </span> | <span class="contDate"> ${b.bdate} </span></div>
			</div>
		</li>
	</ul>	
	`;
}
function printnothingHTML(Dom){
	Dom.innerHTML =`<p class="nothing">검색 결과가 없습니다.</p>`;
}

// 더보기 버튼을 눌렀을때 -------------------------------------------------------------
function detailview(cno){
	// 게시판으로 이동하기 
	// 그런데 검색된 결과물들을 출력하던 상태였으니 키워드도 들고가야함
	location.href='/TryCatch/jsp/board.jsp?cno='+cno+'&keyword='+keywordNow+'&key=bAll';

}

// 이미지 태그 삭제 함수
function removeImageTagsFromContent(content) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    
    // 이미지 태그 삭제
    const imgElements = doc.querySelectorAll('img');
    imgElements.forEach((img) => {
        img.remove();
    });
	console.log('imgElements : '+imgElements);
    // 수정된 내용 반환
    return doc.body.innerHTML;
}

// 줄바꿈 태그 삭제 함수
function removeBRTagsFromContent(content) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    
    // 이미지 태그 삭제
    const imgElements = doc.querySelectorAll('br');
    imgElements.forEach((img) => {
        img.remove();
    });
	console.log('imgElements : '+imgElements);
    // 수정된 내용 반환
    return doc.body.innerHTML;
}
		