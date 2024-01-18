var sideCheck = document.getElementById('expand-direct-menu');
sideCheck.checked = true;

var sidebar = document.getElementById('side_bar');
sidebar.style.width = '4.5rem';

var sidemenu = document.getElementById('side_menu');
sidemenu.style.width = '4.5rem';


const tagMember = document.querySelector('input[id=member-search-input]'),
    tagify = new Tagify(tagMember,  {
    }),
    button = document.getElementsByName('member-check');

tagify.on('input', inputMember);
tagify.on('keydown', keydownTagify);
tagify.on('change', changeMember);

function changeMember()
{
    var submit = document.getElementById('chatting-submit');

    var value = tagify.getInputValue();
    if(value !== '')
    {
        var valList = JSON.parse(value);

        if(valList.length === 0)
        {
            submit.disabled = true;
        }
        else
        {
            submit.disabled = false;
        }
    }
    else
    {
        submit.disabled = true;
    }
}

function clickSubmit()
{
    var form = document.getElementById('chatting-form');

    form.submit();
}

function onAddButtonClick(input, nonMember, container){

    var lastStr = JSON.parse(tagify.getInputValue()).pop();
    var lastTag = tagify.getTagElmByValue(lastStr.value);
    tagify.removeTag(lastTag);

    if(input.checked)
    {
        tagify.addTags(input.value);
    }
    else
    {
        tagify.removeTag(input.value);
    }
    if(nonMember.classList.contains('visually-hidden'))
    {
        nonMember.classList.remove('visually-hidden');
    }
    container.innerHTML = '';
}

document.getElementById('directSendModal').addEventListener('keydown', function(event) {
    var input = document.querySelector('span[class=tagify__input]');
    if (event.key === 'Enter' && event.target === input) {
        console.clear();
        event.preventDefault(); // 엔터 키의 기본 동작을 차단
        return false; // 이벤트 처리 중단
    }
});

function keydownTagify(e)
{
    if (event.detail.event.key === "Enter") {
        e.preventDefault();
        e.detail.tagify.state = null;
        return e;
    }
}

function inputMember()
{
    var input = document.querySelector('span[class=tagify__input]');
    var nonMember = document.getElementById('non-member');
    var container = document.getElementById('member-container');

    if(input.textContent !== '')
    {
        fetch('/member/search/'+input.textContent)
            .then(response=>{
                return response.json()
            })
            .then(data => {
                if(data.result.length === 0)
                {
                    if(nonMember.classList.contains('visually-hidden'))
                    {
                        nonMember.classList.remove('visually-hidden');
                    }
                    container.innerHTML = '';
                }
                else
                {
                    if(!nonMember.classList.contains('visually-hidden'))
                    {
                        nonMember.classList.add('visually-hidden');
                    }

                    container.innerHTML = '';
                    data.result.forEach((member, index) => {
                        var resultMember = document.getElementsByClassName(member.username);

                        if(resultMember.length !== 0)
                        {
                            return;
                        }

                        var div = document.createElement('div');
                        div.classList = 'd-flex';

                        var div2 = document.createElement('div');
                        div2.style.position='relative';
                        div2.style.width='3rem';
                        div2.style.height='3rem';

                        var img = document.createElement('img');
                        if(member.image == null)
                        {
                            img.src='/files/designImg/noneuser.png';
                        }
                        else
                        {
                            img.src='/resources/'+member.image.name;
                        }
                        img.classList='rounded-circle text-center';
                        img.style.position='absolute';
                        img.style.top='0';
                        img.style.left='0';
                        img.style.transform='translate(50,50)';
                        img.style.width = '100%';
                        img.style.height = '100%';
                        img.style.objectFit='cover';
                        img.style.margin='auto';

                        div2.appendChild(img);
                        div.appendChild(div2);
                        container.appendChild(div);

                        var div3 = document.createElement('div');
                        div3.classList='ms-2 d-flex flex-fill';

                        var div4 = document.createElement('div');
                        div4.classList='flex-fill';

                        var div41 = document.createElement('div');
                        div41.classList='fw-bold';
                        if(member.nickname === '')
                        {
                            div41.textContent=member.username;
                        }
                        else
                        {
                            div41.textContent=member.nickname;
                        }

                        var div42 = document.createElement('div');
                        div42.textContent=member.username;
                        div42.classList.add(member.username);
                        div4.appendChild(div41);
                        div4.appendChild(div42);
                        div3.appendChild(div4);

                        var div5 = document.createElement('div');

                        var inputCheck = document.createElement('input');

                        inputCheck.type='checkbox';
                        inputCheck.classList='form-check mx-2';
                        inputCheck.name='member-check';
                        inputCheck.value=member.username;

                        var tagMem = tagify.getInputValue();

                        if(tagMem.includes(member.username))
                        {
                            inputCheck.checked = true;
                        }


                        inputCheck.onclick=function (e)
                        {
                            onAddButtonClick(inputCheck, nonMember, container);
                        };

                        div5.appendChild(inputCheck);
                        div3.appendChild(div5);

                        div.appendChild(div3);
                    });
                }
            })
            .catch(error=>{
                if(nonMember.classList.contains('visually-hidden'))
                {
                    nonMember.classList.remove('visually-hidden');
                }
                container.innerHTML = '';
            });
    }
    else
    {
        if(nonMember.classList.contains('visually-hidden'))
        {
            nonMember.classList.remove('visually-hidden');
        }
        container.innerHTML = '';
    }
}