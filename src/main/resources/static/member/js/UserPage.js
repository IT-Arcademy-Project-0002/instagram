document.addEventListener('DOMContentLoaded', function() {
    debugger;
    const images = document.querySelectorAll('.smooth-update');

    images.forEach(function(img) {
        debugger;
        setTimeout(showImg(img),50)
    });
});

function showImg(img)
{
    img.style.opacity = 1;
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

function clickFollow()
{
    var username = document.getElementById('username').value;
    var spin = document.getElementById('follow-spin');
    var texts = document.getElementsByClassName('follow-text');

    for(var i = 0; i < texts.length; i++)
    {
        texts.item(i).textContent='';
    }
    spin.classList.remove('visually-hidden');

    debugger;
    fetch('/member/follow/'+username)
        .then(response => {
            return response.json()
        })
        .then(data => {
            setTimeout(reload,200);
        })
        .catch(error => console.error('데이터를 받지 못했습니다.',error));
}

function reload()
{
    window.location.reload();
}