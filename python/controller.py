import pygame
import worldview
import worldmodel
import point

KEY_DELAY = 400
KEY_INTERVAL = 100

TIMER_FREQUENCY = 100

def handle_keydown(view, event):
    view_delta = on_keydown(event)
    view.update_view(view_delta)

def on_keydown(event):
    x_delta = 0
    y_delta = 0
    if event.key == pygame.K_UP: y_delta -= 1
    if event.key == pygame.K_DOWN: y_delta += 1
    if event.key == pygame.K_LEFT: x_delta -= 1
    if event.key == pygame.K_RIGHT: x_delta += 1
    
    return (x_delta, y_delta)

def activity_loop(view, world):
    pygame.key.set_repeat(KEY_DELAY, KEY_INTERVAL)
    pygame.time.set_timer(pygame.USEREVENT, TIMER_FREQUENCY)
    
    while 1:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return
            elif event.type == pygame.USEREVENT:
                world.handle_timer_event(view)
            elif event.type == pygame.MOUSEMOTION:
                view.handle_mouse_motion(event)
            elif event.type == pygame.KEYDOWN:
                handle_keydown(view,event)

