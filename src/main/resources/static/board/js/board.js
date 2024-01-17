// 댓글 상단고정
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

//댓글 삭제
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

// 대댓글 삭제
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

// 댓글 사용자 언급 처리 기능
function Comment(id) {
    var commedInput = document.getElementById('commentID');
    commedInput.value = id;

    var user = document.getElementById('comment-username' + id);
    var textarea = document.getElementsByClassName('modal-textarea-content')[0];
    var isRecomment = document.getElementById('isRecomment');

    textarea.value = '@' + user.textContent + " ";
    isRecomment.value = 'true';
}

// 대댓글 사용자 언급 처리 기능
function Recomment(commentId, recommentId) {
    var commedInput = document.getElementById('commentID');
    commedInput.value = commentId;

    var recommedInput = document.getElementById('recommentID');
    recommedInput.value = recommentId;

    var user = document.getElementById('recomment-username' + recommentId);
    var textarea = document.getElementsByClassName('modal-textarea-content')[0];
    var isRecomment = document.getElementById('isRecomment');

    textarea.value = '@' + user.textContent + " ";
    isRecomment.value = 'true';
}

// main페이지 or 모달에서 댓글/대댓글 작성기능
function clickCommentBtn() {
    debugger;
    const boardID = document.getElementById('boardID').innerText;
    console.log(boardID);
    const commentID = document.getElementById('commentID').innerText;
    console.log(commentID);

    // CSRF 토큰 가져오기
    var token = $("input[name='_csrf']").attr("value");
    var header = $("input[name='_csrf_header']").attr("value");

    var isRecomment = document.getElementById('isRecomment').value;

    var inputtext = document.getElementById("modal-content").value;
    var formTextEl = document.createElement("textarea");
    console.log(formTextEl);

    formTextEl.setAttribute('hidden', true);
    formTextEl.setAttribute('name', 'content');
    formTextEl.innerHTML = inputtext;

    var commentData = {
        content: formTextEl.innerHTML
    }
    console.log(commentData);

    var form = null;
    if (isRecomment === 'true') {
        var commedInput = document.getElementById('commentID');
        var commendID = commedInput.value;
        console.log(recommendID);
        // fetch (링크 +recommentid )
        // form.action = '/recomment/create/' + commendID;
        // form.appendChild(formTextEl);
        // form.submit();

        fetch("/recomment/create/" + commendID, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                [header]: token
            },
            body: JSON.stringify(commentData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('데이터를 받지 못했습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log(data.comment);
            })
            .catch(error => console.error(error));

    } else {
        // form = document.getElementById('comment-form')
        fetch("/comment/modalCreate/" + boardID, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                [header]: token
            },
            body: JSON.stringify(commentData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('데이터를 받지 못했습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log(data.comment.content);
                console.log(data.comment);
                let imgDto = data.comment.memberDTO.imageDTO;
                let imgName = "/files/designImg/noneuser.png";

                if(imgDto != null) {
                    imgName = imgDto.name;
                }
                // Creating a new comment div
                var newCommentDiv = document.createElement('div');
                newCommentDiv.classList.add('d-flex', 'comment-container');
                newCommentDiv.style.padding = '16px';
                newCommentDiv.innerHTML = `
                    <div class="d-flex justify-content-center">
                        <div class="me-3"
                             style="position: relative; width: 2rem; height: 2rem;">
                            <img id="none_user_img"
                                 src=${imgName}
                                 class="shadow rounded-circle text-center"
                                 style="position: absolute; top: 0; right: 0; width: 100%; height: 100%; object-fit: cover;">
                        </div>
                    </div>
                    <div class="d-flex justify-content-center align-items-center w-100">
                        <div class="d-flex flex-column w-100">
                            <div class="d-flex mb-2">
                                <div class="pe-2"
                                     id="comment-username${data.comment.id}">` + data.comment.memberDTO.username + `</div>
                                <div> ` + data.comment.content + `</div>
                            </div>
                            <div class="d-flex w-80">
                                <div class="pe-2 text-dark small"
                                     style="width: 30%; font-size: 12px;">` + data.formattedDate + `</div>
                                <div class="pe-2 text-dark small" id="modalCommentLikeCount${data.comment.id}"
                                     style="width: 20%; font-size: 12px;"> `+ '좋아요 0개' + `</div>

                                <!--대댓글 달기-->
                                <a class="btn noneBtn btn-sm btn-link text-dark small p-0 pe-2"
                                    style="width: 20%; font-size: 12px;"
                                    id="commentReplyBtn${data.comment.id}">답글 달기</a>

                                <!-- Button trigger modal -->
                                <button type="button"
                                        style="font-size: 12px; width: 10%;"
                                        class="btn noneBtn btn-sm btn-link p-0 commentBtn"
                                        data-bs-toggle="modal"
                                        data-id="${data.comment.id}"
                                        data-bs-target=#BoardUserCommentModal>
                                    <svg aria-label="옵션 더 보기"
                                         fill="currentColor" height="24" role="img" viewBox="0 0 24 24"
                                         width="24"><title>옵션 더 보기</title>
                                        <circle cx="12" cy="12" r="1.5"></circle>
                                        <circle cx="6" cy="12" r="1.5"></circle>
                                        <circle cx="18" cy="12" r="1.5"></circle>
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div>
                            <a onclick="ModalCommentClickLike(${data.comment.id})">
                                <svg id="modal-comment-liked-svg${data.comment.id}"
                                     class=""
                                     aria-label="좋아요"
                                     fill="currentColor"
                                     height="18" width="18" role="img"
                                     viewBox="0 0 24 24"
                                     style="color: black;">
                                    <path d="M16.792 3.904A4.989 4.989 0 0 1 21.5 9.122c0 3.072-2.652 4.959-5.197 7.222-2.512 2.243-3.865 3.469-4.303 3.752-.477-.309-2.143-1.823-4.303-3.752C5.141 14.072 2.5 12.167 2.5 9.122a4.989 4.989 0 0 1 4.708-5.218 4.21 4.21 0 0 1 3.675 1.941c.84 1.175.98 1.763 1.12 1.763s.278-.588 1.11-1.766a4.17 4.17 0 0 1 3.679-1.938m0-2a6.04 6.04 0 0 0-4.797 2.127 6.052 6.052 0 0 0-4.787-2.127A6.985 6.985 0 0 0 .5 9.122c0 3.61 2.55 5.827 5.015 7.97.283.246.569.494.853.747l1.027.918a44.998 44.998 0 0 0 3.518 3.018 2 2 0 0 0 2.174 0 45.263 45.263 0 0 0 3.626-3.115l.922-.824c.293-.26.59-.519.885-.774 2.334-2.025 4.98-4.32 4.98-7.94a6.985 6.985 0 0 0-6.708-7.218Z"></path>
                                </svg>
                                <svg id="modal-comment-not-like-svg${data.comment.id}"
                                     class="visually-hidden"
                                     aria-label="좋아요 취소"
                                     fill="currentColor"
                                     height="18" width="18" role="img"
                                     viewBox="0 0 48 48"
                                     style="color:red;">
                                    <path d="M34.6 3.1c-4.5 0-7.9 1.8-10.6 5.6-2.7-3.7-6.1-5.5-10.6-5.5C6 3.1 0 9.6 0 17.6c0 7.3 5.4 12 10.6 16.5.6.5 1.3 1.1 1.9 1.7l2.3 2c4.4 3.9 6.6 5.9 7.6 6.5.5.3 1.1.5 1.6.5s1.1-.2 1.6-.5c1-.6 2.8-2.2 7.8-6.8l2-1.8c.7-.6 1.3-1.2 2-1.7C42.7 29.6 48 25 48 17.6c0-8-6-14.5-13.4-14.5z"></path>
                                </svg>
                            </a>
                        </div>
                    </div>
                `;
                // Appending the new comment div to the comment container
                var commentContainer = document.getElementById('commentList');
                commentContainer.appendChild(newCommentDiv);

                var replyButton = document.getElementById(`commentReplyBtn${data.comment.id}`);
                replyButton.addEventListener('click', function() {
                    Comment(data.comment.id);
                });

                // 비동기 처리를 고려하여 textarea의 내용을 비워줍니다.
                document.getElementById("modal-content").value='';
            })
            .catch(error => console.error(error));
    }
}

// 모달에서 대댓글 더보기/접기 기능
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

// main 페이지에서 게시글 좋아요
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
                if (data.like_hide) {
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                } else {
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            } else {
                liked.classList.remove('visually-hidden');
                notLike.classList.add('visually-hidden');
                if (data.like_hide) {
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                } else {
                    document.getElementById('likeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

// 모달에서 게시글 좋아요
function ModalclickLike(id) {
    fetch("/board/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal = document.getElementById('BoardDetailModal');
            var modalLiked = modal.querySelector('#modal-liked-svg' + id);
            var modalNotLike = modal.querySelector('#modal-not-like-svg' + id);


            if (data.result) {
                modalLiked.classList.add('visually-hidden');
                modalNotLike.classList.remove('visually-hidden');
                if (data.like_hide) {
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                } else {
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            } else {
                modalLiked.classList.remove('visually-hidden');
                modalNotLike.classList.add('visually-hidden');
                if (data.like_hide) {
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                } else {
                    document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                }
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

// 모달에서 댓글 좋아요
function ModalCommentClickLike(id) {
    fetch("/comment/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal_comment = document.getElementById('BoardDetailModal');
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

// 모달에서 대댓글 좋아요
function ModalRecommentClickLike(id) {
    fetch("/recomment/like/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal_recomment = document.getElementById('BoardDetailModal');
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

// main페이지에서 게시글 저장
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

// 모달에서 게시글 저장
function ModalclickSaveGroup(id) {
    fetch("/board/saveGroup/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            var modal = document.getElementById('BoardDetailModal');
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

// 좋아요 보이기/숨기기 비동기 코드
function likehide(id) {
    fetch("/board/likehide/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            if (data.likehide) {
                document.getElementById("modalLikeCount").innerHTML = `<div>좋아요 여러개 입니다.</div>`;
                let likehideBtn = document.getElementById("likehideBtn");
                if (likehideBtn != null) {
                    document.getElementById("likehideBtn").innerText = "좋아요 수 숨기기 취소";
                }
            } else {
                document.getElementById('modalLikeCount').innerHTML = `<div>좋아요 ${data.likeCount}개</div>`;
                let likehideBtn = document.getElementById("likehideBtn");
                if (likehideBtn != null) {
                    document.getElementById("likehideBtn").innerText = "좋아요 수 숨기기";
                }
            }
        })
        .then(() => {
            $('#CreateUserBoardSettingModal').modal('hide')
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

// 댓글 활성/비활성화 비동기 코드
function comment_disable(id) {
    fetch("/board/comment_disable/" + id)
        .then(response => {
            return response.json()
        })
        .then(data => {
            debugger;
            if (data.commentDisable) {
                document.getElementById("ModalCommentDisable").style.visibility = "hidden";
                let commentDisableBtn = document.getElementById("commentDisableBtn");
                if (commentDisableBtn != null) {
                    document.getElementById("commentDisableBtn").innerText = "댓글 활성화";
                }
            } else {
                document.getElementById('ModalCommentDisable').style.visibility = "visible";
                let commentDisableBtn = document.getElementById("commentDisableBtn");
                if (commentDisableBtn != null) {
                    document.getElementById("commentDisableBtn").innerText = "댓글 비활성화";
                }
            }
        })
        .then(() => {
            $('#CreateUserBoardSettingModal').modal('hide')
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

// 모달 종료했을때 페이지 로딩 10밀리초 후 페이지 생성
function reloadPage() {
    setTimeout(() => window.location.reload(), 10);
}

// 게시글 내용 일정 길이 넘어가면 더보기.. 버튼 생성 (접기로 조절 가능)
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

