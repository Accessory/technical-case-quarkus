package de.hermes.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Command {
    @Schema(example = "north")
    public String direction;
    public Integer steps;
}
