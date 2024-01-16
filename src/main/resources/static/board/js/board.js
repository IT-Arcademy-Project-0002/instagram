
document.addEventListener('DOMContentLoaded', function () {
    let commentModals = document.querySelectorAll('.modal');

    commentModals.forEach(function (modal) {
        modal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-id');
            console.log(id);

            const recomment_deleteLink = modal.querySelector('.recomment-delete');
            const deleteLink = modal.querySelector('.comment-delete');
            console.log(deleteLink);
            console.log(recomment_deleteLink);

            if (deleteLink) {
                deleteLink.setAttribute('href', '/comment/delete/' + id);
            } else if (recomment_deleteLink) {
                recomment_deleteLink.setAttribute('href', '/recomment/delete/' + id);
            }
        });
    });
});

function Comment(id) {
    var commedInput = document.getElementById('commentId');
    commedInput.value = id;

    var user = document.getElementById('comment-username' + id);
    var input = document.getElementById('modal-content');
    var isRecomment = document.getElementById('isRecomment');

    input.textContent = '@' + user.textContent + " ";
    isRecomment.value = 'true';
}

function Recomment(commentId, recommentId) {
    var commedInput = document.getElementById('commentId');
    commedInput.value = commentId;

    var recommedInput = document.getElementById('recommentId');
    recommedInput.value = recommentId;

    var user = document.getElementById('recomment-username' + recommentId);
    var input = document.getElementById('modal-content');
    var isRecomment = document.getElementById('isRecomment');

    input.textContent = '@' + user.textContent + " ";
    isRecomment.value = 'true';
}

function clickCommentBtn() {
    var isRecomment = document.getElementById('isRecomment').value;

    var inputtext = document.getElementById("modal-content").value;
    var formTextEl = document.createElement("textarea");

    formTextEl.setAttribute('hidden', true);
    formTextEl.setAttribute('name', 'content');
    formTextEl.innerHTML = inputtext;

    var form = null;
    if (isRecomment === 'true') {
        form = document.getElementById('recomment-form');

        var recommedInput = document.getElementById('commentId');
        var recommentid = recommedInput.value;
        form.action = '/recomment/create/' + recommentid;
    } else {
        form = document.getElementById('comment-form')
    }
    form.appendChild(formTextEl);
    form.submit();
}

function ReCommentContainerToggleReplies(commentId) {
    var repliesContainer = document.getElementById('repliesContainer-' + commentId);
    var viewButton = document.getElementById('recommentContainer-' + commentId);
    var countButton = document.getElementById('recommentCount-' + commentId);

    if (repliesContainer.style.display === 'none') {
        repliesContainer.style.display = 'block';
        viewButton.style.display = 'block';
        countButton.style.display = 'none';
    } else {
        repliesContainer.style.display = 'none';
        viewButton.style.display = 'none';
        countButton.style.display = 'block';
    }
}

// /board/like/{id}
function clickLike(id) {
    fetch("/board/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var liked = document.getElementById('liked-svg' + id);
            var notLike = document.getElementById('not-like-svg' + id);

            if (data.result) {
                liked.classList.add('visually-hidden');
                notLike.classList.remove('visually-hidden');
            } else {
                liked.classList.remove('visually-hidden');
                notLike.classList.add('visually-hidden');
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function ModalclickLike(id) {
    fetch("/board/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal = document.getElementById('FeedCommentModal');
            var modalLiked = modal.querySelector('#modal-liked-svg' + id);
            var modalNotLike = modal.querySelector('#modal-not-like-svg' + id);


            if (data.result) {
                modalLiked.classList.add('visually-hidden');
                modalNotLike.classList.remove('visually-hidden');
            } else {
                modalLiked.classList.remove('visually-hidden');
                modalNotLike.classList.add('visually-hidden');
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function clickSaveGroup(id) {
    fetch("/board/saveGroup/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var saved = document.getElementById('saved-svg' + id);
            var notSave = document.getElementById('not-save-svg' + id);

            if (data.result) {
                saved.classList.add('visually-hidden');
                notSave.classList.remove('visually-hidden');
            } else {
                saved.classList.remove('visually-hidden');
                notSave.classList.add('visually-hidden');
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function ModalclickSaveGroup(id) {
    fetch("/board/saveGroup/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal = document.getElementById('FeedCommentModal');
            var modalsaved = modal.querySelector('#modal-saved-svg' + id);
            var modalnotSave = modal.querySelector('#modal-not-save-svg' + id);

            if (data.result) {
                modalsaved.classList.add('visually-hidden');
                modalnotSave.classList.remove('visually-hidden');
            } else {
                modalsaved.classList.remove('visually-hidden');
                modalnotSave.classList.add('visually-hidden');
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

$(document).ready(function () {
    $(".show-more-btn").each(function () {
        var contentContainer = $(this).prev(".content-container");
        var buttonText = contentContainer.css("max-height") === "100px" ? "... 더보기" : "접기";

        $(this).text(buttonText);

        // Check content length and hide the button if not needed
        if (contentContainer[0].scrollHeight <= 100) {
            $(this).hide();
        }
    });

    $(".show-more-btn").click(function () {
        var contentContainer = $(this).prev(".content-container");

        if (contentContainer.css("max-height") === "100px") {
            contentContainer.css("max-height", "none");
            $(this).text("접기");
        } else {
            contentContainer.css("max-height", "100px");
            $(this).text("... 더보기");
        }
    });
});

