const tagMember = document.querySelector('input[id=member-search-input]'),
    tagify = new Tagify(tagMember,  {
        // email address validation (https://stackoverflow.com/a/46181/104380)
        pattern: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
        callbacks: {
            "invalid": onInvalidTag
        }
    }),
    button = document.getElementsByName('member-check');

tagify.on('input', inputMember)
function onAddButtonClick(input){
    tagify.addTags(input.value);
}

function inputMember()
{
    debugger;
    var input = document.querySelector('span[class=tagify__input]');


    var nonMember = document.getElementById('non-member');

    if(input.textContent !== '')
    {
        debugger;
        fetch('/member/search/'+input.textContent)
            .then(response=>{
                return response.json()
            })
            .then(data => {
                debugger;
                if(data.result.length === 0)
                {
                    if(nonMember.classList.contains('visually-hidden'))
                    {
                        nonMember.classList.remove('visually-hidden');
                    }
                }
                else
                {
                    if(!nonMember.classList.contains('visually-hidden'))
                    {
                        nonMember.classList.add('visually-hidden');
                    }

                    var container = document.getElementById('member-container');


                    var div = document.createElement('div');
                    div.classList = ''

                }
            })
            .catch(error=>{
                if(!nonMember.classList.contains('visually-hidden'))
                {
                    nonMember.classList.add('visually-hidden');
                }
            });
    }
    else
    {
        if(nonMember.classList.contains('visually-hidden'))
        {
            nonMember.classList.remove('visually-hidden');
        }
    }
}

