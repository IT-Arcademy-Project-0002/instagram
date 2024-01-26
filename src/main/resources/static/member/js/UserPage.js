

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

function reload()
{
    window.location.reload();
}