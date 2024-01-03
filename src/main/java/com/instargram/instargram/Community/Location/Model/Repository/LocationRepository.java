package com.instargram.instargram.Community.Location.Model.Repository;


import com.instargram.instargram.Community.Location.Model.Entity.Location;
import com.instargram.instargram.Member.Model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByPlaceNameContaining(String keyword);
}
