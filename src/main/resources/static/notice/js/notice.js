// -------------------------- 8-1 : 팔로우 관련 스크립트 시작(이번주) ---------------------
function clickFollowApply() {
    var username = document.getElementById('username').value;
    var spin = document.getElementById('request-follow-spin');
    var texts = document.getElementsByClassName('request-follow-text');

    for (var i = 0; i < texts.length; i++) {
        texts.item(i).textContent = '';
    }
    spin.classList.remove('visually-hidden');

    debugger;
    fetch('/member/requestFollow/' + username)
        .then(response => {
            return response.json()
        })
        .then(data => {
            spin.classList.add('visually-hidden');
            var apply = document.getElementById('apply-container');
            var follow = document.getElementById('follow-container');
            if (data.result) {
                apply.classList.add('visually-hidden');
                follow.classList.remove('visually-hidden');
            }
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function clickFollowDelete(id) {
    debugger;
    fetch("/member/requestFollow/delete/" + id)
        .then(response => {
            return response.json();
        })
        .then(data => {
            var container = document.getElementById('notice-' + id);

            container.classList.add('visually-hidden');
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function clickFollow() {
    var username = document.getElementById('username').value;
    var spin = document.getElementById('follow-spin');
    var text = document.getElementById('follow-text');
    var btn = document.getElementById('follow-btn');

    text.textContent = '';

    spin.classList.remove('visually-hidden');

    debugger;
    fetch('/member/follow/' + username)
        .then(response => {
            return response.json()
        })
        .then(data => {
            setTimeout(function () {
                spin.classList.add('visually-hidden');
                if (data.result) {
                    text.textContent = '팔로잉';

                    btn.classList = 'ms-2 btn btn-outline-secondary w-75';
                } else {
                    text.textContent = '팔로우';

                    btn.classList = 'ms-2 btn btn-primary text-light w-75';
                }
            }, 1500);
        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}
// -------------------------- 8 : 팔로우 관련 스크립트 종료 --------------------------
