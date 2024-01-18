// 해시태그 입력란이 따로 있던가...? 아니면 #을 붙였을때는 음.. 어절의 시작에 #이 붙은 것을 대상으로 검색

// 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
$(document).ready(function () {

    var searchTimer; // 전역 변수로 선언

    var searchListBody = $('#searchList');

    function showSearchList() {
        // searchList 요소의 visibility 속성을 visible로 변경하여 보이게 함
        searchListBody.css('visibility', 'visible');
    }

    function hideSearchList() {
        // searchList 요소의 visibility 속성을 hidden으로 변경하여 숨김
        searchListBody.css('visibility', 'hidden');
    }

    function toggleSearchList(keyword) {

        var menuWrap1 = document.getElementById("searchListInit");
        var menuWrap2 = document.getElementById("searchListResult");

        //searchList 요소의 초기화면을 제어 -> keyword가 undefined이거나 비어있을 때의 처리 추가
        if (!keyword || keyword.trim() === "") {
            menuWrap1.style.display = "flex";
            menuWrap1.style.justifyContent = "space-between";
            menuWrap2.style.display = "none";
        } else {
            menuWrap1.style.display = "none";
            menuWrap2.style.display = "block";
        }
    }

    // input 요소에 대한 keyup 이벤트 핸들러 등록
    $("#keyword").keyup(function () {
        // 검색어를 가져오기
        var searchKeyword = $(this).val();

        // 이전 타이머가 있다면 클리어
        if (searchTimer) {
            clearTimeout(searchTimer);
        }

        // 300밀리초(0.3초) 이후에 검색을 수행
        searchTimer = setTimeout(function () {
            SearchKeyword(searchKeyword);
        }, 300);

    });


    // 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
    function SearchKeyword(keyword) {

        toggleSearchList(keyword);

        $.ajax({
            url: '/search/keyword',
            type: 'GET',
            dataType: 'json',
            data: { // 쿼리 문자열로 변환, URL에 포함시켜 서버로 전달.
                keyword: keyword
            },
            success: function (data) {

                searchListBody.empty();

                if (keyword.trim() == "") {
                    hideSearchList();
                    searchListBody.text('');
                } else {
                    showSearchList()
                    displaySearchResults(data);
                }
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

                if(searchResult.searchType == "1") {

                    var listImageSrc = "/files/designImg/noneuser.png";

                    if (searchResult.listImage !== undefined && searchResult.listImage !== null && searchResult.listImage.trim() !== '') {
                        profileImageSrc = '/resources/' + searchResult.listImage;
                    }

                    var listItem = $(
                        '<a href="/member/page/'+ searchResult.listName + '" target="_blank" class="link">' +
                        '<div class="search-list-box">' +
                        '<div class="search-profile">' +
                        '<img src="'+ listImageSrc +'" class="search-profile-image rounded-circle text-center">' +
                        '</div>' +
                        '<div class="search-userinfo">' +
                        '<div class="search-username">' + searchResult.listName + '</div>' +
                        '<div class="search-userintroduction">' + searchResult.listIntroduction + '</div>' +
                        '</div>' +
                        '</div>' +
                        '</a>'
                    );

                    listItem.on("click", function() {
                        searchFavoriteCrate(searchResult.searchType, searchResult.listName);
                    });

                } else if (searchResult.searchType == "2") {

                    var listImageSrc = "/files/designImg/locationBase.png";

                    var listItem = $(
                        '<a href="/explore/locations/'+ searchResult.listLocationId + '" target="_blank" class="link">' +
                        '<div class="search-list-box">' +
                        '<div class="search-profile">' +
                        '<img src="'+ listImageSrc +'" class="search-profile-image rounded-circle text-center">' +
                        '</div>' +
                        '<div class="search-userinfo">' +
                        '<div class="search-username">' + searchResult.listName + '</div>' +
                        '<div class="search-userintroduction">' + searchResult.listIntroduction + '</div>' +
                        '</div>' +
                        '</div>' +
                        '</a>'
                    );

                    listItem.on("click", function() {
                        searchFavoriteCrate(searchResult.searchType, searchResult.listLocationId);
                    });

                } else if (searchResult.searchType == "3") {

                    var listImageSrc = "/files/designImg/hashTag.jpg";

                    var listItem = $(
                        '<a href="/explore/tags/'+ searchResult.listHashTagId + '" target="_blank" class="link">' +
                        '<div class="search-list-box">' +
                        '<div class="search-profile">' +
                        '<img src="'+ listImageSrc +'" class="search-profile-image rounded-circle text-center">' +
                        '</div>' +
                        '<div class="search-userinfo">' +
                        '<div class="search-username">' + searchResult.listName + '</div>' +
                        '<div class="search-userintroduction">' + searchResult.listIntroduction + '</div>' +
                        '</div>' +
                        '</div>' +
                        '</a>'
                    );

                    listItem.on("click", function() {
                        searchFavoriteCrate(searchResult.searchType, searchResult.listName);
                    });

                }
                newList.append(listItem);
            });
        } else {
            var noResult = $('#noResult');
            var listItem = $('<div></div>');
            listItem.text('검색 결과가 없습니다.');

            listItem.addClass('no_result_text');

            newList.addClass('h-75');
            newList.addClass('d-flex');
            newList.addClass('align-items-center');
            newList.addClass('justify-content-center');
            newList.addClass('p-3');

            newList.append(listItem);

        }

        searchListBody.append(newList);
    }


    function searchFavoriteCrate(searchType, specificName) {

        var token = $("input[name='_csrf']").attr("value");
        var header = $("input[name='_csrf_header']").attr("value");

        var body = JSON.stringify({
            favoriteType: searchType,
            specificName: specificName
        });

        fetch('/search/favorite/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                [header]: token
            },
            body: body
        })
        .then(response => {
            // 응답 처리 (예: 성공 시 콘솔에 로그 출력)
            console.log('POST 요청 성공:', response);
        })
        .catch(error => {
            // 오류 처리
            console.error('POST 요청 실패:', error);
        });

    }
});