import entities
import pygame
import ordered_list
import actions
import occ_grid
import point

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
    
    def remove_entity(self, entity):
        self.remove_entity_at(entities.get_position(entity))
    
    def remove_entity_at(self, pt):
        if (pt.within_bounds(self) and
            occ_grid.get_cell(self.occupancy, pt) != None):
            entity = occ_grid.get_cell(self.occupancy, pt)
            entities.set_position(entity, point.Point(-1, -1))
            self.entities.remove(entity)
            occ_grid.set_cell(self.occupancy, pt, None)
    
    
    def unschedule_action(self, action):
        self.action_queue.remove(action)
    
    def update_on_time(self, ticks):
        tiles = []
        
        next = self.action_queue.head()
        while next and next.ord < ticks:
            self.action_queue.pop()
            tiles.extend(next.item(ticks))  # invoke action function
            next = self.action_queue.head()
        
        return tiles
            
    def schedule_action(self, action, time):
        self.action_queue.insert(action, time)
    
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
    
    def add_entity(self, entity):
        pt = entities.get_position(entity)
        if pt.within_bounds(self):
            old_entity = occ_grid.get_cell(self.occupancy, pt)
            if old_entity != None:
                entities.clear_pending_actions(old_entity)
            occ_grid.set_cell(self.occupancy, pt, entity)
            self.entities.append(entity)


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
