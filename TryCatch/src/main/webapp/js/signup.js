// 회원가입 모달 열 때 공백 대입
function openSignUp(){
	document.querySelector('.signUpName').value = ``;
	document.querySelector('.signUpEmail').value = ``;
	document.querySelector('.signUpPassword').value = ``;
	document.querySelector('.signUpConfirm').value = ``;
}
// 회원가입
function signUp(){
	let name = document.querySelector('.signUpName').value;
	let email = document.querySelector('.signUpEmail').value;
	let pwd = document.querySelector('.signUpPassword').value;
	let confirmPwd = document.querySelector('.signUpConfirm').value;
	// 유효성 검사 false 시 return 	
	if(!checkSighUp( name , email , pwd, confirmPwd )) return;
	
	$.ajax({
		url : "/TryCatch/MemberController",
		method : "post",
		data : {type:'signUp',name : name,email:email,pwd:pwd},
		async: false , 
		success : r => {
			console.log(r)
			if(r){
				alert('회원가입 완료되었습니다.')
				location.href = '/TryCatch/jsp/main.jsp'
			}else
				alert('회원가입 실패')										
		},												
		error : e => {alert('일시적인 에러가 발생했습니다. 잠시후 다시 시도해주세요.')}
	})
}
/*회원가입 유효성 검사*/
function checkSighUp( name , email , pwd, confirmPwd){
	let signUpCheckName = document.querySelector('.signUpCheckName');let signUpCheckEmail = document.querySelector('.signUpCheckEmail');
	let signUpCheckPwd = document.querySelector('.signUpCheckPwd');let signUpCheckConfirm = document.querySelector('.signUpCheckConfirm');
	signUpCheckName.innerHTML = ``;signUpCheckEmail.innerHTML = ``;
	signUpCheckConfirm.innerHTML = ``;signUpCheckPwd.innerHTML = ``;	
	// 아이디, 이메일 중복 검사 ajax
	$.ajax({
		url : "/TryCatch/MemberController",
		method : "get",
		data : {type: 'checkIdAndEmail',name : name,email:email},
		async: false , 
		success : r => {
			if(r == "existsName"){
				signUpCheckName.innerHTML = `이미 존재하는 이름입니다.`
				return false;
			}					
			else if( r == "existsEmail"){
				signUpCheckEmail.innerHTML = `이미 존재하는 이메일입니다.`
				return false;
			}
		},												
		error : e => {alert('일시적인 에러가 발생했습니다. 잠시후 다시 시도해주세요.')}						
	});
	
	// 이메일 정규식 검사
	if(!(/^[a-zA-Z0-9_-]+@[a-zA-Z\d_-]+\.[a-zA-Z]+$/).test( email )){
		signUpCheckEmail.innerHTML = `올바른 이메일 형식이 아닙니다.`
		return false;
	}
	// 비밀번호 자리수 검사 6, 최소 6자리 이상
	if(!(/^[a-zA-Z\d]{6,}$/).test(pwd)){
		signUpCheckPwd.innerHTML = `비밀번호는 최소 6자리 이상이어야합니다.`
		return false;
	}
	// 비밀번호 확인 검사
	if( pwd != confirmPwd ){
		signUpCheckConfirm.innerHTML = `맞지 않는 비밀번호입니다.`
		return false;
	}
	//결과 반환
	return true		
}// 회원가입 유효성 검사 끝