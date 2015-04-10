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

def create_vein(world, name, pt, ticks, i_store):
    vein = entities.Vein("vein" + name,
                         random.randint(entities.VEIN_RATE_MIN, entities.VEIN_RATE_MAX),
                         pt, image_store.get_images(i_store, 'vein'))
    return vein
