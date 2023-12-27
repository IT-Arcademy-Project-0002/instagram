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