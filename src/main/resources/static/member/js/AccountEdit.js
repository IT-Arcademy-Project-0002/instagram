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

function changeSex(sex)
{
    debugger;
    var nowSex = document.getElementById('now-sex');
    var btn = document.getElementById('change-btn');

    var nowInterest = document.getElementById('now-interest').value;
    var interest = document.getElementById('interest').value;

    if(nowInterest === interest)
    {
        btn.disabled = nowSex.value === sex;
    }

    var text = document.getElementsByClassName('now-sex');

    for(var i = 0; text.length; i++)
    {
        text.item(i).text= sex==='male'?'남성': sex==='female'? '여성' : '밝히고 싶지 않음';
    }
}