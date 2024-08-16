package solarsystem;

import javax.validation.constraints.NotNull;

public class AstronomicalBody {

    @NotNull
    private final String name;
    @NotNull
    private final CoordinatesSpherical relativePosition;

    AstronomicalBody(String name, CoordinatesSpherical relativePosition) {
        this.name = name;
        this.relativePosition = relativePosition;
    }

    public String getName() {
        return name;
    }
    public CoordinatesSpherical getRelativePosition() {
        return relativePosition;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", name, relativePosition);
    }
}
