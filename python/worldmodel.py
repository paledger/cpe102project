import entities
import pygame
import ordered_list
import actions
import occ_grid
import point
import image_store
import random

class WorldModel:
    def __init__(self, num_rows, num_cols, background):
        self.background = occ_grid.Grid(num_cols, num_rows, backgroundd)
        self.num_rows = num_rows
        self.num_cols = num_cols
        self.occupancy = occ_grid.Grid(num_cols, num_rows, None)
        self.entities = []
        self.action_queue = ordered_list.OrderedList()

    def handle_timer_event(self, view):
        rects = self.update_on_time(pygame.time.get_ticks())
        view.update_view_tiles(rects)
    

    def find_nearest(self, pt, type):
        oftype = [(e, pt.distance_sq(e.get_position()))
                  for e in self.entities if isinstance(e, type)]
                  
        return nearest_entity(oftype)
    
    def move_entity(self, entity, pt):
        tiles = []
        if pt.within_bounds(self):
            old_pt = entity.get_position()
            self.occupancy.set_cell(old_pt, None)
            tiles.append(old_pt)
            self.occupancy.set_cell(pt, entity)
            tiles.append(pt)
            entity.set_position(pt)
        
        return tiles
    
    def add_entity(self, entity):
        pt = entity.get_position()
        if pt.within_bounds(self):
            old_entity = self.occupancy.get_cell(pt)
            if old_entity != None:
                old_entity.clear_pending_actions()
            self.occupancy.set_cell(pt, entity)
            self.entities.append(entity)

    def remove_entity(self, entity):
        for action in entity.get_pending_actions():
            self.unschedule_action(action)
        entity.clear_pending_actions()
        self.remove_entity_at(entity.get_position())
        
    #def remove_entity(self, entity):
    #   self.remove_entity_at(entities.get_position(entity))
    ##Allison: Moved the remove_entity from actions.py to here.
    ##Added this functionality to that method instead.
    
    def remove_entity_at(self, pt):
        if (pt.within_bounds(self) and
            self.occupancy.get_cell(pt) != None):
            entity = self.occupancy.get_cell(pt)
            entity.set_position(point.Point(-1, -1))
            self.entities.remove(entity)
            self.occupancy.set_cell(pt, None)
    
    def schedule_action(self, entity, action, time):
        entity.add_pending_action(action)
        self.action_queue.insert(action, time)

   # def schedule_action(self, action, time):
   #     self.action_queue.insert(action, time)
   ##Allison: Moved the schedule_action from actions.py to here.
   ##Added this functionality to that method instead.
        
    def unschedule_action(self, action):
        self.action_queue.remove(action)

    def clear_pending_actions(self, entity):
        for action in entity.get_pending_actions():
            self.unschedule_action(action)
        entity.clear_pending_actions()

    def create_entity_death_action(self, entity):
        def action(current_ticks):
            entity.remove_pending_action(action)
            pt = entity.get_position()
            self.remove_entity(entity)
            return [pt]
        return action

    def schedule_animation(self, entity, repeat_count=0):
        self.schedule_action(entity,
            self.create_animation_action(entity, repeat_count),
                              entity.get_animation_rate())

    def create_animation_action(self, entity, repeat_count):
        def action(current_ticks):
            entity.remove_pending_action(action)

            entity.next_image()

            if repeat_count != 1:
                self.schedule_action(entity,
                    self.create_animation_action(entity,
                    max(repeat_count - 1, 0)),
                    current_ticks + entity.get_animation_rate())

            return [entity.get_position()]
        return action
   
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
            return self.background.get_cell(pt).get_image()
    
    
    def get_background(self, pt):
        if pt.within_bounds(self):
            return self.background.get_cell(pt)
    
    
    def set_background(self, pt, bgnd):
        if pt.within_bounds(self):
            self.background.set_cell(pt, bgnd)
    
    
    def get_tile_occupant(self, pt):
        if pt.within_bounds(self):
            return self.occupancy.get_cell(pt)
    
    
    def get_entities(self):
        return self.entities

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
