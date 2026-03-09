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
    void testGetCountByCoaches_WhenNoTrainingSessions_ShouldReturnEmptyList() {
        Timetable timetable = new Timetable();

        List<CoachTrainingCount> list = timetable.getCountByCoaches();

        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testGetCountByCoaches_WhenMultipleSessionsAtSameTime_ShouldCountCorrectly() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach shmatkov = new Coach("Шматков", "Виктор", "Викторович");
        Coach semenov = new Coach("Семёнов", "Владимир", "Константинович");

        Group group = new Group("Акробатика", Age.ADULT, 60);


        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, semenov,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, shmatkov,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0)));

        List<CoachTrainingCount> list = timetable.getCountByCoaches();

        assertEquals(3, list.size());

        assertEquals(3, list.get(0).getCount());
        assertEquals(2, list.get(1).getCount());
        assertEquals(1, list.get(2).getCount());

    }

    @Test
    void testGetCountByCoaches_WhenMultipleSessionsAddedAtSameDayAndTime_ShouldCountOnlyLastSession() {

        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach shmatkov = new Coach("Шматков", "Виктор", "Викторович");
        Coach semenov = new Coach("Семёнов", "Владимир", "Константинович");

        Group groupYoga = new Group("Йога", Age.ADULT, 60);
        Group groupPilates = new Group("Пилатес", Age.ADULT, 90);
        Group groupBoxing = new Group("Бокс", Age.ADULT, 120);
        Group groupSwimming = new Group("Плавание", Age.CHILD, 45);
        Group groupRunning = new Group("Бег", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(groupYoga, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupPilates, shmatkov,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupBoxing, semenov,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupRunning, shmatkov,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupYoga, semenov,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        Map<String, Integer> actualCounts = new HashMap<>();
        for (CoachTrainingCount stats : result) {
            actualCounts.put(stats.getCoach().getSurname(), stats.getCount());
        }
        
        assertEquals(1, actualCounts.get("Шматков"));
        assertNull(actualCounts.get("Васильев"));
        assertEquals(2, actualCounts.get("Семёнов"));
    }
}