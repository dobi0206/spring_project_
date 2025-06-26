document.getElementById("buttonUpdate").addEventListener('click',function(){
	const idx = document.getElementById("idx").value
	
	const formData = {
		title : document.getElementById("title").value,
		content:document.getElementById("content").value,
		writer : document.getElementById("writer").value
	}
	
	const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute('content');
	const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute('content');	
	
	fetch(`/menu/update/${idx}`,{
		method:'PUT',
		headers:{
			'Content-Type':'application/json',
			[csrfHeader]:csrfToken
		},
		body:JSON.stringify(formData)
	}).then(response=>{
		if(!response.ok){
			throw new Error("응답 불가");
		}
		return response.text();
	}).then(_=>{
		alert("수정 성공");
		window.location.href="/";
	}).catch(error=>{
		console.log(`Error발생:${error}`);
	})
	
})