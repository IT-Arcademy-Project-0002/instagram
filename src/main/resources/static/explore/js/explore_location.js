$(document).ready(function () {
    // 현재 페이지의 URL에서 locationId 추출
    var locationId = window.location.pathname.split('/').pop();

    $.ajax({
        url: '/explore/locations/locationMap',
        type: 'GET',
        dataType: 'json',
        data: {
            locationId: locationId
        }
    }).done(function (location) {
        // Ajax 요청이 완료된 후에 실행될 코드
        var map = new mapboxgl.Map({
            container: 'map',
            style: 'https://www.greenwalker.xyz/bright.json',
            center: [location.x, location.y],
            zoom: 14,
            maxZoom: 14,
            minZoom: 14
        });

        var marker = new mapboxgl.Marker({color: 'red'}).setLngLat([location.x, location.y]).addTo(map);
    }).fail(function (error) {
        console.error('Error:', error);
    });
});