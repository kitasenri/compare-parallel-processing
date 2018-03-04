FROM python:3

##
## docker build -t pykka .
## docker run --rm -it pykka bash
##

RUN curl https://bootstrap.pypa.io/get-pip.py | python

RUN pip install pykka
#RUN pip install virtualenv
RUN pip install requests==2.18.4 click==6.7

COPY AkkaMain.py ~/AkkaMain.py
#RUN python ~/AkkaMain.py
