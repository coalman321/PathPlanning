#ifndef POINT2D_H
#define POINT2D_H

#include <climits>
#include <string>

namespace astar {

    enum TraversalCost {
        IMPASSABLE = INT_MAX,
        UNKNOWN = INT_MAX / 2,
        DEFAULT = INT_MAX / 2,
        UNCERTAIN = INT_MAX / 4,
        PASSABLE = 4,
        TRAVERSED = 1
    };

    class Point2D {
       public:
        Point2D(long x, long y, int cost);
        bool getSearched();
        void setGoal(Point2D goal);
        void reset();
        void setPredecesor(Point2D predecessor);
        long getF();
        long getX();
        long getY();

        static long distanceTo(Point2D one, Point2D two);

       protected:
        long xLoc = 0;
        long yLoc = 0;
        int weight = INT_MAX;
        long internalCost = 0;
        bool hasSearched = false;
        Point2D* predecesorPoint = nullptr;
        Point2D* goalPoint = nullptr;
    };

};  // namespace astar

#endif  // POINT2D_H
