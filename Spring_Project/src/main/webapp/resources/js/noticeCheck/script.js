document.getElementById("buttonUpdate").addEventListener('click',function(){
	const idx = document.getElementById("idx").value;
	window.location.href=`/noticeModifyPage?idx=${idx}`;
})

document.getElementById("buttonDelete").addEventListener('click',function(){
	const idx = document.getElementById("idx").value;
	
	const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute('content');
	const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute('content');	
	
	fetch(`/menu/delete/${idx}`,{
		method : "DELETE",
		headers:{
			[csrfHeader]:csrfToken
		}
	}).then(response=>{
		if(!response.ok){
			throw new Error("응답 실패");
		}else{
			return response.text();
		}
	}).then(_=>{
		alert("삭제 성공!");
		window.location.href='/';
	}).catch(error=>{
		console.log(`에러 발생":${error}`);
	})
})