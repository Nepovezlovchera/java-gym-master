package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Map<TimeOfDay, TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertTrue(mondaySessions.containsKey(new TimeOfDay(13, 0)));
        assertEquals(singleTrainingSession, mondaySessions.get(new TimeOfDay(13, 0)));

        Map<TimeOfDay, TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        
        Map<TimeOfDay, TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertTrue(mondaySessions.containsKey(new TimeOfDay(13, 0)));
        assertEquals(mondayChildTrainingSession, mondaySessions.get(new TimeOfDay(13, 0)));


        Map<TimeOfDay, TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        
        TimeOfDay[] times = thursdaySessions.keySet().toArray(new TimeOfDay[0]);
        assertEquals(new TimeOfDay(13, 0), times[0]);
        assertEquals(thursdayChildTrainingSession, thursdaySessions.get(times[0]));
        assertEquals(new TimeOfDay(20, 0), times[1]);
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(times[1]));
        
        Map<TimeOfDay, TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        
        TrainingSession mondaySessionOn13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(singleTrainingSession, mondaySessionOn13);
        
        TrainingSession mondaySessionOn14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertNull(mondaySessionOn14);
    }

    @Test
    void testGetCountByCoachesEmpty() {
        Timetable timetable = new Timetable();
        List<CoachTrainingCount> coachTrainingCounts = timetable.getCountByCoaches();

        assertTrue(coachTrainingCounts.isEmpty());
    }

    @Test
    void testGetCountByCoacheOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        List<CoachTrainingCount>coachTrainingCounts = timetable.getCountByCoaches();

        assertEquals(1,coachTrainingCounts.size());
        assertEquals(3,coachTrainingCounts.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesWithMultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach shmatkov = new Coach("Шматков", "Виктор", "Викторович");
        Coach semenov = new Coach("Семёнов", "Владимир", "Константинович");
        Coach morev = new Coach("Морев", "Евгений", "Владимирович");

        Group group = new Group("Акробатика", Age.ADULT, 60);
        
        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.MONDAY, 
                new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.MONDAY, 
                new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.WEDNESDAY, 
                new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.FRIDAY, 
                new TimeOfDay(9, 0)));
        
        timetable.addNewTrainingSession(new TrainingSession(group, semenov, DayOfWeek.TUESDAY, 
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, semenov, DayOfWeek.THURSDAY, 
                new TimeOfDay(10, 0)));
        
        timetable.addNewTrainingSession(new TrainingSession(group, morev, DayOfWeek.SATURDAY, 
                new TimeOfDay(12, 0)));

        List<CoachTrainingCount>coachTrainingCounts = timetable.getCountByCoaches();

        assertEquals(3,coachTrainingCounts.size());

        assertEquals(4,coachTrainingCounts.get(0).getCount());
        assertEquals(shmatkov,coachTrainingCounts.get(0).getCoach());

        assertEquals(2,coachTrainingCounts.get(1).getCount());
        assertEquals(semenov,coachTrainingCounts.get(1).getCoach());

        assertEquals(1,coachTrainingCounts.get(2).getCount());
        assertEquals(morev,coachTrainingCounts.get(2).getCoach());
    }

    @Test
    void testGetCountByCoachesWithEqualCounts() {
        Timetable timetable = new Timetable();

        Coach shmatkov = new Coach("Шматков", "Виктор", "Викторович");
        Coach semenov = new Coach("Семёнов", "Владимир", "Константинович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov, DayOfWeek.WEDNESDAY,
                new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, semenov, DayOfWeek.TUESDAY,
                new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, semenov, DayOfWeek.THURSDAY,
                new TimeOfDay(10, 0)));

        List<CoachTrainingCount>coachTrainingCounts = timetable.getCountByCoaches();

        assertEquals(2,coachTrainingCounts.size());
        assertEquals(2,coachTrainingCounts.get(0).getCount());
        assertEquals(2,coachTrainingCounts.get(1).getCount());
    }

}
