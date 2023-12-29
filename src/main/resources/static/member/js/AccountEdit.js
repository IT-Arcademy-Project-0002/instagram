let timer;

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

function changeIntroduce()
{
    var btn = document.getElementById('change-btn');
    var nowIntroduce = document.getElementById('now-introduce').value;
    var introduce = document.getElementById('introduce').value;

    debugger;
    btn.disabled = nowIntroduce === introduce;
}

function changeSex(sex)
{
    debugger;
    var nowSex = document.getElementById('now-sex');
    var btn = document.getElementById('change-btn');

    var nowIntroduce = document.getElementById('now-introduce').value;
    var introduce = document.getElementById('introduce').value;

    debugger;
    if(nowIntroduce === introduce)
    {
        btn.disabled = nowSex.value === sex;
    }

    var text = document.getElementsByClassName('now-sex');

    text.item(0).text= sex==='male'?'남성': sex==='female'? '여성' : '밝히고 싶지 않음';

}

function changeProfile()
{
    debugger;
    var selectSex = document.getElementsByClassName('now-sex').item(0);
    var sex = document.getElementById('now-sex');

    if(selectSex.textContent==='밝히고 싶지 않음')
    {
        sex.value = null;
    }
    else if(selectSex.textContent === '여성')
    {
        sex.value = 'female';
    }
    else if(selectSex.textContent === '남성'){
        sex.value = 'male';
    }

    var writeIntroduce = document.getElementById('introduce');
    var introduce = document.getElementById('now-introduce');
    introduce.value=writeIntroduce.value;

    document.getElementById('change-profile').submit();
}

function clickProfilePhotoChange()
{
    debugger;
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

function changeName()
{
    var form = document.getElementById('name-form');

    form.submit();
}

function inputName()
{
    var name = document.getElementById('new-name').value;
    var nowName = document.getElementById('now-name').value;
    var btn = document.getElementById('name-btn');

    btn.disabled = name === nowName;
}


function inputUserName() {
    clearTimeout(timer);

    var username = document.getElementById('new-username').value;
    var nowUsername = document.getElementById('now-username').value;
    var btn = document.getElementById('username-btn');
    var span = document.getElementById('username-error');

    colorChange(span, false);
    if (username === '') {
        btn.disabled = true;
        span.textContent = '사용자 이름은 필수항목입니다.';
    } else if (containsSpecialCharacters(username)) {
        btn.disabled = true;
        span.textContent = '사용자 이름에는 문자, 숫자, 밑줄, 마침표만 사용할 수 있습니다.';
    } else if (username === nowUsername) {
        btn.disabled = true;
        span.textContent = '';
    } else {
        span.textContent = '';
        timer = setTimeout(changeStopUserName, 1000); // 3 seconds in milliseconds
    }
}

function changeStopUserName()
{
    var spin = document.getElementById("username-spin");
    spin.classList.remove("visually-hidden");

    setTimeout(duplicUsername, 1500);
}

function duplicUsername()
{
    var username = document.getElementById('new-username').value;
    fetch("/member/duplicUserName/"+username)
        .then(response=>{
            var spin = document.getElementById("username-spin");
            spin.classList.add("visually-hidden");
            return response.json()
        })
        .then(data => {
            var btn = document.getElementById('username-btn');
            var span = document.getElementById('username-error');

            debugger;
            if(data.result)
            {
                btn.disabled = false;
                colorChange(span, true);
                span.textContent='사용 가능한 사용자 이름입니다.';
            }
            else
            {
                btn.disabled = true;
                colorChange(span, false);
                span.textContent='다른 사용자가 사용 중인 이름입니다.';
            }
        })
        .catch(error=>console.error('데이터를 받지 못했습니다.',error));
}

function colorChange(span, color)
{
    if(color)
    {
        if(span.classList.contains("text-danger"))
        {
            span.classList.add("text-success");
            span.classList.remove("text-danger");
        }
    }
    else
    {
        if(span.classList.contains("text-success"))
        {
            span.classList.add("text-danger");
            span.classList.remove("text-success");
        }
    }
}

function changeUserName(){
    var form = document.getElementById('username-form');

    form.submit();
}

function containsSpecialCharacters(str) {
    return /[^\w._]/.test(str);
}

function changePrivacy(btn)
{
    if(btn.checked)
    {
        var res = confirm("비공개 계정을 하시겠습니까?");
        if(res)
        {
            document.getElementById('privacySetting-form').submit();
        }
        else
        {
            btn.checked = false;
        }
    }
    else
    {
        btn.checked = false;
        document.getElementById('privacySetting-form').submit();
    }
}