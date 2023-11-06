

function gotoQna(){
	location.href='/TryCatch/jsp/board.jsp?cno=2';
}
function gotoChat(){
	location.href='/TryCatch/jsp/chatting.jsp';
}

function gotoBoard(bno){
	location.href='/TryCatch/jsp/boardView.jsp?bno='+bno;
	console.log('bno : '+bno)
}

let arrayOfObjects = [];

printHot()
function printHot(){
	console.log('메인실행')
	
	$.ajax({
		url : "/TryCatch/MainHottopicController",
		method : "get",
		success : function f(r){
			console.log(r)
			console.log('메인실행')
			arrayOfObjects = r;
			
			// 배열 내의 각 객체의 bcontent 속성에서 이미지 태그 삭제
	        arrayOfObjects.forEach((obj) => {
	            obj.bcontent = removeImageTagsFromContent(obj.bcontent);
	            obj.bcontent = removeBRTagsFromContent(obj.bcontent);
	        });
	
	        // 결과 확인
	        console.log('arrayOfObjects: ', arrayOfObjects);
			
			
			
			let maintable = document.querySelector('.maintable');
				let HTML = `
				<tr> <!-- 1행 -->
					<th style="width: 20%;" class="w-25"> 제목 </th> 
					<th style="width: 40%;" class="w-25"> 내용 </th> 
					<th style="width: 10%;" class="w-25"> 작성자 </th> 
					<th style="width: 10%;" class="w-25"> 조회수 </th>
				</tr>
				`;
			for( let i = 0; i<r.length; i++ ){

				HTML += `
				<tr class="mainlist"> 
					<td style="width: 20%;" onclick="gotoBoard(${r[i].bno})" class=""> ${r[i].btitle} </td>
					<td style="width: 40%;" onclick="gotoBoard(${r[i].bno})" class="maintablebcontent"> ${r[i].bcontent}  </td>  
					<td style="width: 10%;" onclick="gotoBoard(${r[i].bno})" class=""> ${r[i].mname}  </td>
					<td style="width: 10%;" onclick="gotoBoard(${r[i].bno})" class=""> ${r[i].bview}  </td>
				</tr>
				`;
			}
				maintable.innerHTML = HTML;
			
		}, error : e=> { console.log(e)}
	})
	
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

