# gaia

A terraform 🌍 ui

[![Build Status](https://travis-ci.org/CodeKaio/gaia.svg?branch=master)](https://travis-ci.org/CodeKaio/gaia)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.codeka%3Agaia&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.codeka%3Agaia)

## using state mgmt

gaia serves terraform state mgmt as an API, and can be used as a backend for `terraform`.

To use it, configure your terraform module using this snippet :

```
terraform {
    backend "http" {
		address="http://localhost:8080/api/state/12"
	}
}
```

## configuring gaia

Gaia can be configured using :

* environment variables
* java JVM options 
* in app settings

### environment variables

* `DOCKER_DAEMON_URL` / `gaia.dockerDaemonUrl` (default `unix:///var/run/docker.sock`) : configure docker daemon url for Gaia runner
* `EXTERNAL_URL` / `gaia.externalUrl` (default `http://localhost:8080`) : configure Gaia url for embedded terraform backend support
* `MONGODB_URI`  / `gaia.mongodb.uri` (default `mongodb://localhost/gaia`) : configure Gaia database access

## building and running with docker

Building Gaia with docker is simple :

```bash
docker build -t gaia .
```

To build mongo database with initiated data :

```bash
docker build -f Dockerfile-db -t gaia-db .
```

Either, we provide a simple `docker-compose.yml` to allow you to start gaia with the mongo database.

Just run `docker-compose up -d`


## healthcheck

healthcheck is available at the `/admin/health` URI.