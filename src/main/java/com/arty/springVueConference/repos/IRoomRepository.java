package com.arty.springVueConference.repos;

import com.arty.springVueConference.domains.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long>
{

}
