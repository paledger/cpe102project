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

def find_open_around(world, pt, distance):
   for dy in range(-distance, distance + 1):
      for dx in range(-distance, distance + 1):
         new_pt = point.Point(pt.x + dx, pt.y + dy)

         if (new_pt.within_bounds(world) and
            (not new_pt.is_occupied(world))):
            return new_pt

   return None

def create_animation_action(world, entity, repeat_count):
   def action(current_ticks):
      entities.remove_pending_action(entity, action)

      entities.next_image(entity)

      if repeat_count != 1:
         schedule_action(world, entity,
            create_animation_action(world, entity, max(repeat_count - 1, 0)),
            current_ticks + entities.get_animation_rate(entity))

      return [entities.get_position(entity)]
   return action


def create_entity_death_action(world, entity):
   def action(current_ticks):
      entities.remove_pending_action(entity, action)
      pt = entities.get_position(entity)
      world.remove_entity(entity)
      return [pt]
   return action

def schedule_action(world, entity, action, time):
   entities.add_pending_action(entity, action)
   world.schedule_action(action, time)


def schedule_animation(world, entity, repeat_count=0):
   schedule_action(world, entity,
      create_animation_action(world, entity, repeat_count),
      entities.get_animation_rate(entity))


def clear_pending_actions(world, entity):
   for action in entities.get_pending_actions(entity):
      world.unschedule_action(action)
   entities.clear_pending_actions(entity)
