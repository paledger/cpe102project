import actions
import entities
import worldmodel
import pygame
import math
import random
import image_store
import occ_grid

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def within_bounds(self,world):
        return (self.x >= 0 and self.x < world.num_cols and
                self.y >= 0 and self.y < world.num_rows)
    
    def is_occupied(self, world):
        return (self.within_bounds(world) and
                occ_grid.get_cell(world.occupancy, self) != None)
    
    def distance_sq(self, p2):
        return (self.x - p2.x)**2 + (self.y - p2.y)**2
    
    def adjacent(self,pt2):
        return ((self.x == pt2.x and abs(self.y - pt2.y) == 1) or
              (self.y == pt2.y and abs(self.x - pt2.x) == 1))
    def next_position(self, world, dest_pt):
        horiz = actions.sign(dest_pt.x - self.x)
        new_pt = Point(self.x + horiz, self.y)
      
        if horiz == 0 or new_pt.is_occupied(world):
            vert = actions.sign(dest_pt.y - self.y)
            new_pt = Point(self.x, self.y + vert)

            if vert == 0 or new_pt.is_occupied(world):
                new_pt = Point(self.x, self.y)

        return new_pt
     
