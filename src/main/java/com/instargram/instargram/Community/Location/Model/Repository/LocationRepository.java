package com.instargram.instargram.Community.Location.Model.Repository;


import com.instargram.instargram.Community.Location.Model.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
