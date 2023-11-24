package de.hermes.model;

public class Line {
    public Line(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position start;
    public Position end;

    public int intersections(Line other) {
        boolean selfIsHorizontal = this.isHorizontal();
        boolean otherIsHorizontal = other.isHorizontal();

        if (selfIsHorizontal && otherIsHorizontal) {
            if (start.y == other.start.y) {
                return other.getHorizontalOverlap(this);
            } else {
                return 0;
            }
        } else if (!selfIsHorizontal && !otherIsHorizontal) {
            if (start.x == other.start.x) {
                return getVerticalOverlap(other);
            } else {
                return 0;
            }
        } else if (selfIsHorizontal && !otherIsHorizontal) {
            if (hasIntersection(other)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (other.hasIntersection(this)) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    private boolean hasIntersection(Line other) {
        var thisStartX =  Math.min(start.x, end.x);
        var thisEndX =  Math.max(start.x, end.x);
        if (thisStartX > other.start.x || thisEndX < other.start.x) {
            return false;
        }

        var otherStartY =  Math.min(other.start.y, other.end.y);
        var otherEndY =  Math.max(other.start.y, other.end.y);

        return otherStartY < this.start.y && this.start.y < otherEndY;
    }

    private int getHorizontalOverlap(Line other) {
        int start = Math.min(this.start.x, this.end.x);
        int end = Math.max(this.start.x, this.end.x);
        int otherStart = Math.min(other.start.x, other.end.x);
        int otherEnd = Math.max(other.start.x, other.end.x);
        return overlapping(start, end, otherStart, otherEnd);
    }

    private int getVerticalOverlap(Line other) {
        int start = Math.min(this.start.y, this.end.y);
        int end = Math.max(this.start.y, this.end.y);
        int otherStart = Math.min(other.start.y, other.end.y);
        int otherEnd = Math.max(other.start.y, other.end.y);
        return overlapping(start, end, otherStart, otherEnd);
    }

    private int overlapping(int start, int end, int otherStart, int otherEnd) {
        var startMax = Math.max(otherStart, start);
        var endMin = Math.min(otherEnd, end);
        var overlap = endMin - startMax;

        if (overlap == 0) return 1;
        else return Math.max(0, overlap + 1);
    }

    private boolean isHorizontal() {
        return this.start.y == this.end.y;
    }
}
