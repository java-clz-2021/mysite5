<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>


<body>
	<div id="wrap">

		<!-- 해더 네비 -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //해더 네비 -->


		<div id="container" class="clearfix">
			<!-- 게시판 aside -->
			<c:import url="/WEB-INF/views/includes/asideGallery.jsp"></c:import>
			<!-- //게시판 aside -->

			<div id="content">

				<div id="content-head">
					<h3>갤러리</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>갤러리</li>
							<li class="last">갤러리</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="gallery">
					<div id="list">

						<c:if test="${not empty authUser }">
							<button id="btnImgUpload">이미지올리기</button>
							<div class="clear"></div>
						</c:if>

						<ul id="viewArea">

							<!-- 이미지반복영역 -->
							<c:forEach items="${galleryList }" var="galleryVo">
								<li id="g-${galleryVo.no}">
									<div class="view" data-no="${galleryVo.no}">
										<img class="imgItem" src="${pageContext.request.contextPath }/upload/${galleryVo.saveName}">
										<div class="imgWriter">작성자: <strong>${galleryVo.userName}</strong></div>
									</div>
								</li>
							</c:forEach>
							<!-- //이미지반복영역 -->

						</ul>
					</div>
					<!-- //list -->
				</div>
				<!-- //gallery -->


			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->


		<!-- 푸터 -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //푸터 -->
	</div>
	<!-- //wrap -->









	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지등록</h4>
				</div>

				<form method="post" action="${pageContext.request.contextPath }/gallery/upload" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label> 
							<input id="addModalContent" type="text" name="content" value="">
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label> 
							<input id="file" type="file" name="file" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
				</form>
				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">

					<div class="formgroup">
						<img id="viewModelImg" src="">
						<!-- ajax로 처리 : 이미지출력 위치-->
					</div>

					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-danger" id="btnDel">삭제</button>
				</div>
				<input type="text" name="no" value="" id="delNo">

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>


<script type="text/javascript">

//이미지올리기 버튼 클릭할때 --> 이미지 업로드 팝업(모달) 호출
$("#btnImgUpload").on("click", function() {
	console.log("addModal");
	$("#addModal").modal();
})







//이미지 클릭 --> 이미지보기 팝업(모달) 호출
$(".view").on("click", function() {
	//클릭이벤트 체크
	console.log("이미지 클릭");
	
	//데이터 수집	
	var no = $(this).data("no");
	$("#delNo").val(no);	 //삭제할때 사용
	
	
	//데이터 전송
	$.ajax({
		url : "${pageContext.request.contextPath }/gallery/read",		
		type : "post",
		/* contentType : "application/json", */
		data : {no: no},
		dataType : "json",
		success : function(galleryVo) {
			console.log(galleryVo);
			var imgurl = "${pageContext.request.contextPath }/upload/"+galleryVo.saveName;
			
			//이미지 출력
			$("#viewModelImg").attr("src", imgurl);
			
			//코멘트 출력
			$("#viewModelContent").html(galleryVo.content);
			
			//버튼 출력(자신의 글일때만 삭제버튼 보이게 처리)
			if("${sessionScope.authUser.no}" == galleryVo.userNo){
				$("#btnDel").show();
			}else {
				$("#btnDel").hide();
			}
			
			$("#viewModal").modal();
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	}); 
          
	
});

/* 보기창에서 삭제버튼 클릭했을때 삭제하기 */
$("#btnDel").on("click", function(){
	//클릭이벤트 체크
	console.log("삭제버튼 클릭");
	
	//데이터 수집	
	var no = $("#delNo").val();	 //삭제할때 사용
	
	//데이터 전송
	$.ajax({
		url : "${pageContext.request.contextPath }/gallery/remove",		
		type : "post",
		/* contentType : "application/json", */
		data : {no: no},
		dataType : "json",
		success : function(count) {
			console.log(count);
			if(count==1){
				$("#g-"+no).remove();
				$("#viewModal").modal("hide");
			}else {
				$("#viewModal").modal("hide");
			}
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	}); 
         
});


</script>


</html>

