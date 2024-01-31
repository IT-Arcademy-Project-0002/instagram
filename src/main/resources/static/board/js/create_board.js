// TagMember
const inputElement = document.getElementById('TagMember');
const container = document.getElementById('TagMember-container');

let mentionedUsernames = [];

inputElement.addEventListener('input', function (event) {
    var inputValue = inputElement.value;
    var usernames = inputValue.split(' ').map(username => username.replace(/^@/, ''));

    if (usernames.includes('')) {
        container.textContent = '';
        return;
    }
    container.textContent = '';

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
                    div.style.padding = '10px';
                    div.style.cursor = 'pointer';

                    var div2 = document.createElement('div');
                    div2.style.width = '2rem';
                    div2.style.height = '2rem';
                    div2.style.overflow = 'hidden';
                    div2.style.marginRight = '0.5rem';
                    div2.style.borderRadius = '50%';
                    div2.style.border = '1px solid #d5d4d2';

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
                        mentionedUsernames.push(`@${member.username}`);
                        inputElement.value = mentionedUsernames.join(' ');
                        container.textContent = '';
                        console.log(mentionedUsernames);
                    });
                    container.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                container.textContent = '';
            });
    });
});
// 사용자가 input에서 데이터를 지우면 mentionedUsernames 배열에서 해당 사용자명을 삭제합니다.
inputElement.addEventListener('input', function () {
    let inputUsernames = inputElement.value.split(' ');
    let removedUsernames = mentionedUsernames.filter(mentionedUsername => !inputUsernames.includes(mentionedUsername));

    mentionedUsernames = mentionedUsernames.filter(mentionedUsername => inputUsernames.includes(mentionedUsername));

    removedUsernames.forEach(removedUsername => {
        mentionedUsernames = mentionedUsernames.filter(username => username !== removedUsername);
    });
});


// HashTag
const hashTagInputElement = document.getElementById('hashTag');
const hashTagContainer = document.getElementById('hashTag-container');
let hashTags = [];

hashTagInputElement.addEventListener('input', function () {
    var inputValue = hashTagInputElement.value;
    var hashTagNames = inputValue.split(' ').map(name => name.replace(/^#/, ''));
    console.log("==============>"+hashTagNames);
    if (hashTagNames.includes('')) {
        hashTagContainer.textContent = '';
        return;
    }
    hashTagContainer.textContent = '';

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
                        hashTags.push(`#${hashTag.name}`);
                        hashTagInputElement.value = hashTags.join(' ');
                        hashTagContainer.textContent = '';
                        console.log(hashTags);
                    });
                    hashTagContainer.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                hashTagContainer.textContent = '';
            });
    });
});

hashTagInputElement.addEventListener('input', function () {
    let inputHashtag = hashTagInputElement.value.split(' ');
    let removedHashtag = hashTags.filter(mentionedUsername => !inputHashtag.includes(mentionedUsername));

    hashTags = hashTags.filter(mentionedUsername => inputHashtag.includes(mentionedUsername));

    removedHashtag.forEach(removedUsername => {
        hashTags = hashTags.filter(username => username !== removedUsername);
    });
});

function clickFileUpload() {
    var upload = document.getElementById("file-upload");
    console.log(upload);

    upload.click();

    upload.addEventListener('change', function () {
        var firstModal = document.getElementById("Board_Create_Modal");
        $(firstModal).modal('hide'); // 첫 번째 모달 닫기

        var selectedFiles = event.target.files;
        console.log(selectedFiles);
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
                console.log(filesArray[i]);
                var carouselItem = document.createElement("div");
                carouselItem.classList.add("carousel-item");

                var imageContainer = document.createElement("div");
                imageContainer.id = "image_container" + i;
                imageContainer.style.position = "relative";
                imageContainer.style.width = "734px";
                imageContainer.style.height = "734px";

                var img = document.createElement("img");
                img.id = "name" + i;
                img.classList.add("shadow", "text-center");
                img.style.position = "absolute";
                img.style.top = "0";
                img.style.left = "0";
                img.style.transform = "translate(50,50)";
                img.style.width = "100%";
                img.style.height = "100%";
                img.style.objectFit = "cover";
                img.style.margin = "auto";
                img.src = URL.createObjectURL(filesArray[i]);

                var video = document.createElement("video");
                video.id = "name" + i;
                video.classList.add("shadow", "text-center");
                video.style.position = "absolute";
                video.style.top = "0";
                video.style.left = "0";
                video.style.transform = "translate(50,50)";
                video.style.width = "100%";
                video.style.height = "100%";
                video.style.objectFit = "cover";
                video.style.margin = "auto";
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
                console.log(files);
            }
            var carousel = document.getElementById("carouselExampleControlsNoTouching");
            var prevButton = carousel.querySelector(".carousel-control-prev");
            var nextButton = carousel.querySelector(".carousel-control-next");

            if (imagePreviewArray.length === 1 || videoPreviewArray.length === 1) {
                prevButton.classList.add("visually-hidden")
                nextButton.classList.add("visually-hidden")
            }
            files.firstElementChild.classList.add('active');
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

textarea.addEventListener('input', function () {
    const textLength = textarea.value.length;
    if (textLength > maxLength) {
        textarea.value = textarea.value.slice(0, maxLength);
    }

    textCount.textContent = `${textLength}`;

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


advancedSettingsToggle.addEventListener('click', function () {
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

accessibilitySettingsToggle.addEventListener('click', function () {
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

function reloadPage() {
    setTimeout(() => window.location.reload(), 10);
}