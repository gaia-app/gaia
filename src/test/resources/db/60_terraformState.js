gaia = db.getSiblingDB('gaia');
gaia.terraformState.drop();
gaia.terraformState.insert([
    {
        "_id" : "de28a01f-257a-448d-8e1b-00e4e3a41db2",
        "value" : {
            "version" : 4,
            "terraform_version" : "0.12.20",
            "serial" : 0,
            "lineage" : "ea06e653-352b-ec53-90c5-58699c6c9043",
            "outputs" : {
                "docker_container_name" : {
                    "value" : "test",
                    "type" : "string"
                }
            },
            "resources" : [
                {
                    "mode" : "managed",
                    "type" : "docker_container",
                    "name" : "mongo",
                    "provider" : "provider.docker",
                    "instances" : [
                        {
                            "schema_version" : 1,
                            "attributes" : {
                                "attach" : false,
                                "bridge" : "",
                                "capabilities" : [],
                                "command" : null,
                                "container_logs" : null,
                                "cpu_set" : null,
                                "cpu_shares" : null,
                                "destroy_grace_seconds" : null,
                                "devices" : [],
                                "dns" : null,
                                "dns_opts" : null,
                                "dns_search" : null,
                                "domainname" : null,
                                "entrypoint" : null,
                                "env" : null,
                                "exit_code" : null,
                                "gateway" : "172.17.0.1",
                                "group_add" : null,
                                "healthcheck" : [],
                                "host" : [],
                                "hostname" : null,
                                "id" : "b247c86c6cee2c3ad6e9565c48cae29877374d07f7c6be5f42489002bdf66b42",
                                "image" : "sha256:5976dac61f4fb85c1a2d1f7c717600f9c78fb02badba6b3c5961a4091ef75905",
                                "ip_address" : "172.17.0.3",
                                "ip_prefix_length" : 16,
                                "ipc_mode" : null,
                                "labels" : null,
                                "links" : null,
                                "log_driver" : "json-file",
                                "log_opts" : null,
                                "logs" : false,
                                "max_retry_count" : null,
                                "memory" : null,
                                "memory_swap" : null,
                                "mounts" : [],
                                "must_run" : true,
                                "name" : "test",
                                "network_alias" : null,
                                "network_data" : [
                                    {
                                        "gateway" : "172.17.0.1",
                                        "ip_address" : "172.17.0.3",
                                        "ip_prefix_length" : 16,
                                        "network_name" : "bridge"
                                    }
                                ],
                                "network_mode" : null,
                                "networks" : null,
                                "networks_advanced" : [],
                                "pid_mode" : null,
                                "ports" : [
                                    {
                                        "external" : 28017,
                                        "internal" : 27017,
                                        "ip" : "0.0.0.0",
                                        "protocol" : "tcp"
                                    }
                                ],
                                "privileged" : null,
                                "publish_all_ports" : null,
                                "read_only" : false,
                                "restart" : "no",
                                "rm" : false,
                                "shm_size" : null,
                                "start" : true,
                                "sysctls" : null,
                                "tmpfs" : null,
                                "ulimit" : [],
                                "upload" : [],
                                "user" : null,
                                "userns_mode" : null,
                                "volumes" : [],
                                "working_dir" : null
                            },
                            "private" : "eyJzY2hlbWFfdmVyc2lvbiI6IjEifQ==",
                            "dependencies" : [
                                "docker_image.mongo"
                            ]
                        }
                    ]
                },
                {
                    "mode" : "managed",
                    "type" : "docker_image",
                    "name" : "mongo",
                    "provider" : "provider.docker",
                    "instances" : [
                        {
                            "schema_version" : 0,
                            "attributes" : {
                                "id" : "sha256:5976dac61f4fb85c1a2d1f7c717600f9c78fb02badba6b3c5961a4091ef75905mongo",
                                "keep_locally" : true,
                                "latest" : "sha256:5976dac61f4fb85c1a2d1f7c717600f9c78fb02badba6b3c5961a4091ef75905",
                                "name" : "mongo",
                                "pull_trigger" : null,
                                "pull_triggers" : null
                            },
                            "private" : "bnVsbA=="
                        }
                    ]
                }
            ]
        },
        "_class" : "io.gaia_app.stacks.bo.TerraformState"
    }
]);
