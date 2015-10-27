#!/bin/sh

cf cups eureka-service -p '{"tag":"eureka","uri":"service-discovery-bb.pezapp.io"}'
cf create-service p-mongodb development mongodb-service
cf create-service p-rabbitmq standard rabbitmq-service
