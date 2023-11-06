myProfile()
// 페이지 열릴 때 랜더링 함수
function myProfile(){
	let mname = document.querySelector('.mname')
	let mgrade= document.querySelector('.mgrade')
	let mexp = document.querySelector('.mexp')
	let mpoint = document.querySelector('.mpoint')
	let nowProfileImg = document.querySelector('.nowProfileImg')
	$.ajax({
			url : "/TryCatch/MemberController" ,
			method : "get" ,
			async : false,	
			data : {type : 'info'},
			success : r=> {
				mname.value = r.mname;
				mexp.innerHTML = r.mexp;
				mpoint.innerHTML = r.mpoint
				mgrade.innerHTML = calGrade(r.mexp)
				nowProfileImg.src = `/TryCatch/memberimg/${r.mimg}`
			},
			error : e => {console.log(e)}	
		});		
}
// 등급 계산 함수
function calGrade( mexp ){
	let grade = ``
	if(  100 > mexp )
		grade = `bit`
	else if( 1000 > mexp )
		grade = `byte`
	else if( 2000 > mexp )
		grade = `kB` 
	else if( 3000 > mexp )
		grade = `MB`
	else if( 4000 > mexp )
		grade = `GB`
	else
		grade = `TB`		
	return grade;
}
// 프로필 이미지 변경 시 미리보기 변경
function fileImgChange( object ){
	let file = new FileReader();
	file.readAsDataURL( object.files[0]);
	file.onload = e=>{document.querySelector('.nowProfileImg').src = e.target.result;}	
}
// 계정탈퇴
function profileDelete(){
	if(!confirm('정말 탈퇴하시겠습니까?'))return;

	$.ajax({
		url:"/TryCatch/MemberProfileController",
		method:"delete",
		data: {},
		success: r=>{console.log(r)
			if(r){ 
				alert('회원 탈퇴가 완료되었습니다.');
				location.href = "/TryCatch/jsp/main.jsp"
			}else{alert('회원 탈퇴 실패.')}		
		},
		error : e=>{console.log(e)}
	})
}
// 프로필 수정 
function profileEdit(){
	let profileForm = document.querySelectorAll('.profileForm')[0]
	
	if((/^(?=.*[!@#$%^&*])/).test( profileForm.mname.value ))return alert('이름 수정 시 특수문자는 불가능합니다.')		
	if(profileForm.mname.value.length > 8 || 2 > profileForm.mname.value.length )return alert('이름의 길이는 2글자 이상 8글자 이하여야합니다.')		
	if(!(/^[a-zA-Z가-힣0-9]{1,8}$/))return alert('잘못된 입력입니다.')
		
			
	$.ajax({
		url : "/TryCatch/MemberProfileController",
		method : "post" ,		// form 객체 [대용량] 전송은 무조건 post
		data : new FormData( profileForm ),
		contentType : false ,
		processData : false,
		success : r =>{ 
			if( r == 0 ) 		alert('수정이 실패하였습니다. 관리자에게 문의해주시기 바랍니다.')
			else if( r == 1) 	alert('이미 사용 중인 이름입니다.')
			else if( r == 2) 	alert('수정 내용이 현재 정보와 일치합니다.')
			else if( r == 3){	alert('정보가 수정되었습니다.'); location.reload()	}																							
		},
		error : r=>{ console.log(r) }
	});
}





