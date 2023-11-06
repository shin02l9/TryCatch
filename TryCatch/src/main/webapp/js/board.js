console.log( 'board.js 열렸다.' )

let listLimit = 10;

let urlParams = new URL(location.href).searchParams;

let cno = urlParams.get("cno")

let fsubcno = 0;

let key = urlParams.get("key");

let keyword = urlParams.get("keyword");
console.log("keyword : "+keyword);


// 검색기능 (예지가 만짐) -------------------------------------------
// 검색창에서 엔터키를 눌렀을때
function enterBoard(){
	console.log(' enterBoard() ')
	if( event.keyCode == 13 ){
		onSearchBoard()
		return;
	}
}
// 검색버튼을 클릭 했을때
function onSearchBoard(){
	console.log(' onSearchBoard() ')
	let keyBoardList = document.querySelector('.keyBoardList').value;
	let keywordBoardList = document.querySelector('.keywordBoardList').value;
	console.log('검색 클릭시 key : '+keyBoardList)
	key = keyBoardList;
	console.log('검색 클릭시 keyword : '+keywordBoardList)
	keyword = keywordBoardList;
	// 검색 끝난 후에 검색창 공백으로 바꾸기
	document.querySelector('.keywordBoardList').value = '';
	
	getSubCategory(cno)
}

getSubCategory(cno)

function getSubCategory(cno){
	
	console.log('getSubCategory 실행')
	
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "get",
	async : false ,
	data : { type : 0 , cno : cno} ,
	success : r => {
		console.log(r);
		console.log("r.subcno[0] : "+r[0].subcno);
		
		fsubcno = r[0].subcno;
		
		cno == 5 ? fsubcno = -1 : fsubcno = r[0].subcno;
		
		console.log(urlParams);
		
		let subcate = document.querySelector('.subcate');
		let html = ``;
		
		cno == 5 ? html += `
							<button  onclick="ctBoardView( 1 , -1 )" type="button"> 
								잡담 & 스터디 
							</button>` : '' 
				
		r.forEach( (c) =>{
			console.log("c.subcno : "+c.subcno)
			
			html += `
					<button onclick="ctBoardView( 1 , ${c.subcno} )" type="button">
						${c.subcname}
					</button>
					 `
		} )
		subcate.innerHTML = html;
		
		ctBoardView( 1 , cno != 5 ? r[0].subcno : -1 );
	} ,
	error : e =>{
		console.log(e);
	}
	})	
	
}


//글쓰기 페이지로 이동
function moveWrite(){
	
	if( loginState == false ){
		alert('로그인 된 회원만 작성 할 수 있습니다.')
		return;
	}
	
	location.href ="/TryCatch/jsp/boardWrite.jsp"
	
}

/*
	type 1 카테고리별 전체 글 출력
	type 2 개별 글 출력
	type 3 댓글 출력
*/


//카테고리별 게시물 출력
function ctBoardView( page , subcno ){
	
	console.log( 'ctBoardView() 실행' )	
	console.log( 'page 는 '+ page)
					//x , y
	window.scrollTo( 0 , 0 );
	
	fsubcno = subcno;
	
	$.ajax({
	url : "/TryCatch/BoardController" ,
	method : "get" ,
	data : { type : 1 ,
			 subcno : subcno ,
			 listLimit : listLimit ,
			 page : page,
			 key : key,
			 keyword : keyword
			} ,
	success : r => {
		
		console.log('boardView 통신 성공')
		
		console.log(r);
		
		let contentBody = document.querySelector('.contentBody');
		let html = ``;
		
		$.ajax({
		url : "/TryCatch/BoardController",
		method : "get",
		data : {type : 5 , subcno : 9} ,
		success :n => { console.log(n);
			
			n.forEach( b =>{
			
			html +=`
					<tr class="contentTr notice">
						<tr class="conTop notice">
							<td>no.${b.bno}</td>
							<td>${b.subcname}</td>
						</tr>
						<tr class="conTop notice">
							<td style="color:${b.mcolor}">
								${b.mname}
							</td>
							<td>
								<img class="iconImg" src="/TryCatch/img/boomupicon.png">${b.blike}
							</td>
							<td>
								<img class="iconImg" src="/TryCatch/img/viewicon.png">${b.bview}
							</td>
						</tr>
						<tr class="conTitle notice">
							<th>
								<a href="/TryCatch/jsp/boardView.jsp?cno=${cno}&subcno=${subcno}&bno=${b.bno}">
									${b.btitle}
								</a>
							</th>
						</tr>
						<tr class="concont notice">
							<td class="conIn">
								${b.bcontent}
							</td>
							<td>
								${b.bdate}
							</td>
						</tr>
					</tr>
					`
		})//forEach end
		contentBody.innerHTML = html;

		r.boardList.forEach( b =>{
			
			html +=`
					<tr class="contentTr">
						<tr class="conTop">
							<td>no.${b.bno}</td><td>${b.subcname}</td>
						</tr>
						<tr class="conTop">
							<td style="color:${b.mcolor}">${b.mname}</td>
							<td><img class="iconImg" src="/TryCatch/img/boomupicon.png">${b.blike}</td>
							<td><img class="iconImg" src="/TryCatch/img/viewicon.png">${b.bview}</td>
						</tr>
						<tr class="conTitle">
							<th><a href="/TryCatch/jsp/boardView.jsp?cno=${cno}&subcno=${subcno}&bno=${b.bno}">${b.btitle}</a></th>
						</tr>
						<tr class="concont">
							<td class="conIn">${b.bcontent}</td><td>${b.bdate}</td>
						</tr>
					</tr>
					`
		})//forEach end

		
		contentBody.innerHTML = html;
		
		html = ``;
		
		html +=` <button onclick ="ctBoardView( ${ page <= 1 ? page : page-1 } , ${fsubcno} )" type="button" > < </button> `;
		
		for( let i = r.startBtn; i<=r.endBtn; i ++){
			html += `<button onclick="ctBoardView( ${ i } , ${fsubcno} )" class="${ page == i ? 'selectpage' : ''}" type="button" > ${i} </button>`;
		}
		
		html += `<button onclick ="ctBoardView( ${ page >= r.totalpage ? page : page+1 } , ${fsubcno} )" type="button"> > </button>`;
		
		document.querySelector('.pageBox').innerHTML = html;
		
	} ,
	error : e =>{
		console.log(e)
	}
	})
	} ,
		error : e =>{}
		})
	key = '';
	keyword = '';
		
}//ctBoardView() end

//출력수 선택
function selectLimit(){
	console.log('seselectLimit 실행');
	
	listLimit = document.querySelector('.liMit').value;
	console.log(listLimit);
	ctBoardView(1 , fsubcno);
}





