// 모달 열 때 공백 대입
function openLogin(){
	document.querySelector('.loginEmail').value = ``;
	document.querySelector('.loginPwd').value = ``;
}
// 로그인
function login(){
	let loginEmail = document.querySelector('.loginEmail').value;
	let loginPwd = document.querySelector('.loginPwd').value;
	if( loginEmail == '') return alert('이메일을 입력해주세요.')
	if( loginPwd == '') return alert('비밀번호를 입력해주세요.')
	
	$.ajax({
		url:"/TryCatch/MemberController",
		data:{type: 'login',loginEmail:loginEmail , loginPwd : loginPwd},
		method:"post",
		success:r=>{console.log(r)
			if( r )
				location.href = "/TryCatch/jsp/main.jsp"
			else
				alert('일치하는 정보가 없습니다.');
		},
		error:e=>{alert('일시적인 오류가 발생했습니다. 잠시후 이용해주세요.')}		
	});
}
// 비밀번호 찾기
function findPassword(){
	
	let email = document.querySelector('.findPasswordEmail').value;	
	if( email == '' ) return alert('이메일을 입력해주세요.')
	$.ajax({
		url:"/TryCatch/MemberController",
		data:{type: 'findPassword',email:email},
		method:"get",
		success:r=>{console.log(r)
			if( r == "notFound" ){
				alert('존재하지 않는 이메일입니다.')
			}else{
				document.querySelector('.findPasswordBox').innerHTML =
					`
					<div class="col d-flex flex-column signInput">
						<span class="forFindPasswordEmail">password found</span>
						<div class="foundPassword">${r}</div>	
					</div>
					`
			}
		},
		error:e=>{alert('일시적인 오류가 발생했습니다. 잠시후 이용해주세요.')}	
	})
}
// 비밀번호 찾기 창 초기화 함수
function findPasswordReset(){
	document.querySelector('.findPasswordBox').innerHTML =
														`	
												     		<div class="col d-flex flex-column signInput">
												     			<span class="forFindPasswordEmail">Email</span>		     		
												     			<input class="findPasswordEmail" type="text" placeholder=""/>		     	
												     		</div>
												     		<div class="col d-flex flex-column sighUpBtnBox">
												     			<button onclick="findPassword()"type="button">find</button>
												     		</div>
												     	`
}
