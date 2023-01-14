/**
 * /post/modify.html
 */
window.addEventListener('DOMContentLoaded', function(){
    
    const btnModify = document.querySelector('#btnModify');
    const btnDelete = document.querySelector('#btnDelete');
    
    btnModify.addEventListener('click', modifyPost);
    btnDelete.addEventListener('click', deletePost);
    
    // 글 수정 함수
    function modifyPost(){
        
        const boardNo = document.querySelector('#boardNo').value;
        const postTitle = document.querySelector('#title').value;
        const postContent = document.querySelector('#content').value;
        
        if(postTitle == null || postTitle == '' || postContent == null || postContent == ''){
            alert('수정할 제목, 내용을 반드시 입력하세요.')
            return;
        }
        
        const result = confirm(`NO.${boardNo} 포스트 수정을 하시겠습니까?`);
        
        const data = {
            title : postTitle,
            content : postContent
        };
        
        if(result){
             axios.put('/api/post/' + boardNo, data)
                //성공 시 동작할 콜백 함수를 등록한다.
                .then(resp => {
                    console.log(resp)
                    alert(`No.${resp.data} 게시글 수정 완료`); // 작성완료한 글번호 alert으로 띄움
                    location.href = '/post/view/' + resp.data; // "다시 목록을 보여주세요." 다시 요청이 감, resp.data = boardNo
                })
                // 에러 처리 콜백 함수를 등록한다.
                // 실제 작동되는 시점은 서버에서 리턴을 해줬을 때 이 함수가 "호출"된다.
                .catch(err => { console.log(err) });
        }
        
    }
    
    // 글 삭제 함수
    function deletePost(){
        const boardNo = document.querySelector('#boardNo').value;
        //변수 boardNo에 있는 값을 ``의 {} 안에 넣어준다.
        const result = confirm(`NO.${boardNo} 포스트를 정말 삭제 하시겠습니까?`);
        
        if(result){
        
            // Ajax로 포스트 삭제 요청을 날린다. -> delete 방식으로 보낸다.
            axios.delete('/api/post/' + boardNo)
                //성공 시 동작할 콜백 함수를 등록한다.
                .then(resp => {
                    console.log(resp)
                    alert(`No.${resp.data} 게시글 삭제 완료`); // 작성완료한 글번호 alert으로 띄움
                    location.href = '/'; // "다시 목록을 보여주세요." 다시 요청이 감
                })
                // 에러 처리 콜백 함수를 등록한다.
                // 실제 작동되는 시점은 서버에서 리턴을 해줬을 때 이 함수가 "호출"된다.
                .catch(err => { console.log(err) });
                
        }
    }

});