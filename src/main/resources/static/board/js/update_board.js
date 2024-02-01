// TagMember
const modifyInputElement = document.getElementById('ModifyTagMember');
const modifyContainer = document.getElementById('ModifyTagMember-container');

let modifyMentionedUsernames = [];

modifyInputElement.addEventListener('input', function (event) {
    modifyMentionedUsernames = modifyInputElement.value.split(' ');
    console.log(modifyMentionedUsernames);
    var inputValue = modifyInputElement.value;
    var usernames = inputValue.split(' ').map(username => username.replace(/^@/, ''));

    if (usernames.includes('')) {
        modifyContainer.textContent = '';
        return;
    }
    modifyContainer.textContent = '';

    usernames.forEach(username => {
        fetch("/board/search/" + username)
            .then(response => response.json())
            .then(data => {
                data.result.forEach((member, index) => {
                    var resultMember = document.getElementsByClassName(member.username);

                    if (resultMember.length !== 0 || usernames.includes(member.username)) {
                        return;
                    }

                    var div = document.createElement('div');
                    div.classList = 'd-flex align-items-center';

                    var div2 = document.createElement('div');
                    div2.style.width = '2rem';
                    div2.style.height = '2rem';
                    div2.style.overflow = 'hidden';
                    div2.style.marginRight = '0.5rem';

                    var img = document.createElement('img');
                    if (member.image == null) {
                        img.src = '/files/designImg/noneuser.png';
                    } else {
                        img.src = '/resources/' + member.image.name;
                    }
                    img.classList = 'rounded-circle text-center';
                    img.style.width = '100%';
                    img.style.height = '100%';
                    img.style.objectFit = 'cover';

                    div2.appendChild(img);
                    div.appendChild(div2);

                    var userInfo = document.createElement('div');
                    userInfo.classList = member.username;

                    var usernameInfo = document.createElement('div');
                    usernameInfo.textContent = member.username;
                    usernameInfo.style.fontSize = '13px';
                    usernameInfo.style.fontWeight = 'bold';
                    userInfo.appendChild(usernameInfo);

                    var nicknameInfo = document.createElement('div');
                    nicknameInfo.textContent = member.nickname;
                    nicknameInfo.style.fontSize = '13px';
                    userInfo.appendChild(nicknameInfo);

                    div.appendChild(userInfo);

                    div.addEventListener('click', function () {
                        const clickedUsername = `@${member.username}`;
                        modifyMentionedUsernames.push(clickedUsername);
                        modifyInputElement.value = modifyMentionedUsernames.join(' ');
                        modifyContainer.textContent = '';

                        modifyMentionedUsernames = modifyMentionedUsernames.filter((element) => element !== `@` + username);
                        modifyInputElement.value = modifyMentionedUsernames.join(' ');
                        console.log(modifyMentionedUsernames);
                    });


                    modifyContainer.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                modifyContainer.textContent = '';
            });
    });
});
// 사용자가 input에서 데이터를 지우면 modifyMentionedUsernames 배열에서 해당 사용자명을 삭제합니다.
modifyInputElement.addEventListener('input', function () {
    let modifyInputUsernames = modifyInputElement.value.split(' ');
    let modifyRemovedUsernames = modifyMentionedUsernames.filter(mentionedUsername => !modifyInputUsernames.includes(mentionedUsername));

    modifyMentionedUsernames = modifyMentionedUsernames.filter(mentionedUsername => modifyInputUsernames.includes(mentionedUsername));

    modifyRemovedUsernames.forEach(removedUsername => {
        modifyMentionedUsernames = modifyMentionedUsernames.filter(username => username !== removedUsername);
    });
});


// HashTag
const modifyHashTagInputElement = document.getElementById('ModifyHashTag');
const modifyHashTagContainer = document.getElementById('ModifyHashTag-container');
let modifyModifyHashTags = [];

modifyHashTagInputElement.addEventListener('input', function () {
    modifyModifyHashTags = modifyHashTagInputElement.value.split(' ');

    var inputValue = modifyHashTagInputElement.value;
    var hashTagNames = inputValue.split(' ').map(name => name.replace(/^#/, ''));

    if (hashTagNames.includes('')) {
        modifyHashTagContainer.textContent = '';
        return;
    }
    modifyHashTagContainer.textContent = '';

    hashTagNames.forEach(name => {
        fetch("/board/search/HashTag/" + name)
            .then(response => response.json())
            .then(data => {
                data.result.forEach((hashTag, index) => {
                    var resultHashTag = document.getElementsByClassName(hashTag.name);
                    if (resultHashTag.length !== 0 || hashTagNames.includes(hashTag.name)) {
                        return;
                    }

                    var div = document.createElement('div');
                    div.classList = 'd-flex align-items-center';

                    var HashTagInfo = document.createElement('div');
                    HashTagInfo.classList = hashTag.name;
                    console.log(HashTagInfo);

                    var HashTagNameInfo = document.createElement('div');
                    HashTagNameInfo.textContent = '#' + hashTag.name;
                    HashTagNameInfo.style.fontSize = '15px';
                    HashTagNameInfo.style.fontWeight = 'bold';
                    HashTagNameInfo.style.margin = '5px 0 5px 0';
                    console.log(HashTagNameInfo);
                    HashTagInfo.appendChild(HashTagNameInfo);

                    div.appendChild(HashTagInfo);

                    div.addEventListener('click', function () {
                        modifyModifyHashTags.push(`#${hashTag.name}`);
                        modifyHashTagInputElement.value = modifyModifyHashTags.join(' ');
                        modifyHashTagContainer.textContent = '';

                        modifyModifyHashTags = modifyModifyHashTags.filter((element) => element !== `#` + name);
                        modifyHashTagInputElement.value = modifyModifyHashTags.join(' ');

                        console.log(modifyMentionedUsernames);
                    });
                    modifyHashTagContainer.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                modifyHashTagContainer.textContent = '';
            });
    });
});

modifyHashTagInputElement.addEventListener('input', function () {
    let modifyInputHashtag = modifyHashTagInputElement.value.split(' ');
    let modifyRemovedHashtag = modifyModifyHashTags.filter(mentionedUsername => !modifyInputHashtag.includes(mentionedUsername));

    modifyModifyHashTags = modifyModifyHashTags.filter(mentionedUsername => modifyInputHashtag.includes(mentionedUsername));

    modifyRemovedHashtag.forEach(removedUsername => {
        modifyModifyHashTags = modifyModifyHashTags.filter(username => username !== removedUsername);
    });
});


// /board/update/{id}
function ModifyBoard(id) {
    // 모달 제거
    $('#BoardDetailModal').modal('hide');
    $('#CreateUserBoardSettingModal').modal('hide');
    $('#ModifyBoardModal').modal('show');

    let imagePreviewArray = [];
    let videoPreviewArray = [];

    console.log(id);
    fetch("/board/update/" + id)
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);

            let filesArray = data.updateFeed.fileDTOS;

            var file = document.getElementById('file-container');
            for (let i = 0; i < filesArray.length; i++) {
                var carouselItem = document.createElement("div");
                carouselItem.classList.add("carousel-item");

                var fileContainer = document.createElement("div");
                fileContainer.id = "file_container" + i;
                fileContainer.style.position = "relative";
                fileContainer.style.width = "55rem";
                fileContainer.style.height = "50rem";

                var modifyImg = document.createElement("img");
                modifyImg.id = "name" + i;
                modifyImg.classList.add("shadow", "text-center");
                modifyImg.style.position = "absolute";
                modifyImg.style.top = "0";
                modifyImg.style.left = "0";
                modifyImg.style.transform = "translate(50,50)";
                modifyImg.style.width = "100%";
                modifyImg.style.height = "100%";
                modifyImg.style.objectFit = "cover";
                modifyImg.style.margin = "auto";
                modifyImg.src = "/resources/" + decodeURIComponent(filesArray[i].name);

                var modifyVideo = document.createElement("video");
                modifyVideo.id = "name" + i;
                modifyVideo.classList.add("shadow", "text-center");
                modifyVideo.style.position = "absolute";
                modifyVideo.style.top = "0";
                modifyVideo.style.left = "0";
                modifyVideo.style.transform = "translate(50,50)";
                modifyVideo.style.width = "100%";
                modifyVideo.style.height = "100%";
                modifyVideo.style.objectFit = "cover";
                modifyVideo.style.margin = "auto";
                modifyVideo.src = "/resources/" + decodeURIComponent(filesArray[i].name);
                modifyVideo.controls = true; // 비디오 컨트롤
                modifyVideo.autoplay = true; // 자동 재생
                modifyVideo.muted = true; // 음소거

                debugger;
                imagePreviewArray.push(decodeURIComponent(modifyImg.src));
                videoPreviewArray.push(decodeURIComponent(modifyVideo.src))
                // 이미지 또는 비디오를 캐러셀에 추가
                if (filesArray[i].path.includes('\\img\\')) {
                    fileContainer.appendChild(modifyImg);
                } else if (filesArray[i].path.includes('\\video\\')) {
                    fileContainer.appendChild(modifyVideo);
                }
                carouselItem.appendChild(fileContainer);
                file.appendChild(carouselItem);
            }

            // 버튼 사라지게 하는거
            var modifyCarousel = document.getElementById("modifyCarouselExampleControlsNoTouching");
            var prevButton = modifyCarousel.querySelector(".carousel-control-prev");
            var nextButton = modifyCarousel.querySelector(".carousel-control-next");

            if (imagePreviewArray.length === 1 || videoPreviewArray.length === 1) {
                prevButton.classList.add("visually-hidden")
                nextButton.classList.add("visually-hidden")
            }
            file.firstElementChild.classList.add('active');

            var contentTextarea = document.getElementById('ModifyContent');
            contentTextarea.value = data.updateFeed.board.content;

            const modifyTextarea = document.getElementById('ModifyContent');
            const modifyTextCount = document.querySelector('.ModifyTextCount');
            const ModifyMaxLength = 2200;

            updateTextCount();

            modifyTextarea.addEventListener('input', function () {
                updateTextCount();

                const textLength = modifyTextarea.value.length;

                if (textLength > ModifyMaxLength) {
                    modifyTextarea.value = modifyTextarea.value.slice(0, ModifyMaxLength);
                    updateTextCount();
                }

                if (textLength >= ModifyMaxLength) {
                    modifyTextCount.style.color = 'red';
                } else {
                    modifyTextCount.style.color = 'initial';
                }
            });

            function updateTextCount() {
                modifyTextCount.textContent = `${modifyTextarea.value.length}자`;
            }

            let members = data.updateFeed.board.boardTagMembers;
            var boardTagMembers = document.getElementById('ModifyTagMember');
            let tagMember = [];
            for (let i = 0; i < members.length; i++) {
                tagMember.push('@' + members[i]);
            }
            boardTagMembers.value = tagMember.join(' ').replace(/,/g, '');

            let HashTags = data.updateFeed.board.boardHashTags;
            var boardHahTags = document.getElementById('ModifyHashTag');
            let hashTag = [];
            for (let i = 0; i < HashTags.length; i++) {
                hashTag.push('#' + HashTags[i]);
            }
            boardHahTags.value = hashTag.join(' ').replace(/,/g, '');

            var location = document.getElementById('ModifyBoardkeyword');
            var ModifyLocationId = document.getElementById("ModifyLocationId");
            var ModifyPlaceName = document.getElementById("ModifyPlaceName");
            var ModifyRoadAddressName = document.getElementById("ModifyRoadAddressName");
            var ModifyAddressName = document.getElementById("ModifyAddressName");
            var Modify_x = document.getElementById("Modify_x");
            var Modify_y = document.getElementById("Modify_y");

            if (data.updateFeed.board.locationDTO) {
                location.value = data.updateFeed.board.locationDTO.placeName;
                ModifyLocationId.value = data.updateFeed.board.locationDTO.locationId;
                ModifyPlaceName.value = data.updateFeed.board.locationDTO.placeName;
                ModifyRoadAddressName.value = data.updateFeed.board.locationDTO.roadAddressName;
                ModifyAddressName.value = data.updateFeed.board.locationDTO.addressName;
                Modify_x.value = data.updateFeed.board.locationDTO.x;
                Modify_y.value = data.updateFeed.board.locationDTO.y;
            } else {
                location.value = "";
                ModifyLocationId.value = "";
                ModifyPlaceName.value = "";
                ModifyRoadAddressName.value = "";
                ModifyAddressName.value = "";
                Modify_x.value = "";
                Modify_y.value = "";
            }
            var ModifyLikeHide = document.getElementById('ModifyLikeHide');
            ModifyLikeHide.checked = data.updateFeed.board.likeHide;


            var ModifyCommentDisable = document.getElementById('ModifyCommentDisable');
            ModifyCommentDisable.checked = data.updateFeed.board.commentDisable;

        })
        .then(() => {
            var keywordValue = document.getElementById('ModifyBoardkeyword');
            if(keywordValue.value === ""){
                var locationDelete = document.getElementById("locationDelete");
                locationDelete.style.display = "none";
            }
            var locationSearchTimer; // 전역 변수로 선언

            var ModifyLocationListBody = $('#ModifyLocation-list');

            // 위치 수정
            function ModifyShowLocationList() {
                // searchList 요소의 visibility 속성을 visible로 변경하여 보이게 함
                ModifyLocationListBody.css('visibility', 'visible');
            }

            function ModifyHideLocationList() {
                // searchList 요소의 visibility 속성을 hidden으로 변경하여 숨김
                ModifyLocationListBody.css('visibility', 'hidden');
            }

            // 위치 수정
            $("#ModifyBoardkeyword").keyup(function () {
                // 검색어를 가져오기
                var locationKeyword = $(this).val();

                if (locationSearchTimer) {
                    clearTimeout(locationSearchTimer);
                }

                // 300밀리초(0.3초) 이후에 검색을 수행
                locationSearchTimer = setTimeout(function () {
                    ModifySearchKeyword(locationKeyword);
                }, 300);
            });

            // 검색창의 변화를 감지하여 완성된 단어를 순서대로 읽어 백엔드로 전달. 실시간으로 검색결과 전송
            // 위치 수정
            function ModifySearchKeyword(keyword) {

                ModifyLocationListBody.empty();
                ModifyHideLocationList();
                $.ajax({
                    url: '/location/modify/keyword',
                    type: 'GET',
                    dataType: 'json',
                    data: { // 쿼리 문자열로 변환, URL에 포함시켜 서버로 전달.
                        keyword: keyword
                    },
                    success: function (data) {
                        ModifyShowLocationList();
                        displayModifySearchResults(data);
                    },
                    error: function (error) {
                        console.error('Error:', error);
                    }
                });
            }

            function displayModifySearchResults(data) {
                var newList = $('<div></div>');

                if (data.length > 0) {
                    data.forEach(searchResult => {
                        var listItem = $(
                            '<div class="ModifyLocation-box">' +
                            '<div class="ModifyLocation-userinfo">' +
                            '<div class="ModifyLocation-username">' + searchResult.placeName + '</div>' +
                            '<div class="ModifyLocation-userintroduction">' + searchResult.addressName + '</div>' +
                            '</div>' +
                            '</div>'
                        );

                        listItem.on('click', function () {

                            document.getElementById('ModifyLocationId').value = searchResult.locationId;
                            document.getElementById('ModifyPlaceName').value = searchResult.placeName;
                            document.getElementById('ModifyRoadAddressName').value = searchResult.roadAddressName;
                            document.getElementById('ModifyAddressName').value = searchResult.addressName;
                            document.getElementById('Modify_x').value = searchResult.x;
                            document.getElementById('Modify_y').value = searchResult.y;

                            document.getElementById('ModifyBoardkeyword').value = searchResult.placeName;

                            var locationDelete = document.getElementById("locationDelete");
                            locationDelete.style.display = "inline-block";

                            ModifyLocationListBody.empty();
                            ModifyHideLocationList();
                        });

                        newList.append(listItem);
                    });
                } else {
                    var listItem = $('<div></div>');
                    listItem.text('검색 결과가 없습니다.');
                    newList.append(listItem);
                }

                ModifyLocationListBody.append(newList);
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

// board 고급설정 js
const ModifyAdvancedSettingsToggle = document.getElementById('ModifyAdvancedSettingsToggle');
const ModifyAdvancedcollapseArrow = document.getElementById('ModifyAdvancedcollapseArrow');
const ModifyAdvancedexpandArrow = document.getElementById('ModifyAdvancedexpandArrow');
const ModifyAdvancedSettings = document.getElementById('ModifyAdvancedSettings');

const ModifyAccessibilitySettingsToggle = document.getElementById('ModifyAccessibilitySettingsToggle');
const ModifyAccessibilitycollapseArrow = document.getElementById('ModifyAccessibilitycollapseArrow');
const ModifyAccessibilityexpandArrow = document.getElementById('ModifyAccessibilityexpandArrow');
const ModifyAccessibilitySettings = document.getElementById('ModifyAccessibilitySettings');


ModifyAdvancedSettingsToggle.addEventListener('click', function () {
    if (ModifyAdvancedSettings.style.display === 'none') {
        ModifyAdvancedSettings.style.display = 'block';
        ModifyAdvancedcollapseArrow.style.display = 'none';
        ModifyAdvancedexpandArrow.style.display = 'inline-block';
    } else {
        ModifyAdvancedSettings.style.display = 'none';
        ModifyAdvancedcollapseArrow.style.display = 'inline-block';
        ModifyAdvancedexpandArrow.style.display = 'none';
    }
});

ModifyAccessibilitySettingsToggle.addEventListener('click', function () {
    if (ModifyAccessibilitySettings.style.display === 'none') {
        ModifyAccessibilitySettings.style.display = 'block';
        ModifyAccessibilitycollapseArrow.style.display = 'none';
        ModifyAccessibilityexpandArrow.style.display = 'inline-block';
    } else {
        ModifyAccessibilitySettings.style.display = 'none';
        ModifyAccessibilitycollapseArrow.style.display = 'inline-block';
        ModifyAccessibilityexpandArrow.style.display = 'none';
    }
});

function deleteLocation() {
    document.getElementById('ModifyBoardkeyword').value = "";
    document.getElementById('ModifyLocationId').value = "";
    document.getElementById('ModifyPlaceName').value = "";
    document.getElementById('ModifyRoadAddressName').value = "";
    document.getElementById('ModifyAddressName').value = "";
    document.getElementById('Modify_x').value = "";
    document.getElementById('Modify_y').value = "";

    var locationDelete = document.getElementById("locationDelete");
    locationDelete.style.display = "none";
}