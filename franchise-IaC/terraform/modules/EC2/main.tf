resource "aws_instance" "app" {
  ami                         = "ami-084568db4383264d4"
  instance_type               = "t2.micro"
  key_name                    = var.key_name
  subnet_id                   = var.public_subnet_id
  vpc_security_group_ids      = [var.security_group_id]
  associate_public_ip_address = true

  tags = { Name = "Franchise-API-EC2" }
}
output "app_instance_id" {value = aws_instance.app.id}