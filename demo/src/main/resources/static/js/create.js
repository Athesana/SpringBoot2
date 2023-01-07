/**
 * /post/create.html
 */
//브라우저(윈도우)에서 발생하는 DOMContentLoaded(DOMContetnt 로드가 끝났으면) 이벤트가 발생하면 함수를 실행시키겠다는 리스너등록
 window.addEventListener('DOMContentLoaded', function(){
     // 작성완료 버튼을 찾아서 클릭 이벤트 리스너를 등록
     // CSS Selector : html_tagname, .class_name, #id_name
     const btnCreate = document.querySelector('#btnCreate'); 
     btnCreate.addEventListener('click', savePost);
     
     // 사용자가 등록 버튼을 눌렀을 때 브라우저가 실행해주는 콜백 함수
     function savePost(){
        
        const postTitle = document.querySelector('#title').value;
        const postAuthor = document.querySelector('#author').value;
        const postContent = document.querySelector('#content').value;
        
        if(postTitle == null || postTitle == '' || postAuthor == null || postAuthor == '' || postContent == null || postContent == ''){
            alert('제목, 작성자, 내용은 공란일 수 없습니다.')
            return;
        }
        
        // 서버로 ajax 요청에 포함시켜서 보낼 데이터
        const data = {
            title : postTitle,
            author : postAuthor,
            content : postContent
        };
        
        // 서버로 ajax 요청 보냄
        axios.post('/api/post', data)
            //성공 시 동작할 콜백 함수를 등록한다.
            .then(function (response) {
                alert(`No.${response.data} 포스트 작성 완료`);
                location.href = '/';
            })
            // 에러 처리 콜백 함수를 등록한다.
            // 실제 작동되는 시점은 서버에서 리턴을 해줬을 때 이 함수가 "호출"된다.
            //.catch(err => { console.log(err) });
            .catch(function (error) {
                console.log(error);
            });
        
     };
 });