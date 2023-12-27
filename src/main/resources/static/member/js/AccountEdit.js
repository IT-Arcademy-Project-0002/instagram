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
