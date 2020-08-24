#include "point2d.h"
#include <cmath>

namespace astar {

    Point2D::Point2D(long x, long y, int traversalWeight) {
        xLoc = x;
        yLoc = y;
        weight = traversalWeight;
    }

    bool Point2D::getSearched() {
        return hasSearched;
    }

    void Point2D::setGoal(Point2D goal) {
        this->goalPoint = &goal;
    }

    void Point2D::reset() {
        predecesorPoint = nullptr;
        goalPoint = nullptr;
        hasSearched = false;
    }

    void Point2D::setPredecesor(Point2D predecessor) {
        predecesorPoint = &predecessor;
        internalCost = predecessor.internalCost + 1;
        hasSearched = true;
    }

    long Point2D::getF() {
        return weight * internalCost + distanceTo(*this, *goalPoint);
    }

    long Point2D::getX() {
        return xLoc;
    }

    long Point2D::getY() {
        return yLoc;
    }

    static long distanceTo(Point2D one, Point2D two) {
        return (long) sqrt(pow(one.getX() - two.getX(), 2) + pow(one.getY() - two.getY(), 2));
    }

};  // namespace astar
