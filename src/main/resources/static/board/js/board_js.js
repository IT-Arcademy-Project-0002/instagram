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

            var imagePreview = document.getElementById("name");
            for (let i = 0; i < filesArray.length; i++) {
                imagePreview.src = URL.createObjectURL(filesArray[i]);
                imagePreviewArray.push(imagePreview.src)
            }
            console.log(imagePreviewArray)
            $(secondModal).modal('show'); // 두 번째 모달 활성화
        });
    });
}