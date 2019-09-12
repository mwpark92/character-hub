package team.spy.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.spy.domain.board.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
