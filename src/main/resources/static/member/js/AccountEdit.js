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
    var username = document.getElementById('new-username').value;
    var nowUsername = document.getElementById('now-username').value;
    var btn = document.getElementById('username-btn');
    var span = document.getElementById('username-error');

    if (username === '')
    {
        btn.disabled = true;
        span.textContent = '사용자 이름은 필수항목입니다.';
    }
    else if(containsSpecialCharacters(username))
    {
        btn.disabled = true;
        span.textContent = '사용자 이름에는 문자, 숫자, 밑줄, 마침표만 사용할 수 있습니다.';
    }
    else
    {
        btn.disabled = username === nowUsername;
        span.textContent='';
    }
}

function changeUserName(){
    var form = document.getElementById('username-form');

    form.submit();
}

function containsSpecialCharacters(str) {
    return /[^\w._]/.test(str);
}

function changeStopUserName()
{
    debugger;
}