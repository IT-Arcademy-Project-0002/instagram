function inputMember(input)
{
    var nonMember = document.getElementById('non-member');

    debugger;
    fetch('/member/search/'+input.value)
        .then(response=>{
            return response.json()
        })
        .then(data => {
            debugger;
        })
        .catch(error=>console.error('데이터를 받지 못했습니다.',error));
}