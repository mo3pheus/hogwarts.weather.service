#!/bin/bash

echo "This is the hogwarts weather service!"

java -jar /opt/hogwarts/weather.service/weather.service-1.0-SNAPSHOT-shaded.jar \
--debug.log.enabled false \
--log.file.path /var/log/weather.service/ \
--api.key f67f69a064918262e432661145c7ffc1 \
--weather.client.id hogwarts1234 \
--weather.command.channel hogwarts-engineering/weather-command \
--weather.destination hogwarts-engineering/weatherUpdate \
--weather.host tcp://3.219.5.247:1883 \
--latitude 19.07545 \
--longitude 72.99162
