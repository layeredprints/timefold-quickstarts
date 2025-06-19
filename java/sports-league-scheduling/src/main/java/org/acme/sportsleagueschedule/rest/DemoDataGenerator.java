package org.acme.sportsleagueschedule.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.sportsleagueschedule.domain.LeagueSchedule;
import org.acme.sportsleagueschedule.domain.Match;
import org.acme.sportsleagueschedule.domain.Round;
import org.acme.sportsleagueschedule.domain.Team;

@ApplicationScoped
public class DemoDataGenerator {

    private final int[][] distanceInKm = new int[][] {
            { 0, 2163, 2163, 2160, 2156, 2156, 2163, 340, 1342, 512, 3038, 1526, 2054, 2054, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 0, 11, 50, 813, 813, 11, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 11, 0, 50, 813, 813, 11, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 50, 50, 0, 862, 862, 50, 1957, 831, 1655, 1180, 1068, 161, 161, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 813, 813, 862, 0, 1, 813, 2083, 1160, 1741, 910, 644, 600, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 813, 813, 862, 1, 0, 813, 2083, 1160, 1741, 910, 644, 600, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 11, 11, 50, 813, 813, 0, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 340, 1967, 1967, 1957, 2083, 2083, 1967, 0, 1126, 341, 2926, 1490, 1836, 1836, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1342, 842, 842, 831, 1160, 1160, 842, 1126, 0, 831, 1874, 820, 714, 714, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 512, 1661, 1661, 1655, 1741, 1741, 1661, 341, 831, 0, 2589, 1151, 1545, 1545, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 3038, 1139, 1139, 1180, 910, 910, 1139, 2926, 1874, 2589, 0, 1552, 1340, 1340, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1526, 1037, 1037, 1068, 644, 644, 1037, 1490, 820, 1151, 1552, 0, 1077, 1077, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2054, 202, 202, 161, 600, 600, 202, 1836, 714, 1545, 1340, 1077, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2054, 202, 202, 161, 600, 600, 202, 1836, 714, 1545, 1340, 1077, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 2163, 2163, 2160, 2156, 2156, 2163, 340, 1342, 512, 3038, 1526, 2054, 2054, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 0, 11, 50, 813, 813, 11, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 11, 0, 50, 813, 813, 11, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 50, 50, 0, 862, 862, 50, 1957, 831, 1655, 1180, 1068, 161, 161, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 813, 813, 862, 0, 1, 813, 2083, 1160, 1741, 910, 644, 600, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2160, 813, 813, 862, 1, 0, 813, 2083, 1160, 1741, 910, 644, 600, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 2163, 11, 11, 50, 813, 813, 0, 1967, 842, 1661, 1139, 1037, 202, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 340, 1967, 1967, 1957, 2083, 2083, 1967, 0, 1126, 341, 2926, 1490, 1836, 1836, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1342, 842, 842, 831, 1160, 1160, 842, 1126, 0, 831, 1874, 820, 714, 714, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    };

    private final Random random = new Random(0);

    public LeagueSchedule generateDemoData() {
        LeagueSchedule schedule = new LeagueSchedule();
        // Rounds
        int countRounds = 32;
        List<Round> rounds = generateRounds(countRounds);
        // Teams
        List<Team> teams = generateTeams();
        // Matches
        List<Match> matches = generateMatches(teams);
        // Update schedule
        schedule.setRounds(rounds);
        schedule.setTeams(teams);
        schedule.setMatches(matches);
        return schedule;
    }

    private List<Round> generateRounds(int countRounds) {
        List<Round> rounds = IntStream.range(0, countRounds)
                .mapToObj(Round::new)
                .toList();

        // Rounds at weekends set as important
        LocalDate today = LocalDate.now();
        rounds.stream()
                .filter(round -> today.plusDays(round.getIndex()).getDayOfWeek() == DayOfWeek.SATURDAY
                        || today.plusDays(round.getIndex()).getDayOfWeek() == DayOfWeek.SUNDAY)
                .forEach(round -> round.setWeekendOrHoliday(true));
        return rounds;
    }

    private List<Team> generateTeams() {
        List<Team> teams = List.of(
                new Team("NAT1A-1", "BRAX-U14B-1", "NAT1A"),
                new Team("NAT1A-2", "DRAG U14B-1", "NAT1A"),
                new Team("NAT1A-3", "GANT U14B-1", "NAT1A"),
                new Team("NAT1A-4", "LARA U14B-1", "NAT1A"),
                new Team("NAT1A-5", "LEOP U14B-1", "NAT1A"),
                new Team("NAT1A-6", "OREE U14B-2", "NAT1A"),
                new Team("NAT1A-7", "RACI U14B-1", "NAT1A"),
                new Team("NAT1A-8", "SGEO U14B-1", "NAT1A"),
                new Team("NAT1A-9", "UCCL U14B-1", "NAT1A"),
                new Team("NAT1A-10", "VICT U14B-1", "NAT1A"),
                new Team("NAT1A-11", "WADU U14B-1", "NAT1A"),

                new Team("NAT1B-1", "EMBG-U14B-1", "NAT1B"),
                new Team("NAT1B-2", "GANT U14B-2", "NAT1B" ),
                new Team("NAT1B-3", "HERA U14B-1", "NAT1B"),
                new Team("NAT1B-4", "INDI U14B-1", "NAT1B"),
                new Team("NAT1B-5", "LEOP U14B-2", "NAT1B"),
                new Team("NAT1B-6", "LEUV U14B-1", "NAT1B"),
                new Team("NAT1B-7", "NAMU U14B-1", "NAT1B"),
                new Team("NAT1B-8", "OREE U14B-1", "NAT1B"),
                new Team("NAT1B-9", "PARC U14B-1", "NAT1B"),
                new Team("NAT1B-10", "PING U14B-1", "NAT1B"),
                new Team("NAT1B-11", "WADU U14B-2", "NAT1B"),
                new Team("NAT1B-12", "WHIT U14B-1", "NAT1B"));

       // Distances
        for (int i = 0; i < teams.size(); i++) {
            Map<Team, Integer> distances = new HashMap<>();
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    distances.put(teams.get(j), distanceInKm[i][j]);
                }
            }
            teams.get(i).setDistanceToTeam(distances);
        }

        return teams;
    }

    private List<Match> generateMatches(List<Team> teams) {
        List<Match> matches = new ArrayList<>(teams.size() * teams.size());
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    Team team1 = teams.get(i);
                    Team team2 = teams.get(j);
                    if(team1.getDivision().equals(team2.getDivision())) {
                        matches.add(new Match("%s-%s".formatted(team1.getId(), team2.getId()), team1, team2));
                    }
                }
            }
        }

        // 5% classic matches
        applyRandomValue((int) (matches.size() * 0.05), matches, match -> !match.isClassicMatch(),
                round -> round.setClassicMatch(true));
        matches.stream()
                .filter(match -> matches.stream()
                        .anyMatch(otherMatch -> match.getHomeTeam().equals(otherMatch.getAwayTeam())
                                && match.getAwayTeam().equals(otherMatch.getHomeTeam()) && otherMatch.isClassicMatch()))
                .forEach(match -> match.setClassicMatch(true));
        return matches;
    }

    private <T> void applyRandomValue(int count, List<T> values, Predicate<T> filter, Consumer<T> consumer) {
        int size = (int) values.stream().filter(filter).count();
        for (int i = 0; i < count; i++) {
            values.stream()
                    .filter(filter)
                    .skip(size > 0 ? random.nextInt(size) : 0).findFirst()
                    .ifPresent(consumer::accept);
            size--;
            if (size < 0) {
                break;
            }
        }
    }
}
