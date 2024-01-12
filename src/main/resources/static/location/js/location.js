$(document).ready(function () {

    var locationSearchTimer; // 전역 변수로 선언

    var locationListBody = $('#locationList');

    // 위치 등록
    function showLocationList() {
        // searchList 요소의 visibility 속성을 visible로 변경하여 보이게 함
        locationListBody.css('visibility', 'visible');
    }

    function hideLocationList() {
        // searchList 요소의 visibility 속성을 hidden으로 변경하여 숨김
        locationListBody.css('visibility', 'hidden');
    }

    // input 요소에 대한 keyup 이벤트 핸들러 등록
    // 위치 등록
    $("#boardkeyword").keyup(function () {
        // 검색어를 가져오기
        var locationKeyword = $(this).val();

        if (locationSearchTimer) {
            clearTimeout(locationSearchTimer);
        }

        // 300밀리초(0.3초) 이후에 검색을 수행
        locationSearchTimer = setTimeout(function () {
            SearchKeyword(locationKeyword);
        }, 300);
    });


    // 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
    function SearchKeyword(keyword) {

        locationListBody.empty();
        hideLocationList();

        $.ajax({
            url: '/location/keyword',
            type: 'GET',
            dataType: 'json',
            data: { // 쿼리 문자열로 변환, URL에 포함시켜 서버로 전달.
                keyword: keyword
            },
            success: function (data) {
                    showLocationList();
                    displaySearchResults(data);
            },
            error: function (error) {
                console.error('Error:', error);
            }
        });
    }

    function displaySearchResults(data) {
        var newList = $('<div></div>');

        if (data.length > 0) {
            data.forEach(searchResult => {

                var listItem = $(
                    '<div class="location-box">' +
                    '<div class="location-userinfo">' +
                    '<div class="location-username">' + searchResult.placeName + '</div>' +
                    '<div class="location-userintroduction">' + searchResult.addressName + '</div>' +
                    '</div>' +
                    '</div>'
                );

                listItem.on('click', function () {

                    document.getElementById('locationId').value = searchResult.locationId;
                    document.getElementById('placeName').value = searchResult.placeName;
                    document.getElementById('roadAddressName').value = searchResult.roadAddressName;
                    document.getElementById('addressName').value = searchResult.addressName;
                    document.getElementById('x').value = searchResult.x;
                    document.getElementById('y').value = searchResult.y;

                    document.getElementById('boardkeyword').value = searchResult.placeName;

                    locationListBody.empty();
                    hideLocationList();
                });

                newList.append(listItem);
            });
        } else {
            var listItem = $('<div></div>');
            listItem.text('검색 결과가 없습니다.');
            newList.append(listItem);
        }

        locationListBody.append(newList);
    }

});
