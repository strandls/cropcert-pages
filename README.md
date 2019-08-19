# Cropcert Pages

Pages API for Cropcert

[![Build Status](https://travis-ci.com/strandls/cropcert-pages.svg?branch=master)](https://travis-ci.com/strandls/cropcert-pages)

## 🚀 Quick start

```sh
mvn clean                   # Clean application
mvn install                 # Build application WAR and generate OpenAPI SDK
sh @ci/pre-configure-m2.sh  # Creates/Updates Maven `settings.xml` (If using local artificatory please set values in shellfile first)
mvn deploy                  # Deploys WAR to artifactory
```

## Building OpenAPI SDK

```sh
@ci/pre-configure-sdk.sh    # Updated `pom.xml` with artificatory configuration
cd target/sdk && mvn deploy # Deploys SDK to artifactory
```
