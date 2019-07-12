#!/usr/bin/env bash
# release commands

./mvnw clean deploy
./mvnw nexus-staging:release

# maybe you will have to create GPG keys - step 7 in https://dzone.com/articles/publish-your-artifacts-to-maven-central
