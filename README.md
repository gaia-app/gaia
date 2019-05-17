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
