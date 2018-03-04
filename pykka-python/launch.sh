
#!/bin/sh

IMAGE_NAME="pykka"
shift

docker run --rm -it \
  -v $(pwd):/usr/local/src/${IMAGE_NAME} \
  -w /usr/local/src/${IMAGE_NAME} \
  ${IMAGE_NAME} "$@"