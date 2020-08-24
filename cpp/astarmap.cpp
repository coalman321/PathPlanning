#include "astarmap.h"

namespace astar {

    MapRegion::MapRegion(long xOffset, long yOffset, long x_size, long y_size) {
        xOff = xOffset;
        yOff = yOffset;
        xSize = x_size;
        ySize = y_size;
    }

    long MapRegion::getXOffset() {
        return xOff;
    }

    long MapRegion::getYOffset() {
        return yOff;
    }

    long MapRegion::getXSize() {
        return xSize;
    }

    long MapRegion::getYSize() {
        return ySize;
    }

    AStarMap::AStarMap(QFile mapFile) {

    }

    void AStarMap::loadRegion(MapRegion region){

    }

};  // namespace astar
