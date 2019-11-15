output "instance_ip_addr" {
  value       = "${aws_instance.server.private_ip}"
  description = "The private IP address of the main server instance."
}

output "db_password" {
  description = "The password for logging in to the database."
  value       = aws_db_instance.db[1].password
  sensitive   = true
}