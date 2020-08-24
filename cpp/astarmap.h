#ifndef ASTARMAP_H
#define ASTARMAP_H

namespace astar {

    class MapRegion {
       public:
        MapRegion(long xOffset, long yOffset, long x_size, long y_size);
        long getXOffset();
        long getYOffset();
        long getXSize();
        long getYSize();

       protected:
        long xOff = 0, yOff = 0;
        long xSize = 0, ySize = 0;
    };

    class AStarMap {
       public:
        AStarMap();
    };

};  // namespace astar

#endif  // ASTARMAP_H
