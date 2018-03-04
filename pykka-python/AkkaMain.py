#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pykka

MESSAGE = {
    'ACTOR1_REQUEST' : 'MessageActor1_Request',
    'ACTOR1_RESPONSE' : 'MessageActor2_Response',
}

class MyActor(pykka.ThreadingActor):

    def on_receive(self, request):
        message = request['message']
        print('Hello, {0}!'.format(message))

def main():
    actor_ref = MyActor.start()
    actor_ref.tell({
        'message' : MESSAGE['ACTOR1_REQUEST']
    })
    actor_ref.stop()

if __name__ == '__main__':
    main()
