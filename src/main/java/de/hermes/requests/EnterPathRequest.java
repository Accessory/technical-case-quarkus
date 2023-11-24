package de.hermes.requests;

import de.hermes.model.Position;
import java.util.List;

public class EnterPathRequest {
    public Position start;
    public List<Command> commands;
}
