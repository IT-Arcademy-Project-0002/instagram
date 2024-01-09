function clickFileUpload() {
    var upload = document.getElementById("file-upload");
    upload.click();

    upload.addEventListener('change', function () {
        var firstModal = document.getElementById("Board_Create_Modal");
        $(firstModal).modal('hide'); // 첫 번째 모달 닫기

        var selectedFiles = event.target.files;
        let filesArray = [];
        // 선택된 각 파일을 배열에 추가하기
        for (let i = 0; i < selectedFiles.length; i++) {
            filesArray.push(selectedFiles[i]);
        }
        console.log(filesArray);

        var secondModal = document.getElementById("Board_Create_Modal2");
        $(firstModal).on('hidden.bs.modal', function () {
            $(firstModal).modal('dispose'); // 첫 번째 모달 삭제

            let imagePreviewArray = [];
            let videoPreviewArray = [];

            var files = document.getElementById("img-container");
            for (let i = 0; i < filesArray.length; i++) {
                var carouselItem = document.createElement("div");
                carouselItem.classList.add("carousel-item");

                var imageContainer  = document.createElement("div");
                imageContainer.id="image_container" + i;
                imageContainer.style.position="relative";
                imageContainer.style.width="55rem";
                imageContainer.style.height="50rem";

                var img = document.createElement("img");
                img.id="name"+i;
                img.classList.add("shadow", "text-center");
                img.style.position="absolute";
                img.style.top="0";
                img.style.left="0";
                img.style.transform="translate(50,50)";
                img.style.width="100%";
                img.style.height="100%";
                img.style.objectFit="cover";
                img.style.margin="auto";
                img.src = URL.createObjectURL(filesArray[i]);

                var video = document.createElement("video");
                video.id="name"+i;
                video.classList.add("shadow", "text-center");
                video.style.position="absolute";
                video.style.top="0";
                video.style.left="0";
                video.style.transform="translate(50,50)";
                video.style.width="100%";
                video.style.height="100%";
                video.style.objectFit="cover";
                video.style.margin="auto";
                video.src = URL.createObjectURL(filesArray[i]);
                video.controls = true; // 비디오 컨트롤
                video.autoplay = true; // 자동 재생
                video.muted = true; // 음소거

                imagePreviewArray.push(img.src);
                videoPreviewArray.push(video.src);

                // 이미지 또는 비디오를 캐러셀에 추가
                if (filesArray[i].type.startsWith('image/')) {
                    imageContainer.appendChild(img);
                } else if (filesArray[i].type.startsWith('video/')) {
                    imageContainer.appendChild(video);
                }
                carouselItem.appendChild(imageContainer);
                files.appendChild(carouselItem);
            }
            var carousel = document.getElementById("carouselExampleControlsNoTouching");
            var prevButton = carousel.querySelector(".carousel-control-prev");
            var nextButton = carousel.querySelector(".carousel-control-next");

            if (imagePreviewArray.length === 1 || videoPreviewArray.length === 1){
                prevButton.classList.add("visually-hidden")
                nextButton.classList.add("visually-hidden")
            }
            carouselItem.classList.add('active');
            console.log(imagePreviewArray);
            console.log(videoPreviewArray);
            $(secondModal).modal('show'); // 두 번째 모달 활성화
        });
    });
}


// 글자수 제한 및 체크
const textarea = document.getElementById('content');
const textCount = document.querySelector('.textCount');
const maxLength = 2200;

textarea.addEventListener('input', function() {
    const textLength = textarea.value.length;
    if (textLength > maxLength) {
        textarea.value = textarea.value.slice(0, maxLength);
    }

    textCount.textContent = `${textLength}자`;

    if (textLength >= maxLength) {
        textCount.style.color = 'red';
    } else {
        textCount.style.color = 'initial';
    }
});


// board 고급설정 js
const advancedSettingsToggle = document.getElementById('advancedSettingsToggle');
const advancedcollapseArrow = document.getElementById('advancedcollapseArrow');
const advancedexpandArrow = document.getElementById('advancedexpandArrow');
const advancedSettings = document.getElementById('advancedSettings');

const accessibilitySettingsToggle = document.getElementById('accessibilitySettingsToggle');
const accessibilitycollapseArrow = document.getElementById('accessibilitycollapseArrow');
const accessibilityexpandArrow = document.getElementById('accessibilityexpandArrow');
const accessibilitySettings = document.getElementById('accessibilitySettings');



advancedSettingsToggle.addEventListener('click', function() {
    if (advancedSettings.style.display === 'none') {
        advancedSettings.style.display = 'block';
        advancedcollapseArrow.style.display = 'none';
        advancedexpandArrow.style.display = 'inline-block';
    } else {
        advancedSettings.style.display = 'none';
        advancedcollapseArrow.style.display = 'inline-block';
        advancedexpandArrow.style.display = 'none';
    }
});

accessibilitySettingsToggle.addEventListener('click', function() {
    if (accessibilitySettings.style.display === 'none') {
        accessibilitySettings.style.display = 'block';
        accessibilitycollapseArrow.style.display = 'none';
        accessibilityexpandArrow.style.display = 'inline-block';
    } else {
        accessibilitySettings.style.display = 'none';
        accessibilitycollapseArrow.style.display = 'inline-block';
        accessibilityexpandArrow.style.display = 'none';
    }
});
