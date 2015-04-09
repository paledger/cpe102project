import worldmodel
import pygame
import math
import random
import point
import image_store
import actions


class Background:
   def __init__(self, name, imgs):
      self.name = name
      self.imgs = imgs
      self.current_img = 0


class MinerNotFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
      
   def miner_to_ore(self,world,ore):
      entity_pt = get_position(self)
      if not ore:
         return ([entity_pt], False)
      ore_pt = get_position(ore)
      if entity_pt.adjacent(ore_pt):
         set_resource_count(self,
            1 + get_resource_count(self))
         actions.remove_entity(world, ore)
         return ([ore_pt], True)
      else:
         new_pt = entity_pt.next_position(world, ore_pt)
         return (world.move_entity(self, new_pt), False)

   def create_miner_not_full_action(self, world, i_store):
      def action(current_ticks):
         remove_pending_action(self, action)

         entity_pt = get_position(self)
         ore = world.find_nearest(entity_pt,Ore)
         (tiles, found) = self.miner_to_ore(world,ore)

         new_entity = self
         if found:
            new_entity = actions.try_transform_miner(world, self,
               actions.try_transform_miner_not_full)

         actions.schedule_action(world, new_entity,
            actions.create_miner_action(world, new_entity, i_store),
            current_ticks + get_rate(new_entity))
         return tiles
      return action      

class MinerFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = resource_limit
      self.animation_rate = animation_rate
      self.pending_actions = []

   def miner_to_smith(self, world, smith):
      entity_pt = get_position(self)
      if not smith:
         return ([entity_pt], False)
      smith_pt = get_position(smith)
      if entity_pt.adjacent(smith_pt):
         set_resource_count(smith,
            get_resource_count(smith) +
            get_resource_count(self))
         set_resource_count(self, 0)
         return ([], True)
      else:
         new_pt = entity_pt.next_position(world,smith_pt)
         return (world.move_entity(self, new_pt), False)

   def create_miner_full_action(self,world, i_store):
      def action(current_ticks):
         remove_pending_action(self, action)

         entity_pt = get_position(self)
         smith = world.find_nearest(entity_pt,Blacksmith)
         (tiles, found) = self.miner_to_smith(world,smith)

         new_entity = self
         if found:
            new_entity = actions.try_transform_miner(world, self,
               actions.try_transform_miner_full)

         actions.schedule_action(world, new_entity,
            actions.create_miner_action(world, new_entity, i_store),
               current_ticks + get_rate(new_entity))
         return tiles
      return action

class Vein:
    def __init__(self, name, rate, position, imgs, resource_distance=1):
        self.name = name
        self.position = position
        self.rate = rate
        self.imgs = imgs
        self.current_img = 0
        self.resource_distance = resource_distance
        self.pending_actions = []

    def create_vein_action(self, world, i_store):
        def action(current_ticks):
            remove_pending_action(self, action)
        
            open_pt = actions.find_open_around(world, self.position,
                self.resource_distance)
            if open_pt:
                ore = actions.create_ore(world,
                "ore - " + self.name + " - " + str(current_ticks),
                    open_pt, current_ticks, i_store)
                world.add_entity(ore)
                tiles = [open_pt]
            else:
                tiles = []
                    
            actions.schedule_action(world, self,
                            self.create_vein_action(world, i_store),
                            current_ticks + self.rate)
            return tiles
        return action

class Ore:
   def __init__(self, name, position, imgs, rate=5000):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.rate = rate
      self.pending_actions = []

class Blacksmith:
   def __init__(self, name, position, imgs, resource_limit, rate,
      resource_distance=1):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.rate = rate
      self.resource_distance = resource_distance
      self.pending_actions = []

class Obstacle:
   def __init__(self, name, position, imgs):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0

class OreBlob:
   def __init__(self, name, position, rate, imgs, animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

   def blob_next_position(self, world, dest_pt):
      horiz = actions.sign(dest_pt.x - self.position.x)
      new_pt = point.Point(self.position.x + horiz, self.position.y)

      if horiz == 0 or (new_pt.is_occupied(world) and
         not isinstance(world.get_tile_occupant(new_pt),
         Ore)):
         vert = actions.sign(dest_pt.y - self.position.y)
         new_pt = point.Point(self.position.x, self.position.y + vert)

         if vert == 0 or (new_pt.is_occupied(world) and
            not isinstance(world.get_tile_occupant(new_pt),Ore)):
            new_pt = point.Point(self.position.x, self.position.y)

      return new_pt

   def blob_to_vein(self,world, vein):
      entity_pt = get_position(self)
      if not vein:
         return ([entity_pt], False)
      vein_pt = get_position(vein)
      if entity_pt.adjacent(vein_pt):
         actions.remove_entity(world, vein)
         return ([vein_pt], True)
      else:
         new_pt = self.blob_next_position(world, vein_pt)
         old_entity = world.get_tile_occupant(new_pt)
         if isinstance(old_entity,Ore):
            actions.remove_entity(world, old_entity)
         return (world.move_entity(self, new_pt), False)

   def create_ore_blob_action(self,world,i_store):
      def action(current_ticks):
         remove_pending_action(self, action)

         entity_pt = get_position(self)
         vein = world.find_nearest(entity_pt, Vein)
         (tiles, found) = self.blob_to_vein(world,vein)

         next_time = current_ticks + get_rate(self)
         if found:
            quake = actions.create_quake(world, tiles[0],
                                         current_ticks, i_store)
            world.add_entity(quake)
            next_time = current_ticks + get_rate(self) * 2

         actions.schedule_action(world, self,
            self.create_ore_blob_action(world, i_store),
            next_time)

         return tiles
      return action

class Quake:
   def __init__(self, name, position, imgs, animation_rate):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []


def set_position(entity, point):
   entity.position = point

def get_position(entity):
   return entity.position


def get_images(entity):
   return entity.imgs

def get_image(entity):
   return entity.imgs[entity.current_img]


def get_rate(entity):
   return entity.rate


def set_resource_count(entity, n):
   entity.resource_count = n

def get_resource_count(entity):
   return entity.resource_count


def get_resource_limit(entity):
   return entity.resource_limit


def get_resource_distance(entity):
   return entity.resource_distance


def get_name(entity):
   return entity.name


def get_animation_rate(entity):
   return entity.animation_rate


def remove_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.remove(action)

def add_pending_action(entity, action):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions.append(action)


def get_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      return entity.pending_actions
   else:
      return []

def clear_pending_actions(entity):
   if hasattr(entity, "pending_actions"):
      entity.pending_actions = []


def next_image(entity):
   entity.current_img = (entity.current_img + 1) % len(entity.imgs)


# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.
def entity_string(entity):
   if isinstance(entity, MinerNotFull):
      return ' '.join(['miner', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.animation_rate)])
   elif isinstance(entity, Vein):
      return ' '.join(['vein', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate),
         str(entity.resource_distance)])
   elif isinstance(entity, Ore):
      return ' '.join(['ore', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate)])
   elif isinstance(entity, Blacksmith):
      return ' '.join(['blacksmith', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.resource_distance)])
   elif isinstance(entity, Obstacle):
      return ' '.join(['obstacle', entity.name, str(entity.position.x),
         str(entity.position.y)])
   else:
      return 'unknown'

