function clickProfilePhotoChange()
{
    var fileInput = document.getElementById('profile-photo-input');

    fileInput.click();
}

function readFile(input) {
    debugger;
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
    debugger;
    var input = document.getElementById("board");

    input.value = board.value;

    document.getElementById("board-pin-form").submit();
}

function boardKeep(board)
{
    debugger;
    var input = document.getElementById("keep");

    input.value = board.value;

    document.getElementById("board-keep-form").submit();
}