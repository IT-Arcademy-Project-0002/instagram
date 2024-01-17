function commentPin() {
    var comment = document.querySelector("#BoardUserCommentModal");
    console.log(comment);
    var button = document.querySelector(".btn[data-bs-target='#BoardUserCommentModal']");
    console.log(button);

    var id = button.getAttribute('data-id');
    console.log(id);
    const commentPinLink = comment.querySelector('.comment-pin');
    console.log(commentPinLink)

    commentPinLink.setAttribute('href', '/comment/pin/' + id);
}

function commentDelete() {
    var commentDelete = document.querySelector("#BoardUserCommentModal");
    console.log(commentDelete);
    var commentDeleteButton = document.querySelector(".btn[data-bs-target='#BoardUserCommentModal']");
    console.log(commentDeleteButton);

    var id = commentDeleteButton.getAttribute('data-id');
    console.log(id);
    const commentDeleteLink = commentDelete.querySelector('.comment-delete');
    console.log(commentDeleteLink)

    commentDeleteLink.setAttribute('href', '/comment/delete/' + id);
}

function recommentDelete() {
    debugger;
    var recommentDelete = document.querySelector("#ReCommentCreateUserModal");
    console.log(recommentDelete);
    var recommentDeleteButton = document.querySelector(".btn[data-bs-target='#ReCommentCreateUserModal']");
    console.log(recommentDeleteButton);

    var id = recommentDeleteButton.getAttribute('data-id');
    console.log(id);
    const recommentDeleteLink = recommentDelete.querySelector('.recomment-delete');
    console.log(recommentDeleteLink)

    recommentDeleteLink.setAttribute('href', '/recomment/delete/' + id);
}

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
    var likeCount = document.getElementById("likeCount");
    console.log(likeCount);
    fetch("/board/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var liked = document.getElementById('liked-svg' + id);
            var notLike = document.getElementById('not-like-svg' + id);
            console.log(data.likeCount);
            if (data.result) {
                liked.classList.add('visually-hidden');
                notLike.classList.remove('visually-hidden');
                if(data.like_hide){
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                }else{
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            } else {
                liked.classList.remove('visually-hidden');
                notLike.classList.add('visually-hidden');
                if(data.like_hide){
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                }else{
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
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
                if(data.like_hide){
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                }else{
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            } else {
                modalLiked.classList.remove('visually-hidden');
                modalNotLike.classList.add('visually-hidden');
                if(data.like_hide){
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                }else{
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function ModalCommentClickLike(id){
    fetch("/comment/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal_comment = document.getElementById('FeedCommentModal');
            var modal_comment_Liked = modal_comment.querySelector('#modal-comment-liked-svg' + id);
            console.log(modal_comment_Liked);
            var modal_comment_NotLike = modal_comment.querySelector('#modal-comment-not-like-svg' + id);
            console.log(modal_comment_NotLike);

            if (data.result) {
                modal_comment_Liked.classList.add('visually-hidden');
                modal_comment_NotLike.classList.remove('visually-hidden');
                document.getElementById('modalCommentLikeCount' + id).innerHTML = `<div>좋아요 ${data.commentLikeCount}개</div>`;

            } else {
                modal_comment_Liked.classList.remove('visually-hidden');
                modal_comment_NotLike.classList.add('visually-hidden');
                document.getElementById('modalCommentLikeCount' + id).innerHTML = `<div>좋아요 ${data.commentLikeCount}개</div>`;
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function ModalRecommentClickLike(id){
    fetch("/recomment/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal_recomment = document.getElementById('FeedCommentModal');
            var modal_recomment_Liked = modal_recomment.querySelector('#modal-recomment-liked-svg' + id);
            console.log(modal_recomment_Liked);
            var modal_recomment_NotLike = modal_recomment.querySelector('#modal-recomment-not-like-svg' + id);
            console.log(modal_recomment_NotLike);

            if (data.result) {
                modal_recomment_Liked.classList.add('visually-hidden');
                modal_recomment_NotLike.classList.remove('visually-hidden');
                document.getElementById('modalRecommentLikeCount' + id).innerHTML = `<div>좋아요 ${data.recommentLikeCount}개</div>`;

            } else {
                modal_recomment_Liked.classList.remove('visually-hidden');
                modal_recomment_NotLike.classList.add('visually-hidden');
                document.getElementById('modalRecommentLikeCount' + id).innerHTML = `<div>좋아요 ${data.recommentLikeCount}개</div>`;
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
                alert("게시글 저장 완료");
            } else {
                saved.classList.remove('visually-hidden');
                notSave.classList.add('visually-hidden');
                alert("게시글 저장 해지");
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
                alert("게시글 저장 완료");
            } else {
                modalsaved.classList.remove('visually-hidden');
                modalnotSave.classList.add('visually-hidden');
                alert("게시글 저장 해지");
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function likehide(id){
    fetch("/board/likehide/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            if (data.likehide) {
                document.getElementById("modalLikeCount").innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                let likehideBtn = document.getElementById("likehideBtn");
                if( likehideBtn != null) {
                    document.getElementById("likehideBtn").innerText = "좋아요 수 숨기기 취소";
                }
            } else {
                document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                let likehideBtn = document.getElementById("likehideBtn");
                if( likehideBtn != null) {
                    document.getElementById("likehideBtn").innerText = "좋아요 수 숨기기";
                }
            }
        })
        .then(() =>{
            $('#CreateUserBoardSettingModal').modal('hide')
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

$(document).ready(function () {
    $(".show-more-btn").each(function () {
        var contentContainer = $(this).prev(".content-container");
        var buttonText = contentContainer.css("max-height") === "45px" ? "... 더보기" : "접기";

        $(this).text(buttonText);

        if (contentContainer[0].scrollHeight <= 48) {
            $(this).hide();
        }
    });

    $(".show-more-btn").click(function () {
        var contentContainer = $(this).prev(".content-container");

        if (contentContainer.css("max-height") === "45px") {
            contentContainer.css("max-height", "none");
            $(this).text("접기");
        } else {
            contentContainer.css("max-height", "45px");
            $(this).text("... 더보기");
        }
    });
});
