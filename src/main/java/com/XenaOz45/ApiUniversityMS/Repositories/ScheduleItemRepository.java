package com.XenaOz45.ApiUniversityMS.Repositories;

import com.XenaOz45.ApiUniversityMS.Entities.Day;
import com.XenaOz45.ApiUniversityMS.Entities.ScheduleItem;
import com.XenaOz45.ApiUniversityMS.Entities.WeekType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    List<ScheduleItem> findByGroupIdAndWeekType(Long groupId, WeekType weekType);
    List<ScheduleItem> findByGroupIdAndDayOfWeekAndWeekType(Long group_id, Day dayOfWeek, WeekType weekType);
    Page<ScheduleItem> findByGroupIdAndWeekType(Long groupId, WeekType weekType, Pageable pageable);
    Page<ScheduleItem> findByGroupIdAndDayOfWeekAndWeekType(Long groupId, Day dayOfWeek, WeekType weekType,
                                                            Pageable pageable);

    List<ScheduleItem> findByDisciplineLectorIdAndWeekType(Long lectorId, WeekType weekType);
    List<ScheduleItem> findByDisciplineLectorIdAndDayOfWeekAndWeekType(Long lectorId, Day dayOfWeek,
                                                                       WeekType weekType);
    List<ScheduleItem> findByDisciplineAssistantIdAndWeekType(Long assistantId, WeekType weekType);
    List<ScheduleItem> findByDisciplineAssistantIdAndDayOfWeekAndWeekType(Long assistantId, Day dayOfWeek,
                                                                       WeekType weekType);
    Page<ScheduleItem> findByDisciplineLectorIdAndWeekType(Long lectorId, WeekType weekType, Pageable pageable);
    Page<ScheduleItem> findByDisciplineLectorIdAndDayOfWeekAndWeekType(Long lectorId, Day dayOfWeek,
                                                                       WeekType weekType, Pageable pageable);
    Page<ScheduleItem> findByDisciplineAssistantIdAndWeekType(Long assistantId, WeekType weekType, Pageable pageable);
    Page<ScheduleItem> findByDisciplineAssistantIdAndDayOfWeekAndWeekType(Long assistantId, Day dayOfWeek,
                                                                         WeekType weekType,
                                                                         Pageable pageable);
    List<ScheduleItem> findByDisciplineId(Long disciplineId);
    
    List<ScheduleItem> findByDayOfWeekAfterAndPairNumber(Day dayOfWeek, int pairNumber);

    List<ScheduleItem> findByAuditoryNumber(String auditoryNumber);

    List<ScheduleItem> findByAuditoryNumberAndDayOfWeek(String auditoryNumber, Day dayOfWeek);

    @Query("SELECT s FROM ScheduleItem s WHERE " +
            "s.dayOfWeek = :day AND " +
            "s.pairNumber = :pairNum AND " +
            "(s.group.id = :groupId OR s.auditoryNumber = :auditoryNum OR " +
            "s.discipline.lector.id = :teacherId OR s.discipline.assistant.id = :teacherId)")
    List<ScheduleItem> findScheduleConflicts(
            @Param("day") Day dayOfWeek,
            @Param("pairNum") int pairNum,
            @Param("groupId") Long groupId,
            @Param("auditoryNum") String auditoryNum,
            @Param("teacherId") Long teacherId);

    @Query("SELECT s.dayOfWeek, COUNT(s) FROM ScheduleItem s GROUP BY s.dayOfWeek")
    List<Object[]> getScheduleDistributionByDay();

    @Query("SELECT s.auditoryNumber, COUNT(s) FROM ScheduleItem s GROUP BY s.auditoryNumber")
    List<Object[]> getAuditoryUsageStats();

    List<ScheduleItem> findByDayOfWeekAndPairNumberBetween(Day dayOfWeek, int startPair, int endPair);

}
