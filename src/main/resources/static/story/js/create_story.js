function clickStoryFileUpload() {
    var story = document.getElementById('story');
    console.log(story);

    story.click();

    // 파일 선택 후에 실행될 함수 등록
    story.addEventListener('change', function() {
        // 선택된 파일 정보를 가져오기
        var file = story.files[0];
        console.log(file);

        // 파일 정보를 표시할 컨테이너 요소 가져오기
        var container = document.getElementById('storyFile-container');
        console.log(container);

        // 파일 정보를 초기화
        container.innerHTML = '';

        // 선택된 파일에 대한 정보를 표시
        if (file) {
            // 이미지를 나타내는 HTML 요소 생성
            var imgElement = document.createElement('img');
            imgElement.src = URL.createObjectURL(file);
            imgElement.alt = 'Uploaded Image';
            imgElement.style.width = '498px'; // Set the width
            imgElement.style.height = 'auto'; // Maintain aspect ratio

            // 생성한 이미지 요소를 컨테이너에 추가
            container.appendChild(imgElement);

            // btnGroup 요소를 제거
            var btnGroup = document.getElementById('btnGroup');
            btnGroup.remove();
        }
    });
}
