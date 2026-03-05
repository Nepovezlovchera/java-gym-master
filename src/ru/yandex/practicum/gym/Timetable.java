package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>>  timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, TrainingSession> dayTraining =
                timetable.getOrDefault(day, new TreeMap<>());
        dayTraining.put(time, trainingSession);
        timetable.put(day, dayTraining);

    }

    public Map<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, TrainingSession> dayTime = timetable.get(dayOfWeek);
        return dayTime.get(timeOfDay);
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
        // Создаем компаратор для сортировки по убыванию количества тренировок
        Comparator<CoachTrainingCount> comparator = new Comparator<>() {
            @Override
            public int compare(CoachTrainingCount a, CoachTrainingCount b) {
                return b.getCount() - a.getCount(); // по убыванию
            }
        };
        Collections.sort(coachTrainingCount, comparator);

        return coachTrainingCount;
    }
}

