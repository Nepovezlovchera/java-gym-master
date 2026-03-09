package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.Coach;

public class CoachTrainingCount implements Comparable<CoachTrainingCount>{
    private Coach coach;
    private int count;

    public CoachTrainingCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CoachTrainingCount other) {
        return Integer.compare(other.count, this.count);
    }
    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }
}