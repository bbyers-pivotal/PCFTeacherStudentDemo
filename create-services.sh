#!/bin/sh

cf cups eureka-service -p '{"tag":"eureka","uri":"service-discovery.pezapp.io"}'
cf create-service p-mongodb development mongodb-service
