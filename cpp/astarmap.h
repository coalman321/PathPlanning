#ifndef ASTARMAP_H
#define ASTARMAP_H

#include <vector>
#include <QFile>

#include "point2d.h"

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
        AStarMap(QFile file);
        void loadRegion(MapRegion region);

       private:
        std::vector<std::vector<Point2D>> loadedNodes;
        MapRegion loadedRegion;
        long metaSizeX = 0, metaSizeY = 0;
        QFile fileRef;
    };

    /*
    QJsonDocument loadJson(QString fileName) {
        QFile jsonFile(fileName);
        jsonFile.open(QFile::ReadOnly);
        return QJsonDocument().fromJson(jsonFile.readAll());
    }

    void saveJson(QJsonDocument document, QString fileName) {
        QFile jsonFile(fileName);
        jsonFile.open(QFile::WriteOnly);
        jsonFile.write(document.toJson());
    }

    */

};  // namespace astar

#endif  // ASTARMAP_H
