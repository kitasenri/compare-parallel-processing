#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pykka

class MyActor1(pykka.Threading):
    """My First Actor"""

    def on_receive(self, request):
        """Message handler"""

        message = request['message']
        print('Hello, {0}!'.format(message)


def main():

    actor_ref = MyActor1.



if __name__ == '__main__':
    main()