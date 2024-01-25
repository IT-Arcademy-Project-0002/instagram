$(document).ready(function () {
    // ColorThief 라이브러리 초기화
    var colorThief = new ColorThief();

    // 배열을 사용하여 이미지의 배경 색상 저장
    var imageColors = [];

    // 각 이미지에 대해 실행
    $('.carousel-item img').each(function () {
        var img = this;

        // 이미지가 이미 로드되었는지 확인
        if (img.complete) {
            handleImageLoad(img);
        } else {
            // 이미지가 로드된 후에 실행
            $(img).on('load', function () {
                handleImageLoad(img);
            });
        }
    });

    // 이미지 로드 후에 실행될 함수
    function handleImageLoad(img) {
        // ColorThief를 사용하여 이미지의 주요 색상 추출
        var dominantColor = colorThief.getColor(img);

        // 추출된 색상을 CSS rgb 형식으로 변환
        var bgColor = 'rgb(' + dominantColor[0] + ',' + dominantColor[1] + ',' + dominantColor[2] + ')';

        // 배열에 배경 색상 추가
        imageColors.push(bgColor);

        // 각 이미지의 부모 요소에 배경 색상 설정
        $(img).parent('.img-Color').css('background', bgColor);
    }
});

function openFollowStoryListModal(id) {
    var modalId = 'followerStoryListModal' + id;
    var myModal = new bootstrap.Modal(document.getElementById(modalId), { backdrop: 'static', keyboard: false });
    myModal.show();
    var aa = 'followStory' + id;
    var bb = document.getElementById(aa);
    var imageCount = bb.value;
    console.log(imageCount);
    var imageDuration = 5;

    function updateProgressBar(index, percentage) {
        var progressBarId = 'progressBar' + id + '_'+  index;
        var progressBar = document.getElementById(progressBarId);
        if (progressBar) {
            progressBar.style.width = percentage + '%';
        }
    }

    function updateCountdown(index, seconds) {
        var countdownId = 'countdown' + id + '_'+  index;
        document.getElementById(countdownId);
    }

    function startCountdown(index, callback) {
        var seconds = imageDuration;
        var interval = setInterval(function () {
            updateCountdown(index, seconds);
            updateProgressBar(index, (imageDuration - seconds) / imageDuration * 100);

            if (seconds <= 0) {
                clearInterval(interval);
                if (callback) {
                    callback();
                }
            }
            seconds--;
        }, 1000);
    }

    function startCountdownForImages(index) {
        console.log(index);
        if (index < imageCount && imageCount > 0) {
            startCountdown(index, function () {
                startCountdownForImages(index + 1);
                if (index + 1) {
                    setTimeout(function () {
                        var nextButton = document.querySelector('[data-test-id="imgChangeBtn' + id + '"]');
                        if (nextButton) {
                            nextButton.click();
                        }
                    }, 1000);
                }
            });
        } else {
            var btn = document.getElementById('followerStoryListModalClose' + id);
            if (btn) {
                setTimeout(function () {
                    btn.click();
                }, 1000);
            }
        }
    }

    if (imageCount > 0) {
        startCountdownForImages(0);
    }
}
