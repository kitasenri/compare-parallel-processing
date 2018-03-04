#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pykka

MESSAGE = {
    'ACTOR1_REQUEST'  : 'MessageActor1_Request',
    'ACTOR1_RESPONSE' : 'MessageActor1_Response',
    'ACTOR2_REQUEST'  : 'MessageActor2_Request',
    'ACTOR2_RESPONSE' : 'MessageActor2_Response',
    'ACTOR3_REQUEST'  : 'MessageActor3_Request',
    'ACTOR3_RESPONSE' : 'MessageActor3_Response',
}

class MyActor1(pykka.ThreadingActor):
    """ Actor1 """

    def on_receive(self, request):
        message = request['message']
        print('Receive {0}!'.format(message))


class MyActor2(pykka.ThreadingActor):
    """ Actor2 """

    def on_receive(self, request):
        message = request['message']
        print('Receive {0}!'.format(message))


class MyActor3(pykka.ThreadingActor):
    """ Actor3 """

    def on_receive(self, request):
        message = request['message']
        print('Receive {0}!'.format(message))
        return {
            'message' : MESSAGE['ACTOR3_RESPONSE']
        }


def main():

    ## Send To Actor1
    actor_ref1 = MyActor1.start()
    actor_ref2 = MyActor2.start()
    actor_ref3 = MyActor3.start()

    for ii in range(5):
        actor_ref1.tell({
            'message' : MESSAGE['ACTOR1_REQUEST']
        })

    actor_ref2.tell({
        'message' : MESSAGE['ACTOR2_REQUEST']
    })

    res_future = actor_ref3.ask(
        {
            'message' : MESSAGE['ACTOR3_REQUEST']
        },
        block=False
    )

    response = res_future.get(timeout=1)
    print('Receive {0}!'.format(response['message']))

    actor_ref1.stop()
    actor_ref2.stop()
    actor_ref3.stop()


if __name__ == '__main__':
    main()
