package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.Coach;

public class CoachTrainingCount {
    private Coach coach;
    private int count;

    public CoachTrainingCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }
}