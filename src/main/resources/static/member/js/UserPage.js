

const username = document.getElementById('page-username').value;
const referer = document.getElementById('referer').value;
const scroll = document.getElementById('scroll').value;
const loginMember = document.getElementById("loginMemberName").value;

scrollSetting(scroll);


debugger;
if (referer === "detail") {
    document.getElementById('BoardDetailModal-button').click();
}
document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.smooth-update');

    images.forEach(function(img) {
        setTimeout(showImg(img),50)
    });
});

function showImg(img)
{
    img.style.opacity = 1;
}

function SaveGroupCreate()
{
    var name = document.getElementById('GroupName').value;

    window.location.href = '/saveGroup/create/' + name;
}

function clickProfilePhotoChange()
{
    var fileInput = document.getElementById('profile-photo-input');

    fileInput.click();
}

function readFile(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.addEventListener('load', function (e) {
            const str = e.target.result.toString();

            var img = document.getElementById('user_img');
            img.setAttribute('src', str);
        });
        reader.readAsDataURL(input.files[0]);

        document.getElementById('img-form').submit();
    }
}

function boardPinChange(board)
{
    var input = document.getElementById("board");

    input.value = board.value;

    document.getElementById("board-pin-form").submit();
}

function boardKeep(board)
{
    var input = document.getElementById("keep");

    input.value = board.value;

    document.getElementById("board-keep-form").submit();
}

function clickBlockCancel()
{
    var username = document.getElementById('page-username').value;

    fetch('/member/block/cancel/' + username)
        .then(response => response.json())
        .then(data => {
            reload();
        })
        .catch(error => {
            // 에러 처리
            console.error('Error:', error);
        });
}

function clickBlock()
{
    var username = document.getElementById('page-username').value;

    fetch('/member/block/' + username)
        .then(response => response.json())
        .then(data => {
            reload();
        })
        .catch(error => {
            // 에러 처리
            console.error('Error:', error);
        });
}


function reload()
{
    window.location.reload();
}

function reloadNoOption()
{
    var currentURL = window.location.href;
    window.location.href = currentURL.split('?')[0];
}

function clickShowDetailBoard(id)
{
    debugger;
    var scroll = getScrollPosition();
    var username = document.getElementById('page-username').value;
    window.location.href = '/member/page/' + username + '?id=' + id + '&page=detail&scroll=' + scroll;
}

function getScrollPosition() {
    if (window.pageYOffset !== undefined) {
        // 대부분의 브라우저에서 지원하는 방법
        return window.pageYOffset;
    } else {
        // IE 8 및 하위 버전에서는 scrollY 대신 scrollTop을 사용
        return (document.documentElement || document.body.parentNode || document.body).scrollTop;
    }
}

function scrollSetting(scroll) {

    debugger;
    var objDiv = document.documentElement || document.body;

    objDiv.scrollTop = scroll;
}