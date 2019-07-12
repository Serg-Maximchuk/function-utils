#!/usr/bin/env bash
# release commands
./mvnw clean deploy
./mvnw nexus-staging:release
