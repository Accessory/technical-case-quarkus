package de.hermes.services;

import de.hermes.entities.Job;
import de.hermes.model.Line;
import de.hermes.model.Position;
import de.hermes.requests.Command;
import de.hermes.requests.EnterPathRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RobotService {
    public static Job createJobFromEnterPathRequest(EnterPathRequest request) {
        int sum = 1;
        int intersections = 0;
        var start = System.nanoTime();
        Position currentPosition = request.start;
        List<Line> lines = new ArrayList<>();
        int toCheck = request.commands.size() - 1;
        for (int i = 0; i < request.commands.size(); i++) {
            Command command = request.commands.get(i);
            sum += command.steps;
            switch (command.direction.toLowerCase()) {
                case "north":
                    lines.add(new Line(
                            currentPosition.copy(),
                            new Position(currentPosition.x, currentPosition.y + (command.steps - (toCheck == i ? 0 : 1)))
                    ));
                    currentPosition.y += command.steps;
                    break;
                case "south":
                    lines.add(new Line(
                            currentPosition.copy(),
                            new Position(currentPosition.x, currentPosition.y - (command.steps - (toCheck == i ? 0 : 1)))
                    ));
                    currentPosition.y -= command.steps;
                    break;
                case "west":
                    lines.add(new Line(
                            currentPosition.copy(),
                            new Position(currentPosition.x - (command.steps - (toCheck == i ? 0 : 1)), currentPosition.y)
                    ));
                    currentPosition.x -= command.steps;
                    break;
                case "east":
                    lines.add(new Line(
                            currentPosition.copy(),
                            new Position(currentPosition.x + (command.steps - (toCheck == i ? 0 : 1)), currentPosition.y)
                    ));
                    currentPosition.x += command.steps;
                    break;
            }
        }


        for (int i = 0; i < lines.size(); i++) {
            var l1 = lines.get(i);
            for (int j = i + 1; j < lines.size(); j++) {
                var l2 = lines.get(j);
                intersections += l1.intersections(l2);
            }
        }


        var elapsed = (double) System.nanoTime() - start;

        return new Job(Instant.now(), request.commands.size(), sum - intersections, elapsed / 1_000_000_000);
    }
}
