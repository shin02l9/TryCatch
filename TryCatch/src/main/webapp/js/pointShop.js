
printBtn();
// 버튼 랜더링
function printBtn(){
	
	// 1. 색 리스트 가져오기.
	$.ajax({
		url: "/TryCatch/PointShopController",
		method: "get",
		async: false ,
		data: {type:"getColorList"},
		success: colorArray => {console.log(colorArray)
		//2. 유저 구매 리스트 가져오기.
		$.ajax({
			url: "/TryCatch/PointShopController",
			method: "get",
			data: {type:"getBuyList"},
			success: buyArray =>{console.log(buyArray)
				let isEquipActive = false;				
				let html = ``; 
				colorArray.forEach( r => { 
					let hasColor = false; let nowEquip = false;
					// 구매 리스트에 존재하는지 확인
					for(let i = 0; i < buyArray.length; i++){
						if( r.pcno == buyArray[i].pcno ) {
							hasColor = true; 
							if( buyArray[i].pestate ) {nowEquip = true; isEquipActive = true; equipColor = r.pccode;}
							break;
						}													
					}
					// 데이터베이스에 등록 되어있는 상품 리스트 개수만큼 랜더링						
					html += `				
						<div class="colorBox ${nowEquip ? 'changeGray' : ''}">
							<div style=color:${r.pccode} class="colorName">${r.pcname}</div>
							<div class="colorPoint">${hasColor ? '-' : '1,000'}</div>
							<div class="buyButtonBox"><button onclick="${hasColor ? nowEquip ? 'alreadyEquip()' : `onEquip(${r.pcno})` : `onBuy(${r.pcno})` }" class="buyBtn ${nowEquip ? 'nowBtn' : hasColor ? 'equipBtn' : 'defaultBtn'}">${hasColor ? nowEquip ? 'Now' : 'Equip' : 'Buy'}</button></div>			
						</div>`			
				});
				html = `<div class="colorBox ${isEquipActive ? '' : 'changeGray'}">
								<div class="colorName">Default</div>
								<div class="colorPoint">-</div>
								<div class="buyButtonBox"><button onclick="${ isEquipActive ?  `onEquip(0)` : 'alreadyEquip()' }" class="buyBtn ${isEquipActive ? 'equipBtn' : 'nowBtn'}">${isEquipActive ? 'Equip' : 'Now'}</button></div>			
							</div>` + html;
				// 랜더링
				document.querySelector('.colorTable').innerHTML = html;
				// 만약 현재 장착된 컬러가 없으면 기본으로 변경
				if( !isEquipActive ) equipColor = `black`;
				console.log(equipColor)
			},// success e
			error: r=>{console.log(r)}
		});// 두번째 ajax 끝
	},// 첫번째 ajax success 끝		
	error : e => {console.log(e)}
	});
}
// 이미 장착함
function alreadyEquip(){
	console.log( '이미 장착된 상태' )
}
// 장착하기
function onEquip( pcno ){
	
	$.ajax({
		url : "/TryCatch/PointShopController",
		method : "put",
		data : { pcno: pcno },
		async : false , 
		success: r=>{
			if(r) printBtn();
			else if(!r) alert('적용 실패되었습니다.')
				
		},
		error: e => {console.log(e)}
	})
}
// 구매하기
function onBuy( pcno ){
	
	if( !confirm('구매하시겠습니까?') ) return;
	
	$.ajax({
		url : "/TryCatch/PointShopController",
		method : "post",
		data : { pcno: pcno },
		async : false , 
		success : r => {
			if( r == 0 )
				alert('포인트가 부족합니다.');
			else if(r == 1){
				alert('구매 완료되었습니다.');
				printBtn();
			}
			else if( r == 2)
				alert('구매 실패되었습니다.')					
		},
		error : e => {console.log(e)}	
	})	
}