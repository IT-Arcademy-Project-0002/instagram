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


// function handleImageLoad(img) {
//     var colorThief = new ColorThief();
//
//     // 배열을 사용하여 이미지의 배경 색상 저장
//     var imageColors = [];
//
//     // ColorThief를 사용하여 이미지의 주요 색상 추출
//     var dominantColor = colorThief.getColor(img);
//     console.log(dominantColor);
//     // 추출된 색상을 CSS rgb 형식으로 변환
//     var bgColor = 'rgb(' + dominantColor[0] + ',' + dominantColor[1] + ',' + dominantColor[2] + ')';
//
//     // 배열에 배경 색상 추가
//     imageColors.push(bgColor);
//
//     // 각 이미지의 부모 요소에 배경 색상 설정
//     $(img).parent('.carousel-item').css('background', bgColor);
//
//     // 모든 이미지의 배경 색상을 결합하여 최종 배경 색상 설정
//     var combinedColor = combineColors(imageColors);
//     $('.carousel-inner').css('background', combinedColor);
// }
//
// function combineColors(colors) {
//     // 여러 색상의 평균을 계산하여 반환 또는 여러 색상을 특정 방식으로 결합할 수 있음
//     // 이 예제에서는 간단히 배열의 마지막 색상을 반환합니다.
//     return colors[colors.length - 1];
// }
