#!/bin/sh

curl https://bootstrap.pypa.io/get-pip.py | sudo python
sudo pip install virtualenv

mkdir env
virtualenv ./env
source ./env/bin/activate
pip install pykka
