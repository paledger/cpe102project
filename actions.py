import entities
import worldmodel
import pygame
import math
import random
import point
import image_store

def sign(x):
   if x < 0:
      return -1
   elif x > 0:
      return 1
   else:
      return 0
   
def create_animation_action(world, entity, repeat_count):
   def action(current_ticks):
      entities.remove_pending_action(entity, action)

      entities.next_image(entity)

      if repeat_count != 1:
         world.schedule_action(entity,
            create_animation_action(world, entity, max(repeat_count - 1, 0)),
            current_ticks + entities.get_animation_rate(entity))

      return [entities.get_position(entity)]
   return action

def schedule_animation(world, entity, repeat_count=0):
   world.schedule_action(entity,
      create_animation_action(world, entity, repeat_count),
      entities.get_animation_rate(entity))
