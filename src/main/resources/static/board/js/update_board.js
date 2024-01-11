// /board/update/{id}
function ModifyBoard(id) {
    // 모달 제거
    $('#FeedCommentModal').modal('hide');
    $('#CreateUserBoardSettingModal').modal('hide');
    $('#ModifyBoardModal').modal('show');

    console.log(id);
    fetch("/board/update/" + id)
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data);
            let imagePreviewArray = [];
            let videoPreviewArray = [];
            let filesArray = data.updateFeed.fileDTOS;
            console.log(filesArray);
            var file = document.getElementById('file-container');
            for (let i = 0; i < filesArray.length; i++) {
                var carouselItem = document.createElement("div");
                carouselItem.classList.add("carousel-item");

                var fileContainer = document.createElement("div");
                fileContainer.id = "file_container" + i;
                fileContainer.style.position = "relative";
                fileContainer.style.width = "55rem";
                fileContainer.style.height = "50rem";

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
                img.src = "/files/img/" + filesArray[i].name;

                console.log(img);
                imagePreviewArray.push(decodeURIComponent(img.src));
                fileContainer.appendChild(img);
                carouselItem.appendChild(fileContainer);
                file.appendChild(carouselItem);
            }

            var carousel = document.getElementById("carouselExampleControlsNoTouching");
            var prevButton = carousel.querySelector(".carousel-control-prev");
            var nextButton = carousel.querySelector(".carousel-control-next");

            if (imagePreviewArray.length === 1 || videoPreviewArray.length === 1) {
                prevButton.classList.add("visually-hidden")
                nextButton.classList.add("visually-hidden")
            }
            console.log(fileContainer);
            carouselItem.classList.add('active');
            console.log(imagePreviewArray);

            console.log(data.updateFeed.board.content);
            var contentTextarea = document.getElementById('modalContent');
            console.log(contentTextarea);
            contentTextarea.value = data.updateFeed.board.content;

            console.log(data.updateFeed.board.boardTagMembers);
            var boardTagMembers = document.getElementById('TagMember');
            console.log(boardTagMembers);
            boardTagMembers.value = data.updateFeed.board.boardTagMembers;

            console.log(data.updateFeed.board.boardHashTags);
            console.log(data.updateFeed.board.commentDisable);
            console.log(data.updateFeed.board.likeHide);
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}