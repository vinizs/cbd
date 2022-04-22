package edu.tus.offering.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import edu.tus.offering.dto.Offering;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
	Page<Offering> findByStartDateTime(LocalDateTime startDateTime, Pageable pageable);
	Page<Offering> findByEndDateTime(LocalDateTime endDateTime, Pageable pageable);
	Page<Offering> findByCourseId(long courseId, Pageable pageable);
	Optional<Offering> findByCourseId(long courseId);
}
