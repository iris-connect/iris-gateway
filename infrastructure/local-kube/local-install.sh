#!/usr/bin/env bash
set -euo pipefail
cd ../../
headTag=$(git rev-parse HEAD)
if [[ "$(  docker images -f reference='inoeg/iris-backend-service:'"$headTag"''  2> /dev/null)" == "" ]]; then
  tag=$headTag
  echo "using local image with tag $tag"
  # try importing locally built images
  k3d image import inoeg/iris-backend-service:$tag
else
  tag=$(git rev-parse origin/develop)
fi

helm upgrade --install \
  --set environment=local --set locations.tag=$tag \
    iris-gateway ./infrastructure/iris-gateway  --wait --debug --timeout 3m
