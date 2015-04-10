import entities
import pygame
import ordered_list
import actions
import occ_grid
import point
import image_store
import random

BLOB_RATE_SCALE = 4
BLOB_ANIMATION_RATE_SCALE = 50
BLOB_ANIMATION_MIN = 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000

class WorldModel:
    def __init__(self, num_rows, num_cols, background):
        self.background = occ_grid.Grid(num_cols, num_rows, background)
        self.num_rows = num_rows
        self.num_cols = num_cols
        self.occupancy = occ_grid.Grid(num_cols, num_rows, None)
        self.entities = []
        self.action_queue = ordered_list.OrderedList()
    
    def find_nearest(self, pt, type):
        oftype = [(e, pt.distance_sq(entities.get_position(e)))
                  for e in self.entities if isinstance(e, type)]
                  
        return nearest_entity(oftype)
    
    def move_entity(self, entity, pt):
        tiles = []
        if pt.within_bounds(self):
            old_pt = entities.get_position(entity)
            occ_grid.set_cell(self.occupancy, old_pt, None)
            tiles.append(old_pt)
            occ_grid.set_cell(self.occupancy, pt, entity)
            tiles.append(pt)
            entities.set_position(entity, pt)
        
        return tiles
    
    def add_entity(self, entity):
        pt = entities.get_position(entity)
        if pt.within_bounds(self):
            old_entity = occ_grid.get_cell(self.occupancy, pt)
            if old_entity != None:
                entities.clear_pending_actions(old_entity)
            occ_grid.set_cell(self.occupancy, pt, entity)
            self.entities.append(entity)

    def remove_entity(self, entity):
        for action in entities.get_pending_actions(entity):
            self.unschedule_action(action)
        entities.clear_pending_actions(entity)
        self.remove_entity_at(entities.get_position(entity))
        
    #def remove_entity(self, entity):
    #   self.remove_entity_at(entities.get_position(entity))
    ##Allison: Moved the remove_entity from actions.py to here.
    ##Added this functionality to that method instead.
    
    def remove_entity_at(self, pt):
        if (pt.within_bounds(self) and
            occ_grid.get_cell(self.occupancy, pt) != None):
            entity = occ_grid.get_cell(self.occupancy, pt)
            entities.set_position(entity, point.Point(-1, -1))
            self.entities.remove(entity)
            occ_grid.set_cell(self.occupancy, pt, None)
    
    def schedule_action(self, entity, action, time):
        entities.add_pending_action(entity, action)
        self.action_queue.insert(action, time)

   # def schedule_action(self, action, time):
   #     self.action_queue.insert(action, time)
   ##Allison: Moved the schedule_action from actions.py to here.
   ##Added this functionality to that method instead.
        
    def unschedule_action(self, action):
        self.action_queue.remove(action)

    def clear_pending_actions(self, entity):
        for action in entities.get_pending_actions(entity):
            self.unschedule_action(action)
        entities.clear_pending_actions(entity)
    
    def update_on_time(self, ticks):
        tiles = []
        
        next = self.action_queue.head()
        while next and next.ord < ticks:
            self.action_queue.pop()
            tiles.extend(next.item(ticks))  # invoke action function
            next = self.action_queue.head()
        
        return tiles
    
    def get_background_image(self, pt):
        if pt.within_bounds(self):
            return entities.get_image(occ_grid.get_cell(self.background, pt))
    
    
    def get_background(self, pt):
        if pt.within_bounds():
            return occ_grid.get_cell(self.background, pt)
    
    
    def set_background(self, pt, bgnd):
        if pt.within_bounds(self):
            occ_grid.set_cell(self.background, pt, bgnd)
    
    
    def get_tile_occupant(self, pt):
        if pt.within_bounds(self):
            return occ_grid.get_cell(self.occupancy, pt)
    
    
    def get_entities(self):
        return self.entities
    
    def create_ore(self, name, pt, ticks, i_store):
        ore = entities.Ore(name, pt, image_store.get_images(i_store, 'ore'),
                  random.randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX))
        ore.schedule_ore(self, ticks, i_store)

        return ore

    def create_blob(self, name, pt, rate, ticks, i_store):
      blob = entities.OreBlob(name, pt, rate,
         image_store.get_images(i_store, 'blob'),
         random.randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
         * BLOB_ANIMATION_RATE_SCALE)
      blob.schedule_blob(self, ticks, i_store)
      return blob

    def create_quake(self, pt, ticks, i_store):
      quake = entities.Quake("quake", pt,
         image_store.get_images(i_store, 'quake'), QUAKE_ANIMATION_RATE)
      quake.schedule_quake(self, ticks)
      return quake

    def create_vein(self, name, pt, ticks, i_store):
        vein = entities.Vein("vein" + name,
                             random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
                             pt, image_store.get_images(i_store, 'vein'))
        return vein

def nearest_entity(entity_dists):
    if len(entity_dists) > 0:
        pair = entity_dists[0]
        for other in entity_dists:
            if other[1] < pair[1]:
                pair = other
        nearest = pair[0]
    else:
        nearest = None
    
    return nearest
