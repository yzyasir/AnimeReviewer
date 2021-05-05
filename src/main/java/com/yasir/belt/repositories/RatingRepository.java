package com.yasir.belt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yasir.belt.models.Rating;

public interface RatingRepository extends CrudRepository<Rating, Long>{

	@Query(value="SELECT * FROM ratings WHERE user_id = ?1 AND show_id = ?2", nativeQuery=true)
	List<Rating> matchingRatings(Long user_id, Long show_id);
}
