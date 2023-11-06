console.log('boardView js 열림');

let urlParams = new URL(location.href).searchParams;

let cno = urlParams.get("cno")
console.log('cno : ' + cno)
let subcno = urlParams.get("subcno");
console.log('subcno : ' + subcno)

let bno = urlParams.get("bno");
console.log('bno : ' + bno)

contentView( bno );
function contentView( bno ){
	
	console.log('contentView 실행')
	
	console.log('bno : ' + bno);
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "get",
	async : false ,
	data : {type : 2 , bno : bno} ,
	success : r => {
		console.log("contentView : "+r);
		
		let conTitle = document.querySelector('.conTitle');
		let conWriterBox = document.querySelector('.conWriterBox');
		let writeTime = document.querySelector('.writeTime');
		let bornCon = document.querySelector('.bornCon');
 		let btnBox = document.querySelector('.btnBox');
 		let writeReview = document.querySelector('.writeReview');
 		
 		conTitle.innerHTML = r.bDto.btitle;
 		
 		// r.bDto.count == mexp -> 타입 똑같아서 생성자에서 필드만 빌려옴
 		conWriterBox.innerHTML = `
 									<div class="memberImg">
										<img src="/TryCatch/memberimg/${r.bDto.mimg}">
									</div>
									<div class="expImg">
										<img src="/TryCatch/img/
														${r.bDto.count >=3000 ? 'gb.png' :
																r.bDto.count >=2000 ? 'mb.png' : 
																	r.bDto.count >=1000 ? 'kb.png' :
																		r.bDto.count >=100 ? 'byte.png' : 'bit.png'}"> 
									</div>
 									<div class="writerName" style="color:${r.bDto.mcolor}">
										${r.bDto.mname}
									</div>
									<div class="iconImg">
										<img src="/TryCatch/img/boomupicon.png">
										<span class="blike"> ${r.blike}</span> 
									</div>
									<div class="iconImg blike" >
										<img src="/TryCatch/img/viewicon.png"> ${r.bDto.bview}
									</div>
									${ loginName == r.bDto.mname ?
									`<div onclick="boardUpdate(${r.bDto.bno})" class="iconImg">
										<img class="hoverIconBtn" src="/TryCatch/img/repairicon.png">
										<span>수정</span> 
									</div>
									<div onclick="boardDelete(${r.bDto.bno})" class="iconImg">
										<img class="hoverIconBtn" src="/TryCatch/img/deleteicon.png">
										<span>삭제</span> 
									</div>
									` : ''
									}
 								 `;
		
		writeTime.innerHTML = r.bDto.bdate;
		
		bornCon.innerHTML = r.bDto.bcontent
		
		btnBox.innerHTML = `
							<button onclick="boomUp(${bno})" type="button">
								<img class="boomupImg" src="/TryCatch/img/boomupicon.png">
							</button>
							`;
		writeReview.innerHTML = `
								리뷰를 달아주세요!
								<textarea class="reviewArea" placeholder="매너있는 댓글은 작성자에게 힘이 됩니다."></textarea>
								<button onclick="reviewWrite(${bno} , ${0})" class="reviewBtn" type="button">등록</button>
								`;
		getReview(bno);
		
	} ,
	error : e =>{
		console.log(e)
	}
	})

}//contentView end

function boardUpdate(boardbno){

	location.href = `/TryCatch/jsp/boardUpdate.jsp?subcno=${subcno}&bno=${boardbno}`
	
}

function boardDelete(boardbno){
	
	console.log('boardDelete 클릭 : '+boardbno)
	let confirmValue = confirm('정말로 삭제하시겠습니까?');
	
	if( confirmValue == true ){
		console.log( '확인눌렀다')
		
		$.ajax({
		url : "/TryCatch/BoardController",
		method : "delete",
		data : { type : "boardDelete" , bno : boardbno } ,
		success : r => {
			console.log(r);
			
			alert('게시글 삭제 성공했습니다.\n글 목록으로 이동합니다.')
			
			location.href = `/TryCatch/jsp/board.jsp?cno=${cno}`;
			
		} ,
		error : e =>{
			console.log(e);
		}
		})
		
	}
	else {
		return;
	}
}

function boomUp( bno ){
	
	if( loginState == false ){
		alert('로그인 된 회원만 추천 할 수 있습니다.')
		return;
	}
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "post",
	data : { type : "boomup" , bno : bno } ,
	success : r => {
		console.log(r);
		
		let blike = document.querySelector('.blike');
		blike.innerHTML = r;
		
	} ,
	error : e =>{}
	})
	
}

function getReview( bno ){
	
	console.log('reviewWrite 실행')
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "get",
	data : { type : 3 , bno : bno } ,
	success : r => {
		console.log(r);
		
		let reviewView = document.querySelector('.reviewView');
		let html1 = ``;

		r.forEach( (re , i) => {
			
			if(re.reindex == 0){
			
				html1 +=`<div class="reconBox">
							<div class="rewriter">
								<span class="memberImg">
									<img src="/TryCatch/memberimg/${re.mimg}">
								</span>
								<span class="reExpImg">
									<img src="/TryCatch/img/${re.mexp >=3000 ? 'gb.png' :
																re.mexp >=2000 ? 'mb.png' : 
																	re.mexp >=1000 ? 'kb.png' :
																		re.mexp >=100 ? 'byte.png' : 'bit.png'}">
								</span>
								<span style = "color:${re.mcolor}">${re.mname}</span>
								<div class="iconImg">
									<img class="hoverIconBtn" onclick="reviewUp(${re.reno})" src="/TryCatch/img/boomupicon.png">
									<span class="relike${re.reno}"> ${re.relike}</span> 
									${ loginName == re.mname ?
									`
										<img class="hoverIconBtn" onclick="getreUpdate(${re.reno} , ${re.reindex})" src="/TryCatch/img/repairicon.png">
										<span>수정</span> 
										<img class="hoverIconBtn" onclick="reviewDelete(${re.reno})" src="/TryCatch/img/deleteicon.png">
										<span>삭제</span> 
									` : ''
									}
								</div>
								 
								
							</div>
							<div class="reviewTime">
								${re.redate}
							</div>
							<div class="recon reindex${re.reno}">
								${re.recontent}
							</div>
							<div class="rerearea${[i]} rerebox rebtnBox${re.reno}">
								<button class="reviewBtn" onclick="reReview(${bno},${re.reno} , ${i})" type="button"> 답글달기 </button>
							</div>
							<div class="reReReviewArea${re.reno}">
							</div>
						</div>
						`
					
				
				
				
				
			}//if end
			
	
		})// forEach end
		
		reviewView.innerHTML = html1;
		
		
		r.forEach( (re) =>{
			
			if(re.reindex > 0 ){
					
					let reReReviewArea = document.querySelector(`.reReReviewArea${re.reindex}`);

					reReReviewArea.innerHTML +=`
												<div class="reReconBox">
													<div class="rewriter rerewriter">
														<span class="memberImg">
															<img src="/TryCatch/memberimg/${re.mimg}">
														</span>
														<span style="color:${re.mcolor}">${re.mname}</span>
														<span class="reExpImg">
															<img src="/TryCatch/img/${re.mexp >=3000 ? 'gb.png' :
																						re.mexp >=2000 ? 'mb.png' : 
																							re.mexp >=1000 ? 'kb.png' :
																								re.mexp >=100 ? 'byte.png' : 'bit.png'}">
														</span>
													<div class="iconImg">
														<img class="hoverIconBtn" onclick="reviewUp(${re.reno})" src="/TryCatch/img/boomupicon.png">
														<span class="relike${re.reno}"> ${re.relike}</span> 
													${ loginName == re.mname ?
													`
														<img class="hoverIconBtn" onclick="getreUpdate(${re.reno} , ${re.reindex})" src="/TryCatch/img/repairicon.png">
														<span>수정</span> 
														<img class="hoverIconBtn" onclick="reviewDelete(${re.reno})" src="/TryCatch/img/deleteicon.png">
														<span>삭제</span> 
													` : ''
													}
													</div>
													</div>
													<div class="reviewTime">
														${re.redate}
													</div>
													<div class="recon reindex${re.reno}">
														${re.recontent}
													</div>
													<div class="rebtnBox${re.reno}">
													</div>
												</div>
												`;
					
					
				
					
			}//if end
			
		} )
		
		
		
	} ,
	error : e =>{}
	})
	
}

function reviewWrite( bno , reindex){
	
	if( loginState == false ){
		alert('로그인 된 회원만 작성 할 수 있습니다.')
		return;
	}
	
	let reviewArea = document.querySelector('.reviewArea');
	
	if(reindex>0){
		reviewArea = document.querySelector('.rereviewArea');
	}
	
	if( reviewArea.value.length == 0 || reviewArea.value == ' ' ){
		alert("공백댓글은 게시할수 없습니다.")
		return;
	}	
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "post",
	data : { type : "reviewWrite" ,
			 bno : bno , 
			 recontent : reviewArea.value , 
			 reindex : reindex } ,
	success : r => {
		
		if( r == true ){
			reviewArea.value = ``;
			alert('리뷰가 작성되었습니다.');
			contentView( bno );
		}
		else{ alert("리뷰 등록 실패") }
		
	} ,
	error : e =>{
		console.log(e);
	}
	})
	
}
function reReview( bno , reindex , i ){
	
	if( loginState == false ){
		alert('로그인 된 회원만 작성 할 수 있습니다.')
		return;
	}
	
	let rerearea = document.querySelector(`.rerearea${[i]}`);
	rerearea.innerHTML = `
						<textarea class="rereviewArea" placeholder="매너있는 댓글로 소통해보세요!"></textarea>
						<button class="reviewBtn" onclick="reviewWrite(${bno},${reindex})" type="button">등록</button>
						`
}

function reviewUp(reno){
	
	if( loginState == false ){
		alert('로그인 된 회원만 추천 할 수 있습니다.')
		return;
	}
	
	$.ajax({
	url : "/TryCatch/BoardController",
	method : "post",
	data : { type : "reviewUp" , reno : reno } ,
	success : r => {
		console.log(r);
		
		let relike = document.querySelector(`.relike${reno}`);
		relike.innerHTML = r;
		
	} ,
	error : e =>{}
	})
	
}

function getreUpdate(reno , reindex){
	console.log('getReview 클릭 : '+reno)
	
	let reconBox = document.querySelector(`.reindex${reno}`);
	let rebtnBox = document.querySelector(`.rebtnBox${reno}`)
	
	$.ajax({
		url : "/TryCatch/BoardController",
		method : "get",
		data : { type : 4 , reno : reno } ,
		success : r => {
			console.log(r);
			
			reconBox.innerHTML = `
								<textarea class="reviewupArea">${r}</textarea>
								`
			rebtnBox.innerHTML = `
								<button class="reviewBtn" onclick="reviewUpdate(${reno},${reindex})" type="button">수정</button>
								`
		} ,
		error : e =>{
			console.log(e);
		}
		})
	
}

function reviewUpdate( reno , reindex ){
	
	let recontent = document.querySelector('.reviewupArea').value;
	
	$.ajax({
		url : "/TryCatch/BoardController",
		method : "put",
		data : { type : "reviewUpdate" , reno : reno , recontent : recontent } ,
		success : r => {
			console.log(r);
			
			if( r == true ){
				alert('리뷰 수정 되었습니다.');
				contentView( bno );
			}
			else{
				alert('리뷰 수정 실패했습니다.')
			}
		} ,
		error : e =>{
			console.log(e);
		}
		})
	
}

function reviewDelete(reno){
	
	console.log('reviewDelete 클릭 : '+reno)
	
	let confirmValue = confirm('정말로 삭제하시겠습니까?');
	
	if( confirmValue == true ){
		
		console.log( '확인눌렀다')
		
		$.ajax({
		url : "/TryCatch/BoardController",
		method : "delete",
		data : { type : "reviewDelete" , reno : reno } ,
		success : r => {
			console.log(r);
			
			if( r == true ){
				alert('댓글 삭제 성공했습니다.')
				
				contentView( bno );
			}
			else{
				alert('대댓글이 포함된 댓글은 삭제 할 수 없습니다.');
			}
			
		} ,
		error : e =>{
			console.log(e);
		}
		})
		
	}
	else {
		return;
	}
}