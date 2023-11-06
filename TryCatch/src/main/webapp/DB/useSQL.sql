drop database if exists howlong;
create database howlong;
use howlong;

# 회원
drop table if exists member;
create table member(
	mno int auto_increment,
	mname varchar(10) not null unique,
	mpwd varchar(15) not null,
	memail varchar(30) not null unique, 
	mimg longtext default 'default.webp' not null,
	mexp int default 0,
	primary key(mno)
);

# 메인 카테고리
drop table if exists maincategory;
create table maincategory(
	cno int auto_increment,
	cname varchar(20) not null unique,
	primary key(cno)
);

# 서브 카테고리
drop table if exists subcategory;
create table subcategory(
	cno int,
	subcno int auto_increment,
	subcname varchar(20) not null unique,
	primary key(subcno),
	foreign key (cno) references maincategory(cno) on delete cascade on update cascade
);

# 게시글
drop table if exists board;
create table board(
	bno int auto_increment,
	btitle varchar(30) not null,
	bcontent longtext,
	bdate datetime default now(),
	bview int default 0,
	blike int default 0,
	mno int,
	subcno int,
	primary key(bno),
	foreign key(mno) references member(mno) on delete cascade on update cascade,
	foreign key(subcno) references subcategory(subcno) on delete cascade on update cascade
);

# 해시태그
drop table if exists tag;
create table tag(
	tno int auto_increment,
    bno int,
    tname varchar(100),
    primary key(tno),
    foreign key(bno) references board(bno) on delete cascade on update cascade
);

# 댓글
drop table if exists review;
create table review(
    reno int auto_increment,
    recontent longtext,
    reindex int not null,
    redate datetime default now(),
    relike int default 0,
    bno int,
    mno int,
    primary key(reno),
    foreign key(bno) references board(bno) on delete cascade on update cascade,
    foreign key(mno) references member(mno) on delete cascade on update cascade
);

# 포인트
drop table if exists point;
create table point(
	pno int auto_increment,
    pdate datetime default now(),
    pcontent longtext,
    pcount int not null,
	mno int,
	primary key(pno),
	foreign key(mno) references member(mno) on delete cascade on update cascade
);
# 채팅방
create table chatroom(
						crno int auto_increment , # 방번호 pk
                        crtitle varchar(30) not null , # 방제
                        crcdate datetime default now() , # 방만든 시간
                        crddate datetime , # 방 삭제 시간
                        mno int , # 방만든 사람
						primary key(crno) , 
                        foreign key(mno) references member(mno) on delete cascade on update cascade
                        );
# 메세지 테이블
create table chmessage(
						cmno int auto_increment , # 메세지번호
                        cmessage longtext not null , #  메세지 내용
                        cmdate datetime default now() , # 메세지 보내 시간
                        crno int , # 방번호 fk
                        mno int , # 메세지 보낸 사람
                        primary key(cmno) ,
                        foreign key(mno) references member(mno) on delete cascade on update cascade ,
                        foreign key(crno) references chatroom(crno) on delete cascade on update cascade
                        );
# 게시물 추천 테이블 
create table boardlike(
						bno int ,
						mno int ,
                        foreign key (mno) references member(mno) on delete cascade on update cascade , 
                        foreign key (bno) references board(bno) on delete cascade on update cascade
                        );
# 리뷰 추천 테이블
create table reviewlike(
						reno int ,
						mno int ,
                        foreign key (mno) references member(mno) on delete cascade on update cascade , 
                        foreign key (reno) references review(reno) on delete cascade on update cascade
                        ); 
# 구매 리스트 테이블
drop table if exists userBuyLog;
create table userBuyLog(
	ubno int auto_increment,								-- 레코드 번호(식별 위함)
	mno int not null,										-- 멤버 번호
	pcno int  ,												-- 상품 번호
    pestate boolean default false,							-- 상품 장착 상태
	pbuyDate datetime default now() not null,				-- 구매한 날짜
    primary key(ubno),
    foreign key( pcno ) references productList(pcno) on update cascade,
    foreign key(mno) references member(mno) on delete cascade on update cascade
);
drop table if exists productList;
#포인트 상품 테이블
create table productList(
	pcno int auto_increment ,								-- 상품 번호
    pcname varchar(20) not null unique ,					-- 상품 이름
    primary key(pcno)
);
insert into productList( pcname ) values( '#ffa500' );		-- 오렌지
insert into productList( pcname ) values( '#1c98ed' );		-- 스카이블루
insert into productList( pcname ) values( '#5d5491' );		-- 퍼플

 -- ------------------------ 샘플데이터 ------------------------------------
# member 샘플데이터 # 아이디 , 비밀번호 이메일은 동일 / 이메일 도메인은 naver 통일 / 이미지는 dfault 통일
insert into member( mname , mid , mpwd , memail , mimg ) values( '김철수' , 'culsu123' , 'culsu123' , 'culsu123@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '스탠리' , 'stanley889' , 'stanley889' , 'stanley889@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '병아리개발자' , 'bbiyak22' , 'bbiyak22' , 'bbiyak22@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '자바신' , 'javagod00' , 'javagod00' , 'javagod00@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '리환희' , 'leehwan11' , 'leehwan11' , 'leehwan11@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '젭스피' , 'jsppp' , 'jsppp' , 'jsppp@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '기면수주니어' , 'babykim36' , 'babykim36' , 'babykim36@naver.com' , 'default.webp' );
insert into member( mname , mid , mpwd , memail , mimg ) values( '맥북말고빅맥' , 'bigmac5500' , 'bigmac5500' , 'bigmac5500@naver.com' , 'default.webp' );

# maincategory 샘플데이터
insert into maincategory ( cno , cname ) values( 1 , 'TOPIC' );
insert into maincategory ( cno , cname ) values( 2 , 'QnA' );
insert into maincategory ( cno , cname ) values( 3 , '정보마당' ); # 가제
insert into maincategory ( cno , cname ) values( 4 , '실시간 채팅' );
insert into maincategory ( cno , cname ) values( 5 , '커뮤니티' );
insert into maincategory ( cno , cname ) values( 6 , '구인구직' );

# subcategory 샘플데이터
insert into subcategory ( cno , subcno , subcname ) values( 1 , 1 , '새소식' ); #TOPIC
insert into subcategory ( cno , subcno , subcname ) values( 1 , 2 , '핫토픽' ); #TOPIC
insert into subcategory ( cno , subcno , subcname ) values( 1 , 3 , '행사' ); #TOPIC
insert into subcategory ( cno , subcno , subcname ) values( 5 , 4 , '잡담' ); # 커뮤니티
insert into subcategory ( cno , subcno , subcname ) values( 5 , 5 , '스터디' ); # 커뮤니티
insert into subcategory ( cno , subcno , subcname ) values( 2 , 6 , 'QnA' ); # qna
insert into subcategory ( cno , subcno , subcname ) values( 3 , 7 , '정보마당' ); # 정보마당
insert into subcategory ( cno , subcno , subcname ) values( 6 , 8 , '구인구직' ); # 구인구직



# board 샘플데이터
insert into board ( subcno , mno , btitle , bcontent  ) values( 3 , 1 , '애플 WWDC 열린다.' , '애플 아이폰 발표회 가는법'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 3 , 1 , 'NFT 2023 SEOUL.' , '글로벌 WEB 3.0 및 디지털콘텐츠, 블록체인 콘퍼런스'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 4 , 2 , '맨날 나한테 뭐라하는 상사 모니터로 머리 갈겨도 되냐' , '미안하다 이거 보여주려고 어그로 끌었다 나루토랑 사스케 세계관 최강자들의 싸움 실화냐?' );
insert into board ( subcno , mno , btitle , bcontent  ) values( 4 , 3 , '안녕하세요 새로 가입했습니다..' , '이제 개발자 준비 해보려고 합니다. 만반잘부' );
insert into board ( subcno , mno , btitle , bcontent  ) values( 5 , 1 , 'JVM에 대하여.' , 'JVM이란 블라블라 샬라샬라 셤머셤머' );
insert into board ( subcno , mno , btitle , bcontent  ) values( 5 , 1 , 'AJAX가 나오기전엔 뭘 썻는가.' , 'XML이 어쩌구저쩌구 김수한무거북이와두루미' );
insert into board ( subcno , mno , btitle , bcontent  ) values( 6 , 4 , 'java 질문 있습니다...' , 'java 공부하고 싶은데 어떤거부터 공부해야할지 모르겠어요 좋은 책 있나요?'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 6 , 5 , 'jsp랑 html이 무슨 차이인가요.' , '왜 두개를 따로 쓰는지 모르곘어요'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 7 , 7 , '부트스트랩 적용하는법.' , '사이트 들어가서 링크 복사해서 쓰세요'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 7 , 7 , '개발자 실무에 완전 적응하는 법.' , '열심히 하세요'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 8 , 1 , '카카오에서 인재를 모십니다..' , '상세요강 홈페이지 참고'  );
insert into board ( subcno , mno , btitle , bcontent  ) values( 8 , 1 , '배민에서 함께할 가족을 모십니다.' , '상세요강 잡코리아 참고'  );

# review 샘플 데이터
insert into review (bno , mno , recontent , reindex) values( 1 , 1 , '1번게시물 리뷰' , 0 ); # 작성자 김철수 
insert into review (bno , mno , recontent , reindex) values( 2 , 2 , '2번게시물 리뷰' , 0 ); # 작성자 스탠리 
insert into review (bno , mno , recontent , reindex) values( 3 , 3 , '3번게시물 리뷰' , 0 ); # 작성자 병아리개발자 
insert into review (bno , mno , recontent , reindex) values( 4 , 4 , '4번게시물 리뷰' , 0 ); # 작성자 자바신 
insert into review (bno , mno , recontent , reindex) values( 5 , 5 , '5번게시물 리뷰' , 0 ); # 작성자 리환희 
insert into review (bno , mno , recontent , reindex) values( 6 , 6 , '6번게시물 리뷰' , 0 ); # 작성자 젭스피
insert into review (bno , mno , recontent , reindex) values( 7 , 7 , '7번게시물 리뷰' , 0 ); # 작성자 기면수주니어
insert into review (bno , mno , recontent , reindex) values( 8 , 8 , '8번게시물 리뷰' , 0 ); # 작성자 맥북말고빅맥 
insert into review (bno , mno , recontent , reindex) values( 9 , 2 , '9번게시물 리뷰' , 0 ); # 작성자 스탠리
insert into review (bno , mno , recontent , reindex) values( 10 , 3 , '10번게시물 리뷰' , 0 ); # 작성자 병아리개발자
insert into review (bno , mno , recontent , reindex) values( 11 , 4 , '11번게시물 리뷰' , 0 ); # 작성자 자바신
insert into review (bno , mno , recontent , reindex) values( 12 , 5 , '12번게시물 리뷰' , 0 ); # 작성자  리환희

# point 샘플 데이터
insert into point ( mno , pcontent , pcount ) values( 1 , '출석' , +10 );
insert into point ( mno , pcontent , pcount ) values( 2 , '출석' , +10 );
insert into point ( mno , pcontent , pcount ) values( 3 , '출석' , +10 );
insert into point ( mno , pcontent , pcount ) values( 4 , '출석' , +10 );


# tag 샘플 데이터
insert into tag (bno , tname ) values( 1 , '#java#javascript' );
insert into tag (bno , tname ) values( 2 , '#java#jsp' );
insert into tag (bno , tname ) values( 3 , '#jsp' );
insert into tag (bno , tname ) values( 4 , '#React#Spring' );
insert into tag (bno , tname ) values( 5 , '#html' );
insert into tag (bno , tname ) values( 6 , '#html' );
insert into tag (bno , tname ) values( 7 , '#java' );
insert into tag (bno , tname ) values( 8 , '#javascript' );
insert into tag (bno , tname ) values( 9 , '#React' );
insert into tag (bno , tname ) values( 10 , '#Spring' );
insert into tag (bno , tname ) values( 11 , '#pathon' );
insert into tag (bno , tname ) values( 12 , '#java#javascript#jsp#React#Spring' );

