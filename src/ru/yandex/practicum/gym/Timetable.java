package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, TrainingSession> dayTraining =
                timetable.getOrDefault(day, new TreeMap<>());
        dayTraining.put(time, trainingSession);
        timetable.put(day, dayTraining);
        for (Map.Entry<TimeOfDay, TrainingSession> entry : dayTraining.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public Map<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            return timetable.get(dayOfWeek);
        }
        return new HashMap<>();
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, TrainingSession> dayTime = timetable.get(dayOfWeek);
        if (dayTime != null) {
            return dayTime.get(timeOfDay);
        }
        return null;
    }

    public List<CoachTrainingCount> getCountByCoaches() {
        Map<Coach, Integer> coachCount = new HashMap<>();
        for (TreeMap<TimeOfDay, TrainingSession> trainingSessionMap : timetable.values()) {
            for (TrainingSession session : trainingSessionMap.values()) {
                Coach coach = session.getCoach();
                coachCount.put(coach, coachCount.getOrDefault(coach, 0) + 1);
            }
        }

        List<CoachTrainingCount> coachTrainingCount = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCount.entrySet()) {
            coachTrainingCount.add(new CoachTrainingCount(entry.getKey(), entry.getValue()));
        }
        Collections.sort(coachTrainingCount);

        return coachTrainingCount;
    }
}

