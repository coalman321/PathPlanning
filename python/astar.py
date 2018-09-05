import numpy as np
import enum
import math

class TraversalState(enum):
    TRAVERSED = 1
    PASSSABLE = 2
    UNCERTAIN = 4
    UNKNOWN = 6
    IMPASSIBLE = 8


class Node:
    camefrom = None
    goal = None
    hassearched = False
    state = TraversalState
    x = 0
    y = 0
    cost = 0

    def __init__(self, x, y, state = None):
        if state is None:
            self.state = TraversalState.UNKNOWN
        else:
            self.state = state
        self.x = x
        self.y = y

    @staticmethod
    def distance(node1, node2):
        return int(math.hypot(node2.x - node1.x, node2.y - node1.y))

    def cantraverse(self):
        return self.state != TraversalState.IMPASSIBLE

    def setcamefrom(self, other):
        self.camefrom = other
        self.hassearched = True
        self.cost = other.cost + 1

    def getf(self):
        return int(self.state.value * self.cost + self.distance(self, self.goal))

    def reset(self):
        self.hassearched = False
        self.camefrom = None
        self.goal = None
