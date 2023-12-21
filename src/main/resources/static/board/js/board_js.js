function clickFileUpload() {
    var upload = document.getElementById("image-upload");
    upload.click();

    upload.addEventListener('change', function () {
        var firstModal = document.getElementById("Board_Create_Modal");
        $(firstModal).modal('hide'); // 첫 번째 모달 닫기

        var selectedFiles = event.target.files;
        let filesArray = [];
        // 선택된 각 파일을 배열에 추가하기
        for (let i = 0; i < selectedFiles.length; i++) {
            filesArray.push(selectedFiles[i]);
        }
        console.log(filesArray);

        var secondModal = document.getElementById("Board_Create_Modal2");
        $(firstModal).on('hidden.bs.modal', function () {
            $(firstModal).modal('dispose'); // 첫 번째 모달 삭제

            var modalBackdrop = document.querySelector('.modal-backdrop');
            modalBackdrop.parentNode.removeChild(modalBackdrop); // 모달 배경 제거

            let imagePreviewArray = [];
            var files = document.getElementById("img-container");
            for (let i = 0; i < filesArray.length; i++) {
                var carouselItem = document.createElement("div");
                carouselItem.classList.add("carousel-item");

                var imageContainer  = document.createElement("div");
                imageContainer.id="image_container" + i;
                imageContainer.style.position="relative";
                imageContainer.style.width="50rem";
                imageContainer.style.height="50rem";

                var img = document.createElement("img");
                img.id="name"+i;
                img.classList.add("shadow", "text-center");
                img.style.position="absolute";
                img.style.top="0";
                img.style.left="0";
                img.style.transform="translate(50,50";
                img.style.width="100%";
                img.style.height="100%";
                img.style.objectFit="cover";
                img.style.margin="auto";
                img.src = URL.createObjectURL(filesArray[i]);

                imagePreviewArray.push(img.src);

                imageContainer.appendChild(img);
                carouselItem.appendChild(imageContainer);
                files.appendChild(carouselItem);
            }
            carouselItem.classList.add('active');
            console.log(imagePreviewArray)
            $(secondModal).modal('show'); // 두 번째 모달 활성화
        });
    });
}

function removeModal() {
    var modal = document.getElementById('Back_Board_Create_Modal');
    modal.remove();
}