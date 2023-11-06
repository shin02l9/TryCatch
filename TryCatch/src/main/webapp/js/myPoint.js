
getMyPoint( 1 );
function getMyPoint( page ){
	
	$.ajax({
		url:"/TryCatch/MemberController",
		method: "get",
		data : {type:"getPointLog",page:page},
		success: pageDto => {console.log(pageDto); console.log(pageDto.pointList)
			let html = ``;
			pageDto.pointList.forEach( (r,i) => {
				html += `			
						<div class="ContentBox">
							<div>${i+pageDto.startrow+1}</div>
							<div class="pointContent">${r.pcontent}</div>
							<div class="pointValue">${r.pcount}p</div>
							<div class="pointDate">${r.pdate}</div>
						</div>`		
			})		
			
			document.querySelector('.pointContentWrap').innerHTML = html;
			
			html = ``;
			
			html += `<button onclick="getMyPoint(${ page <= 1 ? page : page-1})" type="button"> &lt; </button>`
			
			for( let i = pageDto.startBtn; i <= pageDto.totalpage; i++){
				html += `<button class="${page == i ? 'selectpage' : '' }" onclick="getMyPoint(${i})">${i}</button>`
			} 
						
			html += `<button onclick="getMyPoint(${ page >= pageDto.totalpage ? page : page+1 })" type="button"> &gt; </button>`
			
			document.querySelector('.pageBtnWrap').innerHTML = html;		
		},
		error: e=>{console.log(e)}	
	})	
}