package com.instargram.instargram.Community.Location.Service;

import com.instargram.instargram.Community.Board.Model.Entity.Board;
import com.instargram.instargram.Community.Board.Model.Entity.Board_HashTag_Map;
import com.instargram.instargram.Community.Board.Model.Repository.BoardRepository;
import com.instargram.instargram.Community.Board.Model.Repository.Board_HashTag_MapRepository;
import com.instargram.instargram.Community.HashTag.Model.Entity.HashTag;
import com.instargram.instargram.Community.Location.Model.DTO.LocationDTO;
import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Community.Location.Model.Form.LocationForm;
import com.instargram.instargram.Community.Location.Model.Repository.LocationRepository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocationService {

    @Value("${kakao.api.key}")
    private String kakaoLocalKey;

    private final String uri1 = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private final String uri2 = "https://dapi.kakao.com/v2/local/search/address.json";

    private final LocationRepository locationRepository;
    private final BoardRepository boardRepository;


    public Location getLocationByLocationId(String id) {

        return this.locationRepository.findByLocationId(id);
    }

    public List<Location> getLocationListFindById(String id) {

        return this.locationRepository.findLocationListByLocationId(id);
    }

    public List<Board> getBoardFindByLocation(List<Location> location) {

        return this.boardRepository.findByLocationIn(location);
    }

    public Location exists(String locationId) {
        return this.locationRepository.findByLocationId(locationId);
    }

    public Location create(LocationDTO locationDTO) {
        if (isValidLocationDTO(locationDTO)) {
            Location location = new Location();

            location.setLocationId(locationDTO.getLocationId());
            location.setPlaceName(locationDTO.getPlaceName());
            if (locationDTO.getRoadAddressName() != null)  {
                location.setAddress(locationDTO.getRoadAddressName());
            } else {
                location.setAddress(locationDTO.getAddressName());
            }
            location.setX(locationDTO.getX());
            location.setY(locationDTO.getY());

            return this.locationRepository.save(location);
        }

        return null;
    }

    private boolean isValidLocationDTO(LocationDTO locationDTO) {
        return locationDTO != null &&
                StringUtils.isNotBlank(locationDTO.getLocationId()) &&
                StringUtils.isNotBlank(locationDTO.getPlaceName()) &&
                StringUtils.isNotBlank(locationDTO.getAddressName()) &&
                StringUtils.isNotBlank(locationDTO.getX()) &&
                StringUtils.isNotBlank(locationDTO.getY());
    }
    private boolean isValidLocationForm(LocationForm locationForm) {
        return locationForm != null &&
                StringUtils.isNotBlank(locationForm.getModifyLocationId()) &&
                StringUtils.isNotBlank(locationForm.getModifyPlaceName()) &&
                StringUtils.isNotBlank(locationForm.getModifyAddressName()) &&
                StringUtils.isNotBlank(locationForm.getModify_x()) &&
                StringUtils.isNotBlank(locationForm.getModify_y());
    }

    public List<LocationDTO> getCoordinateByKeyword(String keyword) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;

        List<LocationDTO> locationDTOList = new ArrayList<>();

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri1)
                .queryParam("query",keyword)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 정보 추출하기 (공식문서 참조)
        // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword
        JSONArray documents = json.getJSONArray("documents");

        for (int i = 0; i < documents.length(); i++) {
            JSONObject locationJson = documents.getJSONObject(i);

            String id = locationJson.getString("id");
            String placeName = locationJson.getString("place_name");
            String addressName = locationJson.getString("address_name");
            String roadAddressName = locationJson.getString("road_address_name");
            String x = locationJson.getString("x");
            String y = locationJson.getString("y");


            // LocationDTO 생성 및 리스트에 추가
            LocationDTO locationDTO = new LocationDTO(id, placeName, addressName, roadAddressName, x, y);
            locationDTOList.add(locationDTO);
        }

        return locationDTOList;
    }

    public LocationDTO getCoordinateByAddress() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;
        String address = "대전광역시 유성구 계룡로 지하97 (봉명동)";

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri2)
                .queryParam("query",address)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 추출하기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 정보 추출하기
        JSONArray documents = json.getJSONArray("documents");
        String id = documents.getJSONObject(0).getString("id");
        String placeName = documents.getJSONObject(0).getString("place_name");
        String addressName = documents.getJSONObject(0).getString("address_name");
        String roadAddressName = documents.getJSONObject(0).getString("road_address_name");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        return new LocationDTO(id, placeName, addressName, roadAddressName, x, y);
    }

    @Transactional
    public Location createNewLocation(LocationForm locationForm) {

        Location location = new Location();

        if (isValidLocationForm(locationForm)) {
            location.setLocationId(locationForm.getModifyLocationId());
            location.setPlaceName(locationForm.getModifyPlaceName());
            if (locationForm.getModifyRoadAddressName() != null) {
                location.setAddress(locationForm.getModifyRoadAddressName());
            } else {
                location.setAddress(locationForm.getModifyAddressName());
            }
            location.setX(locationForm.getModify_x());
            location.setY(locationForm.getModify_y());
            return this.locationRepository.save(location);
        }
        return locationForm.toLocation();
    }

    @Transactional
    public void uselessLocationDelete(Location targetLocation) {

        // Location이 null이면 아무 작업을 하지 않고 종료
        if (targetLocation == null) {
            return;
        }
        // 해당 Location을 참조하는 Board가 있는지 확인
        List<Board> referringBoards = this.boardRepository.findByLocation(targetLocation);

        // 참조하는 Board가 없다면 삭제 진행
        if (referringBoards.isEmpty()) {
            this.locationRepository.delete(targetLocation);
        }
    }
}
