import actions
import entities
import worldmodel
import pygame
import math
import random
import image_store

class Point:
   def __init__(self, x, y):
      self.x = x
      self.y = y
   def adjacent(self,pt2):
      return ((self.x == pt2.x and abs(self.y - pt2.y) == 1) or
              (self.y == pt2.y and abs(self.x - pt2.x) == 1))
   def next_position(self, world, dest_pt):
      horiz = actions.sign(dest_pt.x - self.x)
      new_pt = Point(self.x + horiz, self.y)
      
      if horiz == 0 or worldmodel.is_occupied(world, new_pt):
         vert = actions.sign(dest_pt.y - self.y)
         new_pt = Point(self.x, self.y + vert)

         if vert == 0 or worldmodel.is_occupied(world, new_pt):
            new_pt = Point(self.x, self.y)

      return new_pt
     
