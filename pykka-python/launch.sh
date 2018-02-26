
#!/bin/sh

IMAGE_NAME="pykka-python python AkkaMain.py"
shift

if [ "${IMAGE_NAME}" = "" ]; then
  exit 1
fi

docker run --rm -it \
  -v $(pwd):/usr/local/src/${IMAGE_NAME} \
  -w /usr/local/src/${IMAGE_NAME} \
  ${IMAGE_NAME} "$@"