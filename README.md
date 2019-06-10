# gaia

A terraform 🌍 ui

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