// 해시태그 입력란이 따로 있던가...? 아니면 #을 붙였을때는 음.. 어절의 시작에 #이 붙은 것을 대상으로 검색

// 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
$(document).ready(function () {

    var searchListBody = $('#searchList');

    function showSearchList() {
        // searchList 요소의 visibility 속성을 visible로 변경하여 보이게 함
        searchListBody.css('visibility', 'visible');
    }

    function hideSearchList() {
        // searchList 요소의 visibility 속성을 hidden으로 변경하여 숨김
        searchListBody.css('visibility', 'hidden');
    }

    // input 요소에 대한 keyup 이벤트 핸들러 등록
    $("#keyword").keyup(function () {
        // 검색어를 가져오기
        var searchKeyword = $(this).val();

        // 검색어를 이용하여 검색을 수행하고 결과를 표시하는 함수 호출
        SearchKeyword(searchKeyword);
    });


    // 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
    function SearchKeyword(keyword) {

        $.ajax({
            url: '/search/keyword',
            type: 'GET',
            dataType: 'json',
            data: { // 쿼리 문자열로 변환, URL에 포함시켜 서버로 전달.
                keyword: keyword
            },
            success: function (data) {

                searchListBody.empty();

                if (keyword.trim() === "") {
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

                var profileImageSrc = "/files/designImg/noneuser.png";

                if (searchResult.profileimage !== undefined && searchResult.profileimage !== null && searchResult.profileimage.trim() !== '') {
                    profileImageSrc = '/resources/' + searchResult.profileimage;
                }

                var listItem = $(
                    '<a href="/member/page/'+ searchResult.username + '" target="_blank" class="link">' +
                    '<div class="search-box">' +
                    '<div class="search-profile">' +
                    '<img src="'+ profileImageSrc +'" class="search-profile-image rounded-circle text-center">' +
                    '</div>' +
                    '<div class="search-userinfo">' +
                    '<div class="search-username">' + searchResult.username + '</div>' +
                    '<div class="search-userintroduction">' + searchResult.introduction + '</div>' +
                    '</div>' +
                    '</div>' +
                    '</a>'
                );

                newList.append(listItem);
            });
        } else {
            var listItem = $('<div></div>');
            listItem.text('검색 결과가 없습니다.');
            newList.append(listItem);
        }

        searchListBody.append(newList);
    }

});