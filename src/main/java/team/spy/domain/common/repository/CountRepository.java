package team.spy.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.spy.domain.common.dto.Count;
import team.spy.domain.common.dto.CountProjectId;

public interface CountRepository extends JpaRepository<Count, CountProjectId>{
	Count findByCountProjectId(CountProjectId id);
}
